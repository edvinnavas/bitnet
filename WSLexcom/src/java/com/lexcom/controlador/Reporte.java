package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    public Reporte() {

    }

    public String[][] reporte(
            String cadenasql, 
            String poolConexion) {

        String[][] resultado;
        Integer filas = 0;
        Integer columnas = 0;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metadatos = rs.getMetaData();
            columnas = metadatos.getColumnCount();
            while (rs.next()) {
                filas++;
            }
            resultado = new String[filas + 1][columnas];

            Integer i = 0;
            for (Integer j = 0; j < columnas; j++) {
                resultado[i][j] = metadatos.getColumnLabel(j + 1);
            }
            rs.close();
            stmt.close();

            i = 1;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                for (Integer j = 0; j < columnas; j++) {
                    if (rs.getString(j + 1) == null) {
                        resultado[i][j] = "-";
                    } else {
                        char caracter_old = (char) 31;
                        char caracter_new = (char) 32;
                        resultado[i][j] = rs.getString(j + 1).replace(caracter_old, caracter_new);
                    }
                }
                i++;
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Reporte): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String sql_transaccion(
            String cadenasql, 
            String poolConexion) {
        
        String resultado;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "1, Transacción completa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(SqlTransaccion): " + ex.toString());
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(SqlTransaccion - rollback): " + ex1.toString());
                resultado = "3," + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
