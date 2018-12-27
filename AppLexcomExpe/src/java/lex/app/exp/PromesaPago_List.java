package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class PromesaPago_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer correlativo;
    private Integer indice;
    private Integer no_convenio;
    private Date fecha;
    private String hora;
    private String estado;
    private Double monto;

    public PromesaPago_List(Integer correlativo, Integer indice, Integer no_convenio, Date fecha, String hora, String estado, Double monto) {
        this.correlativo = correlativo;
        this.indice = indice;
        this.no_convenio = no_convenio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.monto = monto;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Integer getNo_convenio() {
        return no_convenio;
    }

    public void setNo_convenio(Integer no_convenio) {
        this.no_convenio = no_convenio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
}
