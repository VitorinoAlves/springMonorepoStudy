package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memelandia/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> buscaUsuarios() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> buscaUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> novoUsuario(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.userService.novoUsuario(userDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUsuario(@Valid @RequestBody UserDto userDto, @PathVariable Long id){
        return ResponseEntity.ok(this.userService.atualizaUsuario(id, userDto));
    }


}
