package com.exam.repository;

import com.exam.enums.EStatus;
import com.exam.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Set<Quiz> findAllByCreateBy(User user);
    // get quizzes from category
    @Query("SELECT quiz FROM Quiz quiz WHERE quiz.category.id= :category_id and quiz.status = 'Active'")
    Set<Quiz> getQuizzesOfCategory(@Param("category_id") Long category_id);

    Page<Quiz> findAllByStatus(EStatus status, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.status = 'Active'")
    List<Quiz> findAll();

    boolean existsQuizByCategory(Category category);

    // filter quiz by createat
    @Query("SELECT q FROM Quiz q WHERE q.status = 'Active' AND q.createdAt BETWEEN :fromTime AND :toTime")
    List<Quiz> getQuizzesByCreateAt(@Param("fromTime") Timestamp fromTime, @Param("toTime")Timestamp toTime);

    // search question from searchContent
    @Query("SELECT q FROM Quiz q WHERE q.title LIKE %:searchTerm% AND q.status = 'Active'")
    List<Quiz> searchQuizzes(@Param("searchTerm") String searchTerm);

    int countQuizzesByStatus(EStatus status);

    Integer countQuizzesByStatusAndCreateBy(EStatus status, User user);

    @Query("SELECT COUNT(q) FROM Quiz q WHERE YEAR(q.createdAt) = :year AND MONTH (q.createdAt) = :month AND q.status = 'Active'")
    int countQuizzesByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
