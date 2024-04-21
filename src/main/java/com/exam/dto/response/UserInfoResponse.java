package com.exam.dto.response;

import com.exam.enums.ERole;
import com.exam.enums.EStatus;
import com.exam.model.Category;
import com.exam.model.Quiz;
import com.exam.model.UserQuizResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class UserInfoResponse {
    private String id;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private ERole role;
    private String firebaseId;
    private Timestamp createdAt;
    private String createBy;

    public UserInfoResponse(String id, String fullName, String email, ERole role, String firebaseId, Timestamp createdAt, String createBy) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.firebaseId = firebaseId;
        this.createdAt = createdAt;
        this.createBy = createBy;
    }
}
