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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
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
    public ResponseEntity<Category> getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return ResponseEntity.ok(category);
    }
    @Override
    public ResponseEntity<?> addCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByTitle(categoryRequest.getTitle())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category already exists.");
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
        category.setStatus(EStatus.Active);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByStatus(EStatus.Active);
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục nào.");
        }
        return ResponseEntity.ok(categories);
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục.");
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            if (validateCategory(category)) {
                return ResponseEntity.badRequest().body("Delete failed. Because this category already exists in the quiz or the question");
            }
            category.setStatus(EStatus.Deleted);
            categoryRepository.save(category);
            return ResponseEntity.ok("Deleted Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @Override
    public ResponseEntity<?> searchCategories(Map<String, String> request) {
        return ResponseEntity.ok(categoryRepository.searchCategories(request.get("searchContent")));
    }

    private boolean validateCategory(Category category){
        return quizRepository.existsQuizByCategory(category) || questionRepository.existsQuestionByCategory(category);
    }

}
