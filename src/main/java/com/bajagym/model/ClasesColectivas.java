package com.bajagym.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "clases_colectivas")
public class ClasesColectivas {



    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClasesColectivas;

    @Column(name = "nombre", nullable = false)
    private String nombreClase;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = Rutina.class)
    @JoinColumn(name = "rutina_id", referencedColumnName = "id", nullable = false)
    private Rutina rutina;

    public ClasesColectivas(){

    }

    public ClasesColectivas(String nombreClase, Date fecha, Rutina rutina){
        this.nombreClase = nombreClase;
        this.fecha = fecha;
        this.rutina = rutina;
    }

    public Long getIdClasesColectivas() {
        return idClasesColectivas;
    }

    public void setIdClasesColectivas(Long idClasesColectivas) {
        this.idClasesColectivas = idClasesColectivas;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }


    @Override
    public String toString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return "nombre: "+getNombreClase()+", Fecha:"+format.format(getFecha());
    }
}
