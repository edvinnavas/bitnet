package com.lexcom.servicio;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import com.lexcom.controlador.Actor;
import com.lexcom.controlador.Driver;
import com.lexcom.controlador.Antiguedad;
import com.lexcom.controlador.Aumento;
import com.lexcom.controlador.Banco;
import com.lexcom.controlador.Carga_Masiva;
import com.lexcom.controlador.Cartera;
import com.lexcom.controlador.Codigo_Resultado;
import com.lexcom.controlador.Constantes;
import com.lexcom.controlador.Convenio;
import com.lexcom.controlador.Corporacion;
import com.lexcom.controlador.Descuento;
import com.lexcom.controlador.Deudor;
import com.lexcom.controlador.Estado_Estatus_Extrajudicial;
import com.lexcom.controlador.Estado_Estatus_Judicial;
import com.lexcom.controlador.Estado_Extrajudicial;
import com.lexcom.controlador.Estado_Judicial;
import com.lexcom.controlador.Estatus_Extrajudicial;
import com.lexcom.controlador.Estatus_Judicial;
import com.lexcom.controlador.Expediente;
import com.lexcom.controlador.Fiador;
import com.lexcom.controlador.Frase_Predeterminada;
import com.lexcom.controlador.Garantia;
import com.lexcom.controlador.Intencion_Pago;
import com.lexcom.controlador.Juicio;
import com.lexcom.controlador.Juzgado;
import com.lexcom.controlador.Pago;
import com.lexcom.controlador.Promesa_Pago;
import com.lexcom.controlador.Razon_Deuda;
import com.lexcom.controlador.Referencia;
import com.lexcom.controlador.Reporte;
import com.lexcom.controlador.Rol;
import com.lexcom.controlador.Tipo_Aumento;
import com.lexcom.controlador.Tipo_Codigo_Resultado;
import com.lexcom.controlador.Tipo_Codigo_Resultado_Codigo_Resultado;
import com.lexcom.controlador.Tipo_Codigo_Resultado_Codigo_Resultado_Contacto;
import com.lexcom.controlador.Tipo_Descuento;
import com.lexcom.controlador.Usuario;
import java.io.Serializable;
import java.util.Calendar;

