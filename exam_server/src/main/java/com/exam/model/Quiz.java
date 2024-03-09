package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "maxMarks", nullable = false)
    private int maxMarks;
    @Column(name = "numberOfQuestions", nullable = false)
    private int numberOfQuestions;
    @Column(name = "status")
    private boolean status = false;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "createBy", referencedColumnName = "id")
    private User createBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "quiz")
    private Set<UserQuizResult> userQuizResults = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    Set<QuizQuestion> quizQuestions;
}
