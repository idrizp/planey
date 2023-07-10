package dev.idriz.planey.service;

import dev.idriz.planey.PlaneyApplication;
import dev.idriz.planey.model.Profile;
import dev.idriz.planey.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static dev.idriz.planey.string.StringValidator.requireLength;
import static dev.idriz.planey.string.StringValidator.requireValidEmail;

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
     * @param passportNumber The passport number of the user.
     * @param nationality    The nationality of the user.
     * @return The newly created profile.
     */
    public Profile createNewProfile(
            String firstName,
            String lastName,

            String email,
            String passportNumber,

            String nationality
    ) {
        Profile profile = new Profile();

        if (profileRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }


        // Validation checks.
        requireValidEmail(email, "Email is not valid");
        requireLength(firstName, 2, "First name must be at least 2 characters long");
        requireLength(lastName, 2, "Last name must be at least 2 characters long");
        requireLength(email, 5, "Email must be at least 5 characters long");
        requireLength(passportNumber, 5, "Passport number must be at least 5 " +
                "characters long");

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setEmail(email);
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
