package dev.idriz.planey.service;

import dev.idriz.planey.model.Profile;
import dev.idriz.planey.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileServiceTest {

    @Autowired
    private ProfileRepository profileRepository;

    private ProfileService profileService;
    private Profile profile;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService(profileRepository);
        profile = profileService.createNewProfile(
                "bob",
                "ross",
                "bobrossconsulting@gmail.com", "password",
                "P0299298594",
                "Kosovar"
        );
    }


    @Test
    void createNewProfile_incorrectInput_throwsError() {
        assertThrows(IllegalArgumentException.class, () -> {
            profileService.createNewProfile(
                    "a",
                    "b",
                    "c",
                    "d",
                    "e",
                    "f"
            );
        });
    }

    @Test
    void createNewProfile_validInput_returnsProfile() {
        assertNotNull(profile);
    }

    @Test
    void getProfile_invalidProfile_returnsEmptyOptional() {
        assertTrue(profileService.getProfile(UUID.randomUUID()).isEmpty());
    }

    @Test
    void getProfile_validProfile_returnsOptionalOfProfile() {
        assertTrue(profileService.getProfile(profile.getProfileId()).isPresent());
    }

    @Test
    void comparePassword_givenInvalidProfile_throwsError() {
        assertThrows(IllegalArgumentException.class, () -> {
            profileService.comparePassword(UUID.randomUUID(), "password");
        });
    }


    @Test
    void comparePassword_givenValidProfileWithValidPassword_returnsTrue() {
        assertTrue(profileService.comparePassword(profile.getProfileId(), "password"));
    }

    @Test
    void comparePassword_givenValidProfileWithInvalidPassword_returnsFalse() {
        assertFalse(profileService.comparePassword(profile.getProfileId(), "password123"));
    }

    @Test
    void deleteProfile_givenValidProfile_returnsTrue() {
        assertTrue(profileService.deleteProfile(profile.getProfileId()));
    }

    @Test
    void deleteProfile_givenInvalidProfile_returnsFalse() {
        assertFalse(profileService.deleteProfile(UUID.randomUUID()));
    }
}