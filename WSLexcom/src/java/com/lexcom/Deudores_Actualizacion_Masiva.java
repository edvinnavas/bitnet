package com.lexcom;

import java.io.Serializable;
import java.util.Date;

public class Deudores_Actualizacion_Masiva implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer deudor;
    private Integer actor;
    private String moneda;
    private String dpi;
    private String nit;
    private Date fecha_nacimiento;
    private String nombre_deudor;
    private String nacionalidad;
    private String telefono_casa;
    private String telefono_celular;
    private String direccion;
    private Integer zona;
    private String pais;
    private String departamento;
    private String sexo;
    private String estado_civil;
    private Date fecha_ingreso;
    private String profesion;
    private String correo_electronico;
    private String lugar_trabajo;
    private String contacto_trabajo;
    private String telefono_trabajo;
    private Double monto_inicial;
    private Integer gestor;
    private Integer estado_judicial;
    private Integer estatus_judicial;
    private Integer estado_extrajudicial;
    private Integer estatus_extrajudicial;
    private Integer intencion_pago;
    private String no_cuenta;
    private Integer garantia;
    private String cargado;
    private String estado_ae;
    private String notas;
    private Integer codigo_resultado;
    private Integer caso;
    private String pdf;
    private String inv;
    private String maycom;
    private String nits;
    private String cosecha;
    private String otro_no_cuenta;
    private String descripcion_adicional;
    private Date fecha_recepcion;
    private String anticipo;
    private String antiguedad;
    private Date fecha_proximo_pago;
    private Double monto_proximo_pago;
    private Double saldo;
    private String convenio_pactado;
    private Double cuota_convenio;
    private String costas_pagadas;
    private String situacion_caso;
    private String opcion_proximo_pago;
    private String procuracion;
    private Date fecha_demanda;
    private Integer procurador;
    private Integer juzgado;
    private String no_juicio;
    private Integer notificador;
    private Double monto_demanda;
    private Integer cuenta_principal_relacion;
    private Integer deudor_cuenta_relacionada;

    public Deudores_Actualizacion_Masiva(Integer deudor, Integer actor, String moneda, String dpi, String nit, Date fecha_nacimiento, String nombre_deudor, String nacionalidad, String telefono_casa, String telefono_celular, String direccion, Integer zona, String pais, String departamento, String sexo, String estado_civil, Date fecha_ingreso, String profesion, String correo_electronico, String lugar_trabajo, String contacto_trabajo, String telefono_trabajo, Double monto_inicial, Integer gestor, Integer estado_judicial, Integer estatus_judicial, Integer estado_extrajudicial, Integer estatus_extrajudicial, Integer intencion_pago, String no_cuenta, Integer garantia, String cargado, String estado_ae, String notas, Integer codigo_resultado, Integer caso, String pdf, String inv, String maycom, String nits, String cosecha, String otro_no_cuenta, String descripcion_adicional, Date fecha_recepcion, String anticipo, String antiguedad, Date fecha_proximo_pago, Double monto_proximo_pago, Double saldo, String convenio_pactado, Double cuota_convenio, String costas_pagadas, String situacion_caso, String opcion_proximo_pago, String procuracion, Date fecha_demanda, Integer procurador, Integer juzgado, String no_juicio, Integer notificador, Double monto_demanda, Integer cuenta_principal_relacion, Integer deudor_cuenta_relacionada) {
        this.deudor = deudor;
        this.actor = actor;
        this.moneda = moneda;
        this.dpi = dpi;
        this.nit = nit;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nombre_deudor = nombre_deudor;
        this.nacionalidad = nacionalidad;
        this.telefono_casa = telefono_casa;
        this.telefono_celular = telefono_celular;
        this.direccion = direccion;
        this.zona = zona;
        this.pais = pais;
        this.departamento = departamento;
        this.sexo = sexo;
        this.estado_civil = estado_civil;
        this.fecha_ingreso = fecha_ingreso;
        this.profesion = profesion;
        this.correo_electronico = correo_electronico;
        this.lugar_trabajo = lugar_trabajo;
        this.contacto_trabajo = contacto_trabajo;
        this.telefono_trabajo = telefono_trabajo;
        this.monto_inicial = monto_inicial;
        this.gestor = gestor;
        this.estado_judicial = estado_judicial;
        this.estatus_judicial = estatus_judicial;
        this.estado_extrajudicial = estado_extrajudicial;
        this.estatus_extrajudicial = estatus_extrajudicial;
        this.intencion_pago = intencion_pago;
        this.no_cuenta = no_cuenta;
        this.garantia = garantia;
        this.cargado = cargado;
        this.estado_ae = estado_ae;
        this.notas = notas;
        this.codigo_resultado = codigo_resultado;
        this.caso = caso;
        this.pdf = pdf;
        this.inv = inv;
        this.maycom = maycom;
        this.nits = nits;
        this.cosecha = cosecha;
        this.otro_no_cuenta = otro_no_cuenta;
        this.descripcion_adicional = descripcion_adicional;
        this.fecha_recepcion = fecha_recepcion;
        this.anticipo = anticipo;
        this.antiguedad = antiguedad;
        this.fecha_proximo_pago = fecha_proximo_pago;
        this.monto_proximo_pago = monto_proximo_pago;
        this.saldo = saldo;
        this.convenio_pactado = convenio_pactado;
        this.cuota_convenio = cuota_convenio;
        this.costas_pagadas = costas_pagadas;
        this.situacion_caso = situacion_caso;
        this.opcion_proximo_pago = opcion_proximo_pago;
        this.procuracion = procuracion;
        this.fecha_demanda = fecha_demanda;
        this.procurador = procurador;
        this.juzgado = juzgado;
        this.no_juicio = no_juicio;
        this.notificador = notificador;
        this.monto_demanda = monto_demanda;
        this.cuenta_principal_relacion = cuenta_principal_relacion;
        this.deudor_cuenta_relacionada = deudor_cuenta_relacionada;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Integer getActor() {
        return actor;
    }

    public void setActor(Integer actor) {
        this.actor = actor;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre_deudor() {
        return nombre_deudor;
    }

    public void setNombre_deudor(String nombre_deudor) {
        this.nombre_deudor = nombre_deudor;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTelefono_casa() {
        return telefono_casa;
    }

    public void setTelefono_casa(String telefono_casa) {
        this.telefono_casa = telefono_casa;
    }

    public String getTelefono_celular() {
        return telefono_celular;
    }

    public void setTelefono_celular(String telefono_celular) {
        this.telefono_celular = telefono_celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getLugar_trabajo() {
        return lugar_trabajo;
    }

    public void setLugar_trabajo(String lugar_trabajo) {
        this.lugar_trabajo = lugar_trabajo;
    }

    public String getContacto_trabajo() {
        return contacto_trabajo;
    }

    public void setContacto_trabajo(String contacto_trabajo) {
        this.contacto_trabajo = contacto_trabajo;
    }

    public String getTelefono_trabajo() {
        return telefono_trabajo;
    }

    public void setTelefono_trabajo(String telefono_trabajo) {
        this.telefono_trabajo = telefono_trabajo;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public Integer getGestor() {
        return gestor;
    }

    public void setGestor(Integer gestor) {
        this.gestor = gestor;
    }

    public Integer getEstado_judicial() {
        return estado_judicial;
    }

    public void setEstado_judicial(Integer estado_judicial) {
        this.estado_judicial = estado_judicial;
    }

    public Integer getEstatus_judicial() {
        return estatus_judicial;
    }

    public void setEstatus_judicial(Integer estatus_judicial) {
        this.estatus_judicial = estatus_judicial;
    }

    public Integer getEstado_extrajudicial() {
        return estado_extrajudicial;
    }

    public void setEstado_extrajudicial(Integer estado_extrajudicial) {
        this.estado_extrajudicial = estado_extrajudicial;
    }

    public Integer getEstatus_extrajudicial() {
        return estatus_extrajudicial;
    }

    public void setEstatus_extrajudicial(Integer estatus_extrajudicial) {
        this.estatus_extrajudicial = estatus_extrajudicial;
    }

    public Integer getIntencion_pago() {
        return intencion_pago;
    }

    public void setIntencion_pago(Integer intencion_pago) {
        this.intencion_pago = intencion_pago;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public String getEstado_ae() {
        return estado_ae;
    }

    public void setEstado_ae(String estado_ae) {
        this.estado_ae = estado_ae;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getCodigo_resultado() {
        return codigo_resultado;
    }

    public void setCodigo_resultado(Integer codigo_resultado) {
        this.codigo_resultado = codigo_resultado;
    }

    public Integer getCaso() {
        return caso;
    }

    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public String getMaycom() {
        return maycom;
    }

    public void setMaycom(String maycom) {
        this.maycom = maycom;
    }

    public String getNits() {
        return nits;
    }

    public void setNits(String nits) {
        this.nits = nits;
    }

    public String getCosecha() {
        return cosecha;
    }

    public void setCosecha(String cosecha) {
        this.cosecha = cosecha;
    }

    public String getOtro_no_cuenta() {
        return otro_no_cuenta;
    }

    public void setOtro_no_cuenta(String otro_no_cuenta) {
        this.otro_no_cuenta = otro_no_cuenta;
    }

    public String getDescripcion_adicional() {
        return descripcion_adicional;
    }

    public void setDescripcion_adicional(String descripcion_adicional) {
        this.descripcion_adicional = descripcion_adicional;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Date getFecha_proximo_pago() {
        return fecha_proximo_pago;
    }

    public void setFecha_proximo_pago(Date fecha_proximo_pago) {
        this.fecha_proximo_pago = fecha_proximo_pago;
    }

    public Double getMonto_proximo_pago() {
        return monto_proximo_pago;
    }

    public void setMonto_proximo_pago(Double monto_proximo_pago) {
        this.monto_proximo_pago = monto_proximo_pago;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getConvenio_pactado() {
        return convenio_pactado;
    }

    public void setConvenio_pactado(String convenio_pactado) {
        this.convenio_pactado = convenio_pactado;
    }

    public Double getCuota_convenio() {
        return cuota_convenio;
    }

    public void setCuota_convenio(Double cuota_convenio) {
        this.cuota_convenio = cuota_convenio;
    }

    public String getCostas_pagadas() {
        return costas_pagadas;
    }

    public void setCostas_pagadas(String costas_pagadas) {
        this.costas_pagadas = costas_pagadas;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public String getOpcion_proximo_pago() {
        return opcion_proximo_pago;
    }

    public void setOpcion_proximo_pago(String opcion_proximo_pago) {
        this.opcion_proximo_pago = opcion_proximo_pago;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public Date getFecha_demanda() {
        return fecha_demanda;
    }

    public void setFecha_demanda(Date fecha_demanda) {
        this.fecha_demanda = fecha_demanda;
    }

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public Double getMonto_demanda() {
        return monto_demanda;
    }

    public void setMonto_demanda(Double monto_demanda) {
        this.monto_demanda = monto_demanda;
    }

    public Integer getCuenta_principal_relacion() {
        return cuenta_principal_relacion;
    }

    public void setCuenta_principal_relacion(Integer cuenta_principal_relacion) {
        this.cuenta_principal_relacion = cuenta_principal_relacion;
    }

    public Integer getDeudor_cuenta_relacionada() {
        return deudor_cuenta_relacionada;
    }

    public void setDeudor_cuenta_relacionada(Integer deudor_cuenta_relacionada) {
        this.deudor_cuenta_relacionada = deudor_cuenta_relacionada;
    }
    
}
