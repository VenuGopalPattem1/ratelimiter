package com.nt.ratelimiter1.util;

import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RateLimitKeyBuilder {

    public String buildKey(HttpServletRequest request, String userId) {
        String ip = extractClientIp(request);
        String path = request.getRequestURI();

        if (StringUtils.isBlank(userId)) {
            userId = "anonymous";
        }

        // pattern: rl:{path}:{userId}:{ip}
        return "rl:" + path + ":" + userId + ":" + ip;
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}