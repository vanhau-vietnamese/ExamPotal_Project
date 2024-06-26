package com.exam.service.impl;

import com.exam.dto.request.RegisterRequest;
import com.exam.enums.ERole;
import com.exam.enums.EStatus;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import com.exam.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        try{
            // Kiểm tra xem nó có tồn tại trong firebase auth k?
            if (CommonUtils.isUserExists(registerRequest.getEmail())) {
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
            user.setFullName(registerRequest.getFullName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(ERole.student);
            user.setStatus(EStatus.Active);
            userRepository.save(user);

            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }
    // Kiểm tra xem người dùng đã tồn tại trong Firebase Authentication hay khôn
}
