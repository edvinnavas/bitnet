package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import jcifs.smb.SmbFile;

public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;

    public Expediente() {

    }

    public String Gestion_Cobros_Insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer usuario,
            Integer codigo_contactabiliad,
            String descripcion,
            String contacto,
            Integer estado_extrajudicial,
            Integer estatus_extrajudicial,
            Integer estado_judicial,
            Integer estatus_judicial,
            Integer intencion_pago,
            Integer razon_deuda,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_cobros (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion, contacto) values ("
                    + deudor + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ","
                    + usuario + ","
                    + codigo_contactabiliad + ",'"
                    + descripcion + "','"
                    + contacto + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            // **************************** ACTUALIZAR DEUDOR ****************************
            cadenasql = "update deudor set "
                    + "codigo_contactabilidad=" + codigo_contactabiliad + ", "
                    + "sestado=" + estado_judicial + ", "
                    + "estatus=" + estatus_judicial + ", "
                    + "sestado_extra=" + estado_extrajudicial + ", "
                    + "estatus_extra=" + estatus_extrajudicial + ", "
                    + "intencion_pago=" + intencion_pago + ", "
                    + "razon_deuda=" + razon_deuda + " "
                    + "where deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Cobros Insertar: Codigo Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "81" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL ****************************
            String nombre_deudor = "";
            cadenasql = "select d.nombre from deudor d where d.deudor=" + deudor;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                nombre_deudor = rs.getString(1);
            }
            rs.close();
            stmt.close();
            stmt.close();

            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            cadenasql = "select "
                    + "d.sestado, "
                    + "s.nombre, "
                    + "d.estatus, "
                    + "e.nombre, "
                    + "d.sestado_extra, "
                    + "sx.nombre, "
                    + "d.estatus_extra, "
                    + "ex.nombre "
                    + "from "
                    + "deudor d "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra sx on (d.sestado_extra=sx.sestado_extra) "
                    + "left join estatus_extra ex on (d.estatus_extra=ex.estatus_extra) where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                int_estado_judicial_actual = rs.getInt(1);
                str_estado_judicial_actual = rs.getString(2);
                int_status_judicial_actual = rs.getInt(3);
                str_status_judicial_actual = rs.getString(4);
                int_estado_extrajudicial_actual = rs.getInt(5);
                str_estado_extrajudicial_actual = rs.getString(6);
                int_status_extrajudicial_actual = rs.getInt(7);
                str_status_extrajudicial_actual = rs.getString(8);
            }
            rs.close();
            stmt.close();
            stmt.close();

            String nombre_usuario = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                nombre_usuario = rs.getString(1);
            }
            rs.close();
            stmt.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON ****************************
            if (!(estado_judicial == int_estado_judicial_actual && estatus_judicial == int_status_judicial_actual)) {
                String str_sestado_judicial_nuevo = "";
                cadenasql = "select s.nombre from sestado s where s.sestado=" + estado_judicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                String str_estatus_judicial_nuevo = "";
                cadenasql = "select e.nombre from estatus e where e.estatus=" + estatus_judicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                cadenasql = "insert into historial_estatus ("
                        + "fecha, "
                        + "estatus, "
                        + "nombre_estatus, "
                        + "sestado, "
                        + "nombre_sestado, "
                        + "antiguo_estatus, "
                        + "antiguo_nombre_estatus, "
                        + "antiguo_sestado, "
                        + "antiguo_nombre_sestado, "
                        + "deudor, "
                        + "deudor_nombre,"
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + estatus_judicial + "','"
                        + str_estatus_judicial_nuevo + "','"
                        + estado_judicial + "','"
                        + str_sestado_judicial_nuevo + "','"
                        + int_status_judicial_actual.toString() + "','"
                        + str_status_judicial_actual + "','"
                        + int_estado_judicial_actual.toString() + "','"
                        + str_estado_judicial_actual + "','"
                        + deudor + "','"
                        + nombre_deudor + "','"
                        + usuario + "','"
                        + nombre_usuario + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            // ******************************* INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON *******************************
            if (!(estado_extrajudicial == int_estado_extrajudicial_actual && estatus_extrajudicial == int_status_extrajudicial_actual)) {
                String str_sestado_extrajudicial_nuevo = "";
                cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + estado_extrajudicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                String str_estatus_extrajudicial_nuevo = "";
                cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + estatus_extrajudicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                cadenasql = "insert into historial_estatus_extra ("
                        + "fecha, "
                        + "estatus_extra, "
                        + "nombre_estatus_extra, "
                        + "sestado_extra, "
                        + "nombre_sestado_extra, "
                        + "antiguo_estatus_extra, "
                        + "antiguo_nombre_estatus_extra, "
                        + "antiguo_sestado_extra, "
                        + "antiguo_nombre_sestado_extra, "
                        + "deudor, "
                        + "deudor_nombre, "
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + estatus_extrajudicial + "','"
                        + str_estatus_extrajudicial_nuevo + "','"
                        + estado_extrajudicial + "','"
                        + str_sestado_extrajudicial_nuevo + "','"
                        + int_status_extrajudicial_actual.toString() + "','"
                        + str_status_extrajudicial_actual + "','"
                        + int_estado_extrajudicial_actual.toString() + "','"
                        + str_estado_extrajudicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario + "','"
                        + nombre_usuario + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión de cobro registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Cobros_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Cobros_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Gestion_Administracion_Insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer usuario,
            Integer codigo_contactabiliad,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_administrativo (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                    + deudor.toString() + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + usuario.toString() + "','"
                    + codigo_contactabiliad.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Adminstración Insertar: Codigo Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "82" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión administrativa registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Administracion_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Administracion_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Gestion_Juridico_Insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer usuario,
            Integer codigo_contactabiliad,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_juridico (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                    + deudor.toString() + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + usuario.toString() + "','"
                    + codigo_contactabiliad.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Jurídico Insertar: Código Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "83" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión jurídica registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Juridico_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Juridico_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Modificar_Deudor_Expediente(
            Integer usuario_sys,
            Integer deudor,
            Integer actor,
            String moneda,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String telefono_casa,
            String telefono_celular,
            String direccion,
            Calendar fecha_recepcion,
            String correo_electronico,
            String lugar_trabajo,
            String direccion_trabajo,
            String telefono_trabajo,
            Double monto_inicial,
            Integer gestor,
            Integer sestado,
            Integer estatus,
            String no_cuenta,
            Integer garantia,
            String cargado,
            String estado,
            String PDF,
            String INV,
            String MAYCOM,
            String NITS,
            Double saldo,
            Calendar fecha_proximo_pago,
            Integer caso,
            String convenio_pactado,
            Double cuota_convenio,
            String no_cuenta_otro,
            String situacion_caso,
            String opcion_proximo_pago,
            String anticipo,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha_nac = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_recepcion.get(Calendar.DATE);
            mes = fecha_recepcion.get(Calendar.MONTH) + 1;
            ano = fecha_recepcion.get(Calendar.YEAR);
            String fecha_rec = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_proximo_pago.get(Calendar.DATE);
            mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
            ano = fecha_proximo_pago.get(Calendar.YEAR);
            String fecha_prox_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            if (opcion_proximo_pago.equals("NO")) {
                cuota_convenio = 0.00;
                fecha_prox_pago = "1900/01/01";
            }

            String cadenasql = "update deudor set "
                    + "actor='" + actor + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "fecha_nacimiento='" + fecha_nac + "', "
                    + "nombre='" + nombre + "', "
                    + "telefono_casa='" + telefono_casa + "', "
                    + "telefono_celular='" + telefono_celular + "', "
                    + "direccion='" + direccion + "', "
                    + "moneda='" + moneda + "', "
                    + "fecha_recepcion='" + fecha_rec + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "lugar_trabajo='" + lugar_trabajo + "', "
                    + "direccion_trabajo='" + direccion_trabajo + "', "
                    + "telefono_trabajo='" + telefono_trabajo + "', "
                    + "monto_inicial='" + monto_inicial + "', "
                    + "usuario='" + gestor + "', "
                    + "sestado='" + sestado + "', "
                    + "estatus='" + estatus + "', "
                    + "no_cuenta='" + no_cuenta + "', "
                    + "garantia='" + garantia + "', "
                    + "cargado='" + cargado + "', "
                    + "PDF='" + PDF + "', "
                    + "INV='" + INV + "', "
                    + "MAYCOM='" + MAYCOM + "', "
                    + "NITS='" + NITS + "', "
                    + "saldo='" + saldo + "', "
                    + "caso='" + caso + "', "
                    + "convenio_pactado='" + convenio_pactado + "', "
                    + "cuota_convenio='" + cuota_convenio + "', "
                    + "no_cuenta_otro='" + no_cuenta_otro + "', "
                    + "situacion_caso='" + situacion_caso + "', "
                    + "opcion_proximo_pago='" + opcion_proximo_pago + "', "
                    + "fecha_proximo_pago='" + fecha_prox_pago + "', "
                    + "anticipo='" + anticipo + "' "
                    + "where deudor=" + deudor.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Expediente Deudor Modificar=> Deudor: " + deudor + " Actor: " + actor + " Moneda: " + moneda + " dpi:" + dpi + " nit:" + nit + " PDF: " + PDF + " INV: " + INV + " MAYCOM: " + MAYCOM + " NITS: " + NITS + " Anticipo: " + anticipo + "',"
                    + "105" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "EXITO";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Deudor_Expediente): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Deudor_Expediente - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Modificar_Juicio_Expediente(
            Integer usuario_sys,
            Integer deudor,
            Integer juicio,
            Integer procurador,
            Integer juzgado,
            Calendar fecha,
            String razon_notificacion,
            String no_juicio,
            Integer notificador,
            String abogado_deudor,
            String sumario,
            Calendar memorial,
            String procuracion,
            String deudor_notificado,
            Calendar fecha_notificacion,
            Double monto,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_juicio = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = memorial.get(Calendar.DATE);
            mes = memorial.get(Calendar.MONTH) + 1;
            ano = memorial.get(Calendar.YEAR);
            String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_notificacion.get(Calendar.DATE);
            mes = fecha_notificacion.get(Calendar.MONTH) + 1;
            ano = fecha_notificacion.get(Calendar.YEAR);
            String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update juicio set "
                    + "deudor='" + deudor + "', "
                    + "juzgado='" + juzgado + "', "
                    + "fecha='" + fecha_juicio + "', "
                    + "no_juicio='" + no_juicio + "', "
                    + "procurador='" + procurador.toString() + "', "
                    + "razon_notificacion='" + razon_notificacion + "', "
                    + "notificador='" + notificador.toString() + "', "
                    + "abogado_deudor='" + abogado_deudor + "', "
                    + "sumario='" + sumario + "', "
                    + "procuracion='" + procuracion + "', "
                    + "deudor_notificado='" + deudor_notificado + "', "
                    + "fecha_notificacion='" + fecha_noti + "', "
                    + "memorial='" + fecha_memorial + "', "
                    + "monto='" + monto + "' "
                    + "where juicio=" + juicio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Expediente Juicio Modificar=>  Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " Procuración: " + procuracion + "',"
                    + "106" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "EXITO";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String[][] Lista_Archivos_Digitalizados(
            Integer usuario_sys,
            Integer deudor,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String[][] resultado = null;

        try {
            String servidor = "";
            String cadenasql_1 = "select c.valor from constantes c where c.constantes=2";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql_1);
            while (rs.next()) {
                servidor = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String carpeta = "";
            String cadenasql_2 = "select a.digitalizados from actor a left join deudor d on (a.actor=d.actor) where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql_2);
            while (rs.next()) {
                carpeta = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String caso = "";
            String cadenasql_3 = "select d.caso from deudor d where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql_3);
            while (rs.next()) {
                caso = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String Directorio = servidor + "/" + carpeta + "/" + caso + "/";
            SmbFile f = new SmbFile(Directorio);
            if (f.exists()) {
                SmbFile[] ficheros = f.listFiles();
                resultado = new String[ficheros.length][3];
                for (Integer i = 0; i < ficheros.length; i++) {
                    resultado[i][0] = i.toString();
                    resultado[i][1] = ficheros[i].getName();
                    resultado[i][2] = ficheros[i].getPath();
                }
            } else {
                resultado = new String[1][1];
                resultado[0][0] = "*** ERROR: NO EXISTE DIRECTORIO ***";
            }
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente): " + ex.toString());
                conn.rollback();
                resultado[0][0] = "ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente - rollback): " + ex1.toString());
                resultado[0][0] = "ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente - rollback): " + ex.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Guardar_Expediente_Caso(
            Integer usuario_sys,
            Integer deudor,
            Integer garantia,
            String cargado,
            String anticipo,
            Double saldo,
            String pdf,
            String inv,
            String maycom,
            String nits,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            cargado = driver.quitar_simbolos(cargado);
            anticipo = driver.quitar_simbolos(anticipo);
            pdf = driver.quitar_simbolos(pdf);
            inv = driver.quitar_simbolos(inv);
            maycom = driver.quitar_simbolos(maycom);
            nits = driver.quitar_simbolos(nits);

            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "garantia=" + garantia + ", "
                    + "cargado='" + cargado + "', "
                    + "anticipo='" + anticipo + "', "
                    + "saldo=" + saldo + ", "
                    + "pdf='" + pdf + "', "
                    + "inv='" + inv + "', "
                    + "maycom='" + maycom + "', "
                    + "nits='" + nits + "' "
                    + "where "
                    + "deudor=" + deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "|Garantía: " + garantia + "|Cargado: " + cargado + "|Anticipo: " + anticipo + "|Saldo: " + saldo + "|PDF: " + pdf + "|INV: " + inv + "|MAYCOM: " + maycom + "|NITS: " + nits + "',"
                    + "134" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Caso guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Caso): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Caso - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Guardar_Expediente_Extrajudicial(
            Integer usuario_sys,
            Integer deudor,
            Integer estado_extra,
            Integer status_extra,
            String telefono_casa,
            String telefono_celular,
            String correo_electronico,
            String lugar_trabajo,
            String contacto_trabajo,
            String telefono_trabajo,
            String dpi,
            String nit,
            Integer intension_pago,
            String direccion,
            String notas,
            Integer razon_deuda,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            telefono_casa = driver.quitar_simbolos(telefono_casa);
            telefono_celular = driver.quitar_simbolos(telefono_celular);
            correo_electronico = driver.quitar_simbolos(correo_electronico);
            lugar_trabajo = driver.quitar_simbolos(lugar_trabajo);
            contacto_trabajo = driver.quitar_simbolos(contacto_trabajo);
            telefono_trabajo = driver.quitar_simbolos(telefono_trabajo);
            dpi = driver.quitar_simbolos(dpi);
            nit = driver.quitar_simbolos(nit);
            direccion = driver.quitar_simbolos(direccion);
            notas = driver.quitar_simbolos(notas);

            conn.setAutoCommit(false);

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            String nombre_deudor = "";
            String cadenasql = "select "
                    + "d.sestado, "
                    + "s.nombre, "
                    + "d.estatus, "
                    + "e.nombre, "
                    + "d.sestado_extra, "
                    + "sx.nombre, "
                    + "d.estatus_extra, "
                    + "ex.nombre, "
                    + "d.nombre "
                    + "from "
                    + "deudor d "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra sx on (d.sestado_extra=sx.sestado_extra) "
                    + "left join estatus_extra ex on (d.estatus_extra=ex.estatus_extra) where d.deudor=" + deudor.toString();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                int_estado_judicial_actual = rs.getInt(1);
                str_estado_judicial_actual = rs.getString(2);
                int_status_judicial_actual = rs.getInt(3);
                str_status_judicial_actual = rs.getString(4);
                int_estado_extrajudicial_actual = rs.getInt(5);
                str_estado_extrajudicial_actual = rs.getString(6);
                int_status_extrajudicial_actual = rs.getInt(7);
                str_status_extrajudicial_actual = rs.getString(8);
                nombre_deudor = rs.getString(9);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set "
                    + "sestado_extra=" + estado_extra + ", "
                    + "estatus_extra=" + status_extra + ", "
                    + "telefono_casa='" + telefono_casa + "', "
                    + "telefono_celular='" + telefono_celular + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "lugar_trabajo='" + lugar_trabajo + "', "
                    + "direccion_trabajo='" + contacto_trabajo + "', "
                    + "telefono_trabajo='" + telefono_trabajo + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "intencion_pago=" + intension_pago + ", "
                    + "direccion='" + direccion + "', "
                    + "descripcion='" + notas + "', "
                    + "razon_deuda=" + razon_deuda + " "
                    + "where "
                    + "deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            String usuario_nombre = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
            if (!(estado_extra == int_estado_extrajudicial_actual && status_extra == int_status_extrajudicial_actual)) {
                String str_sestado_extrajudicial_nuevo = "";
                cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + estado_extra.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                String str_estatus_extrajudicial_nuevo = "";
                cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + status_extra.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "insert into historial_estatus_extra ("
                        + "fecha, "
                        + "estatus_extra, "
                        + "nombre_estatus_extra, "
                        + "sestado_extra, "
                        + "nombre_sestado_extra, "
                        + "antiguo_estatus_extra, "
                        + "antiguo_nombre_estatus_extra, "
                        + "antiguo_sestado_extra, "
                        + "antiguo_nombre_sestado_extra, "
                        + "deudor, "
                        + "deudor_nombre, "
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + status_extra.toString() + "','"
                        + str_estatus_extrajudicial_nuevo + "','"
                        + estado_extra.toString() + "','"
                        + str_sestado_extrajudicial_nuevo + "','"
                        + int_status_extrajudicial_actual.toString() + "','"
                        + str_status_extrajudicial_actual + "','"
                        + int_estado_extrajudicial_actual.toString() + "','"
                        + str_estado_extrajudicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "|Estado Extrajudicial: " + estado_extra + "|Status Extrajudicial: " + status_extra + "|Teléfono casa: " + telefono_casa + "|Teléfono celular: " + telefono_celular + "|Correo electrónico: " + correo_electronico + "|Lugar de Trabajo: " + lugar_trabajo + "|Contacto Trabajo: " + contacto_trabajo + "|Teléfono Trabajo: " + telefono_trabajo + "|DPI: " + dpi + "|NIT: " + nit + "|Intensión pago: " + intension_pago + "|Dirección: " + direccion + "|Notas: " + notas + "|Razón deuda: " + razon_deuda + "',"
                    + "135" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Guardar_Expediente_Judical(
            Integer usuario_sys,
            Integer deudor,
            Integer estado_judicial,
            Integer status_judicial,
            Integer procurador,
            Calendar fecha_juicio,
            Integer juzgado,
            String no_juicio,
            Integer notificador,
            String abogado_deudor,
            String sumario,
            Calendar memorial,
            String deudor_notificado,
            Calendar fecha_notificacion,
            Double monto_demanda,
            String procuracion,
            String situacion_caso,
            String razon_notificacion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            no_juicio = driver.quitar_simbolos(no_juicio);
            abogado_deudor = driver.quitar_simbolos(abogado_deudor);
            sumario = driver.quitar_simbolos(sumario);
            deudor_notificado = driver.quitar_simbolos(deudor_notificado);
            procuracion = driver.quitar_simbolos(procuracion);
            situacion_caso = driver.quitar_simbolos(situacion_caso);
            razon_notificacion = driver.quitar_simbolos(razon_notificacion);
            //Modo transaccion.
            conn.setAutoCommit(false);

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            String nombre_deudor = "";
            String cadenasql = "select "
                    + "d.sestado, "
                    + "s.nombre, "
                    + "d.estatus, "
                    + "e.nombre, "
                    + "d.sestado_extra, "
                    + "sx.nombre, "
                    + "d.estatus_extra, "
                    + "ex.nombre, "
                    + "d.nombre "
                    + "from "
                    + "deudor d "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra sx on (d.sestado_extra=sx.sestado_extra) "
                    + "left join estatus_extra ex on (d.estatus_extra=ex.estatus_extra) where d.deudor=" + deudor.toString();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                int_estado_judicial_actual = rs.getInt(1);
                str_estado_judicial_actual = rs.getString(2);
                int_status_judicial_actual = rs.getInt(3);
                str_status_judicial_actual = rs.getString(4);
                int_estado_extrajudicial_actual = rs.getInt(5);
                str_estado_extrajudicial_actual = rs.getString(6);
                int_status_extrajudicial_actual = rs.getInt(7);
                str_status_extrajudicial_actual = rs.getString(8);
                nombre_deudor = rs.getString(9);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set "
                    + "sestado=" + estado_judicial + ", "
                    + "estatus=" + status_judicial + ", "
                    + "situacion_caso='" + situacion_caso + "' "
                    + "where "
                    + "deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select j.juicio from juicio j where j.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            Integer id_juicio = 0;
            while (rs.next()) {
                id_juicio = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            Integer dia = fecha_juicio.get(Calendar.DATE);
            Integer mes = fecha_juicio.get(Calendar.MONTH) + 1;
            Integer ano = fecha_juicio.get(Calendar.YEAR);
            String fecha_juicio_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_notificacion.get(Calendar.DATE);
            mes = fecha_notificacion.get(Calendar.MONTH) + 1;
            ano = fecha_notificacion.get(Calendar.YEAR);
            String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = memorial.get(Calendar.DATE);
            mes = memorial.get(Calendar.MONTH) + 1;
            ano = memorial.get(Calendar.YEAR);
            String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            cadenasql = "update juicio set "
                    + "juzgado=" + juzgado + ", "
                    + "fecha='" + fecha_juicio_t + "', "
                    + "no_juicio='" + no_juicio + "', "
                    + "procurador=" + procurador + ", "
                    + "razon_notificacion='" + razon_notificacion + "', "
                    + "notificador=" + notificador + ", "
                    + "abogado_deudor='" + abogado_deudor + "', "
                    + "sumario='" + sumario + "', "
                    + "procuracion='" + procuracion + "', "
                    + "deudor_notificado='" + deudor_notificado + "', "
                    + "fecha_notificacion='" + fecha_noti + "', "
                    + "memorial='" + fecha_memorial + "', "
                    + "monto=" + monto_demanda + " "
                    + "where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            String usuario_nombre = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
            if (!(estado_judicial == int_estado_judicial_actual && status_judicial == int_status_judicial_actual)) {
                String str_sestado_judicial_nuevo = "";
                cadenasql = "select s.nombre from sestado s where s.sestado=" + estado_judicial.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                String str_estatus_judicial_nuevo = "";
                cadenasql = "select e.nombre from estatus e where e.estatus=" + status_judicial.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "insert into historial_estatus ("
                        + "fecha, "
                        + "estatus, "
                        + "nombre_estatus, "
                        + "sestado, "
                        + "nombre_sestado, "
                        + "antiguo_estatus, "
                        + "antiguo_nombre_estatus, "
                        + "antiguo_sestado, "
                        + "antiguo_nombre_sestado, "
                        + "deudor, "
                        + "deudor_nombre,"
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + status_judicial.toString() + "','"
                        + str_estatus_judicial_nuevo + "','"
                        + estado_judicial.toString() + "','"
                        + str_sestado_judicial_nuevo + "','"
                        + int_status_judicial_actual.toString() + "','"
                        + str_status_judicial_actual + "','"
                        + int_estado_judicial_actual.toString() + "','"
                        + str_estado_judicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "Juicio: " + id_juicio + "|Estado Judicial: " + estado_judicial + "|Status Judicial: " + status_judicial + "|Situación caso: " + situacion_caso + "|Juzgado: " + juzgado + "|Fecha juicio: " + fecha_juicio_t + "|No. juicio: " + no_juicio + "|Procurador: " + procurador + "|Razon notificación: " + razon_notificacion + "|Notificador: " + notificador + "|Abogado deudor: " + abogado_deudor + "|Sumario: " + sumario + "|Procuracion: " + procuracion + "|Deudor notificado: " + deudor_notificado + "|Fecha notificación: " + fecha_noti + "|Memorial: " + fecha_memorial + "|Monto demanda: " + monto_demanda + "',"
                    + "136" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Judicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
