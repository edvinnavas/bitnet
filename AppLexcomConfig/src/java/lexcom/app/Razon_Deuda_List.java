package lexcom.app;

import java.io.Serializable;

public class Razon_Deuda_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer razon_deuda;
    private String nombre;
    private String estado;

    public Razon_Deuda_List(Integer razon_deuda, String nombre, String estado) {
        this.razon_deuda = razon_deuda;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getRazon_deuda() {
        return razon_deuda;
    }

    public void setRazon_deuda(Integer razon_deuda) {
        this.razon_deuda = razon_deuda;
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
