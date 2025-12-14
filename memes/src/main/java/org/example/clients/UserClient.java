package org.example.clients;

import org.example.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "http://localhost:8080/memelandia/usuarios")
public interface UserClient {

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id);
}
