package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocenteDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String especialidad;
    private LocalDateTime fechaAlta;
}
