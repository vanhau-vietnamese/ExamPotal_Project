package com.exam.validate;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateUser {
    //họ và tên phải ít nhất 3 ký tự
    public boolean validateFullName(String fullName) {
        return fullName.length() >= 3;
    }
    //mật khẩu phải ít nhất 8 ký tự
    public boolean validatePasswordLength(String password) {
        return password.length() >= 8;
    }
    //mật khẩu phải ít nhất 1 ký tự viết hoa và 1 ký tự đặc biệt
    public boolean validatePasswordComplexity(String password) {
        // Kiểm tra có ít nhất 1 ký tự viết hoa
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Matcher upperCaseMatcher = upperCasePattern.matcher(password);

        // Kiểm tra có ít nhất 1 ký tự đặc biệt
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);

        return upperCaseMatcher.find() && specialCharMatcher.find();
    }

}
