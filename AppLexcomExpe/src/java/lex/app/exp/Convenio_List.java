package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Convenio_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private Date fecha_creacion;
    private String estado;
    private Date fecha_pago;
    private Double total_deuda;
    private Integer no_cuotas;
    private String frecuencia;
    private Double cuota;

    public Convenio_List(Integer indice, Date fecha_creacion, String estado, Date fecha_pago, Double total_deuda, Integer no_cuotas, String frecuencia, Double cuota) {
        this.indice = indice;
        this.fecha_creacion = fecha_creacion;
        this.estado = estado;
        this.fecha_pago = fecha_pago;
        this.total_deuda = total_deuda;
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

    public Double getTotal_deuda() {
        return total_deuda;
    }

    public void setTotal_deuda(Double total_deuda) {
        this.total_deuda = total_deuda;
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
