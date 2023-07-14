package dev.idriz.planey.security.filter;

import dev.idriz.planey.security.user.ProfileUserDetails;
import dev.idriz.planey.service.ProfileService;
import dev.idriz.planey.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ProfileService profileService;

    public JwtAuthenticationFilter(@NotNull TokenService tokenService, @NotNull ProfileService profileService) {
        this.tokenService = tokenService;
        this.profileService = profileService;
    }

    private boolean isBearerToken(String token) {
        return token.startsWith("Bearer ") && !token.substring(7).isBlank();
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, """
                {
                    "errorMessage": "No authorization header provided."
                }
                """);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!isBearerToken(header)) {
            filterChain.doFilter(request, response);
            return;
        }
        var token = header.substring(7);
        var idOptional = tokenService.parseToken(token);
        if (idOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        var id = idOptional.get();
        profileService.getProfile(id).ifPresentOrElse(profile -> {
            var userDetails = new ProfileUserDetails(profile);
            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, null
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            try {
                filterChain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }, () -> {
            try {
                sendUnauthorizedResponse(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
