package com.exam.convert;

import com.exam.helper.QuestionObject;

public class QuestionConverter implements JsonConverter<QuestionObject>{
    @Override
    public Class<QuestionObject> getType() {
        return QuestionObject.class;
    }
}
