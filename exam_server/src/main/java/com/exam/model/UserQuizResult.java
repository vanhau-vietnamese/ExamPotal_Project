package com.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "user_quiz_results")
public class UserQuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "marks", nullable = true)
    private int marks;
    @Column(name = "capture", columnDefinition = "JSON")
    private String capture;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "quizId")
    private Quiz quiz;
}
