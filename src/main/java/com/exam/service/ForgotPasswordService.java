package com.exam.service;

import com.exam.dto.request.email.ChangePasswordRequest;
import com.exam.dto.request.email.OTPRequest;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordService {
    ResponseEntity<String> verifyEmail(String email);

    ResponseEntity<String> verifyOTP(OTPRequest otpRequest);

    ResponseEntity<String> changePassword(String email, ChangePasswordRequest changePasswordRequest);
}
