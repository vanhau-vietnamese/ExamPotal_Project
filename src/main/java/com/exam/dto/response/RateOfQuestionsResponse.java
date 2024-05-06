package com.exam.dto.response;

import com.exam.helper.QuestionObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RateOfQuestionsResponse {
    private QuestionObject question;
    private double correctPercentage;
    private double incorrectPercentage;

    public RateOfQuestionsResponse(QuestionObject question, double correctPercentage, double incorrectPercentage) {
        this.question = question;
        this.correctPercentage = correctPercentage;
        this.incorrectPercentage = incorrectPercentage;
    }
}
