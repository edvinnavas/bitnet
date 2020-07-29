package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Tipo_Descuento implements Serializable {

    private static final long serialVersionUID = 1L;

    public Tipo_Descuento() {

    }

    public String tipo_descuento_insertar(
            Integer usuario_sys,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into tipo_descuento (nombre,estado,descripcion) values ('"
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
                    + "65" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_descuento_modificar(
            Integer usuario_sys,
            Integer id_tipo_descuento,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Tipo_Descuento: " + id_tipo_descuento + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "66" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_descuento_eliminar(
            Integer usuario_sys,
            Integer id_tipo_descuento,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO DESCUENTO: " + id_tipo_descuento + "',"
                    + "67" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_descuento_activar(
            Integer usuario_sys,
            Integer id_tipo_descuento,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_DESCUENTO: " + id_tipo_descuento + "',"
                    + "68" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
