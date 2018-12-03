package lexcom.app;

import java.io.Serializable;

public class Juicio_Salario_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String salario;
    private String empresa;
    private String fecha;

    public Juicio_Salario_List(String salario, String empresa, String fecha) {
        this.salario = salario;
        this.empresa = empresa;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
