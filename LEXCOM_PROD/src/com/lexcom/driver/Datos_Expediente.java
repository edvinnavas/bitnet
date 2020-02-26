package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class Datos_Expediente {

    Connection conn;
    DefaultTableModel modelo;
    DefaultComboBoxModel lst_busqueda;

    public Datos_Expediente(Connection conn) {
        this.conn = conn;
    }

    public DefaultTableModel obtener_tabla(String patron) {
        Integer numeroColumnas = 0;

        this.modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }

            String cadenasql = "SELECT "
                    + "d.deudor AS CODIGO, "
                    + "a.nombre AS ACTOR, "
                    + "d.caso AS CASO, "
                    + "d.nombre AS NOMBRE, "
                    + "d.fecha_ingreso AS FECHA_INGRESO, "
                    + "u.nombre AS GESTOR, "
                    + "s.nombre AS ESTADO, "
                    + "e.nombre AS STATUS, "
                    + "ga.nombre AS GARANTIA, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                    + "j.no_juicio AS NO_JUICIO, "
                    + "d.cargado AS CARGADO "
                    + "FROM "
                    + "deudor d "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join garantia ga on (d.garantia=ga.garantia) "
                    + "left join juicio j on (d.deudor=j.deudor)"
                    + "WHERE "
                    + "a.nombre like '%" + patron + "%' or "
                    + "d.dpi like '%" + patron + "%' or "
                    + "d.nit like '%" + patron + "%' or "
                    + "d.nombre like '%" + patron + "%' or "
                    + "d.caso like '%" + patron + "%' or "
                    + "d.pais like '%" + patron + "%' or "
                    + "d.departamento like '%" + patron + "%' or "
                    + "d.fecha_ingreso like '%" + patron + "%' or "
                    + "u.nombre like '%" + patron + "%' or "
                    + "s.nombre like '%" + patron + "%' or "
                    + "e.nombre like '%" + patron + "%' or "
                    + "d.no_cuenta like '%" + patron + "%' or "
                    + "d.no_cuenta_otro like '%" + patron + "%' or "
                    + "j.no_juicio like '%" + patron + "%' or "
                    + "ga.nombre like '%" + patron + "%' or "
                    + "d.cargado like '%" + patron + "%'";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                ResultSetMetaData metaDatos = rs.getMetaData();
                numeroColumnas = metaDatos.getColumnCount();
                Object[] etiquetas = new Object[numeroColumnas];
                for (int i = 0; i < numeroColumnas; i++) {
                    etiquetas[i] = metaDatos.getColumnLabel(i + 1);
                }
                this.modelo.setColumnIdentifiers(etiquetas);
                
                while (rs.next()) {
                    Object[] fila = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }
                    this.modelo.addRow(fila);
                }
                rs.close();
            } catch(Exception ex) {
                System.out.println(ex);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }
    
    public DefaultTableModel obtener_tabla_asignacion(Integer usuario) {
        Integer numeroColumnas = 0;

        this.modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        
        try {
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }
            
            if(this.existe_asignacion()) {
                try {
                    Integer dia = dar_fecha_asignacion().get(Calendar.DATE);
                    Integer mes = dar_fecha_asignacion().get(Calendar.MONTH) + 1;
                    Integer ano = dar_fecha_asignacion().get(Calendar.YEAR);
                    String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                    
                    String cadenasql = "SELECT "
                            + "d.deudor AS CODIGO, "
                            + "'PP' AS ACTIVIDAD, "
                            + "a.nombre AS ACTOR, "
                            + "d.caso AS CASO, "
                            + "d.nombre AS NOMBRE, "
                            + "d.fecha_ingreso AS FECHA_INGRESO, "
                            + "u.nombre AS GESTOR, "
                            + "s.nombre AS ESTADO, "
                            + "e.nombre AS STATUS, "
                            + "ga.nombre AS GARANTIA, "
                            + "d.no_cuenta AS NO_CUENTA, "
                            + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                            + "j.no_juicio AS NO_JUICIO, "
                            + "d.cargado AS CARGADO, "
                            + "d.fecha_proximo_pago AS FECHA_PROMESA_PAGO "
                            + "FROM "
                            + "deudor d "
                            + "left join actor a on (d.actor=a.actor) "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join sestado s on (d.sestado=s.sestado) "
                            + "left join estatus e on (d.estatus=e.estatus) "
                            + "left join garantia ga on (d.garantia=ga.garantia) "
                            + "left join juicio j on (d.deudor=j.deudor) "
                            + "WHERE "
                            + "d.cargado='" + "CARGADO" + "' AND "
                            + "d.opcion_proximo_pago='SI' AND "
                            + "d.fecha_proximo_pago<='" + fecha_pago + "' AND "
                            + "d.usuario=" + usuario + " "
                            + "ORDER BY "
                            + "d.fecha_proximo_pago desc";
                    
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(cadenasql);
                    ResultSetMetaData metaDatos = rs.getMetaData();
                    numeroColumnas = metaDatos.getColumnCount();
                    Object[] etiquetas = new Object[numeroColumnas];
                    for (int i = 0; i < numeroColumnas; i++) {
                        etiquetas[i] = metaDatos.getColumnLabel(i + 1);
                    }
                    this.modelo.setColumnIdentifiers(etiquetas);

                    while (rs.next()) {
                        Object[] fila = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        this.modelo.addRow(fila);
                    }
                    rs.close();
                    stmt.close();
                    
                    cadenasql = "SELECT "
                            + "d.deudor AS CODIGO, "
                            + "'NG' AS ACTIVIDAD, "
                            + "a.nombre AS ACTOR, "
                            + "d.caso AS CASO, "
                            + "d.nombre AS NOMBRE, "
                            + "d.fecha_ingreso AS FECHA_INGRESO, "
                            + "u.nombre AS GESTOR, "
                            + "s.nombre AS ESTADO, "
                            + "e.nombre AS STATUS, "
                            + "ga.nombre AS GARANTIA, "
                            + "d.no_cuenta AS NO_CUENTA, "
                            + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                            + "j.no_juicio AS NO_JUICIO, "
                            + "d.cargado AS CARGADO, "
                            + "d.fecha_proximo_pago AS FECHA_PROMESA_PAGO "
                            + "FROM "
                            + "deudor d "
                            + "left join actor a on (d.actor=a.actor) "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join sestado s on (d.sestado=s.sestado) "
                            + "left join estatus e on (d.estatus=e.estatus) "
                            + "left join garantia ga on (d.garantia=ga.garantia) "
                            + "left join juicio j on (d.deudor=j.deudor) "
                            + "left join deudor_historial_cobros dhc on (d.deudor=dhc.deudor) "
                            + "WHERE "
                            + "d.cargado='CARGADO' AND "
                            + "dhc.fecha IS NULL AND "
                            + "d.usuario=" + usuario + " "
                            + "GROUP BY "
                            + "d.deudor "
                            + "ORDER BY "
                            + "d.fecha_proximo_pago desc";
                    stmt = conn.createStatement();
                    //System.out.println("QUERY_2: " + cadenasql);
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        Object[] fila = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        this.modelo.addRow(fila);
                    }
                    rs.close();
                    stmt.close();
                    
                    dia = dar_fecha_antiguos().get(Calendar.DATE);
                    mes = dar_fecha_antiguos().get(Calendar.MONTH) + 1;
                    ano = dar_fecha_antiguos().get(Calendar.YEAR);
                    String fecha_antiguo = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                    
                    cadenasql = "SELECT "
                            + "d.deudor AS CODIGO, "
                            + "'GA' AS ACTIVIDAD, "
                            + "a.nombre AS ACTOR, "
                            + "d.caso AS CASO, "
                            + "d.nombre AS NOMBRE, "
                            + "d.fecha_ingreso AS FECHA_INGRESO, "
                            + "u.nombre AS GESTOR, "
                            + "s.nombre AS ESTADO, "
                            + "e.nombre AS STATUS, "
                            + "ga.nombre AS GARANTIA, "
                            + "d.no_cuenta AS NO_CUENTA, "
                            + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                            + "j.no_juicio AS NO_JUICIO, "
                            + "d.cargado AS CARGADO, "
                            + "d.fecha_proximo_pago AS FECHA_PROMESA_PAGO "
                            + "FROM "
                            + "deudor d "
                            + "left join actor a on (d.actor=a.actor) "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join sestado s on (d.sestado=s.sestado) "
                            + "left join estatus e on (d.estatus=e.estatus) "
                            + "left join garantia ga on (d.garantia=ga.garantia) "
                            + "left join juicio j on (d.deudor=j.deudor) "
                            + "left join (SELECT "
                            + "dhc.deudor as deudor, "
                            + "max(dhc.fecha) AS fecha "
                            + "FROM "
                            + "deudor_historial_cobros dhc "
                            + "GROUP BY "
                            + "dhc.deudor) dhc on (d.deudor=dhc.deudor) "
                            + "WHERE "
                            + "d.cargado='CARGADO' AND "
                            + "dhc.fecha IS NOT NULL AND "
                            + "dhc.fecha<='" + fecha_antiguo + "' AND "
                            + "d.usuario=" + usuario + " "
                            + "GROUP BY "
                            + "d.deudor "
                            + "ORDER BY "
                            + "d.fecha_proximo_pago desc";
                    stmt = conn.createStatement();
                    //System.out.println("QUERY_3: " + cadenasql);
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        Object[] fila = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        this.modelo.addRow(fila);
                    }
                    rs.close();
                    stmt.close();
                    
                    cadenasql = "SELECT "
                            + "d.deudor AS CODIGO, "
                            + "'CC' AS ACTIVIDAD, "
                            + "a.nombre AS ACTOR, "
                            + "d.caso AS CASO, "
                            + "d.nombre AS NOMBRE, "
                            + "d.fecha_ingreso AS FECHA_INGRESO, "
                            + "u.nombre AS GESTOR, "
                            + "s.nombre AS ESTADO, "
                            + "e.nombre AS STATUS, "
                            + "ga.nombre AS GARANTIA, "
                            + "d.no_cuenta AS NO_CUENTA, "
                            + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                            + "j.no_juicio AS NO_JUICIO, "
                            + "d.cargado AS CARGADO, "
                            + "d.fecha_proximo_pago AS FECHA_PROMESA_PAGO "
                            + "FROM "
                            + "deudor d "
                            + "left join actor a on (d.actor=a.actor) "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join sestado s on (d.sestado=s.sestado) "
                            + "left join estatus e on (d.estatus=e.estatus) "
                            + "left join garantia ga on (d.garantia=ga.garantia) "
                            + "left join juicio j on (d.deudor=j.deudor) "
                            + "left join (SELECT "
                            + "dhc.deudor as deudor, "
                            + "max(dhc.fecha) AS fecha "
                            + "FROM "
                            + "deudor_historial_cobros dhc "
                            + "GROUP BY "
                            + "dhc.deudor) dhc on (d.deudor=dhc.deudor) "
                            + "WHERE "
                            + "d.cargado='CARGADO' AND "
                            + "d.usuario=3 AND "
                            + "d.codigo_contactabilidad in (select "
                            + "acc.codigo_contactabilidad "
                            + "from "
                            + "asignacion_cartera ac "
                            + "left join asignacion_cartera_codigo_contactabilidad acc on (ac.asignacion_cartera=acc.asignacion_cartera) "
                            + "where "
                            + "ac.fecha=CURRENT_DATE()) "
                            + "ORDER BY "
                            + "d.fecha_proximo_pago desc";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        Object[] fila = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        this.modelo.addRow(fila);
                    }
                    rs.close();
                    stmt.close();
                    
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return this.modelo;
    }
    
    private Calendar dar_fecha_antiguos() {
        Calendar resultado = null;
        
        try{
            String cadenasql = "select a.fecha_casos_por_fecha from asignacion_cartera a where a.fecha=CURRENT_DATE()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = DateToCalendar(rs.getDate(1));
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    private Calendar dar_fecha_asignacion() {
        Calendar resultado = null;
        
        try{
            String cadenasql = "select a.fecha_promesa_pago from asignacion_cartera a where a.fecha=CURRENT_DATE()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = DateToCalendar(rs.getDate(1));
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    private Boolean existe_asignacion() {
        Boolean resultado = false;
        
        try{
            String cadenasql = "select a.asignacion_cartera from asignacion_cartera a where a.fecha=CURRENT_DATE()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = true;
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
        } catch(Exception ex) {
            ;
        }
        
        return cal;
    }

}
