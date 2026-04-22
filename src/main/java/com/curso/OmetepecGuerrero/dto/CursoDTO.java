package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String portadaUrl;
    private String categoria;
    private String estatus;
    private DocenteDTO docente;
    private Integer totalVideos;
    private Integer duracionTotalSeg;
    private Double promedioEstrellas;
    private Long totalResenas;
    private List<VideoDTO> videos;
}
