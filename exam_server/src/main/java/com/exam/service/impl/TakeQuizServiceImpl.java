package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.QuestionChoiceRequest;
import com.exam.dto.request.SubmitRequest;
import com.exam.dto.response.SubmitResponse;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.AnswerRepository;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserQuizResultRepository;
import com.exam.repository.UserRepository;
import com.exam.service.TakeQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TakeQuizServiceImpl implements TakeQuizService {
    private final AnswerRepository answerRepository;
    private final QuizRepository quizRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserQuizResultRepository userQuizResultRepository;


    public void startQuiz(Long quizId){
        // Lấy thời gian nhấn bắt đầu là tời gian hiện tai
        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        Quiz quiz = quizRepository.findById(quizId).get();

        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        String email = jwtUtils.extractUserName(jwt);
        User user = userRepository.findByEmail(email);

        UserQuizResult userQuizResult = new UserQuizResult();
        userQuizResult.setStartTime(startTime);
        userQuizResult.setQuiz(quiz);
        userQuizResult.setUser(user);

        userQuizResultRepository.save(userQuizResult);
    }
    @Override
    public ResponseEntity<?> submitQuiz(SubmitRequest submitRequest) {
        List<QuestionChoiceRequest> answers = submitRequest.getAnswers();
        int totalAnswer = answers.size();
        int totalScore = 0;
        int numberOfCorrect = 0;
        int numberOfIncorrect;
        float capture;
        for (QuestionChoiceRequest answer : answers) {
            List<Long> selectedOptions = answer.getSelectedOptions();
            List<Long> correctAnswerIds = answerRepository.getCorrectAnswerFromQuestion(submitRequest.getQuizId());
            if(selectedOptions.isEmpty()){
                // đáp án k đc đê trống
                return ResponseEntity.badRequest().body("Đáp án k đc null");
            }
            else{
                if(validateScore(selectedOptions, correctAnswerIds)){
                    totalScore++;
                    numberOfCorrect++;
                }

            }
        }
        numberOfIncorrect = totalAnswer - numberOfCorrect;

        capture = (float)numberOfCorrect/totalAnswer;
        String formattedCapture = String.format("%.2f%%", capture);

        String jwt = jwtAuthenticationFilter.getJwt();
        String email = jwtUtils.extractUserName(jwt);
        User user = userRepository.findByEmail(email);

        Quiz quiz = quizRepository.findById(submitRequest.getQuizId()).get();

        saveUserQuizResult(totalScore, formattedCapture, user, quiz);

        SubmitResponse submitResponse = new SubmitResponse();
        submitResponse.setMarks(totalScore);
        submitResponse.setQuizId(submitRequest.getQuizId());
        submitResponse.setNumberOfCorrect(numberOfCorrect);
        submitResponse.setUserId(user.getId());
        submitResponse.setNumberOfIncorrect(numberOfIncorrect);
        submitResponse.setCapture(formattedCapture);
        return ResponseEntity.ok(submitResponse);

    }

    private void saveUserQuizResult(int totalScore, String formattedCapture, User user, Quiz quiz){
        UserQuizResult userQuizResult = userQuizResultRepository.findByUserAndQuiz(user, quiz);
        userQuizResult.setMarks(totalScore);
        userQuizResult.setCapture(formattedCapture);
        userQuizResult.setSubmitTime(new Timestamp(System.currentTimeMillis()));

        String durationTime = userQuizResult.calculateDuration(userQuizResult.getStartTime(), userQuizResult.getSubmitTime());
        userQuizResult.setDurationTime(durationTime);
        userQuizResultRepository.save(userQuizResult);
    }

    private boolean validateScore(List<Long> selectedOptions, List<Long> correctAnswerIds) {
        Set<Long> setSelectOptions = listToSet(selectedOptions);
        Set<Long> setCorrectAnswers = listToSet(correctAnswerIds);
        return setCorrectAnswers.equals(setSelectOptions);
    }
    public static Set<Long> listToSet(List<Long> list) {
        Set<Long> set = new HashSet<>();
        for (Long num : list) {
            set.add(num);
        }
        return set;
    }
}
