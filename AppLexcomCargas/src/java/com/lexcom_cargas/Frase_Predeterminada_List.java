package com.lexcom_cargas;

import java.io.Serializable;

public class Frase_Predeterminada_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer frase_predeterminada;
    private String nombre;
    private String tipo;
    private String frase;
    private String estado;

    public Frase_Predeterminada_List(Integer frase_predeterminada, String nombre, String tipo, String frase, String estado) {
        this.frase_predeterminada = frase_predeterminada;
        this.nombre = nombre;
        this.tipo = tipo;
        this.frase = frase;
        this.estado = estado;
    }

    public Integer getFrase_predeterminada() {
        return frase_predeterminada;
    }

    public void setFrase_predeterminada(Integer frase_predeterminada) {
        this.frase_predeterminada = frase_predeterminada;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
