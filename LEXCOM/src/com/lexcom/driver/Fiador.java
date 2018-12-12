package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Fiador {

    Connection conn;
    Integer usuario_sys;
    
    public Integer indice;
    public Integer deudor;
    public String dpi;
    public String nit;
    public Calendar fecha_nacimiento;
    public String nombre;
    public String nacionalidad;
    public String telefono;
    public String direccion;
    public Integer zona;
    public String pais;
    public String departamento;
    public String sexo;
    public String estado_civil;
    public String profesion;
    public String correo_electronico;
    public String descripcion;

    public Fiador(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            Integer deudor, 
            String dpi, 
            String nit, 
            Calendar fecha_nacimiento, 
            String nombre,
            String nacionalidad,
            String telefono,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            String profesion,
            String correo_electronico,
            String descripcion) {
        String resultado = "";
        
        Integer dia = fecha_nacimiento.get(Calendar.DATE);
        Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
        Integer ano = fecha_nacimiento.get(Calendar.YEAR);
        String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "insert into fiador ("
                + "deudor," 
                + "dpi," 
                + "nit," 
                + "fecha_nacimiento," 
                + "nombre," 
                + "nacionalidad," 
                + "telefono," 
                + "direccion," 
                + "zona," 
                + "pais," 
                + "departamento," 
                + "sexo," 
                + "estado_civil," 
                + "profesion," 
                + "correo_electronico," 
                + "descripcion) values ('"
                + deudor.toString() + "','"
                + dpi + "','"
                + nit + "','"
                + fecha + "','"
                + nombre + "','"
                + nacionalidad + "','"
                + telefono + "','"
                + direccion + "','"
                + zona.toString() + "','"
                + pais + "','"
                + departamento + "','"
                + sexo + "','"
                + estado_civil + "','"
                + profesion + "','"
                + correo_electronico + "','"
                + descripcion + "')";
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Fiador registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Fiador registrado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit, 1);
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
            Integer fiador,
            Integer deudor,
            String dpi, 
            String nit, 
            Calendar fecha_nacimiento, 
            String nombre,
            String nacionalidad,
            String telefono,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            String profesion,
            String correo_electronico,
            String descripcion) {
        String resultado = "";
        
        Integer dia = fecha_nacimiento.get(Calendar.DATE);
        Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
        Integer ano = fecha_nacimiento.get(Calendar.YEAR);
        String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        String cadenasql = "update fiador set "
                + "deudor='" + deudor + "', "
                + "dpi='" + dpi + "', "
                + "nit='" + nit + "', "
                + "fecha_nacimiento='" + fecha + "', "
                + "nombre='" + nombre + "', "
                + "nacionalidad='" + nacionalidad + "', "
                + "telefono='" + telefono + "', "
                + "direccion='" + direccion + "', "
                + "zona='" + zona + "', "
                + "pais='" + pais + "', "
                + "departamento='" + departamento + "', "
                + "sexo='" + sexo + "', "
                + "estado_civil='" + estado_civil + "', "
                + "profesion='" + profesion + "', "
                + "correo_electronico='" + correo_electronico + "', "
                + "descripcion='" + descripcion + "' "
                + "where fiador=" + fiador.toString();
        
        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Fiador modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Fiador modificado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit, 1);
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

    public Fiador obtener(Integer seleccion) {
        String cadenasql = "SELECT f.deudor, f.dpi, f.nit, f.fecha_nacimiento, f.nombre, f.nacionalidad, f.telefono, f.direccion, f.zona, f.pais, f.departamento, f.sexo, f.estado_civil, f.profesion, f.correo_electronico, f.descripcion FROM fiador f where f.fiador=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.dpi = rs.getString(2);
                    this.nit = rs.getString(3);
                    this.fecha_nacimiento = this.DateToCalendar(rs.getDate(4));
                    this.nombre = rs.getString(5);
                    this.nacionalidad = rs.getString(6);
                    this.telefono = rs.getString(7);
                    this.direccion = rs.getString(8);
                    this.zona = rs.getInt(9);
                    this.pais = rs.getString(10);
                    this.departamento = rs.getString(11);
                    this.sexo = rs.getString(12);
                    this.estado_civil = rs.getString(13);
                    this.profesion = rs.getString(14);
                    this.correo_electronico = rs.getString(15);
                    this.descripcion = rs.getString(16);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from fiador where fiador= " + seleccion.toString();
        
        try {
            Fiador fiador_temp = new Fiador(conn, usuario_sys);
            fiador_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Fiador eliminado.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Fiador eliminado=> " + " Fiador: " + seleccion + "  Deudor: " + fiador_temp.deudor + " dpi:" + fiador_temp.dpi + " nit:" + fiador_temp.nit, 1);
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
    
    public DefaultComboBoxModel dar_lista_fiador(Integer deudor) {
        DefaultComboBoxModel lista_cbx = new DefaultComboBoxModel();
        String cadenasql = "select u.nombre from fiador u where u.deudor=" + deudor.toString() + " order by u.nombre";

        try {
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery(cadenasql)) {
                lista_cbx.addElement("SIN FIADOR");
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
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
