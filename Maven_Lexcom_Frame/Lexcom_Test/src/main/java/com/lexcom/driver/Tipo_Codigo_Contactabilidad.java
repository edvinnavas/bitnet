package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Tipo_Codigo_Contactabilidad {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String descripcion;

    public Tipo_Codigo_Contactabilidad(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String nombre, String estado, String descripcion) {
        String resultado = "";
        String cadenasql = "insert into tipo_codigo_contactabilidad (nombre, estado, descripcion) values ('"
                + nombre + "','"
                + estado + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Tipo de Codigo Contactabilidad registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Tipo de Codigo Contactabilidad creado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public String modificar(String nombre, Integer tipo_codigo_contactabilidad, String descripcion) {
        String resultado = "";
        String cadenasql = "update tipo_codigo_contactabilidad set "
                + "nombre='" + nombre + "', "
                + "descripcion='" + descripcion + "' "
                + "where tipo_codigo_contactabilidad=" + tipo_codigo_contactabilidad.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Tipo de Codigo Contactabilidad modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Tipo de Codigo Contactabilidad modificado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public Tipo_Codigo_Contactabilidad obtener(Integer seleccion) {        
        String cadenasql = "select u.nombre, u.descripcion from tipo_codigo_contactabilidad u where u.tipo_codigo_contactabilidad=" + seleccion.toString();
        try {
            Statement stmt = this.conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                this.nombre = rs.getString(1);
                this.descripcion = rs.getString(2);
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.tipo_codigo_contactabilidad from tipo_codigo_contactabilidad u where u.nombre='" + nombre + "'";
        try {
            Statement stmt = this.conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                this.indice = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this.indice;
    }
    
    public String obtener_nombre(Integer indice) {
        String cadenasql = "select u.nombre from tipo_codigo_contactabilidad u where u.tipo_codigo_contactabilidad=" + indice;
        String resultado = "";
        
        try {
            Statement stmt = this.conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                this.indice = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "update tipo_codigo_contactabilidad set "
                + "estado='" + "ELIMINADO" + "' "
                + "where tipo_codigo_contactabilidad=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Tipo de codigo_contactabilidad fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Tipo de Codigo Contactabilidad baja=> Tipo de codigo_contactabilidad: " + seleccion, 1);
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
        String cadenasql = "update tipo_codigo_contactabilidad set "
                + "estado='" + "VIGENTE" + "' "
                + "where tipo_codigo_contactabilidad=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Tipo de codigo_contactabilidad fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Tipo de Ccodigo Contactabilidad alta=> Tipo de codigo_contactabilidad: " + seleccion, 1);
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
        String cadenasql = "select "
                + "distinct tct.nombre "
                + "from "
                + "tipo_codigo_codigo tcc "
                + "left join tipo_codigo_contactabilidad tct on (tcc.tipo_codigo_contactabilidad=tct.tipo_codigo_contactabilidad) "
                + "order by "
                + "tct.nombre";
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
