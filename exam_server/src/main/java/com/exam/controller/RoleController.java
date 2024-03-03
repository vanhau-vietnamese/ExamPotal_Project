package com.exam.controller;

import com.exam.model.Role;
import com.exam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/role")
@CrossOrigin("*")
@RestController
public class RoleController{
    private final RoleService roleService;
    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles(){
        return roleService.getAllRoles();
    }
    // admin update role từ student -> teacher
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updateRole(@RequestBody Map<String, String> request){
        return roleService.updateRole(request);
    }
}
