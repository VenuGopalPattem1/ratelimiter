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

    public boolean isAllowed(String key) {

        RateLimitRule rule = properties.toRule(); // same rule for all APIs

        if ("TOKEN_BUCKET".equalsIgnoreCase(properties.getAlgorithm())) {
            return tokenLimiter.isAllowed(key, rule);
        } else {
            return fixedLimiter.isAllowed(key, rule);
        }
    }
}
