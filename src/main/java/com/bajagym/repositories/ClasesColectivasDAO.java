package com.bajagym.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bajagym.model.ClasesColectivas;

public interface ClasesColectivasDAO extends JpaRepository<ClasesColectivas, Long>{

	List<ClasesColectivas> findAll();
	List<ClasesColectivas> findAllByFecha(Date fecha);
	Optional<ClasesColectivas> findByIdClasesColectivas(Long id);
	void deleteByIdClasesColectivas(Long id);
	ClasesColectivas save(ClasesColectivas e);
}
