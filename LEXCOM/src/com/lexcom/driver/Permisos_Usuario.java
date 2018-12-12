package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;

public class Permisos_Usuario {

    Connection conn;
    Integer usuario_sys;
    
    public Integer usuario;
    public String menu;
    public String nuevo;
    public String modificar;
    public String eliminar;
    public String activar;
    public String ver;

    public Permisos_Usuario(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public DefaultListModel cargar_usuarios() {
        DefaultListModel modelo = new DefaultListModel();
        
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' order by u.nombre";
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
    
    public DefaultListModel cargar_menu() {
        DefaultListModel modelo = new DefaultListModel();
        
        String cadenasql = "select m.nombre from menu m where m.tipo=1 order by m.nombre";
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
    
    public DefaultListModel cargar_menu_uno() {
        DefaultListModel modelo = new DefaultListModel();
        
        String cadenasql = "select m.nombre from menu m where m.tipo=2 order by m.nombre";
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

    public String dar_usuario_index(String nombre_usuario) {
        String dato = "";
        
        String cadenasql = "Select usuario from usuario where estado='VIGENTE' and nombre ='" + nombre_usuario + "'"; 
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
    
    public String dar_menu_index(String nombre_menu) {
        String dato = "";
        
        String cadenasql = "select menu from menu where nombre ='" + nombre_menu + "'"; 
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
    
}
