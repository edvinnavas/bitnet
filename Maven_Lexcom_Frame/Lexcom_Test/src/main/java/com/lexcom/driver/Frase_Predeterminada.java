package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Frase_Predeterminada {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String tipo;
    public String frase;

    public Frase_Predeterminada(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String nombre, String tipo, String estado, String frase) {
        String resultado = "";
        String cadenasql = "insert into frase_predeterminada (nombre, tipo, estado, frase) values ('"
                + nombre + "','"
                + tipo + "','"
                + estado + "','"
                + frase + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Frase Predeterminada registrada en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Frase Predeterminada creada=> Nombre: " + nombre + " tipo: " + tipo + " frase: " + frase, 1);
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

    public String modificar(String nombre, String tipo, Integer frase_predeterminada, String frase) {
        String resultado = "";
        String cadenasql = "update frase_predeterminada set "
                + "nombre='" + nombre + "', "
                + "tipo='" + tipo + "', "
                + "frase='" + frase + "' "
                + "where frase_predeterminada=" + frase_predeterminada.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Frase Predeterminada modificada en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Frase Predeterminada modificada=> Nombre: " + nombre + " tipo: " + tipo + " frase: " + frase, 1);
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

    public Frase_Predeterminada obtener(Integer seleccion) {        
        String cadenasql = "select u.nombre, u.tipo, u.frase from frase_predeterminada u where u.frase_predeterminada=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.nombre = rs.getString(1);
                    this.tipo = rs.getString(2);
                    this.frase = rs.getString(3);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.frase_predeterminada from frase_predeterminada u where u.nombre='" + nombre + "'";
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
        String cadenasql = "select u.nombre from frase_predeterminada u where u.frase_predeterminada=" + indice;
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
        String cadenasql = "update frase_predeterminada set "
                + "estado='" + "ELIMINADO" + "' "
                + "where frase_predeterminada=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,La Frase Predeterminada fue dada de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Frase Predeterminada baja=> Frase Predeterminada: " + seleccion, 1);
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
        String cadenasql = "update frase_predeterminada set "
                + "estado='" + "VIGENTE" + "' "
                + "where frase_predeterminada=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Frase Predeterminada fue dada de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Frase Predeterminada alta=> Frase Predeterminada: " + seleccion, 1);
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
        String cadenasql = "select u.nombre from frase_predeterminada u where u.estado='VIGENTE' order by u.nombre";

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
