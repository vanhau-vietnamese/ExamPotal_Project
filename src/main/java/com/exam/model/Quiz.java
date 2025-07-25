package com.exam.model;

import com.exam.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@DynamicUpdate
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
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
    @Column(name = "durationMinutes")
    private int durationMinutes;
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("quizzes")
    @JoinColumn(name = "createBy", referencedColumnName = "id")
    private User createBy;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("quizzes")
    @JoinColumn(name = "categoryId", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserQuizResult> userQuizResults = new HashSet<>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<QuizQuestion> quizQuestions = new LinkedHashSet<>();
}
