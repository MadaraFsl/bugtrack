package com.bugtracking.service;

import com.bugtracking.domain.entity.Authority;
import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }
        //对该用户下的所有权限进行身份记录
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 用户权限数
        for (Authority authority : user.getAuthoritySet()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + authority.getEname()));
            System.err.println("username is " + username + ", " + authority.getCname());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
