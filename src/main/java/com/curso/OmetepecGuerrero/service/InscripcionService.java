package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.InscripcionDTO;
import com.curso.OmetepecGuerrero.entity.Curso;
import com.curso.OmetepecGuerrero.entity.Inscripcion;
import com.curso.OmetepecGuerrero.entity.Usuario;
import com.curso.OmetepecGuerrero.exception.BadRequestException;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.CursoRepository;
import com.curso.OmetepecGuerrero.repository.InscripcionRepository;
import com.curso.OmetepecGuerrero.repository.ProgresoVistaRepository;
import com.curso.OmetepecGuerrero.repository.UsuarioRepository;
import com.curso.OmetepecGuerrero.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final VideoRepository videoRepository;
    private final ProgresoVistaRepository progresoVistaRepository;

    public List<InscripcionDTO> obtenerPorUsuario(Integer usuarioId) {
        return inscripcionRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InscripcionDTO obtenerPorId(Integer id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
        return toDTO(inscripcion);
    }

    @Transactional
    public InscripcionDTO inscribirse(Integer usuarioId, Integer cursoId) {
        if (inscripcionRepository.existsByUsuarioIdAndCursoId(usuarioId, cursoId)) {
            throw new BadRequestException("Ya estás inscrito en este curso");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Inscripcion inscripcion = Inscripcion.builder()
                .usuario(usuario)
                .curso(curso)
                .fechaInicio(LocalDateTime.now())
                .porcentaje(BigDecimal.ZERO)
                .completado(false)
                .build();

        inscripcionRepository.save(inscripcion);
        return toDTO(inscripcion);
    }

    public InscripcionDTO obtenerInscripcion(Integer usuarioId, Integer cursoId) {
        Inscripcion inscripcion = inscripcionRepository.findByUsuarioIdAndCursoId(usuarioId, cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("No estás inscrito en este curso"));
        return toDTO(inscripcion);
    }

    public boolean estaInscrito(Integer usuarioId, Integer cursoId) {
        return inscripcionRepository.existsByUsuarioIdAndCursoId(usuarioId, cursoId);
    }

    public List<InscripcionDTO> obtenerCompletados(Integer usuarioId) {
        return inscripcionRepository.findByUsuarioIdAndCompletado(usuarioId, true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private InscripcionDTO toDTO(Inscripcion inscripcion) {
        Integer videosVistos = progresoVistaRepository.countVideosVistosByInscripcionId(inscripcion.getId());
        Integer totalVideos = videoRepository.countByCursoId(inscripcion.getCurso().getId());

        return InscripcionDTO.builder()
                .id(inscripcion.getId())
                .usuarioId(inscripcion.getUsuario().getId())
                .cursoId(inscripcion.getCurso().getId())
                .cursoTitulo(inscripcion.getCurso().getTitulo())
                .cursoPortadaUrl(inscripcion.getCurso().getPortadaUrl())
                .fechaInicio(inscripcion.getFechaInicio())
                .porcentaje(inscripcion.getPorcentaje())
                .completado(inscripcion.getCompletado())
                .fechaTermino(inscripcion.getFechaTermino())
                .videosVistos(videosVistos != null ? videosVistos : 0)
                .totalVideos(totalVideos != null ? totalVideos : 0)
                .build();
    }
}
