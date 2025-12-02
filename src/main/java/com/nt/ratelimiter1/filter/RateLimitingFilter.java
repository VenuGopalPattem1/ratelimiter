package com.nt.ratelimiter1.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nt.ratelimiter1.core.RateLimiterService;
import com.nt.ratelimiter1.util.RateLimitKeyBuilder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;
    private final RateLimitKeyBuilder keyBuilder;

    public RateLimitingFilter(RateLimiterService rateLimiterService,
                              RateLimitKeyBuilder keyBuilder) {
        this.rateLimiterService = rateLimiterService;
        this.keyBuilder = keyBuilder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String userId = extractUserIdFromSecurityContext(); // TODO: integrate auth
        String key = keyBuilder.buildKey(request, userId);

        if (!rateLimiterService.isAllowed(key)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                      "status": 429,
                      "error": "Too Many Requests",
                      "message": "Rate limit exceeded. Please try again later."
                    }
                    """);
            return;
        }

        // If allowed -> proceed to next filter / controller
        filterChain.doFilter(request, response);
    }

    private String extractUserIdFromSecurityContext() {
        // TODO: Replace with actual logic
        // Example if using Spring Security:
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // if (auth != null && auth.isAuthenticated()) {
        //    return auth.getName(); // or from JWT claims
        // }
        return null; // Treat as anonymous for now
    }
}