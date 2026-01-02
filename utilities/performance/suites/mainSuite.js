import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";

import { criaUsuarioScenario } from '../tests/criaUsuarioTest.js';
import { buscaUsuarioScenario, setup as setupBusca } from '../tests/buscaUsuarioTest.js';
import { Config } from '../config/environment.js';
import { getWorkload } from '../config/workloads.js';

const baseWorkload = getWorkload(__ENV.WORKLOAD);

export const setup = setupBusca;

export const options = {
    thresholds: baseWorkload.thresholds,

    scenarios: {
        fluxo_criacao: {
            exec: 'criaUsuarioScenario',
            executor: 'ramping-vus',
            startVUs: 0,
            stages: baseWorkload.stages,
            gracefulStop: '30s',
        },
        fluxo_leitura: {
            exec: 'buscaUsuarioScenario',
            executor: 'ramping-vus',
            startVUs: 0,
            stages: baseWorkload.stages,
            gracefulStop: '30s',
        }
    }
};

export { criaUsuarioScenario, buscaUsuarioScenario };

export function handleSummary(data) {
  return {
    "summary.html": htmlReport(data),
    //"stdout": textSummary(data, { indent: " ", enableColors: true }),
  };
}