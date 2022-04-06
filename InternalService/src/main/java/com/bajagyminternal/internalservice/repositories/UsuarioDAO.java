package com.bajagyminternal.internalservice.repositories;

import com.bajagyminternal.internalservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    @Query(value = "FROM Usuario usuario WHERE usuario.entrenador = true")
    List<Usuario> findAllByIsEntrenador();

    Optional<Usuario> findByNombre(String nombre);
}