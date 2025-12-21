package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {
    @NotBlank(message = "O nome é obrigatório para uma categoria")
    private String nome;
    private String descricao;
    @NotNull(message = "O id do usuário que inseriu a categoria é obrigatório")
    private Long userId;
}
