package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificacionDTO {
    private Integer id;
    private Integer inscripcionId;
    private Integer cursoId;
    private String cursoTitulo;
    private String usuarioNombre;
    private Byte estrellas;
    private String comentario;
    private LocalDateTime fechaResena;
}
