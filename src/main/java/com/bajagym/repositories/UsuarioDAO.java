package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	List<Usuario> findByNombre(String name);
	Optional<Usuario> findById(Long id);
	void deleteById(Long id);
	Usuario save(Usuario user);
 }