package com.bajagyminternal.internalservice.service;

import com.bajagyminternal.internalservice.model.Usuario;
import com.bajagyminternal.internalservice.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("service")
public class MailService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("mail/entrenadores/{name}")
    public ResponseEntity<String> notificaEntrenadores(@PathVariable String name){
        try{
            List<Usuario> entrenadores = usuarioDAO.findAllByEntrenadorTrue();

            mailSenderService.sendMailToEntrenadores(entrenadores, name);
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("mail/usuario/{name}")
    public ResponseEntity<String> notificaUsuario(@PathVariable String name){
        Optional<Usuario> usuario = usuarioDAO.findByNombre(name);
        if(usuario.isPresent()) {
            try{
                Usuario user = usuario.get();
                mailSenderService.sendMailToUsuario(user);
            } catch (Exception ex){
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
