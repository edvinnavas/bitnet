package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String juzgado;
    private Date fecha;
    private String no_juicio;
    private Double monto;

    public Juicio_List(Integer indice, String juzgado, Date fecha, String no_juicio, Double monto) {
        this.indice = indice;
        this.juzgado = juzgado;
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

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
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
