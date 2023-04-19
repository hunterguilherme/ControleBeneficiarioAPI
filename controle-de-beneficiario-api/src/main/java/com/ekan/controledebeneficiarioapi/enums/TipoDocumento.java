package com.ekan.controledebeneficiarioapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum TipoDocumento implements Serializable {
    RG, CPF, CNH, CERTIDAO_NASCIMENTO, CERTIDAO_CASAMENTO, OBITO;


}
