package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequest {
    
    @NotNull(message = "El ID del docente es requerido")
    private Integer docenteId;
    
    @NotBlank(message = "El título es requerido")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    private String descripcion;
    
    private String portadaUrl;
    
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;
    
    private String estatus;
}
