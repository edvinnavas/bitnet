package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    public Pago() {

    }

    public String pago_insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer banco,
            Calendar fecha,
            String no_boleta,
            Double monto,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into pago (deudor, banco, fecha, no_boleta, monto, descripcion, fecha_registro) values ('"
                    + deudor.toString() + "','"
                    + banco.toString() + "','"
                    + fecha_pago + "','"
                    + no_boleta + "','"
                    + monto.toString() + "','"
                    + descripcion + "',"
                    + "CURRENT_DATE()" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Pago:= Deudor: " + deudor.toString() + " Banco: " + banco.toString() + " Fecha pago: " + fecha_pago + " No Boleta: " + no_boleta + " Monto: " + monto.toString() + " Descripcion: " + descripcion + "',"
                    + "84" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String pago_modificar(
            Integer usuario_sys,
            Integer id_pago,
            Integer deudor,
            Integer banco,
            Calendar fecha,
            String no_boleta,
            Double monto,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update pago set "
                    + "deudor='" + deudor.toString() + "', "
                    + "banco='" + banco.toString() + "', "
                    + "fecha='" + fecha_pago + "', "
                    + "no_boleta='" + no_boleta + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where pago=" + id_pago.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Pago: " + id_pago + " Deudor: " + deudor.toString() + " Banco: " + banco.toString() + " Fecha pago: " + fecha_pago + " No Boleta: " + no_boleta + " Monto: " + monto.toString() + " Descripcion: " + descripcion + "',"
                    + "85" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String pago_eliminar(
            Integer usuario_sys,
            Integer id_pago,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from pago where pago=" + id_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "PAGO: " + id_pago + "',"
                    + "86" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
