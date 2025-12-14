package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.mapper.CategoriaMapper;
import org.example.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public Categoria novaCategoria(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDto);
        categoria.setDataCadastro(new Date());
        return categoriaRepository.insert(categoria);
    }

    public List<Categoria> retornaTodasCategorias() {
        return categoriaRepository.findAll();
    }
}
