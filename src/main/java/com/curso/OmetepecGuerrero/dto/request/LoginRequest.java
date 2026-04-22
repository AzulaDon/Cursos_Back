package com.curso.OmetepecGuerrero.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "El correo es requerido")
    @Email(message = "El formato del correo no es válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
