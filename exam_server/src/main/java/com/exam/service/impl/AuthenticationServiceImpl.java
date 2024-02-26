package com.exam.service.impl;

import com.exam.config.JwtUtils;
import com.exam.config.UserDetailsServiceImpl;
import com.exam.dto.request.LoginRequest;
import com.exam.dto.request.SignupRequest;
import com.exam.dto.response.JwtResponse;
import com.exam.model.user.Role;
import com.exam.model.user.User;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.AuthenticationService;
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
    @Override
    public ResponseEntity<?> register(SignupRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        else if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        Role role = new Role();
        role = roleRepository.findByRoleName("student");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        String token;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            // Kiểm tra xem quá trình xác thực có thành công không
            if (auth != null && auth.isAuthenticated()) {
                // Sinh token và trả về cho người dùng
                token = jwtUtils.generateToken(userDetailsService.getUserDetail().getUsername());
                return new JwtResponse(token);
            } else {
                // Trả về một thông báo lỗi hoặc đối tượng ResponseEntity tương ứng
                return null;
            }
        } catch (AuthenticationException e) {
            // Xử lý trường hợp không thể xác thực người dùng
            return null;
        }
    }

}
