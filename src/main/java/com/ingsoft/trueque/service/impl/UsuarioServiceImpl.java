package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.*;
import com.ingsoft.trueque.exception.AccesoNoPermitidoException;
import com.ingsoft.trueque.exception.InvalidPasswordException;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.ArticuloMapper;
import com.ingsoft.trueque.mapper.IntercambioMapper;
import com.ingsoft.trueque.mapper.PersonaMapper;
import com.ingsoft.trueque.mapper.UsuarioMapper;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import com.ingsoft.trueque.model.util.Rol;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.repository.PersonaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final PasswordEncoder passwordEncoder;
    private final IntercambioRepository intercambioRepository;
    private final IntercambioMapper intercambioMapper;
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetUsuario> getAllUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toGetUsuario);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetUsuario getUsuarioById(Long id) {
        return usuarioRepository.getUsuarioById(id)
                .map(usuarioMapper::toGetUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al buscar el usuario con id "+id+", no existe en BD"));
    }

    @Override
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreoEqualsIgnoreCase(correo)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al buscar el usuario con correo "+correo+", no existe en BD"));
    }

    @Override
    public Usuario saveUsuario(SaveUsuario usuario) {
        System.out.println(usuario);
        validarClave(usuario);

        Usuario usuarioToSave = Usuario.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .correo(usuario.getCorreo().toLowerCase())
                .clave(passwordEncoder.encode(usuario.getClave()))
                .rol(Rol.USUARIO)
                .build();

        return usuarioRepository.save(usuarioToSave);
    }

    private void validarClave(SaveUsuario usuario) {
        if(!StringUtils.hasText(usuario.getClave()) || !StringUtils.hasText(usuario.getRepetirClave()) ){
            throw new InvalidPasswordException("Error, las claves no coinciden");
        }
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetUsuario updateUsuarioById(Long id, UpdateUsuario usuario) {
        Usuario usuarioSaved = usuarioRepository.getUsuarioById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al buscar el usuario con id "+id+", no existe en BD"));

        Usuario actual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!actual.getRol().equals(Rol.ADMINISTRADOR) && !actual.getId().equals(id)){
            throw new AccesoNoPermitidoException("Solo el mismo usuario o un administrador puede realizar esta accion");
        }
        updateUsuario(usuarioSaved, usuario);
        return usuarioMapper.toGetUsuario(usuarioRepository.save(usuarioSaved));
    }

    private void updateUsuario(Usuario usuarioSaved, UpdateUsuario usuario) {
        if(StringUtils.hasText(usuario.getNombre())){
            usuarioSaved.setNombre(usuario.getNombre());
        }
        if(StringUtils.hasText(usuario.getApellido())){
            usuarioSaved.setApellido(usuario.getApellido());
        }
        if(StringUtils.hasText(usuario.getTelefono())){
            usuarioSaved.setTelefono(usuario.getTelefono());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deleteUsuarioById(Long id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public Page<GetArticulo> getArticulosByUsuario(Long idUsuario, EstadoArticulo estadoArticulo, Pageable pageable) {
        if(estadoArticulo == null){
            //trae todos los articulos
            return articuloRepository.getArticulosByPropietarioId(idUsuario, pageable).map(articuloMapper::toGetArticulo);
        } if(estadoArticulo.equals(EstadoArticulo.INTERCAMBIADO)){
            return articuloRepository.findArticulosIntercambiadosPorUsuario(idUsuario, pageable).map(articuloMapper::toGetArticulo);
        }
        return articuloRepository.getArticulosByPropietarioIdAndEstado(idUsuario,estadoArticulo, pageable).map(articuloMapper::toGetArticulo);
    }

    @Override
    public GetReputacion obtenerReputacionDelUsuario(Long idUsuario) {
        if(!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNotFoundException("Error al obtener la reputacion del usuario con id "+idUsuario+", no existe en BD");
        }
        return new GetReputacion(
                idUsuario,
                Optional.ofNullable(usuarioRepository.obtenerReputacionDelUsuario(idUsuario)).orElse(0.0),
                usuarioRepository.totalResenhasDelUsuario(idUsuario)
                );
    }

    @Override
    public GetPerfilUsuario getUserProfile(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al obtener el perfil de la persona, porque no existe en BD"));
        List<GetIntercambioSimple> intercambioSimples = intercambioRepository.getIntercambiosByUsuarioIdAndEstado(persona.getId(), EstadoIntercambio.REALIZADO).stream()
                .map( i -> intercambioMapper.toGetIntercambioSimple(i))
                .collect(Collectors.toList());

        Double reputacion = usuarioRepository.obtenerReputacionDelUsuario(id);

        String nombreCompleto = persona.getNombre()+" "+persona.getApellido();
        return new GetPerfilUsuario(nombreCompleto, reputacion ,intercambioSimples);
    }
}
