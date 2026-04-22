package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CalificacionDTO;
import com.curso.OmetepecGuerrero.dto.request.CalificacionRequest;
import com.curso.OmetepecGuerrero.entity.Calificacion;
import com.curso.OmetepecGuerrero.entity.Inscripcion;
import com.curso.OmetepecGuerrero.exception.BadRequestException;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.CalificacionRepository;
import com.curso.OmetepecGuerrero.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;

    public List<CalificacionDTO> obtenerPorCurso(Integer cursoId) {
        return calificacionRepository.findByCursoId(cursoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CalificacionDTO obtenerPorInscripcion(Integer inscripcionId) {
        Calificacion calificacion = calificacionRepository.findByInscripcionId(inscripcionId)
                .orElse(null);
        return calificacion != null ? toDTO(calificacion) : null;
    }

    @Transactional
    public CalificacionDTO calificar(Integer inscripcionId, CalificacionRequest request) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));

        if (!inscripcion.getCompletado()) {
            throw new BadRequestException("Debes completar el curso antes de calificarlo");
        }

        if (calificacionRepository.findByInscripcionId(inscripcionId).isPresent()) {
            throw new BadRequestException("Ya has calificado este curso");
        }

        Calificacion calificacion = Calificacion.builder()
                .inscripcion(inscripcion)
                .estrellas(request.getEstrellas())
                .comentario(request.getComentario())
                .fechaResena(LocalDateTime.now())
                .build();

        calificacionRepository.save(calificacion);
        return toDTO(calificacion);
    }

    public Double obtenerPromedio(Integer cursoId) {
        return calificacionRepository.getPromedioEstrellasByCursoId(cursoId);
    }

    private CalificacionDTO toDTO(Calificacion calificacion) {
        return CalificacionDTO.builder()
                .id(calificacion.getId())
                .inscripcionId(calificacion.getInscripcion().getId())
                .cursoId(calificacion.getInscripcion().getCurso().getId())
                .cursoTitulo(calificacion.getInscripcion().getCurso().getTitulo())
                .usuarioNombre(calificacion.getInscripcion().getUsuario().getNombre())
                .estrellas(calificacion.getEstrellas())
                .comentario(calificacion.getComentario())
                .fechaResena(calificacion.getFechaResena())
                .build();
    }
}
