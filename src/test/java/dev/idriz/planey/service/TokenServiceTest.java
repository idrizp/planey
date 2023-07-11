package dev.idriz.planey.service;

import dev.idriz.planey.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private Profile profile;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService("secret".repeat(10));
        profile = new Profile();

        profile.setProfileId(UUID.randomUUID());
        profile.setRole(Profile.Role.USER);
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setEmail("johndoe@gmail.com");
        profile.setNationality("American");
        profile.setPassportNumber("12345678");
        profile.updatePassword("password");
    }

    @Test
    void createToken_givenValidProfile_returnsString() {
        String token = tokenService.createToken(profile);
        assertNotNull(token);
    }

    @Test
    void parseToken_givenValidToken_returnId() {
        String token = tokenService.createToken(profile);
        assertNotNull(tokenService.parseToken(token).orElse(null));
    }

    @Test
    void parseToken_givenExpiredToken_returnNull() {
        String token = tokenService.createToken(profile,
                LocalDateTime.of(1972, 1, 2, 0, 0),
                LocalDateTime.of(1972, 1, 1, 0, 0)
        );
        assertNull(tokenService.parseToken(token).orElse(null));
    }

    @Test
    void parseToken_givenInvalidToken_returnNull() {
        assertNull(tokenService.parseToken("asdfasdfasdfasdfa").orElse(null));
    }
}