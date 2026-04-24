package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_ope_inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InscripcionesId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuariosId", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CursosId", nullable = false)
    private Curso curso;

    @Column(name = "Inscripciones_FechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "Inscripciones_Porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "Inscripciones_Completado")
    private Boolean completado;

    @Column(name = "Inscripciones_FechaTermino")
    private LocalDateTime fechaTermino;
}
