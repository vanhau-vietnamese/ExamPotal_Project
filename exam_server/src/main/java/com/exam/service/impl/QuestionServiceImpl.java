package com.exam.service.impl;

import com.exam.dto.request.AnswerRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.response.CategoryResponse;
import com.exam.dto.response.QuestionResponse;
import com.exam.dto.response.QuestionTypeResponse;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        EQuestionType questionType = EQuestionType.getByAlias(questionRequest.getQuestionTypeId());

        Optional<Category> category = categoryRepository.findById(questionRequest.getCategoryId());

        Question question = new Question();
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setQuestionType(questionType);
        question.setCategory(category.get());

        questionRepository.save(question);

        QuizQuestion quizQuestion = new QuizQuestion();
        if(questionRequest.getQuizId() == null){
            quizQuestion.setQuiz(null);
        }
        else{
            Optional<Quiz> quiz = quizRepository.findById(questionRequest.getQuizId());
            quizQuestion.setQuiz(quiz.get());
        }

        quizQuestion.setQuestion(question);
        quizQuestionRepository.save(quizQuestion);

        List<AnswerRequest> answerList = questionRequest.getAnswerRequestList();
        int size = questionRequest.getAnswerRequestList().size();
        // add answer
        for(int i=0; i<size; i++){
            addListAnswer(answerList.get(i), question);
        }

        QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
        questionTypeResponse.setAlias(question.getQuestionType().getAlias());
        questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

        QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse);
        return ResponseEntity.ok(questionResponse);
    }

    private void addListAnswer(AnswerRequest answerRequest, Question question){
        Answer answer = new Answer();
        answer.setMedia(answerRequest.getMedia());
        answer.setContent(answerRequest.getContent());
        answer.setCorrect(answerRequest.isCorrect());
        answer.setQuestion(question);

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

            QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse);

            return ResponseEntity.ok(questionResponse);
        }
        return ResponseEntity.badRequest().body("Not found Question");
    }

    @Override
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();

        GetQuestionResponseList(questionResponses, questions);

        return ResponseEntity.ok(questionResponses);
    }

    private QuestionResponse getQuestionResponse(Question question, QuestionTypeResponse questionTypeResponse) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(question.getCategory().getId());
        categoryResponse.setTitle(question.getCategory().getTitle());
        categoryResponse.setDescription(question.getCategory().getDescription());

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setContent(question.getContent());
        questionResponse.setMedia(question.getMedia());
        questionResponse.setCreatedAt(question.getCreatedAt());
        questionResponse.setMarksOfQuestion(question.getMarksOfQuestion());
        questionResponse.setQuestionType(questionTypeResponse);
        questionResponse.setStatus(question.getStatus());
        questionResponse.setAnswers(question.getAnswers());
        questionResponse.setCategory(categoryResponse);
        return questionResponse;
    }

    @Override
    public ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            boolean questionTypeValid  = isValidQuestionTypeByAlias(questionRequest.getQuestionTypeId());

            if(!questionTypeValid){
                return ResponseEntity.badRequest().body("Question Type Not Existed");
            }

            EQuestionType questionType = EQuestionType.getByAlias(questionRequest.getQuestionTypeId());

            Optional<Category> categoryOptional = categoryRepository.findById(question.getId());

            question.setMedia(questionRequest.getMedia());
            question.setContent(questionRequest.getContent());
            question.setQuestionType(questionType);
            question.setStatus(questionRequest.getStatus());
            question.setCategory(categoryOptional.get());

            questionRepository.save(question);

            QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
            questionTypeResponse.setAlias(question.getQuestionType().getAlias());
            questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

            QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse);

            return ResponseEntity.ok(questionResponse);
        }
        return ResponseEntity.badRequest().body("Not Found Question");
    }

    @Override
    public ResponseEntity<String> deleteQuestion(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getQuestionsOfQuiz(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<Question> questions = questionRepository.getQuestionsOfQuiz(quizId);
        if(quiz.isPresent()){
            GetQuestionResponseList(questionResponses, questions);
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.badRequest().body("Not Found Quiz");
    }

    private void GetQuestionResponseList(List<QuestionResponse> questionResponses, List<Question> questions) {
        for (Question question : questions){
            QuestionTypeResponse questionTypeResponse = new QuestionTypeResponse();
            questionTypeResponse.setAlias(question.getQuestionType().getAlias());
            questionTypeResponse.setDisplayName(question.getQuestionType().getDisplayName());

            QuestionResponse questionResponse = getQuestionResponse(question, questionTypeResponse);

            questionResponses.add(questionResponse);
        }
    }

    @Override
    public ResponseEntity<?> getQuestionsOfCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        List<QuestionResponse> questionResponses = new ArrayList<>();
        List<Question> questions = questionRepository.getQuestionsOfCategory(id);
        if(category.isPresent()){
            GetQuestionResponseList(questionResponses, questions);
            return ResponseEntity.ok(questionResponses);
        }
        return ResponseEntity.badRequest().body("Not Found Category");
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
