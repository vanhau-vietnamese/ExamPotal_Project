package com.exam.service.impl;

import com.exam.chatbot.dto.AnswerDto;
import com.exam.chatbot.dto.QuestionDto;
import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.service.AgentVerifyQuestionService;
import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.response.UserHistoryDetailResponse;
import com.exam.dto.response.UserQuizResultResponse;
import com.exam.enums.EStatus;
import com.exam.helper.AnswerObject;
import com.exam.mapper.AnswerMapper;
import com.exam.model.User;
import com.exam.model.UserQuestionResult;
import com.exam.model.UserQuizResult;
import com.exam.repository.UserQuizResultRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserQuizResultService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserQuizResultServiceImpl implements UserQuizResultService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserQuizResultRepository userQuizResultRepository;
    private final AnswerMapper answerMapper;
    private final AgentVerifyQuestionService agentVerifyQuestionService;
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

    @Override
    public UserHistoryDetailResponse detail(Long id) throws IOException {
        Optional<UserQuizResult> optionalUserQuizResult = userQuizResultRepository.findById(id);
        UserQuizResult userQuizResult = optionalUserQuizResult.get();

        List<UserQuestionResult> capture = new ArrayList<>(userQuizResult.getCapture());

        List<QuestionResultDto> questions = new ArrayList<>();
        for(UserQuestionResult userQuestionResult : capture){
            QuestionResultDto question = new QuestionResultDto();

            List<AnswerObject> answerObjects = userQuestionResult.getAnswers().getAnswers();
            List<AnswerDto> answerResponse = new ArrayList<>();
            for(AnswerObject answerObject : answerObjects){
                AnswerDto answerDto = AnswerDto.builder()
                        .media(answerObject.getMedia())
                        .content(answerObject.getContent())
                        .isSelect(answerObject.isSelect())
                        .isCorrect(answerObject.isCorrect())
                        .build();
                answerResponse.add(answerDto);
            }

            System.out.println("ANSWER RESPOSNE: " + answerResponse);

            // set question
            QuestionDto questionDto = QuestionDto.builder()
                    .media(userQuestionResult.getQuestion().getMedia())
                    .content(userQuestionResult.getQuestion().getContent())
                    .questionTypeId(userQuestionResult.getQuestion().getQuestionType().getAlias())
                    .categoryId(userQuestionResult.getQuestion().getCategory().getId())
                    .answers(answerResponse)
                    .marksOfQuestion(userQuestionResult.getMarkOfQuestion())
                    .result(userQuestionResult.isResult())
                    .build();
            question.setQuestion(questionDto);

            if(userQuestionResult.isResult()){
                question.setStatus(true);
                question.setReason(null);
                question.setSuggestion(null);
                question.setSolution(null);
                question.setReference(null);
                question.setResult(true);
            }
            else {
                // agent gợi ý của lịch sử
                question = agentVerifyQuestionService.agentSuggestQuestion(questionDto);
                question.setQuestion(questionDto);
            }
            questions.add(question);
        }

        return UserHistoryDetailResponse.builder()
                .quizId(userQuizResult.getQuiz().getId())
                .marks(userQuizResult.getMarks())
                .id(userQuizResult.getId())
                .startTime(userQuizResult.getStartTime())
                .submitTime(userQuizResult.getSubmitTime())
                .durationTime(userQuizResult.getDurationTime())
                .numberOfCorrect(userQuizResult.getNumberOfCorrect())
                .numberOfIncorrect(userQuizResult.getNumberOfIncorrect())
                .userId(userQuizResult.getUser().getId())
                .listQuestions(questions)
                .build();
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
