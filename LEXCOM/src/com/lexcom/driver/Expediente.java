package com.lexcom.driver;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Expediente {

    Connection conn;
    Integer usuario_sys;
    DefaultTableModel modelo;
    Integer deudor;
    Lista cartera;

    public Expediente(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public Boolean es_gestor(Integer usuario) {
        Boolean resultado = false;

        try {
            String cadenasql = "select u.gestor from usuario u where u.usuario='" + usuario + "'";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    resultado = true;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public Boolean es_procurador(Integer usuario) {
        Boolean resultado = false;

        try {
            String cadenasql = "select u.procurador from usuario u where u.usuario='" + usuario + "'";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    resultado = true;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public Boolean es_asistente(Integer usuario) {
        Boolean resultado = false;

        try {
            String cadenasql = "select u.asistente from usuario u where u.usuario='" + usuario + "'";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    resultado = true;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public Boolean es_digitador(Integer usuario) {
        Boolean resultado = false;

        try {
            String cadenasql = "select u.digitador from usuario u where u.usuario='" + usuario + "'";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    resultado = true;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public Boolean es_investigador(Integer usuario) {
        Boolean resultado = false;

        try {
            String cadenasql = "select u.investigador from usuario u where u.usuario='" + usuario + "'";
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    resultado = true;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public void generar_reporte_rotacion_credifacil() {
        try {
            String cadenasql = "SELECT "
                    + "TABLA.DEUDOR AS DEUDOR, "
                    + "TABLA.CASO AS CASO, "
                    + "TABLA.ANTIGUEDAD AS ANTIGUEDAD, "
                    + "TABLA.FECHA_RECEPCION AS FECHA_RECEPCION, "
                    + "TABLA.NO_CUENTA AS NO_CUENTA, "
                    + "TABLA.NOMBRE_DEUDOR AS NOMBRE_DEUDOR, "
                    + "TABLA.MONTO_INICIAL AS MONTO_INICIAL, "
                    + "TABLA.GARANTIA AS GARANTIA, "
                    + "TABLA.ESTADO AS ESTADO, "
                    + "TABLA.FECHA_PROXIMO_PAGO AS FECHA_PROXIMO_PAGO, "
                    + "TABLA.CUOTA AS CUOTA, "
                    + "TABLA.GESTOR AS GESTOR, "
                    + "TABLA.CODIGO_CONTACTABILIDAD AS CODIGO_CONTACTABILIDAD, "
                    + "TABLA.ACTOR AS ACTOR "
                    + "FROM ( "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "-1 AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "'SIN CARTERA' AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') "
                    + "UNION ALL "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "d.usuario AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "u.tipo_usuario AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "d.cargado='CARGADO' and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') and "
                    + "d.deudor not in (select d.deudor "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA')) "
                    + ") AS TABLA "
                    + "WHERE "
                    + "(TABLA.GARANTIA = 'CREDIFACIL' AND TABLA.TIPO_GESTOR = 'SIN CARTERA') OR "
                    + "(TABLA.GARANTIA = 'CREDIFACIL' AND TABLA.TIPO_GESTOR = 'CREDIFACIL')";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            cartera = new Lista();
            while (rs.next()) {
                cartera.insertar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
            }
            rs.close();
            stmt.close();
            rotacion("CREDIFACIL");
            this.cartera.tabla("temp_reporte_rotacion_credifacil", conn);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void generar_reporte_rotacion_oro() {
        try {
            String cadenasql = "SELECT "
                    + "TABLA.DEUDOR AS DEUDOR, "
                    + "TABLA.CASO AS CASO, "
                    + "TABLA.ANTIGUEDAD AS ANTIGUEDAD, "
                    + "TABLA.FECHA_RECEPCION AS FECHA_RECEPCION, "
                    + "TABLA.NO_CUENTA AS NO_CUENTA, "
                    + "TABLA.NOMBRE_DEUDOR AS NOMBRE_DEUDOR, "
                    + "TABLA.MONTO_INICIAL AS MONTO_INICIAL, "
                    + "TABLA.GARANTIA AS GARANTIA, "
                    + "TABLA.ESTADO AS ESTADO, "
                    + "TABLA.FECHA_PROXIMO_PAGO AS FECHA_PROXIMO_PAGO, "
                    + "TABLA.CUOTA AS CUOTA, "
                    + "TABLA.GESTOR AS GESTOR, "
                    + "TABLA.CODIGO_CONTACTABILIDAD AS CODIGO_CONTACTABILIDAD, "
                    + "TABLA.ACTOR AS ACTOR "
                    + "FROM ( "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "-1 AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "'SIN CARTERA' AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') "
                    + "UNION ALL "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "d.usuario AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "u.tipo_usuario AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "d.cargado='CARGADO' and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') and "
                    + "d.deudor not in (select d.deudor "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA')) "
                    + ") AS TABLA "
                    + "WHERE "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.ANTIGUEDAD = 'ORO' AND TABLA.TIPO_GESTOR = 'SIN CARTERA') OR "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.TIPO_GESTOR = 'ORO')";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            cartera = new Lista();
            while (rs.next()) {
                cartera.insertar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
            }
            rs.close();
            stmt.close();
            rotacion("ORO");
            this.cartera.tabla("temp_reporte_rotacion_oro", conn);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void generar_reporte_rotacion_plata() {
        try {
            String cadenasql = "SELECT "
                    + "TABLA.DEUDOR AS DEUDOR, "
                    + "TABLA.CASO AS CASO, "
                    + "TABLA.ANTIGUEDAD AS ANTIGUEDAD, "
                    + "TABLA.FECHA_RECEPCION AS FECHA_RECEPCION, "
                    + "TABLA.NO_CUENTA AS NO_CUENTA, "
                    + "TABLA.NOMBRE_DEUDOR AS NOMBRE_DEUDOR, "
                    + "TABLA.MONTO_INICIAL AS MONTO_INICIAL, "
                    + "TABLA.GARANTIA AS GARANTIA, "
                    + "TABLA.ESTADO AS ESTADO, "
                    + "TABLA.FECHA_PROXIMO_PAGO AS FECHA_PROXIMO_PAGO, "
                    + "TABLA.CUOTA AS CUOTA, "
                    + "TABLA.GESTOR AS GESTOR, "
                    + "TABLA.CODIGO_CONTACTABILIDAD AS CODIGO_CONTACTABILIDAD, "
                    + "TABLA.ACTOR AS ACTOR "
                    + "FROM ( "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "-1 AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "'SIN CARTERA' AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') "
                    + "UNION ALL "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "d.usuario AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "u.tipo_usuario AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "d.cargado='CARGADO' and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') and "
                    + "d.deudor not in (select d.deudor "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA')) "
                    + ") AS TABLA "
                    + "WHERE "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.ANTIGUEDAD = 'PLATA' AND TABLA.TIPO_GESTOR = 'SIN CARTERA') OR "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.TIPO_GESTOR = 'PLATA')";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            cartera = new Lista();
            while (rs.next()) {
                cartera.insertar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
            }
            rs.close();
            stmt.close();
            rotacion("PLATA");
            this.cartera.tabla("temp_reporte_rotacion_plata", conn);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void generar_reporte_rotacion_bronce() {
        try {
            String cadenasql = "SELECT "
                    + "TABLA.DEUDOR AS DEUDOR, "
                    + "TABLA.CASO AS CASO, "
                    + "TABLA.ANTIGUEDAD AS ANTIGUEDAD, "
                    + "TABLA.FECHA_RECEPCION AS FECHA_RECEPCION, "
                    + "TABLA.NO_CUENTA AS NO_CUENTA, "
                    + "TABLA.NOMBRE_DEUDOR AS NOMBRE_DEUDOR, "
                    + "TABLA.MONTO_INICIAL AS MONTO_INICIAL, "
                    + "TABLA.GARANTIA AS GARANTIA, "
                    + "TABLA.ESTADO AS ESTADO, "
                    + "TABLA.FECHA_PROXIMO_PAGO AS FECHA_PROXIMO_PAGO, "
                    + "TABLA.CUOTA AS CUOTA, "
                    + "TABLA.GESTOR AS GESTOR, "
                    + "TABLA.CODIGO_CONTACTABILIDAD AS CODIGO_CONTACTABILIDAD, "
                    + "TABLA.ACTOR AS ACTOR "
                    + "FROM ( "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "-1 AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "'SIN CARTERA' AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') "
                    + "UNION ALL "
                    + "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "d.usuario AS GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR, "
                    + "u.tipo_usuario AS TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "d.cargado='CARGADO' and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA') and "
                    + "d.deudor not in (select d.deudor "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(d.garantia = 7 or d.garantia = 13) and "
                    + "(d.actor = 1) and "
                    + "(d.codigo_contactabilidad <> 4) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.gestor = 'SI') and "
                    + "(u.tipo_usuario <> 'NO APLICA')) "
                    + ") AS TABLA "
                    + "WHERE "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.ANTIGUEDAD = 'BRONCE' AND TABLA.TIPO_GESTOR = 'SIN CARTERA') OR "
                    + "(TABLA.GARANTIA <> 'CREDIFACIL' AND TABLA.TIPO_GESTOR = 'BRONCE')";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            cartera = new Lista();
            while (rs.next()) {
                this.cartera.insertar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
            }
            rs.close();
            stmt.close();
            rotacion("BRONCE");
            this.cartera.tabla("temp_reporte_rotacion_bronce", conn);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void rotacion(String tipo_cartera) {
        String error = "";
        
        try {
            Lista1 cartera_resumen = this.cartera.cartera_resumen();
            String cadenasql = cartera_resumen.query_gestores_falantes(tipo_cartera);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                cartera_resumen.insertar_faltante(rs.getString(1));
            }
            rs.close();
            stmt.close();
            Integer numero_cartera_sin = cartera_resumen.no_casos_sin_gestor();
            if (numero_cartera_sin != 0) {
                Integer minimo_cartera = cartera_resumen.cartera_minimo();
                Integer maximo_cartera = cartera_resumen.cartera_maximo();
                Integer dif_max_min = maximo_cartera - minimo_cartera;
                if (dif_max_min != 0) {
                    String minimo_gestor = cartera_resumen.gestor_cartera_minimo();
                    error = "M_" + minimo_gestor + "," + dif_max_min + "," + minimo_gestor;
                    this.cartera.actualizar_cartera_deudores(dif_max_min, minimo_gestor);
                } else {
                    String minimo_monto_gestor = cartera_resumen.gestor_monto_minimo();
                    this.cartera.actualizar_cartera_monto(minimo_monto_gestor);
                }
                rotacion(tipo_cartera);
            }
        } catch (Exception ex) {
            System.out.println("ERROR_" + error + ": " + ex.toString());
        }
    }

    public void generar_reporte2(String fileName, String cadenasql) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metaDatos = rs.getMetaData();
            Integer numeroColumnas = metaDatos.getColumnCount();

            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet wsheet = workbook.createSheet("Reporte", 0);

            //FORMATO PARA EL ENCABEZADO DE LAS COLUMNAS.
            WritableFont TableFormatTitle = new WritableFont(WritableFont.TIMES, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
            WritableCellFormat tableFormatBackgroundTitle = new WritableCellFormat();
            tableFormatBackgroundTitle.setBackground(Colour.BLUE);
            tableFormatBackgroundTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.WHITE);
            tableFormatBackgroundTitle.setFont(TableFormatTitle);
            tableFormatBackgroundTitle.setAlignment(Alignment.CENTRE);

            //FORMATO PARA EL CUERPO DE LA TABLA.
            WritableFont TableFormatBody = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBody = new WritableCellFormat();
            tableFormatBackgroundBody.setBackground(Colour.WHITE);
            tableFormatBackgroundBody.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBody.setFont(TableFormatBody);
            tableFormatBackgroundBody.setAlignment(Alignment.LEFT);

            //FORMATO PARA LAS COLUMNAS QUE LLENAN EN LEXCOM.
            WritableFont TableFormatLexcom = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundLexcom = new WritableCellFormat();
            tableFormatBackgroundLexcom.setBackground(Colour.YELLOW);
            tableFormatBackgroundLexcom.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundLexcom.setFont(TableFormatLexcom);
            tableFormatBackgroundLexcom.setAlignment(Alignment.LEFT);

            //FORMATO PARA LAS COLUMNAS QUE ENVIA EL BANCO.
            WritableFont TableFormatBanco = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBanco = new WritableCellFormat();
            tableFormatBackgroundBanco.setBackground(Colour.GREEN);
            tableFormatBackgroundBanco.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBanco.setFont(TableFormatBanco);
            tableFormatBackgroundBanco.setAlignment(Alignment.LEFT);

            for (int i = 0; i < numeroColumnas; i++) {
                Label label = new Label(i, 0, metaDatos.getColumnLabel(i + 1).toString(), tableFormatBackgroundTitle);
                wsheet.addCell(label);
            }

            Integer fila = 1;
            while (rs.next()) {
                for (int col = 0; col < numeroColumnas; col++) {
                    Label label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBody);
                    if (col == 32 || col == 31 || col == 24 || col == 25 || col == 42 || col == 1 || col == 27 || col == 45 || col == 41 || col == 16 || col == 23 || col == 28 || col == 29 || col == 40) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundLexcom);
                    }
                    if (col == 26 || col == 2 || col == 22 || col == 6 || col == 37) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBanco);
                    }
                    wsheet.addCell(label);
                }
                fila++;
            }
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException | SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void generar_reporte1(String fileName, String cadenasql) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metaDatos = rs.getMetaData();
            Integer numeroColumnas = metaDatos.getColumnCount();

            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet wsheet = workbook.createSheet("Reporte", 0);

            //FORMATO PARA EL ENCABEZADO DE LAS COLUMNAS.
            WritableFont TableFormatTitle = new WritableFont(WritableFont.TIMES, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
            WritableCellFormat tableFormatBackgroundTitle = new WritableCellFormat();
            tableFormatBackgroundTitle.setBackground(Colour.BLUE);
            tableFormatBackgroundTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.WHITE);
            tableFormatBackgroundTitle.setFont(TableFormatTitle);
            tableFormatBackgroundTitle.setAlignment(Alignment.CENTRE);

            //FORMATO PARA EL CUERPO DE LA TABLA.
            WritableFont TableFormatBody = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBody = new WritableCellFormat();
            tableFormatBackgroundBody.setBackground(Colour.WHITE);
            tableFormatBackgroundBody.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBody.setFont(TableFormatBody);
            tableFormatBackgroundBody.setAlignment(Alignment.LEFT);

            //FORMATO PARA LAS COLUMNAS QUE LLENAN EN LEXCOM.
            WritableFont TableFormatLexcom = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundLexcom = new WritableCellFormat();
            tableFormatBackgroundLexcom.setBackground(Colour.YELLOW);
            tableFormatBackgroundLexcom.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundLexcom.setFont(TableFormatLexcom);
            tableFormatBackgroundLexcom.setAlignment(Alignment.LEFT);

            //FORMATO PARA LAS COLUMNAS QUE ENVIA EL BANCO.
            WritableFont TableFormatBanco = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBanco = new WritableCellFormat();
            tableFormatBackgroundBanco.setBackground(Colour.GREEN);
            tableFormatBackgroundBanco.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBanco.setFont(TableFormatBanco);
            tableFormatBackgroundBanco.setAlignment(Alignment.LEFT);

            for (int i = 0; i < numeroColumnas; i++) {
                Label label = new Label(i, 0, metaDatos.getColumnLabel(i + 1).toString(), tableFormatBackgroundTitle);
                wsheet.addCell(label);
            }

            Integer fila = 1;
            while (rs.next()) {
                for (int col = 0; col < numeroColumnas; col++) {
                    Label label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBody);
                    if (col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6 || col == 7 || col == 8 || col == 10 || col == 11 || col == 12 || col == 16) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundLexcom);
                    }
                    if (col == 9 || col == 13 || col == 14 || col == 15 || col == 17) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBanco);
                    }
                    wsheet.addCell(label);
                }
                fila++;
            }
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException | SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void generar_reporte(String fileName, String cadenasql) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metaDatos = rs.getMetaData();
            Integer numeroColumnas = metaDatos.getColumnCount();

            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(fileName + ".xls"));
            WritableSheet wsheet = workbook.createSheet("Reporte", 0);

            //FORMATO PARA EL ENCABEZADO DE LAS COLUMNAS.
            WritableFont TableFormatTitle = new WritableFont(WritableFont.TIMES, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
            WritableCellFormat tableFormatBackgroundTitle = new WritableCellFormat();
            tableFormatBackgroundTitle.setBackground(Colour.BLUE);
            tableFormatBackgroundTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.WHITE);
            tableFormatBackgroundTitle.setFont(TableFormatTitle);
            tableFormatBackgroundTitle.setAlignment(Alignment.CENTRE);

            //FORMATO PARA EL CUERPO DE LA TABLA.
            WritableFont TableFormatBody = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBody = new WritableCellFormat();
            tableFormatBackgroundBody.setBackground(Colour.WHITE);
            tableFormatBackgroundBody.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBody.setFont(TableFormatBody);
            tableFormatBackgroundBody.setAlignment(Alignment.LEFT);

            for (int i = 0; i < numeroColumnas; i++) {
                Label label = new Label(i, 0, metaDatos.getColumnLabel(i + 1).toString(), tableFormatBackgroundTitle);
                wsheet.addCell(label);
            }
            Integer fila = 1;
            while (rs.next()) {
                for (int col = 0; col < numeroColumnas; col++) {
                    Label label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBody);
                    wsheet.addCell(label);
                }
                fila++;
            }
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException | SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    
    public void generar_reporte_w(String fileName, String[][] reporte, Integer filas, Integer columnas) {
        try {
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(fileName + ".xls"));
            WritableSheet wsheet = workbook.createSheet("Reporte", 0);

            //FORMATO PARA EL ENCABEZADO DE LAS COLUMNAS.
            WritableFont TableFormatTitle = new WritableFont(WritableFont.TIMES, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
            WritableCellFormat tableFormatBackgroundTitle = new WritableCellFormat();
            tableFormatBackgroundTitle.setBackground(Colour.BLUE);
            tableFormatBackgroundTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.WHITE);
            tableFormatBackgroundTitle.setFont(TableFormatTitle);
            tableFormatBackgroundTitle.setAlignment(Alignment.CENTRE);

            //FORMATO PARA EL CUERPO DE LA TABLA.
            WritableFont TableFormatBody = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBody = new WritableCellFormat();
            tableFormatBackgroundBody.setBackground(Colour.WHITE);
            tableFormatBackgroundBody.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBody.setFont(TableFormatBody);
            tableFormatBackgroundBody.setAlignment(Alignment.LEFT);

            for (int j = 0; j < columnas; j++) {
                Label label = new Label(j, 0, reporte[0][j], tableFormatBackgroundTitle);
                wsheet.addCell(label);
            }
            
            for (int i = 1; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    Label label = new Label(j, i, reporte[i][j], tableFormatBackgroundBody);
                    wsheet.addCell(label);
                }
            }
            
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    
    public void generar_reporte_pagos(String fileName, String cadenasql) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metaDatos = rs.getMetaData();
            Integer numeroColumnas = metaDatos.getColumnCount();

            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(new File(fileName + ".xls"));
            WritableSheet wsheet = workbook.createSheet("Reporte", 0);

            //FORMATO PARA EL ENCABEZADO DE LAS COLUMNAS.
            WritableFont TableFormatTitle = new WritableFont(WritableFont.TIMES, 8, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
            WritableCellFormat tableFormatBackgroundTitle = new WritableCellFormat();
            tableFormatBackgroundTitle.setBackground(Colour.BLUE);
            tableFormatBackgroundTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.WHITE);
            tableFormatBackgroundTitle.setFont(TableFormatTitle);
            tableFormatBackgroundTitle.setAlignment(Alignment.CENTRE);

            //FORMATO PARA EL CUERPO DE LA TABLA.
            WritableFont TableFormatBody = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundBody = new WritableCellFormat();
            tableFormatBackgroundBody.setBackground(Colour.WHITE);
            tableFormatBackgroundBody.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundBody.setFont(TableFormatBody);
            tableFormatBackgroundBody.setAlignment(Alignment.LEFT);
            
            //FORMATO PARA LAS COLUMNAS QUE ENVIA EL BANCO.
            WritableFont TableFormatLexcom = new WritableFont(WritableFont.TIMES, 8, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat tableFormatBackgroundLexcom = new WritableCellFormat();
            tableFormatBackgroundLexcom.setBackground(Colour.GREEN);
            tableFormatBackgroundLexcom.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            tableFormatBackgroundLexcom.setFont(TableFormatLexcom);
            tableFormatBackgroundLexcom.setAlignment(Alignment.LEFT);

            for (int i = 0; i < numeroColumnas; i++) {
                Label label = new Label(i, 0, metaDatos.getColumnLabel(i + 1).toString(), tableFormatBackgroundTitle);
                wsheet.addCell(label);
            }
            Integer fila = 1;
            while (rs.next()) {
                for (int col = 0; col < numeroColumnas; col++) {
                    Label label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBody);
                    if (col == 6 || col == 7) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundLexcom);
                    }
                    if (col == 0 || col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 8 || col == 9 || col == 10 || col == 11 || col == 12) {
                        label = new Label(col, fila, rs.getString(col + 1), tableFormatBackgroundBody);
                    }
                    wsheet.addCell(label);
                }
                fila++;
            }
            workbook.write();
            workbook.close();
        } catch (IOException | WriteException | SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public Deudor Dar_Datos_Deudor(Integer deudor) {
        Deudor Resultado = new Deudor(conn, usuario_sys);
        Resultado = Resultado.obtener(deudor);

        return Resultado;
    }

    public DefaultTableModel obtener_tabla_gestion(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "select "
                + "DATE_FORMAT(d.fecha,'%d/%m/%Y') AS FECHA, "
                + "d.hora AS HORA, "
                + "u.nombre AS USUARIO, "
                + "concat(c.codigo,'|',c.nombre) AS CODIGO, "
                + "d.contacto AS CONTACTO, "
                + "d.descripcion AS OBSERVACION "
                + "from "
                + "deudor_historial_cobros d "
                + "left join usuario u on (d.usuario=u.usuario) "
                + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                + "where "
                + "d.deudor = " + this.deudor.toString() + " "
                + "order by d.fecha desc, d.hora desc";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_administracion(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "select "
                + "DATE_FORMAT(d.fecha,'%d/%m/%Y') AS FECHA, "
                + "d.hora AS HORA, "
                + "u.nombre AS USUARIO, "
                + "concat(c.codigo,'|',c.nombre) AS CODIGO, "
                + "d.descripcion AS OBSERVACION "
                + "from "
                + "deudor_historial_administrativo d "
                + "left join usuario u on (d.usuario=u.usuario) "
                + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                + "where "
                + "d.deudor = " + this.deudor.toString() + " "
                + "order by d.fecha desc, d.hora desc";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_juridico(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "select "
                + "DATE_FORMAT(d.fecha,'%d/%m/%Y') AS FECHA, "
                + "d.hora AS HORA, "
                + "u.nombre AS USUARIO, "
                + "concat(c.codigo,'|',c.nombre) AS CODIGO, "
                + "d.descripcion AS OBSERVACION "
                + "from "
                + "deudor_historial_juridico d "
                + "left join usuario u on (d.usuario=u.usuario) "
                + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                + "where "
                + "d.deudor = " + this.deudor.toString() + " "
                + "order by d.fecha desc, d.hora desc";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_pago(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "p.pago AS PAGO, "
                + "b.nombre AS BANCO, "
                + "DATE_FORMAT(p.fecha,'%d/%m/%Y') AS FECHA, "
                + "CONCAT('Q', FORMAT(p.monto, 2)) AS MONTO, "
                + "p.no_boleta AS NO_BOLETA,"
                + "DATE_FORMAT(p.fecha_registro,'%d/%m/%Y') AS FECHA_REGISTRO "
                + "FROM "
                + "pago p "
                + "left join banco b on (p.banco=b.banco) "
                + "WHERE "
                + "p.deudor=" + this.deudor.toString() + " "
                + "order by p.fecha desc";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_promesa_pago(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "p.promesa_pago AS PROMESA_PAGO, "
                + "DATE_FORMAT(p.fecha_ingreso,'%d/%m/%Y') AS FECHA_INGRESO, "
                + "DATE_FORMAT(p.fecha_pago,'%d/%m/%Y') AS FECHA_PAGO, "
                + "p.estado_promesa AS ESTADO_PROMESA, "
                + "CONCAT('Q', FORMAT(p.monto, 2)) AS MONTO "
                + "FROM "
                + "promesa_pago p "
                + "WHERE "
                + "p.deudor=" + this.deudor.toString() + " "
                + "order by p.fecha_ingreso desc";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_juicio(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "j.juicio AS JUICIO, "
                + "ju.nombre AS JUZGADO, "
                + "DATE_FORMAT(j.fecha,'%d/%m/%Y') AS FECHA, "
                + "j.no_juicio AS NO_JUICIO, "
                + "CONCAT('Q', FORMAT(j.monto, 2)) AS MONTO "
                + "FROM "
                + "juicio j "
                + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                + "WHERE "
                + "j.deudor=" + this.deudor.toString() + " "
                + "order by j.fecha";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_descuento(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "d.descuento AS DESCUENTO, "
                + "t.nombre AS TIPO_DESCUENTO, "
                + "DATE_FORMAT(d.fecha,'%d/%m/%Y') AS FECHA, "
                + "CONCAT('Q', FORMAT(d.monto, 2)) AS MONTO "
                + "FROM "
                + "descuento d "
                + "left join tipo_descuento t on (d.tipo_descuento=t.tipo_descuento) "
                + "WHERE "
                + "d.deudor=" + this.deudor.toString() + " "
                + "order by d.fecha";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public DefaultTableModel obtener_tabla_aumento(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "a.aumento AS AUMENTO, "
                + "t.nombre AS TIPO_AUMENTO, "
                + "DATE_FORMAT(a.fecha,'%d/%m/%Y') AS FECHA, "
                + "CONCAT('Q', FORMAT(a.monto, 2)) AS MONTO "
                + "FROM "
                + "aumento a "
                + "left join tipo_aumento t on (a.tipo_aumento=t.tipo_aumento) "
                + "WHERE "
                + "a.deudor=" + this.deudor.toString() + " "
                + "order by a.fecha";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }
    
    public DefaultTableModel obtener_tabla_convenio(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "c.convenio AS CONVENIO, "
                + "DATE_FORMAT(c.fecha_creacion,'%d/%m/%Y') AS FECHA_CREACION, "
                + "c.estado AS ESTADO, "
                + "DATE_FORMAT(c.fecha_pago_inicial,'%d/%m/%Y') AS FECHA_PAGO, "
                + "CONCAT('Q', FORMAT(c.total_pagar, 2)) AS TOTAL_DEUDA, "
                + "c.numero_cuotas AS NO_CUOTAS, "
                + "c.frecuencia AS FRECUENCIA, "
                + "CONCAT('Q', FORMAT(c.monto_cuota, 2)) AS CUOTA "
                + "FROM "
                + "convenio c "
                + "WHERE "
                + "c.deudor=" + this.deudor.toString() + " "
                + "order by "
                + "c.fecha_creacion";

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public String modificar_deudor(
            Integer deudor,
            Integer actor,
            String moneda,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String telefono_casa,
            String telefono_celular,
            String direccion,
            Calendar fecha_recepcion,
            String correo_electronico,
            String lugar_trabajo,
            String direccion_trabajo,
            String telefono_trabajo,
            Double monto_inicial,
            Integer gestor,
            Integer sestado,
            Integer estatus,
            String no_cuenta,
            Integer garantia,
            String cargado,
            String estado,
            String PDF,
            String INV,
            String MAYCOM,
            String NITS,
            Double saldo,
            Calendar fecha_proximo_pago,
            Integer caso,
            String convenio_pactado,
            Double cuota_convenio,
            String no_cuenta_otro,
            String situacion_caso,
            String opcion_proximo_pago,
            String anticipo,
            Integer sestado_extra,
            Integer estatus_extra,
            String notas,
            Integer intencion_pago) {

        String resultado = "";

        try {
            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha_nac = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_recepcion.get(Calendar.DATE);
            mes = fecha_recepcion.get(Calendar.MONTH) + 1;
            ano = fecha_recepcion.get(Calendar.YEAR);
            String fecha_rec = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_proximo_pago.get(Calendar.DATE);
            mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
            ano = fecha_proximo_pago.get(Calendar.YEAR);
            String fecha_prox_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            if (opcion_proximo_pago.equals("NO")) {
                cuota_convenio = 0.00;
                fecha_prox_pago = "1900/01/01";
            }
            
            conn.setAutoCommit(false);
            
            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            String cadenasql = "select "
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
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
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
                    + "actor='" + actor + "', "
                    + "dpi='" + quitar_simbolos(dpi) + "', "
                    + "nit='" + quitar_simbolos(nit) + "', "
                    + "fecha_nacimiento='" + quitar_simbolos(fecha_nac) + "', "
                    + "nombre='" + quitar_simbolos(nombre) + "', "
                    + "telefono_casa='" + quitar_simbolos(telefono_casa) + "', "
                    + "telefono_celular='" + quitar_simbolos(telefono_celular) + "', "
                    + "direccion='" + quitar_simbolos(direccion) + "', "
                    + "moneda='" + quitar_simbolos(moneda) + "', "
                    + "fecha_recepcion='" + quitar_simbolos(fecha_rec) + "', "
                    + "correo_electronico='" + quitar_simbolos(correo_electronico) + "', "
                    + "lugar_trabajo='" + quitar_simbolos(lugar_trabajo) + "', "
                    + "direccion_trabajo='" + quitar_simbolos(direccion_trabajo) + "', "
                    + "telefono_trabajo='" + quitar_simbolos(telefono_trabajo) + "', "
                    + "monto_inicial='" + monto_inicial + "', "
                    + "usuario='" + gestor + "', "
                    + "sestado='" + sestado + "', "
                    + "estatus='" + estatus + "', "
                    + "no_cuenta='" + quitar_simbolos(no_cuenta) + "', "
                    + "garantia='" + garantia + "', "
                    + "cargado='" + quitar_simbolos(cargado) + "', "
                    + "PDF='" + quitar_simbolos(PDF) + "', "
                    + "INV='" + quitar_simbolos(INV) + "', "
                    + "MAYCOM='" + quitar_simbolos(MAYCOM) + "', "
                    + "NITS='" + quitar_simbolos(NITS) + "', "
                    + "saldo='" + saldo + "', "
                    + "caso='" + caso + "', "
                    + "convenio_pactado='" + quitar_simbolos(convenio_pactado) + "', "
                    + "cuota_convenio='" + cuota_convenio + "', "
                    + "no_cuenta_otro='" + quitar_simbolos(no_cuenta_otro) + "', "
                    + "situacion_caso='" + quitar_simbolos(situacion_caso) + "', "
                    + "opcion_proximo_pago='" + quitar_simbolos(opcion_proximo_pago) + "', "
                    + "fecha_proximo_pago='" + quitar_simbolos(fecha_prox_pago) + "', "
                    + "anticipo='" + quitar_simbolos(anticipo) + "', "
                    + "sestado_extra='" + sestado_extra + "', "
                    + "estatus_extra='" + estatus_extra + "', "
                    + "descripcion='" + quitar_simbolos(notas) + "', "
                    + "intencion_pago='" + intencion_pago + "' "
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
            
            conn.commit();
            conn.setAutoCommit(true);
            
            resultado = "1,Deudor modificado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Deudor modificado=> Actor: " + actor + " Moneda: " + moneda + " dpi:" + dpi + " nit:" + nit + " PDF: " + PDF + " INV: " + INV + " MAYCOM: " + MAYCOM + " NITS: " + NITS + " Anticipo: " + anticipo, 1);
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

    public String modificar_juicio(
            Integer deudor,
            Integer juicio,
            Integer procurador,
            Integer juzgado,
            Calendar fecha,
            String razon_notificacion,
            String no_juicio,
            Integer notificador,
            String abogado_deudor,
            String sumario,
            Calendar memorial,
            String procuracion,
            String deudor_notificado,
            Calendar fecha_notificacion,
            Double monto) {

        String resultado = "";

        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String fecha_juicio = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = memorial.get(Calendar.DATE);
        mes = memorial.get(Calendar.MONTH) + 1;
        ano = memorial.get(Calendar.YEAR);
        String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = fecha_notificacion.get(Calendar.DATE);
        mes = fecha_notificacion.get(Calendar.MONTH) + 1;
        ano = fecha_notificacion.get(Calendar.YEAR);
        String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update juicio set "
                    + "deudor='" + deudor + "', "
                    + "juzgado='" + juzgado + "', "
                    + "fecha='" + quitar_simbolos(fecha_juicio) + "', "
                    + "no_juicio='" + quitar_simbolos(no_juicio) + "', "
                    + "procurador='" + procurador.toString() + "', "
                    + "razon_notificacion='" + quitar_simbolos(razon_notificacion) + "', "
                    + "notificador='" + notificador.toString() + "', "
                    + "abogado_deudor='" + quitar_simbolos(abogado_deudor) + "', "
                    + "sumario='" + quitar_simbolos(sumario) + "', "
                    + "procuracion='" + quitar_simbolos(procuracion) + "', "
                    + "deudor_notificado='" + quitar_simbolos(deudor_notificado) + "', "
                    + "fecha_notificacion='" + quitar_simbolos(fecha_noti) + "', "
                    + "memorial='" + quitar_simbolos(fecha_memorial) + "', "
                    + "monto='" + monto + "' "
                    + "where juicio=" + juicio;
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Juicio modificado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Juicio modificado=>  Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " Procuración: " + procuracion, 1);
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

    public DefaultTableModel obtener_tabla_fiador(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "f.fiador AS FIADOR, "
                + "f.dpi AS DPI, "
                + "f.nit AS NIT, "
                + "f.nombre AS NOMBRE, "
                + "f.telefono AS TELEFONO, "
                + "f.direccion AS DIRECICON, "
                + "f.zona AS ZONA, "
                + "f.correo_electronico AS CORREO_ELECTRONICO "
                + "FROM "
                + "fiador f "
                + "WHERE "
                + "f.deudor=" + this.deudor.toString();

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    private String dar_path_digitalizados() {
        String resultado = "";
        
        try {
            String cadenasql = "select c.valor from constantes c where c.constantes=1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    private String dar_path_actor(String deudor) {
        String resultado = "";
        
        try {
            String cadenasql = "select "
                    + "a.digitalizados "
                    + "from "
                    + "actor a "
                    + "left join deudor d on (a.actor=d.actor) "
                    + "where "
                    + "d.deudor=" + deudor;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    private String dar_caso_deudor(String deudor) {
        String resultado = "";
        
        try {
            String cadenasql = "select d.caso from deudor d where d.deudor=" + deudor;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public DefaultTableModel obtener_tabla_digitalizacion(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        this.modelo = modelo;
        
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }
            
            String Directorio = dar_path_digitalizados() + "\\" + dar_path_actor(deudor.toString()) + "\\" + dar_caso_deudor(deudor.toString());
            File f = new File(Directorio);
            if(f.exists()) {
                File[] ficheros = f.listFiles();
                Object[] fila = new Object[3];
                for (Integer i = 0; i < ficheros.length; i++) {
                    fila[0] = i.toString();
                    fila[1] = ficheros[i].getName();
                    fila[2] = ficheros[i].getPath();
                    this.modelo.addRow(fila);
                }
            } else {
                //System.out.println("Directorio no existe.");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }
    
    public DefaultTableModel obtener_tabla_referencia(DefaultTableModel modelo, Integer deudor) {
        this.deudor = deudor;
        Integer numeroColumnas = 0;
        String cadenasql = "SELECT "
                + "r.referencia AS REFERENCIA, "
                + "r.dpi AS DPI, "
                + "r.nit AS NIT, "
                + "r.nombre AS NOMBRE, "
                + "r.telefono AS TELEFONO, "
                + "r.direccion AS DIRECICON, "
                + "r.zona AS ZONA, "
                + "r.correo_electronico AS CORREO_ELECTRONICO "
                + "FROM "
                + "referencia r "
                + "WHERE "
                + "r.deudor=" + this.deudor.toString();

        this.modelo = modelo;
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }

    public void insertar_btn_gestion(Integer deudor, Integer usuario, Integer Codigo_Contactabiliad, String descripcion, String contacto) {
        try {
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_cobros (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion, contacto) values ('"
                    + deudor.toString() + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + usuario.toString() + "','"
                    + Codigo_Contactabiliad.toString() + "','"
                    + descripcion + "','"
                    + contacto + "')";
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

            cadenasql = "update deudor "
                    + "set codigo_contactabilidad='" + Codigo_Contactabiliad.toString() + "' "
                    + "where deudor=" + deudor.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

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

    public void insertar_btn_administracion(Integer deudor, Integer usuario, Integer Codigo_Contactabiliad, String descripcion) {
        String cadenasql = "insert into deudor_historial_administrativo (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                + deudor.toString() + "',"
                + "CURRENT_DATE()" + ","
                + "CURRENT_TIME()" + ",'"
                + usuario.toString() + "','"
                + Codigo_Contactabiliad.toString() + "','"
                + descripcion + "')";

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
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

    public void insertar_btn_juridico(Integer deudor, Integer usuario, Integer Codigo_Contactabiliad, String descripcion) {
        String cadenasql = "insert into deudor_historial_juridico (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                + deudor.toString() + "',"
                + "CURRENT_DATE()" + ","
                + "CURRENT_TIME()" + ",'"
                + usuario.toString() + "','"
                + Codigo_Contactabiliad.toString() + "','"
                + descripcion + "')";

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
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
    
    public String estado_status_bloquea_judicial(Integer estado, Integer status) {
        String resultado = "";
        
        try {
            String cadenasql = "select "
                    + "if(e.permite_estado_judicial=1,'SI','NO') PERMITE "
                    + "from "
                    + "estado_status_extrajudicial e "
                    + "where "
                    + "e.sestado_extra=" + estado + " and "
                    + "e.estatus_extra=" + status;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }

    public Double calcular_saldo(Integer deudor) {
        Double saldo;

        Double monto_inicial = 0.00;
        Double pago = 0.00;
        Double descuento = 0.00;
        Double aumento = 0.00;

        //SALDO INICIAL
        try {
            String cadenasql = "select d.monto_inicial from deudor d where d.deudor=" + deudor;
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    monto_inicial = rs.getDouble(1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        //PAGOS
        try {
            String cadenasql = "select p.deudor, sum(p.monto) from pago p where p.deudor=" + deudor + " group by p.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    pago = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        //DESCUENTOS
        try {
            String cadenasql = "select d.deudor, sum(d.monto) from descuento d where d.deudor=" + deudor + " group by d.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    descuento = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        //AUMENTOS
        try {
            String cadenasql = "select a.deudor, sum(a.monto) from aumento a where a.deudor=" + deudor + " group by a.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    aumento = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        //SALDO
        saldo = monto_inicial - pago - descuento + aumento;

        return saldo;
    }

    public Double calcular_pagos(Integer deudor) {
        Double pagos = 0.00;

        try {
            String cadenasql = "select p.deudor, sum(p.monto) from pago p where p.deudor=" + deudor + " group by p.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    pagos = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return pagos;
    }

    public Double calcular_descuentos(Integer deudor) {
        Double descuentos = 0.00;

        try {
            String cadenasql = "select d.deudor, sum(d.monto) from descuento d where d.deudor=" + deudor + " group by d.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    descuentos = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return descuentos;
    }

    public Double calcular_aumentos(Integer deudor) {
        Double aumentos = 0.00;

        try {
            String cadenasql = "select a.deudor, sum(a.monto) from aumento a where a.deudor=" + deudor + " group by a.deudor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    aumentos = rs.getDouble(2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return aumentos;
    }

    public Calendar fecha_proximo_pago(Integer deudor) {
        Calendar fecha_proximo_pago = Calendar.getInstance();

        try {
            String cadenasql = "select max(p.fecha_proximo_pago) as FECHA_PROXIMO_PAGO from pago p where p.deudor=" + deudor;
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    fecha_proximo_pago = DateToCalendar(rs.getDate(1));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return fecha_proximo_pago;
    }

    public Double monto_proximo_pago(Integer deudor) {
        Calendar fecha_proximo_pago = Calendar.getInstance();
        Double monto_proximo_pago = 0.00;

        try {
            String cadenasql = "select max(p.fecha_proximo_pago) as FECHA_PROXIMO_PAGO from pago p where p.deudor=" + deudor;
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    fecha_proximo_pago = DateToCalendar(rs.getDate(1));
                }
            }
            Integer dia = fecha_proximo_pago.get(Calendar.DATE);
            Integer mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
            Integer ano = fecha_proximo_pago.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            cadenasql = "select monto_proximo_pago as MONTO_PROXIMO_PAGO from pago p where p.deudor=" + deudor + " and p.fecha_proximo_pago='" + fecha + "'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    monto_proximo_pago = rs.getDouble(1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return monto_proximo_pago;
    }

    public Calendar fecha_ultimo_pago(Integer deudor) {
        Calendar fecha_ultimo_pago = Calendar.getInstance();

        try {
            String cadenasql = "select max(p.fecha) as FECHA_ULTIMO_PAGO from pago p where p.deudor=" + deudor;
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    fecha_ultimo_pago = DateToCalendar(rs.getDate(1));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return fecha_ultimo_pago;
    }

    public Double monto_ultimo_pago(Integer deudor) {
        Calendar fecha_ultimo_pago = Calendar.getInstance();
        Double monto_ultimo_pago = 0.00;

        try {
            String cadenasql = "select max(p.fecha) as FECHA_ULTIMO_PAGO from pago p where p.deudor=" + deudor;
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                fecha_ultimo_pago = DateToCalendar(rs.getDate(1));
            }
            
            Integer dia = fecha_ultimo_pago.get(Calendar.DATE);
            Integer mes = fecha_ultimo_pago.get(Calendar.MONTH) + 1;
            Integer ano = fecha_ultimo_pago.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            cadenasql = "select p.monto as MONTO_ULTIMO_PAGO from pago p where p.deudor=" + deudor + " and p.fecha='" + fecha + "'";
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                monto_ultimo_pago = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return monto_ultimo_pago;
    }
    
    public String validar_estado_status_judicial(Integer deudor) {
        String resultado = "INCORRECTO";
        
        try {
            String cadenasql = "select "
                    + "d.sestado, d.estatus "
                    + "from "
                    + "deudor d "
                    + "where "
                    + "(d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) "
                    + "and d.deudor=" + deudor;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = "CORRECTO";
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public String validar_estado_status_extrajudicial(Integer deudor) {
        String resultado = "INCORRECTO";
        
        try {
            String cadenasql = "select "
                    + "d.sestado_extra, d.estatus_extra "
                    + "from "
                    + "deudor d "
                    + "where "
                    + "(d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) "
                    + "and d.deudor=" + deudor;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = "CORRECTO";
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }

    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
        } catch (Exception ex) {
            ;
        }

        return cal;
    }
    
    private String quitar_simbolos(String cadena) {
        String resultado = cadena.replaceAll("\"", "");
        resultado = resultado.replaceAll("'", "");
        resultado = resultado.trim();
        
        return resultado;
    }
    
}
