package com.exam.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class CommonUtils {
    public static boolean isUserExists(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true; // Người dùng đã tồn tại
        } catch (FirebaseAuthException e) {
            return false;
        }
    }
}

