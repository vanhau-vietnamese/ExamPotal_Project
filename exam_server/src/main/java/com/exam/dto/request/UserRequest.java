package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private String firebaseId;
}
