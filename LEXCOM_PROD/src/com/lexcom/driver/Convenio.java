package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

public class Convenio {

    Connection conn;
    Integer usuario_sys;
    
    public Integer deudor;
    public Calendar fecha_creacion;
    public String tipo_convenio;
    public String estado;
    public Double saldo;
    public Double interes;
    public Double mora;
    public Double gasto_otros;
    public Double rebaja_interes;
    public Double subtotal_pagar;
    public Double costas;
    public Double monto_costas;
    public Double total;
    public Double cuota_inicial;
    public Double total_pagar;
    public Integer numero_cuotas;
    public Double monto_cuota;
    public String frecuencia;
    public Calendar fecha_pago_inicial;
    public String observacion;
    
    public List<Nodo_Convenio_Detalle> promesas;

    public Convenio(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
        
        promesas = new ArrayList<>();
    }

    public String insertar(
            Integer deudor,
            String tipo_convenio,
            String estado,
            Double saldo,
            Double interes,
            Double mora,
            Double gasto_otros,
            Double rebaja_interes,
            Double subtotal_pagar,
            Double costas,
            Double monto_costas,
            Double total,
            Double cuota_inicial,
            Double total_pagar,
            Integer numero_cuotas,
            Double monto_cuota,
            String frecuencia,
            Calendar fecha_pago_inicial,
            String observacion,
            List<Nodo_Convenio_Detalle> promesas) {

        String resultado = "";

        Integer dia = fecha_pago_inicial.get(Calendar.DATE);
        Integer mes = fecha_pago_inicial.get(Calendar.MONTH) + 1;
        Integer ano = fecha_pago_inicial.get(Calendar.YEAR);
        String dia_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "insert into convenio ("
                    + "deudor,"
                    + "fecha_creacion,"
                    + "tipo_convenio,"
                    + "estado,"
                    + "saldo,"
                    + "interes,"
                    + "mora,"
                    + "gasto_otros,"
                    + "rebaja_interes,"
                    + "subtotal_pagar,"
                    + "costas,"
                    + "monto_costas,"
                    + "total,"
                    + "cuota_inicial,"
                    + "total_pagar,"
                    + "numero_cuotas,"
                    + "monto_cuota,"
                    + "frecuencia,"
                    + "fecha_pago_inicial,"
                    + "observacion, "
                    + "fecha_activacion, "
                    + "fecha_terminacion "
                    + ") value ("
                    + deudor.toString() + ","
                    + "CURRENT_DATE()" + ",'"
                    + tipo_convenio + "','"
                    + estado + "',"
                    + saldo.toString() + ","
                    + interes.toString() + ","
                    + mora.toString() + ","
                    + gasto_otros.toString() + ","
                    + rebaja_interes.toString() + ","
                    + subtotal_pagar.toString() + ","
                    + costas.toString() + ","
                    + monto_costas.toString() + ","
                    + total.toString() + ","
                    + cuota_inicial.toString() + ","
                    + total_pagar.toString() + ","
                    + numero_cuotas.toString() + ","
                    + monto_cuota.toString() + ",'"
                    + frecuencia + "','"
                    + dia_pago_i + "','"
                    + observacion + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_DATE()" + ")";
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            cadenasql = "update deudor set opcion_proximo_pago='NO', convenio_pactado='" + observacion + "' where deudor=" + deudor.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            Integer max_convenio = 0;
            cadenasql = "select max(c.convenio) from convenio c";
            stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                max_convenio = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            
            for(Integer i=0; i < promesas.size(); i++) {
                dia = promesas.get(i).getFecha_pago().get(Calendar.DATE);
                mes = promesas.get(i).getFecha_pago().get(Calendar.MONTH) + 1;
                ano = promesas.get(i).getFecha_pago().get(Calendar.YEAR);
                String fecha_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                
                cadenasql = "insert into convenio_detalle (convenio, promesa_pago, fecha_pago, hora_pago, estado_promesa, monto, observacion) values ('"
                        + max_convenio.toString() + "','"  
                        + promesas.get(i).getPromesa_pago().toString() + "','"  
                        + fecha_pago_i + "','"  
                        + promesas.get(i).getHora_pago() + "','"  
                        + promesas.get(i).getEstado_promesa() + "','"  
                        + promesas.get(i).getMonto().toString() + "','"  
                        + promesas.get(i).getObservacion() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            Calendar fecha_proximo_pago = null;
            Double monto_promesa = 0.00;
            String opcion_proximo_pago = "NO";
            cadenasql = "select "
                    + "tabla.convenio, "
                    + "tabla.fecha_min, "
                    + "c.monto "
                    + "from "
                    + "(select c.convenio, min(c.fecha_pago) fecha_min from convenio_detalle c where c.estado_promesa = 'PENDIENTE' group by c.convenio) tabla "
                    + "left join convenio_detalle c on (tabla.convenio = c.convenio and  tabla.fecha_min = c.fecha_pago) "
                    + "where c.estado_promesa = 'PENDIENTE' and c.convenio = " + max_convenio.toString();
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                fecha_proximo_pago = this.DateToCalendar(rs.getDate(2));
                monto_promesa = rs.getDouble(3);
                opcion_proximo_pago = "SI";
            }
            rs.close();
            stmt.close();
            
