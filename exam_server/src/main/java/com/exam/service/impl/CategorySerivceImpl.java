package com.exam.service.impl;

import com.exam.model.Category;
import com.exam.repository.CategoryRepository;
import com.exam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategorySerivceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Category> addCategory(Category category) {
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(categoryRepository.findAll());
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
