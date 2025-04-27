package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Intercambio;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IntercambioRepository extends JpaRepository<Intercambio, Long> {

    @Query("update Intercambio i set i.estado = ?1")
    @Modifying
    Intercambio cambiarEstadoDelIntercambio(EstadoIntercambio estado);

}
