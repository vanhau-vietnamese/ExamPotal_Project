package com.exam.dto.response;

import com.exam.model.ERole;
import com.exam.model.Quiz;
import com.exam.model.UserQuizResult;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private ERole role;

    private int numberOfCompleted; // student
    private int numberOfPost; // teacher

    private Set<UserQuizResult> userQuizResults; // Lisst kết quả
    private Set<Quiz> postsOfTeacher; // các bài teacher post
}
