package com.curso.OmetepecGuerrero.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.VideoDTO;
import com.curso.OmetepecGuerrero.dto.request.VideoRequest;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<VideoDTO>>> obtenerPorCurso(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(ApiResponse.success(videoService.obtenerPorCurso(cursoId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoDTO>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(videoService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VideoDTO>> crear(@Valid @RequestBody VideoRequest request) {
        VideoDTO video = videoService.crear(request);
        return ResponseEntity.ok(ApiResponse.success("Video creado exitosamente", video));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoDTO>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody VideoRequest request) {
        VideoDTO video = videoService.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.success("Video actualizado exitosamente", video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        videoService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success("Video eliminado exitosamente", null));
    }
}
