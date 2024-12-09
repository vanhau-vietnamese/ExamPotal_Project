package com.exam.service;

import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.ForgotPasswordRequest;
import com.exam.dto.request.UserRequest;
import com.exam.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getProfile();
    User addNewUser(UserRequest userRequest);

    ResponseEntity<?> changePassword(ChangePasswordRequest request);
}
