package lexcom.app;

import java.io.Serializable;

public class Banco_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer banco;
    private String nombre;
    private String estado;
            
    public Banco_List(Integer banco, String nombre, String estado) {
        this.banco = banco;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
