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

/**
 * Estende os thresholds globais para cenários específicos
 * @param {Object} baseThresholds - Os thresholds do workload (ex: p(95)<500)
 * @param {Array} scenarios - Lista de nomes dos cenários (ex: ['fluxo_criacao', 'fluxo_leitura'])
 */

export function generateScenarioThresholds(baseThresholds, scenarios) {
    const extendedThresholds = { ...baseThresholds };

    scenarios.forEach(scenario => {
        Object.keys(baseThresholds).forEach(metric => {
            const scenarioMetric = `${metric}{scenario:${scenario}}`;
            extendedThresholds[scenarioMetric] = baseThresholds[metric];
        });
    });
    return extendedThresholds;
}

export function getWorkload(name) {
    return WorkloadConfig[name] || WorkloadConfig.smoke;
}