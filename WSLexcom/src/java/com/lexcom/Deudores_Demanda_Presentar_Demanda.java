package com.lexcom;

import java.io.Serializable;

public class Deudores_Demanda_Presentar_Demanda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer deudor;
    private Integer nuevo_estado_judicial;
    private Integer nuevo_estatus_judicial;
    private String situacion_caso;
    private String procuracion;

    public Deudores_Demanda_Presentar_Demanda(Integer deudor, Integer nuevo_estado_judicial, Integer nuevo_estatus_judicial, String situacion_caso, String procuracion) {
        this.deudor = deudor;
        this.nuevo_estado_judicial = nuevo_estado_judicial;
        this.nuevo_estatus_judicial = nuevo_estatus_judicial;
        this.situacion_caso = situacion_caso;
        this.procuracion = procuracion;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Integer getNuevo_estado_judicial() {
        return nuevo_estado_judicial;
    }

    public void setNuevo_estado_judicial(Integer nuevo_estado_judicial) {
        this.nuevo_estado_judicial = nuevo_estado_judicial;
    }

    public Integer getNuevo_estatus_judicial() {
        return nuevo_estatus_judicial;
    }

    public void setNuevo_estatus_judicial(Integer nuevo_estatus_judicial) {
        this.nuevo_estatus_judicial = nuevo_estatus_judicial;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }
    
}
