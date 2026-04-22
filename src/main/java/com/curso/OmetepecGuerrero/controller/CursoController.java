package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CursoDTO;
import com.curso.OmetepecGuerrero.dto.request.CursoRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CursoDTO>>> obtenerTodos() {
        return ResponseEntity.ok(ApiResponse.success(cursoService.obtenerActivos()));
    }

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> obtenerTodosAdmin() {
        return ResponseEntity.ok(ApiResponse.success(cursoService.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoDTO>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(cursoService.obtenerPorId(id)));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(ApiResponse.success(cursoService.obtenerPorCategoria(categoria)));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.success(cursoService.buscar(q)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CursoDTO>> crear(@Valid @RequestBody CursoRequest request) {
        CursoDTO curso = cursoService.crear(request);
        return ResponseEntity.ok(ApiResponse.success("Curso creado exitosamente", curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoDTO>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CursoRequest request) {
        CursoDTO curso = cursoService.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.success("Curso actualizado exitosamente", curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        cursoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success("Curso eliminado exitosamente", null));
    }
}
