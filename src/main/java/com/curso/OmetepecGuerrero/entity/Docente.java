package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_cat_docentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocentesId")
    private Integer id;

    @Column(name = "Docentes_Nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "Docentes_Correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "Docentes_Especialidad", nullable = false, length = 200)
    private String especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuariosId", nullable = false)
    private Usuario adminRegistro;

    @Column(name = "Docentes_FechaAlta")
    private LocalDateTime fechaAlta;
}
