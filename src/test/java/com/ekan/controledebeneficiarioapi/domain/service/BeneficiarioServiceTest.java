package com.ekan.controledebeneficiarioapi.domain.service;

import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.model.dto.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.model.dto.DocumentoDTO;
import com.ekan.controledebeneficiarioapi.domain.repository.BeneficiarioRepository;
import com.ekan.controledebeneficiarioapi.domain.repository.DocumentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.ekan.controledebeneficiarioapi.enums.TipoDocumento.RG;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@ContextConfiguration(
        classes = { H2DataSource.class },
        loader = AnnotationConfigContextLoader.class)*/
@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class BeneficiarioServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BeneficiarioRepository beneficiarioRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BeneficiarioService beneficiarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        beneficiarioRepository.save(createBeneficiario());
    }

    private BeneficiarioDTO createBeneficiarioDocumento() {
        BeneficiarioDTO beneficiario = new BeneficiarioDTO();
        Documento documento = new Documento();
        documento.setDescricao("funciona");
        documento.setTipoDocumento(RG);
        documento.setDataInclusao(LocalDate.now().minusDays(1));
        documento.setDataAtualizacao(LocalDate.now());
        beneficiario.setNome("João Silva");
        beneficiario.setTelefone("11 99999-9999");
        beneficiario.setDataNascimento("1994-12-12");
        beneficiario.setDataInclusao("1994-12-12");
        beneficiario.setDataAtualizacao("1994-12-12");
        beneficiario.setDocumentos(Arrays.asList(documento));
//        BeneficiarioDTO beneficiario = beneficiarioRepository.findAll().get(0);


        return beneficiario;
    }

    private Beneficiario createBeneficiario() {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setNome("João Silva");
        beneficiario.setTelefone("11 99999-9999");
        beneficiario.setDataNascimento(LocalDate.now());
        beneficiario.setDataInclusao(LocalDate.now());
        beneficiario.setDataAtualizacao(LocalDate.now());
        return beneficiario;
    }

    private Beneficiario createBeneficiarioAtualizacao() {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setNome("John wick");
        beneficiario.setTelefone("11 666667777");
        beneficiario.setDataNascimento(LocalDate.now());
        beneficiario.setDataInclusao(LocalDate.now());
        beneficiario.setDataAtualizacao(LocalDate.now());
        return beneficiario;
    }

    @Test
    public void QuandoBeneficiarioNaoEncontrado() throws Exception {
        assertEquals("Given a beneficiario stored inside database on setup", 1, beneficiarioRepository.findAll().size());

        //ter o estado do banco antes da chamada de delete
        this.mockMvc.perform(delete(String.format("/beneficiarios/%d", Integer.MAX_VALUE))).andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Beneficiario não encontrado"));

        assertEquals("...should keep the same information inside database", 1, beneficiarioRepository.findAll().size());
        //ter o estado do banco apos a chamada
    }

    @Test
    public void QuandoBeneficiarioEncontrado() throws Exception {
        assertEquals("Given a beneficiario stored inside database on setup", 1, beneficiarioRepository.findAll().size());
        Long id = beneficiarioRepository.findAll().get(0).getId();
        //ter o estado do banco antes da chamada de delete
        this.mockMvc.perform(delete(String.format("/beneficiarios/%d", id))).andExpect(status().isOk())
                .andExpect(jsonPath("message").value(String.format("beneficiario de id = %d deletado com sucesso.", id)));

        assertEquals("... should have removed one", 0, beneficiarioRepository.findAll().size());
        //ter o estado do banco apos a chamada
    }

    @Test
    public void QuandoBuscaBeneficiarios() throws Exception {

        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
        //ter o estado do banco antes da chamada de delete
        this.mockMvc.perform(get(String.format("/beneficiarios"))).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(beneficiarios)));
    }

    @Test
    public void AtualizaBeneficiario() throws Exception {

        Beneficiario beneficiario = createBeneficiarioAtualizacao();
        Beneficiario beneficiarioAtual = beneficiarioRepository.findAll().get(0);

        BeanUtils.copyProperties(beneficiario, beneficiarioAtual, "id");

        this.mockMvc.perform(put(String.format("/beneficiarios/%d", beneficiarioAtual.getId()))
                        .content(objectMapper.writeValueAsString(beneficiario))
                        .contentType("application/json")).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(beneficiarioAtual)));
    }

    @Test
    public void InsereBeneficiario() throws Exception {


        BeneficiarioDTO beneficiario = createBeneficiarioDocumento();
        this.mockMvc.perform(post(String.format("/beneficiarios"))
                        .content(objectMapper.writeValueAsString(beneficiario))
                        .contentType("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(beneficiario.getNome()))
                .andExpect(jsonPath("$.telefone").value(beneficiario.getTelefone()))
                .andExpect(jsonPath("$.dataNascimento").value(beneficiario.getDataNascimento()))
                .andExpect(jsonPath("$.dataInclusao").value(beneficiario.getDataInclusao()))
                .andExpect(jsonPath("$.dataAtualizacao").value(beneficiario.getDataAtualizacao()))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }


//
//    @Test
//    public void salva() {
//        // given
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDto();
//
//        // when
//        BeneficiarioDTO result = beneficiarioService.salvar(beneficiarioDto);
//
//        when(beneficiarioRepository.save(any(Beneficiario.class))).thenReturn(createBeneficiarioDto());
//        when(documentoService.salvaDocumento(any(Documento.class))).thenReturn(new Documento());
//        // then
//        assertThat(result.getId()).isNotNull();
//        assertThat(result.getNome()).isEqualTo("João Silva");
//        assertThat(result.getTelefone()).isEqualTo("11 99999-9999");
//        assertThat(result.getDocumentos()).hasSize(2);
//    }
//
//    @Test
//    public void salvar_QuandoDatasInvalidas_LancaPreenchimentoIncorretoException() {
//        // given
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDtoWithInvalidDate();
//
//        // then
//        assertThatThrownBy(() -> beneficiarioService.salvar(beneficiarioDto))
//                .isInstanceOf(PreenchimentoIncorretoException.class)
//                .hasMessageContaining("Data de beneficiario fora do padrao");
//    }
//
//
//    @Test
//    public void atualizaBeneficiario_QuandoBeneficiarioExisteAtualizaERetornaBeneficiario() {
//        // given
//        Long id = 1L;
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDto();
//        Beneficiario beneficiario = createBeneficiario();
//        when(beneficiarioRepository.findById(anyLong())).thenReturn(Optional.of(beneficiario));
//        when(beneficiarioRepository.save(any(Beneficiario.class))).thenReturn(beneficiario);
//
//        // when
//        Beneficiario result = beneficiarioService.atualizaBeneficiario(id, beneficiarioDto);
//
//        // then
//        assertThat(result.getId()).isEqualTo(id);
//        assertThat(result.getNome()).isEqualTo("João Silva");
//        assertThat(result.getTelefone()).isEqualTo("11 99999-9999");
//    }
//
//
//    private BeneficiarioDTO createBeneficiarioDto() {
//        BeneficiarioDTO beneficiarioDto = new BeneficiarioDTO();
//        beneficiarioDto.setId(1L);
//        beneficiarioDto.setNome("João Silva");
//        beneficiarioDto.setTelefone("11 99999-9999");
//        beneficiarioDto.setDataNascimento("1990-01-01");
//        beneficiarioDto.setDataInclusao("2022-01-01");
//        beneficiarioDto.setDataAtualizacao("2022-04-19");
//        Documento documento1 = new Documento();
//        documento1.setTipoDocumento(RG);
//        documento1.setId(1L);
//        documento1.setDataInclusao(LocalDate.now().minusDays(1));
//        documento1.setDataAtualizacao(LocalDate.now());
//        Documento documento2 = new Documento();
//        documento2.setTipoDocumento(CPF);
//        documento2.setId(2L);
//        documento2.setDataInclusao(LocalDate.now().minusDays(1));
//        documento2.setDataAtualizacao(LocalDate.now());
//        beneficiarioDto.setDocumentos(Arrays.asList(documento1, documento2));
//        return beneficiarioDto;
//    }
//
//
//    private BeneficiarioDTO createBeneficiarioDtoWithInvalidDate() {
//        BeneficiarioDTO beneficiarioDto = new BeneficiarioDTO();
//        beneficiarioDto.setId(1L);
//        beneficiarioDto.setNome("João Silva");
//        beneficiarioDto.setTelefone("11 99999-9999");
//        beneficiarioDto.setDataNascimento("1990-01-01");
//        beneficiarioDto.setDataInclusao("2022-01-01");
//        beneficiarioDto.setDataAtualizacao("2022-04-19");
//        Documento documento1 = new Documento();
//        documento1.setTipoDocumento(RG);
//        documento1.setId(1L);
//        documento1.setDataInclusao(LocalDate.now().minusDays(1));
//        documento1.setDataAtualizacao(LocalDate.now());
//        return beneficiarioDto;
//    }

}
