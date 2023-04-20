package com.ekan.controledebeneficiarioapi.domain.repository;

import com.ekan.controledebeneficiarioapi.domain.model.Documento;
import com.ekan.controledebeneficiarioapi.enums.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    @Modifying
    @Query(value = "DELETE FROM DOCUMENTO d WHERE d.beneficiario_id = :id", nativeQuery = true)
    void deleteAllByIdBeneficiario(@Param("id") Long id);


    @Query(value = "SELECT * FROM documento AS d WHERE d.beneficiario_id = :id", nativeQuery = true)
    List<Documento> findDocumentosByBeneficiario(@Param("id") Long id);
    @Query(value = "SELECT * FROM documento AS d WHERE d.beneficiario_id = :id and d.tipo_documento = :tipoDocumento", nativeQuery = true)
     Documento findByBeneficiarioAndDocumentType(@Param("id")Long id, @Param("tipoDocumento") TipoDocumento tipoDocumento);
}
