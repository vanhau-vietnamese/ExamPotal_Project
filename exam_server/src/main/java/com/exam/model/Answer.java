package com.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
@jdk.jfr.Timestamp
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "media", columnDefinition = "JSON")
    private String media;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "isCorrect",nullable = false)
    private boolean isCorrect;
    @Column(name = "status", columnDefinition = "TEXT")
    private String status;
    @Column(name = "createAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createAt ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question", referencedColumnName = "id")
    private Question question;
}
