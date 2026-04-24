package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.ProgresoDTO;
import com.curso.OmetepecGuerrero.dto.request.ProgresoRequest;
import com.curso.OmetepecGuerrero.entity.Inscripcion;
import com.curso.OmetepecGuerrero.entity.ProgresoVista;
import com.curso.OmetepecGuerrero.entity.Video;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.InscripcionRepository;
import com.curso.OmetepecGuerrero.repository.ProgresoVistaRepository;
import com.curso.OmetepecGuerrero.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgresoService {

    private final ProgresoVistaRepository progresoVistaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final VideoRepository videoRepository;

    public List<ProgresoDTO> obtenerProgresoPorInscripcion(Integer inscripcionId) {
        return progresoVistaRepository.findByInscripcionId(inscripcionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProgresoDTO obtenerProgresoVideo(Integer inscripcionId, Integer videoId) {
        ProgresoVista progreso = progresoVistaRepository.findByInscripcionIdAndVideoId(inscripcionId, videoId)
                .orElse(null);
        
        if (progreso == null) {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));
            
            return ProgresoDTO.builder()
                    .inscripcionId(inscripcionId)
                    .videoId(videoId)
                    .videoTitulo(video.getTitulo())
                    .ultimoSegundo(0)
                    .visto(false)
                    .build();
        }
        
        return toDTO(progreso);
    }

    @Transactional
    public ProgresoDTO guardarProgreso(Integer inscripcionId, ProgresoRequest request) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado"));

        Optional<ProgresoVista> existente = progresoVistaRepository
                .findByInscripcionIdAndVideoId(inscripcionId, request.getVideoId());

        // Determinar si el video está visto (si llegó a -5 segundos del final)
        boolean visto = video.getDuracionSeg() != null && 
                       request.getSegundoActual() >= (video.getDuracionSeg() - 5);

        ProgresoVista progreso;
        if (existente.isPresent()) {
            progreso = existente.get();
            progreso.setUltimoSegundo(request.getSegundoActual());
            if (visto && !progreso.getVisto()) {
                progreso.setVisto(true);
            }
            progreso.setFechaVista(LocalDateTime.now());
        } else {
            progreso = ProgresoVista.builder()
                    .inscripcion(inscripcion)
                    .video(video)
                    .ultimoSegundo(request.getSegundoActual())
                    .visto(visto)
                    .fechaVista(LocalDateTime.now())
                    .build();
        }

        progresoVistaRepository.save(progreso);

        // Actualizar porcentaje de inscripción si marcó como visto
        if (visto) {
            actualizarPorcentajeInscripcion(inscripcion);
        }

        return toDTO(progreso);
    }

    private void actualizarPorcentajeInscripcion(Inscripcion inscripcion) {
        Integer totalVideos = videoRepository.countByCursoId(inscripcion.getCurso().getId());
        Integer videosVistos = progresoVistaRepository.countVideosVistosByInscripcionId(inscripcion.getId());

        if (totalVideos != null && totalVideos > 0) {
            BigDecimal porcentaje = BigDecimal.valueOf(videosVistos)
                    .divide(BigDecimal.valueOf(totalVideos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);

            inscripcion.setPorcentaje(porcentaje);

            if (porcentaje.compareTo(BigDecimal.valueOf(100)) >= 0) {
                inscripcion.setCompletado(true);
                inscripcion.setFechaTermino(LocalDateTime.now());
            }

            inscripcionRepository.save(inscripcion);
        }
    }

    private ProgresoDTO toDTO(ProgresoVista progreso) {
        return ProgresoDTO.builder()
                .id(progreso.getId())
                .inscripcionId(progreso.getInscripcion().getId())
                .videoId(progreso.getVideo().getId())
                .videoTitulo(progreso.getVideo().getTitulo())
                .ultimoSegundo(progreso.getUltimoSegundo())
                .visto(progreso.getVisto())
                .fechaVista(progreso.getFechaVista())
                .build();
    }
}
