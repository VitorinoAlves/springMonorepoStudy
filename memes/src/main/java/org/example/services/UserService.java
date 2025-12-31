package org.example.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.clients.UserClient;
import org.example.exceptions.InvalidDataExecption;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public void validarUsuario(Long userId) {
        try {
            userClient.getUserById(userId);
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new InvalidDataExecption("Usuário não encontrado para o ID: " + userId);
            }
            throw new RuntimeException("Falha na comunicação com o serviço 'users'. Código: " + e.status(), e);
        }
    }
}
