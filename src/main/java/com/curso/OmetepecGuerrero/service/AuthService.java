package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.UsuarioDTO;
import com.curso.OmetepecGuerrero.dto.request.LoginRequest;
import com.curso.OmetepecGuerrero.dto.request.RegistroRequest;
import com.curso.OmetepecGuerrero.dto.response.AuthResponse;
import com.curso.OmetepecGuerrero.entity.Usuario;
import com.curso.OmetepecGuerrero.exception.BadRequestException;
import com.curso.OmetepecGuerrero.repository.UsuarioRepository;
import com.curso.OmetepecGuerrero.security.JwtService;
import com.curso.OmetepecGuerrero.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        Usuario.Plataforma plataforma = Usuario.Plataforma.web;
        if (request.getPlataforma() != null) {
            try {
                plataforma = Usuario.Plataforma.valueOf(request.getPlataforma().toLowerCase());
            } catch (IllegalArgumentException e) {
                plataforma = Usuario.Plataforma.web;
            }
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .passHash(passwordEncoder.encode(request.getPassword()))
                .rol(Usuario.Rol.usuario)
                .plataforma(plataforma)
                .estado(true)
                .fechaRegistro(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);

        UserDetailsImpl userDetails = UserDetailsImpl.build(usuario);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .tipo("Bearer")
                .usuario(toDTO(usuario))
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .tipo("Bearer")
                .usuario(toDTO(usuario))
                .build();
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name())
                .plataforma(usuario.getPlataforma().name())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .ultimoLogin(usuario.getUltimoLogin())
                .build();
    }
}
