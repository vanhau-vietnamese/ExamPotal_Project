package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
//import com.exam.config.UserDetailsServiceImpl;
import com.exam.dto.request.ChangePasswordRequest;
import com.exam.dto.request.UserRequest;
import com.exam.dto.response.UserInfoResponse;
import com.exam.dto.response.UserResponse;
import com.exam.enums.ERole;
import com.exam.enums.EStatus;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class  UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> getProfile() {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        String firebaseId = decodedToken.getUid();

        User user = userRepository.findByEmailAndFirebaseId(email, firebaseId);

        // nếu tồn tại user chứa firebaseId and email
        if(user != null){
            String createdBy = user.getCreatedBy() != null ? user.getCreatedBy().getFullName() : null;
            UserInfoResponse userInfoResponse = new UserInfoResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.getFirebaseId(), user.getCreatedAt(), createdBy);

            return ResponseEntity.ok(userInfoResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found User");
    }

    @Override
    public User addNewUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirebaseId(userRequest.getFirebaseId());
        user.setFullName(userRequest.getFullName());
        user.setRole(ERole.student);
        user.setStatus(EStatus.Active);
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        String firebaseId = decodedToken.getUid();

        User user = userRepository.findByEmailAndFirebaseId(email, firebaseId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với email: " + email);
        }
        System.out.println("email: "+ email);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu hiện tại không đúng");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        updatePassword(firebaseId, request.getNewPassword());


        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    public void updatePassword(String uid, String newPassword) {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setPassword(newPassword);
        try {
            FirebaseAuth.getInstance().updateUser(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }
}
