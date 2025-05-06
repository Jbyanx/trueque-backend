package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.ArticuloSimple;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.dto.response.GetUsuarioRegistrado;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "articulos", source = "articuloList")
    GetUsuario toGetUsuario(Usuario usuario);

    @Mapping(target = "nombre", expression = "java(usuario.getNombre() + \" \" + usuario.getApellido())")
    GetUsuarioRegistrado toGetUsuarioRegistrado(Usuario usuario);

    Usuario toUsuario(SaveUsuario saveUsuario);

    ArticuloSimple toArticuloSimple(Articulo articulo);

    @InheritInverseConfiguration
    Articulo toArticulo(ArticuloSimple articuloSimple);

    List<GetUsuario> toGetUsuarioList(List<Usuario> usuarioList);
    List<Usuario> toUsuarioList(List<GetUsuario> getUsuarioList);

}
