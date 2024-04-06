package com.exam.service.impl;

import com.exam.dto.request.StatisticRequest;
import com.exam.dto.response.QuizResponse;
import com.exam.dto.response.StatisticResponse;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.UserQuizResultRepository;
import com.exam.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final UserQuizResultRepository userQuizResultRepository;
    @Override
    public ResponseEntity<?> statistics(StatisticRequest statisticRequest) {
        List<UserQuizResult> userQuizResults = userQuizResultRepository.getUserQuizResultsByQuizId(statisticRequest.getQuizId());

        List<StatisticResponse> statisticResponses = new ArrayList<>();

        // sort r setRank
        userQuizResults.sort(Comparator.comparing(UserQuizResult::getMarks).reversed());
        int currentRank = 1;
        int i = 0;
        for(UserQuizResult userQuizResult : userQuizResults){
            Quiz quiz = userQuizResult.getQuiz();

            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setId(statisticRequest.getQuizId());
            quizResponse.setTitle(quiz.getTitle());
            quizResponse.setDescription(quiz.getDescription());
            quizResponse.setMaxMarks(quiz.getMaxMarks());
            quizResponse.setNumberOfQuestions(quiz.getNumberOfQuestions());
            quizResponse.setDurationMinutes(quiz.getDurationMinutes());

            User user = userQuizResult.getUser();

            StatisticResponse statisticResponse = new StatisticResponse();
            statisticResponse.setFullName(user.getFullName());
            statisticResponse.setEmail(user.getEmail());
            statisticResponse.setMarks(userQuizResult.getMarks());

            if (i > 0 && userQuizResult.getMarks() != userQuizResults.get(i - 1).getMarks()) {
                currentRank++;
            }
            i++;
            statisticResponse.setRank(currentRank);

            statisticResponse.setDuration(userQuizResult.getDurationTime());
            statisticResponse.setStartTime(userQuizResult.getStartTime());
            statisticResponse.setSubmitTime(userQuizResult.getSubmitTime());
            statisticResponse.setNumberOfCorrect(userQuizResult.getNumberOfCorrect());
            statisticResponse.setNumberOfIncorrect(userQuizResult.getNumberOfIncorrect());
            statisticResponse.setQuizResponse(quizResponse);

            statisticResponses.add(statisticResponse);
        }


        return ResponseEntity.ok(statisticResponses);
    }
}
