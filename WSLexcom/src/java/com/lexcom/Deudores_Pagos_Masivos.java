package com.lexcom;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Pagos_Masivos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer deudor;
    private Date fecha_pago;
    private Double monto_pago;
    private Integer banco_pago;
    private String boleta_pago;

    public Deudores_Pagos_Masivos(Integer deudor, Date fecha_pago, Double monto_pago, Integer banco_pago, String boleta_pago) {
        this.deudor = deudor;
        this.fecha_pago = fecha_pago;
        this.monto_pago = monto_pago;
        this.banco_pago = banco_pago;
        this.boleta_pago = boleta_pago;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Double getMonto_pago() {
        return monto_pago;
    }

    public void setMonto_pago(Double monto_pago) {
        this.monto_pago = monto_pago;
    }

    public Integer getBanco_pago() {
        return banco_pago;
    }

    public void setBanco_pago(Integer banco_pago) {
        this.banco_pago = banco_pago;
    }

    public String getBoleta_pago() {
        return boleta_pago;
    }

    public void setBoleta_pago(String boleta_pago) {
        this.boleta_pago = boleta_pago;
    }

}
