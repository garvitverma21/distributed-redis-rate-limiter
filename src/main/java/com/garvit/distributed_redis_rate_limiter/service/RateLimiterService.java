package com.garvit.distributed_redis_rate_limiter.service;

import com.garvit.distributed_redis_rate_limiter.dto.RateLimitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final int MAX_REQUESTS = 10;
    private static final int TIME_WINDOW = 60;

    public RateLimitResponse checkLimit(String userId) {
        String key = "rate:limit:" + userId;

        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount != null && currentCount == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW));
        }

        boolean allowed = currentCount != null && currentCount <= MAX_REQUESTS;

        return new RateLimitResponse(
                allowed,
                currentCount == null ? 0 : currentCount,
                MAX_REQUESTS,
                TIME_WINDOW
        );
    }
}
