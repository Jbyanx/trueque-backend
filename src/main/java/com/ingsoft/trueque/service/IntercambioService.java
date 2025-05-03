package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IntercambioService {
    Page<GetIntercambio> getAllIntercambios(Pageable pageable);
    List<GetIntercambio> getIntercambiosByUsuarioIdAndEstado(Long id, EstadoIntercambio estadoIntercambio);
    GetIntercambio getIntercambioById(Long id);
    GetIntercambio solicitarIntercambio(SaveIntercambio intercambio);
    GetIntercambio updateEstadoById(Long id, EstadoIntercambio estado);
    void deleteIntercambioById(Long id);

    GetIntercambio aceptarIntercambio(Long usuarioId, Long intercambioId, EstadoIntercambio estadoIntercambio);

    GetIntercambio rechazarIntercambio(Long usuarioId, Long intercambioId, EstadoIntercambio estadoIntercambio);
}
