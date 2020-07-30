package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Recordatorio_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private Date fecha_ingreso;
    private Date alerta;
    private String estado_promesa;
    private Double monto;

    public Recordatorio_List(Integer indice, String actor, String deudor, Date fecha_ingreso, Date alerta, String estado_promesa, Double monto) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.fecha_ingreso = fecha_ingreso;
        this.alerta = alerta;
        this.estado_promesa = estado_promesa;
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

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getAlerta() {
        return alerta;
    }

    public void setAlerta(Date alerta) {
        this.alerta = alerta;
    }

    public String getEstado_promesa() {
        return estado_promesa;
    }

    public void setEstado_promesa(String estado_promesa) {
        this.estado_promesa = estado_promesa;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
}
