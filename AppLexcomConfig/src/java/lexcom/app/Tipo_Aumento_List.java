package lexcom.app;

import java.io.Serializable;

public class Tipo_Aumento_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer tipo_aumento;
    private String nombre;
    private String estado;
            
    public Tipo_Aumento_List(Integer tipo_aumento, String nombre, String estado) {
        this.tipo_aumento = tipo_aumento;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getTipo_aumento() {
        return tipo_aumento;
    }

    public void setTipo_aumento(Integer tipo_aumento) {
        this.tipo_aumento = tipo_aumento;
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
