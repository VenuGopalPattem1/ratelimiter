package com.nt.ratelimiter1.core;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.nt.ratelimiter1.config.RateLimitRule;

@Component
public class FixedWindowRedisRateLimiter implements RateLimiterStrategy {

    private final StringRedisTemplate redisTemplate;

    public FixedWindowRedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isAllowed(String key, RateLimitRule rule) {
        Duration window = rule.getWindow();

        // Atomically increment the count for this key
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1L) {
            // First hit in this window -> set expiry on the key
            redisTemplate.expire(key, window);
        }

        // Allow only if count <= limit
        return count != null && count <= rule.getLimit();
    }
}