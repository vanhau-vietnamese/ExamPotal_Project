package com.exam.controller;

import com.exam.dto.request.FilterCreateAtRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.request.QuestionTypeRequest;
import com.exam.model.Question;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Map;


@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest questionRequest){
        return questionService.addQuestion(questionRequest);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable("questionId") Long id){
        return questionService.getQuestion(id);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllQuestions(){
        System.out.println("getAllQuestions");
        return questionService.getAllQuestions();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editQuestion(@PathVariable("id") Long id, @RequestBody QuestionRequest questionRequest){
        return questionService.editQuestion(id, questionRequest);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id")Long id){
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quizId")Long id){
        return questionService.getQuestionsOfQuiz(id);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getQuestionsOfCategory(@PathVariable("categoryId")Long id){
        return questionService.getQuestionsOfCategory(id);
    }

    @PostMapping("/filter/questionType")
    public ResponseEntity<?> getQuestionOfQuestionType(@RequestBody QuestionTypeRequest request){
        return questionService.getQuestionsOfQuestionType(request);
    }

    @PostMapping("/filter/createAt")
    public ResponseEntity<?> getQuestionsOfCreateAt(@RequestBody FilterCreateAtRequest request){
        return questionService.getQuestionsOfCreateAt(request);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchQuestions(@RequestBody Map<String, String> searchRequest){
        return questionService.searchQuestions(searchRequest);
    }
}
