package org.example.clients;

import org.example.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users", path = "/memelandia/usuarios")
public interface UserClient {

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id);
}
