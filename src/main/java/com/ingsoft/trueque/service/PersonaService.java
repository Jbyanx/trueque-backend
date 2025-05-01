package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SavePersona;
import com.ingsoft.trueque.dto.response.GetPersona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaService {
    Page<GetPersona> getAllPersonas(Pageable pageable);
    GetPersona getPersonaById(Long id);
    void deletePersonaById(Long id);
}
