package dev.idriz.planey.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void updatePassword_passwordIsNotPlainText() {
        Profile profile = new Profile();
        profile.updatePassword("hello");
        assertNotEquals(profile.getPassword(), "hello");
    }

    @Test
    void setPassword_passwordIsPlainText() {
        Profile profile = new Profile();
        profile.setPassword("hello");
        assertEquals(profile.getPassword(), "hello");
    }

}