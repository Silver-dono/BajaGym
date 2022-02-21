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

import com.bajagym.model.Serie;
import com.bajagym.repositories.SerieDAO;

@RestController
@RequestMapping("/series")
public class SerieController {

	@Autowired
	private SerieDAO serieDAO;

	@GetMapping("/")
	public List<Serie> getSets() {
		return serieDAO.findAll();
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Serie> deleteSet(@PathVariable Long id) {
		Optional<Serie> set = serieDAO.findById(id);
		if (set.isPresent()) {
			serieDAO.deleteById(id);
			return ResponseEntity.ok(set.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/newSet")
	public void newSet(@RequestBody Serie newSet) {

		serieDAO.save(newSet);
	}
}
