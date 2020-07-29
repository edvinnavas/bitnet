package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Estado_Estatus_Judicial implements Serializable {

    private static final long serialVersionUID = 1L;

    public Estado_Estatus_Judicial() {

    }

    public String Estado_Status_Judicial(
            Integer usuario_sys,
            Integer estado_d,
            Integer[] estatus,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from estado_status_judicial where sestado=" + estado_d;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < estatus.length; i++) {
                cadenasql = "insert into estado_status_judicial (sestado,estatus) values (" + estado_d + "," + estatus[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + estado_d + "',"
                    + "120" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado-Status judicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Judicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Judicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
