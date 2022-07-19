package com.lexcom.driver;

public class Nodo {
    private String deudor;
    private String caso;
    private String antiguedad;
    private String fecha_recepcion;
    private String no_cuenta;
    private String nombre_deudor;
    private Double monto_inicial;
    private String garantia;
    private String estado;
    private String fecha_proximo_pago;
    private String cuota_proximo_pago;
    private String gestor;
    private String codigo_contactabilidad;
    private String actor;

    Nodo siguiente;
    Nodo anterior;

    public Nodo(String deudor, String caso, String antiguedad, String fecha_recepcion, String no_cuenta, String nombre_deudor, Double monto_inicial, String garantia, String estado, String fecha_proximo_pago, String cuota_proximo_pago, String gestor, String codigo_contactabilidad, String actor) {
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
        this.cuota_proximo_pago = cuota_proximo_pago;
        this.gestor = gestor;
        this.codigo_contactabilidad = codigo_contactabilidad;
        this.actor = actor;
        
        this.siguiente = null;
        this.anterior = null;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }

    public String getCodigo_contactabilidad() {
        return codigo_contactabilidad;
    }

    public void setCodigo_contactabilidad(String codigo_contactabilidad) {
        this.codigo_contactabilidad = codigo_contactabilidad;
    }

    public String getCuota_proximo_pago() {
        return cuota_proximo_pago;
    }

    public void setCuota_proximo_pago(String cuota_proximo_pago) {
        this.cuota_proximo_pago = cuota_proximo_pago;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
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

    public String getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
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

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
}
