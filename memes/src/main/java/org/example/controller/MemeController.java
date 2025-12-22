package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.MemeDto;
import org.example.entities.Meme;
import org.example.services.MemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memelandia/meme")
@RequiredArgsConstructor
@Tag(name = "Meme")
public class MemeController {
    private final MemeService memeService;

    @PostMapping
    public ResponseEntity<Meme> criarNovoMeme(@Valid @RequestBody MemeDto memeDto) {
       return ResponseEntity.ok(memeService.criaNovoMeme(memeDto));
    }

    @GetMapping
    public ResponseEntity<List<Meme>> retornaTodosOsMemes() {
        return ResponseEntity.ok(memeService.retornaTodosOsMemes());
    }

    @GetMapping("{id}")
    public ResponseEntity<Meme> retornaMemeById(@PathVariable Long id) {
        return ResponseEntity.ok(memeService.retornaMemeById(id));
    }

    @GetMapping("/meme-do-dia")
    public ResponseEntity<Meme> retornaMemeDoDia() {
        return ResponseEntity.ok(memeService.retornaMemeDoDia());
    }


}
