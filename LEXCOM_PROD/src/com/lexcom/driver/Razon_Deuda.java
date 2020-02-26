package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Razon_Deuda {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String descripcion;

    public Razon_Deuda(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String nombre, String estado, String descripcion) {
        String resultado = "";
        String cadenasql = "insert into razon_deuda (nombre, estado, descripcion) values ('"
                + nombre + "','"
                + estado + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,razon_deuda registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "razon_deuda creado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public String modificar(String nombre, Integer razon_deuda, String descripcion) {
        String resultado = "";
        String cadenasql = "update razon_deuda set "
                + "nombre='" + nombre + "', "
                + "descripcion='" + descripcion + "' "
                + "where razon_deuda=" + razon_deuda.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,razon_deuda modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "razon_deuda modificado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public Razon_Deuda obtener(Integer seleccion) {        
        String cadenasql = "select u.nombre, u.descripcion from razon_deuda u where u.razon_deuda=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.nombre = rs.getString(1);
                    this.descripcion = rs.getString(2);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.razon_deuda from razon_deuda u where u.nombre='" + nombre + "'";
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.indice = rs.getInt(1);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this.indice;
    }
    
    public String obtener_nombre(Integer indice) {
        String cadenasql = "select u.nombre from razon_deuda u where u.razon_deuda=" + indice;
        String resultado = "";
        
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    resultado = rs.getString(1);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "update razon_deuda set "
                + "estado='" + "ELIMINADO" + "' "
                + "where razon_deuda=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,razon_deuda fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "razon_deuda baja=> razon_deuda: " + seleccion, 1);
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
    
    public String activar(Integer seleccion) {
        String resultado = "";
        String cadenasql = "update razon_deuda set "
                + "estado='" + "VIGENTE" + "' "
                + "where razon_deuda=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,razon_deuda fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "razon_deuda alta=> razon_deuda: " + seleccion, 1);
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

    public DefaultComboBoxModel dar_lista() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from razon_deuda u where u.estado='VIGENTE' order by u.nombre";

        try {
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    lista_cbx.addElement(rs.getObject(1));
                }
            }
        } catch (SQLException ex) {
            lista_cbx.removeAllElements();
            lista_cbx.addElement("ERROR");
            System.out.println(ex.toString());
        }

        return lista_cbx;
    }
    
}
