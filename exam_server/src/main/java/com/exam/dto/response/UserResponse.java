package com.exam.dto.response;

import com.exam.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String username;
    private String password;
    private String email;
    Role role;

    public UserResponse(String name, String username, String password, String email, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}
