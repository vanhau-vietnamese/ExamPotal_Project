package com.exam.service;

import com.exam.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CategoryService {
    public Category getCategory(Long id);
    public Category addCategory(Category category);
    public ResponseEntity<?> getAllCategories();
}
