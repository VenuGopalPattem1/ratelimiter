package com.nt.ratelimiter1.core;

import com.nt.ratelimiter1.config.RateLimitRule;

public interface RateLimiterStrategy {
    boolean isAllowed(String key, RateLimitRule rule);
}
