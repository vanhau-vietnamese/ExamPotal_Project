package com.exam.controller;

import com.exam.dto.request.AnswerRequest;
import com.exam.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3005/")
@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    @PostMapping("/add")
    public ResponseEntity<?> addAnswer(@RequestBody AnswerRequest answerRequest){
        return answerService.addAnswer(answerRequest);
    }
}
