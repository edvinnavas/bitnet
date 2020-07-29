package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Intencion_Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    public Intencion_Pago() {

    }

    public String intencion_pago_insertar(
            Integer usuario_sys,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into intencion_pago (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "144" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención pago registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String intencion_pago_modificar(
            Integer usuario_sys,
            Integer id_intencion_pago,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_IntencionPago: " + id_intencion_pago + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "145" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención pago modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String intencion_pago_eliminar(
            Integer usuario_sys,
            Integer id_intencion_pago,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "INTENCION_PAGO: " + id_intencion_pago + "',"
                    + "146" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención de pago eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String intencion_pago_activar(
            Integer usuario_sys,
            Integer id_intencion_pago,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "INTENCION_PAGO: " + id_intencion_pago + "',"
                    + "147" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención de pago activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
