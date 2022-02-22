package com.bajagym.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bajagym.model.ClasesColectivas;
import com.bajagym.model.Rutina;
import com.bajagym.repositories.ClasesColectivasDAO;
import com.bajagym.repositories.RutinaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.bajagym.model.Usuario;
import com.bajagym.repositories.UsuarioDAO;


@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ClasesColectivasDAO clasesColectivasDAO;

    @Autowired
    private RutinaDAO rutinaDAO;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @RequestMapping ("/loging")
    public String login(Model model,@RequestParam String userName) {
        model.addAttribute("name",userName);

        return "usuario_logeado";
    }

    @RequestMapping ("/loging/{name}")
    public String vuelta(Model model,@PathVariable String name) {
        model.addAttribute("name",name);

        return "usuario_logeado";
    }



    @RequestMapping("/registered")
    public String registered(Model model, @RequestParam String userName,@RequestParam int edad){
        model.addAttribute("name",userName);
        usuarioDAO.save(new Usuario(userName,edad));
        return "registered_succesfull";
    }

    @RequestMapping ("/ClasesColectivas/{name}")
    public String getGroupLessons(Model model,@PathVariable String name) {
        List<ClasesColectivas> lista = clasesColectivasDAO.findAll();
        List<String> colectivas = new ArrayList<>();
        for(ClasesColectivas clase : lista) {
            colectivas.add(clase.toString());
        }
        model.addAttribute("name",name);
        model.addAttribute("colectiva",colectivas);
        model.addAttribute("user",true);
        return "clases_colectivas";
    }

    @RequestMapping ("/rutinas/{name}")
    public String getRutinaPersonales(Model model,@PathVariable String name) {
        List<Rutina> lista = rutinaDAO.findByNombre(name);
        List<String> rutinas = new ArrayList<>();
        for(Rutina clase : lista) {
            rutinas.add(clase.toString());
        }
        model.addAttribute("name",name);
        model.addAttribute("rutina",rutinas);
        model.addAttribute("user",true);
        return "rutinas_logeado";
    }

    @RequestMapping("/crearRutina/{name}")
    public String setNewRutina(Model model, @PathVariable String name){
        model.addAttribute("name",name);
        return "crear_rutinas";
    }

    @RequestMapping("/crearClaseColectiva/{name}")
    public String setNewClaseColectiva(Model model, @PathVariable String name){
        model.addAttribute("name",name);
        return "crear_clasesColectivas";
    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsers() {
        return usuarioDAO.findAll();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable Long id) {
        Optional<Usuario> user = usuarioDAO.findById(id);
        if (user.isPresent()) {
            usuarioDAO.deleteById(id);
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/newUser")
    public void newUser(@RequestBody Usuario newUser) {

        usuarioDAO.save(newUser);
    }


}