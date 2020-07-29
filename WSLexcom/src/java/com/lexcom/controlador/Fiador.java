package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Fiador implements Serializable {

    private static final long serialVersionUID = 1L;

    public Fiador() {

    }

    public String fiador_insertar(
            Integer usuario_sys,
            Integer deudor,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String nacionalidad,
            String telefono,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            String profesion,
            String correo_electronico,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into fiador ("
                    + "deudor,"
                    + "dpi,"
                    + "nit,"
                    + "fecha_nacimiento,"
                    + "nombre,"
                    + "nacionalidad,"
                    + "telefono,"
                    + "direccion,"
                    + "zona,"
                    + "pais,"
                    + "departamento,"
                    + "sexo,"
                    + "estado_civil,"
                    + "profesion,"
                    + "correo_electronico,"
                    + "descripcion) values ('"
                    + deudor.toString() + "','"
                    + dpi + "','"
                    + nit + "','"
                    + fecha + "','"
                    + nombre + "','"
                    + nacionalidad + "','"
                    + telefono + "','"
                    + direccion + "','"
                    + zona.toString() + "','"
                    + pais + "','"
                    + departamento + "','"
                    + sexo + "','"
                    + estado_civil + "','"
                    + profesion + "','"
                    + correo_electronico + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Fiador registrado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "99" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Fiador registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String fiador_modificar(
            Integer usuario_sys,
            Integer id_fiador,
            Integer deudor,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String nacionalidad,
            String telefono,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            String profesion,
            String correo_electronico,
            String descripcion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update fiador set "
                    + "deudor='" + deudor + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "fecha_nacimiento='" + fecha + "', "
                    + "nombre='" + nombre + "', "
                    + "nacionalidad='" + nacionalidad + "', "
                    + "telefono='" + telefono + "', "
                    + "direccion='" + direccion + "', "
                    + "zona='" + zona + "', "
                    + "pais='" + pais + "', "
                    + "departamento='" + departamento + "', "
                    + "sexo='" + sexo + "', "
                    + "estado_civil='" + estado_civil + "', "
                    + "profesion='" + profesion + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where fiador=" + id_fiador;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Aumento: " + id_fiador + " Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "100" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String fiador_eliminar(
            Integer usuario_sys,
            Integer id_fiador,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from fiador where fiador=" + id_fiador;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "FIADOR: " + id_fiador + "',"
                    + "101" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Fiador eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
