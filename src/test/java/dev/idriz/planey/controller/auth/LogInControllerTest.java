package dev.idriz.planey.controller.auth;

import dev.idriz.planey.service.ProfileService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class LogInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired ProfileService profileService) {
        profileService.createNewProfile(
                "John",
                "Wick",
                "johnwick@gmail.com",
                "password",
                "12345678",
                "American"
        );
    }

    @Test
    void logIn_givenValidInformation_shouldReturnCreatedWithToken() throws Exception {
        mockMvc.perform(
                        post("/api/public/v1/log-in")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "email": "johnwick@gmail.com",
                                          "password": "password"
                                        }
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void logIn_givenInvalidInformation_shouldReturnError() throws Exception {
        mockMvc.perform(
                        post("/api/public/v1/log-in")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "email": "johnwick@gmail.com",
                                          "password": "password123"
                                        }
                                        """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logIn_givenNonExistingAccount_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(
                        post("/api/public/v1/log-in")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "email": "johnwick2@gmail.com",
                                          "password": "password"
                                        }
                                        """))
                .andExpect(status().isUnauthorized());
    }

}
