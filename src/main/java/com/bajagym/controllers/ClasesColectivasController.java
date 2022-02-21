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
	private ClasesColectivasDAO clasesColectivasDAO;

	@GetMapping("/")
	public List<ClasesColectivas> getGroupLessons() {
		return clasesColectivasDAO.findAll();
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<ClasesColectivas> deleteGroupLesson(@PathVariable Long id) {
		Optional<ClasesColectivas> groupLesson = clasesColectivasDAO.findById(id);
		if (groupLesson.isPresent()) {
			clasesColectivasDAO.deleteById(id);
			return ResponseEntity.ok(groupLesson.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/newGroupLesson")
	public void newGroupLesson(@RequestBody ClasesColectivas newGroupLesson) {
		clasesColectivasDAO.save(newGroupLesson);
	}
}
