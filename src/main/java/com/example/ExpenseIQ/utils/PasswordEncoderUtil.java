package com.example.ExpenseIQ.utils;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    private static final PasswordEncoderUtil encoder = new PasswordEncoderUtil();

    public static String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    /**
     * Encode raw password before saving to DB
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Match raw password with encoded password stored in DB
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
