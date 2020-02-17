package com.lexcom_cargas;

import java.io.Serializable;

public class Servicio implements Serializable {

    public final long serialVersionUID = 1L;

    public Servicio() {

    }

    public String actorActivar(java.lang.Integer usuarioSys, java.lang.Integer idActor, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.actorActivar(usuarioSys, idActor, poolConexion);
    }

    public String actorEliminar(java.lang.Integer usuarioSys, java.lang.Integer idActor, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.actorEliminar(usuarioSys, idActor, poolConexion);
    }

    public String actorInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String nitD, java.lang.String telefonoD, java.lang.String descripcionD, java.lang.String corporacionD, java.lang.String digitalizadosD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.actorInsertar(usuarioSys, nombreD, nitD, telefonoD, descripcionD, corporacionD, digitalizadosD, poolConexion);
    }

    public String actorModificar(java.lang.Integer usuarioSys, java.lang.Integer idActor, java.lang.String nombreD, java.lang.String nitD, java.lang.String telefonoD, java.lang.String descripcionD, java.lang.String corporacionD, java.lang.String digitalizadosD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.actorModificar(usuarioSys, idActor, nombreD, nitD, telefonoD, descripcionD, corporacionD, digitalizadosD, poolConexion);
    }

    public String actualizacionMasivaDeudores(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.actualizacionMasivaDeudores(usuarioSys, archivo, poolConexion);
    }

    public String aumentoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.aumentoEliminar(usuarioSys, idAumento, poolConexion);
    }

    public String aumentoInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer tipoDescuento, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.aumentoInsertar(usuarioSys, deudor, tipoDescuento, fecha, monto, descripcion, poolConexion);
    }

    public String aumentoModificar(java.lang.Integer usuarioSys, java.lang.Integer idAumento, java.lang.Integer deudor, java.lang.Integer tipoAumento, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.aumentoModificar(usuarioSys, idAumento, deudor, tipoAumento, fecha, monto, descripcion, poolConexion);
    }

    public String bancoActivar(java.lang.Integer usuarioSys, java.lang.Integer idBanco, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.bancoActivar(usuarioSys, idBanco, poolConexion);
    }

    public String bancoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idBanco, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.bancoEliminar(usuarioSys, idBanco, poolConexion);
    }

    public String bancoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.bancoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String bancoModificar(java.lang.Integer usuarioSys, java.lang.Integer idBanco, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.bancoModificar(usuarioSys, idBanco, nombreD, descripcionD, poolConexion);
    }

    public String cargaMasivaCaratula(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaMasivaCaratula(usuarioSys, archivo, poolConexion);
    }

    public String cargaMasivaDeligenciarMedidas(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaMasivaDeligenciarMedidas(usuarioSys, archivo, poolConexion);
    }

    public String cargaMasivaDeudoroes(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaMasivaDeudoroes(usuarioSys, archivo, poolConexion);
    }

    public String cargaMasivaMedidasPrecautorias(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaMasivaMedidasPrecautorias(usuarioSys, archivo, poolConexion);
    }

    public String cargaMasivaPresentarDemanda(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaMasivaPresentarDemanda(usuarioSys, archivo, poolConexion);
    }

    public String cargaPagosMasivos(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaPagosMasivos(usuarioSys, archivo, poolConexion);
    }

    public String cargaRotacionCartera(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaRotacionCartera(usuarioSys, archivo, poolConexion);
    }

    public String carteraActivar(java.lang.Integer usuarioSys, java.lang.Integer idCartera, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.carteraActivar(usuarioSys, idCartera, poolConexion);
    }

    public String carteraEliminar(java.lang.Integer usuarioSys, java.lang.Integer idCartera, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.carteraEliminar(usuarioSys, idCartera, poolConexion);
    }

    public String carteraInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.carteraInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String carteraModificar(java.lang.Integer usuarioSys, java.lang.Integer idCartera, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.carteraModificar(usuarioSys, idCartera, nombreD, descripcionD, poolConexion);
    }

    public String codigoContactabilidadActivar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.codigoContactabilidadActivar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String codigoContactabilidadEliminar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.codigoContactabilidadEliminar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String codigoContactabilidadInsertar(java.lang.Integer usuarioSys, java.lang.String codigoD, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.codigoContactabilidadInsertar(usuarioSys, codigoD, nombreD, descripcionD, poolConexion);
    }

    public String codigoContactabilidadModificar(java.lang.Integer usuarioSys, java.lang.Integer idCodigoContactabilidad, java.lang.String codigoD, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.codigoContactabilidadModificar(usuarioSys, idCodigoContactabilidad, codigoD, nombreD, descripcionD, poolConexion);
    }

    public String constantesModificar(java.lang.Integer usuarioSys, java.util.List<java.lang.String> constantes, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.constantesModificar(usuarioSys, constantes, poolConexion);
    }

    public String convenioDetalleInsertar(java.lang.Integer usuarioSys, java.lang.Integer idConvenio, javax.xml.datatype.XMLGregorianCalendar fechaPago, java.lang.String horaPago, java.lang.String estadoPromesa, java.lang.Double monto, java.lang.String observacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioDetalleInsertar(usuarioSys, idConvenio, fechaPago, horaPago, estadoPromesa, monto, observacion, poolConexion);
    }

    public String convenioDetalleModificar(java.lang.Integer usuarioSys, java.lang.Integer idConvenio, java.lang.Integer idPromesaPago, javax.xml.datatype.XMLGregorianCalendar fechaPago, java.lang.String horaPago, java.lang.String estadoPromesa, java.lang.Double monto, java.lang.String observacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioDetalleModificar(usuarioSys, idConvenio, idPromesaPago, fechaPago, horaPago, estadoPromesa, monto, observacion, poolConexion);
    }

    public String convenioDetalleModificarEstado(java.lang.Integer usuarioSys, java.lang.Integer idConvenio, java.lang.Integer idPromesaPago, java.lang.String estado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioDetalleModificarEstado(usuarioSys, idConvenio, idPromesaPago, estado, poolConexion);
    }

    public String convenioInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.String tipoConvenio, java.lang.String estado, java.lang.Double saldo, java.lang.Double interes, java.lang.Double mora, java.lang.Double gastoOtros, java.lang.Double rebajaInteres, java.lang.Double subtotalPagar, java.lang.Double costas, java.lang.Double montoCostas, java.lang.Double total, java.lang.Double cuotaInicial, java.lang.Double totalPagar, java.lang.Integer numeroCuotas, java.lang.Double montoCuota, java.lang.String frecuencia, javax.xml.datatype.XMLGregorianCalendar fechaPagoInicial, java.lang.String observacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioInsertar(usuarioSys, deudor, tipoConvenio, estado, saldo, interes, mora, gastoOtros, rebajaInteres, subtotalPagar, costas, montoCostas, total, cuotaInicial, totalPagar, numeroCuotas, montoCuota, frecuencia, fechaPagoInicial, observacion, poolConexion);
    }

    public String convenioModificar(java.lang.Integer usuarioSys, java.lang.Integer idConvenio, java.lang.Integer deudor, java.lang.String tipoConvenio, java.lang.String estado, java.lang.Double saldo, java.lang.Double interes, java.lang.Double mora, java.lang.Double gastoOtros, java.lang.Double rebajaInteres, java.lang.Double subtotalPagar, java.lang.Double costas, java.lang.Double montoCostas, java.lang.Double total, java.lang.Double cuotaInicial, java.lang.Double totalPagar, java.lang.Integer numeroCuotas, java.lang.Double montoCuota, java.lang.String frecuencia, javax.xml.datatype.XMLGregorianCalendar fechaPagoInicial, java.lang.String observacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioModificar(usuarioSys, idConvenio, deudor, tipoConvenio, estado, saldo, interes, mora, gastoOtros, rebajaInteres, subtotalPagar, costas, montoCostas, total, cuotaInicial, totalPagar, numeroCuotas, montoCuota, frecuencia, fechaPagoInicial, observacion, poolConexion);
    }

    public String convenioModificarEstado(java.lang.Integer usuarioSys, java.lang.Integer idConvenio, java.lang.String estado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.convenioModificarEstado(usuarioSys, idConvenio, estado, poolConexion);
    }

    public String corporacionActivar(java.lang.Integer usuarioSys, java.lang.Integer idCorporacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.corporacionActivar(usuarioSys, idCorporacion, poolConexion);
    }

    public String corporacionEliminar(java.lang.Integer usuarioSys, java.lang.Integer idCorporacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.corporacionEliminar(usuarioSys, idCorporacion, poolConexion);
    }

    public String corporacionInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.corporacionInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String corporacionModificar(java.lang.Integer usuarioSys, java.lang.Integer idCorporacion, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.corporacionModificar(usuarioSys, idCorporacion, nombreD, descripcionD, poolConexion);
    }

    public String descuentoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idDescuento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.descuentoEliminar(usuarioSys, idDescuento, poolConexion);
    }

    public String descuentoInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer tipoDescuento, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.descuentoInsertar(usuarioSys, deudor, tipoDescuento, fecha, monto, descripcion, poolConexion);
    }

    public String descuentoModificar(java.lang.Integer usuarioSys, java.lang.Integer idDescuento, java.lang.Integer deudor, java.lang.Integer tipoDescuento, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.descuentoModificar(usuarioSys, idDescuento, deudor, tipoDescuento, fecha, monto, descripcion, poolConexion);
    }

    public String deudorActivar(java.lang.Integer usuarioSys, java.lang.Integer idDeudor, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.deudorActivar(usuarioSys, idDeudor, poolConexion);
    }

    public String deudorEliminar(java.lang.Integer usuarioSys, java.lang.Integer idDeudor, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.deudorEliminar(usuarioSys, idDeudor, poolConexion);
    }

    public String deudorInsertar(java.lang.Integer usuarioSys, java.lang.Integer actorD, java.lang.String monedaD, java.lang.String dpiD, java.lang.String nitD, javax.xml.datatype.XMLGregorianCalendar fechaNacimientoD, java.lang.String nombreD, java.lang.String nacionalidadD, java.lang.String telefonoCasaD, java.lang.String telefonoCelularD, java.lang.String direccionD, java.lang.Integer zonaD, java.lang.String paisD, java.lang.String departamentoD, java.lang.String sexoD, java.lang.String estadoCivilD, javax.xml.datatype.XMLGregorianCalendar fechaIngresoD, java.lang.String profesionD, java.lang.String correoElectronicoD, java.lang.String lugarTrabajoD, java.lang.String direccionTrabajoD, java.lang.String telefonoTrabajoD, java.lang.Double montoInicialD, java.lang.Integer gestorD, java.lang.Integer sestadoD, java.lang.Integer estatusD, java.lang.String noCuentaD, java.lang.Integer garantiaD, java.lang.String cargadoD, java.lang.String estadoD, java.lang.String descripcionD, java.lang.Integer codigoContactabilidadD, java.lang.Integer casoD, java.lang.String pdfD, java.lang.String invD, java.lang.String maycomD, java.lang.String nitsD, java.lang.String cosechaD, java.lang.String noCuentaOtroD, java.lang.String descripcionAdicionalD, javax.xml.datatype.XMLGregorianCalendar fechaRecepcionD, java.lang.String anticipoD, java.lang.String antiguedadD, java.lang.Double saldoD, java.lang.String convenioPactadoD, java.lang.Double cuotaConvenioD, java.lang.String costasPagadasD, java.lang.String situacionCasoD, java.lang.String opcionProximoPagoD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.deudorInsertar(usuarioSys, actorD, monedaD, dpiD, nitD, fechaNacimientoD, nombreD, nacionalidadD, telefonoCasaD, telefonoCelularD, direccionD, zonaD, paisD, departamentoD, sexoD, estadoCivilD, fechaIngresoD, profesionD, correoElectronicoD, lugarTrabajoD, direccionTrabajoD, telefonoTrabajoD, montoInicialD, gestorD, sestadoD, estatusD, noCuentaD, garantiaD, cargadoD, estadoD, descripcionD, codigoContactabilidadD, casoD, pdfD, invD, maycomD, nitsD, cosechaD, noCuentaOtroD, descripcionAdicionalD, fechaRecepcionD, anticipoD, antiguedadD, saldoD, convenioPactadoD, cuotaConvenioD, costasPagadasD, situacionCasoD, opcionProximoPagoD, poolConexion);
    }

    public String deudorModificar(java.lang.Integer usuarioSys, java.lang.Integer idDeudor, java.lang.Integer actorD, java.lang.String monedaD, java.lang.String dpiD, java.lang.String nitD, javax.xml.datatype.XMLGregorianCalendar fechaNacimientoD, java.lang.String nombreD, java.lang.String nacionalidadD, java.lang.String telefonoCasaD, java.lang.String telefonoCelularD, java.lang.String direccionD, java.lang.Integer zonaD, java.lang.String paisD, java.lang.String departamentoD, java.lang.String sexoD, java.lang.String estadoCivilD, javax.xml.datatype.XMLGregorianCalendar fechaIngresoD, java.lang.String profesionD, java.lang.String correoElectronicoD, java.lang.String lugarTrabajoD, java.lang.String direccionTrabajoD, java.lang.String telefonoTrabajoD, java.lang.Double montoInicialD, java.lang.Integer gestorD, java.lang.Integer sestadoD, java.lang.Integer estatusD, java.lang.String noCuentaD, java.lang.Integer garantiaD, java.lang.String cargadoD, java.lang.String estadoD, java.lang.String descripcionD, java.lang.Integer codigoContactabilidadD, java.lang.Integer casoD, java.lang.String pdfD, java.lang.String invD, java.lang.String maycomD, java.lang.String nitsD, java.lang.String cosechaD, java.lang.String noCuentaOtroD, java.lang.String descripcionAdicionalD, javax.xml.datatype.XMLGregorianCalendar fechaRecepcionD, java.lang.String anticipoD, java.lang.String antiguedadD, java.lang.Double saldoD, java.lang.String convenioPactadoD, java.lang.Double cuotaConvenioD, java.lang.String costasPagadasD, java.lang.String situacionCasoD, java.lang.String opcionProximoPagoD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.deudorModificar(usuarioSys, idDeudor, actorD, monedaD, dpiD, nitD, fechaNacimientoD, nombreD, nacionalidadD, telefonoCasaD, telefonoCelularD, direccionD, zonaD, paisD, departamentoD, sexoD, estadoCivilD, fechaIngresoD, profesionD, correoElectronicoD, lugarTrabajoD, direccionTrabajoD, telefonoTrabajoD, montoInicialD, gestorD, sestadoD, estatusD, noCuentaD, garantiaD, cargadoD, estadoD, descripcionD, codigoContactabilidadD, casoD, pdfD, invD, maycomD, nitsD, cosechaD, noCuentaOtroD, descripcionAdicionalD, fechaRecepcionD, anticipoD, antiguedadD, saldoD, convenioPactadoD, cuotaConvenioD, costasPagadasD, situacionCasoD, opcionProximoPagoD, poolConexion);
    }

    public String estadoExtraActivar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoExtraActivar(usuarioSys, idSestado, poolConexion);
    }

    public String estadoExtraEliminar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoExtraEliminar(usuarioSys, idSestado, poolConexion);
    }

    public String estadoExtraInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoExtraInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String estadoExtraModificar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoExtraModificar(usuarioSys, idSestado, nombreD, descripcionD, poolConexion);
    }

    public String estadoActivar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoActivar(usuarioSys, idSestado, poolConexion);
    }

    public String estadoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoEliminar(usuarioSys, idSestado, poolConexion);
    }

    public String estadoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String estadoModificar(java.lang.Integer usuarioSys, java.lang.Integer idSestado, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoModificar(usuarioSys, idSestado, nombreD, descripcionD, poolConexion);
    }

    public String estadoStatusExtraPermite(java.lang.Integer usuarioSys, java.util.List<java.lang.String> estatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoStatusExtraPermite(usuarioSys, estatus, poolConexion);
    }

    public String estadoStatusExtrajudicial(java.lang.Integer usuarioSys, java.lang.Integer estadoD, java.util.List<java.lang.Integer> estatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoStatusExtrajudicial(usuarioSys, estadoD, estatus, poolConexion);
    }

    public String estadoStatusJudicial(java.lang.Integer usuarioSys, java.lang.Integer estadoD, java.util.List<java.lang.Integer> estatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.estadoStatusJudicial(usuarioSys, estadoD, estatus, poolConexion);
    }

    public String fiadorEliminar(java.lang.Integer usuarioSys, java.lang.Integer idFiador, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.fiadorEliminar(usuarioSys, idFiador, poolConexion);
    }

    public String fiadorInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.String dpi, java.lang.String nit, javax.xml.datatype.XMLGregorianCalendar fechaNacimiento, java.lang.String nombre, java.lang.String nacionalidad, java.lang.String telefono, java.lang.String direccion, java.lang.Integer zona, java.lang.String pais, java.lang.String departamento, java.lang.String sexo, java.lang.String estadoCivil, java.lang.String profesion, java.lang.String correoElectronico, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.fiadorInsertar(usuarioSys, deudor, dpi, nit, fechaNacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estadoCivil, profesion, correoElectronico, descripcion, poolConexion);
    }

    public String fiadorModificar(java.lang.Integer usuarioSys, java.lang.Integer idFiador, java.lang.Integer deudor, java.lang.String dpi, java.lang.String nit, javax.xml.datatype.XMLGregorianCalendar fechaNacimiento, java.lang.String nombre, java.lang.String nacionalidad, java.lang.String telefono, java.lang.String direccion, java.lang.Integer zona, java.lang.String pais, java.lang.String departamento, java.lang.String sexo, java.lang.String estadoCivil, java.lang.String profesion, java.lang.String correoElectronico, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.fiadorModificar(usuarioSys, idFiador, deudor, dpi, nit, fechaNacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estadoCivil, profesion, correoElectronico, descripcion, poolConexion);
    }

    public String frasePredeterminadaActivar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.frasePredeterminadaActivar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String frasePredeterminadaEliminar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.frasePredeterminadaEliminar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String frasePredeterminadaInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String tipoD, java.lang.String fraseD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.frasePredeterminadaInsertar(usuarioSys, nombreD, tipoD, fraseD, poolConexion);
    }

    public String frasePredeterminadaModificar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String nombreD, java.lang.String tipoD, java.lang.String fraseD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.frasePredeterminadaModificar(usuarioSys, idTipoAumento, nombreD, tipoD, fraseD, poolConexion);
    }

    public String garantiaActivar(java.lang.Integer usuarioSys, java.lang.Integer idGarantia, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.garantiaActivar(usuarioSys, idGarantia, poolConexion);
    }

    public String garantiaEliminar(java.lang.Integer usuarioSys, java.lang.Integer idGarantia, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.garantiaEliminar(usuarioSys, idGarantia, poolConexion);
    }

    public String garantiaInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.garantiaInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String garantiaModificar(java.lang.Integer usuarioSys, java.lang.Integer idGarantia, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.garantiaModificar(usuarioSys, idGarantia, nombreD, descripcionD, poolConexion);
    }

    public String gestionAdministracionInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer usuario, java.lang.Integer codigoContactabiliad, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.gestionAdministracionInsertar(usuarioSys, deudor, usuario, codigoContactabiliad, descripcion, poolConexion);
    }

    public String gestionJuridicoInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer usuario, java.lang.Integer codigoContactabiliad, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.gestionJuridicoInsertar(usuarioSys, deudor, usuario, codigoContactabiliad, descripcion, poolConexion);
    }

    public String guardarExpedienteCaso(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer garantia, java.lang.String cargado, java.lang.String anticipo, java.lang.Double saldo, java.lang.String pdf, java.lang.String inv, java.lang.String maycom, java.lang.String nits, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.guardarExpedienteCaso(usuarioSys, deudor, garantia, cargado, anticipo, saldo, pdf, inv, maycom, nits, poolConexion);
    }

    public String guardarExpedienteJudicial(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer estadoJudicial, java.lang.Integer statusExtra, java.lang.Integer procurador, javax.xml.datatype.XMLGregorianCalendar fechaJuicio, java.lang.Integer juzgado, java.lang.String noJuicio, java.lang.Integer notificador, java.lang.String abogadoDeudor, java.lang.String sumario, javax.xml.datatype.XMLGregorianCalendar memorial, java.lang.String deudorNotificado, javax.xml.datatype.XMLGregorianCalendar fechaNotificacion, java.lang.Double montoDemanda, java.lang.String procuracion, java.lang.String situacionCaso, java.lang.String razonNotificacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.guardarExpedienteJudicial(usuarioSys, deudor, estadoJudicial, statusExtra, procurador, fechaJuicio, juzgado, noJuicio, notificador, abogadoDeudor, sumario, memorial, deudorNotificado, fechaNotificacion, montoDemanda, procuracion, situacionCaso, razonNotificacion, poolConexion);
    }

    public String juicioEliminar(java.lang.Integer usuarioSys, java.lang.Integer idJuicio, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juicioEliminar(usuarioSys, idJuicio, poolConexion);
    }

    public String juicioModificar(java.lang.Integer usuarioSys, java.lang.Integer idJuicio, java.lang.Integer deudor, java.lang.Integer juzgado, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.String noJuicio, java.lang.Double monto, java.lang.String descripcion, java.util.List<lexcom.ws.StringArray> modeloArraigo, java.util.List<lexcom.ws.StringArray> modeloBanco, java.util.List<lexcom.ws.StringArray> modeloFinca, java.util.List<lexcom.ws.StringArray> modeloSalario, java.util.List<lexcom.ws.StringArray> modeloVehiculo, java.util.List<lexcom.ws.StringArray> modeloOtros, java.lang.Integer procurador, java.lang.String razonNotificacion, java.lang.Integer notificador, java.lang.String abogadoDeudor, java.lang.String sumario, javax.xml.datatype.XMLGregorianCalendar memorial, java.lang.String procuracion, javax.xml.datatype.XMLGregorianCalendar fechaAdmisionDemanda, java.lang.String deudorNotificado, javax.xml.datatype.XMLGregorianCalendar fechaNotificacion, java.lang.String depositario, java.lang.String tiempoEstimado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juicioModificar(usuarioSys, idJuicio, deudor, juzgado, fecha, noJuicio, monto, descripcion, modeloArraigo, modeloBanco, modeloFinca, modeloSalario, modeloVehiculo, modeloOtros, procurador, razonNotificacion, notificador, abogadoDeudor, sumario, memorial, procuracion, fechaAdmisionDemanda, deudorNotificado, fechaNotificacion, depositario, tiempoEstimado, poolConexion);
    }

    public String juzgadoActivar(java.lang.Integer usuarioSys, java.lang.Integer idJuzgado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juzgadoActivar(usuarioSys, idJuzgado, poolConexion);
    }

    public String juzgadoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idJuzgado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juzgadoEliminar(usuarioSys, idJuzgado, poolConexion);
    }

    public String juzgadoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juzgadoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String juzgadoModificar(java.lang.Integer usuarioSys, java.lang.Integer idJuzgado, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.juzgadoModificar(usuarioSys, idJuzgado, nombreD, descripcionD, poolConexion);
    }

    public java.util.List<lexcom.ws.StringArray> listaArchivosDigitalizados(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.listaArchivosDigitalizados(usuarioSys, deudor, poolConexion);
    }

    public String logueo(java.lang.String usuario, java.lang.String contrasena, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.logueo(usuario, contrasena, poolConexion);
    }

    public String logueoCambio(java.lang.String usuario, java.lang.String contrasena, java.lang.String newContrasena, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.logueoCambio(usuario, contrasena, newContrasena, poolConexion);
    }

    public String modificarDeudorExpediente(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer actor, java.lang.String moneda, java.lang.String dpi, java.lang.String nit, javax.xml.datatype.XMLGregorianCalendar fechaNacimiento, java.lang.String nombre, java.lang.String telefonoCasa, java.lang.String telefonoCelular, java.lang.String direccion, javax.xml.datatype.XMLGregorianCalendar fechaRecepcion, java.lang.String correoElectronico, java.lang.String lugarTrabajo, java.lang.String direccionTrabajo, java.lang.String telefonoTrabajo, java.lang.Double montoInicial, java.lang.Integer gestor, java.lang.Integer sestado, java.lang.Integer estatus, java.lang.String noCuenta, java.lang.Integer garantia, java.lang.String cargado, java.lang.String estado, java.lang.String pdf, java.lang.String inv, java.lang.String maycom, java.lang.String nits, java.lang.Double saldo, javax.xml.datatype.XMLGregorianCalendar fechaProximoPago, java.lang.Integer caso, java.lang.String convenioPactado, java.lang.Double cuotaConvenio, java.lang.String noCuentaOtro, java.lang.String situacionCaso, java.lang.String opcionProximoPago, java.lang.String anticipo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.modificarDeudorExpediente(usuarioSys, deudor, actor, moneda, dpi, nit, fechaNacimiento, nombre, telefonoCasa, telefonoCelular, direccion, fechaRecepcion, correoElectronico, lugarTrabajo, direccionTrabajo, telefonoTrabajo, montoInicial, gestor, sestado, estatus, noCuenta, garantia, cargado, estado, pdf, inv, maycom, nits, saldo, fechaProximoPago, caso, convenioPactado, cuotaConvenio, noCuentaOtro, situacionCaso, opcionProximoPago, anticipo, poolConexion);
    }

    public String modificarJuicioExpediente(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer juicio, java.lang.Integer procurador, java.lang.Integer juzgado, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.String razonNotificacion, java.lang.String noJuicio, java.lang.Integer notificador, java.lang.String abogadoDeudor, java.lang.String sumario, javax.xml.datatype.XMLGregorianCalendar memorial, java.lang.String procuracion, java.lang.String deudorNotificado, javax.xml.datatype.XMLGregorianCalendar fechaNotificacion, java.lang.Double monto, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.modificarJuicioExpediente(usuarioSys, deudor, juicio, procurador, juzgado, fecha, razonNotificacion, noJuicio, notificador, abogadoDeudor, sumario, memorial, procuracion, deudorNotificado, fechaNotificacion, monto, poolConexion);
    }

    public String pagoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idPago, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.pagoEliminar(usuarioSys, idPago, poolConexion);
    }

    public String pagoInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer banco, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.String noBoleta, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.pagoInsertar(usuarioSys, deudor, banco, fecha, noBoleta, monto, descripcion, poolConexion);
    }

    public String pagoModificar(java.lang.Integer usuarioSys, java.lang.Integer idPago, java.lang.Integer deudor, java.lang.Integer banco, javax.xml.datatype.XMLGregorianCalendar fecha, java.lang.String noBoleta, java.lang.Double monto, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.pagoModificar(usuarioSys, idPago, deudor, banco, fecha, noBoleta, monto, descripcion, poolConexion);
    }

    public String permisoExpediente(java.lang.Integer usuarioSys, java.util.List<java.lang.String> idCartera, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.permisoExpediente(usuarioSys, idCartera, poolConexion);
    }

    public String permisosRolModificar(java.lang.Integer usuarioSys, java.lang.Integer usuario, java.util.List<java.lang.String> menusNoAsignados, java.util.List<java.lang.String> menusAsignados, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.permisosRolModificar(usuarioSys, usuario, menusNoAsignados, menusAsignados, poolConexion);
    }

    public String permisosUsuarioModificar(java.lang.Integer usuarioSys, java.lang.Integer usuario, java.lang.Integer menu, java.lang.String nuevo, java.lang.String modificar, java.lang.String eliminar, java.lang.String activar, java.lang.String ver, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.permisosUsuarioModificar(usuarioSys, usuario, menu, nuevo, modificar, eliminar, activar, ver, poolConexion);
    }

    public String permisosUsuarioUnoModificar(java.lang.Integer usuarioSys, java.lang.Integer usuario, java.util.List<java.lang.String> menusNoAsignados, java.util.List<java.lang.String> menusAsignados, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.permisosUsuarioUnoModificar(usuarioSys, usuario, menusNoAsignados, menusAsignados, poolConexion);
    }

    public String promesaPagoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idPromesaPago, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.promesaPagoEliminar(usuarioSys, idPromesaPago, poolConexion);
    }

    public String promesaPagoInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, javax.xml.datatype.XMLGregorianCalendar fechaIngreso, javax.xml.datatype.XMLGregorianCalendar fechaPago, java.lang.Integer horaPago, java.lang.Integer minutoPago, java.lang.String estadoPromesa, java.lang.String observacion, java.lang.Double monto, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.promesaPagoInsertar(usuarioSys, deudor, fechaIngreso, fechaPago, horaPago, minutoPago, estadoPromesa, observacion, monto, poolConexion);
    }

    public String promesaPagoModificar(java.lang.Integer usuarioSys, java.lang.Integer idPromesaPago, java.lang.Integer deudor, javax.xml.datatype.XMLGregorianCalendar fechaIngreso, javax.xml.datatype.XMLGregorianCalendar fechaPago, java.lang.Integer horaPago, java.lang.Integer minutoPago, java.lang.String estadoPromesa, java.lang.String observacion, java.lang.Double monto, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.promesaPagoModificar(usuarioSys, idPromesaPago, deudor, fechaIngreso, fechaPago, horaPago, minutoPago, estadoPromesa, observacion, monto, poolConexion);
    }

    public String referenciaEliminar(java.lang.Integer usuarioSys, java.lang.Integer idReferencia, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.referenciaEliminar(usuarioSys, idReferencia, poolConexion);
    }

    public String referenciaInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.String dpi, java.lang.String nit, javax.xml.datatype.XMLGregorianCalendar fechaNacimiento, java.lang.String nombre, java.lang.String nacionalidad, java.lang.String telefono, java.lang.String direccion, java.lang.Integer zona, java.lang.String pais, java.lang.String departamento, java.lang.String sexo, java.lang.String estadoCivil, java.lang.String profesion, java.lang.String correoElectronico, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.referenciaInsertar(usuarioSys, deudor, dpi, nit, fechaNacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estadoCivil, profesion, correoElectronico, descripcion, poolConexion);
    }

    public String referenciaModificar(java.lang.Integer usuarioSys, java.lang.Integer idReferencia, java.lang.Integer deudor, java.lang.String dpi, java.lang.String nit, javax.xml.datatype.XMLGregorianCalendar fechaNacimiento, java.lang.String nombre, java.lang.String nacionalidad, java.lang.String telefono, java.lang.String direccion, java.lang.Integer zona, java.lang.String pais, java.lang.String departamento, java.lang.String sexo, java.lang.String estadoCivil, java.lang.String profesion, java.lang.String correoElectronico, java.lang.String descripcion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.referenciaModificar(usuarioSys, idReferencia, deudor, dpi, nit, fechaNacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estadoCivil, profesion, correoElectronico, descripcion, poolConexion);
    }

    public String reiniciarContrasena(java.lang.Integer usuarioSys, java.lang.Integer idUsuario, java.lang.String contrasenaVieja, java.lang.String contrasenaNueva, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.reiniciarContrasena(usuarioSys, idUsuario, contrasenaVieja, contrasenaNueva, poolConexion);
    }

    public java.util.List<lexcom.ws.StringArray> reporte(java.lang.String cadenasql, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.reporte(cadenasql, poolConexion);
    }

    public String rolEliminar(java.lang.Integer usuarioSys, java.lang.Integer idRol, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.rolEliminar(usuarioSys, idRol, poolConexion);
    }

    public String rolInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.rolInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String rolModificar(java.lang.Integer usuarioSys, java.lang.Integer idRol, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.rolModificar(usuarioSys, idRol, nombreD, descripcionD, poolConexion);
    }

    public java.util.List<lexcom.ws.StringArray> rotacionAutomatica(java.lang.Integer usuarioSys, java.lang.Integer cartera, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.rotacionAutomatica(usuarioSys, cartera, poolConexion);
    }

    public String sqlTransaccion(java.lang.String cadenasql, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.sqlTransaccion(cadenasql, poolConexion);
    }

    public String statusExtraActivar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusExtraActivar(usuarioSys, idEstatus, poolConexion);
    }

    public String statusExtraEliminar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusExtraEliminar(usuarioSys, idEstatus, poolConexion);
    }

    public String statusExtraInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusExtraInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String statusExtraModificar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusExtraModificar(usuarioSys, idEstatus, nombreD, descripcionD, poolConexion);
    }

    public String statusActivar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusActivar(usuarioSys, idEstatus, poolConexion);
    }

    public String statusEliminar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusEliminar(usuarioSys, idEstatus, poolConexion);
    }

    public String statusInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String statusModificar(java.lang.Integer usuarioSys, java.lang.Integer idEstatus, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.statusModificar(usuarioSys, idEstatus, nombreD, descripcionD, poolConexion);
    }

    public String tipoCodigoCodigoResultado(java.lang.Integer usuarioSys, java.lang.Integer tipoCodigoResultado, java.util.List<java.lang.Integer> codigoResultado, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoCodigoResultado(usuarioSys, tipoCodigoResultado, codigoResultado, poolConexion);
    }

    public String tipoAumentoActivar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoAumentoActivar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String tipoAumentoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoAumentoEliminar(usuarioSys, idTipoAumento, poolConexion);
    }

    public String tipoAumentoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoAumentoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String tipoAumentoModificar(java.lang.Integer usuarioSys, java.lang.Integer idTipoAumento, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoAumentoModificar(usuarioSys, idTipoAumento, nombreD, descripcionD, poolConexion);
    }

    public String tipoCodigoResultadoActivar(java.lang.Integer usuarioSys, java.lang.Integer idTipoCodigoContactabilidad, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoResultadoActivar(usuarioSys, idTipoCodigoContactabilidad, poolConexion);
    }

    public String tipoCodigoResultadoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idTipoCodigoContactabilidad, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoResultadoEliminar(usuarioSys, idTipoCodigoContactabilidad, poolConexion);
    }

    public String tipoCodigoResultadoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoResultadoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String tipoCodigoResultadoModificar(java.lang.Integer usuarioSys, java.lang.Integer idTipoCodigoContactabilidad, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoResultadoModificar(usuarioSys, idTipoCodigoContactabilidad, nombreD, descripcionD, poolConexion);
    }

    public String tipoDescuentoActivar(java.lang.Integer usuarioSys, java.lang.Integer idTipoDescuento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoDescuentoActivar(usuarioSys, idTipoDescuento, poolConexion);
    }

    public String tipoDescuentoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idTipoDescuento, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoDescuentoEliminar(usuarioSys, idTipoDescuento, poolConexion);
    }

    public String tipoDescuentoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoDescuentoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String tipoDescuentoModificar(java.lang.Integer usuarioSys, java.lang.Integer idTipoDescuento, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoDescuentoModificar(usuarioSys, idTipoDescuento, nombreD, descripcionD, poolConexion);
    }

    public String usuarioActivar(java.lang.Integer usuarioSys, java.lang.Integer idUsuario, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.usuarioActivar(usuarioSys, idUsuario, poolConexion);
    }

    public String usuarioEliminar(java.lang.Integer usuarioSys, java.lang.Integer idUsuario, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.usuarioEliminar(usuarioSys, idUsuario, poolConexion);
    }

    public String usuarioInsertar(java.lang.Integer usuarioSys, java.lang.String nombreCompletoD, java.lang.String nombreD, java.lang.String contrasenaD, java.lang.String recontrasenaD, java.lang.String descripcionD, java.lang.String gestorD, java.lang.String procuradorD, java.lang.String asistenteD, java.lang.String digitadorD, java.lang.String investigadorD, java.lang.Integer tipoUsuarioD, java.lang.Integer reinicio, java.lang.Integer rol, java.util.List<java.lang.String> usuarioCorporacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.usuarioInsertar(usuarioSys, nombreCompletoD, nombreD, contrasenaD, recontrasenaD, descripcionD, gestorD, procuradorD, asistenteD, digitadorD, investigadorD, tipoUsuarioD, reinicio, rol, usuarioCorporacion, poolConexion);
    }

    public String usuarioModificar(java.lang.Integer usuarioSys, java.lang.Integer idUsuario, java.lang.String nombreCompletoD, java.lang.String nombreD, java.lang.String contrasenaD, java.lang.String descripcionD, java.lang.String gestorD, java.lang.String procuradorD, java.lang.String asistenteD, java.lang.String digitadorD, java.lang.String investigadorD, java.lang.Integer tipoUsuarioD, java.lang.Integer reinicio, java.lang.Integer rol, java.util.List<java.lang.String> usuarioCorporacion, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.usuarioModificar(usuarioSys, idUsuario, nombreCompletoD, nombreD, contrasenaD, descripcionD, gestorD, procuradorD, asistenteD, digitadorD, investigadorD, tipoUsuarioD, reinicio, rol, usuarioCorporacion, poolConexion);
    }

    public String intencionPagoInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.intencionPagoInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String intencionPagoModificar(java.lang.Integer usuarioSys, java.lang.Integer idIntencionPago, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.intencionPagoModificar(usuarioSys, idIntencionPago, nombreD, descripcionD, poolConexion);
    }

    public String intencionPagoEliminar(java.lang.Integer usuarioSys, java.lang.Integer idIntencionPago, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.intencionPagoEliminar(usuarioSys, idIntencionPago, poolConexion);
    }

    public String intencionPagoActivar(java.lang.Integer usuarioSys, java.lang.Integer idIntencionPago, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.intencionPagoActivar(usuarioSys, idIntencionPago, poolConexion);
    }

    public String razonDeudaInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.razonDeudaInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String razonDeudaModificar(java.lang.Integer usuarioSys, java.lang.Integer idRazonDeuda, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.razonDeudaModificar(usuarioSys, idRazonDeuda, nombreD, descripcionD, poolConexion);
    }

    public String razonDeudaEliminar(java.lang.Integer usuarioSys, java.lang.Integer idRazonDeuda, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.razonDeudaEliminar(usuarioSys, idRazonDeuda, poolConexion);
    }

    public String razonDeudaActivar(java.lang.Integer usuarioSys, java.lang.Integer idRazonDeuda, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.razonDeudaActivar(usuarioSys, idRazonDeuda, poolConexion);
    }

    public String tipoCodigoResultadoContacto(java.lang.Integer usuarioSys, java.util.List<java.lang.String> lstTipoCodigoResultadoContacto, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.tipoCodigoResultadoContacto(usuarioSys, lstTipoCodigoResultadoContacto, poolConexion);
    }

    public String gestionCobrosInsertar(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer usuario, java.lang.Integer codigoContactabiliad, java.lang.String descripcion, java.lang.String contacto, java.lang.Integer estadoExtrajudicial, java.lang.Integer estatusExtrajudicial, java.lang.Integer estadoJudicial, java.lang.Integer estatusJudicial, java.lang.Integer intencionPago, java.lang.Integer razonDeuda, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.gestionCobrosInsertar(usuarioSys, deudor, usuario, codigoContactabiliad, descripcion, contacto, estadoExtrajudicial, estatusExtrajudicial, estadoJudicial, estatusJudicial, intencionPago, razonDeuda, poolConexion);
    }

    public String guardarExpedienteExtrajudicial(java.lang.Integer usuarioSys, java.lang.Integer deudor, java.lang.Integer estadoExtra, java.lang.Integer statusExtra, java.lang.String telefonoCasa, java.lang.String telefonoCelular, java.lang.String correoElectronico, java.lang.String lugarTrabajo, java.lang.String contactoTrabajo, java.lang.String telefonoTrabajo, java.lang.String dpi, java.lang.String nit, java.lang.Integer intensionPago, java.lang.String direccion, java.lang.String notas, java.lang.Integer razonDeuda, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.guardarExpedienteExtrajudicial(usuarioSys, deudor, estadoExtra, statusExtra, telefonoCasa, telefonoCelular, correoElectronico, lugarTrabajo, contactoTrabajo, telefonoTrabajo, dpi, nit, intensionPago, direccion, notas, razonDeuda, poolConexion);
    }

    public java.util.List<lexcom.ws.StringArray> getMonitor(java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.getMonitor(poolConexion);
    }

    public String cargaGestionesCobros(java.lang.Integer usuarioSys, java.lang.String archivo, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.cargaGestionesCobros(usuarioSys, archivo, poolConexion);
    }

    public String antiguedadInsertar(java.lang.Integer usuarioSys, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.antiguedadInsertar(usuarioSys, nombreD, descripcionD, poolConexion);
    }

    public String antiguedadModificar(java.lang.Integer usuarioSys, java.lang.Integer idGarantia, java.lang.String nombreD, java.lang.String descripcionD, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.antiguedadModificar(usuarioSys, idGarantia, nombreD, descripcionD, poolConexion);
    }

    public String antiguedadEliminar(java.lang.Integer usuarioSys, java.lang.Integer idAntiguedad, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.antiguedadEliminar(usuarioSys, idAntiguedad, poolConexion);
    }

    public String antiguedadActivar(java.lang.Integer usuarioSys, java.lang.Integer idAntiguedad, java.lang.String poolConexion) {
        lexcom.ws.ServiciosLexcom_Service service = new lexcom.ws.ServiciosLexcom_Service();
        lexcom.ws.ServiciosLexcom port = service.getServiciosLexcomPort();
        return port.antiguedadActivar(usuarioSys, idAntiguedad, poolConexion);
    }
    
}
