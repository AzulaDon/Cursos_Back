package com.curso.OmetepecGuerrero.repository;

import com.curso.OmetepecGuerrero.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    
    List<Video> findByCursoIdOrderByOrdenSecuenciaAsc(Integer cursoId);
    
    @Query("SELECT COUNT(v) FROM Video v WHERE v.curso.id = :cursoId")
    Integer countByCursoId(@Param("cursoId") Integer cursoId);
    
    @Query("SELECT SUM(v.duracionSeg) FROM Video v WHERE v.curso.id = :cursoId")
    Integer sumDuracionByCursoId(@Param("cursoId") Integer cursoId);
}
