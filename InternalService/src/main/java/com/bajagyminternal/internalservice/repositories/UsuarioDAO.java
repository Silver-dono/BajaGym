package com.bajagyminternal.internalservice.repositories;

import com.bajagyminternal.internalservice.model.Usuario;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "Entrenadores")
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    @Cacheable()
    List<Usuario> findAllByEntrenadorTrue();

    @CacheEvict(allEntries = true, condition = "#user.entrenador == true")
    Usuario save(Usuario user);

    Optional<Usuario> findByNombre(String nombre);
}
