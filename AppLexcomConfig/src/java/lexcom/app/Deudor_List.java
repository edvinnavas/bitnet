package lexcom.app;

import java.io.Serializable;

public class Deudor_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer deudor;
    private String actor;
    private String caso;
    private String nombre;
    private String no_cuenta;
    private String no_cuenta_otro;
    private String cargado;

    public Deudor_List(Integer deudor, String actor, String caso, String nombre, String no_cuenta, String no_cuenta_otro, String cargado) {
        this.deudor = deudor;
        this.actor = actor;
        this.caso = caso;
        this.nombre = nombre;
        this.no_cuenta = no_cuenta;
        this.no_cuenta_otro = no_cuenta_otro;
        this.cargado = cargado;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
    
    public String getCaso() {
        return caso;
    }

    public void setCaso(String caso) {
        this.caso = caso;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public String getNo_cuenta_otro() {
        return no_cuenta_otro;
    }

    public void setNo_cuenta_otro(String no_cuenta_otro) {
        this.no_cuenta_otro = no_cuenta_otro;
    }
    
    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

}
