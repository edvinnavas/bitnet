package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Bolson_Promesa_Pago {

    Connection conn;
    Integer usuario_sys;
    
    DefaultTableModel modelo;
    
    Integer usuario;

    public Bolson_Promesa_Pago(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }
    
    public DefaultTableModel obtener_tabla_promesa_pago(DefaultTableModel modelo, Integer usuario) {
        this.usuario = usuario;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "p.promesa_pago AS PROMESA_PAGO, "
                + "a.nombre AS ACTOR, "
                + "d.nombre AS NOMBRE, "
                + "d.caso AS CASO, "
                + "DATE_FORMAT(p.fecha_ingreso,'%d/%m/%Y') AS FECHA_INGRESO, "
                + "DATE_FORMAT(p.fecha_pago,'%d/%m/%Y') AS FECHA_PAGO, "
                + "p.estado_promesa AS ESTADO_PROMESA, "
                + "CONCAT('Q', FORMAT(p.monto, 2)) AS MONTO, "
                + "d.deudor AS COD_DEUDOR "
                + "FROM "
                + "promesa_pago p "
                + "left join deudor d on (p.deudor=d.deudor) "
                + "left join actor a on (d.actor=a.actor) "
                + "WHERE "
                + "d.usuario=" + this.usuario + " and "
                + "p.estado_promesa = 'NO LEIDO' and "
                + "p.fecha_pago <= CURRENT_DATE() and "
                + "p.hora_pago <= CURRENT_TIME() "
                + "order by p.fecha_ingreso desc";
        
        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }
            
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return this.modelo;
    }
    
}
