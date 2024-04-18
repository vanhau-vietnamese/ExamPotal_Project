package com.exam.repository;

import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizResultRepository extends JpaRepository<UserQuizResult, Long> {
    public UserQuizResult findByUserAndQuiz(User User, Quiz quiz);
    @Query("SELECT uqr FROM UserQuizResult uqr WHERE uqr.quiz.id = :quizId")
    public List<UserQuizResult> getUserQuizResultsByQuizId(@Param("quizId") Long quizId);
}
