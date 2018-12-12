package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Usuario {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public String nombre_completo;
    public String nombre;
    public String contrasena;
    public String descripcion;
    public String gestor;
    public String procurador;
    public String asistente;
    public String digitador;
    public String investigador;
    public String tipo_usuario;

    public Usuario(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            String nombre_completo, 
            String nombre, 
            String contrasena,
            String estado, 
            String descripcion, 
            String gestor, 
            String procurador, 
            String asistente, 
            String digitador, 
            String investigador,
            String tipo_usuario) {
        String resultado = "";
        try {
            String cadenasql = "insert into usuario (nombre_completo,nombre,contrasena,estado,descripcion,gestor,procurador,asistente,digitador,investigador,tipo_usuario) values ('"
                    + nombre_completo + "','"
                    + nombre + "','"
                    + contrasena + "','"
                    + estado + "','"
                    + descripcion + "','"
                    + gestor + "','"
                    + procurador + "','"
                    + asistente + "','"
                    + digitador + "','"
                    + investigador + "','"
                    + tipo_usuario + "')";
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

            cadenasql = "select max(u.usuario) from usuario u";
            String usuario_temp = "select max(u.usuario) from usuario u";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_temp = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m where m.tipo=1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                cadenasql = "insert into permiso_usuario (usuario,menu,nuevo,modificar,eliminar,activar,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO','NO','NO','NO','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select m.menu from menu m where m.tipo=2";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                cadenasql = "insert into permiso_usuario_uno (usuario,menu,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();
            
            resultado = "1,Usuario registrado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Usuario creado=> Nombre_Completo: " + nombre_completo + " Nombre: " + nombre + " contraseña: " + contrasena + " descripcion:" + descripcion + " Gestor: " + gestor + " Procurador: " + procurador + " Asistente:" + asistente + " Digitador: " + digitador + " Investigador:" + investigador + " Tipo Usuario:" + tipo_usuario, 1);
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
            String nombre_completo, 
            String nombre, 
            String contrasena, 
            Integer usuario, 
            String descripcion, 
            String gestor, 
            String procurador, 
            String asistente, 
            String digitador, 
            String investigador,
            String tipo_usuario) {
        String resultado = "";
        String cadenasql = "update usuario set "
                + "nombre_completo='" + nombre_completo + "', "
                + "nombre='" + nombre + "', "
                + "contrasena='" + contrasena + "', "
                + "gestor='" + gestor + "', "
                + "procurador='" + procurador + "', "
                + "asistente='" + asistente + "', "
                + "digitador='" + digitador + "', "
                + "investigador='" + investigador + "', "
                + "descripcion='" + descripcion + "', "
                + "tipo_usuario='" + tipo_usuario + "' "
                + "where usuario=" + usuario.toString();
        
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            resultado = "1,Usuario modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Usuario modificado=> Nombre_Completo: " + nombre_completo + " Nombre: " + nombre + " contraseña: " + contrasena + " descripcion:" + descripcion + " Gestor: " + gestor + " Procurador: " + procurador + " Asistente:" + asistente + " Digitador: " + digitador + " Investigador:" + investigador + " Tipo Usuario:" + tipo_usuario, 1);
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

    public Usuario obtener(Integer seleccion) {        
        String cadenasql = "select u.nombre_completo, u.nombre, u.contrasena, u.descripcion, u.gestor, u.procurador, u.asistente, u.digitador, u.investigador, u.tipo_usuario from usuario u where u.usuario=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.nombre_completo = rs.getString(1);
                    this.nombre = rs.getString(2);
                    this.contrasena = rs.getString(3);
                    this.descripcion = rs.getString(4);
                    this.gestor = rs.getString(5);
                    this.procurador = rs.getString(6);
                    this.asistente = rs.getString(7);
                    this.digitador = rs.getString(8);
                    this.investigador = rs.getString(9);
                    this.tipo_usuario = rs.getString(10);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.usuario from usuario u where u.nombre='" + nombre + "'";
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
        String cadenasql = "select u.nombre from usuario u where u.usuario=" + indice;
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
        String cadenasql = "update usuario set "
                + "estado='" + "ELIMINADO" + "' "
                + "where usuario=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Usuario fue dado de baja.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Usuario baja=> Usuario: " + seleccion, 1);
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
        String cadenasql = "update usuario set "
                + "estado='" + "VIGENTE" + "' "
                + "where usuario=" + seleccion.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Usuario fue dado de alta.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Usuario alta=> Usuario: " + seleccion, 1);
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
    
    public DefaultComboBoxModel dar_lista_gestores() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";

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
    
    public DefaultComboBoxModel dar_lista_procuradores() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' and u.procurador='SI' order by u.nombre";

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
    
    public DefaultComboBoxModel dar_lista_asistentes() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' and u.asistente='SI' order by u.nombre";

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
    
    public DefaultComboBoxModel dar_lista_digitadores() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' and u.digitador='SI' order by u.nombre";

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
    
    public DefaultComboBoxModel dar_lista_investigadores() {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from usuario u where u.estado='VIGENTE' and u.investigador='SI' order by u.nombre";

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
    
    public Boolean validar_corporacion(Integer usuario, Integer deudor) {
        Boolean resultado = false;
        
        try {
            String cadenasql = "select a.cooperacion from deudor d left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) where d.deudor=" + deudor;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer corporacion = 0;
            while(rs.next()) {
                corporacion = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select uc.* from usuario_corporacion uc where uc.usuario=" + usuario + " and uc.corporacion=" + corporacion;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = true;
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = false;
        }

        return resultado;
    }
    
}
