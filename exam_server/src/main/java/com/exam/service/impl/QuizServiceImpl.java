package com.exam.service.impl;

import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.QuizRequest;
import com.exam.model.Category;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.repository.CategoryRepository;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserRepository;
import com.exam.service.QuizService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizRepository.findAll());
    }

    @Override
    public ResponseEntity<?> addQuiz(QuizRequest quizRequest) {
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setMaxMarks(quizRequest.getMaxMarks());
        quiz.setCreateBy(user);
        quiz.setDurationMinutes(quizRequest.getDurationMinutes());
        quiz.setStatus(quiz.isStatus());
        quiz.setNumberOfQuestions(quizRequest.getNumberOfQuestions());

        Category category = categoryRepository.findById(quizRequest.getCategoryId()).get();
        quiz.setCategory(category);

        return ResponseEntity.ok(quizRepository.save(quiz));
    }

    @Override
    public ResponseEntity<?> getQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()){
            return ResponseEntity.ok(quiz.get());
        }
        return ResponseEntity.badRequest().body("NOT FOUND QUIZ");
    }

    @Override
    public ResponseEntity<?> deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()){
            quizRepository.delete(quiz.get());
            return ResponseEntity.ok("Deleted Quiz Successfully");
        }
        return ResponseEntity.badRequest().body("NOT FOUND QUIZ");
    }

    @Override
    public ResponseEntity<?> updateQuiz(Long id, QuizRequest quizRequest) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if(quizOptional.isPresent()){
            Category category = categoryRepository.findById(quizRequest.getCategoryId()).get();

            // get jwt from request
            String jwt = jwtAuthenticationFilter.getJwt();
            FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
            String email = decodedToken.getEmail();
            User user = userRepository.findByEmail(email);

            Quiz quiz = quizOptional.get();
            quiz.setTitle(quizRequest.getTitle());
            quiz.setDescription(quizRequest.getDescription());
            quiz.setMaxMarks(quizRequest.getMaxMarks());
            quiz.setStatus(quizRequest.isStatus());
            quiz.setCategory(category);
            quiz.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            quiz.setNumberOfQuestions(quizRequest.getNumberOfQuestions());
            quiz.setCreateBy(user);

            return ResponseEntity.ok(quizRepository.save(quiz));
        }
        return ResponseEntity.badRequest().body("NOT FOUND QUIZ");
    }

    @Override
    public ResponseEntity<?> getQuizzesOfCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)){
            return ResponseEntity.ok(quizRepository.getQuizzesOfCategory(categoryId));
        }
        return ResponseEntity.badRequest().body("Not Found Category");
    }
}
