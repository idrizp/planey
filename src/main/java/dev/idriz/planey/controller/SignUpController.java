package dev.idriz.planey.controller;

import dev.idriz.planey.model.Profile;
import dev.idriz.planey.model.dto.AuthenticationToken;
import dev.idriz.planey.service.ProfileService;
import dev.idriz.planey.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/v1/sign-up")
public class SignUpController {

    private final ProfileService profileService;
    private final TokenService tokenService;

    public SignUpController(ProfileService profileService, TokenService tokenService) {
        this.profileService = profileService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            Profile profile = profileService.createNewProfile(
                    signUpRequest.firstName(),
                    signUpRequest.lastName(),
                    signUpRequest.email(),
                    signUpRequest.password(),
                    signUpRequest.passportNumber(),
                    signUpRequest.nationality()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationToken(
                    tokenService.createToken(profile)
            ));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new Error(exception.getMessage()));
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Error(exception.getMessage()));
        }
    }

    /**
     * The request body for the sign-up endpoint.
     *
     * @param email          The email of the user.
     * @param password       The password of the user.
     * @param firstName      The first name of the user.
     * @param lastName       The last name of the user.
     * @param nationality    The nationality of the user.
     * @param passportNumber The passport number of the user.
     */
    public record SignUpRequest(
            @Email String email,
            @Size(min = 8, max = 64) String password,
            @Size(min = 2, max = 32) String firstName,
            @Size(min = 2, max = 32) String lastName,
            @Size(min = 2, max = 32) String nationality,
            @Size(min = 8, max = 64) String passportNumber
    ) {

    }

}
