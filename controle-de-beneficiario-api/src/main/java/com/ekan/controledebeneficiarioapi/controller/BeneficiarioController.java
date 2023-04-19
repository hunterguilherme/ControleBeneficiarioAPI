package com.ekan.controledebeneficiarioapi.controller;

import com.ekan.controledebeneficiarioapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import com.ekan.controledebeneficiarioapi.domain.model.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.service.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/beneficiarios")
public class BeneficiarioController {


    @Autowired
    private BeneficiarioService beneficiarioService;

    @PostMapping
    public ResponseEntity<?> insereBeneficiario(@RequestBody @Valid BeneficiarioDTO beneficiarioDTO) {

            return ResponseEntity.ok(beneficiarioService.salvar(beneficiarioDTO));

    }

    @GetMapping
    public ResponseEntity<?> getBeneficiarios() {
        return ResponseEntity.ok(beneficiarioService.getBeneficiario());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizaBeneficiario(@PathVariable Long id, @RequestBody BeneficiarioDTO beneficiarioDTO) {
        return ResponseEntity.ok(beneficiarioService.atualizaBenficiario(id, beneficiarioDTO));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> removeBeneficiario(@PathVariable Long id, @RequestBody BeneficiarioDTO beneficiarioDTO) {
        beneficiarioService.excluir(id);
        return ResponseEntity.ok(String.format("beneficiario de id = %s deletado com sucesso", id.toString()));
    }

//    @GetMapping(value = "/v1/folhas-de-ponto/{mes}",produces = { "application/json" })
//    public ResponseEntity<?> geraRelatorioMensal(@PathVariable String mes) {
//        return ResponseEntity.ok(relatorioMensalService.getRelatorio(mes));
//
//    }
}
