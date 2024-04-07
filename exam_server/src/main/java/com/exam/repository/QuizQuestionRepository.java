package com.exam.repository;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    QuizQuestion findByQuizAndQuestion(Quiz quiz, Question question);
    QuizQuestion findByQuestionIdAndQuizId(Long questionId, Long quizId);
    @Query("SELECT qq.question FROM QuizQuestion qq WHERE qq.quiz.id = :quizId")
    List<Question> getQuestionsOfQuiz(@Param("quizId") Long quiz_id);

    List<QuizQuestion> findAllByQuiz(Quiz quiz);
}
