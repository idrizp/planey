package dev.idriz.planey.security;

import dev.idriz.planey.model.Profile;
import dev.idriz.planey.security.user.ProfileUserDetails;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    @NonNull
    public static Profile getAuthenticatedProfile() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ProfileUserDetails) {
            return ((ProfileUserDetails) principal).getProfile();
        } else {
            throw new IllegalStateException("The principal is not an instance of ProfileUserDetails.");
        }
    }
}
