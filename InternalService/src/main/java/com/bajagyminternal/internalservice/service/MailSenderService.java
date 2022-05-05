package com.bajagyminternal.internalservice.service;

import com.bajagyminternal.internalservice.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mailSenderService")
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMailToEntrenadores(List<Usuario> entrenadores, String name){
        for (Usuario entrenador : entrenadores) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(entrenador.getCorreo());
            mailMessage.setSubject("Solicitud cambio rutina: " + name);
            mailMessage.setText("El usuario " + name + " ha solicitado un cambio de su rutina personal");
            javaMailSender.send(mailMessage);
        }
    }

    @Async
    public void sendMailToUsuario(Usuario user){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getCorreo());
        mailMessage.setSubject("Rutina cambiada");
        mailMessage.setText("Se ha cambiado tu rutina personal, revisa la aplicacion para verlo");
        javaMailSender.send(mailMessage);
    }
}
