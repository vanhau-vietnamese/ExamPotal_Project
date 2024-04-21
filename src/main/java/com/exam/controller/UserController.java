package com.exam.controller;

import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.UserRequest;
import com.exam.dto.response.ApiResponse;
import com.exam.dto.response.UserInfoResponse;
import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<?> getProfile(){
        return userService.getProfile();
    }
    @PostMapping("/add")
    public ApiResponse<User> addNewUser(@RequestBody UserRequest  userRequest){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.addNewUser(userRequest));
        return apiResponse;
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        return userService.changePassword(request);
    }
}
