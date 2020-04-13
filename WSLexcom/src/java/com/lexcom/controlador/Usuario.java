package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Usuario() {

    }

    public String usuario_insertar(
            Integer usuario_sys,
            String nombre_completo_d,
            String nombre_d,
            String contrasena_d,
            String recontrasena_d,
            String descripcion_d,
            String gestor_d,
            String procurador_d,
            String asistente_d,
            String digitador_d,
            String investigador_d,
            Integer tipo_usuario_d,
            Integer reinicio,
            Integer rol,
            String[] usuario_corporacion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into usuario (nombre_completo,nombre,contrasena,estado,descripcion,gestor,procurador,asistente,digitador,investigador,tipo_usuario,rol) values ('"
                    + nombre_completo_d + "','"
                    + nombre_d + "','"
                    + contrasena_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "','"
                    + gestor_d + "','"
                    + procurador_d + "','"
                    + asistente_d + "','"
                    + digitador_d + "','"
                    + investigador_d + "','"
                    + tipo_usuario_d + "',"
                    + rol + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select max(u.usuario) from usuario u";
            String usuario_temp = "select max(u.usuario) from usuario u";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_temp = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m where m.tipo=1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                cadenasql = "insert into permiso_usuario (usuario,menu,nuevo,modificar,eliminar,activar,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO','NO','NO','NO','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m where m.tipo=2";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                cadenasql = "insert into permiso_usuario_uno (usuario,menu,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();

            for (Integer i = 0; i < usuario_corporacion.length; i++) {
                cadenasql = "select c.cooperacion from cooperacion c where c.nombre='" + usuario_corporacion[i] + "'";
                System.out.println(cadenasql);
                Integer id_corporacion = 0;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    id_corporacion = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "insert into usuario_corporacion (usuario, corporacion) values ("
                        + usuario_temp + ","
                        + id_corporacion + ")";
                stmt = conn.createStatement();
                System.out.println(cadenasql);
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre_Completo: " + nombre_completo_d + "|Nombre: " + nombre_d + "|Contraseña: " + contrasena_d + "|Descripcion: " + descripcion_d + "|Gestor: " + gestor_d + "|Procurador: " + procurador_d + "|Asistente:" + asistente_d + "|Digitador: " + digitador_d + "|Investigador:" + investigador_d + "|Tipo Usuario:" + tipo_usuario_d + "|Rol:" + rol + "',"
                    + "29" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String usuario_modificar(
            Integer usuario_sys,
            Integer id_usuario,
            String nombre_completo_d,
            String nombre_d,
            String contrasena_d,
            String descripcion_d,
            String gestor_d,
            String procurador_d,
            String asistente_d,
            String digitador_d,
            String investigador_d,
            Integer tipo_usuario_d,
            Integer reinicio,
            Integer rol,
            String[] usuario_corporacion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";
        String cadenasql = "";

        try {
            conn.setAutoCommit(false);

            cadenasql = "update usuario set "
                    + "nombre_completo='" + nombre_completo_d + "', "
                    + "nombre='" + nombre_d + "', "
                    + "contrasena='" + contrasena_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "gestor='" + gestor_d + "', "
                    + "procurador='" + procurador_d + "', "
                    + "asistente='" + asistente_d + "', "
                    + "digitador='" + digitador_d + "', "
                    + "investigador='" + investigador_d + "', "
                    + "tipo_usuario='" + tipo_usuario_d + "', "
                    + "reinicio=" + reinicio + ", "
                    + "rol=" + rol + "  "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from usuario_corporacion where usuario=" + id_usuario;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < usuario_corporacion.length; i++) {
                cadenasql = "select c.cooperacion from cooperacion c where c.nombre='" + usuario_corporacion[i] + "'";
                Integer id_corporacion = 0;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    id_corporacion = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "insert into usuario_corporacion (usuario, corporacion) values ("
                        + id_usuario + ","
                        + id_corporacion + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Usuario: " + id_usuario + "|Nombre_Completo: " + nombre_completo_d + "|Nombre: " + nombre_d + "|Contraseña: " + contrasena_d + "|Descripcion: " + descripcion_d + "|Gestor: " + gestor_d + "|Procurador: " + procurador_d + "|Asistente:" + asistente_d + "|Digitador: " + digitador_d + "|Investigador:" + investigador_d + "|Tipo Usuario:" + tipo_usuario_d + "|Rol :" + rol + "|Reinicio:" + reinicio + "',"
                    + "30 )";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString() + " " + cadenasql;
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String usuario_eliminar(
            Integer usuario_sys, 
            Integer id_usuario, 
            String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update usuario set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "USUARIO: " + id_usuario + "',"
                    + "31" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String usuario_activar(
            Integer usuario_sys, 
            Integer id_usuario, 
            String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update usuario set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "USUARIO: " + id_usuario + "',"
                    + "32" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String logueo(
            String usuario, 
            String contrasena, 
            String poolConexion) {
        
        String resultado = "index";

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            String cadenasql = "select u.reinicio from usuario u where u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("1")) {
                    resultado = "reinicio";
                } else {
                    resultado = "menu"; // ==============> Si el usuario es autenticado devuelve la pagina del menu. <==============
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Logueo): " + ex.toString());
            resultado = "index"; // ==============> Si occure alguna excepcion la aplicacion no loguea al usuario. <==============
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String logueo_cambio(
            String usuario, 
            String contrasena, 
            String new_contrasena, 
            String poolConexion) {
        
        String resultado = "index";
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            String cadenasql = "select u.reinicio from usuario u where u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = "menu";
            }
            rs.close();
            stmt.close();

            if (resultado.endsWith("menu")) {
                cadenasql = "update usuario set "
                        + "contrasena='" + new_contrasena + "', "
                        + "reinicio=0 "
                        + "where "
                        + "nombre='" + usuario + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            } else {
                resultado = "Ingrese la contraseña antigua.";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Logueo_cambio): " + ex.toString());
            resultado = "index" + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
