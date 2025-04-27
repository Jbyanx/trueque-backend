package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.UsuarioMapper;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;


    @Override
    public Page<GetUsuario> getAllUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toGetUsuario);
    }

    @Override
    public GetUsuario getUsuarioById(Long id) {
        return usuarioRepository.getUsuarioById(id)
                .map(usuarioMapper::toGetUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al buscar el usuario con id "+id+", no existe en BD"));
    }

    @Override
    public GetUsuario saveUsuario(SaveUsuario usuario) {
        return usuarioMapper.toGetUsuario(
                usuarioRepository.save(
                    usuarioMapper.toUsuario(usuario)
                )
        );
    }

    @Override
    public GetUsuario updateUsuarioById(Long id, UpdateUsuario usuario) {
        Usuario usuarioSaved = usuarioRepository.getUsuarioById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al buscar el usuario con id "+id+", no existe en BD"));

        updateUsuario(usuarioSaved, usuario);
        return usuarioMapper.toGetUsuario(usuarioRepository.save(usuarioSaved));
    }

    private void updateUsuario(Usuario usuarioSaved, UpdateUsuario usuario) {
        if(StringUtils.hasText(usuario.nombre())){
            usuarioSaved.setNombre(usuario.nombre());
        }
        if(StringUtils.hasText(usuario.apellido())){
            usuarioSaved.setApellido(usuario.apellido());
        }
        if(StringUtils.hasText(usuario.correo())){
            usuarioSaved.setCorreo(usuario.correo());
        }
    }

    @Override
    public void deleteUsuarioById(Long id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }
}
