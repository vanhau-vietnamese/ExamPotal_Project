package com.exam.controller;

import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/all-user")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(){
        return userService.getAllUser();
//        return nul1l;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long id){
        return userService.getUser(id);
    }
    @DeleteMapping("delete/{userId}")
    public void deleteUser(@PathVariable("userId")Long id){
        userService.deleteUser(id);
    }
    // cập nhập thông tin cá nhân -> tất cả user đều có thể update info của cá nhân
    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @GetMapping("/getQuiz/{quizId}")
    public ResponseEntity<?> getUsersOfQuiz(@PathVariable("quizId") Long id){
        return null;
    }
}
