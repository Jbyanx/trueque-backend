package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
@SuperBuilder
@NoArgsConstructor
public class Usuario extends Persona {

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Articulo> articuloList = new ArrayList<>();

}

