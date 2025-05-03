package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResenhaService {
    Page<GetResenha> getAllResenhas(Pageable pageable);
    GetResenha getResenhaById(Long id);
    GetResenha saveResenha(Long idIntercambio, SaveResenha resenha);
    GetResenha updateResenhaById(Long id, SaveResenha resenha);
    void deleteResenhaById(Long id);
}
