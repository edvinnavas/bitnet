package lexcom.app;

import java.io.Serializable;

public class estado_status_extrajudicial_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer index;
    private Integer sestado_extra;
    private String sestado_nombre;
    private Integer status_extra;
    private String estatus_nombre;
    private Integer permite;
    private String permite_nombre;

    public estado_status_extrajudicial_List(Integer index, Integer sestado_extra, String sestado_nombre, Integer status_extra, String estatus_nombre, Integer permite, String permite_nombre) {
        this.index = index;
        this.sestado_extra = sestado_extra;
        this.sestado_nombre = sestado_nombre;
        this.status_extra = status_extra;
        this.estatus_nombre = estatus_nombre;
        this.permite = permite;
        this.permite_nombre = permite_nombre;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSestado_extra() {
        return sestado_extra;
    }

    public void setSestado_extra(Integer sestado_extra) {
        this.sestado_extra = sestado_extra;
    }

    public String getSestado_nombre() {
        return sestado_nombre;
    }

    public void setSestado_nombre(String sestado_nombre) {
        this.sestado_nombre = sestado_nombre;
    }

    public Integer getStatus_extra() {
        return status_extra;
    }

    public void setStatus_extra(Integer status_extra) {
        this.status_extra = status_extra;
    }

    public String getEstatus_nombre() {
        return estatus_nombre;
    }

    public void setEstatus_nombre(String estatus_nombre) {
        this.estatus_nombre = estatus_nombre;
    }

    public Integer getPermite() {
        return permite;
    }

    public void setPermite(Integer permite) {
        this.permite = permite;
    }

    public String getPermite_nombre() {
        return permite_nombre;
    }

    public void setPermite_nombre(String permite_nombre) {
        this.permite_nombre = permite_nombre;
    }
    
}
