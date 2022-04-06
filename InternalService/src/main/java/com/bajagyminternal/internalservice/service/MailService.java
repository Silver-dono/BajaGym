package com.bajagyminternal.internalservice.service;

import com.bajagyminternal.internalservice.model.Usuario;
import com.bajagyminternal.internalservice.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("service")
public class MailService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("mail/entrenadores/{name}")
    public ResponseEntity<String> notificaEntrenadores(@PathVariable String name){
        try{
            List<Usuario> entrenadores = usuarioDAO.findAllByIsEntrenador();
            for (Usuario entrenador : entrenadores) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(entrenador.getCorreo());
                mailMessage.setSubject("Solicitud cambio rutina: " + name);
                mailMessage.setText("El usuario " + name + " ha solicitado un cambio de su rutina personal");
                javaMailSender.send(mailMessage);
            }
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
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getCorreo());
                mailMessage.setSubject("Rutina cambiada");
                mailMessage.setText("Se ha cambiado tu rutina personal, revisa la aplicacion para verlo");
                javaMailSender.send(mailMessage);
            } catch (Exception ex){
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
