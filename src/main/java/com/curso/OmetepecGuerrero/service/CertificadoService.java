package com.curso.OmetepecGuerrero.service;

import lombok.RequiredArgsConstructor;
import com.curso.OmetepecGuerrero.dto.CertificadoDTO;
import com.curso.OmetepecGuerrero.entity.Certificado;
import com.curso.OmetepecGuerrero.entity.Inscripcion;
import com.curso.OmetepecGuerrero.exception.BadRequestException;
import com.curso.OmetepecGuerrero.exception.ResourceNotFoundException;
import com.curso.OmetepecGuerrero.repository.CertificadoRepository;
import com.curso.OmetepecGuerrero.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final InscripcionRepository inscripcionRepository;

    public List<CertificadoDTO> obtenerPorUsuario(Integer usuarioId) {
        return certificadoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CertificadoDTO obtenerPorInscripcion(Integer inscripcionId) {
        Certificado certificado = certificadoRepository.findByInscripcionId(inscripcionId)
                .orElse(null);
        return certificado != null ? toDTO(certificado) : null;
    }

    public CertificadoDTO verificar(String codigo) {
        Certificado certificado = certificadoRepository.findByCodigoVerificacion(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Certificado no encontrado"));
        return toDTO(certificado);
    }

    @Transactional
    public CertificadoDTO generar(Integer inscripcionId) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));

        if (!inscripcion.getCompletado()) {
            throw new BadRequestException("Debes completar el curso para obtener el certificado");
        }

        if (certificadoRepository.existsByInscripcionId(inscripcionId)) {
            throw new BadRequestException("Ya tienes un certificado para este curso");
        }

        String codigoVerificacion = generarCodigo();

        Certificado certificado = Certificado.builder()
                .codigoVerificacion(codigoVerificacion)
                .inscripcion(inscripcion)
                .nombreImpreso(inscripcion.getUsuario().getNombre())
                .cursoImpreso(inscripcion.getCurso().getTitulo())
                .fechaEmision(LocalDate.now())
                .formato(Certificado.Formato.PDF)
                .build();

        certificadoRepository.save(certificado);
        return toDTO(certificado);
    }

    private String generarCodigo() {
        return "UBAM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private CertificadoDTO toDTO(Certificado certificado) {
        return CertificadoDTO.builder()
                .id(certificado.getId())
                .codigoVerificacion(certificado.getCodigoVerificacion())
                .inscripcionId(certificado.getInscripcion().getId())
                .nombreImpreso(certificado.getNombreImpreso())
                .cursoImpreso(certificado.getCursoImpreso())
                .fechaEmision(certificado.getFechaEmision())
                .formato(certificado.getFormato() != null ? certificado.getFormato().name() : null)
                .build();
    }
}
