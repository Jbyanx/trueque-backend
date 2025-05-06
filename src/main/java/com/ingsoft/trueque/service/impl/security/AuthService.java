package com.ingsoft.trueque.service.impl.security;

import com.ingsoft.trueque.dto.request.LoginRequest;
import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.GetUsuarioRegistrado;
import com.ingsoft.trueque.dto.response.LoginResponse;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.UsuarioMapper;
import com.ingsoft.trueque.model.Usuario;
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

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

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
        UserDetails usuario = usuarioService.getUsuarioByCorreo(loginRequest.correo());
        String jwt =  jwtService.generarToken(usuario, generarExtraClaims((Usuario)usuario));

        return  new LoginResponse(jwt);
    }

    private Map<String, Object> generarExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", usuario.getId());
        extraClaims.put("nombre", usuario.getNombre()+" "+usuario.getApellido());
        extraClaims.put("rol", usuario.getRol().name());
        return extraClaims;
    }

    public Usuario findAuthenticatedUser(){
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUser = auth.getPrincipal().toString();

        return usuarioRepository.findByCorreoEqualsIgnoreCase(authenticatedUser)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
    }
}
