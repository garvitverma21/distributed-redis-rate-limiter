package com.garvit.distributed_redis_rate_limiter.dto;

public class RateLimitResponse {

    private boolean allowed;
    private long currentCount;
    private long maxRequests;
    private long windowSeconds;

    public RateLimitResponse( boolean allowed, long currentCount, long maxRequests, long windowSeconds)
    {
        this.allowed=allowed;
        this.currentCount=currentCount;
        this.maxRequests= maxRequests;
        this.windowSeconds= windowSeconds;
    }

    public boolean isAllowed()
    {
        return allowed;
    }

    public long getCurrentCount()
    {
        return currentCount;
    }

    public long getMaxRequests()
    {
        return maxRequests;
    }

    public long getWindowSeconds() {
        return windowSeconds;
    }


}
