package com.exam.repository;

import com.exam.enums.EStatus;
import com.exam.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    public Set<Quiz> findAllByCreateBy(User user);
    // get quizzes from category
    @Query("SELECT quiz FROM Quiz quiz WHERE quiz.category.id= :category_id")
    public Set<Quiz> getQuizzesOfCategory(@Param("category_id") Long category_id);

    List<Quiz> findAllByStatus(EStatus status);

    boolean existsQuizByCategory(Category category);
}
