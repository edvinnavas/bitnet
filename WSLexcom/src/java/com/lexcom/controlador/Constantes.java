package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Constantes implements Serializable {

    private static final long serialVersionUID = 1L;

    public Constantes() {

    }

    public String Constantes_Modificar(
            Integer usuario_sys,
            String[] constantes,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            for (Integer i = 0; i < constantes.length; i++) {
                String[] constante = constantes[i].split(",");

                if (constante[0].trim().equals("1")) {
                    constante[1] = "\\\\\\\\192.168.2.1\\\\discodered\\\\EXPEDIENTES ELECTRONICO";
                }

                String cadenasql = "update constantes set valor='" + constante[1] + "' where constantes=" + constante[0];
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CONSTANTES MODIFICACION" + "',"
                    + "123" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Constantes modificadas en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Constantes_Modificar): " + ex.toString());
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Constantes_Modificar - rollback): " + ex1.toString());
                resultado = "3," + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
