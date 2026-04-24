package com.curso.OmetepecGuerrero.controller;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CalificacionDTO;
import com.curso.OmetepecGuerrero.dto.DashboardDTO;
import com.curso.OmetepecGuerrero.dto.UsuarioDTO;
import com.curso.OmetepecGuerrero.dto.response.ApiResponse;
import com.curso.OmetepecGuerrero.entity.Inscripcion;
import com.curso.OmetepecGuerrero.repository.CalificacionRepository;
import com.curso.OmetepecGuerrero.repository.InscripcionRepository;
import com.curso.OmetepecGuerrero.repository.ProgresoVistaRepository;
import com.curso.OmetepecGuerrero.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ProgresoVistaRepository progresoVistaRepository;
    private final CalificacionRepository calificacionRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerUsuarios() {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(u -> UsuarioDTO.builder()
                        .id(u.getId())
                        .nombre(u.getNombre())
                        .correo(u.getCorreo())
                        .rol(u.getRol().name())
                        .plataforma(u.getPlataforma().name())
                        .estado(u.getEstado())
                        .fechaRegistro(u.getFechaRegistro())
                        .ultimoLogin(u.getUltimoLogin())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(usuarios));
    }

    @GetMapping("/dashboard/progreso")
    public ResponseEntity<ApiResponse<List<DashboardDTO>>> obtenerDashboardProgreso() {
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        
        List<DashboardDTO> dashboard = inscripciones.stream()
                .map(i -> {
                    Integer videosVistos = progresoVistaRepository.countVideosVistosByInscripcionId(i.getId());
                    return DashboardDTO.builder()
                            .estudiante(i.getUsuario().getNombre())
                            .curso(i.getCurso().getTitulo())
                            .avancePorcentaje(i.getPorcentaje())
                            .finalizado(i.getCompletado())
                            .videosVistos(videosVistos != null ? videosVistos : 0)
                            .build();
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }

    @GetMapping("/dashboard/calificaciones")
    public ResponseEntity<ApiResponse<List<CalificacionDTO>>> obtenerCalificaciones() {
        List<CalificacionDTO> calificaciones = calificacionRepository.findAll().stream()
                .map(c -> CalificacionDTO.builder()
                        .id(c.getId())
                        .inscripcionId(c.getInscripcion().getId())
                        .cursoId(c.getInscripcion().getCurso().getId())
                        .cursoTitulo(c.getInscripcion().getCurso().getTitulo())
                        .usuarioNombre(c.getInscripcion().getUsuario().getNombre())
                        .estrellas(c.getEstrellas())
                        .comentario(c.getComentario())
                        .fechaResena(c.getFechaResena())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(calificaciones));
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsuarios", usuarioRepository.count());
        stats.put("totalInscripciones", inscripcionRepository.count());
        stats.put("totalCalificaciones", calificacionRepository.count());
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
