package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Promesa_Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    public Promesa_Pago() {

    }

    public String promesa_pago_insertar(
            Integer usuario_sys,
            Integer deudor,
            Calendar fecha_ingreso,
            Calendar fecha_pago,
            Integer hora_pago,
            Integer minuto_pago,
            String estado_promesa,
            String observacion,
            Double monto,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_ingreso.get(Calendar.DATE);
            Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
            Integer ano = fecha_ingreso.get(Calendar.YEAR);
            String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_pago.get(Calendar.DATE);
            mes = fecha_pago.get(Calendar.MONTH) + 1;
            ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into promesa_pago (fecha_ingreso, fecha_pago, hora_pago, estado_promesa, observacion, monto, deudor) values ('"
                    + fecha_ingreso_t + "','"
                    + fecha_pago_t + "','"
                    + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "','"
                    + estado_promesa + "','"
                    + observacion + "','"
                    + monto + "','"
                    + deudor.toString() + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa Pago:= Deudor: " + deudor.toString() + " Fecha ingreso: " + fecha_ingreso + " Fecha pago: " + fecha_pago_t + " Hora pago: " + hora_pago.toString() + " Estado Promesa: " + estado_promesa + " Observacion: " + observacion + " Monto: " + "',"
                    + "87" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String promesa_pago_modificar(
            Integer usuario_sys,
            Integer id_promesa_pago,
            Integer deudor,
            Calendar fecha_ingreso,
            Calendar fecha_pago,
            Integer hora_pago,
            Integer minuto_pago,
            String estado_promesa,
            String observacion,
            Double monto,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_ingreso.get(Calendar.DATE);
            Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
            Integer ano = fecha_ingreso.get(Calendar.YEAR);
            String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_pago.get(Calendar.DATE);
            mes = fecha_pago.get(Calendar.MONTH) + 1;
            ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update promesa_pago set "
                    + "deudor='" + deudor.toString() + "', "
                    + "fecha_ingreso='" + fecha_ingreso_t + "', "
                    + "fecha_pago='" + fecha_pago_t + "', "
                    + "hora_pago='" + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "', "
                    + "estado_promesa='" + estado_promesa + "', "
                    + "observacion='" + observacion + "', "
                    + "monto='" + monto + "' "
                    + "where promesa_pago=" + id_promesa_pago.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Promesa_Pago: " + id_promesa_pago + " Deudor: " + deudor.toString() + " Fecha ingreso: " + fecha_ingreso + " Fecha pago: " + fecha_pago_t + " Hora pago: " + hora_pago.toString() + " Estado Promesa: " + estado_promesa + " Observacion: " + observacion + " Monto: " + "',"
                    + "88" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String promesa_pago_eliminar(
            Integer usuario_sys,
            Integer id_promesa_pago,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from promesa_pago where promesa_pago=" + id_promesa_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "PROMESA_PAGO: " + id_promesa_pago + "',"
                    + "89" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
