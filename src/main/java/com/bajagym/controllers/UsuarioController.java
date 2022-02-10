package com.bajagym.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bajagym.model.Usuario;
import com.bajagym.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController{
	
	@Autowired
	private UsuarioRepository repositoryUser;
	
	@GetMapping("/")
	public List<Usuario> getUsers() {
		return repositoryUser.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> deleteUser(@PathVariable Long id){
		Optional<Usuario> user = repositoryUser.findById(id);
		if (user.isPresent()) {
			 return ResponseEntity.ok(user.get());
		} else {
			 return ResponseEntity.notFound().build();
		}

	}
	@PostMapping("/")
	public void newUser(@RequestBody Usuario newUser) {
		
		repositoryUser.save(newUser);	
	}
	
	
}