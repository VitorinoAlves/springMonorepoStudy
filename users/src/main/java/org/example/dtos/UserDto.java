package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "O nome é obrigatório para um usuário")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório para um usuário")
    private String email;
}
