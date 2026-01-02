import http from "k6/http";
import { sleep, check } from 'k6';

import { Config } from '../config/environment.js';
import { getWorkload } from '../config/workloads.js';
import { UserClient } from '../clients/UserClient.js';
import { UserFactory } from '../data/userFactory.js';

export const options = getWorkload(__ENV.WORKLOAD);

export function setup() {
    const client = new UserClient(Config.USER_URL);
    const ids = [];

    console.log("--- Iniciando Setup: Criando massa de dados ---");

    for (let i = 0; i<10; i++) {
        const res = client.create(UserFactory.generatePayload());
        if(res.status == 201) {
            ids.push(JSON.parse(res.body).id);
        }
    }

    return { userIds: ids };
}

export function buscaUsuarioScenario(data) {
    const client = new UserClient(Config.USER_URL);

    const randomId = data.userIds[Math.floor(Math.random() * data.userIds.length)];

    const res = client.getById(randomId);

    check(res, {
        'get status is 200': (r) => r.status === 200,
        'has user info': (r) => JSON.parse(r.body).id !== undefined,
    });

    sleep(1);
}

export default buscaUsuarioScenario;