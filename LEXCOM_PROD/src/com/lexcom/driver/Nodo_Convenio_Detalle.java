package com.lexcom.driver;

import java.util.Calendar;

public class Nodo_Convenio_Detalle {

    private Integer promesa_pago;
    private Calendar fecha_pago;
    private String hora_pago;
    private String estado_promesa;
    private Double monto;
    private String observacion;
    private String fecha_creacion;
    private String fecha_anulacion;
    private String fecha_cumplimiento;

    public Nodo_Convenio_Detalle(Integer promesa_pago, Calendar fecha_pago, String hora_pago, String estado_promesa, Double monto, String observacion, String fecha_creacion, String fecha_anulacion, String fecha_cumplimiento) {
        this.promesa_pago = promesa_pago;
        this.fecha_pago = fecha_pago;
        this.hora_pago = hora_pago;
        this.estado_promesa = estado_promesa;
        this.monto = monto;
        this.observacion = observacion;
        this.fecha_creacion = fecha_creacion;
        this.fecha_anulacion = fecha_anulacion;
        this.fecha_cumplimiento = fecha_cumplimiento;
    }

    public String getEstado_promesa() {
        return estado_promesa;
    }

    public void setEstado_promesa(String estado_promesa) {
        this.estado_promesa = estado_promesa;
    }

    public String getFecha_anulacion() {
        return fecha_anulacion;
    }

    public void setFecha_anulacion(String fecha_anulacion) {
        this.fecha_anulacion = fecha_anulacion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_cumplimiento() {
        return fecha_cumplimiento;
    }

    public void setFecha_cumplimiento(String fecha_cumplimiento) {
        this.fecha_cumplimiento = fecha_cumplimiento;
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
