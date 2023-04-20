package com.ekan.controledebeneficiarioapi.controller;

import com.ekan.controledebeneficiarioapi.domain.service.DocumentoService;
import com.ekan.controledebeneficiarioapi.filter.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Authorize
@RestController
@RequestMapping(value = "documentos")
public class DocumentoController {


    @Autowired
    private DocumentoService documentoService;
//    @PostMapping(value = "", produces = { "application/json" })
//    public ResponseEntity<?> insereBatida(@RequestBody Documento documento)  {
//
//        return ResponseEntity.ok(documentoService.registraPonto(momento));
//    }

    @GetMapping(value = "{id}",produces = { "application/json" })
    public ResponseEntity<?> getDocumentosBeneficiario(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.getDocumentos(id));

    }
}
