package com.lexcom.entidad;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Demanda_Deligenciar_Medidas implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer deudor;
    private Integer nuevo_estado_judicial;
    private Integer nuevo_estatus_judicial;
    private String situacion_caso;
    private String procuracion;
    private Integer juzgado;
    private String no_juicio;
    private Integer notificador;
    private Integer procurador;
    private String razon_notificacion;
    private String arraigo;
    private Date fecha_arraigo;
    private String banco;
    private Date fecha_banco;
    private String finca;
    private String letra_finca;
    private Date fecha_finca;
    private String tramite_finca;
    private String salario;
    private String empresa_salario;
    private Date fecha_salario;
    private String vehiculo;
    private String medida_vehiculo;
    private Date fecha_vehiculo;
    private String otros;
    private String medida_otros;
    private Date fecha_otros;

    public Deudores_Demanda_Deligenciar_Medidas(Integer deudor, Integer nuevo_estado_judicial, Integer nuevo_estatus_judicial, String situacion_caso, String procuracion, Integer juzgado, String no_juicio, Integer notificador, Integer procurador, String razon_notificacion, String arraigo, Date fecha_arraigo, String banco, Date fecha_banco, String finca, String letra_finca, Date fecha_finca, String tramite_finca, String salario, String empresa_salario, Date fecha_salario, String vehiculo, String medida_vehiculo, Date fecha_vehiculo, String otros, String medida_otros, Date fecha_otros) {
        this.deudor = deudor;
        this.nuevo_estado_judicial = nuevo_estado_judicial;
        this.nuevo_estatus_judicial = nuevo_estatus_judicial;
        this.situacion_caso = situacion_caso;
        this.procuracion = procuracion;
        this.juzgado = juzgado;
        this.no_juicio = no_juicio;
        this.notificador = notificador;
        this.procurador = procurador;
        this.razon_notificacion = razon_notificacion;
        this.arraigo = arraigo;
        this.fecha_arraigo = fecha_arraigo;
        this.banco = banco;
        this.fecha_banco = fecha_banco;
        this.finca = finca;
        this.letra_finca = letra_finca;
        this.fecha_finca = fecha_finca;
        this.tramite_finca = tramite_finca;
        this.salario = salario;
        this.empresa_salario = empresa_salario;
        this.fecha_salario = fecha_salario;
        this.vehiculo = vehiculo;
        this.medida_vehiculo = medida_vehiculo;
        this.fecha_vehiculo = fecha_vehiculo;
        this.otros = otros;
        this.medida_otros = medida_otros;
        this.fecha_otros = fecha_otros;
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

    public String getRazon_notificacion() {
        return razon_notificacion;
    }

    public void setRazon_notificacion(String razon_notificacion) {
        this.razon_notificacion = razon_notificacion;
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

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getLetra_finca() {
        return letra_finca;
    }

    public void setLetra_finca(String letra_finca) {
        this.letra_finca = letra_finca;
    }

    public Date getFecha_finca() {
        return fecha_finca;
    }

    public void setFecha_finca(Date fecha_finca) {
        this.fecha_finca = fecha_finca;
    }

    public String getTramite_finca() {
        return tramite_finca;
    }

    public void setTramite_finca(String tramite_finca) {
        this.tramite_finca = tramite_finca;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getEmpresa_salario() {
        return empresa_salario;
    }

    public void setEmpresa_salario(String empresa_salario) {
        this.empresa_salario = empresa_salario;
    }

    public Date getFecha_salario() {
        return fecha_salario;
    }

    public void setFecha_salario(Date fecha_salario) {
        this.fecha_salario = fecha_salario;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getMedida_vehiculo() {
        return medida_vehiculo;
    }

    public void setMedida_vehiculo(String medida_vehiculo) {
        this.medida_vehiculo = medida_vehiculo;
    }

    public Date getFecha_vehiculo() {
        return fecha_vehiculo;
    }

    public void setFecha_vehiculo(Date fecha_vehiculo) {
        this.fecha_vehiculo = fecha_vehiculo;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getMedida_otros() {
        return medida_otros;
    }

    public void setMedida_otros(String medida_otros) {
        this.medida_otros = medida_otros;
    }

    public Date getFecha_otros() {
        return fecha_otros;
    }

    public void setFecha_otros(Date fecha_otros) {
        this.fecha_otros = fecha_otros;
    }
    
}
