package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_cat_configuracion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfiguracionId")
    private Integer id;

    @Column(name = "Configuracion_NombreSitio", nullable = false, length = 100)
    private String nombreSitio;

    @Column(name = "Configuracion_LogoUrl", length = 255)
    private String logoUrl;

    @Column(name = "Configuracion_UpdatedAt")
    private LocalDateTime updatedAt;
}
