package com.bajagym.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Material.class)
    @JoinColumn(name = "material_id", referencedColumnName = "id", nullable = false)
    private Material material;


    public Long getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Long idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
