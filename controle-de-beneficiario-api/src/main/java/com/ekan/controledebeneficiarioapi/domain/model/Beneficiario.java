package com.ekan.controledebeneficiarioapi.domain.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
public class Beneficiario {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beneficiario_id")
    private Long id;

    private String nome;

    private String telefone;

    @Column(name = "data_Nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    public Beneficiario(){}

    public Beneficiario(Long id, String nome, String telefone, LocalDate dataNascimento, LocalDate dataInclusao, LocalDate dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.dataInclusao = dataInclusao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
