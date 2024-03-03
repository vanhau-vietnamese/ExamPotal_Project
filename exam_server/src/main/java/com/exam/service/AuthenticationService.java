package com.exam.service;

import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.ForgotPasswordRequest;
import com.exam.dto.request.LoginRequest;
import com.exam.dto.request.SignupRequest;
import com.exam.dto.response.JwtResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> register(SignupRequest request);
    public JwtResponse login(LoginRequest loginRequest);
    public ResponseEntity<String> changePassword(ChangePasswordRequest request);

    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest emailRequest) throws MessagingException;
}
