package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "meme")
public class Meme {

    @Id
    private Long id;
    private String nome;
    private String descricao;
    private Date dataCadastro;
    private Long categoryId;
    private Long userId;
}
