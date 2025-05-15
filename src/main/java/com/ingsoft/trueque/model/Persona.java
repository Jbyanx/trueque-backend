package com.ingsoft.trueque.model;

import com.ingsoft.trueque.model.util.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Persona implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "el apellido no puede estar vacio")
    private String apellido;
    @Email
    @Column(nullable = false, unique = true)
    @NotNull(message = "el correo no puede estar vacio")
    private String correo;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String clave;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Rol rol;
    private String sessionId;

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
