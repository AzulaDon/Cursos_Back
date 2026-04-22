package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_ope_videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VideosId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CursosId", nullable = false)
    private Curso curso;

    @Column(name = "Videos_Titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "Videos_UrlStream", nullable = false, length = 500)
    private String urlStream;

    @Column(name = "Videos_OrdenSecuencia", nullable = false)
    private Integer ordenSecuencia;

    @Column(name = "Videos_DuracionSeg")
    private Integer duracionSeg;
}
