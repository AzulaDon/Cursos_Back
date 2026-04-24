package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    
    Optional<Calificacion> findByInscripcionId(Integer inscripcionId);
    
    @Query("SELECT c FROM Calificacion c WHERE c.inscripcion.curso.id = :cursoId")
    List<Calificacion> findByCursoId(@Param("cursoId") Integer cursoId);
    
    @Query("SELECT AVG(c.estrellas) FROM Calificacion c WHERE c.inscripcion.curso.id = :cursoId")
    Double getPromedioEstrellasByCursoId(@Param("cursoId") Integer cursoId);
    
    @Query("SELECT COUNT(c) FROM Calificacion c WHERE c.inscripcion.curso.id = :cursoId")
    Long countByCursoId(@Param("cursoId") Integer cursoId);
}
