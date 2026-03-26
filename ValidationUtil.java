package com.hotel.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class ValidationUtil {
    
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[0-9]{10}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

    /**
     * Validate phone format
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phonePattern.matcher(phone).matches();
    }

    /**
     * Validate if string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate if number is positive
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /**
     * Validate if number is non-negative
     */
    public static boolean isNonNegative(double number) {
        return number >= 0;
    }

    /**
     * Validate integer input
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate double input
     */
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
