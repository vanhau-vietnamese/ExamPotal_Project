package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTypeId;
    private String displayName;

    @JsonIgnore
    @OneToMany(mappedBy = "questionType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Question> questions = new LinkedHashSet<>();
}
