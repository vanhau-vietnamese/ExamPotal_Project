package com.exam.model;

import com.exam.convert.AnswersToChooseConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.JsonObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "user_question_result")
public class UserQuestionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int markOfQuestion;
    private boolean result;
    private boolean isDone;

    private String questionContent;

    @ManyToOne
    @JsonBackReference
    private UserQuizResult userQuizResult;

    @Column(name = "answersToChoose", columnDefinition = "JSON")
    @Convert(converter = AnswersToChooseConverter.class)
    private AnswersToChoose answersToChoose;
}
