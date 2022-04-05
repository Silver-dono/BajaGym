package com.bajagym.controllers;

import java.security.Principal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.bajagym.model.Usuario;
import com.bajagym.repositories.UsuarioDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ClasesColectivasDAO clasesColectivasDAO;

    @Autowired
    private RutinaDAO rutinaDAO;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/fallo")
    public String falloregistro() {
        return "usuario_logeado_incorrectamente";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @RequestMapping ("/loging")
    public String login(HttpSession session, HttpServletRequest request,Model model) {
        String name = request.getUserPrincipal().getName();
        Usuario user = usuarioDAO.findByNombre(name);
        model.addAttribute("name",user.getNombre());
        model.addAttribute("entrenador",request.isUserInRole("ROLE_ENTRENADOR"));
        return "usuario_logeado";
    }

    @RequestMapping("/registered")
    public String registered(Model model, @RequestParam String userName,@RequestParam String correo ,@RequestParam int edad,@RequestParam String contrasenia,@RequestParam(value="rol_entrenador",required = false)boolean checkboxvalue){
        model.addAttribute("name",userName);
        List<String> roles = new ArrayList<>();
        if(checkboxvalue){
            roles.add("ROLE_USUARIO");
            roles.add("ROLE_ENTRENADOR");
        }else{
            roles.add("ROLE_USUARIO");
        }
        usuarioDAO.save(new Usuario(userName,correo,edad,new BCryptPasswordEncoder().encode(contrasenia),roles, checkboxvalue));
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
    @RequestMapping("/ClasesColectivas/{idClase}/delete/")
    public String deleteGroupLesson(Model model, @PathVariable Long idClase){
        Optional<ClasesColectivas> claseOp= clasesColectivasDAO.findById(idClase);
        model.addAttribute("nameClase", claseOp.get().getNombreClase());
        model.addAttribute("idClase", idClase);
        clasesColectivasDAO.deleteById(idClase);
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
        usuarioDAO.setUsuarioRutinaByNombre(null, name);

        return "rutinas_logeado";

    }
    @RequestMapping ("/rutinas/{name}")
    public String getRutinaPersonales(Model model,@PathVariable String name) {
        Usuario usuario = usuarioDAO.findByNombre(name);
        model.addAttribute("name",name);
        if(usuario != null) {
            model.addAttribute("rutina", usuario.getRutina().toString());
            model.addAttribute("correo",usuario.getCorreo());
        }
        model.addAttribute("user",true);
        return "rutinas_logeado";
    }
    @RequestMapping("/cambiarRutina/{name}")
    public String changeRutinaPersonal(Model model, @PathVariable String name){
        List<Rutina> rutinas= rutinaDAO.findAll();
        List<Usuario> usuarios = usuarioDAO.findAllByIsUsuario();
        model.addAttribute("name",name);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("rutinas", rutinas);

        return "cambiar_rutinas";
    }
    @RequestMapping("/cambiarRutina/cambiadoRutina")
    public String changedRutinaPersonal(Model model, @RequestParam Long id_usuario, @RequestParam Long id_rutina){
        Usuario user = usuarioDAO.findByIdUsuario(id_usuario).get();
        Rutina rutina= rutinaDAO.findById(id_rutina).get();
        user.setRutina(rutina);
        usuarioDAO.setUsuarioRutinaByNombre(rutina, user.getNombre());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getCorreo());
        mailMessage.setSubject("Rutina cambiada");
        mailMessage.setText("Se ha cambiado tu rutina personal, revisa la aplicacion para verlo");
        javaMailSender.send(mailMessage);
        model.addAttribute("name", user.getNombre());
        return "rutina_cambiada";
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

    @RequestMapping("/cambioRutinaSolicitado")
    public String requestedChangeRutina(Model model, @RequestParam String name){
        List<Usuario> entrenadores = usuarioDAO.findAllByIsEntrenador();
        for(Usuario entrenador : entrenadores){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(entrenador.getCorreo());
            mailMessage.setSubject("Solicitud cambio rutina: "+ name);
            mailMessage.setText("El usuario " + name + " ha solicitado un cambio de su rutina personal");
            javaMailSender.send(mailMessage);
        }
        return "cambio_rutina_solicitada";
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