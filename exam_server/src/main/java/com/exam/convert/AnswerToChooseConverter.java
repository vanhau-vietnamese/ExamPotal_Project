package com.exam.convert;

import com.exam.model.AnswersToChoose;

public class AnswerToChooseConverter implements JsonConverter<AnswersToChoose>{
    @Override
    public Class<AnswersToChoose> getType() {
        return AnswersToChoose.class;
    }
}
