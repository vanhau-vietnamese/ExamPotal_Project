package com.exam.service.impl;

import com.exam.dto.response.QuestionTypeResponse;
import com.exam.model.EQuestionType;
import com.exam.service.QuestionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService {
    @Override
    public ResponseEntity<?> getAllQuestionTypes() {
        List<QuestionTypeResponse> questionTypeResponses = Arrays.stream(EQuestionType.values())
                .map(questionType -> {
                    QuestionTypeResponse response = new QuestionTypeResponse();
                    response.setAlias(questionType.getAlias());
                    response.setDisplayName(questionType.getDisplayName());
                    return response;
                }) // Lấy ra tên của mỗi phần tử enum
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionTypeResponses);
    }
}