@WebService(serviceName = "ServiciosLexcom")
public class ServiciosLexcom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @param cadenasql
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Reporte")
    public String[][] Reporte(
            @WebParam(name = "cadenasql") String cadenasql,
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado;

        try {
            Reporte reporte = new Reporte();
            resultado = reporte.reporte(cadenasql, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Reporte): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        }

        return resultado;
    }

    /**
     * @param cadenasql
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "SqlTransaccion")
    public String SqlTransaccion(
            @WebParam(name = "cadenasql") String cadenasql,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Reporte reporte = new Reporte();
            resultado = reporte.sql_transaccion(cadenasql, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(SqlTransaccion): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rotacion_Automatica")
    public String[][] Rotacion_Automatica(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "cartera") Integer cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado = null;
        Driver driver = new Driver();
        try {
            driver.getConn(poolConexion);
            resultado = driver.rotacion_automatica(usuario_sys, cartera);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Rotacion_Automatica): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = ex.toString();
        } finally {
            driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Deudoroes")
    public String Carga_Masiva_Deudoroes(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_masiva_deudoroes(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deudoroes): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actualizacion_Masiva_Deudores")
    public String Actualizacion_Masiva_Deudores(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.actualizacion_masiva_deudores(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Actualizacion_Masiva_Deudores): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Rotacion_Cartera")
    public String Carga_Rotacion_Cartera(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_rotacion_cartera(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Rotacion_Cartera): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Pagos_Masivos")
    public String Carga_Pagos_Masivos(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_pagos_masivos(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Pagos_Masivos): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Presentar_Demanda")
    public String Carga_Masiva_Presentar_Demanda(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_masiva_presentar_demanda(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Presentar_Demanda): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Caratula")
    public String Carga_Masiva_Caratula(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_masiva_caratula(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Caratula): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Medidas_Precautorias")
    public String Carga_Masiva_Medidas_Precautorias(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_masiva_medidas_precautorias(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Medidas_Precautorias): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Deligenciar_Medidas")
    public String Carga_Masiva_Deligenciar_Medidas(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_masiva_deligenciar_medidas(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deligenciar_Medidas): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_completo_d
     * @param nombre_d
     * @param contrasena_d
     * @param recontrasena_d
     * @param descripcion_d
     * @param gestor_d
     * @param procurador_d
     * @param asistente_d
     * @param digitador_d
     * @param investigador_d
     * @param tipo_usuario_d
     * @param reinicio
     * @param rol
     * @param usuario_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Insertar")
    public String Usuario_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_completo_d") String nombre_completo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "contrasena_d") String contrasena_d,
            @WebParam(name = "recontrasena_d") String recontrasena_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "gestor_d") String gestor_d,
            @WebParam(name = "procurador_d") String procurador_d,
            @WebParam(name = "asistente_d") String asistente_d,
            @WebParam(name = "digitador_d") String digitador_d,
            @WebParam(name = "investigador_d") String investigador_d,
            @WebParam(name = "tipo_usuario_d") Integer tipo_usuario_d,
            @WebParam(name = "reinicio") Integer reinicio,
            @WebParam(name = "rol") Integer rol,
            @WebParam(name = "usuario_corporacion") String[] usuario_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.usuario_insertar(usuario_sys, nombre_completo_d, nombre_d, contrasena_d, recontrasena_d, descripcion_d, gestor_d, procurador_d, asistente_d, digitador_d, investigador_d, tipo_usuario_d, reinicio, rol, usuario_corporacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Insertar): " + ex.toString());

        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_usuario
     * @param nombre_completo_d
     * @param nombre_d
     * @param contrasena_d
     * @param descripcion_d
     * @param gestor_d
     * @param procurador_d
     * @param asistente_d
     * @param digitador_d
     * @param investigador_d
     * @param tipo_usuario_d
     * @param reinicio
     * @param rol
     * @param usuario_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Modificar")
    public String Usuario_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "nombre_completo_d") String nombre_completo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "contrasena_d") String contrasena_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "gestor_d") String gestor_d,
            @WebParam(name = "procurador_d") String procurador_d,
            @WebParam(name = "asistente_d") String asistente_d,
            @WebParam(name = "digitador_d") String digitador_d,
            @WebParam(name = "investigador_d") String investigador_d,
            @WebParam(name = "tipo_usuario_d") Integer tipo_usuario_d,
            @WebParam(name = "reinicio") Integer reinicio,
            @WebParam(name = "rol") Integer rol,
            @WebParam(name = "usuario_corporacion") String[] usuario_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.usuario_modificar(usuario_sys, id_usuario, nombre_completo_d, nombre_d, contrasena_d, descripcion_d, gestor_d, procurador_d, asistente_d, digitador_d, investigador_d, tipo_usuario_d, reinicio, rol, usuario_corporacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_usuario
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Eliminar")
    public String Usuario_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.usuario_eliminar(usuario_sys, id_usuario, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_usuario
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Activar")
    public String Usuario_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.usuario_activar(usuario_sys, id_usuario, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario
     * @param contrasena
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Logueo")
    public String Logueo(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "contrasena") String contrasena,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario ws_usuario = new Usuario();
            resultado = ws_usuario.logueo(usuario, contrasena, poolConexion);
        } catch (Exception ex) {
            resultado = "index"; //Si occure alguna excepcion la aplicacion no loguea al usuario.
        }

        return resultado;
    }

    /**
     * @param usuario
     * @param contrasena
     * @param new_contrasena
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Logueo_cambio")
    public String Logueo_cambio(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "contrasena") String contrasena,
            @WebParam(name = "new_contrasena") String new_contrasena,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario ws_usuario = new Usuario();
            resultado = ws_usuario.logueo_cambio(usuario, contrasena, new_contrasena, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Logueo_cambio): " + ex.toString());
            resultado = "index" + ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Insertar")
    public String Corporacion_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Corporacion corporacion = new Corporacion();
            resultado = corporacion.corporacion_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_corporacion
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Modificar")
    public String Corporacion_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Corporacion corporacion = new Corporacion();
            resultado = corporacion.corporacion_modificar(usuario_sys, id_corporacion, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Eliminar")
    public String Corporacion_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Corporacion corporacion = new Corporacion();
            resultado = corporacion.corporacion_eliminar(usuario_sys, id_corporacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Activar")
    public String Corporacion_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Corporacion corporacion = new Corporacion();
            resultado = corporacion.corporacion_activar(usuario_sys, id_corporacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param nit_d
     * @param telefono_d
     * @param descripcion_d
     * @param corporacion_d
     * @param digitalizados_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Insertar")
    public String Actor_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "telefono_d") String telefono_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "corporacion_d") String corporacion_d,
            @WebParam(name = "digitalizados_d") String digitalizados_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Actor actor = new Actor();
            resultado = actor.actor_insertar(usuario_sys, nombre_d, nit_d, telefono_d, descripcion_d, corporacion_d, digitalizados_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Actor_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_actor
     * @param nombre_d
     * @param nit_d
     * @param telefono_d
     * @param descripcion_d
     * @param corporacion_d
     * @param digitalizados_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Modificar")
    public String Actor_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "telefono_d") String telefono_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "corporacion_d") String corporacion_d,
            @WebParam(name = "digitalizados_d") String digitalizados_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Actor actor = new Actor();
            resultado = actor.actor_modificar(usuario_sys, id_actor, nombre_d, nit_d, telefono_d, descripcion_d, corporacion_d, digitalizados_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Actor_Modificar): " + ex.toString());

        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_actor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Eliminar")
    public String Actor_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Actor actor = new Actor();
            resultado = actor.actor_eliminar(usuario_sys, id_actor, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Actor_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_actor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Activar")
    public String Actor_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Actor actor = new Actor();
            resultado = actor.actor_activar(usuario_sys, id_actor, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Actor_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param actor_d
     * @param moneda_d
     * @param dpi_d
     * @param nit_d
     * @param fecha_nacimiento_d
     * @param nombre_d
     * @param nacionalidad_d
     * @param telefono_casa_d
     * @param telefono_celular_d
     * @param direccion_d
     * @param zona_d
     * @param pais_d
     * @param departamento_d
     * @param sexo_d
     * @param estado_civil_d
     * @param fecha_ingreso_d
     * @param profesion_d
     * @param correo_electronico_d
     * @param lugar_trabajo_d
     * @param direccion_trabajo_d
     * @param telefono_trabajo_d
     * @param monto_inicial_d
     * @param gestor_d
     * @param sestado_d
     * @param estatus_d
     * @param no_cuenta_d
     * @param garantia_d
     * @param cargado_d
     * @param estado_d
     * @param descripcion_d
     * @param codigo_contactabilidad_d
     * @param caso_d
     * @param PDF_d
     * @param INV_d
     * @param MAYCOM_d
     * @param NITS_d
     * @param cosecha_d
     * @param no_cuenta_otro_d
     * @param descripcion_adicional_d
     * @param fecha_recepcion_d
     * @param anticipo_d
     * @param antiguedad_d
     * @param fecha_proximo_pago_d,
     * @param monto_proximo_pago_d,
     * @param saldo_d
     * @param convenio_pactado_d
     * @param cuota_convenio_d
     * @param costas_pagadas_d
     * @param situacion_caso_d
     * @param opcion_proximo_pago_d
     * @param sestado_extra_d
     * @param estatus_extra_d
     * @param intencion_pago_d
     * @param razon_deuda_d
     * @param cuenta_principal_relacion_d
     * @param deudor_cuenta_relacionada_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Insertar")
    public String Deudor_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "actor_d") Integer actor_d,
            @WebParam(name = "moneda_d") String moneda_d,
            @WebParam(name = "dpi_d") String dpi_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "fecha_nacimiento_d") Calendar fecha_nacimiento_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nacionalidad_d") String nacionalidad_d,
            @WebParam(name = "telefono_casa_d") String telefono_casa_d,
            @WebParam(name = "telefono_celular_d") String telefono_celular_d,
            @WebParam(name = "direccion_d") String direccion_d,
            @WebParam(name = "zona_d") Integer zona_d,
            @WebParam(name = "pais_d") String pais_d,
            @WebParam(name = "departamento_d") String departamento_d,
            @WebParam(name = "sexo_d") String sexo_d,
            @WebParam(name = "estado_civil_d") String estado_civil_d,
            @WebParam(name = "fecha_ingreso_d") Calendar fecha_ingreso_d,
            @WebParam(name = "profesion_d") String profesion_d,
            @WebParam(name = "correo_electronico_d") String correo_electronico_d,
            @WebParam(name = "lugar_trabajo_d") String lugar_trabajo_d,
            @WebParam(name = "direccion_trabajo_d") String direccion_trabajo_d,
            @WebParam(name = "telefono_trabajo_d") String telefono_trabajo_d,
            @WebParam(name = "monto_inicial_d") Double monto_inicial_d,
            @WebParam(name = "gestor_d") Integer gestor_d,
            @WebParam(name = "sestado_d") Integer sestado_d,
            @WebParam(name = "estatus_d") Integer estatus_d,
            @WebParam(name = "no_cuenta_d") String no_cuenta_d,
            @WebParam(name = "garantia_d") Integer garantia_d,
            @WebParam(name = "cargado_d") String cargado_d,
            @WebParam(name = "estado_d") String estado_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "codigo_contactabilidad_d") Integer codigo_contactabilidad_d,
            @WebParam(name = "caso_d") Integer caso_d,
            @WebParam(name = "PDF_d") String PDF_d,
            @WebParam(name = "INV_d") String INV_d,
            @WebParam(name = "MAYCOM_d") String MAYCOM_d,
            @WebParam(name = "NITS_d") String NITS_d,
            @WebParam(name = "cosecha_d") String cosecha_d,
            @WebParam(name = "no_cuenta_otro_d") String no_cuenta_otro_d,
            @WebParam(name = "descripcion_adicional_d") String descripcion_adicional_d,
            @WebParam(name = "fecha_recepcion_d") Calendar fecha_recepcion_d,
            @WebParam(name = "anticipo_d") String anticipo_d,
            @WebParam(name = "antiguedad_d") String antiguedad_d,
            @WebParam(name = "fecha_proximo_pago") Calendar fecha_proximo_pago_d,
            @WebParam(name = "monto_proximo_pago") Double monto_proximo_pago_d,
            @WebParam(name = "saldo_d") Double saldo_d,
            @WebParam(name = "convenio_pactado_d") String convenio_pactado_d,
            @WebParam(name = "cuota_convenio_d") Double cuota_convenio_d,
            @WebParam(name = "costas_pagadas_d") String costas_pagadas_d,
            @WebParam(name = "situacion_caso_d") String situacion_caso_d,
            @WebParam(name = "opcion_proximo_pago_d") String opcion_proximo_pago_d,
            @WebParam(name = "sestado_extra_d") Integer sestado_extra_d,
            @WebParam(name = "estatus_extra_d") Integer estatus_extra_d,
            @WebParam(name = "intencion_pago_d") Integer intencion_pago_d,
            @WebParam(name = "razon_deuda_d") Integer razon_deuda_d,
            @WebParam(name = "cuenta_principal_relacion_d") Integer cuenta_principal_relacion_d,
            @WebParam(name = "deudor_cuenta_relacionada_d") Integer deudor_cuenta_relacionada_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Deudor deudor = new Deudor();
            resultado = deudor.deudor_insertar(
                    usuario_sys,
                    actor_d,
                    moneda_d,
                    dpi_d,
                    nit_d,
                    fecha_nacimiento_d,
                    nombre_d,
                    nacionalidad_d,
                    telefono_casa_d,
                    telefono_celular_d,
                    direccion_d,
                    zona_d,
                    pais_d,
                    departamento_d,
                    sexo_d,
                    estado_civil_d,
                    fecha_ingreso_d,
                    profesion_d,
                    correo_electronico_d,
                    lugar_trabajo_d,
                    direccion_trabajo_d,
                    telefono_trabajo_d,
                    monto_inicial_d,
                    gestor_d,
                    sestado_d,
                    estatus_d,
                    no_cuenta_d,
                    garantia_d,
                    cargado_d,
                    estado_d,
                    descripcion_d,
                    codigo_contactabilidad_d,
                    caso_d,
                    PDF_d,
                    INV_d,
                    MAYCOM_d,
                    NITS_d,
                    cosecha_d,
                    no_cuenta_otro_d,
                    descripcion_adicional_d,
                    fecha_recepcion_d,
                    anticipo_d,
                    antiguedad_d,
                    fecha_proximo_pago_d,
                    monto_proximo_pago_d,
                    saldo_d,
                    convenio_pactado_d,
                    cuota_convenio_d,
                    costas_pagadas_d,
                    situacion_caso_d,
                    opcion_proximo_pago_d,
                    sestado_extra_d,
                    estatus_extra_d,
                    intencion_pago_d,
                    razon_deuda_d,
                    cuenta_principal_relacion_d,
                    deudor_cuenta_relacionada_d,
                    poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Insertar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_deudor
     * @param actor_d
     * @param moneda_d
     * @param dpi_d
     * @param nit_d
     * @param fecha_nacimiento_d
     * @param nombre_d
     * @param nacionalidad_d
     * @param telefono_casa_d
     * @param telefono_celular_d
     * @param direccion_d
     * @param zona_d
     * @param pais_d
     * @param departamento_d
     * @param sexo_d
     * @param estado_civil_d
     * @param fecha_ingreso_d
     * @param profesion_d
     * @param correo_electronico_d
     * @param lugar_trabajo_d
     * @param direccion_trabajo_d
     * @param telefono_trabajo_d
     * @param monto_inicial_d
     * @param gestor_d
     * @param sestado_d
     * @param estatus_d
     * @param no_cuenta_d
     * @param garantia_d
     * @param cargado_d
     * @param estado_d
     * @param descripcion_d
     * @param codigo_contactabilidad_d
     * @param caso_d
     * @param PDF_d
     * @param INV_d
     * @param MAYCOM_d
     * @param NITS_d
     * @param cosecha_d
     * @param no_cuenta_otro_d
     * @param descripcion_adicional_d
     * @param fecha_recepcion_d
     * @param anticipo_d
     * @param antiguedad_d
     * @param fecha_proximo_pago_d
     * @param monto_proximo_pago_d
     * @param saldo_d
     * @param convenio_pactado_d
     * @param cuota_convenio_d
     * @param costas_pagadas_d
     * @param situacion_caso_d
     * @param opcion_proximo_pago_d
     * @param sestado_extra_d
     * @param estatus_extra_d
     * @param intencion_pago_d
     * @param razon_deuda_d
     * @param cuenta_principal_relacion_d
     * @param deudor_cuenta_relacionada_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Modificar")
    public String Deudor_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "actor_d") Integer actor_d,
            @WebParam(name = "moneda_d") String moneda_d,
            @WebParam(name = "dpi_d") String dpi_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "fecha_nacimiento_d") Calendar fecha_nacimiento_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nacionalidad_d") String nacionalidad_d,
            @WebParam(name = "telefono_casa_d") String telefono_casa_d,
            @WebParam(name = "telefono_celular_d") String telefono_celular_d,
            @WebParam(name = "direccion_d") String direccion_d,
            @WebParam(name = "zona_d") Integer zona_d,
            @WebParam(name = "pais_d") String pais_d,
            @WebParam(name = "departamento_d") String departamento_d,
            @WebParam(name = "sexo_d") String sexo_d,
            @WebParam(name = "estado_civil_d") String estado_civil_d,
            @WebParam(name = "fecha_ingreso_d") Calendar fecha_ingreso_d,
            @WebParam(name = "profesion_d") String profesion_d,
            @WebParam(name = "correo_electronico_d") String correo_electronico_d,
            @WebParam(name = "lugar_trabajo_d") String lugar_trabajo_d,
            @WebParam(name = "direccion_trabajo_d") String direccion_trabajo_d,
            @WebParam(name = "telefono_trabajo_d") String telefono_trabajo_d,
            @WebParam(name = "monto_inicial_d") Double monto_inicial_d,
            @WebParam(name = "gestor_d") Integer gestor_d,
            @WebParam(name = "sestado_d") Integer sestado_d,
            @WebParam(name = "estatus_d") Integer estatus_d,
            @WebParam(name = "no_cuenta_d") String no_cuenta_d,
            @WebParam(name = "garantia_d") Integer garantia_d,
            @WebParam(name = "cargado_d") String cargado_d,
            @WebParam(name = "estado_d") String estado_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "codigo_contactabilidad_d") Integer codigo_contactabilidad_d,
            @WebParam(name = "caso_d") Integer caso_d,
            @WebParam(name = "PDF_d") String PDF_d,
            @WebParam(name = "INV_d") String INV_d,
            @WebParam(name = "MAYCOM_d") String MAYCOM_d,
            @WebParam(name = "NITS_d") String NITS_d,
            @WebParam(name = "cosecha_d") String cosecha_d,
            @WebParam(name = "no_cuenta_otro_d") String no_cuenta_otro_d,
            @WebParam(name = "descripcion_adicional_d") String descripcion_adicional_d,
            @WebParam(name = "fecha_recepcion_d") Calendar fecha_recepcion_d,
            @WebParam(name = "anticipo_d") String anticipo_d,
            @WebParam(name = "antiguedad_d") String antiguedad_d,
            @WebParam(name = "fecha_proximo_pago_d") Calendar fecha_proximo_pago_d,
            @WebParam(name = "monto_proximo_pago_d") Double monto_proximo_pago_d,
            @WebParam(name = "saldo_d") Double saldo_d,
            @WebParam(name = "convenio_pactado_d") String convenio_pactado_d,
            @WebParam(name = "cuota_convenio_d") Double cuota_convenio_d,
            @WebParam(name = "costas_pagadas_d") String costas_pagadas_d,
            @WebParam(name = "situacion_caso_d") String situacion_caso_d,
            @WebParam(name = "opcion_proximo_pago_d") String opcion_proximo_pago_d,
            @WebParam(name = "sestado_extra_d") Integer sestado_extra_d,
            @WebParam(name = "estatus_extra_d") Integer estatus_extra_d,
            @WebParam(name = "intencion_pago_d") Integer intencion_pago_d,
            @WebParam(name = "razon_deuda_d") Integer razon_deuda_d,
            @WebParam(name = "cuenta_principal_relacion_d") Integer cuenta_principal_relacion_d,
            @WebParam(name = "deudor_cuenta_relacionada_d") Integer deudor_cuenta_relacionada_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Deudor deudor = new Deudor();
            resultado = deudor.deudor_modificar(
                    usuario_sys,
                    id_deudor,
                    actor_d,
                    moneda_d,
                    dpi_d,
                    nit_d,
                    fecha_nacimiento_d,
                    nombre_d,
                    nacionalidad_d,
                    telefono_casa_d,
                    telefono_celular_d,
                    direccion_d,
                    zona_d,
                    pais_d,
                    departamento_d,
                    sexo_d,
                    estado_civil_d,
                    fecha_ingreso_d,
                    profesion_d,
                    correo_electronico_d,
                    lugar_trabajo_d,
                    direccion_trabajo_d,
                    telefono_trabajo_d,
                    monto_inicial_d,
                    gestor_d,
                    sestado_d,
                    estatus_d,
                    no_cuenta_d,
                    garantia_d,
                    cargado_d,
                    estado_d,
                    descripcion_d,
                    codigo_contactabilidad_d,
                    caso_d,
                    PDF_d,
                    INV_d,
                    MAYCOM_d,
                    NITS_d,
                    cosecha_d,
                    no_cuenta_otro_d,
                    descripcion_adicional_d,
                    fecha_recepcion_d,
                    anticipo_d,
                    antiguedad_d,
                    fecha_proximo_pago_d,
                    monto_proximo_pago_d,
                    saldo_d,
                    convenio_pactado_d,
                    cuota_convenio_d,
                    costas_pagadas_d,
                    situacion_caso_d,
                    opcion_proximo_pago_d,
                    sestado_extra_d,
                    estatus_extra_d,
                    intencion_pago_d,
                    razon_deuda_d,
                    cuenta_principal_relacion_d,
                    deudor_cuenta_relacionada_d,
                    poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Modificar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Eliminar")
    public String Deudor_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Deudor deudor = new Deudor();
            resultado = deudor.deudor_eliminar(usuario_sys, id_deudor, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Eliminar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Activar")
    public String Deudor_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Deudor deudor = new Deudor();
            resultado = deudor.deudor_activar(usuario_sys, id_deudor, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Activar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Insertar")
    public String Juzgado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juzgado juzgado = new Juzgado();
            resultado = juzgado.juzgado_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_juzgado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Modificar")
    public String Juzgado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juzgado juzgado = new Juzgado();
            resultado = juzgado.juzgado_modificar(usuario_sys, id_juzgado, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_juzgado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Eliminar")
    public String Juzgado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juzgado juzgado = new Juzgado();
            resultado = juzgado.juzgado_eliminar(usuario_sys, id_juzgado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_juzgado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Activar")
    public String Juzgado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juzgado juzgado = new Juzgado();
            resultado = juzgado.juzgado_activar(usuario_sys, id_juzgado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Insertar")
    public String Garantia_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Garantia garantia = new Garantia();
            resultado = garantia.garantia_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_garantia
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Modificar")
    public String Garantia_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Garantia garantia = new Garantia();
            resultado = garantia.garantia_modificar(usuario_sys, id_garantia, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_garantia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Eliminar")
    public String Garantia_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Garantia garantia = new Garantia();
            resultado = garantia.garantia_eliminar(usuario_sys, id_garantia, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_garantia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Activar")
    public String Garantia_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Garantia garantia = new Garantia();
            resultado = garantia.garantia_activar(usuario_sys, id_garantia, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Insertar")
    public String Banco_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Banco banco = new Banco();
            resultado = banco.banco_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Banco_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_banco
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Modificar")
    public String Banco_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Banco banco = new Banco();
            resultado = banco.banco_modificar(usuario_sys, id_banco, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Banco_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_banco
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Eliminar")
    public String Banco_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Banco banco = new Banco();
            resultado = banco.banco_eliminar(usuario_sys, id_banco, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Banco_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_banco
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Activar")
    public String Banco_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Banco banco = new Banco();
            resultado = banco.banco_activar(usuario_sys, id_banco, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Banco_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Insertar")
    public String Estado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Judicial estado_judicial = new Estado_Judicial();
            resultado = estado_judicial.estado_judicial_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Modificar")
    public String Estado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Judicial estado_judicial = new Estado_Judicial();
            resultado = estado_judicial.estado_judicial_modificar(usuario_sys, id_sestado, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Eliminar")
    public String Estado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Judicial estado_judicial = new Estado_Judicial();
            resultado = estado_judicial.estado_judicial_eliminar(usuario_sys, id_sestado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Activar")
    public String Estado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Judicial estado_judicial = new Estado_Judicial();
            resultado = estado_judicial.estado_judicial_activar(usuario_sys, id_sestado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Insertar")
    public String Status_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Judicial estatus_judicial = new Estatus_Judicial();
            resultado = estatus_judicial.estatus_judicial_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Status_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Modificar")
    public String Status_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Judicial estatus_judicial = new Estatus_Judicial();
            resultado = estatus_judicial.estatus_judicial_modificar(usuario_sys, id_estatus, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Status_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Eliminar")
    public String Status_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Judicial estatus_judicial = new Estatus_Judicial();
            resultado = estatus_judicial.estatus_judicial_eliminar(usuario_sys, id_estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Status_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Activar")
    public String Status_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Judicial estatus_judicial = new Estatus_Judicial();
            resultado = estatus_judicial.estatus_judicial_activar(usuario_sys, id_estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Status_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Insertar")
    public String Tipo_Descuento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Descuento tipo_descuento = new Tipo_Descuento();
            resultado = tipo_descuento.tipo_descuento_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Modificar")
    public String Tipo_Descuento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Descuento tipo_descuento = new Tipo_Descuento();
            resultado = tipo_descuento.tipo_descuento_modificar(usuario_sys, id_tipo_descuento, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Eliminar")
    public String Tipo_Descuento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Descuento tipo_descuento = new Tipo_Descuento();
            resultado = tipo_descuento.tipo_descuento_eliminar(usuario_sys, id_tipo_descuento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Activar")
    public String Tipo_Descuento_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Descuento tipo_descuento = new Tipo_Descuento();
            resultado = tipo_descuento.tipo_descuento_activar(usuario_sys, id_tipo_descuento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Insertar")
    public String Tipo_Aumento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Aumento tipo_aumento = new Tipo_Aumento();
            resultado = tipo_aumento.tipo_aumento_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Modificar")
    public String Tipo_Aumento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Aumento tipo_aumento = new Tipo_Aumento();
            resultado = tipo_aumento.tipo_aumento_modificar(usuario_sys, id_tipo_aumento, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Eliminar")
    public String Tipo_Aumento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Aumento tipo_aumento = new Tipo_Aumento();
            resultado = tipo_aumento.tipo_aumento_eliminar(usuario_sys, id_tipo_aumento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Activar")
    public String Tipo_Aumento_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Aumento tipo_aumento = new Tipo_Aumento();
            resultado = tipo_aumento.tipo_aumento_activar(usuario_sys, id_tipo_aumento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param tipo_d
     * @param frase_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Insertar")
    public String Frase_Predeterminada_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "tipo_d") String tipo_d,
            @WebParam(name = "frase_d") String frase_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Frase_Predeterminada frase_predeterminada = new Frase_Predeterminada();
            resultado = frase_predeterminada.frase_predeterminada_insertar(usuario_sys, nombre_d, tipo_d, frase_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param nombre_d
     * @param tipo_d
     * @param frase_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Modificar")
    public String Frase_Predeterminada_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "tipo_d") String tipo_d,
            @WebParam(name = "frase_d") String frase_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Frase_Predeterminada frase_predeterminada = new Frase_Predeterminada();
            resultado = frase_predeterminada.frase_predeterminada_modificar(usuario_sys, id_frase_predeterminada, nombre_d, tipo_d, frase_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Eliminar")
    public String Frase_Predeterminada_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Frase_Predeterminada frase_predeterminada = new Frase_Predeterminada();
            resultado = frase_predeterminada.frase_predeterminada_eliminar(usuario_sys, id_frase_predeterminada, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Activar")
    public String Frase_Predeterminada_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Frase_Predeterminada frase_predeterminada = new Frase_Predeterminada();
            resultado = frase_predeterminada.frase_predeterminada_activar(usuario_sys, id_frase_predeterminada, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param codigo_d
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Insertar")
    public String Codigo_Contactabilidad_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "codigo_d") String codigo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Codigo_Resultado codigo_resultado = new Codigo_Resultado();
            resultado = codigo_resultado.codigo_resultado_insertar(usuario_sys, codigo_d, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param codigo_d
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Modificar")
    public String Codigo_Contactabilidad_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_codigo_contactabilidad") Integer id_codigo_contactabilidad,
            @WebParam(name = "codigo_d") String codigo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Codigo_Resultado codigo_resultado = new Codigo_Resultado();
            resultado = codigo_resultado.codigo_resultado_modificar(usuario_sys, id_codigo_contactabilidad, codigo_d, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Eliminar")
    public String Codigo_Contactabilidad_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Codigo_Resultado codigo_resultado = new Codigo_Resultado();
            resultado = codigo_resultado.codigo_resultado_eliminar(usuario_sys, id_codigo_contactabilidad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Activar")
    public String Codigo_Contactabilidad_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Codigo_Resultado codigo_resultado = new Codigo_Resultado();
            resultado = codigo_resultado.codigo_resultado_activar(usuario_sys, id_codigo_contactabilidad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param contacto
     * @param estado_extrajudicial
     * @param estatus_extrajudicial
     * @param estado_judicial
     * @param estatus_judicial
     * @param intencion_pago
     * @param razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Cobros_Insertar")
    public String Gestion_Cobros_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "contacto") String contacto,
            @WebParam(name = "estado_extrajudicial") Integer estado_extrajudicial,
            @WebParam(name = "estatus_extrajudicial") Integer estatus_extrajudicial,
            @WebParam(name = "estado_judicial") Integer estado_judicial,
            @WebParam(name = "estatus_judicial") Integer estatus_judicial,
            @WebParam(name = "intencion_pago") Integer intencion_pago,
            @WebParam(name = "razon_deuda") Integer razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Gestion_Cobros_Insertar(usuario_sys, deudor, usuario, codigo_contactabiliad, descripcion, contacto, estado_extrajudicial, estatus_extrajudicial, estado_judicial, estatus_judicial, intencion_pago, razon_deuda, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Cobros_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Administracion_Insertar")
    public String Gestion_Administracion_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Gestion_Administracion_Insertar(usuario_sys, deudor, usuario, codigo_contactabiliad, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Administracion_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Juridico_Insertar")
    public String Gestion_Juridico_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Gestion_Juridico_Insertar(usuario_sys, deudor, usuario, codigo_contactabiliad, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Juridico_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param banco
     * @param fecha
     * @param no_boleta
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Insertar")
    public String Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "banco") Integer banco,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_boleta") String no_boleta,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Pago pago = new Pago();
            resultado = pago.pago_insertar(usuario_sys, deudor, banco, fecha, no_boleta, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Pago_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_pago
     * @param deudor
     * @param banco
     * @param fecha
     * @param no_boleta
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Modificar")
    public String Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_pago") Integer id_pago,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "banco") Integer banco,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_boleta") String no_boleta,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Pago pago = new Pago();
            resultado = pago.pago_modificar(usuario_sys, id_pago, deudor, banco, fecha, no_boleta, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Pago_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Eliminar")
    public String Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_pago") Integer id_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Pago pago = new Pago();
            resultado = pago.pago_eliminar(usuario_sys, id_pago, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Pago_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param fecha_ingreso
     * @param fecha_pago
     * @param hora_pago
     * @param minuto_pago
     * @param estado_promesa
     * @param observacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Insertar")
    public String Promesa_Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "fecha_ingreso") Calendar fecha_ingreso,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") Integer hora_pago,
            @WebParam(name = "minuto_pago") Integer minuto_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Promesa_Pago promesa_pago = new Promesa_Pago();
            resultado = promesa_pago.promesa_pago_insertar(usuario_sys, deudor, fecha_ingreso, fecha_pago, hora_pago, minuto_pago, estado_promesa, observacion, monto, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_promesa_pago
     * @param deudor
     * @param fecha_ingreso
     * @param fecha_pago
     * @param hora_pago
     * @param minuto_pago
     * @param estado_promesa
     * @param observacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Modificar")
    public String Promesa_Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "fecha_ingreso") Calendar fecha_ingreso,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") Integer hora_pago,
            @WebParam(name = "minuto_pago") Integer minuto_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Promesa_Pago promesa_pago = new Promesa_Pago();
            resultado = promesa_pago.promesa_pago_modificar(usuario_sys, id_promesa_pago, deudor, fecha_ingreso, fecha_pago, hora_pago, minuto_pago, estado_promesa, observacion, monto, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_promesa_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Eliminar")
    public String Promesa_Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Promesa_Pago promesa_pago = new Promesa_Pago();
            resultado = promesa_pago.promesa_pago_eliminar(usuario_sys, id_promesa_pago, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_juicio
     * @param deudor
     * @param juzgado
     * @param fecha
     * @param no_juicio
     * @param monto
     * @param descripcion
     * @param modelo_arraigo
     * @param modelo_banco
     * @param modelo_finca
     * @param modelo_salario
     * @param modelo_vehiculo
     * @param modelo_otros
     * @param procurador
     * @param razon_notificacion
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param procuracion
     * @param fecha_admision_demanda
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param depositario
     * @param tiempo_estimado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juicio_Modificar")
    public String Juicio_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juicio") Integer id_juicio,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "modelo_arraigo") String[][] modelo_arraigo,
            @WebParam(name = "modelo_banco") String[][] modelo_banco,
            @WebParam(name = "modelo_finca") String[][] modelo_finca,
            @WebParam(name = "modelo_salario") String[][] modelo_salario,
            @WebParam(name = "modelo_vehiculo") String[][] modelo_vehiculo,
            @WebParam(name = "modelo_otros") String[][] modelo_otros,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "fecha_admision_demanda") Calendar fecha_admision_demanda,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "depositario") String depositario,
            @WebParam(name = "tiempo_estimado") String tiempo_estimado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juicio juicio = new Juicio();
            resultado = juicio.juicio_modificar(usuario_sys, id_juicio, deudor, juzgado, fecha, no_juicio, monto, descripcion, modelo_arraigo, modelo_banco, modelo_finca, modelo_salario, modelo_vehiculo, modelo_otros, procurador, razon_notificacion, notificador, abogado_deudor, sumario, memorial, procuracion, fecha_admision_demanda, deudor_notificado, fecha_notificacion, depositario, tiempo_estimado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_juicio
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juicio_Eliminar")
    public String Juicio_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juicio") Integer id_juicio,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Juicio juicio = new Juicio();
            resultado = juicio.juicio_eliminar(usuario_sys, id_juicio, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param tipo_descuento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Insertar")
    public String Descuento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_descuento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Descuento descuento = new Descuento();
            resultado = descuento.descuento_insertar(usuario_sys, deudor, tipo_descuento, fecha, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_descuento
     * @param deudor
     * @param tipo_descuento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Modificar")
    public String Descuento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_descuento") Integer id_descuento,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_descuento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Descuento descuento = new Descuento();
            resultado = descuento.descuento_modificar(usuario_sys, id_descuento, deudor, tipo_descuento, fecha, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Eliminar")
    public String Descuento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_descuento") Integer id_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Descuento descuento = new Descuento();
            resultado = descuento.descuento_eliminar(usuario_sys, id_descuento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param tipo_aumento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Insertar")
    public String Aumento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_aumento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Aumento aumento = new Aumento();
            resultado = aumento.aumento_insertar(usuario_sys, deudor, tipo_aumento, fecha, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_aumento
     * @param deudor
     * @param tipo_aumento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Modificar")
    public String Aumento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_aumento") Integer id_aumento,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_aumento") Integer tipo_aumento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Aumento aumento = new Aumento();
            resultado = aumento.aumento_modificar(usuario_sys, id_aumento, deudor, tipo_aumento, fecha, monto, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Eliminar")
    public String Aumento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_aumento") Integer id_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Aumento aumento = new Aumento();
            resultado = aumento.aumento_eliminar(usuario_sys, id_aumento, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Insertar")
    public String Fiador_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Fiador fiador = new Fiador();
            resultado = fiador.fiador_insertar(usuario_sys, deudor, dpi, nit, fecha_nacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estado_civil, profesion, correo_electronico, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_fiador
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Modificar")
    public String Fiador_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_fiador") Integer id_fiador,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Fiador fiador = new Fiador();
            resultado = fiador.fiador_modificar(usuario_sys, id_fiador, deudor, dpi, nit, fecha_nacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estado_civil, profesion, correo_electronico, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_fiador
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Eliminar")
    public String Fiador_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_fiador") Integer id_fiador,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Fiador fiador = new Fiador();
            resultado = fiador.fiador_eliminar(usuario_sys, id_fiador, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Insertar")
    public String Referencia_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Referencia referencia = new Referencia();
            resultado = referencia.referencia_insertar(usuario_sys, deudor, dpi, nit, fecha_nacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estado_civil, profesion, correo_electronico, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_referencia
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Modificar")
    public String Referencia_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_referencia") Integer id_referencia,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Referencia referencia = new Referencia();
            resultado = referencia.referencia_modificar(usuario_sys, id_referencia, deudor, dpi, nit, fecha_nacimiento, nombre, nacionalidad, telefono, direccion, zona, pais, departamento, sexo, estado_civil, profesion, correo_electronico, descripcion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_referencia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Eliminar")
    public String Referencia_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_referencia") Integer id_referencia,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Referencia referencia = new Referencia();
            resultado = referencia.referencia_eliminar(usuario_sys, id_referencia, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param actor
     * @param moneda
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param telefono_casa
     * @param telefono_celular
     * @param direccion
     * @param fecha_recepcion
     * @param correo_electronico
     * @param lugar_trabajo
     * @param direccion_trabajo
     * @param telefono_trabajo
     * @param monto_inicial
     * @param gestor
     * @param sestado
     * @param estatus
     * @param no_cuenta
     * @param garantia
     * @param cargado
     * @param estado
     * @param PDF
     * @param INV
     * @param MAYCOM
     * @param NITS
     * @param saldo
     * @param fecha_proximo_pago
     * @param caso
     * @param convenio_pactado
     * @param cuota_convenio
     * @param no_cuenta_otro
     * @param situacion_caso
     * @param opcion_proximo_pago
     * @param anticipo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Modificar_Deudor_Expediente")
    public String Modificar_Deudor_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "actor") Integer actor,
            @WebParam(name = "moneda") String moneda,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "telefono_casa") String telefono_casa,
            @WebParam(name = "telefono_celular") String telefono_celular,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "fecha_recepcion") Calendar fecha_recepcion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "lugar_trabajo") String lugar_trabajo,
            @WebParam(name = "direccion_trabajo") String direccion_trabajo,
            @WebParam(name = "telefono_trabajo") String telefono_trabajo,
            @WebParam(name = "monto_inicial") Double monto_inicial,
            @WebParam(name = "gestor") Integer gestor,
            @WebParam(name = "sestado") Integer sestado,
            @WebParam(name = "estatus") Integer estatus,
            @WebParam(name = "no_cuenta") String no_cuenta,
            @WebParam(name = "garantia") Integer garantia,
            @WebParam(name = "cargado") String cargado,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "PDF") String PDF,
            @WebParam(name = "INV") String INV,
            @WebParam(name = "MAYCOM") String MAYCOM,
            @WebParam(name = "NITS") String NITS,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "fecha_proximo_pago") Calendar fecha_proximo_pago,
            @WebParam(name = "caso") Integer caso,
            @WebParam(name = "convenio_pactado") String convenio_pactado,
            @WebParam(name = "cuota_convenio") Double cuota_convenio,
            @WebParam(name = "no_cuenta_otro") String no_cuenta_otro,
            @WebParam(name = "situacion_caso") String situacion_caso,
            @WebParam(name = "opcion_proximo_pago") String opcion_proximo_pago,
            @WebParam(name = "anticipo") String anticipo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente Expediente = new Expediente();
            resultado = Expediente.Modificar_Deudor_Expediente(usuario_sys, deudor, actor, moneda, dpi, nit, fecha_nacimiento, nombre, telefono_casa, telefono_celular, direccion, fecha_recepcion, correo_electronico, lugar_trabajo, direccion_trabajo, telefono_trabajo, monto_inicial, gestor, sestado, estatus, no_cuenta, garantia, cargado, estado, PDF, INV, MAYCOM, NITS, saldo, fecha_proximo_pago, caso, convenio_pactado, cuota_convenio, no_cuenta_otro, situacion_caso, opcion_proximo_pago, anticipo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Deudor_Expediente): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param juicio
     * @param procurador
     * @param juzgado
     * @param fecha
     * @param razon_notificacion
     * @param no_juicio
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param procuracion
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Modificar_Juicio_Expediente")
    public String Modificar_Juicio_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "juicio") Integer juicio,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente Expediente = new Expediente();
            resultado = Expediente.Modificar_Juicio_Expediente(usuario_sys, deudor, juicio, procurador, juzgado, fecha, razon_notificacion, no_juicio, notificador, abogado_deudor, sumario, memorial, procuracion, deudor_notificado, fecha_notificacion, monto, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Lista_Archivos_Digitalizados")
    public String[][] Lista_Archivos_Digitalizados(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado = null;
        try {
            Expediente Expediente = new Expediente();
            resultado = Expediente.Lista_Archivos_Digitalizados(usuario_sys, deudor, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Lista_Archivos_Digitalizados): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param usuario
     * @param menus_no_asignados
     * @param menus_asignados
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Usuario_Uno_Modificar")
    public String Permisos_Usuario_Uno_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menus_no_asignados") String[] menus_no_asignados,
            @WebParam(name = "menus_asignados") String[] menus_asignados,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario_entidad = new Usuario();
            resultado = usuario_entidad.Permisos_Usuario_Uno_Modificar(usuario_sys, usuario_sys, menus_no_asignados, menus_asignados, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Uno_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param usuario
     * @param menu
     * @param nuevo
     * @param modificar
     * @param eliminar
     * @param ver
     * @param activar
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Usuario_Modificar")
    public String Permisos_Usuario_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menu") Integer menu,
            @WebParam(name = "nuevo") String nuevo,
            @WebParam(name = "modificar") String modificar,
            @WebParam(name = "eliminar") String eliminar,
            @WebParam(name = "activar") String activar,
            @WebParam(name = "ver") String ver,
            @WebParam(name = "poolConexion") String poolConexion) {
        
        String resultado = "";
        try {
            Usuario usuario_entidad = new Usuario();
            resultado = usuario_entidad.Permisos_Usuario_Modificar(usuario_sys, usuario, menu, nuevo, modificar, eliminar, activar, ver, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Insertar")
    public String EstadoExtra_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Extrajudicial estado_extrajudicial = new Estado_Extrajudicial();
            resultado = estado_extrajudicial.estado_extrajudicial_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Modificar")
    public String EstadoExtra_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Extrajudicial estado_extrajudicial = new Estado_Extrajudicial();
            resultado = estado_extrajudicial.estado_extrajudicial_modificar(usuario_sys, id_sestado, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Eliminar")
    public String EstadoExtra_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Extrajudicial estado_extrajudicial = new Estado_Extrajudicial();
            resultado = estado_extrajudicial.estado_extrajudicial_eliminar(usuario_sys, id_sestado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Activar")
    public String EstadoExtra_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Extrajudicial estado_extrajudicial = new Estado_Extrajudicial();
            resultado = estado_extrajudicial.estado_extrajudicial_activar(usuario_sys, id_sestado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Insertar")
    public String StatusExtra_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Extrajudicial estatus_extrajudicial = new Estatus_Extrajudicial();
            resultado = estatus_extrajudicial.estatus_extrajudicial_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Modificar")
    public String StatusExtra_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Extrajudicial estatus_extrajudicial = new Estatus_Extrajudicial();
            resultado = estatus_extrajudicial.estatus_extrajudicial_modificar(usuario_sys, id_estatus, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Eliminar")
    public String StatusExtra_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Extrajudicial estatus_extrajudicial = new Estatus_Extrajudicial();
            resultado = estatus_extrajudicial.estatus_extrajudicial_eliminar(usuario_sys, id_estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Activar")
    public String StatusExtra_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estatus_Extrajudicial estatus_extrajudicial = new Estatus_Extrajudicial();
            resultado = estatus_extrajudicial.estatus_extrajudicial_activar(usuario_sys, id_estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param estado_d
     * @param estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Judicial")
    public String Estado_Status_Judicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estado_d") Integer estado_d,
            @WebParam(name = "estatus") Integer[] estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Estatus_Judicial estado_estatus_judicial = new Estado_Estatus_Judicial();
            resultado = estado_estatus_judicial.Estado_Status_Judicial(usuario_sys, estado_d, estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Judicial): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param estado_d
     * @param estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Extrajudicial")
    public String Estado_Status_Extrajudicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estado_d") Integer estado_d,
            @WebParam(name = "estatus") Integer[] estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Estado_Estatus_Extrajudicial estado_estatus_extrajudicial = new Estado_Estatus_Extrajudicial();
            resultado = estado_estatus_extrajudicial.Estado_Status_Extrajudicial(usuario_sys, estado_d, estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extrajudicial): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param estado_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Extra_Permite")
    public String Estado_Status_Extra_Permite(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estatus") String[] estado_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {
        
        String resultado = "";
        try {
            Estado_Estatus_Extrajudicial estado_estatus_extrajudicial = new Estado_Estatus_Extrajudicial();
            resultado = estado_estatus_extrajudicial.Estado_Status_Extra_Permite(usuario_sys, estado_estatus, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extra_Permite): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param constantes
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Constantes_Modificar")
    public String Constantes_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "constantes") String[] constantes,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Constantes constantes_entidad = new Constantes();
            resultado = constantes_entidad.Constantes_Modificar(usuario_sys, constantes, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Constantes_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Insertar")
    public String Tipo_Codigo_Resultado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado tipo_codigo_resultado = new Tipo_Codigo_Resultado();
            resultado = tipo_codigo_resultado.tipo_codigo_resultado_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Modificar")
    public String Tipo_Codigo_Resultado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado tipo_codigo_resultado = new Tipo_Codigo_Resultado();
            resultado = tipo_codigo_resultado.tipo_codigo_resultado_modificar(usuario_sys, id_tipo_codigo_contactabilidad, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Eliminar")
    public String Tipo_Codigo_Resultado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado tipo_codigo_resultado = new Tipo_Codigo_Resultado();
            resultado = tipo_codigo_resultado.tipo_codigo_resultado_eliminar(usuario_sys, id_tipo_codigo_contactabilidad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Activar")
    public String Tipo_Codigo_Resultado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado tipo_codigo_resultado = new Tipo_Codigo_Resultado();
            resultado = tipo_codigo_resultado.tipo_codigo_resultado_activar(usuario_sys, id_tipo_codigo_contactabilidad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param tipo_codigo_resultado
     * @param codigo_resultado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "TipoCodigo_Codigo_Resultado")
    public String TipoCodigo_Codigo_Resultado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "tipo_codigo_resultado") Integer tipo_codigo_resultado,
            @WebParam(name = "codigo_resultado") Integer[] codigo_resultado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado_Codigo_Resultado tipo_codigo_resultado_codigo_resultado = new Tipo_Codigo_Resultado_Codigo_Resultado();
            resultado = tipo_codigo_resultado_codigo_resultado.Tipo_Codigo_Resultado_Codigo_Resultado(usuario_sys, tipo_codigo_resultado, codigo_resultado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_usuario
     * @param contrasena_vieja
     * @param contrasena_nueva
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Reiniciar_Contrasena")
    public String Reiniciar_Contrasena(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "contrasena_vieja") String contrasena_vieja,
            @WebParam(name = "contrasena_nueva") String contrasena_nueva,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.Reiniciar_Contrasena(usuario_sys, id_usuario, contrasena_vieja, contrasena_nueva, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Reiniciar_Contrasena): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param usuario
     * @param menus_no_asignados
     * @param menus_asignados
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Rol_Modificar")
    public String Permisos_Rol_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menus_no_asignados") String[] menus_no_asignados,
            @WebParam(name = "menus_asignados") String[] menus_asignados,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Rol rol = new Rol();
            resultado = rol.Permisos_Rol_Modificar(usuario_sys, usuario, menus_no_asignados, menus_asignados, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Rol_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Insertar")
    public String Rol_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Rol rol = new Rol();
            resultado = rol.rol_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Rol_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_rol
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Modificar")
    public String Rol_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_rol") Integer id_rol,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Rol rol = new Rol();
            resultado = rol.rol_modificar(usuario_sys, id_rol, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Rol_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_rol
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Eliminar")
    public String Rol_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_rol") Integer id_rol,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Rol rol = new Rol();
            resultado = rol.rol_eliminar(usuario_sys, id_rol, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Rol_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param garantia
     * @param cargado
     * @param anticipo
     * @param saldo
     * @param pdf
     * @param inv
     * @param maycom
     * @param nits
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Caso")
    public String Guardar_Expediente_Caso(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "garantia") Integer garantia,
            @WebParam(name = "cargado") String cargado,
            @WebParam(name = "anticipo") String anticipo,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "pdf") String pdf,
            @WebParam(name = "inv") String inv,
            @WebParam(name = "maycom") String maycom,
            @WebParam(name = "nits") String nits,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Guardar_Expediente_Caso(usuario_sys, deudor, garantia, cargado, anticipo, saldo, pdf, inv, maycom, nits, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Caso): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param estado_extra
     * @param status_extra
     * @param telefono_casa
     * @param telefono_celular
     * @param correo_electronico
     * @param lugar_trabajo
     * @param contacto_trabajo
     * @param telefono_trabajo
     * @param dpi
     * @param nit
     * @param intension_pago
     * @param direccion
     * @param notas
     * @param razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Extrajudicial")
    public String Guardar_Expediente_Extrajudicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "estado_extra") Integer estado_extra,
            @WebParam(name = "status_extra") Integer status_extra,
            @WebParam(name = "telefono_casa") String telefono_casa,
            @WebParam(name = "telefono_celular") String telefono_celular,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "lugar_trabajo") String lugar_trabajo,
            @WebParam(name = "contacto_trabajo") String contacto_trabajo,
            @WebParam(name = "telefono_trabajo") String telefono_trabajo,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "intension_pago") Integer intension_pago,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "notas") String notas,
            @WebParam(name = "razon_deuda") Integer razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Guardar_Expediente_Extrajudicial(usuario_sys, deudor, estado_extra, status_extra, telefono_casa, telefono_celular, correo_electronico, lugar_trabajo, contacto_trabajo, telefono_trabajo, dpi, nit, intension_pago, direccion, notas, razon_deuda, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param estado_judicial
     * @param status_judicial
     * @param procurador
     * @param fecha_juicio
     * @param juzgado
     * @param no_juicio
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param monto_demanda
     * @param procuracion
     * @param situacion_caso
     * @param razon_notificacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Judicial")
    public String Guardar_Expediente_Judical(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "estado_judicial") Integer estado_judicial,
            @WebParam(name = "status_extra") Integer status_judicial,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "fecha_juicio") Calendar fecha_juicio,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "monto_demanda") Double monto_demanda,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "situacion_caso") String situacion_caso,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Expediente expediente = new Expediente();
            resultado = expediente.Guardar_Expediente_Judical(usuario_sys, deudor, estado_judicial, status_judicial, procurador, fecha_juicio, juzgado, no_juicio, notificador, abogado_deudor, sumario, memorial, deudor_notificado, fecha_notificacion, monto_demanda, procuracion, situacion_caso, razon_notificacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Judical): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Insertar")
    public String Cartera_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Cartera cartera = new Cartera();
            resultado = cartera.cartera_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_cartera
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Modificar")
    public String Cartera_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Cartera cartera = new Cartera();
            resultado = cartera.cartera_modificar(usuario_sys, id_cartera, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Eliminar")
    public String Cartera_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Cartera cartera = new Cartera();
            resultado = cartera.cartera_eliminar(usuario_sys, id_cartera, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Activar")
    public String Cartera_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Cartera cartera = new Cartera();
            resultado = cartera.cartera_activar(usuario_sys, id_cartera, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param permisos
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permiso_Expediente")
    public String Permiso_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") String[] permisos,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Usuario usuario = new Usuario();
            resultado = usuario.Permiso_Expediente(usuario_sys, permisos, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Permiso_Expediente): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param tipo_convenio
     * @param estado
     * @param saldo
     * @param interes
     * @param mora
     * @param gasto_otros
     * @param rebaja_interes
     * @param subtotal_pagar
     * @param costas
     * @param monto_costas
     * @param total
     * @param cuota_inicial
     * @param total_pagar
     * @param numero_cuotas
     * @param monto_cuota
     * @param frecuencia
     * @param fecha_pago_inicial
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Insertar")
    public String Convenio_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_convenio") String tipo_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "interes") Double interes,
            @WebParam(name = "mora") Double mora,
            @WebParam(name = "gasto_otros") Double gasto_otros,
            @WebParam(name = "rebaja_interes") Double rebaja_interes,
            @WebParam(name = "subtotal_pagar") Double subtotal_pagar,
            @WebParam(name = "costas") Double costas,
            @WebParam(name = "monto_costas") Double monto_costas,
            @WebParam(name = "total") Double total,
            @WebParam(name = "cuota_inicial") Double cuota_inicial,
            @WebParam(name = "total_pagar") Double total_pagar,
            @WebParam(name = "numero_cuotas") Integer numero_cuotas,
            @WebParam(name = "monto_cuota") Double monto_cuota,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "fecha_pago_inicial") Calendar fecha_pago_inicial,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Insertar(usuario_sys, deudor, tipo_convenio, estado, saldo, interes, mora, gasto_otros, rebaja_interes, subtotal_pagar, costas, monto_costas, total, cuota_inicial, total_pagar, numero_cuotas, monto_cuota, frecuencia, fecha_pago_inicial, observacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_convenio
     * @param deudor
     * @param tipo_convenio
     * @param estado
     * @param saldo
     * @param interes
     * @param mora
     * @param gasto_otros
     * @param rebaja_interes
     * @param subtotal_pagar
     * @param costas
     * @param monto_costas
     * @param total
     * @param cuota_inicial
     * @param total_pagar
     * @param numero_cuotas
     * @param monto_cuota
     * @param frecuencia
     * @param fecha_pago_inicial
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Modificar")
    public String Convenio_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_convenio") String tipo_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "interes") Double interes,
            @WebParam(name = "mora") Double mora,
            @WebParam(name = "gasto_otros") Double gasto_otros,
            @WebParam(name = "rebaja_interes") Double rebaja_interes,
            @WebParam(name = "subtotal_pagar") Double subtotal_pagar,
            @WebParam(name = "costas") Double costas,
            @WebParam(name = "monto_costas") Double monto_costas,
            @WebParam(name = "total") Double total,
            @WebParam(name = "cuota_inicial") Double cuota_inicial,
            @WebParam(name = "total_pagar") Double total_pagar,
            @WebParam(name = "numero_cuotas") Integer numero_cuotas,
            @WebParam(name = "monto_cuota") Double monto_cuota,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "fecha_pago_inicial") Calendar fecha_pago_inicial,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Modificar(usuario_sys, id_convenio, deudor, tipo_convenio, estado, saldo, interes, mora, gasto_otros, rebaja_interes, subtotal_pagar, costas, monto_costas, total, cuota_inicial, total_pagar, numero_cuotas, monto_cuota, frecuencia, fecha_pago_inicial, observacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_convenio
     * @param estado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Modificar_Estado")
    public String Convenio_Modificar_Estado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Modificar_Estado(usuario_sys, id_convenio, estado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar_Estado): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_convenio
     * @param fecha_pago
     * @param hora_pago
     * @param estado_promesa
     * @param monto
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Insertar")
    public String Convenio_Detalle_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") String hora_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Detalle_Insertar(usuario_sys, id_convenio, fecha_pago, hora_pago, estado_promesa, monto, observacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_convenio
     * @param id_promesa_pago
     * @param fecha_pago
     * @param hora_pago
     * @param estado_promesa
     * @param monto
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Modificar")
    public String Convenio_Detalle_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") String hora_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Detalle_Modificar(usuario_sys, id_convenio, id_promesa_pago, fecha_pago, hora_pago, estado_promesa, monto, observacion, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_convenio
     * @param id_promesa_pago
     * @param estado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Modificar_Estado")
    public String Convenio_Detalle_Modificar_Estado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Convenio convenio = new Convenio();
            resultado = convenio.Convenio_Detalle_Modificar_Estado(usuario_sys, id_convenio, id_promesa_pago, estado, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar_Estado): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Insertar")
    public String Intencion_Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Intencion_Pago intencion_pago = new Intencion_Pago();
            resultado = intencion_pago.intencion_pago_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_intencion_pago
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Modificar")
    public String Intencion_Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Intencion_Pago intencion_pago = new Intencion_Pago();
            resultado = intencion_pago.intencion_pago_modificar(usuario_sys, id_intencion_pago, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_intencion_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Eliminar")
    public String Intencion_Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Intencion_Pago intencion_pago = new Intencion_Pago();
            resultado = intencion_pago.intencion_pago_eliminar(usuario_sys, id_intencion_pago, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_intencion_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Activar")
    public String Intencion_Pago_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Intencion_Pago intencion_pago = new Intencion_Pago();
            resultado = intencion_pago.intencion_pago_activar(usuario_sys, id_intencion_pago, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Insertar")
    public String Razon_Deuda_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Razon_Deuda razon_deuda = new Razon_Deuda();
            resultado = razon_deuda.razon_deuda_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Insertar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_razon_deuda
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Modificar")
    public String Razon_Deuda_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Razon_Deuda razon_deuda = new Razon_Deuda();
            resultado = razon_deuda.razon_deuda_modificar(usuario_sys, id_razon_deuda, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Modificar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Eliminar")
    public String Razon_Deuda_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Razon_Deuda razon_deuda = new Razon_Deuda();
            resultado = razon_deuda.razon_deuda_eliminar(usuario_sys, id_razon_deuda, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Eliminar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Activar")
    public String Razon_Deuda_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Razon_Deuda razon_deuda = new Razon_Deuda();
            resultado = razon_deuda.razon_deuda_activar(usuario_sys, id_razon_deuda, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Activar): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param lst_tipo_codigo_resultado_contacto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Contacto")
    public String Tipo_Codigo_Resultado_Contacto(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "lst_tipo_codigo_resultado_contacto") String[] lst_tipo_codigo_resultado_contacto,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";
        try {
            Tipo_Codigo_Resultado_Codigo_Resultado_Contacto tipo_codigo_resultado_codigo_resultado_contacto = new Tipo_Codigo_Resultado_Codigo_Resultado_Contacto();
            resultado = tipo_codigo_resultado_codigo_resultado_contacto.Tipo_Codigo_Resultado_Contacto(usuario_sys, lst_tipo_codigo_resultado_contacto, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Contacto): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "getMonitor")
    public String[][] getMonitor(
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado = null;
        try {
            Reporte reporte = new Reporte();
            resultado = reporte.getMonitor();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(getMonitor): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Gestiones_Cobros")
    public String Carga_Gestiones_Cobros(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        try {
            Carga_Masiva carga_masiva = new Carga_Masiva();
            resultado = carga_masiva.carga_gestiones_cobros(usuario_sys, archivo, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Carga_Gestiones_Cobros): " + ex.toString());
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Antiguedad_Insertar")
    public String Antiguedad_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Antiguedad antiguedad = new Antiguedad();
            resultado = antiguedad.antiguedad_insertar(usuario_sys, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Insertar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_antiguedad
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Antiguedad_Modificar")
    public String Antiguedad_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_antiguedad,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Antiguedad antiguedad = new Antiguedad();
            resultado = antiguedad.antiguedad_modificar(usuario_sys, id_antiguedad, nombre_d, descripcion_d, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Modificar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_antiguedad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Antiguedad_Eliminar")
    public String Antiguedad_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_antiguedad") Integer id_antiguedad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Antiguedad antiguedad = new Antiguedad();
            resultado = antiguedad.antiguedad_eliminar(usuario_sys, id_antiguedad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Eliminar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param id_antiguedad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Antiguedad_Activar")
    public String Antiguedad_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_antiguedad") Integer id_antiguedad,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "";

        try {
            Antiguedad antiguedad = new Antiguedad();
            resultado = antiguedad.antiguedad_activar(usuario_sys, id_antiguedad, poolConexion);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Activar): " + ex.toString());
            resultado = ex.toString();
        }

        return resultado;
    }

}
