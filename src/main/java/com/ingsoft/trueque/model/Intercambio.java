package com.ingsoft.trueque.model;

import com.ingsoft.trueque.model.util.EstadoIntercambio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoIntercambio estado;

    @CreationTimestamp
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_usuario_uno", nullable = false)
    private Usuario usuarioUno;

    @ManyToOne
    @JoinColumn(name = "id_usuario_dos", nullable = false)
    private Usuario usuarioDos;

    @OneToOne
    @JoinColumn(name = "id_articulo_uno", nullable = false)
    private Articulo articuloUno;

    @OneToOne
    @JoinColumn(name = "id_articulo_dos", nullable = false)
    private Articulo articuloDos;
}
