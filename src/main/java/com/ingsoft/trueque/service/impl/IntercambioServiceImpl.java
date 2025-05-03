package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.exception.AccesoNoPermitidoException;
import com.ingsoft.trueque.exception.IntercambioNotFoundException;
import com.ingsoft.trueque.exception.LogicaNegocioException;
import com.ingsoft.trueque.mapper.IntercambioMapper;
import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntercambioServiceImpl implements IntercambioService {
    private final IntercambioRepository intercambioRepository;
    private final IntercambioMapper intercambioMapper;

    @Override
    public Page<GetIntercambio> getAllIntercambios(Pageable pageable) {
        return intercambioRepository.findAll(pageable)
                .map(intercambioMapper::toGetIntercambio);
    }

    @Override
    public List<GetIntercambio> getIntercambiosByUsuarioIdAndEstado(Long id, EstadoIntercambio estadoIntercambio) {
        List<Intercambio> historial;
        if(estadoIntercambio != null){
            historial = intercambioRepository.historialIntercambiosByIdUsuarioAndEstado(id, estadoIntercambio);
        }else{
            historial = intercambioRepository.historialIntercambiosDelUsuario(id);
        }
        return intercambioMapper.toGetIntercambioList(historial);
    }

    @Override
    public GetIntercambio getIntercambioById(Long id) {
        return intercambioRepository.findById(id)
                .map(intercambioMapper::toGetIntercambio)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
    }

    @Override
    public GetIntercambio solicitarIntercambio(SaveIntercambio intercambio) {
        Intercambio intercambioToSave = intercambioMapper.toIntercambio(intercambio);

        // El propietario del articulo dos no debe ser el propietario del articulo uno
        if (intercambioToSave.getArticuloDos().getPropietario().equals(
                intercambioToSave.getArticuloUno().getPropietario())) {
            throw new LogicaNegocioException("No puedes ofrecer un artículo por otro tuyo.");
        }

        if (intercambioToSave.getArticuloDos().getEstado() != EstadoArticulo.DISPONIBLE) {
            throw new LogicaNegocioException("El artículo solicitado no está disponible para el intercambio.");
        }

        intercambioToSave.setEstado(EstadoIntercambio.SOLICITADO);

        return intercambioMapper.toGetIntercambio(
                intercambioRepository.save(intercambioToSave)
        );
    }



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

    private void updateEstadoIntercambio(Intercambio intercambioToUpdate, EstadoIntercambio estadoIntercambio) {
        if(estadoIntercambio == null){
            throw new IllegalArgumentException("el estado no puede ser null");
        }
            intercambioToUpdate.setEstado(estadoIntercambio);
    }

    @Override
    public void deleteIntercambioById(Long id) {
        if (intercambioRepository.existsById(id)) {
            intercambioRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public GetIntercambio aceptarIntercambio(Long usuarioId, Long intercambioId, EstadoIntercambio estadoIntercambio) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al aceptar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        if (!intercambio.getUsuarioDos().getId().equals(usuarioId)) {
            throw new AccesoNoPermitidoException("Este usuario no puede aceptar este intercambio");
        }

        updateEstadoIntercambio(intercambio, estadoIntercambio);
        return intercambioMapper.toGetIntercambio(intercambio);
    }

    @Transactional
    @Override
    public GetIntercambio rechazarIntercambio(Long usuarioId, Long intercambioId, EstadoIntercambio estadoIntercambio) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al rechazar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        if (!intercambio.getUsuarioDos().getId().equals(usuarioId)) {
            throw new AccesoNoPermitidoException("Este usuario no puede rechazar este intercambio");
        }

        updateEstadoIntercambio(intercambio, estadoIntercambio);
        return intercambioMapper.toGetIntercambio(intercambio);
    }

    @Transactional
    @Override
    public GetIntercambio cancelarIntercambio(Long usuarioId, Long intercambioId, EstadoIntercambio estadoIntercambio) {
        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al cancelar el intercambio," +
                        "con id "+intercambioId+" no existe en BD."));

        if(!(intercambio.getUsuarioUno().getId().equals(usuarioId) || intercambio.getUsuarioDos().getId().equals(usuarioId))){
            throw new LogicaNegocioException("Solo puede cancelar el intercambio un usuario que participe en dicho intercambio");
        }
        updateEstadoIntercambio(intercambio, estadoIntercambio);

        return null;
    }
}
