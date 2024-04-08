package com.exam.repository;

import com.exam.model.EStatus;
import com.exam.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.category.id = :categoryId")
    List<Question> getQuestionsOfCategory(@Param(("categoryId"))Long categoryId);

    List<Question> findAllByStatus(EStatus status);
}
