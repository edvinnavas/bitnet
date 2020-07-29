package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Aumento implements Serializable {

    private static final long serialVersionUID = 1L;

    public Aumento() {

    }

    public String aumento_insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer tipo_aumento,
            Calendar fecha,
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
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into aumento (deudor, tipo_aumento, fecha, monto, descripcion) values ('"
                    + deudor.toString() + "','"
                    + tipo_aumento.toString() + "','"
                    + fecha_i + "','"
                    + monto.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Aumento:= Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "96" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String aumento_modificar(
            Integer usuario_sys,
            Integer id_aumento,
            Integer deudor,
            Integer tipo_aumento,
            Calendar fecha,
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
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update aumento set "
                    + "deudor='" + deudor.toString() + "', "
                    + "tipo_aumento='" + tipo_aumento.toString() + "', "
                    + "fecha='" + fecha_i + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where aumento=" + id_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Aumento: " + id_aumento + " Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "97" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String aumento_eliminar(
            Integer usuario_sys,
            Integer id_aumento,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from aumento where aumento=" + id_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "AUMENTO: " + id_aumento + "',"
                    + "98" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
