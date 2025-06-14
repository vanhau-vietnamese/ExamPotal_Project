package com.exam.repository;

import com.exam.dto.response.UserHistoryDetailResponse;
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
    UserQuizResult findByUserAndQuiz(User User, Quiz quiz);
    @Query("SELECT uqr FROM UserQuizResult uqr WHERE uqr.quiz.id = :quizId AND uqr.status = 'Active'")
    List<UserQuizResult> getUserQuizResultsByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT uqr FROM UserQuizResult uqr WHERE uqr.submitted = true AND uqr.user.id = :userId AND uqr.status = 'Active'")
    List<UserQuizResult> getHistoryOfUser(@Param("userId") String userId);

    @Query("SELECT uqr FROM UserQuizResult uqr WHERE CAST(JSON_UNQUOTE(JSON_EXTRACT(uqr.exam, '$.title')) AS STRING) LIKE %:searchTerm% AND uqr.submitted = true AND uqr.user.id = :userId AND uqr.status = 'Active'")
    List<UserQuizResult> searchUserQuizResult(@Param("searchTerm") String searchTerm, @Param("userId")String userId);

    Integer countUserQuizResultByUserId(String userId);

    UserQuizResult getUserQuizResultByUserAndId(User user, Long id);

//    UserHistoryDetailResponse getHistoryDetail(Long id);
}
