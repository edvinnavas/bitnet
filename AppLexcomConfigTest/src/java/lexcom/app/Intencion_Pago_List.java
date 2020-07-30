package lexcom.app;

import java.io.Serializable;

public class Intencion_Pago_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer intencion_pago;
    private String nombre;
    private String estado;

    public Intencion_Pago_List(Integer intencion_pago, String nombre, String estado) {
        this.intencion_pago = intencion_pago;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getIntencion_pago() {
        return intencion_pago;
    }

    public void setIntencion_pago(Integer intencion_pago) {
        this.intencion_pago = intencion_pago;
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
