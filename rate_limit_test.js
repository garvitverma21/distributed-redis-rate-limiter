import http from 'k6/http';
import { check } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 50 },   // Warm-up phase
    { duration: '15s', target: 200 },  // Moderate load
    { duration: '20s', target: 500 },  // Peak realistic stress test (500 VUs)
    { duration: '15s', target: 200 },  // Recovery phase
    { duration: '10s', target: 0 },    // Ramp-down
  ],
  // Prevents RAM/Disk slowdowns during high throughput
  discardResponseBodies: true,
};

export default function () {
  // Simulate 5,000 unique users to test real Redis key distribution
  const userId = `user_${Math.floor(Math.random() * 5000)}`;

  // Alternate requests evenly between Port 8081 and Port 8082
  const port = __ITER % 2 === 0 ? 8081 : 8082;

  const res = http.get(`http://localhost:${port}/api/v1/access?userId=${userId}`);

  check(res, {
    'status is 200 or 429': (r) => r.status === 200 || r.status === 429,
  });
}