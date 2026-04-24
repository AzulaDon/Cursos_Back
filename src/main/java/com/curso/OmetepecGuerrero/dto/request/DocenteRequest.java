package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteRequest {
    
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
    private String nombre;
    
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El formato del correo no es válido")
    private String correo;
    
    @NotBlank(message = "La especialidad es requerida")
    @Size(max = 200, message = "La especialidad no puede exceder 200 caracteres")
    private String especialidad;
}
