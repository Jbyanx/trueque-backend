package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntercambioService {
    Page<GetIntercambio> getAllIntercambios(Pageable pageable);
    GetIntercambio getIntercambioById(Long id);
    GetIntercambio saveIntercambio(SaveIntercambio intercambio);
    GetIntercambio updateIntercambioById(Long id, SaveIntercambio intercambio);
    void deleteIntercambioById(Long id);
}
