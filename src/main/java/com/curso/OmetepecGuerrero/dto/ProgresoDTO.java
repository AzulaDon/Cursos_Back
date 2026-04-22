package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgresoDTO {
    private Integer id;
    private Integer inscripcionId;
    private Integer videoId;
    private String videoTitulo;
    private Integer ultimoSegundo;
    private Boolean visto;
    private LocalDateTime fechaVista;
}
