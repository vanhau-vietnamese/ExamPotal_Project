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
    private Long quizId;
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "quizId", referencedColumnName = "id", insertable = false, updatable = false)
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id", insertable = false, updatable = false)
    private Question question;

    @Column(name = "marksOfQuestion")
    private Integer marksOfQuestion ;

}
