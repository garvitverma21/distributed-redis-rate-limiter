import http from 'k6/http';
import { check } from 'k6';

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
    const userId = `user_${Math.floor(Math.random() * 1000)}`;
    const res = http.get(`http://localhost:8081/api/v1/access?userId=${userId}`);
    check(res, {
        'status is 200 or 429': (r) => r.status === 200 || r.status === 429,
    });
}

