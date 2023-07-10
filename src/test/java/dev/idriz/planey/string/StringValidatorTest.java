package dev.idriz.planey.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    @Test
    void requireLength_validLengthProvided_succeeds() {
        assertDoesNotThrow(() -> StringValidator.requireLength("test", 2, "error"));
    }

    @Test
    void requireLength_invalidLengthProvided_throwsError() {
        assertThrows(IllegalArgumentException.class, () -> StringValidator.requireLength("t", 2, "error"));
    }

    @Test
    void isEmailValid_invalidEmailProvided_returnsFalse() {
        assertFalse(StringValidator.isEmailValid("test"));
    }

    @Test
    void requireValidEmail_validEmailProvided_succeds() {
        assertDoesNotThrow(() -> StringValidator.requireValidEmail("email@email.com", "error"));
    }

    @Test
    void requireValidEmail_invalidEmailProvided_throwsError() {
        assertThrows(IllegalArgumentException.class, () -> StringValidator.requireValidEmail("emailaemail.com", "error"));
    }
}