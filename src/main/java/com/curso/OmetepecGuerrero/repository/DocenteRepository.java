package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
    
    Optional<Docente> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);
    
    List<Docente> findByEspecialidadContainingIgnoreCase(String especialidad);
}
