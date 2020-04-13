package com.lexcom.rotacion;

import java.io.Serializable;

public class Nodo1 implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String gestor;
    Integer no_deudores;
    Double monto;
    
    Nodo1 siguiente;
    Nodo1 anterior;

    public Nodo1(String gestor, Integer no_deudores, Double monto) {
        this.gestor = gestor;
        this.no_deudores = no_deudores;
        this.monto = monto;
        
        this.siguiente = null;
        this.anterior = null;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getNo_deudores() {
        return no_deudores;
    }

    public void setNo_deudores(Integer no_deudores) {
        this.no_deudores = no_deudores;
    }
    
}
