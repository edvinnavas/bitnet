package lexcom.app;

import java.io.Serializable;
import java.util.Date;

public class Pago_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private String banco;
    private Date fecha;
    private Double monto;
    private String no_boleta;
    private Date fecha_registro;

    public Pago_List(Integer indice, String actor, String deudor, String banco, Date fecha, Double monto, String no_boleta, Date fecha_registro) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.banco = banco;
        this.fecha = fecha;
        this.monto = monto;
        this.no_boleta = no_boleta;
        this.fecha_registro = fecha_registro;
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
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

    public String getNo_boleta() {
        return no_boleta;
    }

    public void setNo_boleta(String no_boleta) {
        this.no_boleta = no_boleta;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
    
}
