package com.lexcom.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Deudor {

    Connection conn;
    Integer usuario_sys;
    public Integer indice;
    public Integer actor;
    public String moneda;
    public String dpi;
    public String nit;
    public Calendar fecha_nacimiento;
    public String nombre;
    public String nacionalidad;
    public String telefono_casa;
    public String telefono_celular;
    public String direccion;
    public Integer zona;
    public String pais;
    public String departamento;
    public String sexo;
    public String estado_civil;
    public Calendar fecha_ingreso;
    public String profesion;
    public String correo_electronico;
    public String lugar_trabajo;
    public String direccion_trabajo;
    public String telefono_trabajo;
    public Double monto_inicial;
    public Integer gestor;
    public Integer sestado;
    public Integer estatus;
    public String no_cuenta;
    public Integer garantia;
    public String cargado;
    public String descripcion;
    public Integer codigo_contactabilidad;
    public Integer caso;
    public String PDF;
    public String INV;
    public String MAYCOM;
    public String NITS;
    public String cosecha;
    public String no_cuenta_otro;
    public String descripcion_adicional;
    public Calendar fecha_recepcion;
    public String anticipo;
    public String antiguedad;
    public Calendar fecha_proximo_pago;
    public Double monto_proximo_pago;
    public Double saldo;
    public String convenio_pactado;
    public Double cuota_convenio;
    public String costas_pagadas;
    public String situacion_caso;
    public String opcion_proximo_pago;
    public Integer sestado_extra;
    public Integer estatus_extra;
    public Integer intencion_pago;
    public Integer razon_deuda;
    public String cuenta_principal_relacion;

    public Deudor(Connection conn, Integer usuario_sys) {
        this.conn = conn;
        this.usuario_sys = usuario_sys;
    }

    public String insertar(
            Integer actor,
            String moneda,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String nacionalidad,
            String telefono_casa,
            String telefono_celular,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            Calendar fecha_ingreso,
            String profesion,
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
            String descripcion,
            Integer codigo_contactabilidad,
            Integer caso,
            String PDF,
            String INV,
            String MAYCOM,
            String NITS,
            String cosecha,
            String no_cuenta_otro,
            String descripcion_adicional,
            Calendar fecha_recepcion,
            String anticipo,
            String antiguedad,
            Double saldo,
            String convenio_pactado,
            Double cuota_convenio,
            String costas_pagadas,
            String situacion_caso,
            String opcion_proximo_pago,
            Integer sestado_extra,
            Integer estatus_extra,
            Integer intencion_pago,
            Integer razon_deuda) {

        String resultado = "";

        Integer dia = fecha_nacimiento.get(Calendar.DATE);
        Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
        Integer ano = fecha_nacimiento.get(Calendar.YEAR);
        String fecha_nac = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = fecha_ingreso.get(Calendar.DATE);
        mes = fecha_ingreso.get(Calendar.MONTH) + 1;
        ano = fecha_ingreso.get(Calendar.YEAR);
        String fecha_ing = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = fecha_recepcion.get(Calendar.DATE);
        mes = fecha_recepcion.get(Calendar.MONTH) + 1;
        ano = fecha_recepcion.get(Calendar.YEAR);
        String fecha_recep = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        String cadenasql = "insert into deudor ("
                + "actor,"
                + "moneda,"
                + "dpi,"
                + "nit,"
                + "fecha_nacimiento,"
                + "nombre,"
                + "nacionalidad,"
                + "telefono_casa,"
                + "telefono_celular,"
                + "direccion,"
                + "zona,"
                + "pais,"
                + "departamento,"
                + "sexo,"
                + "estado_civil,"
                + "fecha_ingreso,"
                + "profesion,"
                + "correo_electronico,"
                + "lugar_trabajo,"
                + "direccion_trabajo,"
                + "telefono_trabajo,"
                + "monto_inicial,"
                + "usuario,"
                + "sestado,"
                + "estatus,"
                + "no_cuenta,"
                + "garantia,"
                + "cargado,"
                + "estado,"
                + "descripcion,"
                + "codigo_contactabilidad,"
                + "caso,"
                + "PDF,"
                + "INV,"
                + "MAYCOM,"
                + "NITS,"
                + "cosecha,"
                + "no_cuenta_otro,"
                + "descripcion_adicional,"
                + "fecha_recepcion,"
                + "anticipo,"
                + "antiguedad,"
                + "fecha_proximo_pago,"
                + "monto_proximo_pago,"
                + "saldo,"
                + "convenio_pactado,"
                + "cuota_convenio,"
                + "costas_pagadas,"
                + "situacion_caso,"
                + "opcion_proximo_pago,"
                + "sestado_extra,"
                + "estatus_extra,"
                + "intencion_pago,"
                + "razon_deuda) values ('"
                + actor + "','"
                + moneda + "','"
                + dpi + "','"
                + nit + "','"
                + fecha_nac + "','"
                + nombre + "','"
                + nacionalidad + "','"
                + telefono_casa + "','"
                + telefono_celular + "','"
                + direccion + "','"
                + zona + "','"
                + pais + "','"
                + departamento + "','"
                + sexo + "','"
                + estado_civil + "','"
                + fecha_ing + "','"
                + profesion + "','"
                + correo_electronico + "','"
                + lugar_trabajo + "','"
                + direccion_trabajo + "','"
                + telefono_trabajo + "','"
                + monto_inicial + "','"
                + gestor + "','"
                + sestado + "','"
                + estatus + "','"
                + no_cuenta + "','"
                + garantia + "','"
                + cargado + "','"
                + estado + "','"
                + descripcion + "','"
                + codigo_contactabilidad.toString() + "','"
                + caso + "','"
                + PDF + "','"
                + INV + "','"
                + MAYCOM + "','"
                + NITS + "','"
                + cosecha + "','"
                + no_cuenta_otro + "','"
                + descripcion_adicional + "','"
                + fecha_recep + "','"
                + anticipo + "','"
                + antiguedad + "',"
                + "CURRENT_DATE()" + ",'"
                + "0.00" + "','"
                + saldo + "','"
                + convenio_pactado + "','"
                + cuota_convenio + "','"
                + costas_pagadas + "','"
                + situacion_caso + "','"
                + opcion_proximo_pago + "','"
                + sestado_extra + "','"
                + estatus_extra + "','"
                + intencion_pago + "','"
                + razon_deuda + "')";

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);

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
            stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Deudor registrado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Deudor creado=> Actor: " + actor + " Moneda: " + moneda + " dpi:" + dpi + " nit:" + nit + " caso:" + caso.toString() + " PDF: " + PDF + " INV: " + INV + " MAYCOM: " + MAYCOM + " NITS: " + NITS + " cosecha: " + cosecha, 1);
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
            Integer deudor,
            Integer actor,
            String moneda,
            String dpi,
            String nit,
            Calendar fecha_nacimiento,
            String nombre,
            String nacionalidad,
            String telefono_casa,
            String telefono_celular,
            String direccion,
            Integer zona,
            String pais,
            String departamento,
            String sexo,
            String estado_civil,
            Calendar fecha_ingreso,
            String profesion,
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
            String descripcion,
            Integer caso,
            String PDF,
            String INV,
            String MAYCOM,
            String NITS,
            String cosecha,
            String no_cuenta_otro,
            String descripcion_adicional,
            Calendar fecha_recepcion,
            String anticipo,
            String antiguedad,
            Double saldo,
            String convenio_pactado,
            Double cuota_convenio,
            String costas_pagadas,
            String situacion_caso,
            String opcion_proximo_pago,
            Integer sestado_extra,
            Integer estatus_extra,
            Integer intencion_pago,
            Integer razon_deuda) {

        String resultado = "";

        Integer dia = fecha_nacimiento.get(Calendar.DATE);
        Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
        Integer ano = fecha_nacimiento.get(Calendar.YEAR);
        String fecha_nac = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = fecha_ingreso.get(Calendar.DATE);
        mes = fecha_ingreso.get(Calendar.MONTH) + 1;
        ano = fecha_ingreso.get(Calendar.YEAR);
        String fecha_ing = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        dia = fecha_recepcion.get(Calendar.DATE);
        mes = fecha_recepcion.get(Calendar.MONTH) + 1;
        ano = fecha_recepcion.get(Calendar.YEAR);
        String fecha_recep = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

        String cadenasql = "update deudor set "
                + "actor='" + actor + "', "
                + "dpi='" + dpi + "', "
                + "nit='" + nit + "', "
                + "fecha_nacimiento='" + fecha_nac + "', "
                + "nombre='" + nombre + "', "
                + "nacionalidad='" + nacionalidad + "', "
                + "telefono_casa='" + telefono_casa + "', "
                + "telefono_celular='" + telefono_celular + "', "
                + "direccion='" + direccion + "', "
                + "zona='" + zona + "', "
                + "pais='" + pais + "', "
                + "departamento='" + departamento + "', "
                + "sexo='" + sexo + "', "
                + "estado_civil='" + estado_civil + "', "
                + "moneda='" + moneda + "', "
                + "fecha_ingreso='" + fecha_ing + "', "
                + "profesion='" + profesion + "', "
                + "correo_electronico='" + correo_electronico + "', "
                + "lugar_trabajo='" + lugar_trabajo + "', "
                + "direccion_trabajo='" + direccion_trabajo + "', "
                + "telefono_trabajo='" + telefono_trabajo + "', "
                + "monto_inicial='" + monto_inicial + "', "
                + "usuario='" + gestor + "', "
                + "sestado='" + sestado + "', "
                + "estatus='" + estatus + "', "
                + "no_cuenta='" + no_cuenta + "', "
                + "garantia='" + garantia + "', "
                + "cargado='" + cargado + "', "
                + "descripcion='" + descripcion + "', "
                + "caso='" + caso.toString() + "', "
                + "PDF='" + PDF + "', "
                + "INV='" + INV + "', "
                + "MAYCOM='" + MAYCOM + "', "
                + "NITS='" + NITS + "', "
                + "cosecha='" + cosecha + "', "
                + "antiguedad='" + antiguedad + "', "
                + "no_cuenta_otro='" + no_cuenta_otro + "', "
                + "costas_pagadas='" + costas_pagadas + "', "
                + "descripcion_adicional='" + descripcion_adicional + "', "
                + "convenio_pactado='" + convenio_pactado + "', "
                + "cuota_convenio='" + cuota_convenio + "', "
                + "fecha_recepcion='" + fecha_recep + "', "
                + "situacion_caso='" + situacion_caso + "', "
                + "opcion_proximo_pago='" + opcion_proximo_pago + "', "
                + "saldo='" + saldo + "', "
                + "anticipo='" + anticipo + "', "
                + "sestado_extra='" + sestado_extra + "', "
                + "estatus_extra='" + estatus_extra + "' "
                + "where deudor=" + deudor.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Deudor modificado en el sistema.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Deudor modificado=> Actor: " + actor + " Moneda: " + moneda + " dpi:" + dpi + " nit:" + nit + " caso:" + caso.toString() + " PDF: " + PDF + " INV: " + INV + " MAYCOM: " + MAYCOM + " NITS: " + NITS + " cosecha: " + cosecha, 1);
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

    public Deudor obtener(Integer seleccion) {
        String cadenasql = "select "
                + "u.actor, "
                + "u.moneda, "
                + "u.dpi, "
                + "u.nit, "
                + "u.fecha_nacimiento, "
                + "u.nombre, "
                + "u.nacionalidad, "
                + "u.telefono_casa, "
                + "u.telefono_celular, "
                + "u.direccion, "
                + "u.zona, "
                + "u.pais, "
                + "u.departamento, "
                + "u.sexo, "
                + "u.estado_civil, "
                + "u.fecha_ingreso, "
                + "u.profesion, "
                + "u.correo_electronico, "
                + "u.lugar_trabajo, "
                + "u.direccion_trabajo, "
                + "u.telefono_trabajo, "
                + "u.monto_inicial, "
                + "u.usuario, "
                + "u.sestado, "
                + "u.estatus, "
                + "u.no_cuenta, "
                + "u.garantia, "
                + "u.cargado, "
                + "u.descripcion, "
                + "u.codigo_contactabilidad, "
                + "u.caso, "
                + "u.PDF, "
                + "u.INV, "
                + "u.MAYCOM, "
                + "u.NITS, "
                + "u.cosecha, "
                + "u.no_cuenta_otro, "
                + "u.descripcion_adicional, "
                + "u.fecha_recepcion, "
                + "u.anticipo, "
                + "u.antiguedad, "
                + "u.fecha_proximo_pago, "
                + "u.monto_proximo_pago, "
                + "u.saldo, "
                + "u.convenio_pactado, "
                + "u.cuota_convenio, "
                + "u.costas_pagadas, "
                + "u.situacion_caso, "
                + "u.opcion_proximo_pago, "
                + "u.sestado_extra, "
                + "u.estatus_extra, "
                + "u.intencion_pago, "
                + "u.razon_deuda, "
                + "if(u.cuenta_principal_relacion=1,'SI','NO') "
                + "from deudor u where u.deudor=" + seleccion.toString();
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.actor = rs.getInt(1);
                    this.moneda = rs.getString(2);
                    this.dpi = rs.getString(3);
                    this.nit = rs.getString(4);
                    this.fecha_nacimiento = this.DateToCalendar(rs.getDate(5));
                    this.nombre = rs.getString(6);
                    this.nacionalidad = rs.getString(7);
                    this.telefono_casa = rs.getString(8);
                    this.telefono_celular = rs.getString(9);
                    this.direccion = rs.getString(10);
                    this.zona = rs.getInt(11);
                    this.pais = rs.getString(12);
                    this.departamento = rs.getString(13);
                    this.sexo = rs.getString(14);
                    this.estado_civil = rs.getString(15);
                    this.fecha_ingreso = this.DateToCalendar(rs.getDate(16));
                    this.profesion = rs.getString(17);
                    this.correo_electronico = rs.getString(18);
                    this.lugar_trabajo = rs.getString(19);
                    this.direccion_trabajo = rs.getString(20);
                    this.telefono_trabajo = rs.getString(21);
                    this.monto_inicial = rs.getDouble(22);
                    this.gestor = rs.getInt(23);
                    this.sestado = rs.getInt(24);
                    this.estatus = rs.getInt(25);
                    this.no_cuenta = rs.getString(26);
                    this.garantia = rs.getInt(27);
                    this.cargado = rs.getString(28);
                    this.descripcion = rs.getString(29);
                    this.codigo_contactabilidad = rs.getInt(30);
                    this.caso = rs.getInt(31);
                    this.PDF = rs.getString(32);
                    this.INV = rs.getString(33);
                    this.MAYCOM = rs.getString(34);
                    this.NITS = rs.getString(35);
                    this.cosecha = rs.getString(36);
                    this.no_cuenta_otro = rs.getString(37);
                    this.descripcion_adicional = rs.getString(38);
                    this.fecha_recepcion = this.DateToCalendar(rs.getDate(39));
                    this.anticipo = rs.getString(40);
                    this.antiguedad = rs.getString(41);
                    this.fecha_proximo_pago = this.DateToCalendar(rs.getDate(42));
                    this.monto_proximo_pago = rs.getDouble(43);
                    this.saldo = rs.getDouble(44);
                    this.convenio_pactado = rs.getString(45);
                    this.cuota_convenio = rs.getDouble(46);
                    this.costas_pagadas = rs.getString(47);
                    this.situacion_caso = rs.getString(48);
                    this.opcion_proximo_pago = rs.getString(49);
                    this.sestado_extra = rs.getInt(50);
                    this.estatus_extra = rs.getInt(51);
                    this.intencion_pago = rs.getInt(52);
                    this.razon_deuda = rs.getInt(53);
                    this.cuenta_principal_relacion = rs.getString(54);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return this;
    }

    public Integer obtener_indice(Integer actor, String codigo, String moneda, String dpi, String nit, String caso) {
        String cadenasql = "select u.deudor from deudor u where "
                + "u.nombre='" + actor + "' "
                + "and u.codigo='" + codigo + "' "
                + "and u.moneda='" + moneda + "' "
                + "and u.dpi='" + dpi + "' "
                + "and u.nit='" + nit + "'";
        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    this.indice = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "3," + ex.toString());
        }

        return this.indice;
    }

    public String obtener_nombre(Integer indice) {
        String cadenasql = "select u.nombre from actor u where u.actor=" + indice;
        String resultado = "";

        try {
            try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(cadenasql)) {
                while (rs.next()) {
                    resultado = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return resultado;
    }

    public String eliminar(Integer seleccion) {
        String resultado = "";
        String cadenasql = "update deudor set "
                + "estado='" + "ELIMINADO" + "' "
                + "where deudor=" + seleccion.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Deudor fue dado de baja.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Deudor baja=> Deudor: " + seleccion, 1);
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

    public String activar(Integer seleccion) {
        String resultado = "";
        String cadenasql = "update deudor set "
                + "estado='" + "VIGENTE" + "' "
                + "where deudor=" + seleccion.toString();

        try {
            conn.setAutoCommit(false);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(cadenasql);
            conn.commit();
            conn.setAutoCommit(true);
            resultado = "1,Deudor fue dado de alta.";

            com.lexcom.driver.Evento drive = new com.lexcom.driver.Evento(conn);
            drive.insertar(this.usuario_sys, "Deudor alta=> Deudor: " + seleccion, 1);
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
    
    public String texto_gestion(Integer gestor) {
        String resultado = "";
        String gestiones = "0";
        String deudores = "0";
        String tipo_usuario = "";
        String gestor_lider = "-";
        String gestor_lider_gestiones = "0";
        String gestor_lider_deudores = "0";
        Integer anio = 0;
        Integer mes = 0;
        Integer dia = 0;
        Integer hora = 0;
        Integer minuto = 0;
        Integer segundo = 0;
        
        try {
            String cadenasql = "SELECT "
                    + "ifnull(count(*),0) AS NUMERO_GESTIONES "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "WHERE "
                    + "dhc.fecha=current_date() and "
                    + "dhc.usuario = " + gestor + " "
                    + "GROUP BY "
                    + "dhc.usuario";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                gestiones = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "SELECT "
                    + "COUNT(*) AS NUMERO_DEUDORES "
                    + "FROM "
                    + "(SELECT "
                    + "dhc.usuario AS GESTOR, "
                    + "dhc.deudor AS DEUDOR, "
                    + "COUNT(*) AS NUMERO_GESTIONES "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "WHERE "
                    + "dhc.fecha=current_date() and "
                    + "dhc.usuario = " + gestor + " "
                    + "GROUP BY "
                    + "dhc.usuario, "
                    + "dhc.deudor) AS TABLA "
                    + "GROUP BY "
                    + "TABLA.GESTOR";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                deudores = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select u.tipo_usuario from usuario u where u.usuario='" + gestor + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                tipo_usuario = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "SELECT "
                    + "u.nombre AS GESTOR, "
                    + "IFNULL(GESTIONES.NUMERO_GESTIONES,0) AS NUMERO_GESTIONES, "
                    + "IFNULL(DEUDORES.NUMERO_DEUDORES,0) AS NUMERO_DEUDORES, "
                    + "IFNULL(PAGOS.NUMERO_PAGOS,0) AS NUMERO_PAGOS "
                    + "FROM usuario u "
                    + "LEFT JOIN ( "
                    + "SELECT  dhc.fecha AS FECHA, "
                    + "d.usuario AS GESTOR, "
                    + "COUNT(*) AS NUMERO_GESTIONES "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "LEFT JOIN deudor d ON (dhc.deudor=d.deudor) "
                    + "GROUP BY "
                    + "dhc.fecha, "
                    + "d.usuario "
                    + "ORDER BY "
                    + "dhc.fecha, "
                    + "d.usuario) AS GESTIONES ON (u.usuario=GESTIONES.GESTOR) "
                    + "LEFT JOIN ( "
                    + "SELECT "
                    + "TABLA.FECHA AS FECHA, "
                    + "TABLA.GESTOR AS GESTOR, "
                    + "COUNT(*) AS NUMERO_DEUDORES "
                    + "FROM "
                    + "(SELECT "
                    + "dhc.fecha AS FECHA, "
                    + "d.usuario AS GESTOR, "
                    + "d.deudor AS DEUDOR, "
                    + "COUNT(*) AS NUMERO_GESTIONES "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "LEFT JOIN deudor d ON (dhc.deudor=d.deudor) "
                    + "GROUP BY "
                    + "dhc.fecha, "
                    + "d.usuario, "
                    + "d.deudor) AS TABLA "
                    + "GROUP BY "
                    + "TABLA.FECHA, "
                    + "TABLA.GESTOR "
                    + "ORDER BY "
                    + "TABLA.FECHA, "
                    + "TABLA.GESTOR) AS DEUDORES ON (u.usuario=DEUDORES.GESTOR AND GESTIONES.FECHA=DEUDORES.FECHA) "
                    + "LEFT JOIN ( "
                    + "SELECT "
                    + "p.fecha_registro AS FECHA, "
                    + "d.usuario AS GESTOR, "
                    + "COUNT(*) AS NUMERO_PAGOS "
                    + "FROM  pago p "
                    + "LEFT JOIN deudor d ON (p.deudor=d.deudor) "
                    + "GROUP BY "
                    + "p.fecha_registro, "
                    + "d.usuario) AS PAGOS ON (u.usuario=PAGOS.GESTOR AND GESTIONES.FECHA=PAGOS.FECHA) "
                    + "WHERE "
                    + "(GESTIONES.FECHA IS NOT NULL) AND "
                    + "(u.tipo_usuario='" + tipo_usuario + "') AND "
                    + "(GESTIONES.FECHA=current_date()) "
                    + "ORDER BY "
                    + "IFNULL(DEUDORES.NUMERO_DEUDORES,0) DESC, "
                    + "IFNULL(GESTIONES.NUMERO_GESTIONES,0) DESC, "
                    + "IFNULL(PAGOS.NUMERO_PAGOS,0)";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                gestor_lider = rs.getString(1);
                gestor_lider_gestiones = rs.getString(2);
                gestor_lider_deudores = rs.getString(3);
            }
            rs.close();
            stmt.close();

            cadenasql = "SELECT "
                    + "max(dhc.fecha) AS FECHA "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "WHERE "
                    + "dhc.usuario = " + gestor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                anio = rs.getDate(1).getYear() + 1900;
                mes = rs.getDate(1).getMonth() + 1;
                dia = rs.getDate(1).getDate();
            }
            rs.close();
            stmt.close();
            
            cadenasql = "SELECT "
                    + "MAX(dhc.hora) AS HORA "
                    + "FROM "
                    + "deudor_historial_cobros dhc "
                    + "WHERE "
                    + "dhc.usuario = " + gestor + " AND "
                    + "dhc.fecha = '" + anio + "-" + mes + "-" + dia + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                hora = rs.getTime(1).getHours();
                minuto = rs.getTime(1).getMinutes();
                segundo = rs.getTime(1).getSeconds();
            }
            rs.close();
            stmt.close();
            
            resultado = resultado + "(NO. GESTIONES: " + gestiones + ", NO. DEUDORES: " + deudores + ", ULTIMA GESTION: " + anio + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo + ") (GESTOR LÍDER: " + gestor_lider + ", NO. GESTIONES: " + gestor_lider_gestiones + ", NO. DEUDORES: " + gestor_lider_deudores + ") ";
            
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }

    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
