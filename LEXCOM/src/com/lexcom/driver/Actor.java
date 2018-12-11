package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Actor {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String nit;
    public String telefono;
    public String descripcion;
    public Integer cooperacion;
    public String digitalizados;

    public Actor(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            String nombre, 
            String nit, 
            String telefono, 
            String estado, 
            String descripcion, 
            Integer cooperacion,
            String digitalizados) {
        
        String resultado = "";
        String cadenasql = "insert into actor (nombre, nit, telefono, estado, descripcion, cooperacion, digitalizados) values ('"
                + nombre + "','"
                + nit + "','"
                + telefono + "','"
                + estado + "','"
                + descripcion + "','"
                + cooperacion + "','"
                + digitalizados + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Actor registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Actor creado=> Nombre: " + nombre + " Nit: " + nit + " Telefono: " + telefono + " descripcion: " + descripcion + " cooperacion: " + cooperacion + " Digitalizados: " + digitalizados, 1);
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
            String nombre, 
            String nit, 
            String telefono, 
            Integer actor, 
            String descripcion, 
            Integer cooperacion,
            String digitalizados) {
        
        String resultado = "";
        String cadenasql = "update actor set "
                + "nombre='" + nombre + "', "
                + "nit='" + nit + "', "
                + "telefono='" + telefono + "', "
                + "cooperacion='" + cooperacion + "', "
                + "descripcion='" + descripcion + "', "
                + "digitalizados='" + digitalizados + "' "
                + "where actor=" + actor.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Actor modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Actor modificado=> Nombre: " + nombre + " Nit: " + nit + " Telefono: " + telefono + " descripcion: " + descripcion + " cooperacion: " + cooperacion + " Digitalizados: " + digitalizados, 1);
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

    public Actor obtener(Integer seleccion) {
        String cadenasql = "select "
                + "u.nombre, "
                + "u.nit, "
                + "u.telefono, "
                + "u.descripcion, "
                + "u.cooperacion, "
                + "u.digitalizados "
                + "from "
                + "actor u "
                + "where u.actor=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.nombre = rs.getString(1);
                    this.nit = rs.getString(2);
                    this.telefono = rs.getString(3);
                    this.descripcion = rs.getString(4);
                    this.cooperacion  = rs.getInt(5);
                    this.digitalizados = rs.getString(6);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.actor from actor u where u.nombre='" + nombre + "'";
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
        String cadenasql = "select u.nombre from actor u where u.actor=" + indice;
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
        String cadenasql = "update actor set "
                + "estado='" + "ELIMINADO" + "' "
                + "where actor=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Actor fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Actor baja=> Actor: " + seleccion, 1);
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
        String cadenasql = "update actor set "
                + "estado='" + "VIGENTE" + "' "
                + "where actor=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Actor fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Actor alta=> Actor: " + seleccion, 1);
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
        String cadenasql = "select u.nombre from actor u where u.estado='VIGENTE' order by u.nombre";

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
