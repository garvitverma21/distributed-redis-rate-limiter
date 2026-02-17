package com.garvit.distributed_redis_rate_limiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedRedisRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedRedisRateLimiterApplication.class, args);
	}

}
