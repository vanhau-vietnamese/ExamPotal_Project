package com.exam.service;

import com.exam.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CategoryService {
    public ResponseEntity<Category> addCategory(Category category);
    public Category updateCategory(Category category);
    public Category getCategory(Long categoryId);
    public Set<Category> getCategories();
    public void deleteCategory(Long categoryId);
}
