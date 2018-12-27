package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Vehiculo_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String vehiculo;
    private String medida;
    private Date fecha;

    public Juicio_Vehiculo_List(Integer juicio, String vehiculo, String medida, Date fecha) {
        this.juicio = juicio;
        this.vehiculo = vehiculo;
        this.medida = medida;
        this.fecha = fecha;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
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
