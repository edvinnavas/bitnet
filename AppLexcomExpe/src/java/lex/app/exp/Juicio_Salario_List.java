package lex.app.exp;

import java.io.Serializable;
import java.util.Date;

public class Juicio_Salario_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer juicio;
    private String salario;
    private String empresa;
    private Date fecha;

    public Juicio_Salario_List(Integer juicio, String salario, String empresa, Date fecha) {
        this.juicio = juicio;
        this.salario = salario;
        this.empresa = empresa;
        this.fecha = fecha;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
