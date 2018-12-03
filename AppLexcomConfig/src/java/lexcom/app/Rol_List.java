package lexcom.app;

import java.io.Serializable;

public class Rol_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer rol;
    private String nombre;
    private String descripcion;
            
    public Rol_List(Integer rol, String nombre, String descripcion) {
        this.rol = rol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
