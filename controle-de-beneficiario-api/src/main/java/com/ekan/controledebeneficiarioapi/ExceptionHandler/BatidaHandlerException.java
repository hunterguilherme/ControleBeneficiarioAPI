package com.ekan.controledebeneficiarioapi.ExceptionHandler;


import com.ekan.controledebeneficiarioapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class BatidaHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PreenchimentoIncorretoException.class)
    public ResponseEntity<?> handlePreenchimentoIncorretoException(PreenchimentoIncorretoException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(apiErrorMessage);
    }



    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(
                "stage=message-not-readable, headers={}, status={}, request={}, exception={}, message={}, localizedMessage={}, cause={}",
                headers, status, request, ex, ex.getMessage(), ex.getLocalizedMessage(), ex.getCause());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body("Data Documento Fora do padrao (yyyy-MM-dd)");
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaExceptionn(EntidadeNaoEncontradaException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorMessage);
    }


}
