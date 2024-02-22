package com.exam.service;
import com.exam.model.User;
import com.exam.model.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface UserService {
    // create user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
}
