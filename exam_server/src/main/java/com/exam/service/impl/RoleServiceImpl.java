package com.exam.service.impl;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @Override
    public ResponseEntity<String> updateRole(Map<String, String> request) {
        User user = userRepository.findByUsername(request.get("username"));
        System.out.println("username: "+ user);
        if(user != null){
            Role role = new Role();
            role.setRoleId(Long.parseLong(request.get("roleId")));

            user.setRole(role);
            userRepository.save(user);

            return ResponseEntity.ok("Update Role Successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
