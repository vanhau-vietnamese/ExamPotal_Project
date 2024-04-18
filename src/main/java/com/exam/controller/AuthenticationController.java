package com.exam.controller;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.RegisterRequest;
import com.exam.enums.ERole;
import com.exam.model.User;
import com.exam.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        return authenticationService.registerUser(registerRequest);
    }


    private final JwtUtils jwtUtils;
    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, String> request) {
        System.out.println("request: "+request);
        // Tạo một người dùng giả lập
        User user = new User();
        user.setEmail(request.get("email"));
        user.setFullName(request.get("fullName"));
        user.setRole(ERole.student);
        user.setFirebaseId(request.get("uid"));
        // Tạo token cho người dùng giả lập
        String token = jwtUtils.generateToken(user.getEmail(), user.getFirebaseId());
        return ResponseEntity.ok("token: " + token);
    }
}
