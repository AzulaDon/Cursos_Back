package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Integer> {
    
    Optional<Certificado> findByCodigoVerificacion(String codigoVerificacion);
    
    Optional<Certificado> findByInscripcionId(Integer inscripcionId);
    
    @Query("SELECT c FROM Certificado c WHERE c.inscripcion.usuario.id = :usuarioId")
    List<Certificado> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    boolean existsByInscripcionId(Integer inscripcionId);
}
