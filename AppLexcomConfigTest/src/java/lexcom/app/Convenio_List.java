package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Convenio_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private Date fecha_creacion;
    private String estado;
    private Date fecha_pago;
    private Double total_deudo;
    private Integer no_cuotas;
    private String frecuencia;
    private Double cuota;

    public Convenio_List(Integer indice, String actor, String deudor, Date fecha_creacion, String estado, Date fecha_pago, Double total_deudo, Integer no_cuotas, String frecuencia, Double cuota) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.fecha_creacion = fecha_creacion;
        this.estado = estado;
        this.fecha_pago = fecha_pago;
        this.total_deudo = total_deudo;
        this.no_cuotas = no_cuotas;
        this.frecuencia = frecuencia;
        this.cuota = cuota;
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

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Double getTotal_deudo() {
        return total_deudo;
    }

    public void setTotal_deudo(Double total_deudo) {
        this.total_deudo = total_deudo;
    }

    public Integer getNo_cuotas() {
        return no_cuotas;
    }

    public void setNo_cuotas(Integer no_cuotas) {
        this.no_cuotas = no_cuotas;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }
    
}
