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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Quiz> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài thi nào.");
        }
        List<QuizResponseDto> responses = quizzes.stream().map(QuizResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Transactional
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

    @Transactional
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

                QuestionResponse questionResponse = QuestionResponse.builder()
                        .id(question.getId())
                        .questionType(questionTypeResponse)
                        .media(question.getMedia())
                        .createdAt(question.getCreatedAt())
                        .category(categoryResponse)
                        .content(question.getContent())
                        .answers(answerResponseSet)
                        .build();

                questionResponseList.add(questionResponse);
            }

            QuizResponse quizResponse = QuizResponse.builder()
                    .quizId(id)
                    .title(quiz.getTitle())
                    .description(quiz.getDescription())
                    .numberOfQuestions(quiz.getNumberOfQuestions())
                    .durationMinutes(quiz.getDurationMinutes())
                    .maxMarks(quiz.getMaxMarks())
                    .questionResponseList(questionResponseList)
                    .createAt(quiz.getCreatedAt())
                    .build();

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

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public ResponseEntity<?> getQuizzesOfCategory(Long categoryId) {
        if(categoryId == 0){
            List<Quiz> quizzes = quizRepository.findAll();
            if (quizzes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài thi nào.");
            }
            List<QuizResponseDto> responses = quizzes.stream().map(QuizResponseDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }
        if (categoryRepository.existsById(categoryId)){
            Set<Quiz> quizzes = quizRepository.getQuizzesOfCategory(categoryId);
            if (quizzes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài thi nào.");
            }
            List<QuizResponseDto> responses = quizzes.stream().map(QuizResponseDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND Category");
    }

    @Transactional
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

    @Transactional
    @Override
    public ResponseEntity<?> searchQuizzes(Map<String, String> searchRequest) {
        List<Quiz> quizzes = quizRepository.searchQuizzes(searchRequest.get("searchContent"));
        if (quizzes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài thi nào.");
        }
        List<QuizResponseDto> responses = quizzes.stream().map(QuizResponseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<?> paginationQuestion(int page, int size) {
        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        var pageData = quizRepository.findAllByStatus(EStatus.Active, pageable);

//        PageResponse<> pageResponse = PageResponse<>
//        return ResponseEntity.ok(pageResponse);
        return null;
    }
}
