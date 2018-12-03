package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Juicio_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private String jugzado;
    private Date fecha;
    private String no_juicio;
    private Double monto;

    public Juicio_List(Integer indice, String actor, String deudor, String jugzado, Date fecha, String no_juicio, Double monto) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.jugzado = jugzado;
        this.fecha = fecha;
        this.no_juicio = no_juicio;
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

    public String getJugzado() {
        return jugzado;
    }

    public void setJugzado(String jugzado) {
        this.jugzado = jugzado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
}
