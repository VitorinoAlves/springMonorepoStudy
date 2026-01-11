package services;

import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.exceptions.InvalidDataExecption;
import org.example.exceptions.ResourceBeingUsedException;
import org.example.exceptions.ResourceNotFound;
import org.example.mapper.CategoriaMapper;
import org.example.repositories.CategoriaRepository;
import org.example.repositories.MemeRepository;
import org.example.services.CategoriaService;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;
    @Mock private CategoriaMapper categoriaMapper;
    @Mock private UserService userService;
    @Mock private MemeRepository memeRepository;

    @InjectMocks private CategoriaService categoriaService;

    private Categoria categoriaBase;
    private CategoriaDto categoriaDtoBase;
    private final Long CATEGORIA_ID = 1L;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        categoriaDtoBase = new CategoriaDto("Categoria Test", "Descrição", USER_ID);
        categoriaBase = new Categoria(CATEGORIA_ID, "Categoria Test", "Descrição", new Date(), USER_ID);
    }

    // --- CENÁRIOS DE CRIAÇÃO ---

    @Test
    @DisplayName("1. Deve criar categoria com sucesso")
    void shouldCreateCategory_WhenDataIsValid() {
        given(categoriaMapper.toEntity(any())).willReturn(categoriaBase);
        given(categoriaRepository.insert(any(Categoria.class))).willReturn(categoriaBase);

        Categoria result = categoriaService.novaCategoria(categoriaDtoBase);

        assertThat(result).isNotNull();
        then(userService).should().validarUsuario(USER_ID);
    }

    @Test
    @DisplayName("2. Deve falhar ao criar categoria com usuário inválido")
    void shouldThrowException_WhenUserIsInvalid() {
        given(categoriaMapper.toEntity(any())).willReturn(categoriaBase);
        willThrow(new InvalidDataExecption("Usuário não encontrado")).given(userService).validarUsuario(USER_ID);

        assertThatThrownBy(() -> categoriaService.novaCategoria(categoriaDtoBase))
                .isInstanceOf(InvalidDataExecption.class);

        then(categoriaRepository).should(never()).insert(any(Categoria.class));
    }

    // --- CENÁRIOS DE BUSCA ---

    @Test
    @DisplayName("3. Deve retornar todas as categorias")
    void shouldReturnAllCategories() {
        given(categoriaRepository.findAll()).willReturn(List.of(categoriaBase));

        List<Categoria> result = categoriaService.retornaTodasCategorias();

        assertThat(result).hasSize(1);
        then(categoriaRepository).should().findAll();
    }

    @Test
    @DisplayName("4. Deve retornar categoria por ID")
    void shouldReturnCategory_WhenIdExists() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.of(categoriaBase));

        Categoria result = categoriaService.retornaCategoriaById(CATEGORIA_ID);

        assertThat(result.getNome()).isEqualTo(categoriaBase.getNome());
    }

    @Test
    @DisplayName("5. Deve lançar erro quando categoria não existe no findById")
    void shouldThrowNotFound_WhenCategoryIdDoesNotExist() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.retornaCategoriaById(CATEGORIA_ID))
                .isInstanceOf(ResourceNotFound.class);
    }

    // --- CENÁRIOS DE ATUALIZAÇÃO ---

    @Test
    @DisplayName("6. Deve atualizar categoria com sucesso")
    void shouldUpdateCategory_WhenExists() {
        CategoriaDto updateDto = new CategoriaDto("Novo Nome", "Nova Desc", USER_ID);
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.of(categoriaBase));
        given(categoriaRepository.save(any(Categoria.class))).willReturn(categoriaBase);

        Categoria result = categoriaService.atualizaCategoria(updateDto, CATEGORIA_ID);

        assertThat(result).isNotNull();
        then(categoriaRepository).should().save(any(Categoria.class));
    }

    @Test
    @DisplayName("7. Deve falhar ao atualizar categoria inexistente")
    void shouldThrowNotFound_WhenUpdatingNonExistentCategory() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.atualizaCategoria(categoriaDtoBase, CATEGORIA_ID))
                .isInstanceOf(ResourceNotFound.class);

        then(categoriaRepository).should(never()).save(any(Categoria.class));
    }

    // --- CENÁRIOS DE EXCLUSÃO ---

    @Test
    @DisplayName("8. Deve deletar categoria com sucesso")
    void shouldDeleteCategory_WhenNoMemesLinked() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.of(categoriaBase));
        given(memeRepository.existsByCategoryId(CATEGORIA_ID)).willReturn(false);

        categoriaService.deletaCategoria(CATEGORIA_ID);

        then(categoriaRepository).should().delete(any(Categoria.class));
    }

    @Test
    @DisplayName("9. Deve falhar ao deletar categoria inexistente")
    void shouldThrowNotFound_WhenDeletingNonExistentCategory() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.deletaCategoria(CATEGORIA_ID))
                .isInstanceOf(ResourceNotFound.class);

        then(categoriaRepository).should(never()).delete(any(Categoria.class));
    }

    @Test
    @DisplayName("10. Deve falhar ao deletar categoria que possui memes")
    void shouldThrowException_WhenDeletingCategoryWithMemes() {
        given(categoriaRepository.findById(CATEGORIA_ID)).willReturn(Optional.of(categoriaBase));
        given(memeRepository.existsByCategoryId(CATEGORIA_ID)).willReturn(true);

        assertThatThrownBy(() -> categoriaService.deletaCategoria(CATEGORIA_ID))
                .isInstanceOf(ResourceBeingUsedException.class);

        then(categoriaRepository).should(never()).delete(any(Categoria.class));
    }
}