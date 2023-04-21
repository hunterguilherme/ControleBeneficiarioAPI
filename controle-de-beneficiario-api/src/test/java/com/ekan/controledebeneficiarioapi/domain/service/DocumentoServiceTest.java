package com.ekan.controledebeneficiarioapi.domain.service;

import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.ekan.controledebeneficiarioapi.enums.TipoDocumento.RG;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DocumentoServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;
    @InjectMocks
    private DocumentoService documentoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        beneficiarioRepository.save(createBeneficiario());
        documentoRepository.save(createDocumento());
    }

    @Test
    public void QuandoDocumentoNaoEncontrado() throws Exception {
        assertEquals("Given a document stored inside database on setup", 1, documentoRepository.findAll().size());
        this.mockMvc.perform(delete(String.format("/documentos/%d", Integer.MAX_VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Documento não encontrado"));

        assertEquals("Given a document stored inside database on setup", 1, documentoRepository.findAll().size());

    }

    @Test
    public void QuandoDocumentoEncontrado() throws Exception {
        assertEquals("Given a document stored inside database on setup", 1, documentoRepository.findAll().size());
        Long id = documentoRepository.findAll().get(0).getId();
        //ter o estado do banco antes da chamada de delete
        this.mockMvc.perform(delete(String.format("/documentos/%d", id))).andExpect(status().isOk())
                .andExpect(jsonPath("message").value(String.format("documento de id = %s deletado com sucesso.", id)));

        assertEquals("... should have removed one", 0, documentoRepository.findAll().size());
        //ter o estado do banco apos a chamada
    }

    @Test
    public void QuandoBuscaDocumento() throws Exception {
        long id = beneficiarioRepository.findAll().get(0).getId();
        List<Documento> documentos = documentoRepository.findDocumentosByBeneficiario(id);
        DocumentoDTO documentoDTO = createDocumentoDTO(id, documentos);

        this.mockMvc.perform(get(String.format("/documentos/%d", id)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(documentoDTO)));

    }

    @Test
    public void AtualizaDocumento() throws Exception {

        Beneficiario beneficiario = beneficiarioRepository.findAll().get(0);
        Documento documentoBase = documentoRepository.findAll().get(0);
        Documento documento = createDocumentoAtualizacao();

        Documento documentoAtual = documentoRepository.findByBeneficiarioAndDocumentType(beneficiario.getId(), documentoBase.getTipoDocumento().ordinal());


        this.mockMvc.perform(put(String.format("/documentos/%d", beneficiario.getId()))
                .content(objectMapper.writeValueAsString(documentoBase))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(documentoBase)));
    }

    private Documento createDocumento() {
        Beneficiario beneficiario = beneficiarioRepository.findAll().get(0);
        Documento documento = new Documento();
        documento.setTipoDocumento(RG);
        documento.setBeneficiario(beneficiario);
        documento.setDataInclusao(LocalDate.now().minusDays(1));
        documento.setDataAtualizacao(LocalDate.now());

        return documento;
    }

    private Documento createDocumentoAtualizacao() {

        Documento documento = new Documento();
        documento.setTipoDocumento(RG);
        documento.setDataInclusao(LocalDate.now().minusDays(1));
        documento.setDataAtualizacao(LocalDate.now());

        return documento;
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

    private DocumentoDTO createDocumentoDTO(long id, List<Documento> documentos) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setIdBeneficiario(id);
        documentoDTO.setDocumentos(documentos);
        return documentoDTO;
    }
}
