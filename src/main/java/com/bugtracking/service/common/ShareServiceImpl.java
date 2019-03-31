package com.bugtracking.service.common;

import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ShareService")
public class ShareServiceImpl implements ShareService {

    @Autowired
    private UserRepository userDao;


    @Override
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && (!AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass()))){
            User user = userDao.getUserByName(auth.getName());
            return user;
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }
}
