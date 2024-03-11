package com.exam.service;

import com.exam.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Set;

public interface CategoryService {
    public Category getCategory(Long id);
    public Category addCategory(Category category);
    public ResponseEntity<?> getAllCategories();
    public ResponseEntity<?> updateCategory(Long id, Category category);
}
