package lexcom.app;

import java.io.Serializable;

public class Antiguedad_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer antiguedad;
    private String nombre;
    private String estado;
            
    public Antiguedad_List(Integer antiguedad, String nombre, String estado) {
        this.antiguedad = antiguedad;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
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
