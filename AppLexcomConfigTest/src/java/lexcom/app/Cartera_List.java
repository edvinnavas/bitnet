package lexcom.app;

import java.io.Serializable;

public class Cartera_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer cartera;
    private String nombre;
    private String estado;
            
    public Cartera_List(Integer cartera, String nombre, String estado) {
        this.cartera = cartera;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getCartera() {
        return cartera;
    }

    public void setCartera(Integer cartera) {
        this.cartera = cartera;
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
