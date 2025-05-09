package com.ingsoft.trueque.service.impl.security;

import com.ingsoft.trueque.dto.request.LoginRequest;
import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.GetUsuarioRegistrado;
import com.ingsoft.trueque.dto.response.LoginResponse;
import com.ingsoft.trueque.exception.PersonaNotFoundException;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.UsuarioMapper;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.repository.PersonaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public GetUsuarioRegistrado registrarUsuario(@Valid SaveUsuario usuarioNuevo) {
        Usuario usuario = usuarioService.saveUsuario(usuarioNuevo);
        String token = jwtService.generarToken(usuario, generarExtraClaims(usuario));

        return  new GetUsuarioRegistrado(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getRol().name()
        );
    }

    public LoginResponse login(@Valid LoginRequest loginRequest){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.correo(),
                loginRequest.clave()
        );

        authenticationManager.authenticate(authentication);

        Persona persona = personaRepository.findByCorreoEqualsIgnoreCase(loginRequest.correo())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Generar nuevo sessionId
        String nuevoSessionId = UUID.randomUUID().toString();
        persona.setSessionId(nuevoSessionId); // Asegúrate de tener el setter
        personaRepository.save(persona);

        // Claims con sessionId incluido
        String jwt = jwtService.generarToken(persona, generarExtraClaims(persona));

        return new LoginResponse(jwt);
    }

    private Map<String, Object> generarExtraClaims(Persona persona) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", persona.getId());
        extraClaims.put("nombre", persona.getNombre() + " " + persona.getApellido());
        extraClaims.put("rol", persona.getRol().name());
        extraClaims.put("sessionId", persona.getSessionId());
        return extraClaims;
    }

    public Persona findAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        return personaRepository.findByCorreoEqualsIgnoreCase(correo)
                .orElseThrow(() -> new PersonaNotFoundException("Error al buscar al usuario autenticado, no encontrado en BD"));
    }

}
