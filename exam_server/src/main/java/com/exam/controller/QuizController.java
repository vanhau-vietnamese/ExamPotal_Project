package com.exam.controller;

import com.exam.dto.request.QuizRequest;
import com.exam.model.Quiz;
import com.exam.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    @PostMapping("/add")
    public ResponseEntity<?> addQuiz(@RequestBody QuizRequest quizRequest){
        return quizService.addQuiz(quizRequest);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable("id")Long id){
        return quizService.getQuiz(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id")Long id){
        return quizService.deleteQuiz(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable("id")Long id, @RequestBody QuizRequest quizRequest){
        return quizService.updateQuiz(id, quizRequest);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getQuizzesOfCategory(@PathVariable("categoryId")Long categoryId){
        return quizService.getQuizzesOfCategory(categoryId);
    }

}
