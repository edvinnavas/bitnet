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

public class Deudor_Masivo {

    Connection conn;
    Integer usuario_sys;

    public Deudor_Masivo(Connection conn, Integer usuario_sys) {
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
                Object[] fila = new Object[52];
                for (Integer columna = 0; columna < 52; columna++) {
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
        Integer linea = 0;

        try {
            this.conn.setAutoCommit(false);
            for (Integer row = 0; row < modelo.getRowCount(); row++) {
                linea = row;
                //Valida fecha de nacimiento del deudor.
                String fecha_nacimiento = "";
                if (modelo.getValueAt(row, 5) == null) {
                    fecha_nacimiento = "1900-01-01";
                } else {
                    if (modelo.getValueAt(row, 5).toString().equals("")) {
                        fecha_nacimiento = "1900-01-01";
                    } else {
                        fecha_nacimiento = modelo.getValueAt(row, 5).toString();
                    }
                }

                //Valida la zona de residencia del deudor.
                String zona = "";
                if (modelo.getValueAt(row, 11) == null) {
                    zona = "0";
                } else {
                    if (modelo.getValueAt(row, 11).toString().equals("")) {
                        zona = "0";
                    } else {
                        zona = modelo.getValueAt(row, 11).toString();
                    }
                }

                //Valida el sexo del deudor.
                String sexo = "";
                if (modelo.getValueAt(row, 14) == null) {
                    sexo = "-";
                } else {
                    if (modelo.getValueAt(row, 14).toString().equals("")) {
                        sexo = "-";
                    } else {
                        sexo = modelo.getValueAt(row, 14).toString();
                    }
                }

                //Valida el estado_civil del deudor.
                String estado_civil = "";
                if (modelo.getValueAt(row, 15) == null) {
                    estado_civil = "-";
                } else {
                    if (modelo.getValueAt(row, 15).toString().equals("")) {
                        estado_civil = "-";
                    } else {
                        estado_civil = modelo.getValueAt(row, 15).toString();
                    }
                }

                //Valida rubor de PDF.
                String PDF = "";
                if (modelo.getValueAt(row, 33) == null) {
                    PDF = "NO";
                } else {
                    if (modelo.getValueAt(row, 33).toString().equals("")) {
                        PDF = "NO";
                    } else {
                        PDF = modelo.getValueAt(row, 33).toString();
                    }
                }

                //Valida rubor de INV.
                String INV = "";
                if (modelo.getValueAt(row, 34) == null) {
                    INV = "NO";
                } else {
                    if (modelo.getValueAt(row, 34).toString().equals("")) {
                        INV = "NO";
                    } else {
                        INV = modelo.getValueAt(row, 34).toString();
                    }
                }

                //Valida rubor de MAYCOM.
                String MAYCOM = "";
                if (modelo.getValueAt(row, 35) == null) {
                    MAYCOM = "NO";
                } else {
                    if (modelo.getValueAt(row, 35).toString().equals("")) {
                        MAYCOM = "NO";
                    } else {
                        MAYCOM = modelo.getValueAt(row, 35).toString();
                    }
                }

                //Valida rubor de NITS.
                String NITS = "";
                if (modelo.getValueAt(row, 36) == null) {
                    NITS = "NO";
                } else {
                    if (modelo.getValueAt(row, 36).toString().equals("")) {
                        NITS = "NO";
                    } else {
                        NITS = modelo.getValueAt(row, 36).toString();
                    }
                }

                //Valida la fecha del proximo pago del deudor.
                String fecha_proximo_pago = "";
                if (modelo.getValueAt(row, 43) == null) {
                    fecha_proximo_pago = "1900-01-01";
                } else {
                    if (modelo.getValueAt(row, 43).toString().equals("")) {
                        fecha_proximo_pago = "1900-01-01";
                    } else {
                        fecha_proximo_pago = modelo.getValueAt(row, 43).toString();
                    }
                }

                //Valida el monto del proximo pago del deudor.
                String monto_proximo_pago = "";
                if (modelo.getValueAt(row, 44) == null) {
                    monto_proximo_pago = "0.00";
                } else {
                    if (modelo.getValueAt(row, 44).toString().equals("")) {
                        monto_proximo_pago = "0.00";
                    } else {
                        monto_proximo_pago = modelo.getValueAt(row, 44).toString();
                    }
                }

                //Valida rubor de NITS.
                String pais = "";
                if (modelo.getValueAt(row, 12) == null) {
                    pais = "Guatemala";
                } else {
                    if (modelo.getValueAt(row, 12).toString().equals("")) {
                        pais = "Guatemala";
                    } else {
                        pais = modelo.getValueAt(row, 12).toString();
                    }
                }

                //Valida rubor de NITS.
                String departamento = "";
                if (modelo.getValueAt(row, 13) == null) {
                    departamento = "Guatemala";
                } else {
                    if (modelo.getValueAt(row, 13).toString().equals("")) {
                        departamento = "Guatemala";
                    } else {
                        departamento = modelo.getValueAt(row, 13).toString();
                    }
                }

                String cadenasql = "update deudor set "
                        + "actor='" + quitar_simbolos(modelo.getValueAt(row, 1).toString()) + "', "
                        + "moneda='" + quitar_simbolos(modelo.getValueAt(row, 2).toString()) + "', "
                        + "dpi='" + quitar_simbolos(modelo.getValueAt(row, 3).toString()) + "', "
                        + "nit='" + quitar_simbolos(modelo.getValueAt(row, 4).toString()) + "', "
                        + "fecha_nacimiento='" + fecha_nacimiento + "', "
                        + "nombre='" + quitar_simbolos(modelo.getValueAt(row, 6).toString()) + "', "
                        + "nacionalidad='" + quitar_simbolos(modelo.getValueAt(row, 7).toString()) + "', "
                        + "telefono_casa='" + quitar_simbolos(modelo.getValueAt(row, 8).toString()) + "', "
                        + "telefono_celular='" + quitar_simbolos(modelo.getValueAt(row, 9).toString()) + "', "
                        + "direccion='" + quitar_simbolos(modelo.getValueAt(row, 10).toString()) + "', "
                        + "zona='" + zona + "', "
                        + "pais='" + pais + "', "
                        + "departamento='" + departamento + "', "
                        + "sexo='" + sexo + "', "
                        + "estado_civil='" + estado_civil + "', "
                        + "fecha_ingreso='" + quitar_simbolos(modelo.getValueAt(row, 16).toString()) + "', "
                        + "profesion='" + quitar_simbolos(modelo.getValueAt(row, 17).toString()) + "', "
                        + "correo_electronico='" + quitar_simbolos(modelo.getValueAt(row, 18).toString()) + "', "
                        + "lugar_trabajo='" + quitar_simbolos(modelo.getValueAt(row, 19).toString()) + "', "
                        + "direccion_trabajo='" + quitar_simbolos(modelo.getValueAt(row, 20).toString()) + "', "
                        + "telefono_trabajo='" + quitar_simbolos(modelo.getValueAt(row, 21).toString()) + "', "
                        + "monto_inicial='" + quitar_simbolos(modelo.getValueAt(row, 22).toString()) + "', "
                        + "usuario='" + quitar_simbolos(modelo.getValueAt(row, 23).toString()) + "', "
                        + "sestado='" + quitar_simbolos(modelo.getValueAt(row, 24).toString()) + "', "
                        + "estatus='" + quitar_simbolos(modelo.getValueAt(row, 25).toString()) + "', "
                        + "no_cuenta='" + quitar_simbolos(modelo.getValueAt(row, 26).toString()) + "', "
                        + "garantia='" + quitar_simbolos(modelo.getValueAt(row, 27).toString()) + "', "
                        + "cargado='" + quitar_simbolos(modelo.getValueAt(row, 28).toString()) + "', "
                        + "estado='" + quitar_simbolos(modelo.getValueAt(row, 29).toString()) + "', "
                        + "descripcion='" + quitar_simbolos(modelo.getValueAt(row, 30).toString()) + "', "
                        + "caso='" + quitar_simbolos(modelo.getValueAt(row, 32).toString()) + "', "
                        + "PDF='" + PDF + "', "
                        + "INV='" + INV + "', "
                        + "MAYCOM='" + MAYCOM + "', "
                        + "NITS='" + NITS + "', "
                        + "cosecha='" + quitar_simbolos(modelo.getValueAt(row, 37).toString()) + "', "
                        + "no_cuenta_otro='" + quitar_simbolos(modelo.getValueAt(row, 38).toString()) + "', "
                        + "descripcion_adicional='" + quitar_simbolos(modelo.getValueAt(row, 39).toString()) + "', "
                        + "fecha_recepcion='" + quitar_simbolos(modelo.getValueAt(row, 40).toString()) + "', "
                        + "anticipo='" + quitar_simbolos(modelo.getValueAt(row, 41).toString()) + "', "
                        + "antiguedad='" + quitar_simbolos(modelo.getValueAt(row, 42).toString()) + "', "
                        + "fecha_proximo_pago='" + fecha_proximo_pago + "', "
                        + "monto_proximo_pago='" + monto_proximo_pago + "', "
                        + "saldo='" + quitar_simbolos(modelo.getValueAt(row, 45).toString()) + "', "
                        + "convenio_pactado='" + quitar_simbolos(modelo.getValueAt(row, 46).toString()) + "', "
                        + "cuota_convenio='" + quitar_simbolos(modelo.getValueAt(row, 47).toString()) + "', "
                        + "costas_pagadas='" + quitar_simbolos(modelo.getValueAt(row, 48).toString()) + "', "
                        + "situacion_caso='" + quitar_simbolos(modelo.getValueAt(row, 49).toString()) + "', "
                        + "opcion_proximo_pago='" + quitar_simbolos(modelo.getValueAt(row, 50).toString()) + "' "
                        + "where deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                Statement stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                cadenasql = "update juicio set "
                        + "procuracion='" + quitar_simbolos(modelo.getValueAt(row, 51).toString()) + "' "
                        + "where deudor='" + quitar_simbolos(modelo.getValueAt(row, 0).toString()) + "'";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            this.conn.commit();
            this.conn.setAutoCommit(true);
            resultado = "0,Deudores actualizados a la base de datos correctamente.";
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
