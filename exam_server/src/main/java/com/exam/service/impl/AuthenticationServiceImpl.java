package com.exam.service.impl;

import com.exam.config.JwtUtils;
import com.exam.config.UserDetailsServiceImpl;
import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.ForgotPasswordRequest;
import com.exam.dto.request.LoginRequest;
import com.exam.dto.request.SignupRequest;
import com.exam.dto.response.JwtResponse;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import com.exam.utils.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final EmailUtils emailUtils;

    @Override
    public ResponseEntity<?> register(SignupRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        else if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        Role role = new Role();
        role = roleRepository.findByRoleName("ROLE_STUDENT");

        User user = new User();
        user.setName(request.getUsername());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        System.out.println("23513");
        String token;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            if (auth != null && auth.isAuthenticated()) {
                User userDetail = userDetailsService.getUserDetail();
                token = jwtUtils.generateToken(userDetail.getUsername());
                System.out.println("ABCD");
                return new JwtResponse(token);
            } else{
                return null;
            }
        } catch (AuthenticationException e) {
            // Xử lý trường hợp không thể xác thực người dùng
            return null;
        }
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user != null){
            if(passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
                // lưu xuống ật khẩu mã hóa

                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("Password Updated Successfully");
            }
            return ResponseEntity.badRequest().body("Incorrect Old Password");
        }
        return ResponseEntity.badRequest().body("Incorrect username");
    }

    @Override
    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest emailRequest) throws MessagingException {
        User user = userRepository.findByEmail(emailRequest.getEmail());
        System.out.println("emailRequest: "+ emailRequest.getEmail());
        System.out.println("email: "+ user.getEmail());
        if(user != null){
            emailUtils.sendPasswordToEmail(emailRequest.getEmail(), "Credentials By Online Exam", user.getPassword());
            return ResponseEntity.ok("Please check your email to get password");
        }
        return ResponseEntity.badRequest().body("Incorrect Email");
    }


}
