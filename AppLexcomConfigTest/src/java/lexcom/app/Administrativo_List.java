package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Administrativo_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private Date fecha;
    private String hora;
    private String usuario;
    private String codigo;
    private String observacion;

    public Administrativo_List(Integer indice, String actor, String deudor, Date fecha, String hora, String usuario, String codigo, String observacion) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.codigo = codigo;
        this.observacion = observacion;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