            if(opcion_proximo_pago.equals("SI")) {
                dia = fecha_proximo_pago.get(Calendar.DATE);
                mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
                ano = fecha_proximo_pago.get(Calendar.YEAR);
                String fecha_proximo_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                
                cadenasql = "update deudor set "
                        + "cuota_convenio='" + monto_promesa + "', "
                        + "fecha_proximo_pago='" + fecha_proximo_pago_i + "', "
                        + "opcion_proximo_pago='" + opcion_proximo_pago + "' "
                        + "where deudor=" + deudor.toString();
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Convenio registrado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Convenio registrado=> Deudor: " + deudor
                    + "Tipo_Cconvenio: " + tipo_convenio + " "
                    + "Estado: " + estado + " "
                    + "Saldo: " + saldo.toString() + " "
                    + "Interes: " + interes.toString() + " "
                    + "Mora: " + mora.toString() + " "
                    + "Gastos_Otros: " + gasto_otros.toString() + " "
                    + "Rebaja_Interes: " + rebaja_interes.toString() + " "
                    + "SubTotal: " + subtotal_pagar.toString() + " "
                    + "Costas: " + costas.toString() + " "
                    + "Monto_Costas: " + monto_costas.toString() + " "
                    + "Total: " + total.toString() + " "
                    + "Cuota_Inicial: " + cuota_inicial.toString() + " "
                    + "Total_Pagar: " + total_pagar.toString() + " "
                    + "Numero_Cuotas: " + numero_cuotas.toString() + " "
                    + "Monto_Cuota: " + monto_cuota.toString() + " "
                    + "Frecuencia: " + frecuencia + " "
                    + "Fecha_Pago_Inicial: " + dia_pago_i + " "
                    + "Observacion: " + observacion, 109);
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
            Integer convenio,
            Integer deudor,
            String tipo_convenio,
            String estado,
            Double saldo,
            Double interes,
            Double mora,
            Double gasto_otros,
            Double rebaja_interes,
            Double subtotal_pagar,
            Double costas,
            Double monto_costas,
            Double total,
            Double cuota_inicial,
            Double total_pagar,
            Integer numero_cuotas,
            Double monto_cuota,
            String frecuencia,
            Calendar fecha_pago_inicial,
            String observacion,
            List<Nodo_Convenio_Detalle> promesas) {
        
        String resultado = "";
        
        Integer dia = fecha_pago_inicial.get(Calendar.DATE);
        Integer mes = fecha_pago_inicial.get(Calendar.MONTH) + 1;
        Integer ano = fecha_pago_inicial.get(Calendar.YEAR);
        String dia_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "update convenio set "
                + "deudor=" + deudor.toString() + ", "
                + "tipo_convenio='" + tipo_convenio + "', "
                + "estado='" + estado + "', "
                + "saldo=" + saldo.toString() + ", "
                + "interes=" + interes.toString() + ", "
                + "mora=" + mora.toString() + ", "
                + "gasto_otros=" + gasto_otros.toString() + ", "
                + "rebaja_interes=" + rebaja_interes.toString() + ", "
                + "subtotal_pagar=" + subtotal_pagar.toString() + ", "
                + "costas=" + costas.toString() + ", "
                + "monto_costas=" + monto_costas.toString() + ", "
                + "total=" + total.toString() + ", "
                + "cuota_inicial=" + cuota_inicial.toString() + ", "
                + "total_pagar=" + total_pagar.toString() + ", "
                + "numero_cuotas=" + numero_cuotas.toString() + ", "
                + "monto_cuota=" + monto_cuota.toString() + ", "
                + "frecuencia='" + frecuencia + "', "
                + "fecha_pago_inicial='" + dia_pago_i + "', "
                + "observacion='" + observacion + "' "
                + "where convenio=" + convenio.toString();
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            cadenasql = "update deudor set opcion_proximo_pago='NO', convenio_pactado='" + observacion + "' where deudor=" + deudor.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            cadenasql = "delete from convenio_detalle where convenio=" + convenio.toString();
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            for(Integer i=0; i < promesas.size(); i++) {
                dia = promesas.get(i).getFecha_pago().get(Calendar.DATE);
                mes = promesas.get(i).getFecha_pago().get(Calendar.MONTH) + 1;
                ano = promesas.get(i).getFecha_pago().get(Calendar.YEAR);
                String fecha_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                
                cadenasql = "insert into convenio_detalle (convenio, promesa_pago, fecha_pago, hora_pago, estado_promesa, monto, observacion) values ('"
                        + convenio.toString() + "','"  
                        + i.toString() + "','"  
                        + fecha_pago_i + "','"  
                        + promesas.get(i).getHora_pago() + "','"  
                        + promesas.get(i).getEstado_promesa() + "','"  
                        + promesas.get(i).getMonto().toString() + "','"  
                        + promesas.get(i).getObservacion() + "')";
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            Calendar fecha_proximo_pago = null;
            Double monto_promesa = 0.00;
            String opcion_proximo_pago = "NO";
            cadenasql = "select "
                    + "tabla.convenio, "
                    + "tabla.fecha_min, "
                    + "c.monto "
                    + "from "
                    + "(select c.convenio, min(c.fecha_pago) fecha_min from convenio_detalle c where c.estado_promesa = 'PENDIENTE' group by c.convenio) tabla "
                    + "left join convenio_detalle c on (tabla.convenio = c.convenio and  tabla.fecha_min = c.fecha_pago) "
                    + "where c.estado_promesa = 'PENDIENTE' and c.convenio = " + convenio.toString();
            stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                fecha_proximo_pago = this.DateToCalendar(rs.getDate(2));
                monto_promesa = rs.getDouble(3);
                opcion_proximo_pago = "SI";
            }
            rs.close();
            stmt.close();
            
            if(opcion_proximo_pago.equals("SI")) {
                dia = fecha_proximo_pago.get(Calendar.DATE);
                mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
                ano = fecha_proximo_pago.get(Calendar.YEAR);
                String fecha_proximo_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                
                cadenasql = "update deudor set "
                        + "cuota_convenio='" + monto_promesa + "', "
                        + "fecha_proximo_pago='" + fecha_proximo_pago_i + "', "
                        + "opcion_proximo_pago='" + opcion_proximo_pago + "' "
                        + "where deudor=" + deudor.toString();
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            if (estado.equals("ACTIVO")) {
                cadenasql = "update convenio set "
                        + "fecha_activacion=CURRENT_DATE() "
                        + "where convenio=" + convenio.toString();
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            if (estado.equals("ANULADO") || estado.equals("TERMINADO")) {
                cadenasql = "update convenio_detalle set "
                        + "estado_promesa='INCUMPLIDO' "
                        + "where estado_promesa <> 'CUMPLIDO' and convenio=" + convenio.toString();
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update deudor set "
                        + "opcion_proximo_pago='NO' "
                        + "where deudor=" + deudor;
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update convenio set "
                        + "fecha_terminacion=CURRENT_DATE() "
                        + "where convenio=" + convenio.toString();
                stmt = this.conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Convenio modificado en el sistema.";
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Convenio modificado=> Deudor: " + deudor
                    + "Tipo_Cconvenio: " + tipo_convenio + " "
                    + "Estado: " + estado + " "
                    + "Saldo: " + saldo.toString() + " "
                    + "Interes: " + interes.toString() + " "
                    + "Mora: " + mora.toString() + " "
                    + "Gastos_Otros: " + gasto_otros.toString() + " "
                    + "Rebaja_Interes: " + rebaja_interes.toString() + " "
                    + "SubTotal: " + subtotal_pagar.toString() + " "
                    + "Costas: " + costas.toString() + " "
                    + "Monto_Costas: " + monto_costas.toString() + " "
                    + "Total: " + total.toString() + " "
                    + "Cuota_Inicial: " + cuota_inicial.toString() + " "
                    + "Total_Pagar: " + total_pagar.toString() + " "
                    + "Numero_Cuotas: " + numero_cuotas.toString() + " "
                    + "Monto_Cuota: " + monto_cuota.toString() + " "
                    + "Frecuencia: " + frecuencia + " "
                    + "Fecha_Pago_Inicial: " + dia_pago_i + " "
                    + "Observacion: " + observacion, 110);
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
    
    public Convenio obtener(Integer seleccion) {
        try {
            String cadenasql = "select "
                    + "deudor, "
                    + "fecha_creacion, "
                    + "tipo_convenio, "
                    + "estado, "
                    + "saldo, "
                    + "interes, "
                    + "mora, "
                    + "gasto_otros, "
                    + "rebaja_interes, "
                    + "subtotal_pagar, "
                    + "costas, "
                    + "monto_costas, "
                    + "total, "
                    + "cuota_inicial, "
                    + "total_pagar, "
                    + "numero_cuotas, "
                    + "monto_cuota, "
                    + "frecuencia, "
                    + "fecha_pago_inicial, "
                    + "observacion "
                    + "from "
                    + "convenio u "
                    + "where u.convenio=" + seleccion.toString();
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.deudor = rs.getInt(1);
                this.fecha_creacion = this.DateToCalendar(rs.getDate(2));
                this.tipo_convenio = rs.getString(3);
                this.estado = rs.getString(4);
                this.saldo = rs.getDouble(5);
                this.interes = rs.getDouble(6);
                this.mora = rs.getDouble(7);
                this.gasto_otros = rs.getDouble(8);
                this.rebaja_interes = rs.getDouble(9);
                this.subtotal_pagar = rs.getDouble(10);
                this.costas = rs.getDouble(11);
                this.monto_costas = rs.getDouble(12);
                this.total = rs.getDouble(13);
                this.cuota_inicial = rs.getDouble(14);
                this.total_pagar = rs.getDouble(15);
                this.numero_cuotas = rs.getInt(16);
                this.monto_cuota = rs.getDouble(17);
                this.frecuencia = rs.getString(18);
                this.fecha_pago_inicial = this.DateToCalendar(rs.getDate(19));
                this.observacion = rs.getString(20);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select "
                    + "u.promesa_pago, "
                    + "u.fecha_pago, "
                    + "u.hora_pago, "
                    + "u.estado_promesa, "
                    + "u.monto, "
                    + "u.observacion "
                    + "from "
                    + "convenio_detalle u "
                    + "where u.convenio=" + seleccion.toString();
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Nodo_Convenio_Detalle nodo_promesas = new Nodo_Convenio_Detalle(
                        rs.getInt(1),
                        this.DateToCalendar(rs.getDate(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getString(6));
                this.promesas.add(nodo_promesas);
            }
            rs.close();
            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return this;
    }
    
    public String eliminar(Integer seleccion) {        
        String resultado = "";
        String cadenasql = "delete from convenio where convenio= " + seleccion.toString();
        
        try {
            Convenio convenio_temp = new Convenio(conn, usuario_sys);
            convenio_temp.obtener(seleccion);
            
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Convenio eliminado.";
            
            Integer dia = convenio_temp.fecha_pago_inicial.get(Calendar.DATE);
            Integer mes = convenio_temp.fecha_pago_inicial.get(Calendar.MONTH) + 1;
            Integer ano = convenio_temp.fecha_pago_inicial.get(Calendar.YEAR);
            String dia_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
            
            dia = convenio_temp.fecha_creacion.get(Calendar.DATE);
            mes = convenio_temp.fecha_creacion.get(Calendar.MONTH) + 1;
            ano = convenio_temp.fecha_creacion.get(Calendar.YEAR);
            String fecha_creacion_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
            
            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento (conn);
            drive.insertar(this.usuario_sys, "Convenio eliminado=>"
                    + " Convenio: " + seleccion 
                    + " Deudor: " + convenio_temp.deudor
                    + " Fecha_Creacion: " + fecha_creacion_i
                    + " Tipo_Convivio: " + convenio_temp.tipo_convenio
                    + " Estado: " + convenio_temp.estado
                    + " Saldo: " + convenio_temp.saldo.toString()
                    + " Interes: " + convenio_temp.interes.toString()
                    + " Mora: " + convenio_temp.mora.toString()
                    + " Gastos_Otros: " + convenio_temp.gasto_otros.toString()
                    + " Rebaja_Interes: " + convenio_temp.rebaja_interes.toString()
                    + " SubTotal: " + convenio_temp.subtotal_pagar.toString()
                    + " Costas: " + convenio_temp.costas.toString()
                    + " Monto_Costas: " + convenio_temp.monto_costas.toString()
                    + " Total: " + convenio_temp.total
                    + " Cuota_Inicial: " + convenio_temp.cuota_inicial.toString()
                    + " Total_Pagar: " + convenio_temp.total_pagar.toString()
                    + " Numero_Cuotas: " + convenio_temp.numero_cuotas.toString()
                    + " Monto_Cuota: " + convenio_temp.monto_cuota.toString()
                    + " Frecuencia: " + convenio_temp.frecuencia
                    + " Fecha_Primer_Cuota: " + dia_pago_i
                    + " Observacion: " + convenio_temp.observacion, 111);
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
    
    public Integer dar_convenio_activo(Integer deudor) {
        Integer resultado = 0;
        
        try {
            String cadenasql = "select "
                    + "u.convenio "
                    + "from "
                    + "convenio u where u.estado='ACTIVO' and u.deudor=" + deudor;
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            resultado = 0;
        }
        
        return resultado;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
}
