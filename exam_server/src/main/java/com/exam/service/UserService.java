package com.exam.service;

import com.exam.dto.request.UserRequest;
import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserService {
    public ResponseEntity<UserResponse> getProfile();
    public ResponseEntity<?> addNewUser(UserRequest userRequest);
//    public ResponseEntity<?> getAllUser();
//    public ResponseEntity<UserResponse> getUser(Long id);
//    public void deleteUser(Long id);
//    public ResponseEntity<String> updateUser(User user);
//    public ResponseEntity<?> getUsersOfQuiz(@PathVariable("quizId") Long id);
}
