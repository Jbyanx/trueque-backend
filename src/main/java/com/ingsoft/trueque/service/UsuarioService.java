package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.GetUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    Page<GetUsuario> getAllUsuarios(Pageable pageable);
    GetUsuario getUsuarioById(Long id);
    GetUsuario saveUsuario(SaveUsuario usuario);
    GetUsuario updateUsuarioById(Long id, SaveUsuario usuario);
    void deleteUsuarioById(Long id);
}
