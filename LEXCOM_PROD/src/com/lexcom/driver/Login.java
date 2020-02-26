package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;

public class Login {

    Connection conn;
    Integer usuario;

    public Login(Connection conn) {
        this.conn = conn;
    }

    public DefaultComboBoxModel dar_usuarios() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' order by u.nombre";

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
    
    public int Login(String usuario, String contrasena) {
        int resultado = 0;
        String cadenasql = "select u.usuario, u.nombre, u.contrasena, u.reinicio from usuario u where u.estado='VIGENTE' and u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'" ;
        try {
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.usuario = rs.getInt(1);
                    if(rs.getInt(4)==1){
                        resultado=2;
                    }else{
                    resultado = 1;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            resultado = 0;
        }
        
        return resultado;
    }
    
    
    public int NewLogin(String usuario, String contrasena,String Nueva) {
        int resultado = 0;
        
        int login=0;
        String cadenasql = "select u.usuario, u.nombre, u.contrasena, u.reinicio from usuario u where u.estado='VIGENTE' and u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'" ;
       
        try {
             if(!contrasena.equals(Nueva)){
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.usuario = rs.getInt(1);
                    
                        login=1;
                    
                   
                }
                
                if(login==1){
                Statement stmtU = conn.createStatement();
                        stmtU.executeUpdate("UPDATE usuario set contrasena='"+Nueva+"' ,reinicio=0 where nombre= '"+usuario+"'");
                        stmtU.close();
                login=2;
                }else {
                    
                }
            }
            
             }else{
                 login=3;
                     }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            login = 0;
        }
        
        return login;
    }
    
    public Integer dar_usuario_login() {
        return usuario;
    }
}
