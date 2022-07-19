package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Descuento {

    Connection conn;
    Integer usuario_sys;
    
    public Integer deudor;
    public Integer tipo_descuento;
    public Calendar fecha;
    public Double monto;
    public String descripcion;

    public Descuento(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(Integer deudor, Integer tipo_descuento, Calendar fecha, Double monto, String descripcion) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "insert into descuento (deudor, tipo_descuento, fecha, monto, descripcion) values ('"
                + deudor.toString() + "','"
                + tipo_descuento.toString() + "','"
                + fecha_i + "','"
                + monto.toString() + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Descuento registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Descuento registrado=> Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString(), 1);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

    public String modificar(Integer descuento, Integer deudor, Integer tipo_descuento, Calendar fecha, Double monto, String descripcion) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "update descuento set "
                + "deudor='" + deudor.toString() + "', "
                + "tipo_descuento='" + tipo_descuento.toString() + "', "
                + "fecha='" + fecha_i + "', "
                + "monto='" + monto + "', "
                + "descripcion='" + descripcion + "' "
                + "where descuento=" + descuento.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Descuento modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Descuento modificado=> Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString(), 1);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

    public Descuento obtener(Integer seleccion) {
        String cadenasql = "select "
                + "u.deudor, "
                + "u.tipo_descuento, "
                + "u.fecha, "
                + "u.monto, "
                + "u.descripcion "
                + "from "
                + "descuento u "
                + "where u.descuento=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.tipo_descuento = rs.getInt(2);
                    this.fecha = this.DateToCalendar(rs.getDate(3));
                    this.monto = rs.getDouble(4);
                    this.descripcion = rs.getString(5);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from descuento where descuento= " + seleccion.toString();
        
        try {
            Descuento descuento_temp = new Descuento(conn, usuario_sys);
            descuento_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Descuento eliminado.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Descuento eliminado=> " + " Descuento: " + seleccion + " Deudor: " + descuento_temp.deudor + " Fecha: " + descuento_temp.fecha + " Monto: " + descuento_temp.monto + " Descripcion: " + descuento_temp.descripcion, 1);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
