package com.exam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;
    @Column(nullable = false)
    private Integer otp;
    @Column(nullable = false)
    private Timestamp expirationTime;
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
