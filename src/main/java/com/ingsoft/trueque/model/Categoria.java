package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "categorias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "el nombre de la categoria no puede estar vacio")
    private String nombre;
    @NotBlank(message = "la descripcion de la categoria no puede estar vacia")
    private String descripcion;

}
