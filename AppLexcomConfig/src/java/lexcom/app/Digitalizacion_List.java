package lexcom.app;

import java.io.Serializable;

public class Digitalizacion_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer indice;
    private String actor;
    private String deudor;
    private String nombre;
    private String path;

    public Digitalizacion_List(Integer indice, String actor, String deudor, String nombre, String path) {
        this.indice = indice;
        this.actor = actor;
        this.deudor = deudor;
        this.nombre = nombre;
        this.path = path;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
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
