package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MemesApplication;
import org.example.controller.CategoriaController;
import org.example.dtos.CategoriaDto;
import org.example.entities.Categoria;
import org.example.services.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

@WebMvcTest(CategoriaController.class)
@ContextConfiguration(classes = MemesApplication.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService categoriaService;

    private Categoria categoriaBase;
    private CategoriaDto categoriaDtoBase;
    private final Long CATEGORIA_ID = 1L;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        categoriaDtoBase = new CategoriaDto("Categoria Test", "Descrição", USER_ID);
        categoriaBase = new Categoria(CATEGORIA_ID, "Categoria Test", "Descrição", new Date(), USER_ID);
    }

    @Test
    void deveCriarNovaCategoriaERetornerStatus201() throws Exception {
        given(categoriaService.novaCategoria(categoriaDtoBase)).willReturn(categoriaBase);

        mockMvc.perform(post("/memelandia/categoria")
                .content(objectMapper.writeValueAsString(categoriaDtoBase))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CATEGORIA_ID))
                .andExpect(jsonPath("$.nome").value("Categoria Test"));

        then(categoriaService).should().novaCategoria(any(CategoriaDto.class));
    }
}
