package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.exception.*;
import com.ingsoft.trueque.mapper.IntercambioMapper;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntercambioServiceImpl implements IntercambioService {
    private final IntercambioRepository intercambioRepository;
    private final IntercambioMapper intercambioMapper;
    private final UsuarioRepository usuarioRepository;
    private final ArticuloRepository articuloRepository;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetIntercambio> getAllIntercambios(Pageable pageable) {
        return intercambioRepository.findAll(pageable)
                .map(intercambioMapper::toGetIntercambio);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public List<GetIntercambio> getIntercambiosByUsuarioIdAndEstado(Long id, EstadoIntercambio estadoIntercambio) {
        List<Intercambio> historial;
        if(estadoIntercambio != null){
            historial = intercambioRepository.getIntercambiosByUsuarioIdAndEstado(id, estadoIntercambio);
        }else{
            historial = intercambioRepository.getAllIntercambiosByUsuarioId(id);
        }
        return intercambioMapper.toGetIntercambioList(historial);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public GetIntercambio getIntercambioById(Long id) {
        return intercambioRepository.findById(id)
                .map(intercambioMapper::toGetIntercambio)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
    }

    @PreAuthorize("hasRole('USUARIO')")
    @Override
    public GetIntercambio solicitarIntercambio(SaveIntercambio intercambio) {
        Intercambio intercambioToSave = intercambioMapper.toIntercambio(intercambio);

        Usuario propietario = (Usuario) obtenerPrincipal();
        intercambioToSave.setUsuarioUno(propietario);

        Usuario usuarioDos = obtenerUsuarioDestino(intercambio.idUsuarioDos());
        intercambioToSave.setUsuarioDos(usuarioDos);

        Articulo articuloUno = obtenerArticulo(intercambio.idArticuloUno(), "uno");
        Articulo articuloDos = obtenerArticulo(intercambio.idArticuloDos(), "dos");

        validarPropietarios(articuloUno, articuloDos, propietario, usuarioDos);
        validarDisponibilidad(articuloUno, articuloDos);

        intercambioToSave.setArticuloUno(articuloUno);
        intercambioToSave.setArticuloDos(articuloDos);
        intercambioToSave.setEstado(EstadoIntercambio.SOLICITADO);

        if (intercambio.idIntercambioPadre() != null) {
            Intercambio padre = obtenerIntercambioPadre(intercambio.idIntercambioPadre());
            validarIntercambioPadre(padre);
            intercambioToSave.setIntercambioPadre(padre);
            intercambioToSave.setEstado(EstadoIntercambio.EN_NEGOCIACION);
        }

        return intercambioMapper.toGetIntercambio(intercambioRepository.save(intercambioToSave));
    }


    @PreAuthorize("hasRole('USUARIO')")
    @Transactional
    @Override
    public GetIntercambio aceptarIntercambio(Long intercambioId) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al aceptar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        Usuario actual = (Usuario) obtenerPrincipal();

        if (!intercambio.getUsuarioDos().getId().equals(actual.getId())) {
            throw new AccesoNoPermitidoException("Este usuario no puede aceptar este intercambio");
        }

        updateEstadoIntercambio(intercambio, EstadoIntercambio.ACEPTADO);
        return intercambioMapper.toGetIntercambio(intercambio);
    }

    private void updateEstadoIntercambio(Intercambio intercambioToUpdate, EstadoIntercambio estadoIntercambio) {
        if(estadoIntercambio == null){
            throw new IllegalArgumentException("el estado no puede ser null");
        }
            intercambioToUpdate.setEstado(estadoIntercambio);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @Transactional
    @Override
    public GetIntercambio rechazarIntercambio(Long intercambioId) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al rechazar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        Usuario actual = (Usuario) obtenerPrincipal();

        if (!intercambio.getUsuarioDos().getId().equals(actual.getId())) {
            throw new AccesoNoPermitidoException("Este usuario no puede rechazar este intercambio");
        }

        updateEstadoIntercambio(intercambio, EstadoIntercambio.RECHAZADO);
        return intercambioMapper.toGetIntercambio(intercambio);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Transactional
    @Override
    public GetIntercambio cancelarIntercambio(Long intercambioId) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al cancelar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        Usuario actual = (Usuario)  obtenerPrincipal();

        if(!(intercambio.getUsuarioUno().getId().equals(actual.getId()) || intercambio.getUsuarioDos().getId().equals(actual.getId()))){
            throw new LogicaNegocioException("Solo puede cancelar el intercambio un usuario que participe en dicho intercambio");
        }
        updateEstadoIntercambio(intercambio, EstadoIntercambio.CANCELADO);

        return intercambioMapper.toGetIntercambio(intercambio);
    }

    @PreAuthorize("hasRole('USUARIO')")
    @Override
    public Page<GetIntercambio> getMisIntercambios(Pageable pageable) {
        Usuario actual = (Usuario) obtenerPrincipal();

        List<GetIntercambio> intercambios = intercambioRepository.getAllIntercambiosByUsuarioId(actual.getId()).stream()
                .map(e -> intercambioMapper.toGetIntercambio(e))
                .collect(Collectors.toList());

        return new PageImpl<>(intercambios, pageable, intercambios.size());
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    @Transactional
    public GetIntercambio updateEstadoById(Long id, EstadoIntercambio estadoIntercambio) {
        Intercambio intercambioToUpdate = intercambioRepository.findById(id)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
        updateEstadoIntercambio(intercambioToUpdate, estadoIntercambio);
        return intercambioMapper.toGetIntercambio(
                intercambioRepository.save(intercambioToUpdate)
        );
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deleteIntercambioById(Long id) {
        if (intercambioRepository.existsById(id)) {
            intercambioRepository.deleteById(id);
        }else{
            throw new IntercambioNotFoundException("error al eliminar el intercambio con id "+id);
        }
    }

    private Usuario obtenerUsuarioDestino(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario solicitado no existe."));
    }

    private Articulo obtenerArticulo(Long id, String etiqueta) {
        return articuloRepository.findById(id).orElseThrow(() ->
                new ArticuloNotFoundException("El artículo " + etiqueta + " no existe."));
    }

    private Intercambio obtenerIntercambioPadre(Long idPadre) {
        return intercambioRepository.findById(idPadre)
                .orElseThrow(() -> new IntercambioNotFoundException("El intercambio padre no existe."));
    }

    private void validarPropietarios(Articulo articuloUno, Articulo articuloDos, Usuario actual, Usuario destino) {
        if (!articuloUno.getPropietario().getId().equals(actual.getId())) {
            throw new LogicaNegocioException("El artículo uno no te pertenece.");
        }

        if (!articuloDos.getPropietario().getId().equals(destino.getId())) {
            throw new LogicaNegocioException("El artículo dos no pertenece al usuario destino.");
        }

        if (articuloUno.getPropietario().getId().equals(articuloDos.getPropietario().getId())) {
            throw new LogicaNegocioException("No puedes ofrecer un artículo por otro tuyo.");
        }
    }

    private void validarDisponibilidad(Articulo articuloUno, Articulo articuloDos) {
        if (articuloUno.getEstado() != EstadoArticulo.DISPONIBLE) {
            throw new LogicaNegocioException("Tu artículo no está disponible para el intercambio.");
        }

        if (articuloDos.getEstado() != EstadoArticulo.DISPONIBLE) {
            throw new LogicaNegocioException("El artículo solicitado no está disponible para el intercambio.");
        }
    }

    private void validarIntercambioPadre(Intercambio padre) {
        if (padre.getEstado() != EstadoIntercambio.SOLICITADO) {
            throw new LogicaNegocioException("Solo puedes responder a un intercambio que esté en estado SOLICITADO.");
        }
    }

    private Persona obtenerPrincipal() {
        return (Persona) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
