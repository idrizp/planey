package dev.idriz.planey.repository;

import dev.idriz.planey.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The profile repository
 */
public interface ProfileRepository extends CrudRepository<Profile, UUID> {

    /**
     * Find a profile by its email
     *
     * @param email the email
     * @return An optional profile
     */
    Optional<Profile> findByEmail(String email);

    /**
     * Find a profile by its passport number
     *
     * @param passportNumber the passport number
     * @return An optional profile
     */
    Optional<Profile> findByPassportNumber(String passportNumber);

}
