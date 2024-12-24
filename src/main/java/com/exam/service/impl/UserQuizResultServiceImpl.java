package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.response.UserQuizResultResponse;
import com.exam.enums.EStatus;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.UserQuizResultRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserQuizResultService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
        User user = userRepository.findByEmail(email);

        List<UserQuizResult> userQuizResultList = userQuizResultRepository.getHistoryOfUser(user.getId());
        return getUserQuizResultResponseList(userQuizResultList);
    }

    @Override
    public ResponseEntity<?> searchUserQuizResult(Map<String, String> searchRequest) {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);
        List<UserQuizResult> userQuizResultList = userQuizResultRepository.searchUserQuizResult(searchRequest.get("searchContent"), user.getId());
        return getUserQuizResultResponseList(userQuizResultList);
    }

    @Override
    public ResponseEntity<?> deleteUserQuizResult(Long id) {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        UserQuizResult userQuizResult = userQuizResultRepository.getUserQuizResultByUserAndId(user, id);
        userQuizResult.setStatus(EStatus.Deleted);
        userQuizResultRepository.save(userQuizResult);
        return ResponseEntity.ok("Deleted successful");
    }

    @NotNull
    private ResponseEntity<?> getUserQuizResultResponseList(List<UserQuizResult> userQuizResultList) {
        List<UserQuizResultResponse> userQuizResultResponseList = new ArrayList<>();

        for(UserQuizResult userQuizResult : userQuizResultList){
            UserQuizResultResponse userQuizResultResponse = new UserQuizResultResponse();
            userQuizResultResponse.setId(userQuizResult.getId());
            userQuizResultResponse.setMarks(userQuizResult.getMarks());
            userQuizResultResponse.setStartTime(userQuizResult.getStartTime());
            userQuizResultResponse.setSubmitTime(userQuizResult.getSubmitTime());
            userQuizResultResponse.setDurationTime(userQuizResult.getDurationTime());
            userQuizResultResponse.setNumberOfCorrect(userQuizResult.getNumberOfCorrect());
            userQuizResultResponse.setNumberOfIncorrect(userQuizResult.getNumberOfIncorrect());

            Map<String, Object> quiz = new HashMap<>();
            quiz.put("tittle", userQuizResult.getExam().getTitle());
            quiz.put("maxMarks", userQuizResult.getExam().getMaxMarks());
            quiz.put("numberOfQuestion", userQuizResult.getExam().getNumberOfQuestions());
            quiz.put("durationMinutes", userQuizResult.getExam().getDurationMinutes());
            quiz.put("id", userQuizResult.getExam().getQuizId());

            userQuizResultResponse.setQuiz(quiz);

            userQuizResultResponseList.add(userQuizResultResponse);
        }
        return ResponseEntity.ok(userQuizResultResponseList);
    }


}
