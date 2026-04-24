package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.DocenteDTO;
import com.curso.OmetepecGuerrero.dto.request.DocenteRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.security.UserDetailsImpl;
import com.curso.OmetepecGuerrero.service.DocenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DocenteDTO>>> obtenerTodos() {
        return ResponseEntity.ok(ApiResponse.success(docenteService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(docenteService.obtenerPorId(id)));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<DocenteDTO>>> buscarPorEspecialidad(
            @RequestParam String especialidad) {
        return ResponseEntity.ok(ApiResponse.success(docenteService.buscarPorEspecialidad(especialidad)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DocenteDTO>> crear(
            @Valid @RequestBody DocenteRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        DocenteDTO docente = docenteService.crear(request, userDetails.getId());
        return ResponseEntity.ok(ApiResponse.success("Docente creado exitosamente", docente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DocenteRequest request) {
        DocenteDTO docente = docenteService.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.success("Docente actualizado exitosamente", docente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        docenteService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success("Docente eliminado exitosamente", null));
    }
}
