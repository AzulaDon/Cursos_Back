package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    
    List<Curso> findByEstatus(Curso.Estatus estatus);
    
    List<Curso> findByCategoria(String categoria);
    
    List<Curso> findByDocenteId(Integer docenteId);
    
    @Query("SELECT c FROM Curso c WHERE c.titulo LIKE %:keyword% OR c.descripcion LIKE %:keyword%")
    List<Curso> buscarPorKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT DISTINCT c.categoria FROM Curso c WHERE c.categoria IS NOT NULL")
    List<String> findAllCategorias();
}
