package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_rel_progreso_vistas_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgresoVista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgresoId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InscripcionesId", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideosId", nullable = false)
    private Video video;

    @Column(name = "Progreso_UltimoSegundo")
    private Integer ultimoSegundo;

    @Column(name = "Progreso_Visto")
    private Boolean visto;

    @Column(name = "Progreso_FechaVista")
    private LocalDateTime fechaVista;
}
