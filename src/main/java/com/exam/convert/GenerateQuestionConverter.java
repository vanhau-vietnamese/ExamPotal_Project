package com.exam.convert;

import com.exam.dto.response.QuestionResponse;

public class GenerateQuestionConverter implements JsonConverter<QuestionResponse> {
    @Override
    public Class<QuestionResponse> getType() {
        return QuestionResponse.class;
    }
}
