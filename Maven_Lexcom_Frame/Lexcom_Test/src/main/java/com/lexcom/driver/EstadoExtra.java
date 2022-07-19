package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class EstadoExtra {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre;
    public String descripcion;

    public EstadoExtra(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String nombre, String estado, String descripcion) {
        String resultado = "";
        String cadenasql = "insert into sestado_extra (nombre, estado, descripcion) values ('"
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
            resultado = "1,Estado Extrajudicial registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Estado Extrajudicial creado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public String modificar(String nombre, Integer sestado, String descripcion) {
        String resultado = "";
        String cadenasql = "update sestado_extra set "
                + "nombre='" + nombre + "', "
                + "descripcion='" + descripcion + "' "
                + "where sestado_extra=" + sestado.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Estado Extrajudicial modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Estado Extrajudicial modificado=> Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public EstadoExtra obtener(Integer seleccion) {
        String cadenasql = "select u.nombre, u.descripcion from sestado_extra u where u.sestado_extra=" + seleccion.toString();
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
        String cadenasql = "select u.sestado_extra from sestado_extra u where u.nombre='" + nombre + "'";
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
        String cadenasql = "select u.nombre from sestado_extra u where u.sestado_extra=" + indice;
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
        String cadenasql = "update sestado_extra set "
                + "estado='" + "ELIMINADO" + "' "
                + "where sestado_extra=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Estado Extrajudicial fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Estado Extrajudicial baja=> Estado: " + seleccion, 1);
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
        String cadenasql = "update sestado_extra set "
                + "estado='" + "VIGENTE" + "' "
                + "where sestado_extra=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Estado Extrajudicial fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Estado Extrajudicial alta=> Estado: " + seleccion, 1);
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
        String cadenasql = "select u.nombre from sestado_extra u where u.estado='VIGENTE' order by u.nombre";

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
    
    public DefaultComboBoxModel dar_lista_comb() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select "
                + "distinct se.nombre "
                + "from "
                + "estado_status_extrajudicial ese "
                + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                + "order by "
                + "se.nombre";

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
    
    public DefaultComboBoxModel dar_lista_comb_vacio() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select "
                + "distinct se.nombre "
                + "from "
                + "estado_status_extrajudicial ese "
                + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                + "order by "
                + "se.nombre";

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
