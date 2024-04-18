package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionChoiceRequest {
    private Long questionId;
    private List<Long> selectedOptions = new ArrayList<>();
}
