package com.exam.controller;

import com.exam.dto.response.UserResponse;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable("id") Long id){
        return userService.getProfile(id);
    }
}
