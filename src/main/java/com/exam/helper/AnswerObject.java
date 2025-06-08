package com.exam.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerObject {
    private Long id;
    private String media;
    private String content;
    private boolean isSelect;
    private boolean isCorrect;
}
