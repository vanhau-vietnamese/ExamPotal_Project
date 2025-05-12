package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.RegisterRequest;
import com.exam.dto.response.UserResponse;
import com.exam.enums.ERole;
import com.exam.enums.EStatus;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AdminManagerService;
import com.exam.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminManagerServiceImpl implements AdminManagerService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> createAdminAccount(RegisterRequest request) {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User createBy = userRepository.findByEmail(email);

        if(createBy.getRole().equals(ERole.admin)){
            if(CommonUtils.isUserExists(request.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
            }
            UserRecord.CreateRequest requestRecord = new UserRecord.CreateRequest()
                    .setEmail(request.getEmail())
                    .setPassword(request.getPassword());

            UserRecord userRecord = null;
            try {
                userRecord = FirebaseAuth.getInstance().createUser(requestRecord);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
            String firebaseId = userRecord.getUid(); // Trả về UID của người dùng mới được tạo

            // lưu vào db
            User user = new User();
            user.setEmail(request.getEmail());
            user.setFirebaseId(firebaseId);
            user.setFullName(request.getFullName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(ERole.admin);
            user.setCreatedBy(createBy);
            user.setStatus(EStatus.Active);
            userRepository.save(user);

            System.out.println("userId:" + user.getId());

            return ResponseEntity.ok(userRepository.getUserById(user.getId()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không phải là admin, nên bạn không có quyền tạo tài khoản admin");
    }

    @Override
    public ResponseEntity<?> getAllAdminAccount() {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User userGet = userRepository.findByEmail(email);

        if(userGet.getRole().equals(ERole.admin)){
            List<User> adminUsers = userRepository.getAllAdminUsers(userGet.getId());
            List<UserResponse> userInfoResponses = new ArrayList<>();

            for (User user : adminUsers) {
                String createdBy = user.getCreatedBy() != null ? user.getCreatedBy().getFullName() : null;
                UserResponse userInfoResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.getFirebaseId(), user.getCreatedAt(), createdBy);
                userInfoResponses.add(userInfoResponse);
            }
            return ResponseEntity.ok(userInfoResponses);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không phải là admin, nên bạn không có quyền xem thông tin của tất cả tài khoản admin");
    }

    @Override
    public ResponseEntity<?> deleteAdminAccount(Map<String, String> request) {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        if(user.getRole().equals(ERole.admin)){
            System.out.println("userId123: " + request.get("userId"));

            User userDeleted = userRepository.findById(request.get("userId")).get();
            System.out.println("usseID: " +userDeleted.getId());
            if(userDeleted.getRole().equals(ERole.student)){
                return ResponseEntity.badRequest().body("Đây là tài khoản của học sinh, nên bạn không được xóa");
            }
            userDeleted.setStatus(EStatus.Deleted);
            userRepository.save(userDeleted);
            return ResponseEntity.ok("Bạn đã xóa thành công");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không phải là admin, nên bạn không có quyền xóa tài khoản admin");
    }

    @Override
    public ResponseEntity<?> getAllUserAccount() {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User userGet = userRepository.findByEmail(email);

        if(userGet.getRole().equals(ERole.admin)){
            List<User> studentUsers = userRepository.getAllStudent();
            List<UserResponse> userInfoResponses = new ArrayList<>();

            for (User user : studentUsers) {
                String createdBy = user.getCreatedBy() != null ? user.getCreatedBy().getFullName() : null;
                UserResponse userInfoResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.getFirebaseId(), user.getCreatedAt(), createdBy);
                userInfoResponses.add(userInfoResponse);
            }
            return ResponseEntity.ok(userInfoResponses);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không phải là admin, nên bạn không có quyền xem thông tin của tất cả tài khoản người dùng");
    }

}
