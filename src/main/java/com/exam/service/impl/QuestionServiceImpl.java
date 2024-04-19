package com.exam.service.impl;

import com.exam.dto.request.AnswerRequest;
import com.exam.dto.request.FilterCreateAtRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.request.QuestionTypeRequest;
import com.exam.dto.response.AnswerResponse;
import com.exam.dto.response.CategoryResponse;
import com.exam.dto.response.QuestionResponse;
import com.exam.dto.response.QuestionTypeResponse;
import com.exam.enums.EQuestionType;
import com.exam.enums.EStatus;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        boolean questionTypeValid  = isValidQuestionTypeByAlias(questionRequest.getQuestionTypeId());
        if(!questionTypeValid){
            return ResponseEntity.badRequest().body("Question Type Not Existed");
        }

        // Kiểm tra category
        Optional<Category> optionalCategory = categoryRepository.findById(questionRequest.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return ResponseEntity.badRequest().body("Category not found");
        }
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
        return ResponseEntity.ok(questionResponse);
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
        return ResponseEntity.badRequest().body("Not found Question");
    }

    @Override
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionRepository.findAllByStatus(EStatus.Active);
        List<QuestionResponse> questionResponses = new ArrayList<>();

        GetQuestionResponseList(questionResponses, questions, null);

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
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<Question> questions = quizQuestionRepository.getQuestionsOfQuiz(quizId);
        if(quiz.isPresent()){
            GetQuestionResponseList(questionResponses, questions, quiz.get());
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.badRequest().body("Not Found Quiz");
    }

    private void GetQuestionResponseList(List<QuestionResponse> questionResponses, List<Question> questions, Quiz quiz) {
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
    }

    @Override
    public ResponseEntity<?> getQuestionsOfCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<Question> questions = questionRepository.getQuestionsOfCategory(id, EStatus.Active);
        if(category.isPresent()){
            GetQuestionResponseList(questionResponses, questions, null);
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.badRequest().body("Not Found Category");
    }

    @Override
    public ResponseEntity<?> getQuestionsOfQuestionType(QuestionTypeRequest request) {
        EQuestionType questionType = EQuestionType.getByAlias(request.getAlias());
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<Question> questions = questionRepository.findQuestionsByQuestionType(questionType);
        GetQuestionResponseList(questionResponses, questions, null);
        return ResponseEntity.ok(questionResponses);
    }

    @Override
    public ResponseEntity<?> getQuestionsOfCreateAt(FilterCreateAtRequest request) {
        if(request.getFromTime() == null || request.getToTime() == null){
            return ResponseEntity.badRequest().body("Thời gian không được để trống");
        }
        List<Question> questions = questionRepository.getQuestionsByCreateAt(request.getFromTime(), request.getToTime());
        List<QuestionResponse> questionResponses = new ArrayList<>();
        GetQuestionResponseList(questionResponses, questions, null);
        return ResponseEntity.ok(questionResponses);
    }

    @Override
    public ResponseEntity<?> searchQuestions(Map<String, String> searchRequest) {
        List<Question> questions = questionRepository.searchQuestions(searchRequest.get("searchContent"));
        List<QuestionResponse> questionResponses = new ArrayList<>();
        GetQuestionResponseList(questionResponses, questions, null);
        return ResponseEntity.ok(questionResponses);
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
