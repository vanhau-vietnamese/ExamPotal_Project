package com.exam.service;

import com.exam.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AdminManagerService {
    ResponseEntity<?> createAdminAccount(RegisterRequest request);

    ResponseEntity<?> getAllAdminAccount();

    ResponseEntity<?> deleteAdminAccount(Map<String, String> request);
}
