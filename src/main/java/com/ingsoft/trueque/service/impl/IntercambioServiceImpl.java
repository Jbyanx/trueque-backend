package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.exception.IntercambioNotFoundException;
import com.ingsoft.trueque.mapper.IntercambioMapper;
import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public GetIntercambio getIntercambioById(Long id) {
        return intercambioRepository.findById(id)
                .map(intercambioMapper::toGetIntercambio)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
    }

    @Override
    public GetIntercambio saveIntercambio(SaveIntercambio intercambio) {
        return intercambioMapper.toGetIntercambio(
                intercambioRepository.save(
                        intercambioMapper.toIntercambio(intercambio)
                )
        );
    }

    /***
     * Este metodo solo cambia el estado
     * @param id
     * @param intercambio
     * @return
     */
    @Override
    @Transactional
    public GetIntercambio updateIntercambioById(Long id, SaveIntercambio intercambio) {
        Intercambio intercambioToUpdate = intercambioRepository.findById(id)
                .orElseThrow(() -> new IntercambioNotFoundException("Error al buscar!, el intercambio con id "+ id + " no existe en BD."));
        updateIntercambio(intercambioToUpdate, intercambio);
        return intercambioMapper.toGetIntercambio(
                intercambioRepository.save(intercambioToUpdate)
        );
    }

    private void updateIntercambio(Intercambio intercambioToUpdate, SaveIntercambio intercambio) {
        if(intercambio.estadoIntercambio()!=null){
            intercambioToUpdate.setEstado(intercambio.estadoIntercambio());
        }
    }

    @Override
    public void deleteIntercambioById(Long id) {
        if (intercambioRepository.existsById(id)) {
            intercambioRepository.deleteById(id);
        }
    }
}
