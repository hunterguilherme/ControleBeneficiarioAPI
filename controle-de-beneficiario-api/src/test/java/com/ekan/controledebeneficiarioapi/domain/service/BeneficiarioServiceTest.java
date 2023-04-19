package com.ekan.controledebeneficiarioapi.domain.service;

import static com.ekan.controledebeneficiarioapi.enums.TipoDocumento.CPF;
import static com.ekan.controledebeneficiarioapi.enums.TipoDocumento.RG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ekan.controledebeneficiarioapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.repository.BeneficiarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BeneficiarioServiceTest {
//
//    @Mock
//    private BeneficiarioRepository beneficiarioRepository;
//
//    @Mock
//    private DocumentoService documentoService;
//
//    @InjectMocks
//    private BeneficiarioService beneficiarioService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void salvar_QuandoBeneficiarioDtoValido_RetornaBeneficiarioDtoComId() {
//        // given
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDto();
//        when(beneficiarioRepository.save(Beneficiario).thenReturn(createBeneficiario());
//        when(documentoService.salvaDocumento(any(Documento.class))).thenReturn(new Documento());
//
//        // when
//        BeneficiarioDTO result = beneficiarioService.salvar(beneficiarioDto);
//
//        // then
//        assertThat(result.getId()).isNotNull();
//        assertThat(result.getNome()).isEqualTo("John Doe");
//        assertThat(result.getTelefone()).isEqualTo("(11) 98765-4321");
//        assertThat(result.getDocumentos()).hasSize(1);
//    }
//
//    @Test
//    void salvar_QuandoDatasInvalidas_LancaPreenchimentoIncorretoException() {
//        // given
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDtoWithInvalidDate();
//
//        // then
//        assertThatThrownBy(() -> beneficiarioService.salvar(beneficiarioDto))
//                .isInstanceOf(PreenchimentoIncorretoException.class)
//                .hasMessageContaining("Data de beneficiario fora do padrao");
//    }
//
//    @Test
//    void atualizaBeneficiario_QuandoBeneficiarioExisteAtualizaERetornaBeneficiario() {
//        // given
//        Long id = 1L;
//        BeneficiarioDTO beneficiarioDto = createBeneficiarioDto();
//        Beneficiario beneficiario = createBeneficiario();
//        when(beneficiarioRepository.findById(anyLong())).thenReturn(Optional.of(beneficiario));
//        when(beneficiarioRepository.save(any(Beneficiario.class))).thenReturn(beneficiario);
//
//        // when
//        Beneficiario result = beneficiarioService.atualizaBenficiario(id, beneficiarioDto);
//
//        // then
//        assertThat(result.getId()).isEqualTo(id);
//        assertThat(result.getNome()).isEqualTo("John Doe");
//        assertThat(result.getTelefone()).isEqualTo("(11) 987";
//    }
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
}
