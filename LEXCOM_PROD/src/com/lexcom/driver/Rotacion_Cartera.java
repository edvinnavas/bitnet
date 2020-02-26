package com.lexcom.driver;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Rotacion_Cartera {

    Connection conn;
    Integer usuario_sys;

    public Rotacion_Cartera(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public DefaultTableModel cargar_archivo(String archivo, DefaultTableModel modelo) {
        try {
            while (modelo.getRowCount() > 0) {
                modelo.removeRow(0);
            }
            Workbook workbook = Workbook.getWorkbook(new File(archivo));
            Sheet sheet = workbook.getSheet(0);
            Integer i = 1;
            while (i <= 70000) {
                Object[] fila = new Object[13];
                for (Integer columna = 0; columna < 13; columna++) {
                    Cell dato = sheet.getCell(columna, i);
                    fila[columna] = dato.getContents().toString();
                }
                modelo.addRow(fila);
                i++;
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }

        return modelo;
    }

    public String cargar_deudores(DefaultTableModel modelo) {
        String resultado = "";

        try {
            this.conn.setAutoCommit(false);
            for (Integer row = 0; row < modelo.getRowCount(); row++) {
                String cadenasql = "update deudor set "
                        + "usuario='" + quitar_simbolos(modelo.getValueAt(row, 11).toString()) + "' "
                        + "where deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                Statement stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            this.conn.commit();
            this.conn.setAutoCommit(true);
            resultado = "0,Rotación de cartera actualizada correctamente.";
        } catch (SQLException | HeadlessException ex) {
            try {
                this.conn.rollback();
                resultado = "0," + ex.toString() + "";
            } catch (SQLException ex1) {
                resultado = "0," + ex1.toString() + "";
            }
        }

        return resultado;
    }

    private String quitar_simbolos(String cadena) {
        String resultado = cadena.replaceAll("\"", "");
        resultado = cadena.replaceAll("'", "");

        return resultado;
    }
}
