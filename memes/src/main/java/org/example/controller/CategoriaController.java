package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memelandia/categoria")
@RequiredArgsConstructor
@Tag(name = "Categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> novaCategoria(@RequestBody CategoriaDto categoriaDto) {
        return ResponseEntity.ok(categoriaService.novaCategoria(categoriaDto));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> retornaListaCategorias() {
        return ResponseEntity.ok(categoriaService.retornaTodasCategorias());
    }
}
