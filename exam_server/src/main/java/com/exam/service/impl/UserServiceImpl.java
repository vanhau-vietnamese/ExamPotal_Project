package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User userLocal = userRepository.findByUserName(user.getUserName());
        if(userLocal != null){
            System.out.println("User is already there!!!");
            throw new Exception("User already present!!!");
        }
        else{
            // create user
            for(UserRole ur:userRoles){
                roleRepository.save(ur.getRole());
            }
            user.setUserRoles(userRoles);
            userLocal = userRepository.save(user);
        }
        return userLocal;
    }
}
