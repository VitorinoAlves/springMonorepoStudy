package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.exceptions.ResourceNotFound;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found for id: " + id));
    }

    public User novoUsuario(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setDataCadastro(new Date());
        return userRepository.insert(user);
    }

    public User atualizaUsuario(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found for id: " + id));

        userMapper.updateEntityFromDto(userDto, existingUser);
        return userRepository.save(existingUser);
    }
}
