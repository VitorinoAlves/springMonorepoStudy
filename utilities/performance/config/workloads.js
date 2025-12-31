export const WorkloadConfig = {
    smoke: {
        stages: [{ duration: "10s", target: 1 }],
        thresholds: {
            http_req_failed: ['rate<0.01'],
            http_req_duration: ['p(99)<250'],
        },
    },
    average: {
        stages: [
            { duration: "1m", target: 100 },
            { duration: "4m", target: 100 },
            { duration: "1m", target: 0 }
        ],
        thresholds: {
            http_req_failed: ['rate<0.01'],    // Menos de 1% de erro
            http_req_duration: ['p(95)<500'],  // 95% das requisições < 500ms
            http_req_duration: ['p(99)<1000'], // 99% das requisições < 1s
        },
    },
    stress: {
        stages: [
            { duration: "2m", target: 200 },
            { duration: "5m", target: 700 },
            { duration: "2m", target: 0 }
        ],
        thresholds: {
            http_req_failed: ['rate<0.05'],     // No stress, aceitamos até 5% de erro
            http_req_duration: ['p(95)<2000'],  // No stress, aceitamos até 2s de latência
        },
    }
};

export function getWorkload(name) {
    return WorkloadConfig[name] || WorkloadConfig.smoke;
}