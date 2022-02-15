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

import com.bajagym.model.ClasesColectivas;
import com.bajagym.repositories.ClasesColectivasDAO;

@RequestMapping("/ClasesColectivas")
@RestController
public class ClasesColectivasController {

	@Autowired
	private ClasesColectivasDAO repositoryGroupLessons;

	@GetMapping("/")
	public List<ClasesColectivas> getGroupLessons() {
		return repositoryGroupLessons.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClasesColectivas> deleteExercise(@PathVariable Long id) {
		Optional<ClasesColectivas> groupLesson = repositoryGroupLessons.findById(id);
		if (groupLesson.isPresent()) {
			repositoryGroupLessons.deleteById(id);
			return ResponseEntity.ok(groupLesson.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/newGroupLesson")
	public void newExercise(@RequestBody ClasesColectivas newGroupLesson) {
		repositoryGroupLessons.save(newGroupLesson);
	}
}
