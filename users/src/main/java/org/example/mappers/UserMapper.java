package org.example.mappers;

import org.example.dtos.UserDto;
import org.example.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}
