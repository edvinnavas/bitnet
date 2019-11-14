package com.lexcom_cargas;

import java.io.Serializable;

public class Constantes_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer constantes;
    private String nombre;
    private String valor;

    public Constantes_List(Integer constantes, String nombre, String valor) {
        this.constantes = constantes;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Integer getConstantes() {
        return constantes;
    }

    public void setConstantes(Integer constantes) {
        this.constantes = constantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
