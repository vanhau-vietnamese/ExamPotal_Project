package com.exam.controller;

import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.ForgotPasswordRequest;
import com.exam.dto.request.LoginRequest;
import com.exam.dto.request.SignupRequest;
import com.exam.dto.response.JwtResponse;
import com.exam.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController{
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request){
        System.out.println("1234");
        return authenticationService.register(request);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        return authenticationService.changePassword(request);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest emailRequest) throws MessagingException {
        return authenticationService.forgotPassword(emailRequest);
    }
}
