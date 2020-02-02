package com.lexcom_cargas;

import java.io.Serializable;

public class Estatus_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer estatus;
    private String nombre;
    private String estado;
            
    public Estatus_List(Integer estatus, String nombre, String estado) {
        this.estatus = estatus;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
