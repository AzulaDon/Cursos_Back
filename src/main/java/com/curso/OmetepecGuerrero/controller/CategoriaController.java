package com.curso.OmetepecGuerrero.controller;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> obtenerCategorias() {
        return ResponseEntity.ok(ApiResponse.success(cursoService.obtenerCategorias()));
    }
}
