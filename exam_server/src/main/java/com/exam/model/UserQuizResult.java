package com.exam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_quiz_results")
public class UserQuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "marks", nullable = true)
    private int marks;

    @Column(name = "capture")
    @OneToMany(mappedBy = "userQuizResult", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserQuestionResult> capture;

    @Column(name = "startTime")
    private Timestamp startTime;
    @Column(name = "submitTime")
    private Timestamp submitTime;
    @Column(name = "duration")
    private String durationTime;
    @Column(name = "numberOfCorrect")
    private Integer numberOfCorrect;
    @Column(name = "numberOfIncorrect")
    private Integer numberOfIncorrect;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "quizId")
    private Quiz quiz;

    public String calculateDuration(Timestamp startTime, Timestamp submitTime) {
        if (startTime != null && submitTime != null) {
            Duration duration = Duration.between(startTime.toInstant(), submitTime.toInstant());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return null;
        }
    }
}
