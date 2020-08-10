package lexcom.app;

import java.io.Serializable;

public class Juicio_Medida_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer juicio_medida;
    private String nombre;
    private String reiteracion;
    private String estado;

    public Juicio_Medida_List(Integer juicio_medida, String nombre, String reiteracion, String estado) {
        this.juicio_medida = juicio_medida;
        this.nombre = nombre;
        this.reiteracion = reiteracion;
        this.estado = estado;
    }

    public Integer getJuicio_medida() {
        return juicio_medida;
    }

    public void setJuicio_medida(Integer juicio_medida) {
        this.juicio_medida = juicio_medida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReiteracion() {
        return reiteracion;
    }

    public void setReiteracion(String reiteracion) {
        this.reiteracion = reiteracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
