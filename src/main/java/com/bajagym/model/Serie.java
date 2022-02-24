package com.bajagym.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "serie")
public class Serie {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSerie;

    @Column(name = "repeticiones", nullable = false)
    private int repeticiones;

    @Column(name = "descanso", nullable = false)
    private int descanso;

    @Fetch(FetchMode.JOIN)
    //@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Rutina.class)
    @JoinColumn(name = "rutina_id", referencedColumnName = "id_externo", nullable = false)
    private Long rutina_id;

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
        return rutina_id;
    }

    public void setIdRutina(Long idRutina) {
        this.rutina_id = idRutina;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    @Override
    public String toString(){
        return "Repeticiones: "+getRepeticiones()+", Descanso:"+getDescanso();
    }
}
