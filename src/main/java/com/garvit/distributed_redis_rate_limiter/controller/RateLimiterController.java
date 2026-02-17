package com.garvit.distributed_redis_rate_limiter.controller;

import com.garvit.distributed_redis_rate_limiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garvit.distributed_redis_rate_limiter.dto.RateLimitResponse;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@RestController
@RequestMapping("/api/v1")
public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/access")
    public ResponseEntity<RateLimitResponse> accessResource (@RequestParam String userId)
    {
        RateLimitResponse response= rateLimiterService.checkLimit(userId);

        if(response.isAllowed())
        {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(TOO_MANY_REQUESTS).body(response);

    }
}
