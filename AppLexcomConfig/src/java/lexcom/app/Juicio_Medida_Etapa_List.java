package lexcom.app;

import java.io.Serializable;

public class Juicio_Medida_Etapa_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer juicio_medida_etapa;
    private String nombre;
    private String estado;

    public Juicio_Medida_Etapa_List(Integer juicio_medida_etapa, String nombre, String estado) {
        this.juicio_medida_etapa = juicio_medida_etapa;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getJuicio_medida_etapa() {
        return juicio_medida_etapa;
    }

    public void setJuicio_medida_etapa(Integer juicio_medida_etapa) {
        this.juicio_medida_etapa = juicio_medida_etapa;
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
