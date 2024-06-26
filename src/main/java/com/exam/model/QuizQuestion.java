package com.exam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quizId")
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @Column(name = "marksOfQuestion")
    private Integer marksOfQuestion ;

}
