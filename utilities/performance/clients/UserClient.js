import http from "k6/http";

export class UserClient {
    constructor (baseUrl) {
        this.baseUrl = baseUrl;
    }

    create(user) {
        const url = `${this.baseUrl}/memelandia/usuarios`;
        const payload = JSON.stringify(user);
        const params = {
            headers: {
                "Content-Type": "application/json",
            },
        };

        return http.post(url, payload, params);
    }
}