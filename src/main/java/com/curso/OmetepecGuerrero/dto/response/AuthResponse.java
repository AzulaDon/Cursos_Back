package com.curso.OmetepecGuerrero.dto.response;

import lombok.*;
import com.curso.OmetepecGuerrero.dto.UsuarioDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String tipo;
    private UsuarioDTO usuario;
}
