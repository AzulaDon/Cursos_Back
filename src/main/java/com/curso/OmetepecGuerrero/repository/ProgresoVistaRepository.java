package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.ProgresoVista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoVistaRepository extends JpaRepository<ProgresoVista, Integer> {
    
    List<ProgresoVista> findByInscripcionId(Integer inscripcionId);
    
    Optional<ProgresoVista> findByInscripcionIdAndVideoId(Integer inscripcionId, Integer videoId);
    
    @Query("SELECT COUNT(p) FROM ProgresoVista p WHERE p.inscripcion.id = :inscripcionId AND p.visto = true")
    Integer countVideosVistosByInscripcionId(@Param("inscripcionId") Integer inscripcionId);
}
