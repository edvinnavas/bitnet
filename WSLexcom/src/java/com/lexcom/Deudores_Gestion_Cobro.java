package com.lexcom;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Gestion_Cobro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer deudor;
    private Date fecha;
    private String hora;
    private Integer usuario;
    private Integer codigo_contactabilidad;
    private String descripcion;
    private String contacto;

    public Deudores_Gestion_Cobro(Integer deudor, Date fecha, String hora, Integer usuario, Integer codigo_contactabilidad, String descripcion, String contacto) {
        this.deudor = deudor;
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.codigo_contactabilidad = codigo_contactabilidad;
        this.descripcion = descripcion;
        this.contacto = contacto;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getCodigo_contactabilidad() {
        return codigo_contactabilidad;
    }

    public void setCodigo_contactabilidad(Integer codigo_contactabilidad) {
        this.codigo_contactabilidad = codigo_contactabilidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
}
