package com.exam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @Size(min = 3, message = "Tên đăng nhập phải nhiều hơn 3 kí tự")
    private String fullName;

    private String email;

    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 kí tự")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Mật khẩu phải có ít nhất 1 kí tự viết hoa"),
            @Pattern(regexp = ".*[!@#$%^&*()].*", message = "Mật khẩu phải có ít nhất 1 kí tự đặc biệt")
    })
    private String password;
    
    private String firebaseId;
}
