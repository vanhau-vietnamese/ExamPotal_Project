package com.exam.service;

import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.UserRequest;
import com.exam.dto.response.UserResponse;
import com.exam.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserService {
    public ResponseEntity<UserResponse> getProfile();
    public User addNewUser(UserRequest userRequest);

    ResponseEntity<?> changePassword(ChangePasswordRequest request);
}
