package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoEqualsIgnoreCase(String correo);
    Optional<Usuario> getUsuarioById(Long id);

    @Query("select avg(r.puntuacion) from Resenha r where r.usuarioCalificado.id = ?1")
    Double obtenerReputacionDelUsuario(Long idUsuario);

    @Query("select count(r) from Resenha r where r.usuarioCalificado.id = ?1")
    Long totalResenhasDelUsuario(Long idUsuario);

}
