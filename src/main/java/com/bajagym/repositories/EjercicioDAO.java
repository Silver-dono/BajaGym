package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Ejercicio;

public interface EjercicioDAO extends JpaRepository<Ejercicio, Long>{

	Optional<Ejercicio> findByNombre(String name);
	List<Ejercicio> findAll();
	Optional<Ejercicio> findByIdEjercicio(Long id);
	void deleteByIdEjercicio(Long id);
	Ejercicio save(Ejercicio e);
}
