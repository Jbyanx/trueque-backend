package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
@SuperBuilder
@NoArgsConstructor
public class Usuario extends Persona implements UserDetails {

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Articulo> articuloList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (getRol() == null) return Collections.emptyList();

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Agregar el rol con el prefijo "ROLE_"
        authorities.add(new SimpleGrantedAuthority("ROLE_" + getRol().name()));

        // Agregar los permisos si existen
        if (getRol().getPermisos() != null) {
            authorities.addAll(
                    getRol().getPermisos().stream()
                            .map(permiso -> new SimpleGrantedAuthority(permiso.name()))
                            .collect(Collectors.toList())
            );
        }

        return authorities;
    }


    @Override
    public String getPassword() {
        return this.getClave();
    }

    @Override
    public String getUsername() {
        return this.getCorreo();
    }

}

