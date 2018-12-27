package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Cobro_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private Date fecha;
    private String hora;
    private String usuario;
    private String codigo;
    private String contacto;
    private String observacion;

    public Cobro_List(Integer indice, Date fecha, String hora, String usuario, String codigo, String contacto, String observacion) {
        this.indice = indice;
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.codigo = codigo;
        this.contacto = contacto;
        this.observacion = observacion;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
