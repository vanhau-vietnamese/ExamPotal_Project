package com.exam.service.impl;

import com.exam.dto.request.QuestionRequest;
import com.exam.model.Question;
import com.exam.model.QuestionType;
import com.exam.model.Quiz;
import com.exam.model.QuizQuestion;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizQuestionRepository;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    @Override
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        QuestionType questionType = new QuestionType();
        questionType.setAlias(questionRequest.getQuestionTypeId());

        Question question = new Question();
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setStatus(question.getStatus());
        question.setQuestionType(questionType);

        Quiz quiz = new Quiz();
        quiz.setId(questionRequest.getQuizId());

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);

        Set<QuizQuestion> quizQuestions = new LinkedHashSet<>();
        quizQuestions.add(quizQuestion);

        question.setQuizQuestions(quizQuestions);


        return ResponseEntity.ok(questionRepository.save(question));
    }

    @Override
    public ResponseEntity<Question> getQuestion(Long id) {
        Question question = questionRepository.findById(id).get();
        if(question != null){
            return ResponseEntity.ok(question);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getAllQuestions() {
        return ResponseEntity.ok(questionRepository.findAll());
    }

    @Override
    public ResponseEntity<Question> editQuestion(Long id, Question questionRequest) {
        Question question = questionRepository.findById(id).get();

        if(question != null){
            question.setMedia(question.getMedia());
            question.setContent(question.getContent());
            question.setQuestionType(questionRequest.getQuestionType());
            question.setStatus(questionRequest.getStatus());

            return ResponseEntity.ok(questionRepository.save(question));
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteQuestion(Long id) {
        return null;
    }
}
