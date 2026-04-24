package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer cursoId;
    private String cursoTitulo;
    private String cursoPortadaUrl;
    private LocalDateTime fechaInicio;
    private BigDecimal porcentaje;
    private Boolean completado;
    private LocalDateTime fechaTermino;
    private Integer videosVistos;
    private Integer totalVideos;
}
