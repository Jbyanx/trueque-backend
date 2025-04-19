package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intercambios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Intercambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_uno_id", nullable = false)
    private Usuario usuarioUno;

    @ManyToOne
    @JoinColumn(name = "usuario_dos_id", nullable = false)
    private Usuario usuarioDos;

    @OneToMany(mappedBy = "intercambio", targetEntity = Resenha.class, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Resenha> resenhaList = new ArrayList<>();

    @OneToMany(mappedBy = "intercambio",targetEntity = Articulo.class, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Articulo> articuloList = new ArrayList<>();
}
