package com.ingsoft.trueque.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "administradores")
@SuperBuilder
@NoArgsConstructor
public class Administrador extends Persona {

    @OneToMany(targetEntity = Reporte.class, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Reporte> reportesActivos = new ArrayList<>();
}
