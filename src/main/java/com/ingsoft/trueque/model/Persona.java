package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "el apellido no puede estar vacio")
    private String apellido;
    @Email
    @NotNull(message = "el correo no puede estar vacio")
    private String correo;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String clave;
}
