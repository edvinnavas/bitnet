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

    public String[][] getMonitor() {
        String[][] resultado = new String[2][9];
        Driver driver = new Driver();
        
        try {
            // CONEXION A PRODUCCION.
            Connection conn_produccion = driver.getConn("LEXCOMJNDI");
            
            resultado[0][0] = "fecha_ultima_gestion_prod";
            resultado[0][1] = "hora_ultima_gestion_prod";
            resultado[0][2] = "numero_gestion_prod";

            String cadenasql = "select max(e.fecha) from evento e";
            Statement stmt = conn_produccion.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][0] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select max(e.hora) from evento e where e.fecha='" + resultado[1][0] + "'";
            stmt = conn_produccion.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][1] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select count(*) from evento e";
            stmt = conn_produccion.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][2] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            conn_produccion = driver.closeConn();
            
            // CONEXION A PRUEBAS.
            Connection conn_pruebas = driver.getConn("LexcomJndiTEST");
            
            resultado[0][3] = "fecha_ultima_gestion_test";
            resultado[0][4] = "hora_ultima_gestion_test";
            resultado[0][5] = "numero_gestion_test";

            cadenasql = "select max(e.fecha) from evento e";
            stmt = conn_pruebas.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][3] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select max(e.hora) from evento e where e.fecha='" + resultado[1][3] + "'";
            stmt = conn_pruebas.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][4] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select count(*) from evento e";
            stmt = conn_pruebas.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][5] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            conn_pruebas = driver.closeConn();
            
            // CONEXION A PRUEBAS.
            Connection conn_replica = driver.getConn("LexcomJndiReplica");
            
            resultado[0][6] = "fecha_ultima_gestion_replica";
            resultado[0][7] = "hora_ultima_gestion_replica";
            resultado[0][8] = "numero_gestion_replica";

            cadenasql = "select max(e.fecha) from evento e";
            stmt = conn_replica.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][6] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select max(e.hora) from evento e where e.fecha='" + resultado[1][6] + "'";
            stmt = conn_replica.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][7] = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select count(*) from evento e";
            stmt = conn_replica.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado[1][8] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            conn_replica = driver.closeConn();

        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(getMonitor): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        } finally {
            driver = null;
        }

        return resultado;
    }

}
