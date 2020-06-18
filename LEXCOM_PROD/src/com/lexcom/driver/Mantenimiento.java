package com.lexcom.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

public class Mantenimiento {
    
    Connection conn;
    
    public Mantenimiento(Connection conn) {
        this.conn = conn;
    }
    
    public void agregar_convenio_promesa_pago() {
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "SELECT "
                    + "d.deudor, "
                    + "d.opcion_proximo_pago, "
                    + "d.fecha_proximo_pago, "
                    + "d.cuota_convenio, "
                    + "d.convenio_pactado "
                    + "FROM "
                    + "deudor d "
                    + "WHERE "
                    + "d.opcion_proximo_pago = 'SI'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            
            Integer contador = 1;
            while (rs.next()) {
                cadenasql = "insert into convenio ("
                        + "deudor,"
                        + "fecha_creacion,"
                        + "estado,"
                        + "observacion,"
                        + "monto_deudor,"
                        + "interes,"
                        + "subtotal_pagar,"
                        + "costas,"
                        + "total_pagar,"
                        + "cuota_inicial,"
                        + "numero_cuotas,"
                        + "frecuencia,"
                        + "cuota_periodo,"
                        + "dia_pago) values ("
                        + rs.getString(1) + "," //DEUDOR
                        + "CURRENT_DATE()" + ",'"
                        + "ACTIVO" + "','"
                        + rs.getString(5) + "'," //CONVENIO PACTADO
                        + rs.getString(4) + "," // MONTO CONVENIO
                        + "0.00" + ","
                        + rs.getString(4) + ","
                        + "0.00" + ","
                        + rs.getString(4) + ","
                        + "0.00" + ","
                        + "1" + ",'"
                        + "MENSUAL" + "',"
                        + rs.getString(4) + ",'"
                        + rs.getString(3) + "')"; //FECHA CREACION CONVENIO
                Statement stmt1 = conn.createStatement();
                System.out.println(contador + ":" + cadenasql);
                stmt1.executeUpdate(cadenasql);
                
                Integer max_convenio = 1;
                cadenasql = "select max(c.convenio) from convenio c";
                stmt1 = conn.createStatement();
                System.out.println(contador + ":" + cadenasql);
                ResultSet rs1 = stmt1.executeQuery(cadenasql);
                while(rs1.next()) {
                    max_convenio = rs1.getInt(1);
                }
                rs1.close();
                stmt1.close();
                
                cadenasql = "insert into convenio_detalle ("
                        + "convenio,"
                        + "promesa_pago,"
                        + "fecha_pago,"
                        + "hora_pago,"
                        + "estado_promesa,"
                        + "monto,"
                        + "observacion) values ("
                        + max_convenio.toString() + ","
                        + "1" + ",'"
                        + rs.getString(3) + "','"
                        + "00:00:00" + "','"
                        + "PENDIENTE" + "',"
                        + rs.getString(4) + ",'"
                        + "Promesa de pago migrada desde la tabla deudores." + "')";
                stmt1 = conn.createStatement();
                System.out.println(contador + ":" + cadenasql);
                stmt1.executeUpdate(cadenasql);
                
                contador++;
            }
            
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void actualizar_deudor_contactabilidad_ultimo() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "SELECT "
                    + "dhc.deudor as deudor, "
                    + "dhc.fecha AS fecha, "
                    + "dhc.codigo_contactabilidad as codigo "
                    + "FROM "
                    + "(SELECT "
                    + "dh.deudor as deudor, "
                    + "max(dh.fecha) AS fecha "
                    + "FROM "
                    + "deudor_historial_cobros dh "
                    + "WHERE "
                    + "dh.fecha IS NOT NULL "
                    + "GROUP BY "
                    + "dh.deudor) dh "
                    + "LEFT JOIN deudor_historial_cobros dhc ON (dh.deudor=dhc.deudor and dh.fecha=dhc.fecha) "
                    + "WHERE "
                    + "dhc.fecha IS NOT NULL "
                    + "GROUP BY "
                    + "dhc.deudor, dhc.fecha";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer contador = 1;
            while (rs.next()) {
                cadenasql = "update deudor set codigo_contactabilidad=" + rs.getString(3) + " where deudor=" + rs.getString(1);
                System.out.println(contador + ": update deudor set codigo_contactabilidad=" + rs.getString(3) + " where deudor=" + rs.getString(1));
                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate(cadenasql);
                contador++;
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void actualizar_deudor_contactabilidad() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer codigo = aleatorio.nextInt(9)+1;
                    
                    cadenasql = "update deudor set codigo_contactabilidad=" + codigo.toString() + " where deudor=" + rs.getString(1);
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(cadenasql);
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_pagos_deudor() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(10)+1;
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer banco = aleatorio.nextInt(16)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        Double monto = aleatorio.nextDouble() * rs.getDouble(2);
                        cadenasql = "insert into pago (deudor, banco, fecha, no_boleta, monto, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + banco + "','"
                                + fecha_pago + "','"
                                + "000-" + rs.getString(1) + "','"
                                + monto.toString() + "','"
                                + "descripcion" + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_aumento_deudor() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(2);
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer tipo_aumento = aleatorio.nextInt(2)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        Double monto = aleatorio.nextDouble() * rs.getDouble(2);
                        cadenasql = "insert into aumento (deudor, tipo_aumento, fecha, monto, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + tipo_aumento.toString() + "','"
                                + fecha_pago + "','"
                                + monto.toString() + "','"
                                + "descripcion" + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_descuento_deudor() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(2);
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer tipo_descuento = aleatorio.nextInt(2)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        Double monto = aleatorio.nextDouble() * rs.getDouble(2);
                        cadenasql = "insert into descuento (deudor, tipo_descuento, fecha, monto, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + tipo_descuento.toString() + "','"
                                + fecha_pago + "','"
                                + monto.toString() + "','"
                                + "descripcion" + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_historio_cobros() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(20)+1;
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer Codigo_Contactabiliad = aleatorio.nextInt(9)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_historial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        cadenasql = "insert into deudor_historial_cobros (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + fecha_historial + "',"
                                + "CURRENT_TIME()" + ",'"
                                + "1" + "','"
                                + Codigo_Contactabiliad.toString() + "','"
                                + "descripcion de prueba " + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                        
                        cadenasql = "update deudor "
                                + "set codigo_contactabilidad='" + Codigo_Contactabiliad.toString() + "' "
                                + "where deudor=" + rs.getString(1);
                        Statement stmt2 = conn.createStatement();
                        stmt2.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_historio_administrativo() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(20)+1;
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer Codigo_Contactabiliad = aleatorio.nextInt(9)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_historial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        cadenasql = "insert into deudor_historial_administrativo (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + fecha_historial + "',"
                                + "CURRENT_TIME()" + ",'"
                                + "1" + "','"
                                + Codigo_Contactabiliad.toString() + "','"
                                + "descripcion de prueba " + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void agregar_historio_juridico() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor, d.monto_inicial from deudor d";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while(rs.next()) {
                    Random aleatorio = new Random();
                    Integer num_pagos = aleatorio.nextInt(20)+1;
                    
                    for(Integer i=0; i<=num_pagos; i++) {
                        Integer Codigo_Contactabiliad = aleatorio.nextInt(9)+1;
                        Calendar fecha = this.fecha_aleatorea();
                        Integer dia = fecha.get(Calendar.DATE);
                        Integer mes = fecha.get(Calendar.MONTH) + 1;
                        Integer ano = fecha.get(Calendar.YEAR);
                        String fecha_historial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
                        cadenasql = "insert into deudor_historial_juridico (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                                + rs.getString(1) + "','"
                                + fecha_historial + "',"
                                + "CURRENT_TIME()" + ",'"
                                + "1" + "','"
                                + Codigo_Contactabiliad.toString() + "','"
                                + "descripcion de prueba " + rs.getString(1) + "')";
                        Statement stmt1 = conn.createStatement();
                        stmt1.executeUpdate(cadenasql);
                        System.out.println(cadenasql);
                    }
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void permisos_usuario() {
        try {
            conn.setAutoCommit(false);
            String cadenasql1 = "select u.usuario from usuario u where u.usuario>0";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(cadenasql1);
            while(rs1.next()) {
                String cadenasql2 = "select m.menu from menu m where m.tipo=1";
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(cadenasql2);
                while(rs2.next()) {
                    try {
                            String cadenasql3 = "insert into permiso_usuario (usuario,menu,nuevo,modificar,eliminar,activar,ver) values ('" + rs1.getString(1) + "','" + rs2.getString(1) + "','NO','NO','NO','NO','NO')";
                            Statement stmt3 = conn.createStatement();
                            System.out.println(cadenasql3);
                            stmt3.executeUpdate(cadenasql3);
                    } catch(Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
                rs2.close();
                stmt2.close();
                
                cadenasql2 = "select m.menu from menu m where m.tipo=2";
                stmt2 = conn.createStatement();
                rs2 = stmt2.executeQuery(cadenasql2);
                while(rs2.next()) {
                    try {
                        String cadenasql3 = "insert into permiso_usuario_uno (usuario,menu,ver) values ('" + rs1.getString(1) + "','" + rs2.getString(1) + "','SI')";
                        Statement stmt3 = conn.createStatement();
                        System.out.println(cadenasql3);
                        stmt3.executeUpdate(cadenasql3);
                    } catch(Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
                rs2.close();
                stmt2.close();
            }
            rs1.close();
            stmt1.close();
            
            conn.commit();
            conn.setAutoCommit(true);
        } catch(Exception ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void insertar_juicios_AA_No_Demandado() {
        try {
            conn.setAutoCommit(false);
            String cadenasql = "select d.deudor from deudor d where d.deudor not in (select j.deudor from juicio j)";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                cadenasql = "insert into juicio ("
                        + "deudor,"
                        + "juzgado,"
                        + "fecha,"
                        + "no_juicio,"
                        + "monto,"
                        + "descripcion,"
                        + "procurador,"
                        + "razon_notificacion,"
                        + "notificador,"
                        + "abogado_deudor,"
                        + "sumario,"
                        + "memorial,"
                        + "procuracion,"
                        + "fecha,"
                        + "deudor_notificado,"
                        + "fecha_notificacion,"
                        + "depositario,"
                        + "tiempo_estimado) values ('" 
                        + rs.getString(1) + "','" 
                        + "30" + "','" 
                        + "1900/01/01" + "','" 
                        + "0" + "','" 
                        + "0.00" + "','" 
                        + "-" + "','" 
                        + "66" + "','" 
                        + "-" + "','"
                        + "0" + "','" 
                        + "-" + "','" 
                        + "-" + "','" 
                        + "1900/01/01" + "','" 
                        + "-" + "','" 
                        + "1900/01/01" + "','" 
                        + "NO" + "','" 
                        + "1900/01/01" + "','" 
                        + "-" + "','" 
                        + "0" + "')"; 
                Statement stmt2 = conn.createStatement();
                stmt2.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);
        } catch(Exception ex) {
            try {
                conn.rollback();
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
    public void migrar_fecha_telefono_celular() {
        Integer contador = 1;
        try {
            String cadenasql = "select "
                    + "de.deudor, "
                    + "de.telefono_celular, "
                    + "de.descripcion "
                    + "from deudor de "
                    + "where de.deudor not in (select d.deudor from deudor d where (length(trim(replace(d.telefono_celular,'-',''))) = 8 and trim(replace(d.telefono_celular,'-','')) regexp '^[0-9]+$') or (d.telefono_celular='0'))";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                Integer deudor = rs.getInt(1);
                String telefono_celular = rs.getString(2);
                String notas = rs.getString(3);
                notas = "- TELEFONO_CELULAR: {" + telefono_celular + "}\n" + notas;
                
                cadenasql = "update deudor set telefono_celular='0', descripcion='" + notas + "' where deudor=" + deudor;
                Statement stmt1 = conn.createStatement();
                System.out.println("ACTUALIZAR ID-DEUDOR: " + deudor + "CONTADOR: " + contador);
                stmt1.executeUpdate(cadenasql);
                stmt1.close();
                
                contador++;
            }
            rs.close();
            stmt.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    private Calendar fecha_aleatorea() {
        Random aleatorio = new Random();
        Calendar fecha = Calendar.getInstance();
        fecha.set (aleatorio.nextInt(84)+1930, aleatorio.nextInt(12)+1, aleatorio.nextInt(26)+1);
        
        return fecha;
    }
    
}
