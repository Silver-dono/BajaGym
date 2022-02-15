package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.controllers.RutinaController;
import com.bajagym.model.Rutina;

public interface RutinaDAO extends JpaRepository<Rutina, Long>{

	List<Rutina> findAll();
	Rutina save(Rutina r);
	Optional<Rutina> findById(Long id);
}
