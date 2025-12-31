package org.example.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.clients.UserClient;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.exceptions.InvalidDataExecption;
import org.example.exceptions.ResourceBeingUsedException;
import org.example.exceptions.ResourceNotFound;
import org.example.mapper.CategoriaMapper;
import org.example.repositories.CategoriaRepository;
import org.example.repositories.MemeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final MemeRepository memeRepository;
    private final UserService userService;

    public Categoria novaCategoria(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDto);
        categoria.setDataCadastro(new Date());

        userService.validarUsuario(categoria.getUserId());

        return categoriaRepository.insert(categoria);
    }

    public List<Categoria> retornaTodasCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria retornaCategoriaById(Long categoriaId) {
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new ResourceNotFound("Categoria não encontrada para o id: " + categoriaId));
    }

    public Categoria atualizaCategoria(CategoriaDto categoriaDto, Long categoriaId) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId).orElseThrow(() -> new ResourceNotFound("Categoria não encontrada para o id: " + categoriaId));

        categoriaMapper.updateEntityFromDto(categoriaDto, categoriaExistente);
        return categoriaRepository.save(categoriaExistente);
    }

    public void deletaCategoria(Long categoriaId) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId).orElseThrow(() -> new ResourceNotFound("Categoria não encontrada para o id: " + categoriaId));
        if(memeRepository.existsByCategoryId(categoriaId)) {
            throw new ResourceBeingUsedException("Não é possível deletar: existem memes nesta categoria.");
        }
        categoriaRepository.delete(categoriaExistente);
    }

    public void validarCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFound("Categoria não encontrada para o id: " + categoriaId);
        }
    }
}
