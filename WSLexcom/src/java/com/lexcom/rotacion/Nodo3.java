package com.lexcom.rotacion;

import java.io.Serializable;

public class Nodo3 implements Serializable {
    private static final long serialVersionUID = 1L;
    
    Integer deudor;
    Integer gestor;
    Integer iteracion;
    
    Nodo3 siguiente;
    Nodo3 anterior;

    public Nodo3(Integer deudor, Integer gestor, Integer iteracion) {
        this.deudor = deudor;
        this.gestor = gestor;
        this.iteracion = iteracion;
        
        this.siguiente = null;
        this.anterior = null;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Integer getGestor() {
        return gestor;
    }

    public void setGestor(Integer gestor) {
        this.gestor = gestor;
    }

    public Integer getIteracion() {
        return iteracion;
    }

    public void setIteracion(Integer iteracion) {
        this.iteracion = iteracion;
    }
    
}
