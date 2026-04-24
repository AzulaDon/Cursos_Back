package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionRequest {
    
    @NotNull(message = "Las estrellas son requeridas")
    @Min(value = 1, message = "Mínimo 1 estrella")
    @Max(value = 5, message = "Máximo 5 estrellas")
    private Byte estrellas;
    
    private String comentario;
}
