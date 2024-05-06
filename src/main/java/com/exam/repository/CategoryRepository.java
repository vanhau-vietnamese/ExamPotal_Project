package com.exam.repository;

import com.exam.enums.EStatus;
import com.exam.model.Category;
import com.exam.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitleAndStatus(String title, EStatus status);
    void deleteById(Long id);
    List<Category> findAllByStatus(EStatus status);
    @Query("SELECT c FROM Category c WHERE c.title LIKE %:searchTerm% AND c.status = 'Active'")
    List<Category> searchCategories(@Param("searchTerm") String searchTerm);
}
