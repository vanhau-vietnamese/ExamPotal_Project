package com.exam.repository;

import com.exam.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public boolean existsByTitle(String title);
    public void deleteById(Long id);
}
