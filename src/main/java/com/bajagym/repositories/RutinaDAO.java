package com.bajagym.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Rutina;
import org.springframework.data.jpa.repository.Query;

@CacheConfig(cacheNames = "RutinasEj")
public interface RutinaDAO extends JpaRepository<Rutina, Long> {

    @Query(value = "SELECT COUNT(rutina) FROM Rutina rutina")
    long countAll();

    List<Rutina> findAll();

    List<Rutina> findByNombre(String nombre);

    @Cacheable
    List<Rutina> findAllByEjemploTrue();

    @CacheEvict(allEntries = true, condition = "#r.ejemplo == true")
    Rutina save(Rutina r);

    Optional<Rutina> findById(Long id);
}
