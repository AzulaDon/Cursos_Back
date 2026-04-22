package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.ProgresoDTO;
import com.curso.OmetepecGuerrero.dto.request.ProgresoRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.service.ProgresoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progreso")
@RequiredArgsConstructor
public class ProgresoController {

    private final ProgresoService progresoService;

    @GetMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<ApiResponse<List<ProgresoDTO>>> obtenerPorInscripcion(
            @PathVariable Integer inscripcionId) {
        return ResponseEntity.ok(ApiResponse.success(
                progresoService.obtenerProgresoPorInscripcion(inscripcionId)));
    }

    @GetMapping("/inscripcion/{inscripcionId}/video/{videoId}")
    public ResponseEntity<ApiResponse<ProgresoDTO>> obtenerProgresoVideo(
            @PathVariable Integer inscripcionId,
            @PathVariable Integer videoId) {
        return ResponseEntity.ok(ApiResponse.success(
                progresoService.obtenerProgresoVideo(inscripcionId, videoId)));
    }

    @PostMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<ApiResponse<ProgresoDTO>> guardarProgreso(
            @PathVariable Integer inscripcionId,
            @Valid @RequestBody ProgresoRequest request) {
        ProgresoDTO progreso = progresoService.guardarProgreso(inscripcionId, request);
        return ResponseEntity.ok(ApiResponse.success("Progreso guardado", progreso));
    }
}
