package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_rel_calificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CalificacionesId")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InscripcionesId", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "Calificaciones_Estrellas", nullable = false)
    private Byte estrellas;

    @Column(name = "Calificaciones_Comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "Calificaciones_FechaResena")
    private LocalDateTime fechaResena;
}
