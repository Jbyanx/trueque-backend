package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    Optional<Notificacion> findNotificacionById(Long id);

    List<Notificacion> findAll();
    List<Notificacion> findAllByReceptorId(Long receptorId);

    void deleteNotificacionById(Long id);
}
