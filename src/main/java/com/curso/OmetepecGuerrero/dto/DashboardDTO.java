package com.curso.OmetepecGuerrero.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private String estudiante;
    private String curso;
    private BigDecimal avancePorcentaje;
    private Boolean finalizado;
    private Integer videosVistos;
}
