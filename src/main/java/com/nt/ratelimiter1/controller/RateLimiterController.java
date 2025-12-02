package com.nt.ratelimiter1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {
    
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, you are within the rate limit!";
    }
}
