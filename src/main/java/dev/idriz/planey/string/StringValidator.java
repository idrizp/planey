package dev.idriz.planey.string;

import java.util.regex.Pattern;

/**
 * A utility class for validating strings
 */
public class StringValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Ensures that the string is of at least a certain length.
     *
     * @param string       the string to check
     * @param length       the length of the string
     * @param errorMessage the error message to throw if the string's length is below the threshold.
     */
    public static void requireLength(String string, int length, String errorMessage) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        if (string.length() < length) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Ensures that the string is of a length that falls within the interval provided.
     * @param string The string to check.
     * @param minLength The minimum length of the string.
     * @param maxLength The maximum length of the string.
     * @param minErrorMessage The error message to throw if the string's length is below the minimum threshold.
     * @param maxErrorMessage The error message to throw if the string's length is above the maximum threshold.
     */
    public static void requireBoundedLength(String string, int minLength, int maxLength,
                                            String minErrorMessage, String maxErrorMessage) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        if (string.length() < minLength) {
            throw new IllegalArgumentException(minErrorMessage);
        }
        if (string.length() > maxLength) {
            throw new IllegalArgumentException(maxErrorMessage);
        }
    }

    /**
     * Checks if the string is an email
     *
     * @param email the string to check
     * @return true if the string is a valid email
     */
    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if the string is an email
     *
     * @param email        the string to check
     * @param errorMessage the error message to throw if the string is not a valid email
     */
    public static void requireValidEmail(String email, String errorMessage) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
