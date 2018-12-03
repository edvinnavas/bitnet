package lexcom.app;

import java.io.Serializable;

public class Fiador_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private String dpi;
    private String nit;
    private String nombre;
    private String telefono;
    private String direccion;
    private String zona;
    private String correo_electronico;

    public Fiador_List(Integer indice, String actor, String deudor, String dpi, String nit, String nombre, String telefono, String direccion, String zona, String correo_electronico) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.dpi = dpi;
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.zona = zona;
        this.correo_electronico = correo_electronico;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }
    
}
