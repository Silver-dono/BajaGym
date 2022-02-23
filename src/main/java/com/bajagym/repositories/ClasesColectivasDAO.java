package com.bajagym.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bajagym.model.ClasesColectivas;

import javax.transaction.Transactional;

@Transactional
public interface ClasesColectivasDAO extends JpaRepository<ClasesColectivas, Long> {

    List<ClasesColectivas> findAll();

    List<ClasesColectivas> findAllByFecha(Date fecha);

    Optional<ClasesColectivas> findByIdClasesColectivas(Long id);

    void deleteByIdClasesColectivas(Long id);

    void deleteByNombreClase(String name);

    ClasesColectivas save(ClasesColectivas e);
}
