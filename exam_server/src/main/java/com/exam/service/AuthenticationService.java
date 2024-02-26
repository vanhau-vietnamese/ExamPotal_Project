package com.exam.service;

import com.exam.dto.request.LoginRequest;
import com.exam.dto.request.SignupRequest;
import com.exam.dto.response.JwtResponse;
import com.exam.model.user.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> register(SignupRequest request);
    public JwtResponse login(LoginRequest loginRequest);
}
