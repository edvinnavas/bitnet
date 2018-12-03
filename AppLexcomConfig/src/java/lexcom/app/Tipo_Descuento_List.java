package lexcom.app;

import java.io.Serializable;

public class Tipo_Descuento_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer tipo_descuento;
    private String nombre;
    private String estado;
            
    public Tipo_Descuento_List(Integer tipo_descuento, String nombre, String estado) {
        this.tipo_descuento = tipo_descuento;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getTipo_descuento() {
        return tipo_descuento;
    }

    public void setTipo_descuento(Integer tipo_descuento) {
        this.tipo_descuento = tipo_descuento;
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
