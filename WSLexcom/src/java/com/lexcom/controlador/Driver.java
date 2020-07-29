package com.lexcom.controlador;

import com.lexcom.rotacion.Lista;
import com.lexcom.rotacion.Lista3;
import com.lexcom.rotacion.Nodo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    private Connection conn;
    private Lista cartera = new Lista();

    public Connection getConn(String poolConexion) {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(poolConexion);
            conn = ds.getConnection();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(getConn): " + ex.toString());
        }

        return conn;
    }

    public Connection closeConn() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(closeConn): " + ex.toString());
        }

        return conn;
    }

    public String[][] rotacion_automatica(Integer sys, Integer tipo_cartera) {
        String[][] resultado = null;

        try {
            this.generar_rotacion(tipo_cartera);
            Integer numero_filas = this.cartera.getLongitud_lista();
            resultado = new String[numero_filas + 1][18];

            Integer fila = 0;
            resultado[fila][0] = "Deudor";
            resultado[fila][1] = "Caso";
            resultado[fila][2] = "Antiguedad";
            resultado[fila][3] = "Fecha Recepción";
            resultado[fila][4] = "No. Cuenta";
            resultado[fila][5] = "Nombre Deudor";
            resultado[fila][6] = "Monto Inicial";
            resultado[fila][7] = "Garantía";
            resultado[fila][8] = "Codigo Recuperabilidad";
            resultado[fila][9] = "Fecha Proximo Pago";
            resultado[fila][10] = "Cuota";
            resultado[fila][11] = "Gestor";
            resultado[fila][12] = "Tipo Gestor";
            resultado[fila][13] = "Estado-Judicial";
            resultado[fila][14] = "Status-Judicial";
            resultado[fila][15] = "Estado-Extrajudicial";
            resultado[fila][16] = "Status-Extrajudicial";
            resultado[fila][17] = "Actor";

            fila = 1;
            for (Integer i = 0; i < this.cartera.getLongitud_lista(); i++) {
                Nodo nodo = this.cartera.getNode(i);
                resultado[fila][0] = nodo.getDeudor();
                resultado[fila][1] = nodo.getCaso();
                resultado[fila][2] = nodo.getAntiguedad();
                resultado[fila][3] = nodo.getFecha_recepcion();
                resultado[fila][4] = nodo.getNo_cuenta();
                resultado[fila][5] = nodo.getNombre_deudor();
                resultado[fila][6] = nodo.getMonto_inicial().toString();
                resultado[fila][7] = nodo.getGarantia();
                resultado[fila][8] = nodo.getCodigo_resultado();
                resultado[fila][9] = nodo.getFecha_proximo_pago();
                resultado[fila][10] = nodo.getCuota_proximo_pago();
                resultado[fila][11] = nodo.getGestor();
                resultado[fila][12] = nodo.getTipo_gestor().toString();
                resultado[fila][13] = nodo.getEstado_judicial();
                resultado[fila][14] = nodo.getStatus_judicial();
                resultado[fila][15] = nodo.getEstado_extrajudicial();
                resultado[fila][16] = nodo.getStatus_extrajudicial();
                resultado[fila][17] = nodo.getActor();
                fila++;
            }
            this.cartera = null;
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(rotacion_automatica): " + ex.toString());
        }

        return resultado;
    }

    private void generar_rotacion(Integer tipo_cartera) {
        try {
            String cadenasql = "SELECT "
                    + "TABLA.DEUDOR, "
                    + "TABLA.CASO, "
                    + "TABLA.ANTIGUEDAD, "
                    + "TABLA.FECHA_RECEPCION, "
                    + "TABLA.NO_CUENTA, "
                    + "TABLA.NOMBRE_DEUDOR, "
                    + "TABLA.MONTO_INICIAL, "
                    + "TABLA.GARANTIA, "
                    + "TABLA.CODIGO_CONTACTABILIDAD, "
                    + "TABLA.FECHA_PROXIMO_PAGO, "
                    + "TABLA.CUOTA, "
                    + "TABLA.GESTOR, "
                    + "TABLA.TIPO_GESTOR, "
                    + "TABLA.ESTADO_JUDICIAL, "
                    + "TABLA.STATUS_JUDICIAL, "
                    + "TABLA.ESTADO_EXTRAJUDICIAL, "
                    + "TABLA.STATUS_EXTRAJUDICIAL, "
                    + "TABLA.ACTOR ACTOR "
                    + "FROM (select "
                    + "d.deudor DEUDOR, "
                    + "d.caso CASO, "
                    + "d.antiguedad ANTIGUEDAD, "
                    + "d.fecha_recepcion FECHA_RECEPCION, "
                    + "d.no_cuenta NO_CUENTA, "
                    + "d.nombre NOMBRE_DEUDOR, "
                    + "d.monto_inicial MONTO_INICIAL, "
                    + "g.nombre GARANTIA, "
                    + "s.nombre ESTADO_JUDICIAL, "
                    + "e.nombre STATUS_JUDICIAL, "
                    + "se.nombre ESTADO_EXTRAJUDICIAL, "
                    + "ee.nombre STATUS_EXTRAJUDICIAL, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') CUOTA, "
                    + "-1 GESTOR, "
                    + "c.nombre CODIGO_CONTACTABILIDAD, "
                    + "a.nombre ACTOR, "
                    + "-1 TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra se on (d.sestado_extra=se.sestado_extra) "
                    + "left join estatus_extra ee on (d.estatus_extra=ee.estatus_extra) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "((d.sestado_extra,d.estatus_extra) not in (select 3, 18 union all select 3, 8 union all select 3, 20 union all select 3, 6 union all select 7, 11 union all select 7, 12 union all select 7, 16 union all select 10, 19 union all select 4, 9)) and "
                    + "(d.cuota_convenio <= 0.00) and "
                    + "(u.tipo_usuario = " + tipo_cartera + ") "
                    + "union all "
                    + "select "
                    + "d.deudor DEUDOR, "
                    + "d.caso CASO, "
                    + "d.antiguedad ANTIGUEDAD, "
                    + "d.fecha_recepcion FECHA_RECEPCION, "
                    + "d.no_cuenta NO_CUENTA, "
                    + "d.nombre NOMBRE_DEUDOR, "
                    + "d.monto_inicial MONTO_INICIAL, "
                    + "g.nombre GARANTIA, "
                    + "s.nombre ESTADO_JUDICIAL, "
                    + "e.nombre STATUS_JUDICIAL, "
                    + "se.nombre ESTADO_EXTRAJUDICIAL, "
                    + "ee.nombre STATUS_EXTRAJUDICIAL, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') CUOTA, "
                    + "d.usuario GESTOR, "
                    + "c.nombre CODIGO_CONTACTABILIDAD, "
                    + "a.nombre ACTOR, "
                    + "u.tipo_usuario TIPO_GESTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra se on (d.sestado_extra=se.sestado_extra) "
                    + "left join estatus_extra ee on (d.estatus_extra=ee.estatus_extra) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "(d.cargado = 'CARGADO') and "
                    + "(u.tipo_usuario = " + tipo_cartera + ") and "
                    + "(d.deudor not in (select "
                    + "d1.deudor "
                    + "from "
                    + "deudor d1 "
                    + "left join usuario u1 on (d1.usuario=u1.usuario) "
                    + "where "
                    + "(d1.cargado = 'CARGADO') and "
                    + "((d1.sestado_extra,d1.estatus_extra) not in (select 3, 18 union all select 3, 8 union all select 3, 20 union all select 3, 6 union all select 7, 11 union all select 7, 12 union all select 7, 16 union all select 10, 19 union all select 4, 9)) and "
                    + "(d1.cuota_convenio <= 0.00) and "
                    + "(u1.tipo_usuario = " + tipo_cartera + "))) "
                    + ") AS TABLA";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                this.cartera.insertar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18));
            }
            rs.close();
            stmt.close();

            cadenasql = "select "
                    + "d.deudor, "
                    + "d.usuario, "
                    + "0 iteracion "
                    + "from "
                    + "deudor d left join usuario u on (d.usuario=u.usuario) "
                    + "where "
                    + "d.cargado='CARGADO' and "
                    + "d.cuota_convenio <= 0.00 and "
                    + "u.tipo_usuario=" + tipo_cartera;
            Lista3 asignacion_actual = new Lista3();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                asignacion_actual.insertar(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }
            rs.close();
            stmt.close();

            this.cartera.setConn(conn);
            this.cartera.rotacion(tipo_cartera, asignacion_actual);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(generar_rotacion): " + ex.toString());
        }
    }

    public String quitar_simbolos(String cadena) {
        String resultado = "";

        try {
            resultado = cadena.replaceAll("\"", "");
            resultado = resultado.replaceAll("'", "");
            resultado = resultado.replaceAll(";", "");
            resultado = resultado.trim();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(quitar_simbolos): " + ex.toString());
        }

        return resultado;
    }

    public boolean es_fecha_valida(String fecha) {
        boolean resultado = false;

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            resultado = true;
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(es_fecha_valida): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Double valida_double(String monto) {
        Double resultado = 0.00;

        try {
            if (monto == null) {
                monto = "0.00";
            }
            resultado = Double.parseDouble(monto.trim());
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_double): " + ex.toString());
            resultado = 0.00;
        }

        return resultado;
    }

    public Integer valida_integer(String caso) {
        Integer resultado = 0;

        try {
            if (caso == null) {
                caso = "0";
            }

            resultado = Integer.parseInt(caso);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_integer): " + ex.toString());
            resultado = 0;
        }

        return resultado;
    }

    public String valida_fecha_hoy(String fecha) {
        String resultado = "";

        try {
            if (fecha == null) {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                formatoFecha.setLenient(false);
                Date date = new Date();
                fecha = formatoFecha.format(date);
            }

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            resultado = fecha;
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_fecha_hoy): " + ex.toString());
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            Date date = new Date();
            resultado = formatoFecha.format(date);
        }

        return resultado;
    }

    public String valida_fecha_pasado(String fecha) {
        String resultado = "";

        try {
            if (fecha == null) {
                fecha = "1900-01-01";
            }

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            resultado = fecha;
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_fecha_pasado): " + ex.toString());
            resultado = "1900-01-01";
        }

        return resultado;
    }

    public String valida_sexo(String sexo) {
        String resultado = "";

        try {
            if (sexo == null) {
                sexo = "-";
            }

            if (sexo.equals("MASCULINO") || sexo.equals("FEMENINO") || sexo.equals("-")) {
                resultado = sexo;
            } else {
                resultado = "-";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_sexo): " + ex.toString());
            resultado = "1900-01-01";
        }

        return resultado;
    }

    public String valida_estado_civil(String estado_civil) {
        String resultado = "";

        try {
            if (estado_civil == null) {
                estado_civil = "NO";
            }
            if (estado_civil.equals("SOLTERO") || estado_civil.equals("CASADO") || estado_civil.equals("-")) {
                resultado = estado_civil;
            } else {
                resultado = "-";
            }
            if (estado_civil == null) {
                estado_civil = "NO";
            }
            if (estado_civil.equals("SOLTERO") || estado_civil.equals("CASADO") || estado_civil.equals("-")) {
                resultado = estado_civil;
            } else {
                resultado = "-";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_estado_civil): " + ex.toString());
            resultado = "-";
        }

        return resultado;
    }

    public String valida_NOSI(String NOSI) {
        String resultado = "";

        try {
            if (NOSI == null) {
                NOSI = "NO";
            }
            if (NOSI.equals("SI") || NOSI.equals("NO")) {
                resultado = NOSI;
            } else {
                resultado = "NO";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valida_NOSI): " + ex.toString());
            resultado = "NO";
        }

        return resultado;
    }

    public Boolean valido_arraigo(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "DILIGENCIADO"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_arraigo): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Boolean valido_banco(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "DILIGENCIADO"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_banco): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Boolean valido_finca(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PENDIENTE", "EN TRAMITE", "EN REGISTRO", "PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "ANOTADA"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_finca): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Boolean valido_otro(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "DILIGENCIADO"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_otro): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Boolean valido_salario(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "DILIGENCIADO", "NO LABORA"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_salario): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Boolean valido_vehiculo(String medida) {
        Boolean resultado = false;

        try {
            String medidas[] = {"PEDIDO", "NO PEDIDO", "DECRETADO", "NO DECRETADO", "DILIGENCIADO"};

            for (Integer i = 0; i < medidas.length; i++) {
                if (medidas[i].equals(medida)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(valido_vehiculo): " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

    public Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(date);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Driver(DateToCalendar): " + ex.toString());
            cal = Calendar.getInstance();
        }

        return cal;
    }

}
