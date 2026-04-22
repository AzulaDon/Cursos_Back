package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificadoDTO {
    private Integer id;
    private String codigoVerificacion;
    private Integer inscripcionId;
    private String nombreImpreso;
    private String cursoImpreso;
    private LocalDate fechaEmision;
    private String formato;
}
