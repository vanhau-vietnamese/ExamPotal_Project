package com.exam.service;

import com.exam.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public ResponseEntity<List<Role>> getAllRoles();
    public ResponseEntity<String> updateRole(Map<String, String> request);
}
