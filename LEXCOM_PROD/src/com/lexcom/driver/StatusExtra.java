package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class StatusExtra {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String descripcion;

    public StatusExtra(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String nombre, String estado, String descripcion) {
        String resultado = "";
        String cadenasql = "insert into estatus_extra (nombre, estado, descripcion) values ('"
                + nombre + "','"
                + estado + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Status Extrajudicial registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Status Extrajudicial creado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
        } catch (Exception ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

    public String modificar(String nombre, Integer estatus, String descripcion) {
        String resultado = "";
        String cadenasql = "update estatus_extra set "
                + "nombre='" + nombre + "', "
                + "descripcion='" + descripcion + "' "
                + "where estatus_extra=" + estatus.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Status Extrajudicial modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Status Extrajudicial modificado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
        } catch (Exception ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

    public StatusExtra obtener(Integer seleccion) {
        String cadenasql = "select u.nombre, u.descripcion from estatus_extra u where u.estatus_extra=" + seleccion.toString();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.nombre = rs.getString(1);
                this.descripcion = rs.getString(2);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.estatus_extra from estatus_extra u where u.nombre='" + nombre + "'";
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.indice = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return this.indice;
    }
    
    public String obtener_nombre(Integer indice) {
        String cadenasql = "select u.nombre from estatus_extra u where u.estatus_extra=" + indice;
        String resultado = "";

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "update estatus_extra set "
                + "estado='" + "ELIMINADO" + "' "
                + "where estatus_extra=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Status Extrajudicial fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Status Extrajudicial baja=> Status: " + seleccion, 1);
        } catch (Exception ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }
    
    public String activar(Integer seleccion) {
        String resultado = "";
        String cadenasql = "update estatus_extra set "
                + "estado='" + "VIGENTE" + "' "
                + "where estatus_extra=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Status Extrajudicial fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Status Extrajudicial alta=> Status: " + seleccion, 1);
        } catch (Exception ex) {
            try {
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }

        return resultado;
    }

    public DefaultComboBoxModel dar_lista() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from estatus_extra u where u.estado='VIGENTE' order by u.nombre";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                lista_cbx.addElement(rs.getObject(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            lista_cbx.removeAllElements();
            lista_cbx.addElement("ERROR");
            System.out.println(ex.toString());
        }

        return lista_cbx;
    }
    
    public DefaultComboBoxModel dar_lista_comb(String estado) {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select "
                + "distinct ee.nombre "
                + "from "
                + "estado_status_extrajudicial ese "
                + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                + "where "
                + "se.nombre = '" + estado + "' "
                + "order by "
                + "ee.nombre";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                lista_cbx.addElement(rs.getObject(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            lista_cbx.removeAllElements();
            lista_cbx.addElement("ERROR");
            System.out.println(ex.toString());
        }

        return lista_cbx;
    }
    
    public DefaultComboBoxModel dar_lista_comb_vacio(String estado) {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select "
                + "distinct ee.nombre "
                + "from "
                + "estado_status_extrajudicial ese "
                + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                + "where "
                + "se.nombre = '" + estado + "' "
                + "order by "
                + "ee.nombre";

        try {
            lista_cbx.addElement("Seleccione...");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                lista_cbx.addElement(rs.getObject(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            lista_cbx.removeAllElements();
            lista_cbx.addElement("ERROR");
            System.out.println(ex.toString());
        }

        return lista_cbx;
    }
    
}
