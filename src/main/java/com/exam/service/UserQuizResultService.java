package com.exam.service;

import com.exam.dto.response.UserHistoryDetailResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface UserQuizResultService {
    ResponseEntity<?> getHistoryOfUser();

    ResponseEntity<?> searchUserQuizResult(Map<String, String> searchRequest);

    ResponseEntity<?> deleteUserQuizResult(Long id);

    UserHistoryDetailResponse detail(Long id) throws IOException;
}
