package dev.idriz.planey.controller;

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
