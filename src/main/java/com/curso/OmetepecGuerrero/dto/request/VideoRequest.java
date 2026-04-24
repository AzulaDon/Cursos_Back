package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {
    
    @NotNull(message = "El ID del curso es requerido")
    private Integer cursoId;
    
    @NotBlank(message = "El título es requerido")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "La URL del video es requerida")
    @Size(max = 500, message = "La URL no puede exceder 500 caracteres")
    private String urlStream;
    
    @NotNull(message = "El orden de secuencia es requerido")
    private Integer ordenSecuencia;
    
    private Integer duracionSeg;
}
