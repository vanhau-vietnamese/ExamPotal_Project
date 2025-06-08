package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.response.*;
import com.exam.enums.EStatus;
import com.exam.helper.AnswerObject;
import com.exam.dto.request.QuestionChoiceRequest;
import com.exam.dto.request.StartQuizRequest;
import com.exam.dto.request.SubmitRequest;
import com.exam.helper.ExamObject;
import com.exam.helper.QuestionObject;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.TakeQuizService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
        Quiz quiz = quizRepository.findById(startQuizRequest.getQuizId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Not Exists"));
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        // response bài làm lên cho học sinh làm
        List<Question> questionList = quizQuestionRepository.getQuestionsOfQuiz(startQuizRequest.getQuizId());
        List<QuestionResponse> questionResponseList = questionList.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());

        ExamObject examObject = getExamObject(quiz, questionResponseList);
        UserQuizResult userQuizResult = new UserQuizResult();
        userQuizResult.setStartTime(startTime);
        userQuizResult.setQuiz(quiz);
        userQuizResult.setUser(user);
        userQuizResult.setExam(examObject);
        userQuizResult.setSubmitted(false);
        userQuizResult.setStatus(EStatus.Active);
        userQuizResultRepository.save(userQuizResult);
        Collections.shuffle(questionResponseList);
        QuizResponse quizResponse = getQuizResponse(quiz, questionResponseList, userQuizResult);
        return ResponseEntity.status(HttpStatus.OK).body(quizResponse);
    }

    private QuestionResponse mapToQuestionResponse(Question question) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(question.getCategory().getId());
        categoryResponse.setTitle(question.getCategory().getTitle());
        categoryResponse.setDescription(question.getCategory().getDescription());

        QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
        questionTypeResponse.setAlias(question.getQuestionType().getAlias());
        questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

        Set<Answer> answerList = answerRepository.findAllByQuestion(question);
        Set<AnswerResponse> answerResponseSet = getAnswerResponses(answerList);

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setQuestionType(questionTypeResponse);
        questionResponse.setMedia(question.getMedia());
        questionResponse.setCreatedAt(question.getCreatedAt());
        questionResponse.setCategory(categoryResponse);
        questionResponse.setContent(question.getContent());
        questionResponse.setAnswers(answerResponseSet);

        return questionResponse;
    }

    @NotNull
    private static ExamObject getExamObject(Quiz quiz, List<QuestionResponse> questionResponseList) {
        ExamObject examObject = new ExamObject();
        examObject.setQuizId(quiz.getId());
        examObject.setTitle(quiz.getTitle());
        examObject.setDescription(quiz.getDescription());
        examObject.setMaxMarks(quiz.getMaxMarks());
        examObject.setDurationMinutes(quiz.getDurationMinutes());
        examObject.setQuestionResponseList(questionResponseList);
        examObject.setNumberOfQuestions(quiz.getNumberOfQuestions());
        return examObject;
    }

    @NotNull
    private static Set<AnswerResponse> getAnswerResponses(Set<Answer> answerList) {
        Set<AnswerResponse> answerResponseSet = new LinkedHashSet<>();
        for(Answer answer : answerList){
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(answer.getId());
            answerResponse.setContent(answer.getContent());
            answerResponse.setMedia(answer.getMedia());
            answerResponse.setCreatedAt(answer.getCreatedAt());
            answerResponse.setCorrect(answer.isCorrect());

            answerResponseSet.add(answerResponse);
        }
        return answerResponseSet;
    }

    @NotNull
    private static QuizResponse getQuizResponse(Quiz quiz, List<QuestionResponse> questionResponseList, UserQuizResult userQuizResult) {
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setUserQuizResultId(userQuizResult.getId());
        quizResponse.setQuizId(quiz.getId());
        quizResponse.setTitle(quiz.getTitle());
        quizResponse.setDescription(quiz.getDescription());
        quizResponse.setNumberOfQuestions(quiz.getNumberOfQuestions());
        quizResponse.setMaxMarks(quiz.getMaxMarks());
        quizResponse.setDurationMinutes(quiz.getDurationMinutes());
        quizResponse.setQuestionResponseList(questionResponseList);
        quizResponse.setCreateAt(quiz.getCreatedAt());
        return quizResponse;
    }

    @Override
    public ResponseEntity<?> submitQuiz(SubmitRequest submitRequest) {
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        Quiz quiz = quizRepository.findById(submitRequest.getQuizId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz Not Exists"));

        UserQuizResult userQuizResult = userQuizResultRepository.findById(submitRequest.getUserQuizResultId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserQuizResult Not Exists"));

        if (userQuizResult.getSubmitted()) {
            return ResponseEntity.badRequest().body("Quiz has already been submitted.");
        }

        List<QuestionChoiceRequest> answers = submitRequest.getAnswers();
        int totalAnswer = answers.size();
        int totalScore = 0;
        int numberOfCorrect = 0;
        int numberOfIncorrect;
        float ratio;

        for (QuestionChoiceRequest answer : answers) {
            Question question = questionRepository.findById(answer.getQuestionId()).orElse(null);
            QuizQuestion quizQuestion = quizQuestionRepository.findByQuizAndQuestion(quiz, question);
            Set<Answer> answerList = answerRepository.findAllByQuestion(question);
            List<Long> correctAnswerIds = answerRepository.getCorrectAnswerFromQuestion(answer.getQuestionId());

            // create userQuestionResult to save to db
            List<Long> selectedOptions = answer.getSelectedOptions();
            boolean isQuestionAnswered = !selectedOptions.isEmpty();
            boolean isCorrect = isQuestionAnswered && validateScore(selectedOptions, correctAnswerIds);

            if (isCorrect) {
                totalScore += quizQuestion.getMarksOfQuestion();
                numberOfCorrect++;
            }

            AnswersToChoose answersToChoose = getAnswersToChoose(answerList, selectedOptions);

            UserQuestionResult userQuestionResult = new UserQuestionResult();
            userQuestionResult.setDone(isQuestionAnswered);
            userQuestionResult.setResult(isCorrect);
            userQuestionResult.setQuestion(mapToQuestionObject(question));
            userQuestionResult.setAnswers(answersToChoose);
            userQuestionResult.setMarkOfQuestion(quizQuestion.getMarksOfQuestion());
            userQuestionResult.setUserQuizResult(userQuizResult);
            userQuestionResult.setGenerateQuestion(mapToQuestionResponse(question));
            userQuestionResultRepository.save(userQuestionResult);
        }

        numberOfIncorrect = totalAnswer - numberOfCorrect;
        saveUserQuizResult(totalScore, userQuizResult, numberOfCorrect, numberOfIncorrect);

//        ratio = (float)numberOfCorrect/totalAnswer;
//        String formattedCapture = String.format("%.2f%%", ratio * 100);

        SubmitResponse submitResponse = new SubmitResponse();
        submitResponse.setMarks(totalScore);
        submitResponse.setQuizId(submitRequest.getQuizId());
        submitResponse.setNumberOfCorrect(numberOfCorrect);
        submitResponse.setUserId(user.getId());
        submitResponse.setNumberOfIncorrect(numberOfIncorrect);
//        submitResponse.setRatio(formattedCapture);
        return ResponseEntity.ok(submitResponse);
    }

    private QuestionObject mapToQuestionObject(Question question) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(question.getCategory().getId());
        categoryResponse.setTitle(question.getCategory().getTitle());
        categoryResponse.setDescription(question.getCategory().getDescription());

        QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
        questionTypeResponse.setAlias(question.getQuestionType().getAlias());
        questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

        QuestionObject questionObject = new QuestionObject();
        questionObject.setId(question.getId());
        questionObject.setMedia(question.getMedia());
        questionObject.setContent(question.getContent());
        questionObject.setCategory(categoryResponse);
        questionObject.setQuestionType(questionTypeResponse);
        questionObject.setCreatedAt(question.getCreatedAt());
        return questionObject;
    }

    private AnswersToChoose getAnswersToChoose(Set<Answer> answerList, List<Long> selectedOptions) {
        List<AnswerObject> answerObjectList = new ArrayList<>();

        // lặp qua mảng các answer của question để set các value cho answerObject
        // đồng thời cũng add nó vào trong answerObjectList
        for(Answer answer : answerList){
            AnswerObject answerObject = new AnswerObject();
            Long answerId = answer.getId();
            answerObject.setId(answer.getId());
            answerObject.setMedia(answer.getMedia());
            answerObject.setContent(answer.getContent());
            answerObject.setCorrect(answer.isCorrect());
            // setSlect theo: nếu answerId tồn tại trong selectedOptions
            answerObject.setSelect(selectedOptions.contains(answerId));
            // add vao answerObjectList
            answerObjectList.add(answerObject);
        }
        AnswersToChoose answersToChoose = new AnswersToChoose();
        answersToChoose.setAnswers(answerObjectList);
        return answersToChoose;
    }

    private void saveUserQuizResult(int totalScore, UserQuizResult userQuizResult, int numberOfCorrect, int numberOfIncorrect){
        if (userQuizResult != null) {
            userQuizResult.setMarks(totalScore);
            userQuizResult.setSubmitTime(new Timestamp(System.currentTimeMillis()));
            userQuizResult.setNumberOfCorrect(numberOfCorrect);
            userQuizResult.setNumberOfIncorrect(numberOfIncorrect);
            userQuizResult.setSubmitted(true);

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
