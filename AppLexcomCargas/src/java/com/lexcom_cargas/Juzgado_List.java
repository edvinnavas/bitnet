package com.lexcom_cargas;

import java.io.Serializable;

public class Juzgado_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer juzgado;
    private String nombre;
    private String estado;
            
    public Juzgado_List(Integer juzgado, String nombre, String estado) {
        this.juzgado = juzgado;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
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
