package com.lexcom.driver;

import java.util.Calendar;

public class Nodo_Convenio_Detalle {
    private Integer promesa_pago;
    private Calendar fecha_pago;
    private String hora_pago;
    private String estado_promesa;
    private Double monto;
    private String observacion;

    public Nodo_Convenio_Detalle(Integer promesa_pago, Calendar fecha_pago, String hora_pago, String estado_promesa, Double monto, String observacion) {
        this.promesa_pago = promesa_pago;
        this.fecha_pago = fecha_pago;
        this.hora_pago = hora_pago;
        this.estado_promesa = estado_promesa;
        this.monto = monto;
        this.observacion = observacion;
    }

    public String getEstado_promesa() {
        return estado_promesa;
    }

    public void setEstado_promesa(String estado_promesa) {
        this.estado_promesa = estado_promesa;
    }

    public Calendar getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Calendar fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getHora_pago() {
        return hora_pago;
    }

    public void setHora_pago(String hora_pago) {
        this.hora_pago = hora_pago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getPromesa_pago() {
        return promesa_pago;
    }

    public void setPromesa_pago(Integer promesa_pago) {
        this.promesa_pago = promesa_pago;
    }
    
}
