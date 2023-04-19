package com.ekan.controledebeneficiarioapi.domain.service;

import com.ekan.controledebeneficiarioapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.dto.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.repository.BeneficiarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BeneficiarioService {
    private static final String NOT_FOUND = "Beneficiario não encontrado";
    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Autowired
    private DocumentoService documentoService;

    @Transactional
    public BeneficiarioDTO salvar(BeneficiarioDTO beneficiarioDTO) {

        validacaoDatas(beneficiarioDTO);

        Beneficiario beneficiario = Beneficiario.builder()
                .nome(beneficiarioDTO.getNome())
                .telefone(beneficiarioDTO.getTelefone())
                .dataNascimento(stringToDate(beneficiarioDTO.getDataNascimento()))
                .dataInclusao(stringToDate(beneficiarioDTO.getDataInclusao()))
                .dataAtualizacao(stringToDate(beneficiarioDTO.getDataAtualizacao()))
                .build();

        beneficiario = beneficiarioRepository.save(beneficiario);

        List<Documento> documentos = validaDocumento(beneficiarioDTO.getDocumentos(), beneficiario.getId());

        beneficiarioDTO.setId(beneficiario.getId());
        beneficiarioDTO.setDocumentos(documentos);

        return beneficiarioDTO;
    }

    private List<Documento> validaDocumento(List<Documento> documentos, Long idBeneficiario) {
        Beneficiario beneficiario = buscarOuFalhar(idBeneficiario);

        documentos.forEach(documento -> {
            documento.setBeneficiario(beneficiario);
            documentoService.salvaDocumento(documento);
        });

        return documentos;
    }

    public Beneficiario atualizaBenficiario(Long id, BeneficiarioDTO beneficiario) {
        Beneficiario beneficiarioAtual = buscarOuFalhar(id);
        validacaoDatas(beneficiario);
        BeanUtils.copyProperties(beneficiario, beneficiarioAtual, "id", "documentos");
        return beneficiarioRepository.save(beneficiarioAtual);
    }


    public Beneficiario buscarOuFalhar(Long id) {
        return beneficiarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND));
    }

    public List<Beneficiario> getBeneficiario() {
        return beneficiarioRepository.findAll();
    }

    @Transactional
    public Map<String, String> excluir(Long id) {
        try {
            documentoService.excluiDocumento(id);
            beneficiarioRepository.deleteById(id);

            String message = String.format("beneficiario de id = %s deletado com sucesso.", id.toString());
            Map<String, String> messagemMap = new HashMap<>();
            messagemMap.put("message", message);
            return messagemMap;
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(NOT_FOUND);
        }

    }


    private void validacaoDatas(BeneficiarioDTO beneficiarioDTO) {
        try {
            LocalDate.parse(beneficiarioDTO.getDataAtualizacao());
            LocalDate.parse(beneficiarioDTO.getDataInclusao());
            LocalDate.parse(beneficiarioDTO.getDataNascimento());
        } catch (DateTimeException e) {
            throw new PreenchimentoIncorretoException("Data de beneficiario fora do padrao: (yyyy-MM-dd) ");
        }
    }

    private LocalDate stringToDate(String date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataFormatada = LocalDate.parse(date, formatter);
            return dataFormatada;
    }

}
