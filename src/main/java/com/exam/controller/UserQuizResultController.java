package com.exam.controller;

import com.exam.model.UserQuizResult;
import com.exam.service.UserQuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class UserQuizResultController {
    private final UserQuizResultService userQuizResultService;
    @GetMapping("/")
    public ResponseEntity<?> getHistoryOfUser(){
        return userQuizResultService.getHistoryOfUser();
    }

}
