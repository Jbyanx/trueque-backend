package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.dto.response.GetPerfilUsuario;
import com.ingsoft.trueque.dto.response.GetReputacion;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    Page<GetUsuario> getAllUsuarios(Pageable pageable);
    GetUsuario getUsuarioById(Long id);
    Usuario getUsuarioByCorreo(String correo);
    Usuario saveUsuario(SaveUsuario usuario);
    GetUsuario updateUsuarioById(Long id, UpdateUsuario usuario);
    void deleteUsuarioById(Long id);

    Page<GetArticulo> getArticulosByUsuario(Long idUsuario, EstadoArticulo estadoArticulo, Pageable pageable);

    GetReputacion obtenerReputacionDelUsuario(Long idUsuario);

    GetPerfilUsuario getUserProfile(Long id);
}
