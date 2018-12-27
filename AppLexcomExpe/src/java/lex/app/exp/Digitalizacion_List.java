package lex.app.exp;

import java.io.Serializable;

public class Digitalizacion_List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String nombre;
    private String path;

    public Digitalizacion_List(Integer indice, String nombre, String path) {
        this.indice = indice;
        this.nombre = nombre;
        this.path = path;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
