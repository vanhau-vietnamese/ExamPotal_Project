package com.exam.service.impl;

import com.exam.dto.request.AnswerRequest;
import com.exam.model.Answer;
import com.exam.model.Question;
import com.exam.repository.AnswerRepository;
import com.exam.repository.QuestionRepository;
import com.exam.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    @Override
    public ResponseEntity<?> addAnswer(AnswerRequest answerRequest) {
        Optional<Question> question = questionRepository.findById(answerRequest.getQuestionId());
        Answer answer = new Answer();
        answer.setMedia(answerRequest.getMedia());
        answer.setContent(answerRequest.getContent());
        answer.setCorrect(answerRequest.isCorrect());
        answer.setQuestion(question.get());

        return ResponseEntity.ok(answerRepository.save(answer));
    }
}
