package com.lexcom.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Evento {

    Connection conn;

    public Evento(Connection conn) {
        this.conn = conn;
    }

    public String insertar(Integer usuario, String descripcion, Integer tipo_evento) {
        String resultado = "";
        String cadenasql = "insert into evento (usuario,fecha,hora,descripcion, tipo_evento) values ("
                + usuario + ","
                + "CURRENT_DATE()" + ","
                + "CURRENT_TIME()" + ",'"
                + descripcion + "','"
                + tipo_evento + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                System.out.println("2," + ex.toString());
            } catch (SQLException ex1) {
                JOptionPane.showConfirmDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

}
