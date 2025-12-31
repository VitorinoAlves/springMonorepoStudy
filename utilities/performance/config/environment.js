const EnvConfig = {
    dev: {
        USER_URL: 'http://localhost:8080',
        MEME_URL: 'http://localhost:8082'
    }
}

export const Config = EnvConfig[__ENV.ENVIRONMENT] || EnvConfig['dev'];