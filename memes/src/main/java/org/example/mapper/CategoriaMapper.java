package org.example.mapper;

import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria toEntity(CategoriaDto categoriaDto);
}
