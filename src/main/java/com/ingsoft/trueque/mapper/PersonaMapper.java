package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SavePersona;
import com.ingsoft.trueque.dto.response.GetPerfilUsuario;
import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = IntercambioMapper.class)
public interface PersonaMapper {

    GetPersona toGetPersona(Persona persona);

    @Mapping(target = "nombreCompleto", expression = "java(nombreCompleto(persona))")
    @Mapping(target = "intercambiosRealizados", ignore = true)
    GetPerfilUsuario toGetPerfilUsuario(Persona persona);

    Persona toPersona(SavePersona savePersona);

    List<GetPersona> toGetPersonaList(List<Persona> personaList);
    List<Persona> toPersonaList(List<GetPersona> getPersonaList);

    default String nombreCompleto(Persona persona) {
        return persona.getNombre() + " " + persona.getApellido();
    }
}
