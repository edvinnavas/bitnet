package lexcom.app;

import java.io.Serializable;

public class SestadoExtra_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer sestado;
    private String nombre;
    private String estado;
            
    public SestadoExtra_List(Integer sestado, String nombre, String estado) {
        this.sestado = sestado;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getSestado() {
        return sestado;
    }

    public void setSestado(Integer sestado) {
        this.sestado = sestado;
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
