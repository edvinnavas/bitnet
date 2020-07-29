package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Tipo_Codigo_Resultado_Codigo_Resultado implements Serializable {

    private static final long serialVersionUID = 1L;

    public Tipo_Codigo_Resultado_Codigo_Resultado() {

    }

    public String Tipo_Codigo_Resultado_Codigo_Resultado(
            Integer usuario_sys,
            Integer tipo_codigo_resultado,
            Integer[] codigo_resultado,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from tipo_codigo_codigo where tipo_codigo_contactabilidad=" + tipo_codigo_resultado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < codigo_resultado.length; i++) {
                cadenasql = "insert into tipo_codigo_codigo (tipo_codigo_contactabilidad,codigo_contactabilidad) values (" + tipo_codigo_resultado + "," + codigo_resultado[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_CODIGO_RESULTADO: " + tipo_codigo_resultado + "',"
                    + "128" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo Código Resultado - Código Resultado guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Codigo_Resultado): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Codigo_Resultado - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
}
