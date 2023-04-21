package com.ekan.controledebeneficiarioapi.validador;

import com.ekan.controledebeneficiarioapi.domain.exceptions.PreenchimentoIncorretoException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidadorDataUtil {

    public static List<LocalDate> validacaoDatas(String...dates) {
        try {
            return Arrays.stream(dates).map(LocalDate::parse).collect(Collectors.toList());

        } catch (DateTimeException e) {
            throw new PreenchimentoIncorretoException("Data de beneficiario fora do padrao: (yyyy-MM-dd) ");
        }
    }
}
