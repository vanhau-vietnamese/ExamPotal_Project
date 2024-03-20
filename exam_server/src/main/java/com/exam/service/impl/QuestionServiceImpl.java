package com.exam.service.impl;

import com.exam.dto.request.AnswerRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.model.*;
import com.exam.repository.*;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    @Override
    public ResponseEntity<?> addQuestion(QuestionRequest questionRequest) {
        QuestionType questionType = questionTypeRepository.findByAlias(questionRequest.getQuestionTypeId());
        System.out.println("questionType: "+questionType);

        Question question = new Question();
        question.setMedia(questionRequest.getMedia());
        question.setContent(questionRequest.getContent());
        question.setQuestionType(questionType);

        questionRepository.save(question);

        Quiz quiz = quizRepository.findById(questionRequest.getQuizId()).get();

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);
        quizQuestionRepository.save(quizQuestion);

        List<AnswerRequest> answerList = questionRequest.getAnswerRequestList();
        int size = questionRequest.getAnswerRequestList().size();
        System.out.println("size: "+size);
        // add answer
        for(int i=0; i<size; i++){
            addListAnswer(answerList.get(i), question);
        }

        return ResponseEntity.ok(question);
    }

    private void addListAnswer(AnswerRequest answerRequest, Question question){
        Answer answer = new Answer();
        answer.setMedia(answerRequest.getMedia());
        answer.setContent(answerRequest.getContent());
        answer.setCorrect(answerRequest.isCorrect());
        System.out.println("isCorrect: "+answerRequest.isCorrect());
        answer.setQuestion(question);

        answerRepository.save(answer);
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
