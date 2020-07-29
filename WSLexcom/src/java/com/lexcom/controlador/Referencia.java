package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Referencia implements Serializable {

    private static final long serialVersionUID = 1L;

    public Referencia() {

    }

    public String referencia_insertar(
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

            String cadenasql = "insert into referencia ("
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
                    + "Referencia registrado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "102" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String referencia_modificar(
            Integer usuario_sys,
            Integer id_referencia,
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

            String cadenasql = "update referencia set "
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
                    + "where referencia=" + id_referencia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Referencia: " + id_referencia + " Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "103" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String referencia_eliminar(
            Integer usuario_sys,
            Integer id_referencia,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from referencia where referencia=" + id_referencia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "REFERENCIA: " + id_referencia + "',"
                    + "104" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
