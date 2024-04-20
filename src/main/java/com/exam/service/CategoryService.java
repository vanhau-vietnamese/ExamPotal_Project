package com.exam.service;

import com.exam.dto.request.CategoryRequest;
import com.exam.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryService {
    public Category getCategory(Long id);
    public ResponseEntity<?> addCategory(CategoryRequest categoryRequest);
    public ResponseEntity<?> getAllCategories();
    public ResponseEntity<?> updateCategory(Long id, CategoryRequest categoryRequest);
    ResponseEntity<?> deleteCategory(Long id);

    ResponseEntity<?> searchCategories(Map<String, String> request);
}
