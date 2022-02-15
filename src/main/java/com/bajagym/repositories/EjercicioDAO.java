package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Ejercicio;
import com.bajagym.model.Material;
public interface EjercicioDAO extends JpaRepository<Ejercicio, Long>{

	List<Material> findByNombre(String name);
	Optional<Ejercicio> findById(Long id);
	void deleteById(Long id);
	Ejercicio save(Ejercicio e);
}
