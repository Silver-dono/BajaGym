package com.bajagym.repositories;

import java.util.List;
import java.util.Optional;

import com.bajagym.model.Rutina;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@CacheConfig(cacheNames = "usuarios")
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    @Cacheable
    Usuario findByNombre(String name);

    Optional<Usuario> findByIdUsuario(Long id);

    @Query(value = "SELECT usuario.rutina FROM Usuario usuario WHERE usuario.nombre like :name")
    Rutina getRutinaUsuario(@Param("name") String name);

    @Query(value = "FROM Usuario usuario WHERE usuario.entrenador = false")
    List<Usuario> findAllByIsUsuario();

    void deleteByIdUsuario(Long id);

    Usuario save(Usuario user);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u set u.rutina = :rutina where u.nombre = :name")
    void setUsuarioRutinaByNombre(@Param("rutina") Rutina rutina, @Param("name") String name);
}