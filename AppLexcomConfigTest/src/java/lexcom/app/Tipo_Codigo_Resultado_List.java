package lexcom.app;

import java.io.Serializable;

public class Tipo_Codigo_Resultado_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer tipo_codigo_resultado;
    private String nombre;
    private String estado;
            
    public Tipo_Codigo_Resultado_List(Integer tipo_codigo_resultado, String nombre, String estado) {
        this.tipo_codigo_resultado = tipo_codigo_resultado;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getTipo_codigo_resultado() {
        return tipo_codigo_resultado;
    }

    public void setTipo_codigo_resultado(Integer tipo_codigo_resultado) {
        this.tipo_codigo_resultado = tipo_codigo_resultado;
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
