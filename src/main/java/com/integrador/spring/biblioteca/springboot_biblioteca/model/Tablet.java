package com.integrador.spring.biblioteca.springboot_biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tablets", uniqueConstraints = {
        @UniqueConstraint(name = "uk_tablet_sn", columnNames = {"sn"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Tablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con marca
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tablet_marca"))
    private MarcaTablet marca;

    @Column(nullable = false, length = 80)
    private String modelo;

    // SN único
    @Column(nullable = false, length = 80, unique = true)
    private String sn;

    // Año de adquisición (numérico simple; si prefieres Year, lo cambiamos)
    @Column(name = "anio_adquisicion", nullable = false)
    private Integer anioAdquisicion;

    // Estado con default 'Disponible' y compatible con @Builder
    @Builder.Default
    @Column(nullable = false, length = 20)
    private String estado = "Disponible"; // Disponible | Prestada | Mantenimiento | Dañada

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
