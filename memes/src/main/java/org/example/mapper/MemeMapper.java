package org.example.mapper;

import org.example.dtos.MemeDto;
import org.example.entities.Meme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemeMapper {

    Meme toEntity(MemeDto memeDto);

    //@Mapping(target = "id", ignore = true)
    //@Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void updateEntityFromDto(MemeDto memeDto, @MappingTarget Meme meme);
}
