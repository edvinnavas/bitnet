package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    public Rol() {

    }

    public String rol_insertar(
            Integer usuario_sys,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into rol (nombre,descripcion) values ('"
                    + nombre_d + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select max(rol) from rol";
            int rolact = 0;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                rolact = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);

            while (rs.next()) {
                cadenasql = "insert into rol_menu (rol,menu,ver) values ('" + rolact + "','" + rs.getString(1) + "','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "131" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String rol_modificar(
            Integer usuario_sys,
            Integer id_rol,
            String nombre_d,
            String descripcion_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update rol set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where rol=" + id_rol;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Rol: " + id_rol + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "132" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String rol_eliminar(
            Integer usuario_sys,
            Integer id_rol,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update rol set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where juzgado=" + id_rol;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUZGADO: " + id_rol + "',"
                    + "133" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Permisos_Rol_Modificar(
            Integer usuario_sys,
            Integer usuario,
            String[] menus_no_asignados,
            String[] menus_asignados,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            for (Integer i = 0; i < menus_no_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_no_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update rol_menu set ver='NO' where rol=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < menus_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update rol_menu set ver='SI' where rol=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Rol Menu Modificar=>  usuario: " + usuario + "',"
                    + "130" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Permisos del Rol modificados.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Rol_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Rol_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
            
}
