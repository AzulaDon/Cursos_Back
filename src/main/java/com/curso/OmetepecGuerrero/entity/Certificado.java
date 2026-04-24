package com.curso.OmetepecGuerrero.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_ope_certificados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CertificadosId")
    private Integer id;

    @Column(name = "Certificados_CodigoVerif", unique = true, length = 50)
    private String codigoVerificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InscripcionesId", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "Certificados_NombreImpreso", nullable = false, length = 150)
    private String nombreImpreso;

    @Column(name = "Certificados_CursoImpreso", nullable = false, length = 200)
    private String cursoImpreso;

    @Column(name = "Certificados_FechaEmision", nullable = false)
    private LocalDate fechaEmision;

    @Enumerated(EnumType.STRING)
    @Column(name = "Certificados_Formato")
    private Formato formato;

    public enum Formato {
        PDF, JPG, PNG
    }
}
