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
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questId;
    @Lob
    @Column(name = "media_content")
    private byte[] mediaContent;
    @Column(name = "text_content")
    private String textContent;
    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionType questionType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
}
