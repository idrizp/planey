package dev.idriz.planey.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/v1/health")
public class HealthCheckController {

    @GetMapping
    public String health() {
        return "OK";
    }

}
