package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "articulos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del articulo no debe ir vacio")
    private String nombre;
    @NotBlank(message = "La descripcion del articulo no debe ir vacio")
    private String descripcion;
    private String rutaImagen;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoriaArticulo categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "intercambio_id") // este es el foreign key en la tabla articulo
    private Intercambio intercambio;
}
