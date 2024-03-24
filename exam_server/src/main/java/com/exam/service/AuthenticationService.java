package com.exam.service;

import com.exam.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest);
}
