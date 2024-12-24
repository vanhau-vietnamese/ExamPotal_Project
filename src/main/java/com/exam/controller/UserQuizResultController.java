package com.exam.controller;

import com.exam.service.UserQuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class UserQuizResultController {
    private final UserQuizResultService userQuizResultService;
    @GetMapping("/")
    public ResponseEntity<?> getHistoryOfUser(){
        return userQuizResultService.getHistoryOfUser();
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchUserQuizResult(@RequestBody Map<String, String> searchRequest){
        return userQuizResultService.searchUserQuizResult(searchRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserQuizResult(@PathVariable("id") Long id){
        return userQuizResultService.deleteUserQuizResult(id);
    }
}
