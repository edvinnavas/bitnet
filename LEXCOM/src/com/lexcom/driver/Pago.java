package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Pago {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public Integer deudor;
    public Integer banco;
    public Calendar fecha;
    public String no_boleta;
    public Double monto;
    public String descripcion;

    public Pago(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            Integer deudor, 
            Integer banco, 
            Calendar fecha, 
            String no_boleta, 
            Double monto, 
            String descripcion) {
        
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "insert into pago (deudor, banco, fecha, no_boleta, monto, descripcion, fecha_registro) values ('"
                + deudor.toString() + "','"
                + banco.toString() + "','"
                + fecha_pago + "','"
                + no_boleta + "','"
                + monto.toString() + "','"
                + descripcion + "',"
                + "CURRENT_DATE()" + ")";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Pago registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Pago registrado=> Deudor: " + deudor + " banco: " + banco.toString() + " fecha: " + fecha_pago + " no_boleta: " + no_boleta + " monto: " + monto.toString(), 1);
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

    public String modificar(
            Integer pago, 
            Integer deudor, 
            Integer banco, 
            Calendar fecha, 
            String no_boleta, 
            Double monto, 
            String descripcion) {
        
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "update pago set "
                + "deudor='" + deudor.toString() + "', "
                + "banco='" + banco.toString() + "', "
                + "fecha='" + fecha_pago + "', "
                + "no_boleta='" + no_boleta + "', "
                + "monto='" + monto + "', "
                + "descripcion='" + descripcion + "' "
                + "where pago=" + pago.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Pago modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Pago modificado=> Deudor: " + deudor + " banco: " + banco.toString() + " fecha: " + fecha_pago + " no_boleta: " + no_boleta + " monto: " + monto.toString(), 1);
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

    public Pago obtener(Integer seleccion) {
        String cadenasql = "select u.deudor, u.banco, u.fecha, u.no_boleta, u.monto, u.descripcion from pago u where u.pago=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.banco = rs.getInt(2);
                    this.fecha = this.DateToCalendar(rs.getDate(3));
                    this.no_boleta = rs.getString(4);
                    this.monto = rs.getDouble(5);
                    this.descripcion = rs.getString(6);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from pago where pago= " + seleccion.toString();
        
        try {
            Pago pago_temp = new Pago(conn, usuario_sys);
            pago_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Pago eliminado.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Pago eliminado=> " + " Pago: " + seleccion + " Deudor: " + pago_temp.deudor + " Banco: " + pago_temp.banco + " Fecha: " + pago_temp.fecha + " No_Boleta: " + pago_temp.no_boleta + " Monto: " + pago_temp.monto + " Descripcion: " + pago_temp.descripcion, 1);
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
