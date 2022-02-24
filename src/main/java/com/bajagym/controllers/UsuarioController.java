package com.bajagym.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.bajagym.model.ClasesColectivas;
import com.bajagym.model.Rutina;
import com.bajagym.repositories.ClasesColectivasDAO;
import com.bajagym.repositories.RutinaDAO;
import org.apache.commons.lang3.time.DateUtils;
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

    @RequestMapping("/crearRutina/newRutina")
    public String newRutina(Model model, @RequestParam String userName,@RequestParam(required=false) boolean ejemplo){
        model.addAttribute("name",userName);

        rutinaDAO.save(new Rutina(userName,ejemplo, rutinaDAO.countAll()+1));
        return "rutina_creada";
    }
    @RequestMapping("/crearClaseColectiva/newClaseColectiva")
    public String registered(Model model, @RequestParam String userName,@RequestParam String date, @RequestParam long id_rutina){
        model.addAttribute("name",userName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            clasesColectivasDAO.save(new ClasesColectivas(userName, format.parse(date), rutinaDAO.findById(id_rutina).get()));
        } catch (ParseException ex){
            System.out.println(ex);
        }
        return "claseColectiva_creada";
    }
    @RequestMapping("/ClasesColectivas/{nameClase}/delete/")
    public String deleteGroupLesson(Model model, @PathVariable String nameClase){
        model.addAttribute("nameClase", nameClase);
        clasesColectivasDAO.deleteByNombreClase(nameClase);
        return "claseColectiva_borrada";
    }

    @RequestMapping ("/ClasesColectivas/{name}")
    public String getGroupLessons(Model model,@PathVariable String name) {
        List<ClasesColectivas> lista = clasesColectivasDAO.findAll();
        List<String> colectivas = new ArrayList<>();

        for(ClasesColectivas clase : lista) {
            colectivas.add(clase.toString());
        }
        model.addAttribute("lista", lista);
        model.addAttribute("name",name);
        model.addAttribute("colectiva",colectivas);
        model.addAttribute("user",true);
        return "clases_colectivas";
    }
    @RequestMapping("/rutinas/{name}/deleteRutina")
    public String deleteRutinaPersonal(Model model, @PathVariable String name){
        Usuario user = usuarioDAO.findByNombre(name);
        model.addAttribute("name",name);
        user.setRutina(null);

        return "rutinas_logeado";

    }
    @RequestMapping ("/rutinas/{name}")
    public String getRutinaPersonales(Model model,@PathVariable String name) {
        Rutina rutina = usuarioDAO.getRutinaUsuario(name);
        model.addAttribute("name",name);
        if(rutina != null){
            model.addAttribute("rutina",rutina.toString());
        }
        else{
            model.addAttribute("rutina","no hay rutinas personales");
        }
        model.addAttribute("user",true);
        return "rutinas_logeado";
    }
    @RequestMapping("/cambiarRutina/{name}")
    public String changeRutinaPersonal(Model model, @PathVariable String name){
        List<Rutina> rutinas= rutinaDAO.findAll();
        List<Long> idRutinas=new ArrayList();
        for(Rutina r: rutinas){
            idRutinas.add(r.getIdRutina());
        }
        model.addAttribute("name",name);
        model.addAttribute("rutinas", rutinas);
        model.addAttribute("idListaRutinas", idRutinas);

        return "cambiar_rutinas";
    }
    @RequestMapping("/cambiarRutina/cambiadoRutina/{name}")
    public String changedRutinaPersonal(@PathVariable String name, @RequestParam Long id_rutina){
        Usuario user = usuarioDAO.findByNombre(name);
        Optional<Rutina> rutina= rutinaDAO.findById(id_rutina);
        user.setRutina(rutina.get());
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
        model.addAttribute("rutinas", rutinaDAO.findAll());
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