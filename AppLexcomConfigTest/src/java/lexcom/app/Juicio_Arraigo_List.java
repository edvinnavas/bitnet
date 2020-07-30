package lexcom.app;

import java.io.Serializable;

public class Juicio_Arraigo_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String arraigo;
    private String fecha;

    public Juicio_Arraigo_List(String arraigo, String fecha) {
        this.arraigo = arraigo;
        this.fecha = fecha;
    }

    public String getArraigo() {
        return arraigo;
    }

    public void setArraigo(String arraigo) {
        this.arraigo = arraigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
