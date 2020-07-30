package lexcom.app;

import java.io.Serializable;

public class Actor_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer actor;
    private String nombre;
    private String nit;
    private String telefono;
    private String estado;
    private String cooperacion;
    private String digitalizados;

    public Actor_List(Integer actor, String nombre, String nit, String telefono, String estado, String cooperacion, String digitalizados) {
        this.actor = actor;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.estado = estado;
        this.cooperacion = cooperacion;
        this.digitalizados = digitalizados;
    }

    public Integer getActor() {
        return actor;
    }

    public void setActor(Integer actor) {
        this.actor = actor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCooperacion() {
        return cooperacion;
    }

    public void setCooperacion(String cooperacion) {
        this.cooperacion = cooperacion;
    }

    public String getDigitalizados() {
        return digitalizados;
    }

    public void setDigitalizados(String digitalizados) {
        this.digitalizados = digitalizados;
    }
    
}
