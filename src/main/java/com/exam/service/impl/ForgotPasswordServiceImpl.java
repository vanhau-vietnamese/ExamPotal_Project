package com.exam.service.impl;

import com.exam.dto.request.email.*;
import com.exam.model.ForgotPassword;
import com.exam.model.User;
import com.exam.repository.ForgotPasswordRepository;
import com.exam.repository.UserRepository;
import com.exam.repository.client.EmailClient;
import com.exam.service.ForgotPasswordService;
import feign.FeignException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final EmailClient emailClient;

    private final PasswordEncoder passwordEncoder;

    @Value("${brevo.api.key}")
    private String apiKey;

    // TH:
    // khi người dùng

    @RateLimiter(name = "verifyEmailRatelimiter", fallbackMethod = "handleRateLimitExceeded")
    @Override
    public ResponseEntity<String> verifyEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please provide a valid email");
        }

        // xóa otp đã hết hạn của user
        forgotPasswordRepository.deleteByExpirationTimeBeforeAndUser(Timestamp.from(Instant.now()), user);

        int otp = generateOtp();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(Timestamp.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
                .user(user)
                .build();

        // sendMail
        String subject = "OTP for Forgot Password Request";
        String htmlContent = "<h1>Your OTP for Password Reset is: " + otp + "</h1>";
        Recipient to = Recipient.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
        boolean isSendMail = sendMail(to, subject, htmlContent);

        // ba không thể send lại email khi

        if (isSendMail){
            forgotPasswordRepository.save(forgotPassword);
            return ResponseEntity.ok("Email sent for verification");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Send Mail");
    }
    // Fallback nếu vượt quá giới hạn RateLimiter
    public ResponseEntity<String> handleRateLimitExceeded(Exception e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("You can only request email verification once every minute.");
    }

    // verify OTP
    public ResponseEntity<String> verifyOTP(OTPRequest otpRequest) {
        User user = userRepository.findByEmail(otpRequest.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please provide a valid email");
        }

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otpRequest.getOtp(), user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + otpRequest.getEmail()));

        // if het han hoặc delete khi chúng ta verify email otp thành cng
        forgotPasswordRepository.delete(fp);

        if(fp.getExpirationTime().before(Timestamp.from(Instant.now()))) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("OTP has expired!");
        }

        return ResponseEntity.ok("OTP verified!");
    }

    @Override
    public ResponseEntity<String> changePassword(String email, ChangePasswordRequest request) {
        if(!request.getPassword().equals(request.getRepeatPassword())){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Passwords do not match! Please enter the password again!");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.status(HttpStatus.OK).body("Password has been changed!");
    }

    // generate OTP
    private Integer generateOtp(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

    // send mail
    private boolean sendMail(Recipient to, String subject, String htmlContent){
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Hau Tran")
                        .email("tranhau.010120199@gmail.com")
                        .build())
                .to(List.of(to))
                .subject(subject)
                .htmlContent(htmlContent)
                .build();
        try{
            emailClient.sendEmail(apiKey, emailRequest);
            return true;
        }catch (FeignException e){
            return false;
        }
    }
}
