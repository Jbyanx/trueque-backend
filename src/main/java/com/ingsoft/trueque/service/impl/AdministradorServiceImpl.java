package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.UpdateAdministrador;
import com.ingsoft.trueque.exception.AdministradorNotFoundException;
import com.ingsoft.trueque.mapper.AdministradorMapper;
import com.ingsoft.trueque.dto.request.SaveAdministrador;
import com.ingsoft.trueque.dto.response.GetAdministrador;
import com.ingsoft.trueque.model.Administrador;
import com.ingsoft.trueque.repository.AdministradorRepository;
import com.ingsoft.trueque.service.AdministradorService;
import jakarta.transaction.Transactional;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;

    public AdministradorServiceImpl(AdministradorRepository administradorRepository, AdministradorMapper administradorMapper) {
        this.administradorRepository = administradorRepository;
        this.administradorMapper = administradorMapper;
    }


    @Override
    public Page<GetAdministrador> getAllAdministradores(Pageable pageable) {
        return administradorRepository.findAll(pageable)
                .map(administradorMapper::toGetAdministrador);
    }

    @Override
    public GetAdministrador getAdministradorById(Long id) {
        return administradorRepository.findById(id)
                .map(administradorMapper::toGetAdministrador)
                .orElseThrow(() -> new AdministradorNotFoundException("Error al obtener, el administrador con id " + id + "No existe en BD"));
    }

    @Override
    @Transactional
    public GetAdministrador updateAdministradorById(Long id, UpdateAdministrador admin) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Error al actualizar, el administrador con id "+id+ "No existe en BD"));
        updateAdmin(administrador, admin);
        return administradorMapper.toGetAdministrador(administrador);
    }

    private void updateAdmin(Administrador administrador, UpdateAdministrador admin) {
        if(StringUtils.hasText(admin.getNombre())){
            administrador.setNombre(admin.getNombre());
        }
        if(StringUtils.hasText(admin.getApellido())){
            administrador.setApellido(admin.getApellido());
        }
        if(StringUtils.hasText(admin.getCorreo())){
            administrador.setCorreo(admin.getCorreo());
        }
    }

    @Override
    public void deleteAdministradorById(Long id) {
        if (administradorRepository.existsById(id))
            administradorRepository.deleteById(id);
        throw new AdministradorNotFoundException("Error al eliminar, el administrador con id "+id+ "No existe en BD");
    }
}
