package com.ingsoft.trueque.model;

import com.ingsoft.trueque.model.util.EstadoArticulo;
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
    private String descripcion;
    private String rutaImagen;

    @OneToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoArticulo estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario propietario;


}
