package com.exam.controller;

import com.exam.dto.request.RegisterRequest;
import com.exam.service.AdminManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminManagerController {
    private final AdminManagerService adminManagerService;
    @PostMapping("/create/adminAccount")
    public ResponseEntity<?> createAdminAccount(@RequestBody RegisterRequest request){
        return adminManagerService.createAdminAccount(request);
    }

    @GetMapping("/adminAccount")
    public ResponseEntity<?> getAdminAccount(){
        return adminManagerService.getAllAdminAccount();
    }

    @DeleteMapping("/delete/adminAccount")
    public ResponseEntity<?> deleteAdminAccount(@RequestBody Map<String, String> request){
        return adminManagerService.deleteAdminAccount(request);
    }

    @GetMapping("/userAccount")
    public ResponseEntity<?> getUserAccount(){
        return adminManagerService.getAllUserAccount();
    }
}
