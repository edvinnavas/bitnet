package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Arraigo_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String arraigo;
    private Date fecha;

    public Juicio_Arraigo_List(Integer juicio, String arraigo, Date fecha) {
        this.juicio = juicio;
        this.arraigo = arraigo;
        this.fecha = fecha;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getArraigo() {
        return arraigo;
    }

    public void setArraigo(String arraigo) {
        this.arraigo = arraigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
