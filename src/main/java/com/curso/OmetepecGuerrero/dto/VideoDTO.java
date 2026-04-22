package com.curso.OmetepecGuerrero.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDTO {
    private Integer id;
    private Integer cursoId;
    private String titulo;
    private String urlStream;
    private Integer ordenSecuencia;
    private Integer duracionSeg;
}
