package com.exam.controller;

import com.exam.dto.request.UserRequest;
import com.exam.dto.response.UserResponse;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(){
        return userService.getProfile();
    }
    @PostMapping("/add")
    public ResponseEntity<?> addNewUser(@RequestBody UserRequest userRequest){
        return userService.addNewUser(userRequest);
    }
}
