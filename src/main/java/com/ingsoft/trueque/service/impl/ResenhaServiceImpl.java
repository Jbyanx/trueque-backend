package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import com.ingsoft.trueque.exception.LogicaNegocioException;
import com.ingsoft.trueque.exception.ResenhaNotFoundException;
import com.ingsoft.trueque.mapper.ResenhaMapper;
import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.model.Resenha;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.repository.ResenhaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.ResenhaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ResenhaServiceImpl implements ResenhaService {
    private final ResenhaRepository resenhaRepository;
    private final IntercambioRepository intercambioRepository;
    private final ResenhaMapper resenhaMapper;
    private final UsuarioRepository usuarioRepository;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetResenha> getAllResenhas(Pageable pageable) {
        return resenhaRepository.findAll(pageable)
                .map(resenhaMapper::toGetResenha);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public GetResenha getResenhaById(Long id) {
        return resenhaRepository.findById(id)
                .map(resenhaMapper::toGetResenha)
                .orElseThrow(() -> new ResenhaNotFoundException("Error al obtener la resenha con id "+id+", no existe en BD"));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    @Transactional
    public GetResenha saveResenha(Long idIntercambio, SaveResenha resenha) {
        Intercambio intercambio = intercambioRepository.findById(idIntercambio)
                .orElseThrow(() -> new ResenhaNotFoundException("Error al obtener la resenha con id "+idIntercambio+", no existe en BD"));

        Usuario usuarioCalificante = usuarioRepository.findById(resenha.getIdUsuarioCalificante())
                .orElseThrow(() -> new ResenhaNotFoundException("Error al obtener la resenha con id "+resenha.getIdUsuarioCalificante()+", no existe en BD"));

        Usuario usuarioCalificado = determinarUsuarioCalificado(usuarioCalificante, intercambio);

        if(usuarioCalificante.getId().equals(intercambio.getUsuarioDos().getId())){
            throw new LogicaNegocioException("No puedes guardar una reseña para el mismo usuario que la crea");
        }

        Resenha resenhaToSave = resenhaMapper.toResenha(resenha);
        resenhaToSave.setUsuarioCalificado(usuarioCalificado);
        resenhaToSave.setUsuarioCalificante(usuarioCalificante);
        resenhaToSave.setIntercambio(intercambio);

        return resenhaMapper.toGetResenha(
                resenhaRepository.save(
                    resenhaToSave
                )
        );
    }

    private Usuario determinarUsuarioCalificado(Usuario usuarioCalificante, Intercambio intercambio) {
        if (usuarioCalificante.getId().equals(intercambio.getUsuarioUno().getId())) {
            return intercambio.getUsuarioDos();
        } else if (usuarioCalificante.getId().equals(intercambio.getUsuarioDos().getId())) {
            return intercambio.getUsuarioUno();
        } else {
            throw new LogicaNegocioException("El usuarioCalificante no participó en este intercambio.");
        }
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetResenha updateResenhaById(Long id, SaveResenha resenha) {
        Resenha resenhaSaved = resenhaRepository.findById(id)
                .orElseThrow(() -> new ResenhaNotFoundException("Error al obtener la resenha con id "+id+", no existe en BD"));

        updateResenha(resenhaSaved, resenha);
        return resenhaMapper.toGetResenha(resenhaRepository.save(resenhaSaved));
    }

    private void updateResenha(Resenha resenhaSaved, SaveResenha resenha) {
        if(StringUtils.hasText(resenha.getDescripcion())){
            resenhaSaved.setDescripcion(resenha.getDescripcion());
        }
        if(resenha.getPuntuacion() != null && resenha.getPuntuacion() > 0 && resenha.getPuntuacion()<= 5){
            resenhaSaved.setPuntuacion(resenha.getPuntuacion());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deleteResenhaById(Long id) {
        if(resenhaRepository.existsById(id)){
            resenhaRepository.deleteById(id);
        }
    }
}
