package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaService {
    Page<GetPersona> getAllPersonas(Pageable pageable);
    GetPersona getPersonaById(Long id);
    void deletePersonaById(Long id);
    Persona getPersonaByCorreo(String correo);
}
