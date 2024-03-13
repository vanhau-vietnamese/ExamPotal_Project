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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<UserResponse> getProfile() {
        String jwt = jwtAuthenticationFilter.getJwt();
        System.out.println("jwt: "+jwt);
        String email = jwtUtils.extractUserName(jwt);
        System.out.println("email: "+email);
        String firebaseId = jwtUtils.extractFirebaseId(jwt);
        System.out.println("firebase: "+firebaseId);

        User user = userRepository.findByEmailAndFirebaseId(email, firebaseId);
        System.out.println("user:" + user);

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
    public ResponseEntity<?> addNewUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirebaseId(userRequest.getFirebaseId());
        user.setFullName(userRequest.getFullName());
        user.setRole(ERole.student);
        return ResponseEntity.ok(userRepository.save(user));
    }
//    private final UserRepository userRepository;
//    private final JwtUtils jwtUtils;
//    private final UserDetailsServiceImpl userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public ResponseEntity<?> getAllUser() {
//        return ResponseEntity.ok(userRepository.getAllUser());
//    }
//
//    @Override
//    public ResponseEntity<UserResponse> getUser(Long id) {
//        User user = userRepository.findById(id).get();
//        UserResponse userResponse = new UserResponse();
//        try{
//            if(user != null){
//                userResponse.setName(user.getName());
//                userResponse.setUsername(user.getUsername());
//                userResponse.setPassword(user.getPassword());
//                userResponse.setEmail(user.getEmail());
//                userResponse.setRole(user.getRole());
//
//                return ResponseEntity.ok(userResponse);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        User user = userRepository.findById(id).get();
//        if(user != null){
//            userRepository.deleteById(id);
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> updateUser(User userRequest) {
//        String username = userDetailsService.getUserDetail().getUsername();
//        User user = userRepository.findByUsername(username);
//        if(user != null){
//            user.setName(userRequest.getName());
//            user.setUsername(userRequest.getUsername());
//            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//            user.setEmail(userRequest.getEmail());
//
//            userRepository.save(user);
//            return ResponseEntity.ok("Update User Successful");
//        }
//        return ResponseEntity.badRequest().body("User Not Exists");
//    }
//
//    @Override
//    public ResponseEntity<?> getUsersOfQuiz(Long id) {
//        return null;
//    }
}
