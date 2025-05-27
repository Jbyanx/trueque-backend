package com.ingsoft.trueque;

import com.ingsoft.trueque.model.Administrador;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.util.Rol;
import com.ingsoft.trueque.repository.AdministradorRepository;
import com.ingsoft.trueque.repository.PersonaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@SpringBootApplication
public class TruequeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruequeApplication.class, args);
	}

	@Bean
	public CommandLineRunner runOnStartup(PasswordEncoder passwordEncoder,
										  PersonaRepository personaRepository,
										  AdministradorRepository administradorRepository) {
		return args -> {
			Administrador administrador = Administrador.builder()
					.nombre("Carlos")
					.apellido("Henriquez")
					.rol(Rol.ADMINISTRADOR)
					.clave(passwordEncoder.encode("admin"))
					.correo("admin@gmail.com")
					.telefono("3106453223")
					.build();

			Boolean existe = personaRepository.existsByCorreoIgnoreCase(administrador.getCorreo());

			if(!existe){
				administradorRepository.save(administrador);
				log.info("administrador:{}",administrador);
			}
		};
	}
}
