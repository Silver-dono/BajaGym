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

import com.bajagym.model.Ejercicio;
import com.bajagym.repositories.EjercicioDAO;

@RequestMapping("/ejercicios")
@Controller
public class EjercicioController {

    @Autowired
    private EjercicioDAO ejercicioDAO;

    @GetMapping("/")
    public List<Ejercicio> getExercises() {
        return ejercicioDAO.findAll();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Ejercicio> deleteExercise(@PathVariable Long id) {
        Optional<Ejercicio> exercise = ejercicioDAO.findById(id);
        if (exercise.isPresent()) {
            ejercicioDAO.deleteById(id);
            return ResponseEntity.ok(exercise.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/newExercise")
    public void newExercise(@RequestBody Ejercicio newExercise) {
        ejercicioDAO.save(newExercise);
    }

}
