package com.exam.repository;

import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizResultRepository extends JpaRepository<UserQuizResult, Long> {
    public UserQuizResult findByUserAndQuiz(User User, Quiz quiz);
}
