package com.bajagym.controllers;

import java.util.*;

import com.bajagym.repositories.UsuarioDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bajagym.model.Rutina;
import com.bajagym.repositories.RutinaDAO;

@RequestMapping("/rutinas")
@Controller
public class RutinaController {
    @Autowired
    private RutinaDAO rutinaDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/")
    public List<Rutina> getRoutines() {
        return rutinaDAO.findAll();
    }

    @GetMapping("/ejemplos")
    public String ejemplo(Model model) {
        List<Rutina> lista = rutinaDAO.findAllByEjemploTrue();
        List<String> rutinas = new ArrayList<>();
        for(Rutina rut: lista){
            rutinas.add(rut.toString());
        }
        model.addAttribute("rutina",rutinas);
        return "rutina_ejemplo";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Rutina> deleteRoutine(@PathVariable Long id) {
        Optional<Rutina> routine = rutinaDAO.findById(id);
        if (routine.isPresent()) {
            rutinaDAO.deleteById(id);
            return ResponseEntity.ok(routine.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{nombre}")
    public Rutina getRutinaUsuario(@PathVariable String nombre) {
        return usuarioDAO.getRutinaUsuario(nombre);
    }

    @GetMapping("/ejemplo")
    public List<Rutina> getRutinasEjemplo() {
        List<Rutina> rutinas = rutinaDAO.findAllByEjemploTrue();
        if (CollectionUtils.isNotEmpty(rutinas)) {
            return rutinas;
        } else {
            return null;
        }
    }

    @PostMapping("/newRoutine")
    public void newRoutine(@RequestBody Rutina newRoutine) {

        rutinaDAO.save(newRoutine);
    }

}
