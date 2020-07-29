package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Estado_Estatus_Extrajudicial implements Serializable {

    private static final long serialVersionUID = 1L;

    public Estado_Estatus_Extrajudicial() {

    }

    public String Estado_Status_Extrajudicial(
            Integer usuario_sys,
            Integer estado_d,
            Integer[] estatus,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from estado_status_extrajudicial where sestado_extra=" + estado_d;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < estatus.length; i++) {
                cadenasql = "insert into estado_status_extrajudicial (sestado_extra,estatus_extra) values (" + estado_d + "," + estatus[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + estado_d + "',"
                    + "121" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado-Status extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extrajudicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extrajudicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Estado_Status_Extra_Permite(
            Integer usuario_sys,
            String[] estado_estatus,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            for (Integer i = 0; i < estado_estatus.length; i++) {
                String[] registro = estado_estatus[i].split(",");
                String cadenasql = "update estado_status_extrajudicial set permite_estado_judicial=" + registro[2] + " where sestado_extra=" + registro[0] + " and estatus_extra=" + registro[1];
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "MODIFICACION ESTADO-STATUS PERMITE" + "',"
                    + "122" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Configuración bloqueo Estado-Status extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extra_Permite): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extra_Permite - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
