package lexcom.app;

import java.io.Serializable;

public class Codigo_Contactabilidad_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer codigo_contactabilidad;
    private String codigo;
    private String nombre;
    private String estado;
            
    public Codigo_Contactabilidad_List(Integer codigo_contactabilidad, String codigo, String nombre, String estado) {
        this.codigo_contactabilidad = codigo_contactabilidad;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getCodigo_contactabilidad() {
        return codigo_contactabilidad;
    }

    public void setCodigo_contactabilidad(Integer codigo_contactabilidad) {
        this.codigo_contactabilidad = codigo_contactabilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
