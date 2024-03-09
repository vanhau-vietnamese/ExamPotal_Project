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
        return categoryRepository.findById(id).get();
    }
    @Override
    public Category addCategory(Category category) {
        if(categoryRepository.existsByTitle(category.getTitle())){
            return null;
        }
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        System.out.println("ads:" + jwt);
        String email = jwtUtils.extractUserName(jwt);
        System.out.println("email:" + email);
        User user = userRepository.findByEmail(email);
        System.out.println("user:" + user);
        category.setCreateBy(user);
        return categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
