package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_cat_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuariosId")
    private Integer id;

    @Column(name = "Usuarios_Nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "Usuarios_Correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "Usuarios_PassHash", nullable = false, length = 255)
    private String passHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "Usuarios_Rol", nullable = false)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "Usuarios_Plataforma", nullable = false)
    private Plataforma plataforma;

    @Column(name = "Usuarios_Estado")
    private Boolean estado;

    @Column(name = "Usuarios_FechaRegistro")
    private LocalDateTime fechaRegistro;

    @Column(name = "Usuarios_UltimoLogin")
    private LocalDateTime ultimoLogin;

    public enum Rol {
        admin, usuario
    }

    public enum Plataforma {
        web, app, ambas
    }
}
