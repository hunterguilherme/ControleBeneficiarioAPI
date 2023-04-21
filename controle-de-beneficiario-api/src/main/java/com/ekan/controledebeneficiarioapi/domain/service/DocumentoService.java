package com.ekan.controledebeneficiarioapi.domain.service;

import com.ekan.controledebeneficiarioapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.model.dto.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.model.dto.DocumentoDTO;
import com.ekan.controledebeneficiarioapi.domain.repository.BeneficiarioRepository;
import com.ekan.controledebeneficiarioapi.domain.repository.DocumentoRepository;
import com.ekan.controledebeneficiarioapi.enums.TipoDocumento;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentoService {
    private static final String DOCUMENT_NOT_FOUND = "Documento não encontrado";
    private static final String BENEFICIARIO_NOT_FOUND = "Beneficiário não encontrado";

    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    public void salvaDocumento(Documento documento) {
        documentoRepository.save(documento);
    }

    public void excluiDocumento(Long id) {
        documentoRepository.deleteAllByIdBeneficiario(id);
    }

    public DocumentoDTO getDocumentos(Long id) {

        List<Documento> documentos = documentoRepository.findDocumentosByBeneficiario(id);

        DocumentoDTO documentosDTO = new DocumentoDTO();
        documentosDTO.setIdBeneficiario(id);
        documentosDTO.setDocumentos(documentos);

        return documentosDTO;

    }

    public Map<String, String> excluir(Long id) {
        try {
            documentoRepository.deleteById(id);
            String message = String.format("documento de id = %s deletado com sucesso.", id.toString());
            Map<String, String> messagemMap = new HashMap<>();
            messagemMap.put("message", message);
            return messagemMap;
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(DOCUMENT_NOT_FOUND);
        }

    }

    public Documento atualizaDocumento(Long id, Documento documento) {
        beneficiarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(BENEFICIARIO_NOT_FOUND));
        try {

            Documento documentoAtual = documentoRepository.findByBeneficiarioAndDocumentType(id, documento.getTipoDocumento().ordinal());
            BeanUtils.copyProperties(documento, documentoAtual, "id", "beneficiario");
            return documentoRepository.save(documentoAtual);

        } catch (IllegalArgumentException e) {
            throw new EntidadeNaoEncontradaException(DOCUMENT_NOT_FOUND);
        }
    }

}
