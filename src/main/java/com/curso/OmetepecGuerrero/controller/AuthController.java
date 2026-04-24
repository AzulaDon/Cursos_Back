package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.request.LoginRequest;
import com.curso.OmetepecGuerrero.dto.request.RegistroRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.dto.response.AuthResponse;
import com.curso.OmetepecGuerrero.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<AuthResponse>> registrar(@Valid @RequestBody RegistroRequest request) {
        AuthResponse response = authService.registrar(request);
        return ResponseEntity.ok(ApiResponse.success("Usuario registrado exitosamente", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso", response));
    }
}
