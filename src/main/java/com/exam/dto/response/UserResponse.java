package com.exam.dto.response;

import com.exam.enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class UserResponse {
    private String id;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private ERole role;
    private String firebaseId;
    private Timestamp createdAt;
    private String createBy;

    public UserResponse(String id, String fullName, String email, ERole role, String firebaseId, Timestamp createdAt, String createBy) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.firebaseId = firebaseId;
        this.createdAt = createdAt;
        this.createBy = createBy;
    }

    public UserResponse(String id, String fullName, String email, ERole role, String firebaseId, Timestamp createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.firebaseId = firebaseId;
        this.createdAt = createdAt;
    }
}
