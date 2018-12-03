package lexcom.app;

import java.io.Serializable;

public class Garantia_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer garantia;
    private String nombre;
    private String estado;
            
    public Garantia_List(Integer garantia, String nombre, String estado) {
        this.garantia = garantia;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
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
