package com.bajagyminternal.internalservice.model;


import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "entrenador", nullable = false)
    private boolean entrenador;

    public Usuario(){}

    public Usuario(String nombre, String correo, boolean entrenador) {
        this.nombre = nombre;
        this.correo = correo;
        this.entrenador = entrenador;
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
