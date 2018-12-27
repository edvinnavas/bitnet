package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Banco_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String banco;
    private Date fecha;

    public Juicio_Banco_List(Integer juicio, String banco, Date fecha) {
        this.juicio = juicio;
        this.banco = banco;
        this.fecha = fecha;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
