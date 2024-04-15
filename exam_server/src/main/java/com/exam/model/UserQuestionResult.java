package com.exam.model;


import com.exam.convert.AnswerToChooseConverter;
import com.exam.convert.JsonConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String question;

    @ManyToOne
    @JsonBackReference
    private UserQuizResult userQuizResult;

    @Column(name = "answers", columnDefinition = "JSON")
    @Convert(converter = AnswerToChooseConverter.class)
    private AnswersToChoose answersToChoose;
}
