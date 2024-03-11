package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.model.Category;
import com.exam.model.User;
import com.exam.repository.CategoryRepository;
import com.exam.repository.UserRepository;
import com.exam.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    public Category getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }
    @Override
    public Category addCategory(Category category) {
        if(categoryRepository.existsByTitle(category.getTitle())){
            return null;
        }
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        String email = jwtUtils.extractUserName(jwt);
        User user = userRepository.findByEmail(email);

        category.setCreateBy(user);
        return categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, Category categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setTitle(categoryRequest.getTitle());
            category.get().setDescription(categoryRequest.getDescription());
            // get jwt from request
            String jwt = jwtAuthenticationFilter.getJwt();
            String email = jwtUtils.extractUserName(jwt);
            User user = userRepository.findByEmail(email);
            category.get().setCreateBy(user);

            return ResponseEntity.ok(categoryRepository.save(category.get()));
        }
        return null;
    }


}
