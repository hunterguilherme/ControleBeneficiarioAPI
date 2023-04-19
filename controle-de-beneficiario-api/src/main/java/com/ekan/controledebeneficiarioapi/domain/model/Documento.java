package com.ekan.controledebeneficiarioapi.domain.model;

import com.ekan.controledebeneficiarioapi.enums.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.executable.ValidateOnExecution;
import java.time.LocalDate;

@Entity
@Data
public class Documento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    private String descricao;

    @Column(name = "data_inclusao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInclusao;

    @Column(name = "data_atualizacao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAtualizacao;

    @JsonIgnore
    @ManyToOne
    @Lazy
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Beneficiario beneficiario;

}
