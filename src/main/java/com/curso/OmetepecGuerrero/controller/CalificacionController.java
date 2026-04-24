package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CalificacionDTO;
import com.curso.OmetepecGuerrero.dto.request.CalificacionRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.service.CalificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<CalificacionDTO>>> obtenerPorCurso(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(ApiResponse.success(calificacionService.obtenerPorCurso(cursoId)));
    }

    @GetMapping("/curso/{cursoId}/promedio")
    public ResponseEntity<ApiResponse<Double>> obtenerPromedio(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(ApiResponse.success(calificacionService.obtenerPromedio(cursoId)));
    }

    @GetMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<ApiResponse<CalificacionDTO>> obtenerPorInscripcion(
            @PathVariable Integer inscripcionId) {
        return ResponseEntity.ok(ApiResponse.success(calificacionService.obtenerPorInscripcion(inscripcionId)));
    }

    @PostMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<ApiResponse<CalificacionDTO>> calificar(
            @PathVariable Integer inscripcionId,
            @Valid @RequestBody CalificacionRequest request) {
        CalificacionDTO calificacion = calificacionService.calificar(inscripcionId, request);
        return ResponseEntity.ok(ApiResponse.success("Calificación registrada exitosamente", calificacion));
    }
}
