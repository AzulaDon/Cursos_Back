package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoRequest {
    
    @NotNull(message = "El ID del video es requerido")
    private Integer videoId;
    
    @NotNull(message = "El segundo actual es requerido")
    @Min(value = 0, message = "El segundo no puede ser negativo")
    private Integer segundoActual;
}
