package com.exam.convert;

import com.exam.helper.ExamObject;

public class ExamObjectConverter implements JsonConverter<ExamObject>{
    @Override
    public Class<ExamObject> getType() {
        return ExamObject.class;
    }
}
