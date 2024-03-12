package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String fullName;
    private String firebaseId;
    private String password;
    private String email;
}
