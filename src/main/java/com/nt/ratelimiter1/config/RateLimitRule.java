package com.nt.ratelimiter1.config;

import java.time.Duration;

public class RateLimitRule {
    private final int limit;
    private final Duration window;

    public RateLimitRule(int limit, Duration window) {
        this.limit = limit;
        this.window = window;
    }

    public int getLimit() {
        return limit;
    }

    public Duration getWindow() {
        return window;
    }
}
