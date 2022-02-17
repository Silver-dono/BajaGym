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

import com.bajagym.model.Material;
import com.bajagym.repositories.MaterialDAO;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

	@Autowired
	private MaterialDAO repositoryMaterial;

	@GetMapping("/")
	public List<Material> getMaterials() {
		return repositoryMaterial.findAll();
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Material> deleteMaterial(@PathVariable Long id) {
		Optional<Material> material = repositoryMaterial.findById(id);
		if (material.isPresent()) {
			repositoryMaterial.deleteById(id);
			return ResponseEntity.ok(material.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/newMaterial")
	public void newMaterial(@RequestBody Material newMaterial) {
		repositoryMaterial.save(newMaterial);
	}

}
