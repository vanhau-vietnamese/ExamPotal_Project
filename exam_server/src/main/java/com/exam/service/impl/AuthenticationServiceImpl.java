package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.RegisterRequest;
import com.exam.model.ERole;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.internal.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {
        try{
            String jwt = jwtAuthenticationFilter.getJwt();

            if(userRepository.existsByEmail(registerRequest.getEmail())){
                return ResponseEntity.badRequest().body("Email already exists");
            }

            String firebaseId = jwtUtils.extractFirebaseId(jwt);
            // lưu vào db
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setFullName(registerRequest.getFullName());
            user.setFirebaseId(firebaseId);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(ERole.ROLE_STUDENT);
            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }

}
