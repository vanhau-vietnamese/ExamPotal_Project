package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
//import com.exam.config.UserDetailsServiceImpl;
import com.exam.dto.request.UserRequest;
import com.exam.dto.response.UserResponse;
import com.exam.model.ERole;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
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
    public ResponseEntity<UserResponse> getProfile() {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        String firebaseId = decodedToken.getUid();

        User user = userRepository.findByEmailAndFirebaseId(email, firebaseId);

        UserResponse userResponse = new UserResponse();
        // nếu tồn tại user chứa firebaseId and email
        if(user != null){
            userResponse.setId(user.getId());
            userResponse.setFullName(user.getFullName());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());

            Set<UserQuizResult> listResultOfUser = userRepository.getQuizResultOfUser(user.getId());
            userResponse.setNumberOfCompleted(listResultOfUser.size());
            userResponse.setUserQuizResults(listResultOfUser);

            if(user.getRole().equals(ERole.student)){
                return ResponseEntity.ok(userResponse);
            }
            Set<Quiz> quizzes = quizRepository.findAllByCreateBy(user);
            userResponse.setNumberOfPost(quizzes.size());
            userResponse.setPostsOfTeacher(quizzes);

            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @Override
    public User addNewUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirebaseId(userRequest.getFirebaseId());
        user.setFullName(userRequest.getFullName());
        user.setRole(ERole.student);
        return userRepository.save(user);
    }
}
