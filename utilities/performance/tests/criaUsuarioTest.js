import http from "k6/http";
import { sleep, check } from 'k6';

import { Config } from '../config/environment.js';
import { getWorkload } from '../config/workloads.js';
import { UserClient } from '../clients/UserClient.js';
import { UserFactory } from '../data/userFactory.js';

const userClient = new UserClient(Config.USER_URL);
export const options = getWorkload(__ENV.WORKLOAD);

export function criaUsuarioScenario() {
    const newUser = UserFactory.generatePayload();
    const res = userClient.create(newUser);
    check(res, {
        'status is 201': (r) => r.status === 201,
        'transaction time OK': (r) => r.timings.duration < 500,
    });
    sleep(1);
}

export default function() { criaUsuarioScenario(); }