const randomString = (length) => Math.random().toString(36).substring(2, length + 2);

export class UserFactory {
    static generatePayload() {
        return {
            nome: `Performance User ${randomString(5)}`,
            email: `perf_${Date.now()}_${randomString(3)}@test.com`,
        };
    }
}