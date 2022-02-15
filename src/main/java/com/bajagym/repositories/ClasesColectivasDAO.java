package com.bajagym.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bajagym.model.ClasesColectivas;
import com.bajagym.model.Ejercicio;
import com.bajagym.model.Material;
public interface ClasesColectivasDAO extends JpaRepository<ClasesColectivas, Long>{

	Optional<ClasesColectivas> findByNombre(String name);
	List<ClasesColectivas> findAll();
	Optional<ClasesColectivas> findByFecha(String fecha);
	Optional<ClasesColectivas> findById(Long id);
	void deleteById(Long id);
	ClasesColectivas save(ClasesColectivas e);
}
