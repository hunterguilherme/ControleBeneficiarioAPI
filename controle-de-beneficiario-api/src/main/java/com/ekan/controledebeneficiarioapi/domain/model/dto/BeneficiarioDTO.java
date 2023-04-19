package com.ekan.controledebeneficiarioapi.domain.model.dto;

import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BeneficiarioDTO {

    @ApiModelProperty(notes = "Beneficiario ID", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "nome", example = "John Wick", required = true)
    private String nome;

    @ApiModelProperty(notes = "telefone", example = "11 95798-9898", required = true)
    private String telefone;

    @ApiModelProperty(notes = "data de nascimento", example = "1990-09-13", required = true)
    private String dataNascimento;

    @ApiModelProperty(notes = "data de inclusao", example = "2000-11-27", required = true)
    private String dataInclusao;

    @ApiModelProperty(notes = "data de atualizacao", example = "2015-09-04", required = true)
    private String dataAtualizacao;

    private List<Documento> documentos;


//    public BeneficiarioDTO getBeneficiario(Beneficiario beneficiario) {
//        BeanUtils.copyProperties(beneficiario, this, "id", "documentos");
//        return this;
//
//    }
}

