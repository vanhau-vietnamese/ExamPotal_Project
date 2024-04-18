package com.exam.repository;

import com.exam.enums.EStatus;
import com.exam.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitle(String title);
    void deleteById(Long id);
    List<Category> findAllByStatus(EStatus status);
}
