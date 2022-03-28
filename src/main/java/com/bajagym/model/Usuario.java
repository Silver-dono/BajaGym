package com.bajagym.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "contraseña", nullable = false)
    private String passwordHash;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "entrenador")
    private boolean entrenador;

    @Column(name = "Roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Rutina.class)
    @JoinColumn(name = "rutina_id", referencedColumnName = "id")
    private Rutina rutina;


    public Usuario(){}

    public Usuario(String name, int edad,String passwordHash){
        this.nombre=name;
        this.edad = edad;
        this.passwordHash = passwordHash;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<String> getRoles() {
        return roles;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isEntrenador() {
        return entrenador;
    }

    public void setEntrenador(boolean entrenador) {
        this.entrenador = entrenador;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }
}
