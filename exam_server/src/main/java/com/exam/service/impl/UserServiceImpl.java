package com.exam.service.impl;

import com.exam.config.JwtUtils;
import com.exam.config.UserDetailsServiceImpl;
import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userRepository.getAllUser());
    }

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        User user = userRepository.findById(id).get();
        UserResponse userResponse = new UserResponse();
        try{
            if(user != null){
                userResponse.setName(user.getName());
                userResponse.setUsername(user.getUsername());
                userResponse.setPassword(user.getPassword());
                userResponse.setEmail(user.getEmail());
                userResponse.setRole(user.getRole());

                return ResponseEntity.ok(userResponse);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).get();
        if(user != null){
            userRepository.deleteById(id);
        }
    }

    @Override
    public ResponseEntity<String> updateUser(User userRequest) {
        String username = userDetailsService.getUserDetail().getUsername();
        User user = userRepository.findByUsername(username);
        if(user != null){
            user.setName(userRequest.getName());
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setEmail(userRequest.getEmail());

            userRepository.save(user);
            return ResponseEntity.ok("Update User Successful");
        }
        return ResponseEntity.badRequest().body("User Not Exists");
    }

    @Override
    public ResponseEntity<?> getUsersOfQuiz(Long id) {
        return null;
    }
}
