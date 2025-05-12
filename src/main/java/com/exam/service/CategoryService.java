package com.exam.service;

import com.exam.dto.request.CategoryRequest;
import com.exam.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryService {
    ResponseEntity<Category> getCategory(Long id);
    ResponseEntity<?> addCategory(CategoryRequest categoryRequest);
    ResponseEntity<?> getAllCategories();
    ResponseEntity<?> updateCategory(Long id, CategoryRequest categoryRequest);
    ResponseEntity<?> deleteCategory(Long id);

    ResponseEntity<?> searchCategories(Map<String, String> request);
}
