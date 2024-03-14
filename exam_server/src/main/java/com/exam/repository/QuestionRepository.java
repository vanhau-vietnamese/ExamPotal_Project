package com.exam.repository;

import com.exam.model.Question;
import com.exam.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT qq.question FROM QuizQuestion qq WHERE qq.quiz.id = :quizId")
    public Set<Question> getQuestionsOfQuiz(@Param("quizId") Long quiz_id);
}
