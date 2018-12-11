package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Aumento {

    Connection conn;
    Integer usuario_sys;
    
    public Integer deudor;
    public Integer tipo_aumento;
    public Calendar fecha;
    public Double monto;
    public String descripcion;

    public Aumento(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(Integer deudor, Integer tipo_aumento, Calendar fecha, Double monto, String descripcion) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "insert into aumento (deudor, tipo_aumento, fecha, monto, descripcion) values ('"
                + deudor.toString() + "','"
                + tipo_aumento.toString() + "','"
                + fecha_i + "','"
                + monto.toString() + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Aumento registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Aumento registrado=> Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString(), 1);
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

    public String modificar(Integer aumento, Integer deudor, Integer tipo_aumento, Calendar fecha, Double monto, String descripcion) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "update aumento set "
                + "deudor='" + deudor.toString() + "', "
                + "tipo_aumento='" + tipo_aumento.toString() + "', "
                + "fecha='" + fecha_i + "', "
                + "monto='" + monto + "', "
                + "descripcion='" + descripcion + "' "
                + "where aumento=" + aumento.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Aumento modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Aumento modificado=> Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString(), 1);
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

    public Aumento obtener(Integer seleccion) {
        String cadenasql = "select u.deudor, u.tipo_aumento, u.fecha, u.monto, u.descripcion from aumento u where u.aumento=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.tipo_aumento = rs.getInt(2);
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
        String cadenasql = "delete from aumento where aumento= " + seleccion.toString();
        
        try {
            Aumento aumento_temp = new Aumento(conn, usuario_sys);
            aumento_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Aumento eliminado.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Aumento eliminado=> " + " Aumento: " + seleccion + " Deudor: " + aumento_temp.deudor + " Fecha: " + aumento_temp.fecha + " Monto: " + aumento_temp.monto + " Descripcion: " + aumento_temp.descripcion, 1);
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
