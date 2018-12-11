package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Promesa_Pago {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public Calendar fecha_ingreso;
    public Calendar fecha_pago;
    public Integer hora_pago;
    public Integer minuto_pago;
    public String estado_promesa;
    public String observacion;
    public Double monto;
    public Integer deudor;

    public Promesa_Pago(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            Integer deudor,
            Calendar fecha_ingreso,
            Calendar fecha_pago,
            Integer hora_pago,
            Integer minuto_pago,
            String estado_promesa,
            String observacion,
            Double monto) {
        
        String resultado = "";
        
        Integer dia = fecha_ingreso.get(Calendar.DATE);
        Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
        Integer ano = fecha_ingreso.get(Calendar.YEAR);
        String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_pago.get(Calendar.DATE);
        mes = fecha_pago.get(Calendar.MONTH) + 1;
        ano = fecha_pago.get(Calendar.YEAR);
        String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "insert into promesa_pago(fecha_ingreso, fecha_pago, hora_pago, estado_promesa, observacion, monto, deudor) values ('"
                + fecha_ingreso_t + "','"
                + fecha_pago_t + "','"
                + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "','"
                + estado_promesa + "','"
                + observacion + "','"
                + monto + "','"
                + deudor.toString() + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Recordatorio registrada en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Promesa de Pago registrado=> Deudor: " + deudor + " fecha_ingreso: " + fecha_ingreso_t + " fecha_pago: " + fecha_pago + " hora_pago " + hora_pago + ":" + minuto_pago.toString() + " estado_promesa: " + estado_promesa + " observacion: " + observacion + " monto:" + monto, 1);
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
            Integer promesa_pago,
            Integer deudor,
            Calendar fecha_ingreso,
            Calendar fecha_pago,
            Integer hora_pago,
            Integer minuto_pago,
            String estado_promesa,
            String observacion,
            Double monto) {
        
        String resultado = "";
        
        Integer dia = fecha_ingreso.get(Calendar.DATE);
        Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
        Integer ano = fecha_ingreso.get(Calendar.YEAR);
        String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_pago.get(Calendar.DATE);
        mes = fecha_pago.get(Calendar.MONTH) + 1;
        ano = fecha_pago.get(Calendar.YEAR);
        String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "update promesa_pago set "
                + "deudor='" + deudor.toString() + "', "
                + "fecha_ingreso='" + fecha_ingreso_t + "', "
                + "fecha_pago='" + fecha_pago_t + "', "
                + "hora_pago='" + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "', "
                + "estado_promesa='" + estado_promesa + "', "
                + "observacion='" + observacion + "', "
                + "monto='" + monto + "' "
                + "where promesa_pago=" + promesa_pago.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Recordatorio modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Promesa de Pago modificado=> Deudor: " + deudor + " fecha_ingreso: " + fecha_ingreso_t + " fecha_pago: " + fecha_pago + " hora_pago " + hora_pago + ":" + minuto_pago.toString() + " estado_promesa: " + estado_promesa + " observacion: " + observacion + " monto:" + monto, 1);
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

    public Promesa_Pago obtener(Integer seleccion) {
        String cadenasql = "select u.deudor, u.fecha_ingreso, u.fecha_pago, u.hora_pago, u.estado_promesa, u.observacion, u.monto from promesa_pago u where u.promesa_pago=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.fecha_ingreso = this.DateToCalendar(rs.getDate(2));
                    this.fecha_pago = this.DateToCalendar(rs.getDate(3));
                    this.hora_pago = rs.getTime(4).getHours();
                    this.minuto_pago = rs.getTime(4).getMinutes();
                    this.estado_promesa = rs.getString(5);
                    this.observacion = rs.getString(6);
                    this.monto = rs.getDouble(7);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from promesa_pago where promesa_pago=" + seleccion.toString();
        
        try {
            Promesa_Pago promesa_pago_temp = new Promesa_Pago(conn, usuario_sys);
            promesa_pago_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Promesa de Pago eliminada.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Promesa de Pago eliminado=> " + " Promesa Pago: " + seleccion + " Deudor: " + deudor + " fecha_ingreso: " + promesa_pago_temp.fecha_ingreso + " fecha_pago_t: " + promesa_pago_temp.fecha_pago + " estado_promesa: " + promesa_pago_temp.estado_promesa + " observacion: " + promesa_pago_temp.observacion + " monto: " + promesa_pago_temp.monto, 1);
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
