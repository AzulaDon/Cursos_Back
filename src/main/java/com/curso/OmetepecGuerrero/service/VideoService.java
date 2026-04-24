package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.VideoDTO;
import com.curso.OmetepecGuerrero.dto.request.VideoRequest;
import com.curso.OmetepecGuerrero.entity.Curso;
import com.curso.OmetepecGuerrero.entity.Video;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.CursoRepository;
import com.curso.OmetepecGuerrero.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final CursoRepository cursoRepository;

    public List<VideoDTO> obtenerPorCurso(Integer cursoId) {
        return videoRepository.findByCursoIdOrderByOrdenSecuenciaAsc(cursoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VideoDTO obtenerPorId(Integer id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado con ID: " + id));
        return toDTO(video);
    }

    @Transactional
    public VideoDTO crear(VideoRequest request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Video video = Video.builder()
                .curso(curso)
                .titulo(request.getTitulo())
                .urlStream(request.getUrlStream())
                .ordenSecuencia(request.getOrdenSecuencia())
                .duracionSeg(request.getDuracionSeg() != null ? request.getDuracionSeg() : 0)
                .build();

        videoRepository.save(video);
        return toDTO(video);
    }

    @Transactional
    public VideoDTO actualizar(Integer id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video no encontrado con ID: " + id));

        if (request.getCursoId() != null && !request.getCursoId().equals(video.getCurso().getId())) {
            Curso curso = cursoRepository.findById(request.getCursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
            video.setCurso(curso);
        }

        video.setTitulo(request.getTitulo());
        video.setUrlStream(request.getUrlStream());
        video.setOrdenSecuencia(request.getOrdenSecuencia());
        video.setDuracionSeg(request.getDuracionSeg() != null ? request.getDuracionSeg() : 0);

        videoRepository.save(video);
        return toDTO(video);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Video no encontrado con ID: " + id);
        }
        videoRepository.deleteById(id);
    }

    private VideoDTO toDTO(Video video) {
        return VideoDTO.builder()
                .id(video.getId())
                .cursoId(video.getCurso().getId())
                .titulo(video.getTitulo())
                .urlStream(video.getUrlStream())
                .ordenSecuencia(video.getOrdenSecuencia())
                .duracionSeg(video.getDuracionSeg())
                .build();
    }
}
