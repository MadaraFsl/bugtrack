package com.bugtracking.domain.repository;

import com.bugtracking.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    @Query("from User u where u.username=:username")
    User getUserByName(@Param("username") String username);

    @Query("from User u")
    List<User> getAllUser();

}
