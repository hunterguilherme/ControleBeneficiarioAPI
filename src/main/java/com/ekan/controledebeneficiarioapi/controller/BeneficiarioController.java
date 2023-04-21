package com.ekan.controledebeneficiarioapi.controller;

import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.model.dto.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.service.BeneficiarioService;
import com.ekan.controledebeneficiarioapi.filter.Authorize;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Authorize
@RestController
@RequestMapping(value = "/beneficiarios")
public class BeneficiarioController {
    @Autowired
    private BeneficiarioService beneficiarioService;

    @ApiOperation(value = "insere Beneficiario", nickname = "insereBeneficiario", notes = "insere beneficiario com seus documentos", response = BeneficiarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Beneficiario Inserido com documentos", response = BeneficiarioDTO.class),
            @ApiResponse(code = 400, message = "Beneficiario fora do padrao: (yyyy-MM-dd)/ Documento fora do padrao: (yyyy-MM-dd)/ tipo Documento invalido")})
    @RequestMapping(consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<?> insereBeneficiario(@RequestBody @Valid BeneficiarioDTO beneficiarioDTO) {

            return ResponseEntity.ok(beneficiarioService.salvar(beneficiarioDTO));


    }

    @ApiOperation(value = "busca Beneficiarios", nickname = "getBeneficiario", notes = "busca apenas a lista de beneficiario", response = Beneficiario.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Beneficiarios", response = Beneficiario.class),
            @ApiResponse(code = 404, message = "Beneficiarios não encontrados")})
    @RequestMapping(produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<?> getBeneficiarios() {
        return ResponseEntity.ok(beneficiarioService.getBeneficiario());
    }

    @ApiOperation(value = "atualiza beneficiario", nickname = "atualizaBeneficiario", notes = "atualiza unicamente beneficiario", response = Beneficiario.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "beneficiario atualizado", response = Beneficiario.class),
            @ApiResponse(code = 400, message = "Data Fora do padrao: (yyyy-MM-dd)"),
            @ApiResponse(code = 404, message = "Beneficiario não encontrado")})
    @RequestMapping(value = "{id}",produces = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaBeneficiario(@PathVariable Long id, @RequestBody BeneficiarioDTO beneficiarioDTO) {
        return ResponseEntity.ok(beneficiarioService.atualizaBenficiario(id, beneficiarioDTO));
    }


    @ApiOperation(value = "Remove Beneficiarios", nickname = "removeBeneficiario", notes = "Remove beneficiario e seus documentos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Beneficiario com id = x removido com sucesso."),
            @ApiResponse(code = 404, message = "Beneficiario não encontrado")})
    @RequestMapping(value = "{id}",produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<?> removeBeneficiario(@PathVariable Long id) {
            return ResponseEntity.ok(beneficiarioService.excluir(id)) ;
    }

}
