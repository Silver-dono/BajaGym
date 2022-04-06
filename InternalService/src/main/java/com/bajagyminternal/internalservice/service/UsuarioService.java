package com.bajagyminternal.internalservice.service;

import com.bajagyminternal.internalservice.model.Usuario;
import com.bajagyminternal.internalservice.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @PostMapping("usuario")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario){
        usuarioDAO.save(usuario);
        return ResponseEntity.ok().build();
    }
}
