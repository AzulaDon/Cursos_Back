package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {
}
