package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Asignacion_Cartera {

    Connection conn;
    Integer usuario_sys;
    
    public Calendar fecha;
    public Calendar fecha_promesa_pago;
    public Calendar fecha_casos_por_fecha;
    
    public DefaultListModel lstCodigoContactabilidadAsignadas;

    public Asignacion_Cartera(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }
    
    public String insertar(
            Calendar fecha,
            Calendar fecha_promesa_pago,
            Calendar fecha_casos_por_fecha,
            DefaultListModel lstCodigoContactabilidadAsignadas) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_asignacion = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_promesa_pago.get(Calendar.DATE);
        mes = fecha_promesa_pago.get(Calendar.MONTH) + 1;
        ano = fecha_promesa_pago.get(Calendar.YEAR);
        String fecha_promesa_pago_f = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_casos_por_fecha.get(Calendar.DATE);
        mes = fecha_casos_por_fecha.get(Calendar.MONTH) + 1;
        ano = fecha_casos_por_fecha.get(Calendar.YEAR);
        String fecha_casos_por_fecha_f = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "insert into asignacion_cartera ("
                    + "fecha, "
                    + "fecha_promesa_pago, "
                    + "fecha_casos_por_fecha) values ('"
                    + fecha_asignacion + "','"
                    + fecha_promesa_pago_f + "','"
                    + fecha_casos_por_fecha_f + "')";
                    
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            String maximo = "1";
            cadenasql = "select max(a.asignacion_cartera) from asignacion_cartera a";
            stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                maximo = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            for(Integer i=0; i<lstCodigoContactabilidadAsignadas.getSize(); i++) {
                cadenasql = "insert into asignacion_cartera_codigo_contactabilidad (asignacion_cartera, codigo_contactabilidad) values ('"
                        + maximo + "','"
                        + this.dar_codigo_contactabilidad_index(lstCodigoContactabilidadAsignadas.get(i).toString()) + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Asignación de Cartera registrada en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Asignacion Cartera registrado=> Fecha: " + fecha_asignacion + " fecha_promesa_pago: " + fecha_promesa_pago_f + " Fecha_Casos_Por_Fecha: " + fecha_casos_por_fecha_f, 1);
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
            Integer asignacion_cartera,
            Calendar fecha,
            Calendar fecha_promesa_pago,
            Calendar fecha_casos_por_fecha,
            DefaultListModel lstCodigoContactabilidadAsignadas) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_asignacion = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_promesa_pago.get(Calendar.DATE);
        mes = fecha_promesa_pago.get(Calendar.MONTH) + 1;
        ano = fecha_promesa_pago.get(Calendar.YEAR);
        String fecha_promesa_pago_f = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_casos_por_fecha.get(Calendar.DATE);
        mes = fecha_casos_por_fecha.get(Calendar.MONTH) + 1;
        ano = fecha_casos_por_fecha.get(Calendar.YEAR);
        String fecha_casos_por_fecha_f = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "update asignacion_cartera set "
                    + "fecha='" + fecha_asignacion + "', "
                    + "fecha_promesa_pago='" + fecha_promesa_pago_f + "', "
                    + "fecha_casos_por_fecha='" + fecha_casos_por_fecha_f + "' "
                    + "where asignacion_cartera=" + asignacion_cartera.toString();
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from asignacion_cartera_codigo_contactabilidad where asignacion_cartera=" + asignacion_cartera.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            for(Integer i=0; i<lstCodigoContactabilidadAsignadas.getSize(); i++) {
                cadenasql = "insert into asignacion_cartera_codigo_contactabilidad (asignacion_cartera, codigo_contactabilidad) values ('"
                        + asignacion_cartera.toString() + "','"
                        + this.dar_codigo_contactabilidad_index(lstCodigoContactabilidadAsignadas.get(i).toString()) + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Asignacion Cartera modificada en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Asignacion cartera modificado=> Fecha: " + fecha_asignacion + " fecha_promesa_pago: " + fecha_promesa_pago_f + " Fecha_Casos_Por_Fecha: " + fecha_casos_por_fecha_f, 1);
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
    
    public Asignacion_Cartera obtener(
            Integer seleccion,
            DefaultListModel lstCodigoContactabilidadAsignadas) {
        
        String cadenasql = "select "
                + "u.fecha, "
                + "u.fecha_promesa_pago, "
                + "u.fecha_casos_por_fecha "
                + "from asignacion_cartera u where u.asignacion_cartera=" + seleccion.toString();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                this.fecha = this.DateToCalendar(rs.getDate(1));
                this.fecha_promesa_pago = this.DateToCalendar(rs.getDate(2));
                this.fecha_casos_por_fecha = this.DateToCalendar(rs.getDate(3));
            }
            rs.close();
            stmt.close();
            
            this.lstCodigoContactabilidadAsignadas = lstCodigoContactabilidadAsignadas;
            cadenasql = "select c.nombre from asignacion_cartera_codigo_contactabilidad a left join codigo_contactabilidad c on (a.codigo_contactabilidad=c.codigo_contactabilidad) where a.asignacion_cartera=" + seleccion.toString();
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.lstCodigoContactabilidadAsignadas.addElement(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Asignacion_Cartera obtener_eliminar(Integer seleccion) {
        String cadenasql = "select "
                + "u.fecha, "
                + "u.fecha_promesa_pago, "
                + "u.fecha_casos_por_fecha "
                + "from asignacion_cartera u where u.asignacion_cartera=" + seleccion.toString();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                this.fecha = this.DateToCalendar(rs.getDate(1));
                this.fecha_promesa_pago = this.DateToCalendar(rs.getDate(2));
                this.fecha_casos_por_fecha = this.DateToCalendar(rs.getDate(3));
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }

    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from asignacion_cartera where asignacion_cartera= " + seleccion.toString();
        
        try {
            Asignacion_Cartera asignacion_cartera_temp = new Asignacion_Cartera(conn, usuario_sys);
            asignacion_cartera_temp.obtener_eliminar(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Asignación Cartera eliminada.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Asignacion Cartera eliminada=> " + " Fecha: " + asignacion_cartera_temp.fecha + " fecha_promesa_pago: " + asignacion_cartera_temp.fecha_promesa_pago + " Fecha_Casos_Por_Fecha: " + asignacion_cartera_temp.fecha_casos_por_fecha, 1);
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

    public DefaultListModel cargar_codigo_contactabilidad() {
        DefaultListModel modelo = new DefaultListModel();
        
        String cadenasql = "select c.nombre from codigo_contactabilidad c where c.estado='VIGENTE' order by c.nombre";
        try {
            Statement stmt = conn.createStatement();        
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }
            rs.close();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }

        return modelo;
    }
    
    public String dar_codigo_contactabilidad_index(String codigo_contactabilidad) {
        String dato = "";
        
        String cadenasql = "select c.codigo_contactabilidad from codigo_contactabilidad c where c.nombre ='" + codigo_contactabilidad + "'"; 
        try {
            Statement stmt = conn.createStatement();        
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            rs.close();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
        
        return dato;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
}
