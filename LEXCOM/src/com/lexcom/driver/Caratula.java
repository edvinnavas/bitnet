package com.lexcom.driver;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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

public class Caratula {

    Connection conn;
    Integer usuario_sys;

    public Caratula(Connection conn, Integer usuario_sys) {
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
                Object[] fila = new Object[16];
                for (Integer columna = 0; columna < 16; columna++) {
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

    public String cargar_juicios(DefaultTableModel modelo) {
        String resultado = "";

        try {
            this.conn.setAutoCommit(false);
            for (Integer row = 0; row < modelo.getRowCount(); row++) {
                String cadenasql = "update deudor set "
                        + "estatus='" + quitar_simbolos(modelo.getValueAt(row, 4).toString()) + "', "
                        + "situacion_caso='" + quitar_simbolos(modelo.getValueAt(row, 5).toString()) + "' "
                        + "where deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                Statement stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                cadenasql = "update juicio set "
                        + "procuracion='" + quitar_simbolos(modelo.getValueAt(row, 6).toString()) + "', "
                        + "juzgado='" + quitar_simbolos(modelo.getValueAt(row, 7).toString()) + "', "
                        + "no_juicio='" + quitar_simbolos(modelo.getValueAt(row, 8).toString()) + "', "
                        + "fecha='" + quitar_simbolos(modelo.getValueAt(row, 9).toString()) + "', "
                        + "notificador='" + quitar_simbolos(modelo.getValueAt(row, 10).toString()) + "', "
                        + "procurador='" + quitar_simbolos(modelo.getValueAt(row, 11).toString()) + "' "
                        + "where deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                String juicio = "";
                cadenasql = "select j.juicio from juicio j where j.deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                stmt = this.conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    juicio = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                if(quitar_simbolos(modelo.getValueAt(row, 12).toString()).equals("PEDIDO") || 
                        quitar_simbolos(modelo.getValueAt(row, 12).toString()).equals("NO PEDIDO") || 
                        quitar_simbolos(modelo.getValueAt(row, 12).toString()).equals("DECRETADO") || 
                        quitar_simbolos(modelo.getValueAt(row, 12).toString()).equals("NO DECRETADO") ||
                        quitar_simbolos(modelo.getValueAt(row, 12).toString()).equals("DILIGENCIADO")) {
                    if(validar_fecha(quitar_simbolos(modelo.getValueAt(row, 13).toString()))) {
                        cadenasql = "delete from juicio_arraigo where juicio='" + juicio + "'";
                        stmt = this.conn.createStatement();
                        stmt.executeUpdate(cadenasql);

                        cadenasql = "insert into juicio_arraigo (juicio,correlativo,arraigo,deligenciado) values ('"
                                + juicio + "','" + "1" + "','" + quitar_simbolos(modelo.getValueAt(row, 12).toString()) + "','" + quitar_simbolos(modelo.getValueAt(row, 13).toString()) + "')";
                        stmt = this.conn.createStatement();
                        stmt.executeUpdate(cadenasql);
                    }
                }
                
                if(quitar_simbolos(modelo.getValueAt(row, 14).toString()).equals("PEDIDO") || 
                        quitar_simbolos(modelo.getValueAt(row, 14).toString()).equals("NO PEDIDO") || 
                        quitar_simbolos(modelo.getValueAt(row, 14).toString()).equals("DECRETADO") || 
                        quitar_simbolos(modelo.getValueAt(row, 14).toString()).equals("NO DECRETADO") ||
                        quitar_simbolos(modelo.getValueAt(row, 14).toString()).equals("DILIGENCIADO")) {
                    if(validar_fecha(quitar_simbolos(modelo.getValueAt(row, 15).toString()))) {
                        cadenasql = "delete from juicio_banco where juicio='" + juicio + "'";
                        stmt = this.conn.createStatement();
                        stmt.executeUpdate(cadenasql);

                        cadenasql = "insert into juicio_banco (juicio,correlativo,bancos,deligenciado) values ('"
                                + juicio + "','" + "1" + "','" + quitar_simbolos(modelo.getValueAt(row, 14).toString()) + "','" + quitar_simbolos(modelo.getValueAt(row, 15).toString()) + "')";
                        stmt = this.conn.createStatement();
                        stmt.executeUpdate(cadenasql);
                    }
                }
            }
            
            this.conn.commit();
            this.conn.setAutoCommit(true);
            resultado = "0,Carga de cartula correctamente.";
        } catch (SQLException | HeadlessException ex) {
            try {
                this.conn.rollback();
                resultado = "1," + ex.toString() + "";
            } catch (SQLException ex1) {
                resultado = "1," + ex1.toString() + "";
            }
        }

        return resultado;
    }
    
    private Boolean validar_fecha(String fecha) {
        Boolean resultado = false;
        
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            resultado = true;
        } catch (ParseException ex) {
            resultado = false;
        }
        
        return resultado;
    }

    private String quitar_simbolos(String cadena) {
        String resultado = cadena.replaceAll("\"", "");
        resultado = cadena.replaceAll("'", "");

        return resultado;
    }
}
