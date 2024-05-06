package com.exam.repository;

import com.exam.dto.response.RateOfQuestionsResponse;
import com.exam.model.UserQuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionResultRepository extends JpaRepository<UserQuestionResult, Long> {
    @Query("SELECT new com.exam.dto.response.RateOfQuestionsResponse(u.question, " +
            "CAST(SUM(CASE WHEN u.result = true THEN 1 ELSE 0 END) AS DOUBLE) / COUNT(*) * 100, " +
            "CAST(SUM(CASE WHEN u.result = false THEN 1 ELSE 0 END) AS DOUBLE) / COUNT(*) * 100) " +
            "FROM UserQuestionResult u " +
            "GROUP BY u.question")
    List<RateOfQuestionsResponse> getRateOfQuestions();
}
