package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    public Actor() {

    }

    public String actor_insertar(
            Integer usuario_sys,
            String nombre_d,
            String nit_d,
            String telefono_d,
            String descripcion_d,
            String corporacion_d,
            String digitalizados_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String id_corporacion = "";
            String cadenasql = "select c.cooperacion from cooperacion c where c.nombre = '" + corporacion_d + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                id_corporacion = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "insert into actor (nombre,nit,telefono,estado,descripcion,cooperacion,digitalizados) values ('"
                    + nombre_d + "','"
                    + nit_d + "','"
                    + telefono_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "','"
                    + id_corporacion + "','"
                    + digitalizados_d + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + " Nombre: " + nombre_d + " Nit: " + nit_d + " telefono: " + telefono_d + " descripcion: " + descripcion_d + " corporación:" + corporacion_d + " digitalizados:" + digitalizados_d + "',"
                    + "37" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String actor_modificar(
            Integer usuario_sys,
            Integer id_actor,
            String nombre_d,
            String nit_d,
            String telefono_d,
            String descripcion_d,
            String corporacion_d,
            String digitalizados_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String id_corporacion = "";
            String cadenasql = "select c.cooperacion from cooperacion c where c.nombre = '" + corporacion_d + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                id_corporacion = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "update actor set "
                    + "nombre='" + nombre_d + "', "
                    + "nit='" + nit_d + "', "
                    + "telefono='" + telefono_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "cooperacion='" + id_corporacion + "', "
                    + "digitalizados='" + digitalizados_d + "' "
                    + "where actor=" + id_actor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Actor: " + id_actor + " Nombre: " + nombre_d + " Nit: " + nit_d + " telefono: " + telefono_d + " descripcion: " + descripcion_d + " corporación:" + corporacion_d + " digitalizados:" + digitalizados_d + "',"
                    + "38" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String actor_eliminar(
            Integer usuario_sys,
            Integer id_actor,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update actor set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where actor=" + id_actor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ACTOR: " + id_actor + "',"
                    + "39" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String actor_activar(
            Integer usuario_sys,
            Integer id_actor,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update actor set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where actor=" + id_actor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ACTOR: " + id_actor + "',"
                    + "40" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
