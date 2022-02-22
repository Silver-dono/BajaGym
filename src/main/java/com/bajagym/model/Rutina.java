package com.bajagym.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "rutina")
public class Rutina {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "id_externo", unique = true, nullable = false)
    private Long idExterno;

    @Column(name = "ejemplo")
    private boolean ejemplo;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idRutina", targetEntity = Serie.class)
    private List<Serie> series;



    public Long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(Long idExterno) {
        this.idExterno = idExterno;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public boolean getEjemplo(){
        return ejemplo;
    }

    public void setEjemplo(boolean ejemplo) {
        this.ejemplo = ejemplo;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
@Override
public String toString(){

    return "Nombre: "+getNombre()+"";
}
}
