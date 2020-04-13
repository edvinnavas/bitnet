package com.lexcom.rotacion;

import java.io.Serializable;

public class Nodo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String deudor;
    private String caso;
    private String antiguedad;
    private String fecha_recepcion;
    private String no_cuenta;
    private String nombre_deudor;
    private Double monto_inicial;
    private String garantia;
    private String codigo_resultado;
    private String fecha_proximo_pago;
    private String cuota_proximo_pago;
    private String gestor;
    private Integer tipo_gestor;
    private String estado_judicial;
    private String status_judicial;
    private String estado_extrajudicial;
    private String status_extrajudicial;
    private String actor;

    Nodo siguiente;
    Nodo anterior;

    public Nodo(String deudor, String caso, String antiguedad, String fecha_recepcion, String no_cuenta, String nombre_deudor, Double monto_inicial, String garantia, String codigo_resultado, String fecha_proximo_pago, String cuota_proximo_pago, String gestor, Integer tipo_gestor, String estado_judicial, String status_judicial, String estado_extrajudicial, String status_extrajudicial, String actor) {
        this.deudor = deudor;
        this.caso = caso;
        this.antiguedad = antiguedad;
        this.fecha_recepcion = fecha_recepcion;
        this.no_cuenta = no_cuenta;
        this.nombre_deudor = nombre_deudor;
        this.monto_inicial = monto_inicial;
        this.garantia = garantia;
        this.codigo_resultado = codigo_resultado;
        this.fecha_proximo_pago = fecha_proximo_pago;
        this.cuota_proximo_pago = cuota_proximo_pago;
        this.gestor = gestor;
        this.tipo_gestor = tipo_gestor;
        this.estado_judicial = estado_judicial;
        this.status_judicial = status_judicial;
        this.estado_extrajudicial = estado_extrajudicial;
        this.status_extrajudicial = status_extrajudicial;
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

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getCodigo_resultado() {
        return codigo_resultado;
    }

    public void setCodigo_resultado(String codigo_resultado) {
        this.codigo_resultado = codigo_resultado;
    }

    public String getFecha_proximo_pago() {
        return fecha_proximo_pago;
    }

    public void setFecha_proximo_pago(String fecha_proximo_pago) {
        this.fecha_proximo_pago = fecha_proximo_pago;
    }

    public String getCuota_proximo_pago() {
        return cuota_proximo_pago;
    }

    public void setCuota_proximo_pago(String cuota_proximo_pago) {
        this.cuota_proximo_pago = cuota_proximo_pago;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public Integer getTipo_gestor() {
        return tipo_gestor;
    }

    public void setTipo_gestor(Integer tipo_gestor) {
        this.tipo_gestor = tipo_gestor;
    }

    public String getEstado_judicial() {
        return estado_judicial;
    }

    public void setEstado_judicial(String estado_judicial) {
        this.estado_judicial = estado_judicial;
    }

    public String getStatus_judicial() {
        return status_judicial;
    }

    public void setStatus_judicial(String status_judicial) {
        this.status_judicial = status_judicial;
    }

    public String getEstado_extrajudicial() {
        return estado_extrajudicial;
    }

    public void setEstado_extrajudicial(String estado_extrajudicial) {
        this.estado_extrajudicial = estado_extrajudicial;
    }

    public String getStatus_extrajudicial() {
        return status_extrajudicial;
    }

    public void setStatus_extrajudicial(String status_extrajudicial) {
        this.status_extrajudicial = status_extrajudicial;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
    
}
