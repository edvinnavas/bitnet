package lexcom.app;

import java.io.Serializable;

public class Tipo_Codigo_Resultado_Contacto_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private Integer tipo_codigo_resultado;
    private String tipo_codigo_resultado_nombre;
    private Integer codigo_resultado;
    private String codigo_resultado_nombre;
    private String contacto;

    public Tipo_Codigo_Resultado_Contacto_List(Integer indice, Integer tipo_codigo_resultado, String tipo_codigo_resultado_nombre, Integer codigo_resultado, String codigo_resultado_nombre, String contacto) {
        this.indice = indice;
        this.tipo_codigo_resultado = tipo_codigo_resultado;
        this.tipo_codigo_resultado_nombre = tipo_codigo_resultado_nombre;
        this.codigo_resultado = codigo_resultado;
        this.codigo_resultado_nombre = codigo_resultado_nombre;
        this.contacto = contacto;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Integer getTipo_codigo_resultado() {
        return tipo_codigo_resultado;
    }

    public void setTipo_codigo_resultado(Integer tipo_codigo_resultado) {
        this.tipo_codigo_resultado = tipo_codigo_resultado;
    }

    public String getTipo_codigo_resultado_nombre() {
        return tipo_codigo_resultado_nombre;
    }

    public void setTipo_codigo_resultado_nombre(String tipo_codigo_resultado_nombre) {
        this.tipo_codigo_resultado_nombre = tipo_codigo_resultado_nombre;
    }

    public Integer getCodigo_resultado() {
        return codigo_resultado;
    }

    public void setCodigo_resultado(Integer codigo_resultado) {
        this.codigo_resultado = codigo_resultado;
    }

    public String getCodigo_resultado_nombre() {
        return codigo_resultado_nombre;
    }

    public void setCodigo_resultado_nombre(String codigo_resultado_nombre) {
        this.codigo_resultado_nombre = codigo_resultado_nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
}
