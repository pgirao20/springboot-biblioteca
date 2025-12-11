//src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/model/MarcaTablet.java
package com.integrador.spring.biblioteca.springboot_biblioteca.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marca_tablet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaTablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Builder.Default
    @Column(nullable = false, length = 30)
    private String estado = "Disponible";
}
