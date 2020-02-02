package com.lexcom_cargas;

import java.io.Serializable;

public class Juicio_Banco_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String banco;
    private String fecha;

    public Juicio_Banco_List(String banco, String fecha) {
        this.banco = banco;
        this.fecha = fecha;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
