package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Rutina;
import org.springframework.data.jpa.repository.Query;

public interface RutinaDAO extends JpaRepository<Rutina, Long>{

	List<Rutina> findAll();
	List<Rutina> getAllByEjemplo(boolean ejemplo);
	Rutina save(Rutina r);
	Optional<Rutina> findById(Long id);
}
