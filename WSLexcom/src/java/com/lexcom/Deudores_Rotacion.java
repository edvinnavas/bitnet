package com.lexcom;

import java.io.Serializable;

public class Deudores_Rotacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String deudor;
    private String caso;
    private String antiguedad;
    private String fecha_recepcion;
    private String no_cuenta;
    private String nombre_deudor;
    private String monto_inicial;
    private String garantia;
    private String estado;
    private String fecha_proximo_pago;
    private String cuota;
    private String gestor;
    private String tipo_gestor;
    private String codigo_recuperabilidad;
    private String actor;

    public Deudores_Rotacion(String deudor, String caso, String antiguedad, String fecha_recepcion, String no_cuenta, String nombre_deudor, String monto_inicial, String garantia, String estado, String fecha_proximo_pago, String cuota, String gestor, String tipo_gestor, String codigo_recuperabilidad, String actor) {
        this.deudor = deudor;
        this.caso = caso;
        this.antiguedad = antiguedad;
        this.fecha_recepcion = fecha_recepcion;
        this.no_cuenta = no_cuenta;
        this.nombre_deudor = nombre_deudor;
        this.monto_inicial = monto_inicial;
        this.garantia = garantia;
        this.estado = estado;
        this.fecha_proximo_pago = fecha_proximo_pago;
        this.cuota = cuota;
        this.gestor = gestor;
        this.tipo_gestor = tipo_gestor;
        this.codigo_recuperabilidad = codigo_recuperabilidad;
        this.actor = actor;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
    }

    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public String getNombre_deudor() {
        return nombre_deudor;
    }

    public void setNombre_deudor(String nombre_deudor) {
        this.nombre_deudor = nombre_deudor;
    }

    public String getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(String monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_proximo_pago() {
        return fecha_proximo_pago;
    }

    public void setFecha_proximo_pago(String fecha_proximo_pago) {
        this.fecha_proximo_pago = fecha_proximo_pago;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getTipo_gestor() {
        return tipo_gestor;
    }

    public void setTipo_gestor(String tipo_gestor) {
        this.tipo_gestor = tipo_gestor;
    }

    public String getCodigo_recuperabilidad() {
        return codigo_recuperabilidad;
    }

    public void setCodigo_recuperabilidad(String codigo_recuperabilidad) {
        this.codigo_recuperabilidad = codigo_recuperabilidad;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
    
}
