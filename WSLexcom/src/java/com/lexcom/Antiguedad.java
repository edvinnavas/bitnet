package com.lexcom;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Antiguedad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public Antiguedad () {
        
    }
    
    public String Insertar (
            Integer usuario_sys,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into antiguedad (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "154" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Antigüedad registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    public String Modificar (
            Integer usuario_sys,
            Integer id_antiguedad,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update antiguedad set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where antiguedad=" + id_antiguedad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Antiguedad: " + id_antiguedad + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "155" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Antigüedad modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    public String Eliminar (
        Integer usuario_sys,
        Integer id_antiguedad,
        String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update antiguedad set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where antiguedad=" + id_antiguedad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ANTIGUEDAD: " + id_antiguedad + "',"
                    + "156" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Antigüedad eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    public String Activar (
        Integer usuario_sys,
        Integer id_antiguedad,
        String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update antiguedad set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where antiguedad=" + id_antiguedad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ANTIGUEDAD: " + id_antiguedad + "',"
                    + "157" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Antigüedad activa en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Antiguedad_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
}
