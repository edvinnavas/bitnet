package com.lexcom.driver;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Deudor_Masivo_Carga {

    Connection conn;
    Integer usuario_sys;

    public Deudor_Masivo_Carga(Connection conn, Integer usuario_sys) {
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
                Object[] fila = new Object[17];
                for (Integer columna = 0; columna < 17; columna++) {
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
                //Inserta deudor masivamente.
                String cadenasql = "insert into deudor ("
                        + "caso,"
                        + "sestado,"
                        + "estatus,"
                        + "antiguedad,"
                        + "fecha_recepcion,"
                        + "situacion_caso,"
                        + "cargado,"
                        + "no_cuenta,"
                        + "actor,"
                        + "garantia,"
                        + "saldo,"
                        + "moneda,"
                        + "monto_inicial,"
                        + "nombre,"
                        + "anticipo,"
                        + "cosecha,"
                        + "usuario,"
                        + "dpi,"
                        + "nit,"
                        + "fecha_nacimiento,"
                        + "nacionalidad,"
                        + "telefono_casa,"
                        + "telefono_celular,"
                        + "direccion,"
                        + "zona,"
                        + "pais,"
                        + "departamento,"
                        + "sexo,"
                        + "estado_civil,"
                        + "fecha_ingreso,"
                        + "profesion,"
                        + "correo_electronico,"
                        + "lugar_trabajo,"
                        + "direccion_trabajo,"
                        + "telefono_trabajo,"
                        + "estado,"
                        + "descripcion,"
                        + "PDF,"
                        + "INV,"
                        + "MAYCOM,"
                        + "NITS,"
                        + "no_cuenta_otro,"
                        + "descripcion_adicional,"
                        + "fecha_proximo_pago,"
                        + "monto_proximo_pago,"
                        + "convenio_pactado,"
                        + "cuota_convenio,"
                        + "costas_pagadas,"
                        + "opcion_proximo_pago) values ('"
                        
                        + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 1).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 2).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 3).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 4).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 5).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 6).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 7).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 8).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 9).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 10).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 11).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 12).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 13).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 14).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 15).toString()) + "','"
                        + quitar_simbolos(modelo.getValueAt(row, 16).toString()) + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "1900-01-01" + "','"
                        + "Guatemalteco" + "','"
                        + "0" + "','"
                        + "0" + "','"
                        + "-" + "','"
                        + "0" + "','"
                        + "Guatemala" + "','"
                        + "Guatemala" + "','"
                        + "-" + "','"
                        + "-" + "',"
                        + "CURRENT_DATE()" + ",'"
                        + "-" + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "VIGENTE" + "','"
                        + "-" + "','"
                        + "NO" + "','"
                        + "NO" + "','"
                        + "NO" + "','"
                        + "NO" + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "1900-01-01" + "','"
                        + "0.00" + "','"
                        + "-" + "','"
                        + "0.00" + "','"
                        + "-"  + "','"
                        + "NO" + "')";
                Statement stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                String max_deudor = ""; 
                cadenasql = "select max(d.deudor) from deudor d";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    max_deudor = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                cadenasql = "insert into juicio ("
                        + "deudor,"
                        + "juzgado,"
                        + "fecha,"
                        + "no_juicio,"
                        + "monto,"
                        + "descripcion,"
                        + "procurador,"
                        + "razon_notificacion,"
                        + "notificador,"
                        + "abogado_deudor,"
                        + "sumario,"
                        + "memorial,"
                        + "procuracion,"
                        + "fecha_admision_demanda,"
                        + "deudor_notificado,"
                        + "fecha_notificacion,"
                        + "depositario,"
                        + "tiempo_estimado) values ('"
                        + max_deudor + "','"
                        + "30" + "','"
                        + "1900/01/01" + "','"
                        + "0" + "','"
                        + "0.00" + "','"
                        + "-" + "','"
                        + "66" + "','"
                        + "-" + "','"
                        + "0" + "','"
                        + "-" + "','"
                        + "-" + "','"
                        + "1900/01/01" + "','"
                        + "-" + "','"
                        + "1900/01/01" + "','"
                        + "NO" + "','"
                        + "1900/01/01" + "','"
                        + "-" + "','"
                        + "0" + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            this.conn.commit();
            this.conn.setAutoCommit(true);
            resultado = "0,Deudores insertados a la base de datos correctamente.";
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
