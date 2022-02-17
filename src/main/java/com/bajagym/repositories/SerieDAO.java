package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Serie;
public interface SerieDAO extends JpaRepository<Serie, Long>{
	Optional<Serie> findById(Long id);
	List<Serie> findAll();
	void deleteById(Long id);
	Serie save(Serie set);
}
