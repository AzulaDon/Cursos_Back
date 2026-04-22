package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    
    List<Inscripcion> findByUsuarioId(Integer usuarioId);
    
    List<Inscripcion> findByCursoId(Integer cursoId);
    
    Optional<Inscripcion> findByUsuarioIdAndCursoId(Integer usuarioId, Integer cursoId);
    
    boolean existsByUsuarioIdAndCursoId(Integer usuarioId, Integer cursoId);
    
    List<Inscripcion> findByUsuarioIdAndCompletado(Integer usuarioId, Boolean completado);
}
