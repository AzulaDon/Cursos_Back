package com.curso.OmetepecGuerrero.controller;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.InscripcionDTO;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.security.UserDetailsImpl;
import com.curso.OmetepecGuerrero.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @GetMapping("/mis-cursos")
    public ResponseEntity<ApiResponse<List<InscripcionDTO>>> obtenerMisCursos(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(inscripcionService.obtenerPorUsuario(userDetails.getId())));
    }

    @GetMapping("/completados")
    public ResponseEntity<ApiResponse<List<InscripcionDTO>>> obtenerCompletados(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(inscripcionService.obtenerCompletados(userDetails.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InscripcionDTO>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(inscripcionService.obtenerPorId(id)));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<InscripcionDTO>> obtenerInscripcionCurso(
            @PathVariable Integer cursoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                inscripcionService.obtenerInscripcion(userDetails.getId(), cursoId)));
    }

    @GetMapping("/verificar/{cursoId}")
    public ResponseEntity<ApiResponse<Boolean>> verificarInscripcion(
            @PathVariable Integer cursoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                inscripcionService.estaInscrito(userDetails.getId(), cursoId)));
    }

    @PostMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<InscripcionDTO>> inscribirse(
            @PathVariable Integer cursoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        InscripcionDTO inscripcion = inscripcionService.inscribirse(userDetails.getId(), cursoId);
        return ResponseEntity.ok(ApiResponse.success("Te has inscrito al curso exitosamente", inscripcion));
    }
}
