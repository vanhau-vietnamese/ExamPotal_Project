package com.exam.service.impl;

import com.exam.dto.request.AnswerRequest;
import com.exam.dto.request.FilterCreateAtRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.request.QuestionTypeRequest;
import com.exam.dto.response.*;
import com.exam.enums.EQuestionType;
import com.exam.enums.EStatus;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public QuestionResponse addQuestion(QuestionRequest questionRequest) {
        boolean questionTypeValid  = isValidQuestionTypeByAlias(questionRequest.getQuestionTypeId());

        // Kiểm tra category
        Optional<Category> optionalCategory = categoryRepository.findById(questionRequest.getCategoryId());
        Category category = optionalCategory.get();

        // Tạo và lưu câu hỏi
        Question question = new Question();
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setQuestionType(EQuestionType.getByAlias(questionRequest.getQuestionTypeId()));
        question.setCategory(category);
        question.setStatus(EStatus.Active);
        questionRepository.save(question);

        // Tạo và lưu các câu trả lời của câu hỏi
        Set<Answer> answers = new LinkedHashSet<>();
        for (AnswerRequest answerRequest : questionRequest.getAnswerRequestList()) {
            Answer answer = new Answer();
            addListAnswer(answerRequest, question, answer);
            answers.add(answer);
        }
        question.setAnswers(answers);
        questionRepository.save(question);

        // Tạo và trả về phản hồi
        QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
        questionTypeResponse.setAlias(question.getQuestionType().getAlias());
        questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

        QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse, null);
        return questionResponse;
    }

    @Transactional
    @Override
    public ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (!optionalQuestion.isPresent()) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        Question question = optionalQuestion.get();

        // Kiểm tra loại câu hỏi
        EQuestionType questionType = EQuestionType.getByAlias(questionRequest.getQuestionTypeId());
        if (questionType == null) {
            return ResponseEntity.badRequest().body("Question type not found");
        }

        // Kiểm tra category
        Optional<Category> optionalCategory = categoryRepository.findById(questionRequest.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.badRequest().body("Category not found");
        }

        // Cập nhật thông tin của câu hỏi
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setQuestionType(questionType);
        question.setCategory(optionalCategory.get());
        question.setStatus(EStatus.Active);
        questionRepository.save(question);

        // Xóa các câu trả lời cũ của câu hỏi
        answerRepository.deleteByQuestionId(id);

        // Thêm các câu trả lời mới
        Set<Answer> answers = new LinkedHashSet<>();
        for (AnswerRequest answerRequest : questionRequest.getAnswerRequestList()) {
            Answer answer = new Answer();
            addListAnswer(answerRequest, question, answer);
            answers.add(answer);
        }
        question.setAnswers(answers);

        // Tạo và trả về phản hồi
        QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
        questionTypeResponse.setAlias(question.getQuestionType().getAlias());
        questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

        QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse, null);
        return ResponseEntity.ok(questionResponse);
    }

    private void addListAnswer(AnswerRequest answerRequest, Question question, Answer answer){
        answer.setMedia(answerRequest.getMedia());
        answer.setContent(answerRequest.getContent());
        answer.setCorrect(answerRequest.isCorrect());
        answer.setQuestion(question);
        answer.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        answerRepository.save(answer);
    }

    @Override
    public ResponseEntity<?> getQuestion(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();

            QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
            questionTypeResponse.setAlias(question.getQuestionType().getAlias());
            questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());  
            QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse, null);

            return ResponseEntity.ok(questionResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Question.");
    }

    @Override
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);

        return ResponseEntity.ok(questionResponses);
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

    private QuestionResponse getQuestionResponse(Question question, QuestionTypeResponse questionTypeResponse, Object additionalField) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(question.getCategory().getId());
        categoryResponse.setTitle(question.getCategory().getTitle());
        categoryResponse.setDescription(question.getCategory().getDescription());

        Set<AnswerResponse> answerResponseSet = getAnswerResponses(question.getAnswers());

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setContent(question.getContent());
        questionResponse.setMedia(question.getMedia());
        questionResponse.setCreatedAt(question.getCreatedAt());
        questionResponse.setQuestionType(questionTypeResponse);
        questionResponse.setAnswers(answerResponseSet);
        questionResponse.setCategory(categoryResponse);

        // add marksOfquestion
        Map<String, Object> additionalFields = new HashMap<>();
        additionalFields.put("marksOfQuestion", additionalField);
        questionResponse.setAdditionalFields(additionalFields);
        return questionResponse;
    }

    @Override
    public ResponseEntity<String> deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).get();
        if(validateDeleteQuestion(question)){
            return ResponseEntity.badRequest().body("Delete failed. This question already exists in the test");
        }
        question.setStatus(EStatus.Deleted);
        questionRepository.save(question);
        return ResponseEntity.ok("Deleted Successfully");
    }

    private boolean validateDeleteQuestion(Question question){
        return quizQuestionRepository.existsQuizQuestionByQuestion(question);
    }

    @Override
    public ResponseEntity<?> getQuestionsOfQuiz(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        List<Question> questions = quizQuestionRepository.getQuestionsOfQuiz(quizId);
        if(quiz.isPresent()){
            List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, quiz.get());
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Quiz.");
    }

    private List<QuestionResponse> GetQuestionResponseList(List<Question> questions, Quiz quiz) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions){
            Integer marksOfQuestion = null;
            QuizQuestion quizQuestion = null;
            if(quiz != null){
                quizQuestion = quizQuestionRepository.findByQuizAndQuestion(quiz, question);
                marksOfQuestion = quizQuestion.getMarksOfQuestion();
            }
            QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
            questionTypeResponse.setAlias(question.getQuestionType().getAlias());
            questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

            QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse, marksOfQuestion);

            questionResponses.add(questionResponse);
        }
        return questionResponses;
    }

    @Override
    public ResponseEntity<?> getQuestionsOfCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        List<Question> questions = questionRepository.getQuestionsOfCategory(id, EStatus.Active);
        if(category.isPresent()){
            List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Category.");
    }

    @Override
    public ResponseEntity<?> getQuestionsOfQuestionType(QuestionTypeRequest request) {
        EQuestionType questionType = EQuestionType.getByAlias(request.getAlias());
        List<Question> questions = questionRepository.findQuestionsByQuestionType(questionType);
        List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);
        return ResponseEntity.ok(questionResponses);
    }

    @Override
    public ResponseEntity<?> getQuestionsOfCreateAt(FilterCreateAtRequest request) {
        if(request.getFromTime() == null || request.getToTime() == null){
            return ResponseEntity.badRequest().body("Thời gian không được để trống");
        }
        List<Question> questions = questionRepository.getQuestionsByCreateAt(request.getFromTime(), request.getToTime());
        List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);
        return ResponseEntity.ok(questionResponses);
    }

    @Override
    public ResponseEntity<?> searchQuestions(Map<String, String> searchRequest) {
        List<Question> questions = questionRepository.searchQuestions(searchRequest.get("searchContent"));
        List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);
        return ResponseEntity.ok(questionResponses);
    }

    @Override
    public ResponseEntity<?> paginationQuestions(int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = questionRepository.findAllByStatus(EStatus.Active, pageable);
        List<Question> questions = pageData.getContent();
        List<QuestionResponse> questionResponses = GetQuestionResponseList(questions, null);
        PageResponse<QuestionResponse> pageResponse = PageResponse.<QuestionResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(questionResponses)
                .build();
        return ResponseEntity.ok(pageResponse);
    }

    public boolean isValidQuestionTypeByAlias(String alias) {
        for (EQuestionType type : EQuestionType.values()) {
            if (type.getAlias().equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return false;
    }

}
