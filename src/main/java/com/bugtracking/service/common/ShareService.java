package com.bugtracking.service.common;

import com.bugtracking.domain.entity.User;

import java.util.List;


public interface ShareService {

    User getUser();

    List<User> getAllUser();
}
