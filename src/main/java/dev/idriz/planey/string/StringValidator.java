package dev.idriz.planey.string;

import java.util.regex.Pattern;

/**
 * A utility class for validating strings
 */
public class StringValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Checks if the string is null or empty
     *
     * @param string       the string to check
     * @param length       the length of the string
     * @param errorMessage the error message to throw if the string is null or empty
     */
    public static void requireLength(String string, int length, String errorMessage) {
        if (string.length() < length) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Checks if the string is an email
     * @param email the string to check
     * @return true if the string is a valid email
     */
    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if the string is an email
     * @param email the string to check
     * @param errorMessage the error message to throw if the string is not a valid email
     */
    public static void requireValidEmail(String email, String errorMessage) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
