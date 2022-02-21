package com.bajagym.repositories;
import java.util.List;
import java.util.Optional;

import com.bajagym.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bajagym.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	List<Usuario> findByNombre(String name);
	Optional<Usuario> findByIdUsuario(Long id);

	@Query(value = "SELECT usuario.rutina FROM Usuario usuario WHERE usuario.nombre like :name")
	Rutina getRutinaUsuario(@Param("name") String name);

	void deleteByIdUsuario(Long id);
	Usuario save(Usuario user);
 }