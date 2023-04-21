package com.ekan.controledebeneficiarioapi.ExceptionHandler;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ApiErrorMessage {

    private String message;


}
