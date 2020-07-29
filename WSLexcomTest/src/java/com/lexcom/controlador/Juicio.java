package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;

public class Juicio implements Serializable {

    private static final long serialVersionUID = 1L;

    public Juicio() {

    }

    public String juicio_modificar(
            Integer usuario_sys,
            Integer id_juicio,
            Integer deudor,
            Integer juzgado,
            Calendar fecha,
            String no_juicio,
            Double monto,
            String descripcion,
            String[][] modelo_arraigo,
            String[][] modelo_banco,
            String[][] modelo_finca,
            String[][] modelo_salario,
            String[][] modelo_vehiculo,
            String[][] modelo_otros,
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
            String tiempo_estimado,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

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
                    + "where juicio=" + id_juicio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_arraigo where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_banco where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_finca where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_salario where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_vehiculo where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "delete from juicio_otros where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < modelo_arraigo.length; i++) {
                cadenasql = "insert into juicio_arraigo (juicio, correlativo, arraigo, deligenciado) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_arraigo[i][0] + "','"
                        + modelo_arraigo[i][1] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < modelo_banco.length; i++) {
                cadenasql = "insert into juicio_banco (juicio, correlativo, bancos, deligenciado) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_banco[i][0] + "','"
                        + modelo_banco[i][1] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < modelo_finca.length; i++) {
                cadenasql = "insert into juicio_finca (juicio, correlativo, finca, letra, deligenciado, tramite) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_finca[i][0] + "','"
                        + modelo_finca[i][1] + "','"
                        + modelo_finca[i][2] + "','"
                        + modelo_finca[i][3] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < modelo_salario.length; i++) {
                cadenasql = "insert into juicio_salario (juicio, correlativo, salario, empresa, deligenciado) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_salario[i][0] + "','"
                        + modelo_salario[i][1] + "','"
                        + modelo_salario[i][2] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < modelo_vehiculo.length; i++) {
                cadenasql = "insert into juicio_vehiculo (juicio, correlativo, vehiculo, medida, deligenciado) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_vehiculo[i][0] + "','"
                        + modelo_vehiculo[i][1] + "','"
                        + modelo_vehiculo[i][2] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < modelo_otros.length; i++) {
                cadenasql = "insert into juicio_otros (juicio, correlativo, otros, medida, deligenciado) values ('"
                        + id_juicio + "','"
                        + i.toString() + "','"
                        + modelo_otros[i][0] + "','"
                        + modelo_otros[i][1] + "','"
                        + modelo_otros[i][2] + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Juicio: " + id_juicio + " Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " monto: " + monto.toString() + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " fecha_adminision_demanda: " + fecha_admision + " deudor_notificado: " + deudor_notificado + " fecha_notificacion: " + fecha_noti + " depositario: " + depositario + " tiempo_estamado: " + tiempo_estimado + "',"
                    + "91" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juicio modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String juicio_eliminar(
            Integer usuario_sys,
            Integer id_juicio,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "delete from juicio where juicio=" + id_juicio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUICIO: " + id_juicio + "',"
                    + "92" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juicio eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juicio_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
