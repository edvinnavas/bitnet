package com.lexcom_cargas;

import java.io.Serializable;

public class Juicio_Vehiculo_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String vehiculo;
    private String medida;
    private String fecha;

    public Juicio_Vehiculo_List(String vehiculo, String medida, String fecha) {
        this.vehiculo = vehiculo;
        this.medida = medida;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
