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
    private final MemeMapper memeMapper;
    private final UserService userService;
    private final CategoriaService categoriaService;


    public Meme criaNovoMeme(MemeDto memeDto) {
        Meme meme = memeMapper.toEntity(memeDto);
        meme.setDataCadastro(new Date());

        categoriaService.validarCategoria(meme.getCategoryId());
        userService.validarUsuario(meme.getUserId());

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

    public Meme atualizaMeme(MemeDto memeDto, Long id) {
        Meme memeExistente = memeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Meme não encontrado para o ID: " + id));
        memeMapper.updateEntityFromDto(memeDto, memeExistente);

        categoriaService.validarCategoria(memeExistente.getCategoryId());
        userService.validarUsuario(memeExistente.getUserId());


        return memeRepository.save(memeExistente);
    }

}
