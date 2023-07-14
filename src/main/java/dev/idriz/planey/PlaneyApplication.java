package dev.idriz.planey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PlaneyApplication {

    // Perhaps switch this to argon2 in the future.
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(PlaneyApplication.class, args);
    }

}
