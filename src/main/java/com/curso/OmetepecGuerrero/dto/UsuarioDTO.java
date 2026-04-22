package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String rol;
    private String plataforma;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoLogin;
}
