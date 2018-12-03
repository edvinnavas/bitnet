package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Aumento_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private String tipo_aumento;
    private Date fecha;
    private Double monto;

    public Aumento_List(Integer indice, String actor, String deudor, String tipo_aumento, Date fecha, Double monto) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.tipo_aumento = tipo_aumento;
        this.fecha = fecha;
        this.monto = monto;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
    }

    public String getTipo_aumento() {
        return tipo_aumento;
    }

    public void setTipo_aumento(String tipo_aumento) {
        this.tipo_aumento = tipo_aumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
}
