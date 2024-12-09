package com.exam.controller;

import com.exam.dto.request.email.ChangePasswordRequest;
import com.exam.dto.request.email.OTPRequest;
import com.exam.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/verifyMail/")
    public ResponseEntity<String> verifyMail(@RequestParam(value = "email") String email) {
        System.out.println("email: " + email);
        return forgotPasswordService.verifyEmail(email);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPRequest otpRequest) {
        return forgotPasswordService.verifyOTP(otpRequest);
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable("email") String email, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return forgotPasswordService.changePassword(email, changePasswordRequest);
    }
}



