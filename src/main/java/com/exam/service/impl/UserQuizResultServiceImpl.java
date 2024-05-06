package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.response.UserQuizResultResponse;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.UserQuizResultRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserQuizResultService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserQuizResultServiceImpl implements UserQuizResultService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserQuizResultRepository userQuizResultRepository;
    @Override
    public ResponseEntity<?> getHistoryOfUser() {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        System.out.println("asjdajda");
        User user = userRepository.findByEmail(email);

        System.out.println("123313");
        List<UserQuizResult> userQuizResultList = userQuizResultRepository.getHistoryOfUser(user.getId());
        System.out.println("abcd");
        List<UserQuizResultResponse> userQuizResultResponseList = new ArrayList<>();

        for(UserQuizResult userQuizResult : userQuizResultList){
            UserQuizResultResponse userQuizResultResponse = new UserQuizResultResponse();
            userQuizResultResponse.setMarks(userQuizResult.getMarks());
            userQuizResultResponse.setStartTime(userQuizResult.getStartTime());
            userQuizResultResponse.setSubmitTime(userQuizResult.getSubmitTime());
            userQuizResultResponse.setDurationTime(userQuizResultResponse.getDurationTime());
            userQuizResultResponse.setNumberOfCorrect(userQuizResultResponse.getNumberOfCorrect());
            userQuizResultResponse.setNumberOfIncorrect(userQuizResultResponse.getNumberOfIncorrect());

            Map<String, Object> quiz = new HashMap<>();
            quiz.put("tittle", userQuizResult.getExam().getTitle());
            quiz.put("maxMarks", userQuizResult.getExam().getMaxMarks());
            quiz.put("numberOfQuestion", userQuizResult.getExam().getNumberOfQuestions());
            quiz.put("durationMinutes", userQuizResult.getExam().getDurationMinutes());
            userQuizResultResponse.setQuiz(quiz);

            userQuizResultResponseList.add(userQuizResultResponse);
        }
        return ResponseEntity.ok(userQuizResultResponseList);
    }
}
