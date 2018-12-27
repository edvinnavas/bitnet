package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Finca_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String finca;
    private String letra;
    private Date fecha;
    private String tramite;

    public Juicio_Finca_List(Integer juicio, String finca, String letra, Date fecha, String tramite) {
        this.juicio = juicio;
        this.finca = finca;
        this.letra = letra;
        this.fecha = fecha;
        this.tramite = tramite;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }
    
}
