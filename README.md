# Distributed Redis Rate Limiter (Spring Boot)

A distributed rate limiter built using Java (Spring Boot) and Redis to control API request limits across multiple instances of an application.  
This project demonstrates how to implement a production-style rate limiting mechanism using Redis as a centralized, in-memory store.

## 🚀 Features

- Distributed rate limiting using Redis
- Fixed window rate limiter (10 requests per minute per user)
- Spring Boot REST API
- Redis running in Docker
- Load tested using k6
- Configurable rate limit window and request count
- Clean API response with rate limit metadata

## 🧠 How It Works

Each incoming request:
1. Uses Redis `INCR` to increment a per-user counter.
2. Sets an expiry on first request (TTL = time window).
3. Allows or blocks the request based on the count.
4. Returns metadata like:
   - current request count
   - max allowed requests
   - time window

This works across multiple app instances because Redis acts as a shared store.

## 🛠 Tech Stack

- Java 17 (Spring Boot 3.x)
- Spring Web
- Spring Data Redis
- Redis (Docker)
- k6 (Load Testing)
- Maven

## 📦 Prerequisites

- Java 17+
- Docker
- Maven
- Redis Docker Image
- k6 (for load testing)

### 1️⃣ Run Redis using Docker
bash:
docker run -d -p 6379:6379 --name my-redis redis


2️⃣ Configure Redis in application.yml : 
----------------------------------------
server:
  port: 8081

spring:
  data:
    redis:
      host: localhost
      port: 6379

server:
  tomcat:
    threads:
      max: 200
    accept-count: 100
    connection-timeout: 5000
    

3️⃣ Run Spring Boot App: 
-----------------------
 mvn spring-boot:run

4️⃣ Test API in Browser / Postman:    http://localhost:8081/api/v1/access?userId=testuser
----------------------------------
After 10 requests per minute per user, API returns HTTP 429 (Too Many Requests).


⚡ Load Testing (k6)
-----------------------
Example k6 script:

import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 200 },
    { duration: '10s', target: 500 },
    { duration: '10s', target: 1000 },
    { duration: '10s', target: 1000 },
    { duration: '10s', target: 0 },
  ],
};

export default function () {
  http.get('http://localhost:8081/api/v1/access?userId=testuser');
  sleep(1);
}

To run it : 
-----------
k6 run script.js

📈 Performance
----------------




-Tested up to ~500–1000 concurrent virtual users (VUs)

-Sustained ~11k+ requests per second on local machine

-Tuned Tomcat thread pool for better throughput

(Actual numbers depend on system specs)

🔒 Future Improvements
------------------------

-Token Bucket / Sliding Window algorithm

-Lua scripting for atomic operations in Redis

-Rate limits per API route

-Cloud deployment (EC2 + Docker)

-Redis cluster support

-Authentication-based rate limits


    
