package dev.idriz.planey.service;

import dev.idriz.planey.model.Profile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final String jwtSecret;
    private final Key jwtSecretKey;

    private final JwtParser parser;

    public TokenService(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(jwtSecretKey).build();
    }

    /**
     * Creates a JWT token for the given profile.
     *
     * @param profile  The profile to create the token for.
     * @param issuedAt The time the token was issued at.
     * @return The JWT token.
     */
    public String createToken(Profile profile, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        // Create a JWT token using JJWT.
        return Jwts.builder()
                .setSubject(profile.getProfileId().toString())
                .setIssuedAt(Date.from(issuedAt.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expiresAt.toInstant(ZoneOffset.UTC)))
                .addClaims(Map.of(
                        "role", profile.getRole().name(),
                        "email", profile.getEmail(),
                        "firstName", profile.getFirstName(),
                        "lastName", profile.getLastName()
                ))
                .signWith(jwtSecretKey)
                .compact();
    }

    /**
     * Creates a JWT token for the given profile.
     *
     * @param profile The profile to create the token for.
     * @return The JWT token.
     */
    public String createToken(Profile profile) {
        return createToken(profile, LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.DAYS));
    }

    /**
     * Parses the given token and returns the profile ID.
     *
     * @param tokenProvided The token to parse.
     * @return The profile ID, or null.
     */
    public Optional<UUID> parseToken(String tokenProvided) {
        try {
            Jws<Claims> claims = parser.parseClaimsJws(tokenProvided);
            return Optional.of(UUID.fromString(claims.getBody().get("sub", String.class)));
        } catch (Exception exception) {
            System.err.println("Failed to parse token: " + exception.getMessage());
            return Optional.empty();
        }
    }

}
