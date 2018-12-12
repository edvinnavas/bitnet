package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Gestion_Pendiente_Cobros {

    Connection conn;
    Integer usuario_sys;
    
    public Integer deudor;
    public Integer tipo_aumento;
    public Calendar fecha;
    public Double monto;
    public String descripcion;

    public Gestion_Pendiente_Cobros(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public void insertar_btn_gestion(
            Integer deudor, 
            Calendar fecha, 
            Integer usuario, 
            Integer Codigo_Contactabiliad, 
            String descripcion,
            Integer sestado,
            Integer estatus,
            Integer sestado_extra,
            Integer estatus_extra,
            Integer intencion_pago,
            Integer razon_pago) {
        
        try {
            conn.setAutoCommit(false);
            
            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
            
            String cadenasql = "insert into deudor_historial_cobros_pendiente (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion, estado) values ('"
                    + deudor.toString() + "','"
                    + fecha_i + "','"
                    + "00:00:00" + "','"
                    + usuario.toString() + "','"
                    + Codigo_Contactabiliad.toString() + "','"
                    + descripcion + "','"
                    + "PENDIENTE" + "')";
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
           
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            String nombre = "";
            cadenasql = "select d.nombre from deudor d where d.deudor=" + deudor.toString();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            cadenasql = "select "
                    + "d.sestado, "
                    + "s.nombre, "
                    + "d.estatus, "
                    + "e.nombre, "
                    + "d.sestado_extra, "
                    + "sx.nombre, "
                    + "d.estatus_extra, "
                    + "ex.nombre "
                    + "from "
                    + "deudor d "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra sx on (d.sestado_extra=sx.sestado_extra) "
                    + "left join estatus_extra ex on (d.estatus_extra=ex.estatus_extra) where d.deudor=" + deudor.toString();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                int_estado_judicial_actual = rs.getInt(1);
                str_estado_judicial_actual = rs.getString(2);
                int_status_judicial_actual = rs.getInt(3);
                str_status_judicial_actual = rs.getString(4);
                int_estado_extrajudicial_actual = rs.getInt(5);
                str_estado_extrajudicial_actual = rs.getString(6);
                int_status_extrajudicial_actual = rs.getInt(7);
                str_status_extrajudicial_actual = rs.getString(8);
            }
            rs.close();
            stmt.close();

            // **************************** ACTUALIZAR DEUDOR
            cadenasql = "update deudor set "
                    + "sestado='" + sestado + "', "
                    + "estatus='" + estatus + "', "
                    + "sestado_extra='" + sestado_extra + "', "
                    + "estatus_extra='" + estatus_extra + "', "
                    + "intencion_pago='" + intencion_pago + "', "
                    + "razon_deuda='" + razon_pago + "' "
                    + "where deudor=" + deudor.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

            String usuario_nombre = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + this.usuario_sys;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
            if (!(sestado == int_estado_judicial_actual && estatus == int_status_judicial_actual)) {
                String str_sestado_judicial_nuevo = "";
                cadenasql = "select s.nombre from sestado s where s.sestado=" + sestado.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
            
                String str_estatus_judicial_nuevo = "";
                cadenasql = "select e.nombre from estatus e where e.estatus=" + estatus.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                cadenasql = "insert into historial_estatus ("
                        + "fecha, "
                        + "estatus, "
                        + "nombre_estatus, "
                        + "sestado, "
                        + "nombre_sestado, "
                        + "antiguo_estatus, "
                        + "antiguo_nombre_estatus, "
                        + "antiguo_sestado, "
                        + "antiguo_nombre_sestado, "
                        + "deudor, "
                        + "deudor_nombre,"
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + estatus.toString() + "','"
                        + str_estatus_judicial_nuevo + "','"
                        + sestado.toString() + "','"
                        + str_sestado_judicial_nuevo + "','"
                        + int_status_judicial_actual.toString() + "','"
                        + str_status_judicial_actual + "','"
                        + int_estado_judicial_actual.toString() + "','"
                        + str_estado_judicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre + "','"
                        + this.usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
            if (!(sestado_extra == int_estado_extrajudicial_actual && estatus_extra == int_status_extrajudicial_actual)) {
                String str_sestado_extrajudicial_nuevo = "";
                cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + sestado_extra.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
            
                String str_estatus_extrajudicial_nuevo = "";
                cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + estatus_extra.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                
                cadenasql = "insert into historial_estatus_extra ("
                        + "fecha, "
                        + "estatus_extra, "
                        + "nombre_estatus_extra, "
                        + "sestado_extra, "
                        + "nombre_sestado_extra, "
                        + "antiguo_estatus_extra, "
                        + "antiguo_nombre_estatus_extra, "
                        + "antiguo_sestado_extra, "
                        + "antiguo_nombre_sestado_extra, "
                        + "deudor, "
                        + "deudor_nombre, "
                        + "usuario, "
                        + "usuario_nombre) values ("
                        + "NOW()" + ",'"
                        + estatus_extra.toString() + "','"
                        + str_estatus_extrajudicial_nuevo + "','"
                        + sestado_extra.toString() + "','"
                        + str_sestado_extrajudicial_nuevo + "','"
                        + int_status_extrajudicial_actual.toString() + "','"
                        + str_status_extrajudicial_actual + "','"
                        + int_estado_extrajudicial_actual.toString() + "','"
                        + str_estado_extrajudicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre + "','"
                        + this.usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "3," + ex1.toString());
            }
        }
    }
    
}
