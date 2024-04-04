package com.exam.repository;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    public QuizQuestion findByQuizAndQuestion(Quiz quiz, Question question);
    QuizQuestion findByQuestionIdAndQuizId(Long questionId, Long quizId);
}
