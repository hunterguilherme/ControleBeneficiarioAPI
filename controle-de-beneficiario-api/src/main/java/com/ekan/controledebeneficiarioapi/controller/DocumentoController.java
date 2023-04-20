package com.ekan.controledebeneficiarioapi.controller;

import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.domain.model.dto.BeneficiarioDTO;
import com.ekan.controledebeneficiarioapi.domain.service.DocumentoService;
import com.ekan.controledebeneficiarioapi.filter.Authorize;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Authorize
@RestController
@RequestMapping(value = "documentos")
public class DocumentoController {


    @Autowired
    private DocumentoService documentoService;
    @ApiOperation(value = "Remove Documento", nickname = "removeDocumento", notes = "Remove documento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "documento com id = x removido com sucesso."),
            @ApiResponse(code = 404, message = "documento não encontrado")})
    @RequestMapping(value = "{id}",produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<?> removeBeneficiario(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.excluir(id)) ;
    }

    @GetMapping(value = "{id}",produces = { "application/json" })
    public ResponseEntity<?> getDocumentosBeneficiario(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.getDocumentos(id));
    }

    @ApiOperation(value = "atualiza documento", nickname = "atualizadocumento", notes = "atualiza unicamente documento", response = Documento.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Documento atualizado com sucesso", response = Documento.class),
            @ApiResponse(code = 400, message = "Data Documento Fora do padrao: (yyyy-MM-dd) ou tipo de documento invalido."),
            @ApiResponse(code = 404, message = "Documento não encontrado")})
    @RequestMapping(value = "{id}",produces = { "application/json" }, method = RequestMethod.PUT)
    public ResponseEntity<?> atualizadocumento(@PathVariable Long id, @RequestBody Documento documento) {
        return ResponseEntity.ok(documentoService.atualizaDocumento(id, documento));
    }
}
