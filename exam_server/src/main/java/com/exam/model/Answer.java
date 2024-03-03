package com.exam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @Column(nullable = true)
    private byte[] mediaContent;
    @Column(nullable = true)
    private String textContent;
    @Column(nullable = true)
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;
}
