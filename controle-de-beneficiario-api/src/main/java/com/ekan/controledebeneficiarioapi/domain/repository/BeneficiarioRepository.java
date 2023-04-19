package com.ekan.controledebeneficiarioapi.domain.repository;

import com.ekan.controledebeneficiarioapi.domain.model.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

}
