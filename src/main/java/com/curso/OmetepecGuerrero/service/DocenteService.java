package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.DocenteDTO;
import com.curso.OmetepecGuerrero.dto.request.DocenteRequest;
import com.curso.OmetepecGuerrero.entity.Docente;
import com.curso.OmetepecGuerrero.entity.Usuario;
import com.curso.OmetepecGuerrero.exception.BadRequestException;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.DocenteRepository;
import com.curso.OmetepecGuerrero.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocenteService {

    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    public List<DocenteDTO> obtenerTodos() {
        return docenteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DocenteDTO obtenerPorId(Integer id) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + id));
        return toDTO(docente);
    }

    @Transactional
    public DocenteDTO crear(DocenteRequest request, Integer adminId) {
        if (docenteRepository.existsByCorreo(request.getCorreo())) {
            throw new BadRequestException("Ya existe un docente con ese correo");
        }

        Usuario admin = usuarioRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no encontrado"));

        Docente docente = Docente.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .especialidad(request.getEspecialidad())
                .adminRegistro(admin)
                .build();

        docenteRepository.save(docente);
        return toDTO(docente);
    }

    @Transactional
    public DocenteDTO actualizar(Integer id, DocenteRequest request) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + id));

        if (!docente.getCorreo().equals(request.getCorreo()) && 
            docenteRepository.existsByCorreo(request.getCorreo())) {
            throw new BadRequestException("Ya existe un docente con ese correo");
        }

        docente.setNombre(request.getNombre());
        docente.setCorreo(request.getCorreo());
        docente.setEspecialidad(request.getEspecialidad());

        docenteRepository.save(docente);
        return toDTO(docente);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!docenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Docente no encontrado con ID: " + id);
        }
        docenteRepository.deleteById(id);
    }

    public List<DocenteDTO> buscarPorEspecialidad(String especialidad) {
        return docenteRepository.findByEspecialidadContainingIgnoreCase(especialidad).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private DocenteDTO toDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .correo(docente.getCorreo())
                .especialidad(docente.getEspecialidad())
                .fechaAlta(docente.getFechaAlta())
                .build();
    }
}
