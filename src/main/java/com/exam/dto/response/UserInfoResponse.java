package com.exam.dto.response;

import com.exam.enums.ERole;
import com.exam.model.Quiz;
import com.exam.model.UserQuizResult;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserInfoResponse {
    private String id;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private ERole role;

    private int numberOfCompleted; // student
    private int numberOfPost; // teacher

    private Set<UserQuizResult> userQuizResults; // Lisst kết quả
    private Set<Quiz> postsOfTeacher;  // các bài teacher post
}
