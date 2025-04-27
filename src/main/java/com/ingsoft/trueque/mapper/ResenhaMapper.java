package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import com.ingsoft.trueque.model.Resenha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResenhaMapper {

    @Mapping(target = "idAutor", source = "autor.id")
    @Mapping(target = "idIntercambio", source = "intercambio.id")
    GetResenha toGetResenha(Resenha resenha);

    @Mapping(target = "autor.id", source = "idAutor")
    @Mapping(target = "intercambio.id", source = "idIntercambio")
    Resenha toResenha(SaveResenha saveResenha);

    List<GetResenha> toGetResenhaList(List<Resenha> resenhaList);
    List<Resenha> toResenhaList(List<GetResenha> getResenhaList);

}
