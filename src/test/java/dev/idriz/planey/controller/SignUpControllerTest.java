package dev.idriz.planey.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void signUp_givenValidInformation_shouldReturnCreatedWithToken() throws Exception {
        mockMvc.perform(
                        post("/api/public/v1/sign-up")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "email": "bobross@gmail.com",
                                          "password": "password",
                                          "firstName": "Bob",
                                          "lastName": "Ross",
                                          "passportNumber": "12345678",
                                          "nationality": "American"
                                        }
                                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void signUp_givenInvalidInformation_shouldReturnError() throws Exception {
        mockMvc.perform(
                        post("/api/public/v1/sign-up")
                                .contentType("application/json")
                                .content("""
                                        {
                                          "email": "bobrossgmail.com",
                                          "password": "password",
                                          "firstName": "Bob",
                                          "lastName": "Ross",
                                          "passportNumber": "12345678",
                                          "nationality": "American"
                                        }
                                        """))
                .andExpect(status().isBadRequest());
    }
}
