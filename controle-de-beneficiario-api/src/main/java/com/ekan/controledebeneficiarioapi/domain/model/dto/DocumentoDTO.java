package com.ekan.controledebeneficiarioapi.domain.model.dto;

import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.enums.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Data
public class DocumentoDTO {

    private Long idBeneficiario;

    List<Documento> documentos;


}
