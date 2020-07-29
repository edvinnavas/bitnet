package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Descuento implements Serializable {

    private static final long serialVersionUID = 1L;

    public Descuento() {

    }

    public String descuento_insertar(
            Integer usuario_sys,
            Integer deudor,
            Integer tipo_descuento,
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

            String cadenasql = "insert into descuento (deudor, tipo_descuento, fecha, monto, descripcion) values ('"
                    + deudor.toString() + "','"
                    + tipo_descuento.toString() + "','"
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
                    + "Descuento:= Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "93" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String descuento_modificar(
            Integer usuario_sys,
            Integer id_descuento,
            Integer deudor,
            Integer tipo_descuento,
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

            String cadenasql = "update descuento set "
                    + "deudor='" + deudor.toString() + "', "
                    + "tipo_descuento='" + tipo_descuento.toString() + "', "
                    + "fecha='" + fecha_i + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where descuento=" + id_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Descuento: " + id_descuento + " Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "94" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String descuento_eliminar(
            Integer usuario_sys,
            Integer id_descuento,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from descuento where descuento=" + id_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DESCUENTO: " + id_descuento + "',"
                    + "95" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
