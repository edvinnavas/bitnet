package com.lexcom_cargas;

import java.io.Serializable;

public class Juicio_Finca_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String tramite;
    private String finca;
    private String letra;
    private String fecha;

    public Juicio_Finca_List(String tramite, String finca, String letra, String fecha) {
        this.tramite = tramite;
        this.finca = finca;
        this.letra = letra;
        this.fecha = fecha;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
