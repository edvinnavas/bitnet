package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Convenio implements Serializable {

    private static final long serialVersionUID = 1L;

    public Convenio() {

    }

    public String Convenio_Insertar(
            Integer usuario_sys,
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
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_pago_inicial.get(Calendar.DATE);
            Integer mes = fecha_pago_inicial.get(Calendar.MONTH) + 1;
            Integer ano = fecha_pago_inicial.get(Calendar.YEAR);
            String dia_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into convenio ("
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
                    + "observacion) value ("
                    + deudor + ","
                    + "CURRENT_DATE()" + ",'"
                    + tipo_convenio + "','"
                    + estado + "',"
                    + saldo + ","
                    + interes + ","
                    + mora + ","
                    + gasto_otros + ","
                    + rebaja_interes + ","
                    + subtotal_pagar + ","
                    + costas + ","
                    + monto_costas + ","
                    + total + ","
                    + cuota_inicial + ","
                    + total_pagar + ","
                    + numero_cuotas + ","
                    + monto_cuota + ",'"
                    + frecuencia + "','"
                    + dia_pago_i + "','"
                    + observacion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select max(c.convenio) from convenio c";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer id_convenio = 0;
            while (rs.next()) {
                id_convenio = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set convenio_pactado='" + observacion + "' where deudor=" + deudor.toString();
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Convenio registrado=> Convenio: " + id_convenio + " "
                    + "Deudor: " + deudor + " "
                    + "Tipo_Convenio: " + tipo_convenio + " "
                    + "Estado: " + estado + " "
                    + "Saldo: " + saldo + " "
                    + "Interes: " + interes + " "
                    + "Mora: " + mora + " "
                    + "Gastos_Otros: " + gasto_otros + " "
                    + "Rebaja_Interes: " + rebaja_interes + " "
                    + "SubTotal: " + subtotal_pagar + " "
                    + "Costas: " + costas + " "
                    + "Monto_Costas: " + monto_costas + " "
                    + "Total: " + total + " "
                    + "Cuota_Inicial: " + cuota_inicial + " "
                    + "Total_Pagar: " + total_pagar + " "
                    + "Numero_Cuotas: " + numero_cuotas + " "
                    + "Monto_Cuota: " + monto_cuota + " "
                    + "Frecuencia: " + frecuencia + " "
                    + "Fecha_Pago_Inicial: " + dia_pago_i + " "
                    + "Observacion: " + observacion + "',"
                    + "109" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Convenio de pago registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Convenio_Modificar(
            Integer usuario_sys,
            Integer id_convenio,
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
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_pago_inicial.get(Calendar.DATE);
            Integer mes = fecha_pago_inicial.get(Calendar.MONTH) + 1;
            Integer ano = fecha_pago_inicial.get(Calendar.YEAR);
            String dia_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

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
                    + "where convenio=" + id_convenio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "update deudor set convenio_pactado='" + observacion + "' where deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Convenio de pago modificado=> Convenio: " + id_convenio + " "
                    + "Deudor: " + deudor + " "
                    + "Tipo_Cconvenio: " + tipo_convenio + " "
                    + "Estado: " + estado + " "
                    + "Saldo: " + saldo + " "
                    + "Interes: " + interes + " "
                    + "Mora: " + mora + " "
                    + "Gastos_Otros: " + gasto_otros + " "
                    + "Rebaja_Interes: " + rebaja_interes + " "
                    + "SubTotal: " + subtotal_pagar + " "
                    + "Costas: " + costas + " "
                    + "Monto_Costas: " + monto_costas + " "
                    + "Total: " + total + " "
                    + "Cuota_Inicial: " + cuota_inicial + " "
                    + "Total_Pagar: " + total_pagar + " "
                    + "Numero_Cuotas: " + numero_cuotas + " "
                    + "Monto_Cuota: " + monto_cuota + " "
                    + "Frecuencia: " + frecuencia + " "
                    + "Fecha_Pago_Inicial: " + dia_pago_i + " "
                    + "Observacion: " + observacion + "',"
                    + "110" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Convenio de pago modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Convenio_Modificar_Estado(
            Integer usuario_sys,
            Integer id_convenio,
            String estado,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update convenio set "
                    + "estado='" + estado + "' "
                    + "where convenio=" + id_convenio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select c.deudor, cuota_inicial, fecha_pago_inicial, monto_cuota from convenio c where c.convenio=" + id_convenio;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer deudor = 0;
            Double cuota_inicial = 0.00;
            Date fecha_pago_inicial = new Date();
            Double monto_cuota = 0.00;
            while (rs.next()) {
                deudor = rs.getInt(1);
                cuota_inicial = rs.getDouble(2);
                fecha_pago_inicial = rs.getDate(3);
                monto_cuota = rs.getDouble(4);
            }
            rs.close();
            stmt.close();

            if (estado.equals("ACTIVO")) {
                Integer max_id_promesa_pago = 0;

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (cuota_inicial > 0.00) {
                    cadenasql = "insert into convenio_detalle ("
                            + "convenio, "
                            + "promesa_pago, "
                            + "fecha_pago, "
                            + "hora_pago, "
                            + "estado_promesa, "
                            + "monto, "
                            + "observacion) value ("
                            + id_convenio + ","
                            + max_id_promesa_pago + ",'"
                            + dateFormat.format(fecha_pago_inicial) + "','"
                            + "23:59:59" + "','"
                            + "PENDIENTE" + "',"
                            + cuota_inicial + ",'"
                            + "Promesa de pago creada automáticamente: PAGO INICIAL." + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();

                    max_id_promesa_pago++;
                }
                cadenasql = "insert into convenio_detalle ("
                        + "convenio, "
                        + "promesa_pago, "
                        + "fecha_pago, "
                        + "hora_pago, "
                        + "estado_promesa, "
                        + "monto, "
                        + "observacion) value ("
                        + id_convenio + ","
                        + max_id_promesa_pago + ",'"
                        + dateFormat.format(fecha_pago_inicial) + "','"
                        + "23:59:59" + "','"
                        + "PENDIENTE" + "',"
                        + monto_cuota + ",'"
                        + "Promesa de pago creada automáticamente: PRIMERA CUOTA." + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                if (max_id_promesa_pago == 0) {
                    cadenasql = "update deudor set opcion_proximo_pago='SI', fecha_proximo_pago='" + dateFormat.format(fecha_pago_inicial) + "', cuota_convenio=" + monto_cuota + " where deudor=" + deudor;
                } else {
                    cadenasql = "update deudor set opcion_proximo_pago='SI', fecha_proximo_pago='" + dateFormat.format(fecha_pago_inicial) + "', cuota_convenio=" + cuota_inicial + " where deudor=" + deudor;
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

            }
            if (estado.equals("ANULADO") || estado.equals("TERMINADO")) {
                cadenasql = "update convenio_detalle set estado_promesa='INCUMPLIDO' where convenio=" + id_convenio + " and estado_promesa='PENDIENTE'";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update deudor set opcion_proximo_pago='NO' where deudor=" + deudor;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Convenio de pago modificar estado=> Convenio: " + id_convenio + " Estado: " + estado + "',"
                    + "111" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Convenio estado modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar_Estado): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Modificar_Estado - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Convenio_Detalle_Insertar(
            Integer usuario_sys,
            Integer id_convenio,
            Calendar fecha_pago,
            String hora_pago,
            String estado_promesa,
            Double monto,
            String observacion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_pago.get(Calendar.DATE);
            Integer mes = fecha_pago.get(Calendar.MONTH) + 1;
            Integer ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "select max(cd.promesa_pago) from convenio_detalle cd where cd.convenio=" + id_convenio;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer max_id_promesa_pago = 0;
            while (rs.next()) {
                max_id_promesa_pago = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            max_id_promesa_pago++;

            cadenasql = "insert into convenio_detalle ("
                    + "convenio, "
                    + "promesa_pago, "
                    + "fecha_pago, "
                    + "hora_pago, "
                    + "estado_promesa, "
                    + "monto, "
                    + "observacion) value ("
                    + id_convenio + ","
                    + max_id_promesa_pago + ",'"
                    + fecha_pago_i + "','"
                    + hora_pago + "','"
                    + estado_promesa + "',"
                    + monto + ",'"
                    + observacion + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select c.deudor from convenio c where c.convenio=" + id_convenio;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            Integer deudor = 0;
            while (rs.next()) {
                deudor = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set opcion_proximo_pago='SI', fecha_proximo_pago='" + fecha_pago_i + "', cuota_convenio=" + monto + " where deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa de pago insertar=> Deudor: " + deudor + " "
                    + "Convenio: " + id_convenio + " "
                    + "Promesa pago: " + max_id_promesa_pago + " "
                    + "Fecha pago: " + fecha_pago_i + " "
                    + "Hora pago: " + hora_pago + " "
                    + "Estado promesa pago: " + estado_promesa + " "
                    + "Monto: " + monto + " "
                    + "Observacion: " + observacion + "',"
                    + "141" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Convenio_Detalle_Modificar(
            Integer usuario_sys,
            Integer id_convenio,
            Integer id_promesa_pago,
            Calendar fecha_pago,
            String hora_pago,
            String estado_promesa,
            Double monto,
            String observacion,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_pago.get(Calendar.DATE);
            Integer mes = fecha_pago.get(Calendar.MONTH) + 1;
            Integer ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update convenio_detalle set "
                    + "fecha_pago='" + fecha_pago_i + "', "
                    + "hora_pago='" + hora_pago + "', "
                    + "estado_promesa='" + estado_promesa + "', "
                    + "monto=" + monto + ", "
                    + "observacion='" + observacion + "' "
                    + "where convenio=" + id_convenio + " and promesa_pago=" + id_promesa_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa de pago modificar=> Convenio: " + id_convenio + " "
                    + "Promesa pago: " + id_promesa_pago + " "
                    + "Fecha pago: " + fecha_pago_i + " "
                    + "Hora pago: " + hora_pago + " "
                    + "Estado promesa pago: " + estado_promesa + " "
                    + "Monto: " + monto + " "
                    + "Observacion: " + observacion + "',"
                    + "142" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String Convenio_Detalle_Modificar_Estado(
            Integer usuario_sys,
            Integer id_convenio,
            Integer id_promesa_pago,
            String estado,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update convenio_detalle set "
                    + "estado_promesa='" + estado + "' "
                    + "where convenio=" + id_convenio + " and promesa_pago=" + id_promesa_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select c.deudor from convenio c where c.convenio=" + id_convenio;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Integer deudor = 0;
            while (rs.next()) {
                deudor = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select fecha_pago, monto from convenio_detalle cd where cd.estado_promesa='PENDIENTE' and cd.convenio=" + id_convenio;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            String opcion_proximo_pago = "NO";
            Date fecha_pago = new Date();
            Double monto_cuota = 0.00;
            while (rs.next()) {
                opcion_proximo_pago = "SI";
                fecha_pago = rs.getDate(1);
                monto_cuota = rs.getDouble(2);
            }
            rs.close();
            stmt.close();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cadenasql = "update deudor set opcion_proximo_pago='" + opcion_proximo_pago + "', fecha_proximo_pago='" + dateFormat.format(fecha_pago) + "', cuota_convenio=" + monto_cuota + " where deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa de pago modificar estado=> Convenio: " + id_convenio + " Promesa Pago: " + id_promesa_pago + " Estado: " + estado + "',"
                    + "143" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "El estado de la promesa de pago ha sido modificada.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar_Estado): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Convenio_Detalle_Modificar_Estado - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
