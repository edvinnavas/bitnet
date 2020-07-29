package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Tipo_Codigo_Resultado implements Serializable {

    private static final long serialVersionUID = 1L;

    public Tipo_Codigo_Resultado() {

    }

    public String tipo_codigo_resultado_insertar(
            Integer usuario_sys,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into tipo_codigo_contactabilidad (nombre,estado,descripcion) values ('"
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
                    + "124" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_codigo_resultado_modificar(
            Integer usuario_sys,
            Integer id_tipo_codigo_contactabilidad,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Tipo_Codigo_Resultado: " + id_tipo_codigo_contactabilidad + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "125" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_codigo_resultado_eliminar(
            Integer usuario_sys,
            Integer id_tipo_codigo_contactabilidad,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO CODIGO RESULTADO: " + id_tipo_codigo_contactabilidad + "',"
                    + "126" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String tipo_codigo_resultado_activar(
            Integer usuario_sys,
            Integer id_tipo_codigo_contactabilidad,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_CODIGO_RESULTADO: " + id_tipo_codigo_contactabilidad + "',"
                    + "127" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
