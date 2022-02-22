package com.bajagym.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bajagym.model.Material;
import com.bajagym.repositories.MaterialDAO;

@RequestMapping("/materiales")
@Controller
public class MaterialController {

    @Autowired
    private MaterialDAO materialDAO;

    @GetMapping("/")
    public List<Material> getMaterials() {
        return materialDAO.findAll();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Material> deleteMaterial(@PathVariable Long id) {
        Optional<Material> material = materialDAO.findById(id);
        if (material.isPresent()) {
            materialDAO.deleteById(id);
            return ResponseEntity.ok(material.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/newMaterial")
    public void newMaterial(@RequestBody Material newMaterial) {
        materialDAO.save(newMaterial);
    }

}
