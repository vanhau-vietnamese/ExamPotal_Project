package com.exam.dto.request;

import com.exam.model.user.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private boolean enable = true;
}
