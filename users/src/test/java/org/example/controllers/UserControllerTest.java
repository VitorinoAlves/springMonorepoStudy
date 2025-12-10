package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.AuthCmd;
import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.exceptions.ResourceNotFound;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.List;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void deveRetornarListaDeusuariosEStatus200() throws Exception {
        List<User> mockUsers = List.of(
                new User(1L, "A", "a@test.com", new Date()),
                new User(2L, "B", "b@test.com", new Date())
        );

        given(userService.getAllUsers()).willReturn(mockUsers);

        mockMvc.perform(get("/memelandia/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveCriarNovoUsuarioERetornerStatus201() throws Exception {
        UserDto inputDto = new UserDto("Novo User", "novo@email");
        User returnedUser = new User(10L, "Novo User", "novo@email", new Date());

        given(userService.novoUsuario(any(UserDto.class))).willReturn(returnedUser);

        mockMvc.perform(post("/memelandia/usuarios")
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType("application/json")
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Novo User"));

        Mockito.verify(userService, Mockito.times(1)).novoUsuario((any(UserDto.class)));
    }

    @Test
    void deveAtualizarUsuarioERetornarStatus200() throws Exception {
        Long userId = 5L;
        UserDto inputDto = new UserDto("User Editado", "editando@email.com");
        User expectedUpdatedUser = new User(userId, "User Editado", "editando@email.com", new Date());

        given(userService.atualizaUsuario(any(Long.class),any(UserDto.class))).willReturn(expectedUpdatedUser);

        mockMvc.perform(put("/memelandia/usuarios/{id}", userId)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nome").value("User Editado"))
                .andExpect(jsonPath("$.email").value("editando@email.com"));

        Mockito.verify(userService, Mockito.times(1)).atualizaUsuario(userId, inputDto);
    }

    @Test
    void deveRetornarStatus404AoAtualizarUsuario() throws Exception {
        Long nonExistentId = 99L;
        UserDto inputDto = new UserDto("User Editado", "editando@email.com");
        given(userService.atualizaUsuario(any(Long.class), any(UserDto.class))).willThrow(new ResourceNotFound("User not found for id: " + nonExistentId));

        mockMvc.perform(put("/memelandia/usuarios/{id}", nonExistentId)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType("application/json")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found for id: " + nonExistentId));

        Mockito.verify(userService, Mockito.times(1)).atualizaUsuario(nonExistentId, inputDto);
    }

    @Test
    void deveRetornarStatus400InputInvalido() throws Exception {
        UserDto invalidDto = new UserDto(null, "test@email.com");

        mockMvc.perform(post("/memelandia/usuarios")
                .content(objectMapper.writeValueAsString(invalidDto))
                .contentType("application/json")
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("O nome é obrigatório para um usuário"));

        Mockito.verify(userService, never()).novoUsuario(any(UserDto.class));
    }

    @Test
    void deveRetornarUserByIdEStatus200() throws Exception {
        Long userId = 15L;
        User returnedUser = new User(userId, "User From DataBase", "testUser@email.com", new Date());

        given(userService.getUserById(anyLong())).willReturn(returnedUser);

        mockMvc.perform(get("/memelandia/usuarios/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nome").value("User From DataBase"))
                .andExpect(jsonPath("$.email").value("testUser@email.com"));

        Mockito.verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void deveRetornarUserNotFoundAndStatus404() throws Exception {
        Long userId = 15L;

        given(userService.getUserById(anyLong())).willThrow(new ResourceNotFound("User not found for id: " + userId));

        mockMvc.perform(get("/memelandia/usuarios/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found for id: " + userId));

        Mockito.verify(userService, times(1)).getUserById(userId);

    }


}
