package lexcom.app;

import java.io.Serializable;

public class Juicio_Otro_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String otro;
    private String medida;
    private String fecha;

    public Juicio_Otro_List(String otro, String medida, String fecha) {
        this.otro = otro;
        this.medida = medida;
        this.fecha = fecha;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
