package com.ekan.controledebeneficiarioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ekan.controledebeneficiarioapi.controller",
				"com.ekan.controledebeneficiarioapi.domain.service",
				"com.ekan.controledebeneficiarioapi.domain.repository",
				"com.ekan.controledebeneficiarioapi.domain.exceptions",
				"com.ekan.controledebeneficiarioapi.ExceptionHandler",
				"com.ekan.controledebeneficiarioapi"})
public class ControleDeBeneficiarioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDeBeneficiarioApiApplication.class, args);
	}

}
