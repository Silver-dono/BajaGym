package com.bajagym.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bajagym.model.ClasesColectivas;
import com.bajagym.model.Rutina;
import com.bajagym.repositories.ClasesColectivasDAO;
import com.bajagym.repositories.RutinaDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.bajagym.model.Usuario;
import com.bajagym.repositories.UsuarioDAO;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

    private Logger logger = LogManager.getLogger(UsuarioController.class);

    @Value("${internalService.baseUri}")
    private String intServiceURI;

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private ClasesColectivasDAO clasesColectivasDAO;
    @Autowired
    private RutinaDAO rutinaDAO;

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

        PostUser postUser = new PostUser(userName,correo,checkboxvalue);

        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.postForObject(intServiceURI + "/service/usuario", postUser, String.class);
        } catch (RestClientException ex) {
            logger.error("Error connection refuse internal service");
            return "error_servicio_interno";
        }

        usuarioDAO.save(new Usuario(userName,edad,new BCryptPasswordEncoder().encode(contrasenia),roles, checkboxvalue));

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
        Usuario user = usuarioDAO.findByNombre(name);
        List<String> colectivas = new ArrayList<>();

        for(ClasesColectivas clase : lista) {
            colectivas.add(clase.toString());
        }
        model.addAttribute("lista", lista);
        model.addAttribute("name",name);
        //model.addAttribute("entrenador", request.isUserInRole("ROLE_ENTRENADOR"));)
        if(user.isEntrenador()){
            model.addAttribute("entrenador", user.getNombre());
        }
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
            if(usuario.getRutina() != null) {
                model.addAttribute("rutina", usuario.getRutina().toString());
            }
        }
        model.addAttribute("user",true);
        return "rutinas_logeado";
    }
    @RequestMapping("/cambiarRutina/{name}")
    public String changeRutinaPersonal(Model model, @PathVariable String name){
        List<Usuario> usuarios = usuarioDAO.findAllByIsUsuario();
        List<Rutina> rutinas= rutinaDAO.findAll();
        model.addAttribute("name",name);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("rutinas", rutinas);

        return "cambiar_rutinas";
    }
    @RequestMapping("/cambiarRutina/cambiadoRutina")
    public String changedRutinaPersonal(Model model, @RequestParam Long id_usuario, @RequestParam Long id_rutina){
        Usuario user = usuarioDAO.findById(id_usuario).get();
        Rutina rutina= rutinaDAO.findById(id_rutina).get();
        user.setRutina(rutina);
        usuarioDAO.setUsuarioRutinaByNombre(rutina, user.getNombre());

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(intServiceURI + "/service/mail/usuario/" + user.getNombre(), String.class);
        } catch (RestClientException ex) {
            logger.error("Error connection refuse internal service");
        }
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

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject(intServiceURI + "/service/mail/entrenadores/"+name,String.class);

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


    private class PostUser{

        private String nombre;
        private String correo;
        private boolean entrenador;

        public PostUser(){}

        public PostUser(String nombre, String correo, boolean entrenador) {
            this.nombre = nombre;
            this.correo = correo;
            this.entrenador = entrenador;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public boolean isEntrenador() {
            return entrenador;
        }

        public void setEntrenador(boolean entrenador) {
            this.entrenador = entrenador;
        }
    }

}