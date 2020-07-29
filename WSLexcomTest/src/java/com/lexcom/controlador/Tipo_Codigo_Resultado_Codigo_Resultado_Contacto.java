package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;

public class Tipo_Codigo_Resultado_Codigo_Resultado_Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Tipo_Codigo_Resultado_Codigo_Resultado_Contacto() {

    }

    public String Tipo_Codigo_Resultado_Contacto(
            Integer usuario_sys,
            String[] lst_tipo_codigo_resultado_contacto,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from tipo_codigo_codigo where tipo_codigo_contactabilidad > 0";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < lst_tipo_codigo_resultado_contacto.length; i++) {
                String[] tipo_codigo_resultado_contacto = lst_tipo_codigo_resultado_contacto[i].split(",");
                Integer tipo_codigo_resultado = Integer.parseInt(tipo_codigo_resultado_contacto[0]);
                Integer codigo_resultado = Integer.parseInt(tipo_codigo_resultado_contacto[1]);
                String contacto = tipo_codigo_resultado_contacto[2];

                cadenasql = "insert into tipo_codigo_codigo ("
                        + "tipo_codigo_contactabilidad,"
                        + "codigo_contactabilidad,"
                        + "contacto) values ("
                        + tipo_codigo_resultado + ","
                        + codigo_resultado + ",'"
                        + contacto + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Tipo_Codigo_Resultado_Contacto: Usuario(" + usuario_sys + ")" + "',"
                    + "152" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Contacto de los códigos de resultado actualizado.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Contacto): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Contacto - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
}
