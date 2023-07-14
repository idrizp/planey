package dev.idriz.planey.controller.auth;

import dev.idriz.planey.PlaneyApplication;
import dev.idriz.planey.model.dto.AuthenticationTokenResponse;
import dev.idriz.planey.model.dto.ErrorResponse;
import dev.idriz.planey.service.ProfileService;
import dev.idriz.planey.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/v1/log-in")
public class LogInController {

    private final ProfileService profileService;
    private final TokenService tokenService;
    private final ResponseEntity<?> badCredentialsResponse = ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("Bad credentials."));

    public LogInController(ProfileService profileService, TokenService tokenService) {
        this.profileService = profileService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> logIn(@Valid @RequestBody LoginRequest loginRequest) {
        return profileService.getProfileByEmail(loginRequest.email())
                .map(profile -> {
                    if (!PlaneyApplication.PASSWORD_ENCODER.matches(loginRequest.password(), profile.getPassword())) {
                        return badCredentialsResponse;
                    }
                    var token = tokenService.createToken(profile);
                    return ResponseEntity.ok(new AuthenticationTokenResponse(token));
                })
                .orElse(badCredentialsResponse);
    }

    public record LoginRequest(
            @Email @NotBlank String email,
            @NotBlank String password
    ) {
    }

}
