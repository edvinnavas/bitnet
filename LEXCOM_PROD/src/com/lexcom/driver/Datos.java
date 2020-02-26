package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class Datos {
    
    Connection conn;
    DefaultTableModel modelo;
    DefaultComboBoxModel lst_busqueda;
    
    public Datos(Connection conn) {
        this.conn = conn;
    }
    
    public DefaultComboBoxModel obtener_lista_busqueda(DefaultTableModel modelo) {
        lst_busqueda = new DefaultComboBoxModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.lst_busqueda.addElement(modelo.getColumnName(i));
        }
        
        return lst_busqueda;
    }
    
    public DefaultTableModel obtener_tabla(String cadenasql) {
        Integer numeroColumnas = 0;
        
        this.modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }
            
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(cadenasql);
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                Object[] etiquetas = new Object[numeroColumnas];
                for (int i = 0; i < numeroColumnas; i++) {
                    etiquetas[i] = metaDatos.getColumnLabel(i + 1);
                }
                this.modelo.setColumnIdentifiers(etiquetas);
                
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return this.modelo;
    }
    
}
