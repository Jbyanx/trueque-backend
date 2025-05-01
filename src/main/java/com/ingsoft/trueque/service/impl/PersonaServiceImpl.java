package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.exception.PersonaNotFoundException;
import com.ingsoft.trueque.mapper.PersonaMapper;
import com.ingsoft.trueque.repository.PersonaRepository;
import com.ingsoft.trueque.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @Override
    public Page<GetPersona> getAllPersonas(Pageable pageable) {
        return personaRepository.findAll(pageable)
                .map(personaMapper::toGetPersona);
    }

    @Override
    public GetPersona getPersonaById(Long id) {
        return personaRepository.findById(id)
                .map(personaMapper::toGetPersona)
                .orElseThrow(() -> new PersonaNotFoundException("Error, persona con id "+ id+" no encontrada en BD"));
    }

    @Override
    public void deletePersonaById(Long id) {
        if(personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
        }
    }
}
