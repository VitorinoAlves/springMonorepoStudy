package org.example.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.clients.UserClient;
import org.example.dtos.MemeDto;
import org.example.entities.Meme;
import org.example.exceptions.InvalidDataExecption;
import org.example.exceptions.ResourceNotFound;
import org.example.mapper.CategoriaMapper;
import org.example.mapper.MemeMapper;
import org.example.repositories.CategoriaRepository;
import org.example.repositories.MemeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemeService {
    private final MemeRepository memeRepository;
    private final CategoriaRepository categoriaRepository;
    private final MemeMapper memeMapper;
    private final UserClient userClient;


    public Meme criaNovoMeme(MemeDto memeDto) {
        Meme meme = memeMapper.toEntity(memeDto);
        meme.setDataCadastro(new Date());

        categoriaRepository.findById(meme.getCategoryId()).orElseThrow(() -> new InvalidDataExecption("Categoria não encontrada para o Id informado: " + meme.getCategoryId()));
        try {
            userClient.getUserById(meme.getUserId());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new InvalidDataExecption("Usuário não encontrado para o ID informado: " + meme.getUserId());
            }
            throw new RuntimeException("Falha na comunicação com o serviço 'users'. Código: " + e.status(), e);
        }

        return memeRepository.insert(meme);
    }

    public List<Meme> retornaTodosOsMemes() {
        return memeRepository.findAll();
    }

    public Meme retornaMemeById(Long id) {
        return memeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Meme não encontrado para o ID: " + id));
    }

    @Cacheable(value = "memeDoDia")
    public Meme retornaMemeDoDia() {
        Meme memeRandom = memeRepository.findRandomMeme();

        if (memeRandom == null) {
            throw new ResourceNotFound("Nenhum meme disponível no momento.");
        }
        return memeRandom;
    }

}
