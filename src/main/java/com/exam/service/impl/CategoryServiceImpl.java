package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.CategoryRequest;
import com.exam.enums.EStatus;
import com.exam.model.Category;
import com.exam.model.User;
import com.exam.repository.CategoryRepository;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserRepository;
import com.exam.service.CategoryService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    @Override
    public ResponseEntity<?> addCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByTitle(categoryRequest.getTitle())){
            throw new RuntimeException("Category Existed.");
        }
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        Category category = new Category();
        category.setCreateBy(user);
        category.setTitle(categoryRequest.getTitle());
        category.setDescription(categoryRequest.getDescription());

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAllByStatus(EStatus.Active));
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setTitle(categoryRequest.getTitle());
            category.get().setDescription(categoryRequest.getDescription());
            category.get().setCreatedAt(new Timestamp(System.currentTimeMillis()));
            // get jwt from request
            String jwt = jwtAuthenticationFilter.getJwt();
            FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
            String email = decodedToken.getEmail();
            User user = userRepository.findByEmail(email);
            category.get().setCreateBy(user);

            return ResponseEntity.ok(categoryRepository.save(category.get()));
        }
        throw new RuntimeException("Category Not Found");
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        if (validateCategory(category)){
            return ResponseEntity.badRequest().body("Delete failed. Because this category already exists in the quiz or the question");
        }
        category.setStatus(EStatus.Deleted);
        categoryRepository.save(category);

        return ResponseEntity.ok("Deleted Successfully");
    }

    @Override
    public ResponseEntity<?> searchCategories(Map<String, String> request) {
        return ResponseEntity.ok(categoryRepository.searchCategories(request.get("searchContent")));
    }

    private boolean validateCategory(Category category){
        return quizRepository.existsQuizByCategory(category) || questionRepository.existsQuestionByCategory(category);
    }

}
