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

import com.bajagym.model.Ejercicio;
import com.bajagym.repositories.EjercicioDAO;

@RequestMapping("/ejercicios")
@RestController
public class EjercicioController {

	@Autowired
	private EjercicioDAO repositoryExercise;

	@GetMapping("/")
	public List<Ejercicio> getExercises() {
		return repositoryExercise.findAll();
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Ejercicio> deleteExercise(@PathVariable Long id) {
		Optional<Ejercicio> exercise = repositoryExercise.findById(id);
		if (exercise.isPresent()) {
			repositoryExercise.deleteById(id);
			return ResponseEntity.ok(exercise.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/newExercise")
	public void newExercise(@RequestBody Ejercicio newExercise) {
		repositoryExercise.save(newExercise);
	}

}
