package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.services.CategoriaService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Categoria> novaCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.novaCategoria(categoriaDto));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> retornaListaCategorias() {
        return ResponseEntity.ok(categoriaService.retornaTodasCategorias());
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> retornaCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.retornaCategoriaById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Categoria> atualizaCategoria(@Valid @RequestBody CategoriaDto categoriaDto, @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.atualizaCategoria(categoriaDto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaCategoria(@PathVariable Long id) {
        categoriaService.deletaCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
