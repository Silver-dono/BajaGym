package com.bajagym.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "serie")
public class Serie {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSerie;

    @Column(name = "reps", nullable = false)
    private int repeticiones;

    @Column(name = "descanso", nullable = false)
    private int descanso;

    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "rutina_id", referencedColumnName = "id_externo", nullable = false)
    private Long idRutina;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.EAGER, optional = false, targetEntity = Ejercicio.class)
    @JoinColumn(name = "ejercicio_id", referencedColumnName = "id", nullable = false)
    private Ejercicio ejercicio;


    public Long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

    public Long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }
}
