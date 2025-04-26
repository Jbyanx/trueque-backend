package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResenhaService {
    List<GetResenha> getAllResenhas(Pageable pageable);
    GetResenha getResenhaById(Long id);
    GetResenha saveResenha(SaveResenha resenha);
    GetResenha updateResenhaById(Long id, SaveResenha resenha);
    void deleteResenhaById(Long id);
}
