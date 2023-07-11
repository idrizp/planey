package dev.idriz.planey.service;

import dev.idriz.planey.PlaneyApplication;
import dev.idriz.planey.model.Profile;
import dev.idriz.planey.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static dev.idriz.planey.string.StringValidator.*;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Creates a new profile.
     *
     * @param firstName      The first name of the user.
     * @param lastName       The last name of the user.
     * @param email          The email of the user.
     * @param password       The password of the user.
     * @param passportNumber The passport number of the user.
     * @param nationality    The nationality of the user.
     * @return The newly created profile.
     */
    public Profile createNewProfile(
            String firstName,
            String lastName,

            String email,
            String password,

            String passportNumber,

            String nationality
    ) {
        Profile profile = new Profile();

        if (profileRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }


        // Validation checks.
        requireValidEmail(email, "Email is not valid");

        requireBoundedLength(firstName,
                2, 32,
                "First name must be at least 2 characters long",
                "First name must be at most 32 characters long"
        );
        requireBoundedLength(lastName,
                2, 32,
                "Last name must be at least 2 characters long",
                "Last name must be at most 32 characters long"
        );

        requireBoundedLength(password,
                8, 64,
                "Password must be at least 8 characters long",
                "Password must be at most 64 characters long"
        );

        requireBoundedLength(email,
                5, 256,
                "Email must be at least 5 characters long",
                "Email must be at most 256 characters long"
        );

        requireBoundedLength(passportNumber,
                5, 64,
                "Passport number must be at least 5 characters long",
                "Passport number must be at most 64 characters long"
        );

        profile.setFirstName(firstName);
        profile.setRole(Profile.Role.USER);
        profile.setLastName(lastName);
        profile.setEmail(email);
        profile.updatePassword(password);
        profile.setPassportNumber(passportNumber);
        profile.setNationality(nationality);

        profileRepository.save(profile);
        return profile;
    }

    /**
     * Returns a profile by its id.
     *
     * @param profileId The profile id.
     * @return The profile if it exists, empty otherwise.
     */
    public Optional<Profile> getProfile(UUID profileId) {
        return profileRepository.findById(profileId);
    }

    /**
     * Returns whether a profile's password matches with the plain text password.
     *
     * @param profileId The profile id.
     * @param password  The plaintext password.
     */
    public boolean comparePassword(UUID profileId, String password) {
        return getProfile(profileId)
                .map(profile -> PlaneyApplication.PASSWORD_ENCODER.matches(password, profile.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Profile does not exist"));
    }

    /**
     * Deletes a profile.
     *
     * @param profileId The profile id.
     * @return True if the profile was deleted, false otherwise.
     */
    public boolean deleteProfile(UUID profileId) {
        if (profileRepository.findById(profileId).isEmpty()) {
            return false;
        }
        profileRepository.deleteById(profileId);
        return true;
    }


}
