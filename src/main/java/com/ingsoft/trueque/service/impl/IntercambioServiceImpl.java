package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.exception.IntercambioNotFoundException;
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
        return intercambioMapper.toGetIntercambioList(
                intercambioRepository.historialIntercambiosByIdUsuarioAndEstado(id, estadoIntercambio)
        );
    }

    @Override
    public GetIntercambio getIntercambioById(Long id) {
        return intercambioRepository.findById(id)
                .map(intercambioMapper::toGetIntercambio)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
    }

    @Override
    public GetIntercambio saveIntercambio(SaveIntercambio intercambio) {
        Intercambio intercambioToSave = intercambioMapper.toIntercambio(intercambio);

        // El propietario del articulo dos no debe ser el propietario del articulo uno
        if (intercambioToSave.getArticuloDos().getPropietario().equals(
                intercambioToSave.getArticuloUno().getPropietario())) {
            throw new RuntimeException("No puedes ofrecer un artículo por otro tuyo.");
        }

        if (intercambioToSave.getArticuloDos().getEstado() != EstadoArticulo.DISPONIBLE) {
            throw new RuntimeException("El artículo solicitado no está disponible para el intercambio.");
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
        updateIntercambio(intercambioToUpdate, estadoIntercambio);
        return intercambioMapper.toGetIntercambio(
                intercambioRepository.save(intercambioToUpdate)
        );
    }

    private void updateIntercambio(Intercambio intercambioToUpdate, EstadoIntercambio estadoIntercambio) {
        if(estadoIntercambio != null){
            intercambioToUpdate.setEstado(estadoIntercambio);
        }
    }

    @Override
    public void deleteIntercambioById(Long id) {
        if (intercambioRepository.existsById(id)) {
            intercambioRepository.deleteById(id);
        }
    }
}
