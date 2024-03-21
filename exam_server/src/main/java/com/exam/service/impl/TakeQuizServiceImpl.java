package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.QuestionChoiceRequest;
import com.exam.dto.request.StartQuizRequest;
import com.exam.dto.request.SubmitRequest;
import com.exam.dto.response.SubmitResponse;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.TakeQuizService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionRepository questionRepository;
    private final UserQuestionChoiceRepository userQuestionChoiceRepository;

    public ResponseEntity<?> startQuiz(StartQuizRequest startQuizRequest){
        // Lấy thời gian nhấn bắt đầu là tời gian hiện tai
        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        Quiz quiz = quizRepository.findById(startQuizRequest.getQuizId()).get();
        if(quiz == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quiz Not Exists");
        }

        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        UserQuizResult userQuizResult = new UserQuizResult();
        userQuizResult.setStartTime(startTime);
        userQuizResult.setQuiz(quiz);
        userQuizResult.setUser(user);

        userQuizResultRepository.save(userQuizResult);
        return ResponseEntity.status(HttpStatus.OK).body("Start Successfully. You can start taking the test");
    }
    @Override
    public ResponseEntity<?> submitQuiz(SubmitRequest submitRequest) {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        Quiz quiz = quizRepository.findById(submitRequest.getQuizId()).get();

        List<QuestionChoiceRequest> answers = submitRequest.getAnswers();
        int totalAnswer = answers.size();
        int totalScore = 0;
        int numberOfCorrect = 0;
        int numberOfIncorrect;
        float capture;
        for (QuestionChoiceRequest answer : answers) {
            List<Long> selectedOptions = answer.getSelectedOptions();
            System.out.println("abc: "+answer.getQuestionId());
            System.out.println("123: "+answerRepository.getCorrectAnswerFromQuestion(answer.getQuestionId()));
            List<Long> correctAnswerIds = answerRepository.getCorrectAnswerFromQuestion(answer.getQuestionId());
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
            Question question = questionRepository.findById(answer.getQuestionId()).get();

            // lưu đáp án của student vào db
            QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestion(quiz, question);

            UserQuestionChoice userQuestionChoice = new UserQuestionChoice();
            userQuestionChoice.setQuizQuestion(quizQuestion);
            userQuestionChoice.setSelectedOptions(answer.getSelectedOptions());
            userQuestionChoice.setUser(user);
            userQuestionChoiceRepository.save(userQuestionChoice);
        }
        numberOfIncorrect = totalAnswer - numberOfCorrect;

        capture = (float)numberOfCorrect/totalAnswer;
        String formattedCapture = String.format("%.2f%%", capture * 100);

        // lưu kết quả bài exam
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
        System.out.println("absdbasd: "+setCorrectAnswers.equals(setSelectOptions));
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
