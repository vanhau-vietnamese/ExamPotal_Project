package com.exam.service.impl;

import com.exam.dto.request.QuestionRequest;
import com.exam.model.Question;
import com.exam.model.QuestionType;
import com.exam.model.Quiz;
import com.exam.model.QuizQuestion;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuestionTypeRepository;
import com.exam.repository.QuizQuestionRepository;
import com.exam.repository.QuizRepository;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuizRepository quizRepository;
    @Override
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        QuestionType questionType = questionTypeRepository.findByAlias(questionRequest.getQuestionTypeId());

        Question question = new Question();
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setStatus(question.getStatus());
        question.setQuestionType(questionType);

        questionRepository.save(question);

        Quiz quiz = quizRepository.findById(questionRequest.getQuizId()).get();

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);
        quizQuestionRepository.save(quizQuestion);

        return ResponseEntity.ok(question);
    }

    @Override

    public ResponseEntity<?> getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if(question.isPresent()){
            return ResponseEntity.ok(question.get());
        }
        return ResponseEntity.badRequest().body("Not found Question");
    }

    @Override
    public ResponseEntity<?> getAllQuestions() {
        return ResponseEntity.ok(questionRepository.findAll());
    }

    @Override
    public ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if(questionOptional.isPresent()){
            Question question = questionOptional.get();

            QuestionType questionType = questionTypeRepository.findByAlias(questionRequest.getQuestionTypeId());

            question.setMedia(questionRequest.getMedia());
            question.setContent(questionRequest.getContent());
            question.setQuestionType(questionType);
            question.setStatus(questionRequest.getStatus());

            return ResponseEntity.ok(questionRepository.save(question));
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
        if(quiz.isPresent()){
            return ResponseEntity.ok(questionRepository.getQuestionsOfQuiz(quizId));
        }
        return ResponseEntity.badRequest().body("Not Found Quiz");
    }
}
