package lex.app.exp;

import java.io.Serializable;

public class Expediente_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id_deudor;
    private String cargado;
    private String actor;
    private String garantia;
    private String gestor;
    private String caso;
    private String cuenta;
    private String nombre;
    private Double monto_inicial;
    private String no_juicio;
    private String estado_extrajudicial;
    private String status_extrajudicial;
    private String estado_judicial;
    private String status_judicial;
    private String otro_no_cuenta;

    public Expediente_List(Integer id_deudor, String cargado, String actor, String garantia, String gestor, String caso, String cuenta, String nombre, Double monto_inicial, String no_juicio, String estado_extrajudicial, String status_extrajudicial, String estado_judicial, String status_judicial, String otro_no_cuenta) {
        this.id_deudor = id_deudor;
        this.cargado = cargado;
        this.actor = actor;
        this.garantia = garantia;
        this.gestor = gestor;
        this.caso = caso;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.monto_inicial = monto_inicial;
        this.no_juicio = no_juicio;
        this.estado_extrajudicial = estado_extrajudicial;
        this.status_extrajudicial = status_extrajudicial;
        this.estado_judicial = estado_judicial;
        this.status_judicial = status_judicial;
        this.otro_no_cuenta = otro_no_cuenta;
    }

    public Integer getId_deudor() {
        return id_deudor;
    }

    public void setId_deudor(Integer id_deudor) {
        this.id_deudor = id_deudor;
    }

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
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

    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
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

    public String getOtro_no_cuenta() {
        return otro_no_cuenta;
    }

    public void setOtro_no_cuenta(String otro_no_cuenta) {
        this.otro_no_cuenta = otro_no_cuenta;
    }
    
}
