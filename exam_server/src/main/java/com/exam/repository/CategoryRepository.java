package com.exam.repository;

import com.exam.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public boolean existsByTitle(String title);
}
