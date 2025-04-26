package com.ingsoft.trueque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(min = 5, max = 100, message = "El nombre debe tener de 5 a 100 caracteres")
    private String nombre;
    @NotBlank
    @Size(min = 5, max = 100, message = "El apellido debe tener de 5 a 100 caracteres")
    private String apellido;
    @Email
    @Size(min = 5, max = 150, message = "El correo debe tener de 5 a 150 caracteres")
    private String correo;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String clave;
}
