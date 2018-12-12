package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Juicio {

    Connection conn;
    Integer usuario_sys;
    
    public DefaultTableModel modelo_arraigo;
    public DefaultTableModel modelo_banco;
    public DefaultTableModel modelo_finca;
    public DefaultTableModel modelo_salario;
    public DefaultTableModel modelo_vehiculo;
    public DefaultTableModel modelo_otros;
    public Integer indice;
    public Integer deudor;
    public Integer juzgado;
    public Calendar fecha;
    public String no_juicio;
    public Double monto;
    public String descripcion;
    public Integer procurador;
    public Integer notificador;
    public String abogado_deudor;
    public String sumario;
    public Calendar memorial;
    public String procuracion;
    public Calendar fecha_admision_demanda;
    public String deudor_notificado;
    public Calendar fecha_notificacion;
    public String razon_notificacion;
    public String depositario;
    public String tiempo_estimado;

    public Juicio(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            Integer deudor, 
            Integer juzgado, 
            Calendar fecha, 
            String no_juicio, 
            Double monto, 
            String descripcion, 
            DefaultTableModel modelo_arraigo, 
            DefaultTableModel modelo_banco,
            DefaultTableModel modelo_finca, 
            DefaultTableModel modelo_salario,
            DefaultTableModel modelo_vehiculo,
            DefaultTableModel modelo_otros,
            Integer procurador,
            String razon_notificacion,
            Integer notificador,
            String abogado_deudor,
            String sumario,
            Calendar memorial,
            String procuracion,
            Calendar fecha_admision_demanda,
            String deudor_notificado,
            Calendar fecha_notificacion,
            String depositario,
            String tiempo_estimado) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_juicio = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = memorial.get(Calendar.DATE);
        mes = memorial.get(Calendar.MONTH) + 1;
        ano = memorial.get(Calendar.YEAR);
        String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_admision_demanda.get(Calendar.DATE);
        mes = fecha_admision_demanda.get(Calendar.MONTH) + 1;
        ano = fecha_admision_demanda.get(Calendar.YEAR);
        String fecha_admision = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_notificacion.get(Calendar.DATE);
        mes = fecha_notificacion.get(Calendar.MONTH) + 1;
        ano = fecha_notificacion.get(Calendar.YEAR);
        String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "insert into juicio ("
                    + "deudor, "
                    + "juzgado, "
                    + "fecha, "
                    + "no_juicio, "
                    + "monto, "
                    + "descripcion, "
                    + "procurador, "
                    + "razon_notificacion, "
                    + "notificador, "
                    + "abogado_deudor, "
                    + "sumario, "
                    + "memorial, "
                    + "procuracion, "
                    + "fecha_admision_demanda, "
                    + "deudor_notificado, "
                    + "fecha_notificacion, "
                    + "depositario, "
                    + "tiempo_estimado) values ('"
                    + deudor.toString() + "','"
                    + juzgado.toString() + "','"
                    + fecha_juicio + "','"
                    + no_juicio + "','"
                    + monto.toString() + "','"
                    + descripcion + "','"
                    + procurador + "','"
                    + razon_notificacion + "','"
                    + notificador + "','"
                    + abogado_deudor + "','"
                    + sumario + "','"
                    + fecha_memorial +  "','"
                    + procuracion + "','"
                    + fecha_admision +  "','"
                    + deudor_notificado + "','"
                    + fecha_noti + "','"
                    + depositario + "','"
                    + tiempo_estimado + "')";
                    
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            String maximo = "1";
            cadenasql = "select max(j.juicio) from juicio j";
            stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                maximo = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            for(Integer i=0; i<modelo_arraigo.getRowCount(); i++) {
                cadenasql = "insert into juicio_arraigo (juicio, correlativo, arraigo, deligenciado) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_arraigo.getValueAt(i, 0).toString() + "','"
                        + modelo_arraigo.getValueAt(i, 1).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_banco.getRowCount(); i++) {
                cadenasql = "insert into juicio_banco (juicio, correlativo, bancos, deligenciado) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_banco.getValueAt(i, 0).toString() + "','"
                        + modelo_banco.getValueAt(i, 1).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_finca.getRowCount(); i++) {
                cadenasql = "insert into juicio_finca (juicio, correlativo, finca, letra, deligenciado, tramite) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_finca.getValueAt(i, 1).toString() + "','"
                        + modelo_finca.getValueAt(i, 2).toString() + "','"
                        + modelo_finca.getValueAt(i, 3).toString() + "','"
                        + modelo_finca.getValueAt(i, 0).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_salario.getRowCount(); i++) {
                cadenasql = "insert into juicio_salario (juicio, correlativo, salario, empresa, deligenciado) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_salario.getValueAt(i, 0).toString() + "','"
                        + modelo_salario.getValueAt(i, 1).toString() + "','"
                        + modelo_salario.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_vehiculo.getRowCount(); i++) {
                cadenasql = "insert into juicio_vehiculo (juicio, correlativo, vehiculo, medida, deligenciado) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 0).toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 1).toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_otros.getRowCount(); i++) {
                cadenasql = "insert into juicio_otros (juicio, correlativo, otros, medida, deligenciado) values ('"
                        + maximo + "','"
                        + i.toString() + "','"
                        + modelo_otros.getValueAt(i, 0).toString() + "','"
                        + modelo_otros.getValueAt(i, 1).toString() + "','"
                        + modelo_otros.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Juicio registrado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Juicio registrado=> Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " monto: " + monto.toString() + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " fecha_adminision_demanda: " + fecha_admision + " deudor_notificado: " + deudor_notificado + " fecha_notificacion: " + fecha_noti + " depositario: " + depositario + " tiempo_estamado: " + tiempo_estimado, 1);
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
            Integer juicio, 
            Integer deudor, 
            Integer juzgado, 
            Calendar fecha, 
            String no_juicio, 
            Double monto, 
            String descripcion, 
            DefaultTableModel modelo_arraigo, 
            DefaultTableModel modelo_banco,
            DefaultTableModel modelo_finca, 
            DefaultTableModel modelo_salario,
            DefaultTableModel modelo_vehiculo,
            DefaultTableModel modelo_otros,
            Integer procurador,
            String razon_notificacion,
            Integer notificador,
            String abogado_deudor,
            String sumario,
            Calendar memorial,
            String procuracion,
            Calendar fecha_admision_demanda,
            String deudor_notificado,
            Calendar fecha_notificacion,
            String depositario,
            String tiempo_estimado) {
        String resultado = "";
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_juicio = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = memorial.get(Calendar.DATE);
        mes = memorial.get(Calendar.MONTH) + 1;
        ano = memorial.get(Calendar.YEAR);
        String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_admision_demanda.get(Calendar.DATE);
        mes = fecha_admision_demanda.get(Calendar.MONTH) + 1;
        ano = fecha_admision_demanda.get(Calendar.YEAR);
        String fecha_admision = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        dia = fecha_notificacion.get(Calendar.DATE);
        mes = fecha_notificacion.get(Calendar.MONTH) + 1;
        ano = fecha_notificacion.get(Calendar.YEAR);
        String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "update juicio set "
                    + "deudor='" + deudor + "', "
                    + "juzgado='" + juzgado + "', "
                    + "fecha='" + fecha_juicio + "', "
                    + "no_juicio='" + no_juicio + "', "
                    + "monto='" + monto.toString() + "', "
                    + "descripcion='" + descripcion + "', "
                    + "procurador='" + procurador.toString() + "', "
                    + "razon_notificacion='" + razon_notificacion + "', "
                    + "notificador='" + notificador.toString() + "', "
                    + "abogado_deudor='" + abogado_deudor + "', "
                    + "sumario='" + sumario + "', "
                    + "memorial='" + fecha_memorial + "', "
                    + "procuracion='" + procuracion + "', "
                    + "fecha_admision_demanda='" + fecha_admision + "', "
                    + "deudor_notificado='" + deudor_notificado + "', "
                    + "fecha_notificacion='" + fecha_noti + "', "
                    + "depositario='" + depositario + "', "
                    + "tiempo_estimado='" + tiempo_estimado + "' "
                    + "where juicio=" + juicio;
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_arraigo where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_banco where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_finca where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_salario where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_vehiculo where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "delete from juicio_otros where juicio=" + juicio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            for(Integer i=0; i<modelo_arraigo.getRowCount(); i++) {
                cadenasql = "insert into juicio_arraigo (juicio, correlativo, arraigo, deligenciado) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_arraigo.getValueAt(i, 0).toString() + "','"
                        + modelo_arraigo.getValueAt(i, 1).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_banco.getRowCount(); i++) {
                cadenasql = "insert into juicio_banco (juicio, correlativo, bancos, deligenciado) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_banco.getValueAt(i, 0).toString() + "','"
                        + modelo_banco.getValueAt(i, 1).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_finca.getRowCount(); i++) {
                cadenasql = "insert into juicio_finca (juicio, correlativo, finca, letra, deligenciado, tramite) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_finca.getValueAt(i, 1).toString() + "','"
                        + modelo_finca.getValueAt(i, 2).toString() + "','"
                        + modelo_finca.getValueAt(i, 3).toString() + "','"
                        + modelo_finca.getValueAt(i, 0).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_salario.getRowCount(); i++) {
                cadenasql = "insert into juicio_salario (juicio, correlativo, salario, empresa, deligenciado) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_salario.getValueAt(i, 0).toString() + "','"
                        + modelo_salario.getValueAt(i, 1).toString() + "','"
                        + modelo_salario.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_vehiculo.getRowCount(); i++) {
                cadenasql = "insert into juicio_vehiculo (juicio, correlativo, vehiculo, medida, deligenciado) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 0).toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 1).toString() + "','"
                        + modelo_vehiculo.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            for(Integer i=0; i<modelo_otros.getRowCount(); i++) {
                cadenasql = "insert into juicio_otros (juicio, correlativo, otros, medida, deligenciado) values ('"
                        + juicio.toString() + "','"
                        + i.toString() + "','"
                        + modelo_otros.getValueAt(i, 0).toString() + "','"
                        + modelo_otros.getValueAt(i, 1).toString() + "','"
                        + modelo_otros.getValueAt(i, 2).toString() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Juicio modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Juicio modificado=>  Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " monto: " + monto.toString() + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " fecha_adminision_demanda: " + fecha_admision + " deudor_notificado: " + deudor_notificado + " fecha_notificacion: " + fecha_noti + " depositario: " + depositario + " tiempo_estamado: " + tiempo_estimado, 1);
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

    public Juicio obtener(
            Integer seleccion, 
            DefaultTableModel modelo_arraigo,
            DefaultTableModel modelo_banco,
            DefaultTableModel modelo_finca,
            DefaultTableModel modelo_salario,
            DefaultTableModel modelo_vehiculo,
            DefaultTableModel modelo_otros) {
        
        String cadenasql = "select "
                + "u.deudor, "
                + "u.juzgado, "
                + "u.fecha, "
                + "u.no_juicio, "
                + "u.monto, "
                + "u.descripcion, "
                + "u.procurador, "
                + "u.razon_notificacion, "
                + "u.notificador, "
                + "u.abogado_deudor, "
                + "u.sumario, "
                + "u.memorial, "                
                + "u.procuracion, "
                + "u.fecha_admision_demanda, "
                + "u.deudor_notificado, "
                + "u.fecha_notificacion, "
                + "u.depositario, "
                + "u.tiempo_estimado "
                + "from juicio u where u.juicio=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    this.deudor = rs.getInt(1);
                    this.juzgado = rs.getInt(2);
                    this.fecha = this.DateToCalendar(rs.getDate(3));
                    this.no_juicio = rs.getString(4);
                    this.monto = rs.getDouble(5);
                    this.descripcion = rs.getString(6);
                    this.procurador = rs.getInt(7);
                    this.razon_notificacion = rs.getString(8);
                    this.notificador = rs.getInt(9);
                    this.abogado_deudor = rs.getString(10);
                    this.sumario = rs.getString(11);
                    this.memorial = this.DateToCalendar(rs.getDate(12));
                    this.procuracion = rs.getString(13);
                    this.fecha_admision_demanda = this.DateToCalendar(rs.getDate(14));
                    this.deudor_notificado = rs.getString(15);
                    this.fecha_notificacion = this.DateToCalendar(rs.getDate(16));
                    this.depositario = rs.getString(17);
                    this.tiempo_estimado = rs.getString(18);
                }
            }
            
            this.modelo_arraigo = modelo_arraigo;
            cadenasql = "select ja.arraigo, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d') from juicio_arraigo ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs.getObject(1);
                    fila[1] = rs.getObject(2);
                    this.modelo_arraigo.addRow(fila);
                }
            }
            
            this.modelo_banco = modelo_banco;
            cadenasql = "select ja.bancos, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d') from juicio_banco ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs.getObject(1);
                    fila[1] = rs.getObject(2);
                    this.modelo_banco.addRow(fila);
                }
            }
            
            this.modelo_finca = modelo_finca;
            cadenasql = "select ja.finca, ja.letra, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d'), ja.tramite from juicio_finca ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getObject(4);
                    fila[1] = rs.getObject(1);
                    fila[2] = rs.getObject(2);
                    fila[3] = rs.getObject(3);
                    this.modelo_finca.addRow(fila);
                }
            }
            
            this.modelo_salario = modelo_salario;
            cadenasql = "select ja.salario, ja.empresa, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d') from juicio_salario ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    fila[0] = rs.getObject(1);
                    fila[1] = rs.getObject(2);
                    fila[2] = rs.getObject(3);
                    this.modelo_salario.addRow(fila);
                }
            }
            
            this.modelo_vehiculo = modelo_vehiculo;
            cadenasql = "select ja.vehiculo, ja.medida, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d') from juicio_vehiculo ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    fila[0] = rs.getObject(1);
                    fila[1] = rs.getObject(2);
                    fila[2] = rs.getObject(3);
                    this.modelo_vehiculo.addRow(fila);
                }
            }
            
            this.modelo_otros = modelo_otros;
            cadenasql = "select ja.otros, ja.medida, DATE_FORMAT(ja.deligenciado,'%Y/%m/%d') from juicio_otros ja where ja.juicio=" + seleccion.toString();
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    fila[0] = rs.getObject(1);
                    fila[1] = rs.getObject(2);
                    fila[2] = rs.getObject(3);
                    this.modelo_otros.addRow(fila);
                }
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this;
    }
    
    public Juicio obtener_eliminar(Integer seleccion) {
        String cadenasql = "select "
                + "u.deudor, "
                + "u.juzgado, "
                + "u.fecha, "
                + "u.no_juicio, "
                + "u.monto, "
                + "u.descripcion, "
                + "u.procurador, "
                + "u.razon_notificacion, "
                + "u.notificador, "
                + "u.abogado_deudor, "
                + "u.sumario, "
                + "u.memorial, "
                + "u.procuracion, "
                + "u.fecha_admision_demanda, "
                + "u.deudor_notificado, "
                + "u.fecha_notificacion, "
                + "u.depositario, "
                + "u.tiempo_estimado "
                + "from juicio u where u.juicio=" + seleccion.toString();
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.deudor = rs.getInt(1);
                this.juzgado = rs.getInt(2);
                this.fecha = this.DateToCalendar(rs.getDate(3));
                this.no_juicio = rs.getString(4);
                this.monto = rs.getDouble(5);
                this.descripcion = rs.getString(6);
                this.procurador = rs.getInt(7);
                this.razon_notificacion = rs.getString(8);
                this.notificador = rs.getInt(9);
                this.abogado_deudor = rs.getString(10);
                this.sumario = rs.getString(11);
                this.memorial = this.DateToCalendar(rs.getDate(12));
                this.procuracion = rs.getString(13);
                this.fecha_admision_demanda = this.DateToCalendar(rs.getDate(14));
                this.deudor_notificado = rs.getString(15);
                this.fecha_notificacion = this.DateToCalendar(rs.getDate(16));
                this.depositario = rs.getString(17);
                this.tiempo_estimado = rs.getString(18);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from juicio where juicio= " + seleccion.toString();
        
        try {
            Juicio juicio_temp = new Juicio(conn, usuario_sys);
            juicio_temp.obtener_eliminar(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Juicio eliminado.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Juicio eliminado=> " + " Juicio: " + seleccion + " Deudor: " + juicio_temp.deudor + " Juzgado: " + juicio_temp.juzgado + " Fecha: " + juicio_temp.fecha + " No_Juicio: " + juicio_temp.no_juicio + " Monto: " + juicio_temp.monto + " Descripcion: " + juicio_temp.descripcion + " Procurador: " + juicio_temp.procurador + " Razon: " + juicio_temp.razon_notificacion + " Notificador: " + juicio_temp.notificador + " Abogado Deudor: " + juicio_temp.abogado_deudor + " Sumario: " + juicio_temp.sumario + " Memorial: " + juicio_temp.memorial, 1);
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
    
    public Integer dar_juicio(Integer deudor) {
        Integer juicio = 0;
        String cadenasql = "select j.juicio from juicio j where j.deudor=" + deudor.toString();

        try {
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    juicio = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return juicio;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
