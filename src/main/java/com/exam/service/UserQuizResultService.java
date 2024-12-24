package com.exam.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserQuizResultService {
    ResponseEntity<?> getHistoryOfUser();

    ResponseEntity<?> searchUserQuizResult(Map<String, String> searchRequest);

    ResponseEntity<?> deleteUserQuizResult(Long id);
}
