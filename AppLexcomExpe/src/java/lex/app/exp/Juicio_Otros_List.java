package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Otros_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String otros;
    private String medida;
    private Date fecha;

    public Juicio_Otros_List(Integer juicio, String otros, String medida, Date fecha) {
        this.juicio = juicio;
        this.otros = otros;
        this.medida = medida;
        this.fecha = fecha;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
