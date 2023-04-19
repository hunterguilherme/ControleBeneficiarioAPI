package com.ekan.controledebeneficiarioapi.domain.service;

import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.model.DocumentoDTO;
import com.ekan.controledebeneficiarioapi.domain.repository.DocumentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public void salvaDocumento(Documento documento) {
        documentoRepository.save(documento);
    }

    public void excluiDocumento(Long id) {
        documentoRepository.deleteAllByIdBeneficiario(id);
    }

    public DocumentoDTO getDocumentos(Long id) {

        List<Documento> documentos = documentoRepository.findDocumentosByBeneficiario(id);
        documentos.forEach(documento -> validacaoDatas(documento));

        DocumentoDTO documentosDTO = new DocumentoDTO();
        documentosDTO.setIdBeneficiario(id);
        documentosDTO.setDocumentos(documentos);

        return documentosDTO;

    }

    private void validacaoDatas(Documento documento) {
        try {

            LocalDate.parse(documento.getDataAtualizacao().toString());
            LocalDate.parse(documento.getDataInclusao().toString());

        } catch (DateTimeException e) {
            throw new PreenchimentoIncorretoException("Data de documento fora do padrao: (yyyy-MM-dd)");
        }
    }
}
