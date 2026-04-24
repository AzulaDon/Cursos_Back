package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "tbl_ope_cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CursosId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DocentesId", nullable = false)
    private Docente docente;

    @Column(name = "Cursos_Titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "Cursos_Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Cursos_PortadaUrl", length = 255)
    private String portadaUrl;

    @Column(name = "Cursos_Categoria", length = 50)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "Cursos_Estatus")
    private Estatus estatus;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    public enum Estatus {
        activo, inactivo, mantenimiento
    }
}
