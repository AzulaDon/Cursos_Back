package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CursoDTO;
import com.curso.OmetepecGuerrero.dto.DocenteDTO;
import com.curso.OmetepecGuerrero.dto.VideoDTO;
import com.curso.OmetepecGuerrero.dto.request.CursoRequest;
import com.curso.OmetepecGuerrero.entity.Curso;
import com.curso.OmetepecGuerrero.entity.Docente;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.CalificacionRepository;
import com.curso.OmetepecGuerrero.repository.CursoRepository;
import com.curso.OmetepecGuerrero.repository.DocenteRepository;
import com.curso.OmetepecGuerrero.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;
    private final VideoRepository videoRepository;
    private final CalificacionRepository calificacionRepository;

    public List<CursoDTO> obtenerTodos() {
        return cursoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CursoDTO> obtenerActivos() {
        return cursoRepository.findByEstatus(Curso.Estatus.activo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CursoDTO obtenerPorId(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return toDTOConVideos(curso);
    }

    public List<CursoDTO> obtenerPorCategoria(String categoria) {
        return cursoRepository.findByCategoria(categoria).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CursoDTO> buscar(String keyword) {
        return cursoRepository.buscarPorKeyword(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<String> obtenerCategorias() {
        return cursoRepository.findAllCategorias();
    }

    @Transactional
    public CursoDTO crear(CursoRequest request) {
        Docente docente = docenteRepository.findById(request.getDocenteId())
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));

        Curso.Estatus estatus = Curso.Estatus.activo;
        if (request.getEstatus() != null) {
            try {
                estatus = Curso.Estatus.valueOf(request.getEstatus().toLowerCase());
            } catch (IllegalArgumentException e) {
                estatus = Curso.Estatus.activo;
            }
        }

        Curso curso = Curso.builder()
                .docente(docente)
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .portadaUrl(request.getPortadaUrl())
                .categoria(request.getCategoria())
                .estatus(estatus)
                .build();

        cursoRepository.save(curso);
        return toDTO(curso);
    }

    @Transactional
    public CursoDTO actualizar(Integer id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));

        if (request.getDocenteId() != null) {
            Docente docente = docenteRepository.findById(request.getDocenteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado"));
            curso.setDocente(docente);
        }

        curso.setTitulo(request.getTitulo());
        curso.setDescripcion(request.getDescripcion());
        curso.setPortadaUrl(request.getPortadaUrl());
        curso.setCategoria(request.getCategoria());

        if (request.getEstatus() != null) {
            try {
                curso.setEstatus(Curso.Estatus.valueOf(request.getEstatus().toLowerCase()));
            } catch (IllegalArgumentException ignored) {}
        }

        cursoRepository.save(curso);
        return toDTO(curso);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    private CursoDTO toDTO(Curso curso) {
        Integer totalVideos = videoRepository.countByCursoId(curso.getId());
        Integer duracionTotal = videoRepository.sumDuracionByCursoId(curso.getId());
        Double promedio = calificacionRepository.getPromedioEstrellasByCursoId(curso.getId());
        Long totalResenas = calificacionRepository.countByCursoId(curso.getId());

        return CursoDTO.builder()
                .id(curso.getId())
                .titulo(curso.getTitulo())
                .descripcion(curso.getDescripcion())
                .portadaUrl(curso.getPortadaUrl())
                .categoria(curso.getCategoria())
                .estatus(curso.getEstatus() != null ? curso.getEstatus().name() : null)
                .docente(DocenteDTO.builder()
                        .id(curso.getDocente().getId())
                        .nombre(curso.getDocente().getNombre())
                        .especialidad(curso.getDocente().getEspecialidad())
                        .build())
                .totalVideos(totalVideos != null ? totalVideos : 0)
                .duracionTotalSeg(duracionTotal != null ? duracionTotal : 0)
                .promedioEstrellas(promedio)
                .totalResenas(totalResenas)
                .build();
    }

    private CursoDTO toDTOConVideos(Curso curso) {
        CursoDTO dto = toDTO(curso);
        
        List<VideoDTO> videos = videoRepository.findByCursoIdOrderByOrdenSecuenciaAsc(curso.getId())
                .stream()
                .map(v -> VideoDTO.builder()
                        .id(v.getId())
                        .cursoId(curso.getId())
                        .titulo(v.getTitulo())
                        .urlStream(v.getUrlStream())
                        .ordenSecuencia(v.getOrdenSecuencia())
                        .duracionSeg(v.getDuracionSeg())
                        .build())
                .collect(Collectors.toList());
        
        dto.setVideos(videos);
        return dto;
    }
}
