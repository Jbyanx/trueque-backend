package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IntercambioRepository extends JpaRepository<Intercambio, Long> {

    @Query("update Intercambio i set i.estado = ?1")
    @Modifying
    Intercambio cambiarEstadoDelIntercambio(EstadoIntercambio estado);

    @Query("select i from Intercambio i where (i.usuarioUno = ?1 or i.usuarioDos = ?1) and i.estado = ?2")
    List<Intercambio> historialIntercambiosByIdUsuarioAndEstado(Long idUsuario, EstadoIntercambio estado);


    @Query("select i from Intercambio i where i.usuarioUno = ?1 or i.usuarioDos = ?1")
    List<Intercambio> historialIntercambiosDelUsuario(Long id);
}
