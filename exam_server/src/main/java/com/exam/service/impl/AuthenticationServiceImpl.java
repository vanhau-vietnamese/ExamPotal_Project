package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.RegisterRequest;
import com.exam.model.ERole;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import com.exam.validate.ValidateUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.internal.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ValidateUser validateUser;
    @Override
    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {
        try{
            // Kiểm tra xem nó có tồn tại trong firebase auth k?
            if (isUserExists(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
            }

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(registerRequest.getEmail())
                    .setPassword(registerRequest.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            String firebaseId = userRecord.getUid(); // Trả về UID của người dùng mới được tạo

            // lưu vào db
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setFirebaseId(firebaseId);
            if(!validateUser.validateFullName(registerRequest.getFullName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên đăng nhập phải nhiều hơn 3 kí tự");
            }
            user.setFullName(registerRequest.getFullName());

            if(!validateUser.validatePasswordLength(registerRequest.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu phải ít nhất 8 kí tự");

            }
            if(!validateUser.validatePasswordComplexity(registerRequest.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu phải có ít nhất 1 ký tự viết hoa và 1 ký tự đặc biệt!");
            }
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(ERole.student);
            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }
    // Kiểm tra xem người dùng đã tồn tại trong Firebase Authentication hay không
    private boolean isUserExists(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true; // Người dùng đã tồn tại
        } catch (FirebaseAuthException e) {

            return false;
        }
    }
}
