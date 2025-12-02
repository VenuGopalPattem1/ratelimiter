package com.nt.ratelimiter1.core;

import org.springframework.stereotype.Service;

import com.nt.ratelimiter1.config.RateLimitRule;
import com.nt.ratelimiter1.config.RateLimiterProperties;

@Service
public class RateLimiterService {

    private final FixedWindowRedisRateLimiter fixedLimiter;
    private final TokenBucketRedisRateLimiter tokenLimiter;
    private final RateLimiterProperties properties;

    public RateLimiterService(FixedWindowRedisRateLimiter fixedLimiter,
                              TokenBucketRedisRateLimiter tokenLimiter,
                              RateLimiterProperties properties) {
        this.fixedLimiter = fixedLimiter;
        this.tokenLimiter = tokenLimiter;
        this.properties = properties;
    }

    public boolean isAllowed(String key, String path) {

        RateLimitRule rule;

        // ✅ SIMPLE PER-API SELECTION USING IF–ELSE
        if (path.contains("/api/hello") && properties.getApi().getHello() != null) {
            rule = properties.getApi().getHello().toRule();
        } 
        else if (path.contains("/api/login") && properties.getApi().getLogin() != null) {
            rule = properties.getApi().getLogin().toRule();
        } 
        else if (path.contains("/api/orders") && properties.getApi().getOrders() != null) {
            rule = properties.getApi().getOrders().toRule();
        } 
        else {
            rule = properties.getDefaultRule().toRule();  // ✅ fallback
        }

        // ✅ SIMPLE ALGORITHM SWITCH
        if ("TOKEN_BUCKET".equalsIgnoreCase(properties.getAlgorithm())) {
            return tokenLimiter.isAllowed(key, rule);
        } else {
            return fixedLimiter.isAllowed(key, rule);
        }
    }
}
