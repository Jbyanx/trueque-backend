package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SavePersona;
import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.model.Persona;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    GetPersona toGetPersona(Persona persona);

    Persona toPersona(SavePersona savePersona);

    List<GetPersona> toGetPersonaList(List<Persona> personaList);
    List<Persona> toPersonaList(List<GetPersona> getPersonaList);

}
