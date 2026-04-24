package com.curso.OmetepecGuerrero.controller;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CertificadoDTO;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.security.UserDetailsImpl;
import com.curso.OmetepecGuerrero.service.CertificadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificados")
@RequiredArgsConstructor
public class CertificadoController {

    private final CertificadoService certificadoService;

    @GetMapping("/mis-certificados")
    public ResponseEntity<ApiResponse<List<CertificadoDTO>>> obtenerMisCertificados(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(certificadoService.obtenerPorUsuario(userDetails.getId())));
    }

    @GetMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<ApiResponse<CertificadoDTO>> obtenerPorInscripcion(
            @PathVariable Integer inscripcionId) {
        return ResponseEntity.ok(ApiResponse.success(certificadoService.obtenerPorInscripcion(inscripcionId)));
    }

    @GetMapping("/verificar/{codigo}")
    public ResponseEntity<ApiResponse<CertificadoDTO>> verificar(@PathVariable String codigo) {
        return ResponseEntity.ok(ApiResponse.success(certificadoService.verificar(codigo)));
    }

    @PostMapping("/generar/{inscripcionId}")
    public ResponseEntity<ApiResponse<CertificadoDTO>> generar(@PathVariable Integer inscripcionId) {
        CertificadoDTO certificado = certificadoService.generar(inscripcionId);
        return ResponseEntity.ok(ApiResponse.success("Certificado generado exitosamente", certificado));
    }
}
