package org.example.services;

import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.exceptions.ResourceNotFound;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void deveRetornarUsuarioQuandoIdExiste() {
        Long userId = 1L;
        User testUser = new User(userId, "Test User 01", "testUser01@test.com", new Date());
        given(userRepository.findById(userId)).willReturn(Optional.of(testUser));

        User actualUser = userService.getUserById(userId);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getId()).isEqualTo(userId);
        assertThat(actualUser).isEqualTo(testUser);

    }

    @Test
    void shouldReturnResourceNotFound() {
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        ResourceNotFound exception = Assertions.assertThrows(
                ResourceNotFound.class,
                () -> userService.getUserById(userId)
        );

        assertThat(exception.getMessage()).contains("User not found for id: 1");
    }

    @Test
    void shouldReturnListOfUsers() {
        User testUser01 = new User(1L, "Test User 01", "testUser01@test.com", new Date());
        User testUser02 = new User(2L, "Test User 02", "testUser02@test.com", new Date());

        given(userRepository.findAll()).willReturn(List.of(testUser01, testUser02));

        List<User> actualUserList = userService.getAllUsers();

        assertThat(actualUserList).isNotNull();
        assertThat(actualUserList.size()).isEqualTo(2);
        assertThat(actualUserList.get(0)).isEqualTo(testUser01);
        assertThat(actualUserList.get(1)).isEqualTo(testUser02);
    }

    @Test
    void createNewUserTest() {
        UserDto testUserDto = new UserDto("Test User 01", "testUser01@test.com");
        User testUserToEntity = new User(null, "Test User 01", "testUser01@test.com", null);
        User testSavedUser = new User(1L, "Test User 01", "testUser01@test.com", new Date());

        given(userMapper.toEntity(testUserDto)).willReturn(testUserToEntity);
        given(userRepository.insert(testUserToEntity)).willReturn(testSavedUser);

        User actualUser = userService.novoUsuario(testUserDto);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getId()).isEqualTo(1L);
        assertThat(actualUser).isEqualTo(testSavedUser);

        // Verificação 3: O MAIS IMPORTANTE: O método 'insert' do repositório foi chamado?
        // Verificamos se o mock foi invocado exatamente 1 vez com a entidade correta.
        assertThat(actualUser.getDataCadastro()).isNotNull();
        Mockito.verify(userRepository, Mockito.times(1)).insert(testUserToEntity);
    }

    @Test
    void updateUserTest() {
        Long userId = 1L;
        UserDto newUserDataDto = new UserDto("Test User Edited", "testUser01@edited.com");
        User existingUser = new User(userId, "Test User 01", "testUser01@test.com", new Date());
        User updatedUser = new User(userId, "Test User Edited", "testUser01@edited.com",existingUser.getDataCadastro());

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userRepository.save(ArgumentMatchers.any(User.class))).willReturn(updatedUser);

        User actualUser = userService.atualizaUsuario(userId, newUserDataDto);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getNome()).isEqualTo("Test User Edited");
        assertThat(actualUser.getEmail()).isEqualTo("testUser01@edited.com");

        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
        Mockito.verify(userMapper, Mockito.times(1)).updateEntityFromDto(newUserDataDto, existingUser);
    }

    @Test
    void updateUserNotFoundTest() {
        Long userId = 1L;
        UserDto newUserDataDto = new UserDto("Test User Edited", "testUser01@edited.com");
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        ResourceNotFound exception = Assertions.assertThrows(
                ResourceNotFound.class,
                () -> userService.atualizaUsuario(userId, newUserDataDto)
        );

        assertThat(exception.getMessage()).contains("User not found for id: 1");

        Mockito.verify(userRepository, Mockito.never()).save(ArgumentMatchers.any(User.class));
        Mockito.verifyNoInteractions(userMapper);
    }
}
