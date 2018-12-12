package com.lexcom.driver;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Pagos_Masivo {

    Connection conn;
    Integer usuario_sys;

    public Pagos_Masivo(Connection conn, Integer usuario_sys) {
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
    
    private boolean esFechaValida(String fecha) {
        boolean resultado = false;
        
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            resultado = true;
        } catch (ParseException e) {
            resultado = false;
        }
        
        return resultado;
    }

    public String cargar_deudores(DefaultTableModel modelo) {
        String resultado = "";
        Integer linea = 0;

        try {
            this.conn.setAutoCommit(false);
            for (Integer row = 0; row < modelo.getRowCount(); row++) {
                linea = row;
                
                String deudor = modelo.getValueAt(row, 0).toString();
                boolean esfecha = esFechaValida(modelo.getValueAt(row, 6).toString());
                
                Double monto_pago = 0.00;
                try {
                    monto_pago = Double.parseDouble(modelo.getValueAt(row, 7).toString());
                } catch(Exception ex) {
                    monto_pago = 0.00;
                }
                
                if(monto_pago > 0.00 && esfecha) {
                    String cadenasql = "insert into pago (deudor, banco, fecha, no_boleta, monto, descripcion, fecha_registro) values ('"
                            + deudor.toString() + "','"
                            + "3" + "','"
                            + modelo.getValueAt(row, 6).toString() + "','"
                            + "B-000" + "','"
                            + monto_pago.toString() + "','"
                            + "Pago cargado masivamente." + "',"
                            + "CURRENT_DATE()" + ")";
                    Statement stmt = this.conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                }
            }
            this.conn.commit();
            this.conn.setAutoCommit(true);
            resultado = "0,Pagos masivos cargados exitosamente.";
        } catch (SQLException | HeadlessException ex) {
            try {
                this.conn.rollback();
                resultado = "Linea:" + linea + ",0," + ex.toString() + "";
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
