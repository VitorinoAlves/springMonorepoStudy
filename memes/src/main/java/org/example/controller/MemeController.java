package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dtos.MemeDto;
import org.example.entities.Meme;
import org.example.services.MemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
