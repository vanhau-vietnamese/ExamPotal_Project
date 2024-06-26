package com.exam.repository;

import com.exam.enums.EQuestionType;
import com.exam.enums.EStatus;
import com.exam.model.Category;
import com.exam.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.category.id = :categoryId and q.status = :status")
    List<Question> getQuestionsOfCategory(@Param("categoryId")Long categoryId, @Param("status")EStatus status);

    List<Question> findAllByStatus(EStatus status);

    boolean existsQuestionByCategory(Category category);
    List<Question> findQuestionsByQuestionType(EQuestionType questionType);

    // filter question from craeteAt
    @Query("SELECT q FROM Question q WHERE q.status = 'Active' and q.createdAt BETWEEN :fromTime AND :toTime")
    List<Question> getQuestionsByCreateAt(@Param("fromTime")Timestamp fromTime, @Param("toTime")Timestamp toTime);

    // search question from searchContent
    @Query("SELECT q FROM Question q WHERE q.content LIKE %:searchTerm% AND q.status = 'Active'")
    List<Question> searchQuestions(@Param("searchTerm") String searchTerm);

    int countQuestionsByStatus(EStatus status);
}
