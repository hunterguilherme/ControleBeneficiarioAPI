package com.ekan.controledebeneficiarioapi.domain.model;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BeneficiarioDTO {


    private Long id;

    @NotBlank(message = "Campo obrigatório não informado")
    private String nome;

    private String telefone;

    private String dataNascimento;

    private String dataInclusao;

    private String dataAtualizacao;

    private List<Documento> documentos;


//    public BeneficiarioDTO getBeneficiario(Beneficiario beneficiario) {
//        BeanUtils.copyProperties(beneficiario, this, "id", "documentos");
//        return this;
//
//    }
}

