package com.lexcom.entidad;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Demanda_Caratula implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer deudor;
    private Integer nuevo_estado_judicial;
    private Integer nuevo_estatus_judicial;
    private String situacion_caso;
    private String procuracion;
    private Integer juzgado;
    private String no_juicio;
    private Date fecha_juicio;
    private Integer notificador;
    private Integer procurador;
    private String arraigo;
    private Date fecha_arraigo;
    private String banco;
    private Date fecha_banco;

    public Deudores_Demanda_Caratula(Integer deudor, Integer nuevo_estado_judicial, Integer nuevo_estatus_judicial, String situacion_caso, String procuracion, Integer juzgado, String no_juicio, Date fecha_juicio, Integer notificador, Integer procurador, String arraigo, Date fecha_arraigo, String banco, Date fecha_banco) {
        this.deudor = deudor;
        this.nuevo_estado_judicial = nuevo_estado_judicial;
        this.nuevo_estatus_judicial = nuevo_estatus_judicial;
        this.situacion_caso = situacion_caso;
        this.procuracion = procuracion;
        this.juzgado = juzgado;
        this.no_juicio = no_juicio;
        this.fecha_juicio = fecha_juicio;
        this.notificador = notificador;
        this.procurador = procurador;
        this.arraigo = arraigo;
        this.fecha_arraigo = fecha_arraigo;
        this.banco = banco;
        this.fecha_banco = fecha_banco;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Integer getNuevo_estado_judicial() {
        return nuevo_estado_judicial;
    }

    public void setNuevo_estado_judicial(Integer nuevo_estado_judicial) {
        this.nuevo_estado_judicial = nuevo_estado_judicial;
    }

    public Integer getNuevo_estatus_judicial() {
        return nuevo_estatus_judicial;
    }

    public void setNuevo_estatus_judicial(Integer nuevo_estatus_judicial) {
        this.nuevo_estatus_judicial = nuevo_estatus_judicial;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Date getFecha_juicio() {
        return fecha_juicio;
    }

    public void setFecha_juicio(Date fecha_juicio) {
        this.fecha_juicio = fecha_juicio;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public String getArraigo() {
        return arraigo;
    }

    public void setArraigo(String arraigo) {
        this.arraigo = arraigo;
    }

    public Date getFecha_arraigo() {
        return fecha_arraigo;
    }

    public void setFecha_arraigo(Date fecha_arraigo) {
        this.fecha_arraigo = fecha_arraigo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Date getFecha_banco() {
        return fecha_banco;
    }

    public void setFecha_banco(Date fecha_banco) {
        this.fecha_banco = fecha_banco;
    }
    
}
