package com.lexcom.entidad;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Carga_Masiva implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer actor;
    private Integer caso;
    private Integer estado_judicial;
    private Integer estatus_judicial;
    private Integer estado_extrajudicial;
    private Integer estatus_extrajudicial;
    private String intension_pago;
    private Integer garantia;
    private Integer gestor;
    private Double saldo;
    private Double monto_inicial;
    private String nombre_deudor;
    private String no_cuenta;
    private String antiguedad;
    private String situacion_caso;
    private Date fecha_recepcion;
    private String cargado;
    private String moneda;
    private String anticipo;
    private String cosecha;
    private String no_juicio;
    private Integer juzgado;
    private Integer procurador;
    private Integer notificador;
    private Double monto_demanda;
    private String otro_no_cuenta;
    private Date fecha_nacimiento;
    private String telefono_casa;
    private String telefono_celular;
    private String telefono_trabajo;
    private String correo_electronico;
    private String lugar_trabajo;
    private String contacto_trabajo;
    private String direccion;
    private String dpi;
    private String nit;
    private String notas;
    private String procuracion;
    private Integer cuenta_principal_relacion;
    private Integer deudor_cuenta_relacionada;

    public Deudores_Carga_Masiva(Integer actor, Integer caso, Integer estado_judicial, Integer estatus_judicial, Integer estado_extrajudicial, Integer estatus_extrajudicial, String intension_pago, Integer garantia, Integer gestor, Double saldo, Double monto_inicial, String nombre_deudor, String no_cuenta, String antiguedad, String situacion_caso, Date fecha_recepcion, String cargado, String moneda, String anticipo, String cosecha, String no_juicio, Integer juzgado, Integer procurador, Integer notificador, Double monto_demanda, String otro_no_cuenta, Date fecha_nacimiento, String telefono_casa, String telefono_celular, String telefono_trabajo, String correo_electronico, String lugar_trabajo, String contacto_trabajo, String direccion, String dpi, String nit, String notas, String procuracion, Integer cuenta_principal_relacion, Integer deudor_cuenta_relacionada) {
        this.actor = actor;
        this.caso = caso;
        this.estado_judicial = estado_judicial;
        this.estatus_judicial = estatus_judicial;
        this.estado_extrajudicial = estado_extrajudicial;
        this.estatus_extrajudicial = estatus_extrajudicial;
        this.intension_pago = intension_pago;
        this.garantia = garantia;
        this.gestor = gestor;
        this.saldo = saldo;
        this.monto_inicial = monto_inicial;
        this.nombre_deudor = nombre_deudor;
        this.no_cuenta = no_cuenta;
        this.antiguedad = antiguedad;
        this.situacion_caso = situacion_caso;
        this.fecha_recepcion = fecha_recepcion;
        this.cargado = cargado;
        this.moneda = moneda;
        this.anticipo = anticipo;
        this.cosecha = cosecha;
        this.no_juicio = no_juicio;
        this.juzgado = juzgado;
        this.procurador = procurador;
        this.notificador = notificador;
        this.monto_demanda = monto_demanda;
        this.otro_no_cuenta = otro_no_cuenta;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono_casa = telefono_casa;
        this.telefono_celular = telefono_celular;
        this.telefono_trabajo = telefono_trabajo;
        this.correo_electronico = correo_electronico;
        this.lugar_trabajo = lugar_trabajo;
        this.contacto_trabajo = contacto_trabajo;
        this.direccion = direccion;
        this.dpi = dpi;
        this.nit = nit;
        this.notas = notas;
        this.procuracion = procuracion;
        this.cuenta_principal_relacion = cuenta_principal_relacion;
        this.deudor_cuenta_relacionada = deudor_cuenta_relacionada;
    }

    public Integer getActor() {
        return actor;
    }

    public void setActor(Integer actor) {
        this.actor = actor;
    }

    public Integer getCaso() {
        return caso;
    }

    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    public Integer getEstado_judicial() {
        return estado_judicial;
    }

    public void setEstado_judicial(Integer estado_judicial) {
        this.estado_judicial = estado_judicial;
    }

    public Integer getEstatus_judicial() {
        return estatus_judicial;
    }

    public void setEstatus_judicial(Integer estatus_judicial) {
        this.estatus_judicial = estatus_judicial;
    }

    public Integer getEstado_extrajudicial() {
        return estado_extrajudicial;
    }

    public void setEstado_extrajudicial(Integer estado_extrajudicial) {
        this.estado_extrajudicial = estado_extrajudicial;
    }

    public Integer getEstatus_extrajudicial() {
        return estatus_extrajudicial;
    }

    public void setEstatus_extrajudicial(Integer estatus_extrajudicial) {
        this.estatus_extrajudicial = estatus_extrajudicial;
    }

    public String getIntension_pago() {
        return intension_pago;
    }

    public void setIntension_pago(String intension_pago) {
        this.intension_pago = intension_pago;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public Integer getGestor() {
        return gestor;
    }

    public void setGestor(Integer gestor) {
        this.gestor = gestor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getNombre_deudor() {
        return nombre_deudor;
    }

    public void setNombre_deudor(String nombre_deudor) {
        this.nombre_deudor = nombre_deudor;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    public String getCosecha() {
        return cosecha;
    }

    public void setCosecha(String cosecha) {
        this.cosecha = cosecha;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public Double getMonto_demanda() {
        return monto_demanda;
    }

    public void setMonto_demanda(Double monto_demanda) {
        this.monto_demanda = monto_demanda;
    }

    public String getOtro_no_cuenta() {
        return otro_no_cuenta;
    }

    public void setOtro_no_cuenta(String otro_no_cuenta) {
        this.otro_no_cuenta = otro_no_cuenta;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono_casa() {
        return telefono_casa;
    }

    public void setTelefono_casa(String telefono_casa) {
        this.telefono_casa = telefono_casa;
    }

    public String getTelefono_celular() {
        return telefono_celular;
    }

    public void setTelefono_celular(String telefono_celular) {
        this.telefono_celular = telefono_celular;
    }

    public String getTelefono_trabajo() {
        return telefono_trabajo;
    }

    public void setTelefono_trabajo(String telefono_trabajo) {
        this.telefono_trabajo = telefono_trabajo;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getLugar_trabajo() {
        return lugar_trabajo;
    }

    public void setLugar_trabajo(String lugar_trabajo) {
        this.lugar_trabajo = lugar_trabajo;
    }

    public String getContacto_trabajo() {
        return contacto_trabajo;
    }

    public void setContacto_trabajo(String contacto_trabajo) {
        this.contacto_trabajo = contacto_trabajo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public Integer getCuenta_principal_relacion() {
        return cuenta_principal_relacion;
    }

    public void setCuenta_principal_relacion(Integer cuenta_principal_relacion) {
        this.cuenta_principal_relacion = cuenta_principal_relacion;
    }

    public Integer getDeudor_cuenta_relacionada() {
        return deudor_cuenta_relacionada;
    }

    public void setDeudor_cuenta_relacionada(Integer deudor_cuenta_relacionada) {
        this.deudor_cuenta_relacionada = deudor_cuenta_relacionada;
    }
    
}
