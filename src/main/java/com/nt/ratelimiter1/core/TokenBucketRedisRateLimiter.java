package com.nt.ratelimiter1.core;

import com.nt.ratelimiter1.config.RateLimitRule;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenBucketRedisRateLimiter implements RateLimiterStrategy {

    private final StringRedisTemplate redisTemplate;

    public TokenBucketRedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isAllowed(String key, RateLimitRule rule) {

        String tokenKey = key + ":tokens";// this is for no of tokens
        String timestampKey = key + ":ts";// this is for last updated timestamp

        int capacity = rule.getLimit();                     // max bucket size
        long refillIntervalMs = rule.getWindow().toMillis(); // full refill time

        long now = Instant.now().toEpochMilli();

        // 1️⃣ Get current token count
        String tokenStr = redisTemplate.opsForValue().get(tokenKey);
        int currentTokens = (tokenStr == null) ? capacity : Integer.parseInt(tokenStr);

        // 2️⃣ Get last refill time
        String tsStr = redisTemplate.opsForValue().get(timestampKey);
        long lastRefillTime = (tsStr == null) ? now : Long.parseLong(tsStr);

        // 3️⃣ Calculate how many tokens to refill
        long elapsedTime = now - lastRefillTime;

        long tokensToAdd = (elapsedTime * capacity) / refillIntervalMs;

        int newTokenCount = Math.min(capacity, currentTokens + (int) tokensToAdd);

        // 4️⃣ If there is NO token → BLOCK
        if (newTokenCount <= 0) {
            return false;
        }

        // 5️⃣ Consume 1 token
        newTokenCount--;

        // 6️⃣ Save updated values back to Redis
        redisTemplate.opsForValue().set(tokenKey, String.valueOf(newTokenCount));
        redisTemplate.opsForValue().set(timestampKey, String.valueOf(now));

        return true;
    }
}
