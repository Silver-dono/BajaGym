package com.bajagym.repositories;
import com.bajagym.model.Material;
import com.bajagym.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialDAO extends JpaRepository<Material, Long>{
	Optional<Material> findByNombre(String name);
	List<Material> findAll();
	Optional<Material> findById(Long id);
	void deleteById(Long id);
	Material save(Material material);
	
}
