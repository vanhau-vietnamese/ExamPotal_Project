package com.exam.model;


import com.exam.convert.AnswerToChooseConverter;
import com.exam.convert.GenerateQuestionConverter;
import com.exam.convert.JsonConverter;
import com.exam.convert.QuestionConverter;
import com.exam.dto.response.QuestionResponse;
import com.exam.helper.QuestionObject;
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

    @ManyToOne
    private UserQuizResult userQuizResult;

    @Column(name = "answers", columnDefinition = "JSON")
    @Convert(converter = AnswerToChooseConverter.class)
    private AnswersToChoose answers;

    @Column(name = "question", columnDefinition = "JSON")
    @Convert(converter = QuestionConverter.class)
    private QuestionObject question;

    @Column(name = "generateQuestion", columnDefinition = "JSON")
    @Convert(converter = GenerateQuestionConverter.class)
    private QuestionResponse generateQuestion;
}
