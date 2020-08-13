package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Juicio_Medida_Etapa_Workflow implements Serializable {

    private static final long serialVersionUID = 1L;

    public Juicio_Medida_Etapa_Workflow() {

    }

    public String juicio_medida_etapa_workflow_actualizar(
            Integer usuario_sys,
            Long juicio_medida_d,
            Long juicio_medida_etapa_actual_d,
            String[] siguiente_desc_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "delete from juicio_medida_etapa_workflow where juicio_medida=" + juicio_medida_d + " and juicio_medida_etapa_actual=" + juicio_medida_etapa_actual_d;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            for (Integer i = 0; i < siguiente_desc_d.length; i++) {
                String nombre_medida = "";
                cadenasql = "select jm.nombre from juicio_medida jm where jm.juicio_medida=" + juicio_medida_d;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    nombre_medida = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                String nombre_etapa = "";
                cadenasql = "select jme.nombre from juicio_medida_etapa jme where jme.juicio_medida_etapa=" + juicio_medida_etapa_actual_d;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    nombre_etapa = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                Long juicio_medida_etapa_siguiente_d = new Long(0);
                cadenasql = "select jme.juicio_medida_etapa from juicio_medida_etapa jme where jme.nombre='" + siguiente_desc_d[i] + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    juicio_medida_etapa_siguiente_d = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                
                String descripcion = "Medida " + nombre_medida + " etapa " + nombre_etapa + " siguiente etapa " + siguiente_desc_d[i] + ".";

                cadenasql = "insert into juicio_medida_etapa_workflow (juicio_medida, juicio_medida_etapa_actual, juicio_medida_etapa_siguiente, descripcion) values ("
                        + juicio_medida_d + ","
                        + juicio_medida_etapa_actual_d + ","
                        + juicio_medida_etapa_siguiente_d + ",'"
                        + descripcion + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                        + usuario_sys + ","
                        + "CURRENT_DATE()" + ","
                        + "CURRENT_TIME()" + ",'"
                        + "JUICIO_MEDIDA_ETAPA_WORKFLOW_ACTUALIZAR: juicio_medida: " + nombre_medida + " | juicio_medida_etapa_actual: " + nombre_etapa + " | juicio_medida_etapa_siguiente: " + siguiente_desc_d[i] + "',"
                        + "168" + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juicio-Medida-Etapa-Workflow actualiado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(juicio_medida_etapa_workflow_actualizar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(juicio_medida_etapa_workflow_actualizar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
