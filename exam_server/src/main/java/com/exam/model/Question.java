package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "media", columnDefinition = "JSON")
    private String media;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private String status ;
    @Column(name = "marksOfQuestion")
    private Integer marksOfQuestion ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("questions")
    @JoinColumn(name = "questionType", referencedColumnName = "alias")
    private QuestionType questionType;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt  = new Timestamp(System.currentTimeMillis());

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore 
    private Set<QuizQuestion> quizQuestions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Answer> answers = new HashSet<>();

}
