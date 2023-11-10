package com.example.p1_1accesodatosvet;

import java.util.Date;

/**
 * Clase Mascota
 * Clase para poder operar con las mascotas de la aplicacion de veterinario.
 */
public class Mascota {
    private int id;

    private String nombre;

    private String raza;

    private Double peso;

    private Date fechaN;

    private String causaConsulta;

    private String otros;

    //Constructor con todos los parametros
    public Mascota(int id, String nombre, String raza, Double peso, java.sql.Date fechaN, String causaConsulta, String otros) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
        this.fechaN = fechaN;
        this.causaConsulta = causaConsulta;
        this.otros = otros;
    }

    public Mascota(String nombre, String raza, Double peso, Date fechaN, String causaConsulta, String otros) {
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
        this.fechaN = fechaN;
        this.causaConsulta = causaConsulta;
        this.otros = otros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getCausaConsulta() {
        return causaConsulta;
    }

    public void setCausaConsulta(String causaConsulta) {
        this.causaConsulta = causaConsulta;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }
}
