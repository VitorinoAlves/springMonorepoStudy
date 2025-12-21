package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemeDto {
    @NotBlank(message = "O nome é obrigatório para um meme")
    private String nome;
    @NotBlank(message = "A descrição é obrigatório para um meme")
    private String descricao;
    @NotNull(message = "O id de uma categoria é obrigatório para um meme")
    private Long categoryId;
    @NotNull(message = "O id de um user é obrigatório para um meme")
    private Long userId;
}
