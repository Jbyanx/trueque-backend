package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "administradores")
@SuperBuilder
@NoArgsConstructor
public class Administrador extends Persona {

    @OneToMany(targetEntity = Reporte.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "administradores_reportes_activos",
            joinColumns = @JoinColumn(
                    name = "id_administrador",
                    foreignKey = @ForeignKey(name = "fk_admin_reportes_admin")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "id_reporte",
                    foreignKey = @ForeignKey(name = "fk_admin_reportes_reporte")
            )
    )
    @Builder.Default
    private List<Reporte> reportesActivos = new ArrayList<>();
}
