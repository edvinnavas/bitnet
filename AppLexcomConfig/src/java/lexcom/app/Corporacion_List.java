package lexcom.app;

import java.io.Serializable;

public class Corporacion_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer cooperacion;
    private String nombre;
    private String estado;
            
    public Corporacion_List(Integer cooperacion, String nombre, String estado) {
        this.cooperacion = cooperacion;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getCooperacion() {
        return cooperacion;
    }

    public void setCooperacion(Integer cooperacion) {
        this.cooperacion = cooperacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
