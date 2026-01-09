package services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.exceptions.InvalidDataExecption;
import org.example.mapper.CategoriaMapper;
import org.example.repositories.CategoriaRepository;
import org.example.services.CategoriaService;
import org.example.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void criaNovaCategoriaTest() {
        CategoriaDto categoriaDto = new CategoriaDto("Categoria Test", "Categoria Descrição", 1l);
        Categoria categoriaToSave = new Categoria(null, "Categoria Test", "Categoria Descrição",  null, 1l);
        Categoria categoriaSaved = new Categoria(1l, "Categoria Test", "Categoria Descrição",  new Date(), 1l);

        given(categoriaMapper.toEntity(categoriaDto)).willReturn(categoriaToSave);
        given(categoriaRepository.insert(categoriaToSave)).willReturn(categoriaSaved);


        Categoria actualSavedCategoria = categoriaService.novaCategoria(categoriaDto);

        assertThat(actualSavedCategoria).isNotNull();
        assertThat(actualSavedCategoria.getNome()).isEqualTo("Categoria Test");

        Mockito.verify(categoriaRepository, Mockito.times(1)).insert(categoriaToSave);
        Mockito.verify(userService, Mockito.times(1)).validarUsuario(categoriaToSave.getUserId());
    }

    @Test
    void criaNovaCategoriaWithInvalidUser() {
        Long userId = 1l;
        CategoriaDto categoriaDto = new CategoriaDto("Categoria Test", "Categoria Descrição", userId);
        Categoria categoriaToSave = new Categoria(null, "Categoria Test", "Categoria Descrição",  null, userId);

        given(categoriaMapper.toEntity(categoriaDto)).willReturn(categoriaToSave);
        doThrow(new InvalidDataExecption("Usuário não encontrado para o ID: " + userId))
                .when(userService).validarUsuario(userId);

        InvalidDataExecption execption = Assertions.assertThrows(
                InvalidDataExecption.class,
                () -> categoriaService.novaCategoria(categoriaDto)
        );

        assertThat(execption.getMessage()).isEqualTo("Usuário não encontrado para o ID: 1");
        Mockito.verify(categoriaRepository, Mockito.never()).insert(any(Categoria.class));
    }

    @Test
    void retornaTodasCategoriasTest() {
        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(new Categoria(1l, "Categoria Test - 1", "Categoria Descrição - 2",  new Date(), 1l));
        categoriaList.add(new Categoria(2l, "Categoria Test - 2", "Categoria Descrição - 2",  new Date(), 1l));

        given(categoriaRepository.findAll()).willReturn(categoriaList);

        List<Categoria> returnedCategoriaList = categoriaService.retornaTodasCategorias();

        assertThat(returnedCategoriaList).isNotNull();
        assertThat(returnedCategoriaList.size()).isEqualTo(2);
        assertThat(returnedCategoriaList.get(0).getNome()).isEqualTo("Categoria Test - 1");
        assertThat(returnedCategoriaList.get(1).getNome()).isEqualTo("Categoria Test - 2");

        Mockito.verify(categoriaRepository, Mockito.times(1)).findAll();
    }


}
