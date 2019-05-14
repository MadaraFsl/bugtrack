package com.bugtracking.service.common;

import com.bugtracking.domain.entity.User;
import com.bugtracking.domain.repository.UserRepository;
import com.bugtracking.tools.Util;
import com.bugtracking.vo.AuthorityVo;
import com.bugtracking.vo.Bugpriority;
import com.bugtracking.vo.Bugstatus;
import com.bugtracking.vo.Bugtype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QSort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("ShareService")
public class ShareServiceImpl implements ShareService {

    @Autowired
    private UserRepository userDao;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && (!AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass()))) {
            User user = userDao.getUserByName(auth.getName());
            return user;
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public List getAllBugType() {
        String sql = " SELECT " +
                " bugtype_id, " +
                " type_name  " +
                " FROM " +
                " bugtype";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), Bugtype.class);
    }

    @Override
    public List getAllBugStatus() {
        String sql = " SELECT " +
                " bugstatus_id, " +
                " bugstatus_cname  " +
                " FROM " +
                " bugstatus";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), Bugstatus.class);
    }

    @Override
    public List getAllBugPriority() {
        String sql = " SELECT " +
                " id, " +
                " cname  " +
                " FROM " +
                " bugpriority";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), Bugpriority.class);
    }

    @Override
    public List getAuthoritys() {
        String sql = " SELECT " +
                " id, " +
                " authority_ename, " +
                " authority_cname  " +
                " FROM " +
                " authority AS a  " +
                " WHERE " +
                " a.id !=1";
        Query query = entityManager.createNativeQuery(sql);
        return Util.transferObjectsToList(query.getResultList(), AuthorityVo.class);
    }
}
