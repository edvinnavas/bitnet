package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Codigo_Contactabilidad {

    Connection conn;
    Integer usuario_sys;
    public Integer indice;
    public String codigo;
    public String nombre;
    public String descripcion;

    public Codigo_Contactabilidad(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(String codigo, String nombre, String estado, String descripcion) {
        String resultado = "";
        String cadenasql = "insert into codigo_contactabilidad (codigo, nombre, estado, descripcion) values ('"
                + codigo + "','"
                + nombre + "','"
                + estado + "','"
                + descripcion + "')";

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Codigo de Contactabilidad registrado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Codigo de Contactabilidad creado=> Codigo: " + codigo + " Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public String modificar(String codigo, String nombre, Integer codigo_contactabilidad, String descripcion) {
        String resultado = "";
        String cadenasql = "update codigo_contactabilidad set "
                + "codigo='" + codigo + "', "
                + "nombre='" + nombre + "', "
                + "descripcion='" + descripcion + "' "
                + "where codigo_contactabilidad=" + codigo_contactabilidad.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Codigo de Contactabilidad modificado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Codigo de Contactabilidad modificado=> Codigo: " + codigo + " Nombre: " + nombre + " descripcion: " + descripcion, 1);
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

    public Codigo_Contactabilidad obtener(Integer seleccion) {
        String cadenasql = "select u.codigo, u.nombre, descripcion from codigo_contactabilidad u where u.codigo_contactabilidad=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.codigo = rs.getString(1);
                    this.nombre = rs.getString(2);
                    this.descripcion = rs.getString(3);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return this;
    }

    public Integer obtener_indice(String nombre) {
        String cadenasql = "select u.codigo_contactabilidad from codigo_contactabilidad u where concat(u.codigo,'|',u.nombre)='" + nombre + "'";
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.indice = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return this.indice;
    }

    public String obtener_nombre(Integer indice) {
        String cadenasql = "select u.nombre from codigo_contactabilidad u where u.codigo_contactabilidad=" + indice;
        String resultado = "";

        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    resultado = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public String eliminar(Integer seleccion) {
        String resultado = "";
        String cadenasql = "update codigo_contactabilidad set "
                + "estado='" + "ELIMINADO" + "' "
                + "where codigo_contactabilidad=" + seleccion.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Codigo de Contactabilidad fue dado de baja.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Codigo de Contactabilidad baja=> Codigo de Contactabilidad: " + seleccion, 1);
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
        String cadenasql = "update codigo_contactabilidad set "
                + "estado='" + "VIGENTE" + "' "
                + "where codigo_contactabilidad=" + seleccion.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Codigo de Contactabilidad fue dado de alta.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Codigo de Contactabilidad alta=> Codigo de Contactabilidad: " + seleccion, 1);
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
        String cadenasql = "select concat(u.codigo,'|',u.nombre) from codigo_contactabilidad u where u.estado='VIGENTE' order by u.nombre";
        lista_cbx.addElement("Seleccione uno");
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
    
    public DefaultComboBoxModel dar_lista_comb(String tipo_codigo_contactabilidad, String contacto) {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select "
                + "distinct concat(cc.codigo,'|',cc.nombre) codigo "
                + "from "
                + "tipo_codigo_codigo tcc "
                + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                + "where "
                + "tcci.nombre = '" + tipo_codigo_contactabilidad + "' and "
                + "tcc.contacto like '" + contacto + "'"
                + "order by "
                + "cc.nombre";
        lista_cbx.addElement("Seleccione uno");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                lista_cbx.addElement(rs.getObject(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            lista_cbx.removeAllElements();
            lista_cbx.addElement("ERROR");
            System.out.println(ex.toString());
        }

        return lista_cbx;
    }
}
