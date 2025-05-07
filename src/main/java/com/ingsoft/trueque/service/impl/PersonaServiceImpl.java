package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.exception.PersonaNotFoundException;
import com.ingsoft.trueque.mapper.PersonaMapper;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.repository.PersonaRepository;
import com.ingsoft.trueque.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetPersona> getAllPersonas(Pageable pageable) {
        return personaRepository.findAll(pageable)
                .map(personaMapper::toGetPersona);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public GetPersona getPersonaById(Long id) {
        return personaRepository.findById(id)
                .map(personaMapper::toGetPersona)
                .orElseThrow(() -> new PersonaNotFoundException("Error, persona con id "+ id+" no encontrada en BD"));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deletePersonaById(Long id) {
        if(personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
        }
    }

    @Override
    public Persona getPersonaByCorreo(String correo) {
        return personaRepository.getPersonaByCorreoEqualsIgnoreCase(correo)
                .orElseThrow(() -> new PersonaNotFoundException("Error al obtener la persona con correo "+correo+", no existe en BD"));
    }
}
