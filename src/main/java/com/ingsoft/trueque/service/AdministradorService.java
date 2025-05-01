package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveAdministrador;
import com.ingsoft.trueque.dto.request.UpdateAdministrador;
import com.ingsoft.trueque.dto.response.GetAdministrador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdministradorService {
    Page<GetAdministrador> getAllAdministradores(Pageable pageable);
    GetAdministrador getAdministradorById(Long id);
    GetAdministrador updateAdministradorById(Long id, UpdateAdministrador admin);
    void deleteAdministradorById(Long id);
}
