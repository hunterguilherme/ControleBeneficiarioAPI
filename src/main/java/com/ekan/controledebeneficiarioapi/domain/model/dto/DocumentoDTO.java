package com.ekan.controledebeneficiarioapi.domain.model.dto;

import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import lombok.Data;

import java.util.List;
@Data
public class DocumentoDTO {

    private Long idBeneficiario;

    List<Documento> documentos;


}
