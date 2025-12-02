package com.nt.ratelimiter1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterProperties {

    private String algorithm;
    private DefaultRule defaultRule;
    private ApiRule api;

    // -------- GETTERS & SETTERS --------

    public String getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public DefaultRule getDefaultRule() {
        return defaultRule;
    }
    public void setDefaultRule(DefaultRule defaultRule) {
        this.defaultRule = defaultRule;
    }

    public ApiRule getApi() {
        return api;
    }
    public void setApi(ApiRule api) {
        this.api = api;
    }

    // -------- INNER CLASSES --------

    public static class DefaultRule {
        private int limit;
        private int windowSeconds;

        public int getLimit() {
            return limit;
        }
        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getWindowSeconds() {
            return windowSeconds;
        }
        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }

        public RateLimitRule toRule() {
            return new RateLimitRule(limit, Duration.ofSeconds(windowSeconds));
        }
    }

    public static class ApiRule {
        private Rule hello;
        private Rule login;
        private Rule orders;

        public Rule getHello() {
            return hello;
        }
        public void setHello(Rule hello) {
            this.hello = hello;
        }

        public Rule getLogin() {
            return login;
        }
        public void setLogin(Rule login) {
            this.login = login;
        }

        public Rule getOrders() {
            return orders;
        }
        public void setOrders(Rule orders) {
            this.orders = orders;
        }
    }

    public static class Rule {
        private int limit;
        private int windowSeconds;

        public int getLimit() {
            return limit;
        }
        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getWindowSeconds() {
            return windowSeconds;
        }
        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }

        public RateLimitRule toRule() {
            return new RateLimitRule(limit, Duration.ofSeconds(windowSeconds));
        }
    }
}
