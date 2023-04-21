package com.ekan.controledebeneficiarioapi.domain.model;

import com.ekan.controledebeneficiarioapi.enums.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Documento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento",nullable = false)
    @ApiModelProperty(notes = "Tipo de documento", example = "RG", required = true)
    private TipoDocumento tipoDocumento;

    @ApiModelProperty(notes = "Product ID", example = "Documento de identificação", required = true)
    private String descricao;

    @Column(name = "data_inclusao", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "Data de inclusao", example = "2000-11-27", required = true)
    private LocalDate dataInclusao;

    @Column(name = "data_atualizacao", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "Data de Atualizacao", example = "2015-09-04", required = true)
    private LocalDate dataAtualizacao;

    @JsonIgnore
    @ManyToOne
    @Lazy
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Beneficiario beneficiario;


}
