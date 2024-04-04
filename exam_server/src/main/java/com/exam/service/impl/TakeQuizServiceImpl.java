package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.helper.AnswerObject;
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
import java.util.ArrayList;
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
    private final QuestionRepository questionRepository;
    private final UserQuestionResultRepository userQuestionResultRepository;
    private final QuizQuestionRepository quizQuestionRepository;

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

        Quiz quiz = quizRepository.findById(submitRequest.getQuizId()).orElse(null);

        List<QuestionChoiceRequest> answers = submitRequest.getAnswers();
        System.out.println("AAAAAAAAAAA");
        int totalAnswer = answers.size();
        int totalScore = 0;
        int numberOfCorrect = 0;
        int numberOfIncorrect;
        float ratio;

        UserQuizResult userQuizResult = userQuizResultRepository.findByUserAndQuiz(user, quiz);
        System.out.println("AAAAAAAAAAA1");
        for (QuestionChoiceRequest answer : answers) {
            List<Long> selectedOptions = answer.getSelectedOptions();
            // get Question from questionID
            Question  question = questionRepository.findById(answer.getQuestionId()).orElse(null);
            System.out.println("AAAAAAAAAAA2");
            QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestion(quiz, question);

            // get AnswerList of Question
            List<Answer> answerList = answerRepository.getAnswerFromQuestion(answer.getQuestionId());
            // Get id of the correct answer
            List<Long> correctAnswerIds = answerRepository.getCorrectAnswerFromQuestion(answer.getQuestionId());

            // create userQuestionResult to save to db
            UserQuestionResult userQuestionResult = new UserQuestionResult();
            boolean isQuestionAnswered = !selectedOptions.isEmpty();
            boolean isCorrect = isQuestionAnswered && validateScore(selectedOptions, correctAnswerIds);

            userQuestionResult.setDone(isQuestionAnswered);
            userQuestionResult.setResult(isCorrect);

            if (isCorrect) {
                totalScore += quizQuestion.getMarksOfQuestion();
                numberOfCorrect++;
            }

            AnswersToChoose answersToChoose = getAnswersToChoose(answerList, selectedOptions);

            userQuestionResult.setQuestionContent(question.getContent());
            userQuestionResult.setAnswersToChoose(answersToChoose);
            userQuestionResult.setMarkOfQuestion(quizQuestion.getMarksOfQuestion());
            userQuestionResult.setUserQuizResult(userQuizResult);
            // save userQuestionResult
            userQuestionResultRepository.save(userQuestionResult);
        }
        // lưu kết quả bài exam
        saveUserQuizResult(totalScore, userQuizResult);

        numberOfIncorrect = totalAnswer - numberOfCorrect;
        ratio = (float)numberOfCorrect/totalAnswer;
        String formattedCapture = String.format("%.2f%%", ratio * 100);

        SubmitResponse submitResponse = new SubmitResponse();
        submitResponse.setMarks(totalScore);
        submitResponse.setQuizId(submitRequest.getQuizId());
        submitResponse.setNumberOfCorrect(numberOfCorrect);
        submitResponse.setUserId(user.getId());
        submitResponse.setNumberOfIncorrect(numberOfIncorrect);
        submitResponse.setRatio(formattedCapture);
        return ResponseEntity.ok(submitResponse);

//        return null;
    }

    private static AnswersToChoose getAnswersToChoose(List<Answer> answerList, List<Long> selectedOptions) {
        List<AnswerObject> answerObjectList = new ArrayList<>();

        // lặp qua mảng các answer của question để set các value cho answerObject
        // đồng thời cũng add nó vào trong answerObjectList
        for(Answer answer1 : answerList){
            AnswerObject answerObject = new AnswerObject();
            Long answerId = answer1.getId();
            answerObject.setId(answer1.getId());
            answerObject.setMedia(answer1.getMedia());
            answerObject.setContent(answer1.getContent());
            // setSlect theo: nếu answerId tồn tại trong selectedOptions
            answerObject.setSelect(selectedOptions.contains(answerId));
            answerObject.setCorrectAnswer(answer1.isCorrect());
            // add vao answerObjectList
            answerObjectList.add(answerObject);
        }
        AnswersToChoose answersToChoose = new AnswersToChoose();
        answersToChoose.setAnswers(answerObjectList);
        return answersToChoose;
    }

    private void saveUserQuizResult(int totalScore, UserQuizResult userQuizResult){
        if (userQuizResult != null) {
            userQuizResult.setMarks(totalScore);
            userQuizResult.setSubmitTime(new Timestamp(System.currentTimeMillis()));

            String durationTime = userQuizResult.calculateDuration(userQuizResult.getStartTime(), userQuizResult.getSubmitTime());
            userQuizResult.setDurationTime(durationTime);
            userQuizResultRepository.save(userQuizResult);
        }
    }

    private boolean validateScore(List<Long> selectedOptions, List<Long> correctAnswerIds) {
        Set<Long> setSelectOptions = new HashSet<>(selectedOptions);
        Set<Long> setCorrectAnswers = new HashSet<>(correctAnswerIds);
        return setCorrectAnswers.equals(setSelectOptions);
    }
}
