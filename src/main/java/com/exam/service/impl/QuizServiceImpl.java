package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.QuizQuestionRequest;
import com.exam.dto.request.QuizRequest;
import com.exam.dto.response.*;
import com.exam.enums.EStatus;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.QuizService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final AnswerRepository answerRepository;
    @Override
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizRepository.findAllByStatus(EStatus.Active));
    }

    @Override
    public ResponseEntity<?> addQuiz(QuizRequest quizRequest) {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setMaxMarks(quizRequest.getMaxMarks());
        quiz.setCreateBy(user);
        quiz.setDurationMinutes(quizRequest.getDurationMinutes());
        quiz.setStatus(EStatus.Active);
        quiz.setNumberOfQuestions(quizRequest.getListQuestion().size());

        Optional<Category> category = categoryRepository.findById(quizRequest.getCategoryId());
        if (!category.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Category.");
        }
        quiz.setCategory(category.get());

        quizRepository.save(quiz);

        List<QuizQuestionRequest> listQuestionRequest = quizRequest.getListQuestion();
//        QuizQuestion quizQuestion
        for(QuizQuestionRequest questionRequest : listQuestionRequest){
            //ở nayf nên thêm 1 điệu kiện kiểm tra xem questionId có tồn tại hay k
            Question question = new Question();
            question.setId(questionRequest.getQuestionId());

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(question);
            quizQuestion.setMarksOfQuestion(questionRequest.getMarksOfQuestion());

            quizQuestionRepository.save(quizQuestion);
        }
        return ResponseEntity.ok(quiz);
    }

    @Override
    public ResponseEntity<?> getQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).get();
        if(quiz != null){
            List<Question> questionList = quizQuestionRepository.getQuestionsOfQuiz(id);

            List<QuestionResponse> questionResponseList = new ArrayList<>();

            for(Question question : questionList){
                // setQuestionTypeReponse
                QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
                questionTypeResponse.setAlias(question.getQuestionType().getAlias());
                questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

                // setCategoryReponse
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(question.getCategory().getId());
                categoryResponse.setTitle(question.getCategory().getTitle());
                categoryResponse.setDescription(question.getCategory().getDescription());

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

                questionResponseList.add(questionResponse);
            }

            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setQuizId(id);
            quizResponse.setTitle(quiz.getTitle());
            quizResponse.setDescription(quiz.getDescription());
            quizResponse.setNumberOfQuestions(quiz.getNumberOfQuestions());
            quizResponse.setDurationMinutes(quiz.getDurationMinutes());
            quizResponse.setMaxMarks(quiz.getMaxMarks());
            quizResponse.setQuestionResponseList(questionResponseList);
            quizResponse.setCreateAt(quiz.getCreatedAt());

            return ResponseEntity.ok(quizResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND QUIZ");
    }

    private static Set<AnswerResponse> getAnswerResponses(Set<Answer> answerList) {
        Set<AnswerResponse> answerResponseSet = new LinkedHashSet<>();
        for(Answer answer : answerList){
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(answer.getId());
            answerResponse.setContent(answer.getContent());
            answerResponse.setMedia(answer.getMedia());
            answerResponse.setCreatedAt(answer.getCreatedAt());

            answerResponseSet.add(answerResponse);
        }
        return answerResponseSet;
    }

    @Override
    public ResponseEntity<?> deleteQuiz(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            quiz.setStatus(EStatus.Deleted);
            quizRepository.save(quiz);
            return ResponseEntity.ok("Deleted Quiz Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND QUIZ");
    }

    @Override
    public ResponseEntity<?> updateQuiz(Long id, QuizRequest quizRequest) {
        Quiz quiz = quizRepository.findById(id).get();
        if(quiz != null){
            Category category = categoryRepository.findById(quizRequest.getCategoryId()).get();
            // get jwt from request
            String jwt = jwtAuthenticationFilter.getJwt();
            FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
            String email = decodedToken.getEmail();
            User user = userRepository.findByEmail(email);

            quiz.setTitle(quizRequest.getTitle());
            quiz.setDescription(quizRequest.getDescription());
            quiz.setMaxMarks(quizRequest.getMaxMarks());
            quiz.setStatus(EStatus.Active);
            quiz.setCategory(category);
            quiz.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            quiz.setNumberOfQuestions(quizRequest.getListQuestion().size());
            quiz.setCreateBy(user);
            quiz.setDurationMinutes(quizRequest.getDurationMinutes());

            quizRepository.save(quiz);

            // delete quizQuestion cos quiz
            List<QuizQuestion> quizQuestionList = quizQuestionRepository.findAllByQuiz(quiz);
            List<QuizQuestionRequest> listQuestionRequest = quizRequest.getListQuestion();
//        QuizQuestion quizQuestion

            if(!areListEqual(quizQuestionList, listQuestionRequest)){
                quizQuestionRepository.deleteByQuizId(id);

                for(QuizQuestionRequest questionRequest : listQuestionRequest){
                    //ở nayf nên thêm 1 điệu kiện kiểm tra xem questionId có tồn tại hay k
                    Question question = new Question();
                    question.setId(questionRequest.getQuestionId());

                    QuizQuestion quizQuestion = new QuizQuestion();
                    quizQuestion.setQuiz(quiz);
                    quizQuestion.setQuestion(question);
                    quizQuestion.setMarksOfQuestion(questionRequest.getMarksOfQuestion());

                    quizQuestionRepository.save(quizQuestion);
                }

            }
            return ResponseEntity.ok(quiz);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND QUIZ");

    }

    private static boolean areListEqual(List<QuizQuestion> quizQuestionList,List<QuizQuestionRequest> listQuestionRequest){
        if(quizQuestionList.size() != listQuestionRequest.size()){
            return false;
        }

        Map<Long, Integer> map = new HashMap<>();
        for(QuizQuestion itemQuizQuestion : quizQuestionList){
            map.put(itemQuizQuestion.getQuestion().getId(), itemQuizQuestion.getMarksOfQuestion());
        }

        for(QuizQuestionRequest item : listQuestionRequest){
            if(!map.containsKey(item.getQuestionId())){
                return false;
            }
            else{
                Integer marks = map.get(item.getQuestionId());
                if(marks == null || !marks.equals(item.getMarksOfQuestion())){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ResponseEntity<?> getQuizzesOfCategory(Long categoryId) {
        if(categoryId == 0){
            return ResponseEntity.ok(quizRepository.findAllByStatus(EStatus.Active));
        }
        if (categoryRepository.existsById(categoryId)){
            return ResponseEntity.ok(quizRepository.getQuizzesOfCategory(categoryId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND Category");
    }

    @Override
    public ResponseEntity<?> getQuizzesOfCreateAt(Map<String, Timestamp> request) {
        if(request.get("fromTime") == null || request.get("toTime") == null){
            return ResponseEntity.badRequest().body("Thời gian không được để trống");
        }
        List<Quiz> quizzes = quizRepository.getQuizzesByCreateAt(request.get("fromTime"), request.get("toTime"));

        if (quizzes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Quiz");
        }

        return ResponseEntity.ok(quizzes);
    }

    @Override
    public ResponseEntity<?> searchQuizzes(Map<String, String> searchRequest) {
        List<Quiz> quizzes = quizRepository.searchQuizzes(searchRequest.get("searchContent"));
        return ResponseEntity.ok(quizzes);
    }
}
