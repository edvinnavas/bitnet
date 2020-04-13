package com.lexcom.controlador;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class Deudor implements Serializable {

    private static final long serialVersionUID = 1L;

    public Deudor() {

    }

    public String deudor_insertar(
            Integer usuario_sys,
            Integer actor_d,
            String moneda_d,
            String dpi_d,
            String nit_d,
            Calendar fecha_nacimiento_d,
            String nombre_d,
            String nacionalidad_d,
            String telefono_casa_d,
            String telefono_celular_d,
            String direccion_d,
            Integer zona_d,
            String pais_d,
            String departamento_d,
            String sexo_d,
            String estado_civil_d,
            Calendar fecha_ingreso_d,
            String profesion_d,
            String correo_electronico_d,
            String lugar_trabajo_d,
            String direccion_trabajo_d,
            String telefono_trabajo_d,
            Double monto_inicial_d,
            Integer gestor_d,
            Integer sestado_d,
            Integer estatus_d,
            String no_cuenta_d,
            Integer garantia_d,
            String cargado_d,
            String estado_d,
            String descripcion_d,
            Integer codigo_contactabilidad_d,
            Integer caso_d,
            String PDF_d,
            String INV_d,
            String MAYCOM_d,
            String NITS_d,
            String cosecha_d,
            String no_cuenta_otro_d,
            String descripcion_adicional_d,
            Calendar fecha_recepcion_d,
            String anticipo_d,
            String antiguedad_d,
            Calendar fecha_proximo_pago_d,
            Double monto_proximo_pago_d,
            Double saldo_d,
            String convenio_pactado_d,
            Double cuota_convenio_d,
            String costas_pagadas_d,
            String situacion_caso_d,
            String opcion_proximo_pago_d,
            Integer sestado_extra_d,
            Integer estatus_extra_d,
            Integer intencion_pago_d,
            Integer razon_deuda_d,
            Integer cuenta_principal_relacion_d,
            Integer deudor_cuenta_relacionada_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento_d.get(Calendar.DATE);
            Integer mes = fecha_nacimiento_d.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento_d.get(Calendar.YEAR);
            String fecha_nac_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_ingreso_d.get(Calendar.DATE);
            mes = fecha_ingreso_d.get(Calendar.MONTH) + 1;
            ano = fecha_ingreso_d.get(Calendar.YEAR);
            String fecha_ing_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_recepcion_d.get(Calendar.DATE);
            mes = fecha_recepcion_d.get(Calendar.MONTH) + 1;
            ano = fecha_recepcion_d.get(Calendar.YEAR);
            String fecha_recep_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into deudor ("
                    + "actor, "
                    + "moneda, "
                    + "dpi, "
                    + "nit, "
                    + "fecha_nacimiento, "
                    + "nombre, "
                    + "nacionalidad, "
                    + "telefono_casa, "
                    + "telefono_celular, "
                    + "direccion, "
                    + "zona, "
                    + "pais, "
                    + "departamento, "
                    + "sexo, "
                    + "estado_civil, "
                    + "fecha_ingreso, "
                    + "profesion, "
                    + "correo_electronico, "
                    + "lugar_trabajo, "
                    + "direccion_trabajo, "
                    + "telefono_trabajo, "
                    + "monto_inicial, "
                    + "usuario, "
                    + "sestado, "
                    + "estatus, "
                    + "no_cuenta, "
                    + "garantia, "
                    + "cargado, "
                    + "estado, "
                    + "descripcion, "
                    + "codigo_contactabilidad, "
                    + "caso, "
                    + "PDF, "
                    + "INV, "
                    + "MAYCOM, "
                    + "NITS, "
                    + "cosecha, "
                    + "no_cuenta_otro, "
                    + "descripcion_adicional, "
                    + "fecha_recepcion, "
                    + "anticipo, "
                    + "antiguedad, "
                    + "fecha_proximo_pago, "
                    + "monto_proximo_pago, "
                    + "saldo, "
                    + "convenio_pactado, "
                    + "cuota_convenio, "
                    + "costas_pagadas, "
                    + "situacion_caso, "
                    + "opcion_proximo_pago, "
                    + "sestado_extra, "
                    + "estatus_extra, "
                    + "intencion_pago, "
                    + "razon_deuda, "
                    + "cuenta_principal_relacion, "
                    + "deudor_cuenta_relacionada) values ("
                    + actor_d + ",'"
                    + moneda_d + "','"
                    + dpi_d + "','"
                    + nit_d + "','"
                    + fecha_nac_d + "','"
                    + nombre_d + "','"
                    + nacionalidad_d + "','"
                    + telefono_casa_d + "','"
                    + telefono_celular_d + "','"
                    + direccion_d + "',"
                    + zona_d + ",'"
                    + pais_d + "','"
                    + departamento_d + "','"
                    + sexo_d + "','"
                    + estado_civil_d + "','"
                    + fecha_ing_d + "','"
                    + profesion_d + "','"
                    + correo_electronico_d + "','"
                    + lugar_trabajo_d + "','"
                    + direccion_trabajo_d + "','"
                    + telefono_trabajo_d + "',"
                    + monto_inicial_d + ","
                    + gestor_d + ","
                    + sestado_d + ","
                    + estatus_d + ",'"
                    + no_cuenta_d + "',"
                    + garantia_d + ",'"
                    + cargado_d + "','"
                    + estado_d + "','"
                    + descripcion_d + "',"
                    + codigo_contactabilidad_d + ","
                    + caso_d + ",'"
                    + PDF_d + "','"
                    + INV_d + "','"
                    + MAYCOM_d + "','"
                    + NITS_d + "','"
                    + cosecha_d + "','"
                    + no_cuenta_otro_d + "','"
                    + descripcion_adicional_d + "','"
                    + fecha_recep_d + "','"
                    + anticipo_d + "','"
                    + antiguedad_d + "',"
                    + "CURRENT_DATE()" + ","
                    + "0.00" + ","
                    + saldo_d + ",'"
                    + convenio_pactado_d + "',"
                    + cuota_convenio_d + ",'"
                    + costas_pagadas_d + "','"
                    + situacion_caso_d + "','"
                    + opcion_proximo_pago_d + "',"
                    + sestado_extra_d + ","
                    + estatus_extra_d + ","
                    + intencion_pago_d + ","
                    + razon_deuda_d + ","
                    + cuenta_principal_relacion_d + ","
                    + deudor_cuenta_relacionada_d + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            String max_deudor = "";
            cadenasql = "select max(d.deudor) from deudor d";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                max_deudor = rs.getString(1);
            }
            rs.close();
            stmt.close();

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
                    + "fecha_admision_demanda,"
                    + "deudor_notificado,"
                    + "fecha_notificacion,"
                    + "depositario,"
                    + "tiempo_estimado) values ('"
                    + max_deudor + "','"
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
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "";

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Actor: " + actor_d + " Moneda: " + moneda_d + " dpi:" + dpi_d + " nit:" + nit_d + " caso:" + caso_d.toString() + " PDF: " + PDF_d + " INV: " + INV_d + " MAYCOM: " + MAYCOM_d + " NITS: " + NITS_d + " cosecha: " + cosecha_d + "',"
                    + "41" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String deudor_modificar(
            Integer usuario_sys,
            Integer id_deudor,
            Integer actor_d,
            String moneda_d,
            String dpi_d,
            String nit_d,
            Calendar fecha_nacimiento_d,
            String nombre_d,
            String nacionalidad_d,
            String telefono_casa_d,
            String telefono_celular_d,
            String direccion_d,
            Integer zona_d,
            String pais_d,
            String departamento_d,
            String sexo_d,
            String estado_civil_d,
            Calendar fecha_ingreso_d,
            String profesion_d,
            String correo_electronico_d,
            String lugar_trabajo_d,
            String direccion_trabajo_d,
            String telefono_trabajo_d,
            Double monto_inicial_d,
            Integer gestor_d,
            Integer sestado_d,
            Integer estatus_d,
            String no_cuenta_d,
            Integer garantia_d,
            String cargado_d,
            String estado_d,
            String descripcion_d,
            Integer codigo_contactabilidad_d,
            Integer caso_d,
            String PDF_d,
            String INV_d,
            String MAYCOM_d,
            String NITS_d,
            String cosecha_d,
            String no_cuenta_otro_d,
            String descripcion_adicional_d,
            Calendar fecha_recepcion_d,
            String anticipo_d,
            String antiguedad_d,
            Calendar fecha_proximo_pago_d,
            Double monto_proximo_pago_d,
            Double saldo_d,
            String convenio_pactado_d,
            Double cuota_convenio_d,
            String costas_pagadas_d,
            String situacion_caso_d,
            String opcion_proximo_pago_d,
            Integer sestado_extra_d,
            Integer estatus_extra_d,
            Integer intencion_pago_d,
            Integer razon_deuda_d,
            Integer cuenta_principal_relacion_d,
            Integer deudor_cuenta_relacionada_d,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento_d.get(Calendar.DATE);
            Integer mes = fecha_nacimiento_d.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento_d.get(Calendar.YEAR);
            String fecha_nac_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_ingreso_d.get(Calendar.DATE);
            mes = fecha_ingreso_d.get(Calendar.MONTH) + 1;
            ano = fecha_ingreso_d.get(Calendar.YEAR);
            String fecha_ing_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_recepcion_d.get(Calendar.DATE);
            mes = fecha_recepcion_d.get(Calendar.MONTH) + 1;
            ano = fecha_recepcion_d.get(Calendar.YEAR);
            String fecha_recep_d = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update deudor set "
                    + "actor=" + actor_d + ", "
                    + "moneda='" + moneda_d + "', "
                    + "dpi='" + dpi_d + "', "
                    + "nit='" + nit_d + "', "
                    + "fecha_nacimiento='" + fecha_nac_d + "', "
                    + "nombre='" + nombre_d + "', "
                    + "nacionalidad='" + nacionalidad_d + "', "
                    + "telefono_casa='" + telefono_casa_d + "', "
                    + "telefono_celular='" + telefono_celular_d + "', "
                    + "direccion='" + direccion_d + "', "
                    + "zona=" + zona_d + ", "
                    + "pais='" + pais_d + "', "
                    + "departamento='" + departamento_d + "', "
                    + "sexo='" + sexo_d + "', "
                    + "estado_civil='" + estado_civil_d + "', "
                    + "fecha_ingreso='" + fecha_ing_d + "', "
                    + "profesion='" + profesion_d + "', "
                    + "correo_electronico='" + correo_electronico_d + "', "
                    + "lugar_trabajo='" + lugar_trabajo_d + "', "
                    + "direccion_trabajo='" + direccion_trabajo_d + "', "
                    + "telefono_trabajo='" + telefono_trabajo_d + "', "
                    + "monto_inicial=" + monto_inicial_d + ", "
                    + "usuario=" + gestor_d + ", "
                    + "sestado=" + sestado_d + ", "
                    + "estatus=" + estatus_d + ", "
                    + "no_cuenta='" + no_cuenta_d + "', "
                    + "garantia=" + garantia_d + ", "
                    + "cargado='" + cargado_d + "', "
                    + "estado='" + estado_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "codigo_contactabilidad=" + codigo_contactabilidad_d + ", "
                    + "caso=" + caso_d + ", "
                    + "PDF='" + PDF_d + "', "
                    + "INV='" + INV_d + "', "
                    + "MAYCOM='" + MAYCOM_d + "', "
                    + "NITS='" + NITS_d + "', "
                    + "cosecha='" + cosecha_d + "', "
                    + "no_cuenta_otro='" + no_cuenta_otro_d + "', "
                    + "descripcion_adicional='" + descripcion_adicional_d + "', "
                    + "fecha_recepcion='" + fecha_recep_d + "', "
                    + "anticipo='" + anticipo_d + "', "
                    + "antiguedad='" + antiguedad_d + "', "
                    // + "fecha_proximo_pago='" + fecha_proximo_pago_d + "', "
                    // + "monto_proximo_pago=" + monto_proximo_pago_d + ", "
                    + "saldo=" + saldo_d + ", "
                    + "convenio_pactado='" + convenio_pactado_d + "', "
                    // + "cuota_convenio=" + cuota_convenio_d + ", "
                    + "costas_pagadas='" + costas_pagadas_d + "', "
                    + "situacion_caso='" + situacion_caso_d + "', "
                    // + "opcion_proximo_pago='" + opcion_proximo_pago_d + "', "
                    // + "sestado_extra=" + sestado_extra_d + ", "
                    // + "estatus_extra=" + estatus_extra_d + ", "
                    + "intencion_pago=" + intencion_pago_d + ", "
                    + "razon_deuda=" + razon_deuda_d + ", "
                    + "cuenta_principal_relacion=" + cuenta_principal_relacion_d + ", "
                    + "deudor_cuenta_relacionada=" + deudor_cuenta_relacionada_d + " "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Deudor:" + id_deudor + " Actor: " + actor_d + " Moneda: " + moneda_d + " dpi:" + dpi_d + " nit:" + nit_d + " caso:" + caso_d.toString() + " PDF: " + PDF_d + " INV: " + INV_d + " MAYCOM: " + MAYCOM_d + " NITS: " + NITS_d + " cosecha: " + cosecha_d + "',"
                    + "42" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String deudor_eliminar(
            Integer usuario_sys,
            Integer id_deudor,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "estado='" + "ELIMINADO" + "', "
                    + "cargado='" + "DESCARGADO" + "' "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DEUDOR: " + id_deudor + "',"
                    + "43" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String deudor_activar(
            Integer usuario_sys,
            Integer id_deudor,
            String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "estado='" + "VIGENTE" + "', "
                    + "cargado='" + "DESCARGADO" + "' "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DEUDOR: " + id_deudor + "',"
                    + "44" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor activo en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor:Deudor_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
