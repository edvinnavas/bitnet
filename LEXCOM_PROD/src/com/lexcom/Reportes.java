package com.lexcom;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Reportes extends Canvas implements Runnable {

    private static final long serialVersionUID = 1l;
    Image image;
    Boolean opcion = true;
    Connection conn;
    Integer usuario;
    String reporte;

    public Reportes(Connection conn, Integer usuario, String reporte) {
        try {
            this.conn = conn;
            this.usuario = usuario;
            this.reporte = reporte;

            MediaTracker media = new MediaTracker(this);
            image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/ajax-loader2.gif"));
            media.addImage(image, 0);
            System.out.println();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public void setOpcion(Boolean opcion) {
        this.opcion = opcion;
    }

    @Override
    public void run() {
        try {
            Frame frame = new Frame();
            frame.setUndecorated(true);
            frame.resize(100, 100);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = frame.getSize();
            frame.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            frame.add("Center", new Reportes(this.conn, this.usuario, this.reporte));
            frame.show();
            if (this.reporte.equals("ROTACION_AUTOMATICA")) {
                Rotacion_automatica(frame);
            }
            if (this.reporte.equals("CARGA_DEUDORES")) {
                carga_deudores(frame);
            }
            if (this.reporte.equals("ACTUALIZACION_DEUDORES")) {
                actualizacion_deudores(frame);
            }
            if (this.reporte.equals("ROTACION_MANUAL")) {
                Rotacion_manual(frame);
            }
            if (this.reporte.equals("PRESENTAR_DEMANDA")) {
                presentar_demanda(frame);
            }
            if (this.reporte.equals("CARATULA")) {
                caratula(frame);
            }
            if (this.reporte.equals("MEDIDAS_PRECAUTORIAS")) {
                medidas_precautorias(frame);
            }
            if (this.reporte.equals("DELIGENCIAR_MEDIDAS")) {
                deligenciar_medidas(frame);
            }
            if (this.reporte.equals("PAGOS_GESTOR_GARANTIA")) {
                pagos_gestor_garantia(frame);
            }
            if (this.reporte.equals("PAGOS_ANTIGUEDAD_GARANTIA")) {
                pagos_antiguedad_garantia(frame);
            }
            if (this.reporte.equals("ASIGNACION_CARTERA")) {
                asignacion_cartera(frame);
            }
            if (this.reporte.equals("REPORTE_CONSEJO")) {
                reporte_consejo(frame);
            }
            if (this.reporte.equals("REPORTE_PROCURADORES")) {
                reporte_procuradores(frame);
            }
            if (this.reporte.equals("REPORTE_PAGOS")) {
                reporte_pagos(frame);
            }
            if (this.reporte.equals("REPORTE_IRRECUPERABILIDAD")) {
                reporte_irrecuperabilidad(frame);
            }
            if (this.reporte.equals("NOTIFICADOS_ENTIDAD_GARANTIA")) {
                notificados_entidad_garantia(frame);
            }
            if (this.reporte.equals("LIQUIDACION")) {
                liquidacion(frame);
            }
            if (this.reporte.equals("ARCHIVO_CARGA_PAGOS")) {
                archivo_carga_pagos(frame);
            }

            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente.");
            frame.dispose();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void archivo_carga_pagos(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                    + "d.nombre AS NOMBRE, "
                    + "d.convenio_pactado AS CONVENIO_PACTADO, "
                    + "'-' AS FECHA, "
                    + "0.00 AS PAGO_MONTO, "
                    + "ifnull(ju.nombre,'-') AS NOMBRE_JUZGADO, "
                    + "s.nombre AS ESTADO, "
                    + "e.nombre AS STATUS, "
                    + "d.saldo AS SALDO, "
                    + "d.situacion_caso AS SITUACION_CASO "
                    + "from "
                    + "deudor d "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "where "
                    + "(d.cargado='CARGADO') and "
                    + "(a.cooperacion=1) and "
                    + "(d.garantia=7 or d.garantia=13)";
            DExpediente.generar_reporte_pagos(fileName, cadenasql);
        }
    }

    private void pagos_gestor_garantia(Frame frame) {
        FiltroFechaIncialFinal a = new FiltroFechaIncialFinal(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String fecha1 = a.dar_fecha1();
        String fecha2 = a.dar_fecha2();
        String corporacion = a.dar_corporacion();

        if (!fecha1.equals("") && !fecha2.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "select "
                        + "d.cargado as CARGADO, "
                        + "u.nombre as GESTOR, "
                        + "g.nombre as GARANTIA, "
                        + "COUNT(p.monto) as SUMADEPAGOMES, "
                        + "SUM(p.monto) as SUMADEPAGOMES "
                        + "from "
                        + "pago p "
                        + "left join deudor d on (p.deudor=d.deudor) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join garantia g on (d.garantia=g.garantia) "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join cooperacion c on (a.cooperacion=c.cooperacion) "
                        + "where "
                        + "(d.cargado='CARGADO') and "
                        + "(p.fecha_registro between '" + fecha1 + "' and '" + fecha2 + "') and "
                        + "(p.monto > 0.00) and "
                        + "(c.nombre like '%" + corporacion + "%') "
                        + "group by "
                        + "d.usuario, "
                        + "d.garantia "
                        + "order by "
                        + "d.cargado, "
                        + "u.nombre, "
                        + "g.nombre";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void pagos_antiguedad_garantia(Frame frame) {
        FiltroFechaIncialFinal a = new FiltroFechaIncialFinal(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String fecha1 = a.dar_fecha1();
        String fecha2 = a.dar_fecha2();
        String corporacion = a.dar_corporacion();

        if (!fecha1.equals("") && !fecha2.equals("") && !corporacion.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "select "
                        + "d.cargado as CARGADO, "
                        + "d.antiguedad as ANTIGUEDAD, "
                        + "g.nombre as GARANTIA, "
                        + "COUNT(p.monto) as SUMADEPAGOMES, "
                        + "SUM(p.monto) as SUMADEPAGOMES "
                        + "from "
                        + "pago p "
                        + "left join deudor d on (p.deudor=d.deudor) "
                        + "left join garantia g on (d.garantia=g.garantia) "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join cooperacion c on (a.cooperacion=c.cooperacion) "
                        + "where "
                        + "(d.cargado='CARGADO') "
                        + "and (p.fecha_registro between '" + fecha1 + "' and '" + fecha2 + "') "
                        + "and (p.monto > 0.00) "
                        + "and (c.nombre like '%" + corporacion + "%') "
                        + "group by "
                        + "d.antiguedad, "
                        + "d.garantia;";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void asignacion_cartera(Frame frame) {
        FiltroAsignacion a = new FiltroAsignacion(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String corporacion = a.dar_corporacion();

        if (!corporacion.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "select "
                        + "d.caso AS CASO, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "d.antiguedad AS ANTIGUEDAD, "
                        + "dhc.ULTIMA_GESTION AS ULTIMA_GESTION, "
                        + "CAST(d.no_cuenta AS CHAR) AS NO_CREDITO, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "e.nombre AS STATUS, "
                        + "s.nombre AS ESTADO, "
                        + "d.moneda AS MONEDA, "
                        + "d.monto_inicial AS MONTO_INICIAL, "
                        + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                        + "pagos.ULTIMA_FECHA AS ULTIMO_PAGO_REGISTRO, "
                        + "pagos_f.ULTIMA_FECHA AS ULTIMO_PAGO_BANCO, "
                        + "sum(pag.monto) AS PAGO_MES, "
                        + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                        + "g.nombre AS GARANTIA, "
                        + "u.nombre AS GESTOR, "
                        + "cc.nombre as CODIGO_CONTACTABILIDAD, "
                        + "a.nombre as ACTOR "
                        + "from "
                        + "deudor d "
                        + "left join estatus e on (d.estatus=e.estatus) "
                        + "left join sestado s on (d.sestado=s.sestado) "
                        + "left join garantia g on (d.garantia=g.garantia) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join pago pag on (d.deudor=pag.deudor) "
                        + "left join codigo_contactabilidad cc on (d.codigo_contactabilidad=cc.codigo_contactabilidad) "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join cooperacion cor on (a.cooperacion=cor.cooperacion) "
                        + "left join (select "
                        + "p.deudor AS DEUDOR, "
                        + "p.fecha_registro ULTIMA_FECHA "
                        + "from "
                        + "pago p "
                        + "where "
                        + "(p.deudor, p.fecha_registro) in (select "
                        + "pa.deudor, "
                        + "MAX(pa.fecha_registro) "
                        + "from "
                        + "pago pa "
                        + "group by "
                        + "pa.deudor) "
                        + "group by "
                        + "p.deudor) pagos on (d.deudor=pagos.DEUDOR) "
                        + "left join (select "
                        + "p.deudor AS DEUDOR, "
                        + "p.fecha ULTIMA_FECHA "
                        + "from "
                        + "pago p "
                        + "where "
                        + "(p.deudor, p.fecha) in (select "
                        + "pa.deudor, "
                        + "MAX(pa.fecha) "
                        + "from "
                        + "pago pa "
                        + "group by "
                        + "pa.deudor) "
                        + "group by "
                        + "p.deudor) pagos_f on (d.deudor=pagos_f.DEUDOR) "
                        + "left join (select "
                        + "dh.deudor AS DEUDOR, "
                        + "max(dh.fecha) AS ULTIMA_GESTION "
                        + "from "
                        + "deudor_historial_cobros dh "
                        + "group by "
                        + "dh.deudor) dhc on (d.deudor=dhc.DEUDOR) "
                        + "where "
                        + "(d.cargado='CARGADO') and "
                        + "(cor.nombre like '%" + corporacion + "%') "
                        + "group by "
                        + "d.deudor";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void reporte_consejo(Frame frame) {
        FiltroPeriodoIncialFinal a = new FiltroPeriodoIncialFinal(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String periodo1 = a.dar_periodo1();
        String periodo2 = a.dar_periodo2();
        String corporacion = a.dar_corporacion();

        if (!periodo1.equals("") && !periodo2.equals("") && !corporacion.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "SELECT "
                        + "d.caso AS CASO, "
                        + "u.nombre AS GESTOR, "
                        + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                        + "d.cargado AS CARGADO, "
                        + "a.nombre AS ACTOR, "
                        + "s.nombre AS ESTADO, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "j.fecha AS PRESENTACION_DEMANDA, "
                        + "j.tiempo_estimado AS TIEMPO_ESTIMADO, "
                        + "ju.nombre AS JUZGADO, "
                        + "j.no_juicio AS NUMERO_JUICIO, "
                        + "d.no_cuenta AS NUMERO_CREDITO, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "d.moneda AS TIPO_MONEDA, "
                        + "d.monto_inicial AS MONTO, "
                        + "d.saldo AS SALDO, "
                        + "g.nombre AS GARANTIA, "
                        + "d.convenio_pactado AS CONVENIO, "
                        + "CONCAT(IFNULL(M_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(J_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(P_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(JU_ANT.ANTERIORES_ACCIONES,'-'))  AS ANTERIORES_ACCIONES, "
                        + "CONCAT(IFNULL(M_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(J_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(P_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(JU_SIG.ULTIMAS_ACCIONES,'-')) AS ULTIMAS_ACCIONES, "
                        + "d.situacion_caso AS SITUACION_CASO "
                        + "FROM  "
                        + "deudor d "
                        + "LEFT JOIN actor a ON (d.actor=a.actor) "
                        + "LEFT JOIN cooperacion cor ON (a.cooperacion=cor.cooperacion) "
                        + "LEFT JOIN sestado s ON (d.sestado=s.sestado) "
                        + "LEFT JOIN juicio j ON (d.deudor=j.deudor) "
                        + "LEFT JOIN juzgado ju ON (j.juzgado=ju.juzgado) "
                        + "LEFT JOIN garantia g ON (d.garantia=g.garantia) "
                        + "LEFT JOIN usuario u on (d.usuario=u.usuario) "
                        + "LEFT JOIN (SELECT "
                        + "dhc.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhc.descripcion,'\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "deudor_historial_cobros dhc "
                        + "WHERE "
                        + "dhc.fecha between " + periodo1 + " "
                        + "GROUP BY "
                        + "dhc.deudor "
                        + "ORDER BY "
                        + "dhc.fecha) M_ANT ON (d.deudor=M_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhc.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhc.descripcion,'\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "deudor_historial_cobros dhc "
                        + "WHERE "
                        + "dhc.fecha between " + periodo2 + " "
                        + "GROUP BY "
                        + "dhc.deudor "
                        + "ORDER BY "
                        + "dhc.fecha) M_SIG ON (d.deudor=M_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhj.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhj.descripcion,'\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "deudor_historial_juridico dhj "
                        + "WHERE "
                        + "dhj.fecha between " + periodo1 + " "
                        + "GROUP BY "
                        + "dhj.deudor "
                        + "ORDER BY "
                        + "dhj.fecha) J_ANT ON (d.deudor=J_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhj.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhj.descripcion,'\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "deudor_historial_juridico dhj "
                        + "WHERE "
                        + "dhj.fecha between " + periodo2 + " "
                        + "GROUP BY "
                        + "dhj.deudor "
                        + "ORDER BY "
                        + "dhj.fecha) J_SIG ON (d.deudor=J_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "p.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Pago realizado ', p.fecha_registro, ' #', p.monto, '\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "pago p "
                        + "WHERE "
                        + "p.fecha_registro BETWEEN " + periodo1 + " "
                        + "GROUP BY "
                        + "p.deudor "
                        + "ORDER BY "
                        + "p.fecha_registro) P_ANT ON (d.deudor=P_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "p.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Pago realizado ', p.fecha_registro, ' #', p.monto, '\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "pago p "
                        + "WHERE "
                        + "p.fecha_registro BETWEEN " + periodo2 + " "
                        + "GROUP BY "
                        + "p.deudor "
                        + "ORDER BY "
                        + "p.fecha_registro) P_SIG ON (d.deudor=P_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "ju.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Presentación Demanda ', ju.fecha, ' Numero Juicio: ', ju.no_juicio, ' ',ju.procuracion, '\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "juicio ju "
                        + "WHERE "
                        + "ju.fecha BETWEEN " + periodo1 + " "
                        + "GROUP BY "
                        + "ju.deudor "
                        + "ORDER BY "
                        + "ju.fecha) JU_ANT ON (d.deudor=JU_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "ju.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Presentación Demanda ', ju.fecha, ' Numero Juicio: ', ju.no_juicio, ' ',ju.procuracion, '\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "juicio ju "
                        + "WHERE "
                        + "ju.fecha BETWEEN " + periodo2 + " "
                        + "GROUP BY "
                        + "ju.deudor "
                        + "ORDER BY "
                        + "ju.fecha) JU_SIG ON (d.deudor=JU_SIG.COD_DEUDOR) "
                        + "WHERE "
                        + "cor.nombre like '%" + corporacion + "%'";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void reporte_procuradores(Frame frame) {
        FiltroProcurador a = new FiltroProcurador(new javax.swing.JFrame(), true, this.conn, this.usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String procurador = a.dar_procurador();
        String garantia = a.dar_garantia();
        String status = a.dar_status();
        String estado = a.dar_estado();
        String juzgado = a.dar_juzgado();

        if (!procurador.equals("") && !garantia.equals("") && !status.equals("") && !estado.equals("") && !juzgado.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (procurador.equals("TODOS")) {
                    procurador = "";
                }
                if (garantia.equals("TODOS")) {
                    garantia = "";
                }
                if (status.equals("TODOS")) {
                    status = "";
                }
                if (estado.equals("TODOS")) {
                    estado = "";
                }
                if (juzgado.equals("TODOS")) {
                    juzgado = "";
                }

                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "SELECT "
                        + "g.nombre AS GARANTIA, "
                        + "if(d.moneda='QUETZAL',d.monto_inicial,0.00) AS MontoQQ, "
                        + "if(d.moneda='DOLLAR',d.monto_inicial,0.00) AS MontoUSD, "
                        + "d.caso AS CASO, "
                        + "d.nombre AS NOMBRE, "
                        + "s.nombre AS ESTADO, "
                        + "e.nombre AS STATUS, "
                        + "jg.nombre AS JUZGADO, "
                        + "j.no_juicio AS NO_JUICIO, "
                        + "j.notificador AS NOTIFICADOR, "
                        + "u.nombre AS PROCURADOR, "
                        + "j.procuracion AS PROCURACION, "
                        + "d.situacion_caso AS SITUACION_JUICIO, "
                        + "j.razon_notificacion AS RAZON_NOTIFICACION, "
                        + "j.fecha AS PRESENTACION_DEMANDA, "
                        + "j.fecha AS ADMINISION_DEMANDA, "
                        + "j.sumario AS SUMARIO, "
                        + "j.memorial AS MEMORIAL, "
                        + "j.deudor_notificado AS NOTIFICADO, "
                        + "j.fecha_notificacion AS FECHA_NOTIFICADO, "
                        + "a.nombre AS ACTOR "
                        + "FROM "
                        + "deudor d "
                        + "LEFT JOIN juicio j ON (d.deudor = j.deudor) "
                        + "LEFT JOIN usuario u ON (j.procurador = u.usuario) "
                        + "LEFT JOIN garantia g ON (d.garantia = g.garantia) "
                        + "LEFT JOIN estatus e ON (d.estatus = e.estatus) "
                        + "LEFT JOIN sestado s ON (d.sestado = s.sestado) "
                        + "LEFT JOIN juzgado jg ON (j.juzgado = jg.juzgado) "
                        + "LEFT JOIN actor a ON (d.actor = a.actor) "
                        + "WHERE "
                        + "d.cargado='CARGADO' and "
                        + "u.nombre_completo like '%" + procurador + "%' and "
                        + "g.nombre like '%" + garantia + "%' and "
                        + "e.nombre like '%" + status + "%' and "
                        + "s.nombre like '%" + estado + "%' and "
                        + "jg.nombre like '%" + juzgado + "%'";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void reporte_pagos(Frame frame) {
        FiltroFechaIncialFinal a = new FiltroFechaIncialFinal(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        String fecha1 = a.dar_fecha1();
        String fecha2 = a.dar_fecha2();
        String corporacion = a.dar_corporacion();

        if (!fecha1.equals("") && !fecha2.equals("") && !corporacion.equals("")) {
            FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
            fDialog.setVisible(true);
            String archivo = fDialog.getFile();
            if (archivo != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                String fileName = fDialog.getDirectory() + archivo;
                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
                String cadenasql = "select "
                        + "d.caso AS CASO, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "d.antiguedad AS ANTIGUEDAD, "
                        + "dhc.ULTIMA_GESTION AS ULTIMA_GESTION, "
                        + "CAST(d.no_cuenta AS CHAR) AS NO_CREDITO, "
                        + "CAST(d.no_cuenta_otro AS CHAR) AS OTRO_NO_CREDITO, "
                        + "d.saldo AS SALDO_AL_DIA, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "e.nombre AS STATUS, "
                        + "s.nombre AS ESTADO, "
                        + "d.moneda AS MONEDA, "
                        + "d.monto_inicial AS MONTO_INICIAL, "
                        + "if(d.cuota_convenio>0.00, d.cuota_convenio, 0.00) AS CUOTA, "
                        + "pag.fecha_registro AS PAGO_REGISTRO, "
                        + "ban.nombre AS BANCO, "
                        + "pag.no_boleta AS NO_BOLETA, "
                        + "pag.fecha AS FECHA_BOLETA, "
                        + "pag.monto AS PAGO_MES, "
                        + "if(d.cuota_convenio>0.00, d.fecha_proximo_pago, '1900-01-01') AS FECHA_PROXIMO_PAGO, "
                        + "g.nombre AS GARANTIA, "
                        + "u.nombre AS GESTOR, "
                        + "cc.nombre as CODIGO_CONTACTABILIDAD, "
                        + "a.nombre AS ACTOR "
                        + "from "
                        + "deudor d "
                        + "left join estatus e on (d.estatus=e.estatus) "
                        + "left join sestado s on (d.sestado=s.sestado) "
                        + "left join garantia g on (d.garantia=g.garantia) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join pago pag on (d.deudor=pag.deudor) "
                        + "left join banco ban on (pag.banco=ban.banco) "
                        + "left join codigo_contactabilidad cc on (d.codigo_contactabilidad=cc.codigo_contactabilidad) "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join cooperacion cor on (a.cooperacion=cor.cooperacion) "
                        + "left join (select "
                        + "dh.deudor AS DEUDOR, "
                        + "max(dh.fecha) AS ULTIMA_GESTION "
                        + "from "
                        + "deudor_historial_cobros dh "
                        + "group by "
                        + "dh.deudor) dhc on (d.deudor=dhc.DEUDOR) "
                        + "where "
                        + "(d.cargado='CARGADO') AND "
                        + "(pag.fecha_registro between '" + fecha1 + "' and '" + fecha2 + "') AND "
                        + "(pag.monto > 0.00) AND "
                        + "(cor.nombre like '%" + corporacion + "%')";

//                java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//                Integer filas = resultado.size();
//                Integer columnas = resultado.get(1).getItem().size();
//                String[][] reporte_excel = new String[resultado.size()][columnas];
//                for (Integer i = 0; i < resultado.size(); i++) {
//                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                    }
//                }
//
//                DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
                DExpediente.generar_reporte(fileName, cadenasql);
            }
        }
    }

    private void reporte_irrecuperabilidad(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "g.nombre AS GESTOR, "
                    + "g.nombre AS PROCURADOR, "
                    + "d.caso AS CASO, "
                    + "ga.nombre AS GARANTIA, "
                    + "se.nombre AS ESTADO, "
                    + "es.nombre AS STATUS, "
                    + "j.procuracion AS PROCURACION, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "d.moneda AS MONEDA, "
                    + "j.fecha AS PRESENTACION_DEMANDA, "
                    + "ju.nombre AS JUZGADO, "
                    + "j.no_juicio AS NO_JUICIO, "
                    + "ifnull(sum(pa.monto),0.00) AS SUMA_PAGOS_REALIZADOS, "
                    + "arraigo.ARRAIGO AS ARRAIGO, "
                    + "arraigo.A1 AS A1, "
                    + "banco.BANCOS AS BANCOS, "
                    + "banco.B1 AS B1, "
                    + "finca.FINCA AS FINCA, "
                    + "finca.LETRA AS LETRA, "
                    + "otros.OTROS AS OTROS, "
                    + "otros.O1 AS O1, "
                    + "salario.SALARIO AS SALARIO, "
                    + "salario.S1 AS S1, "
                    + "vehiculo.VEHICULO AS VEHICULO, "
                    + "vehiculo.V1 AS V1 "
                    + "from "
                    + "deudor d "
                    + "left join usuario g on (d.usuario=g.usuario) "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "left join usuario p on (j.procurador=p.usuario) "
                    + "left join garantia ga on (d.garantia=ga.garantia) "
                    + "left join sestado se on (d.sestado=se.sestado) "
                    + "left join estatus es on (d.estatus=es.estatus) "
                    + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                    + "left join pago pa on (d.deudor=pa.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(ja.arraigo),'-') AS ARRAIGO, "
                    + "ifnull(group_concat(ja.deligenciado),'-') AS A1 "
                    + "from "
                    + "juicio j "
                    + "left join juicio_arraigo ja on (j.juicio=ja.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) arraigo on (j.juicio=arraigo.juicio and j.deudor=arraigo.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(jb.bancos),'-') AS BANCOS, "
                    + "ifnull(group_concat(jb.deligenciado),'-') AS B1 "
                    + "from "
                    + "juicio j "
                    + "left join juicio_banco jb on (j.juicio=jb.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) banco on (j.juicio=banco.juicio and j.deudor=banco.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(jf.finca),'-') AS FINCA, "
                    + "ifnull(group_concat(jf.letra),'-') AS LETRA "
                    + "from "
                    + "juicio j "
                    + "left join juicio_finca jf on (j.juicio=jf.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) finca on (j.juicio=finca.juicio and j.deudor=finca.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(jo.otros),'-') AS OTROS, "
                    + "ifnull(group_concat(jo.deligenciado),'-') AS O1 "
                    + "from "
                    + "juicio j "
                    + "left join juicio_otros jo on (j.juicio=jo.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) otros on (j.juicio=otros.juicio and j.deudor=otros.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(js.empresa),'-') AS SALARIO, "
                    + "ifnull(group_concat(js.deligenciado),'-') AS S1 "
                    + "from "
                    + "juicio j "
                    + "left join juicio_salario js on (j.juicio=js.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) salario on (j.juicio=salario.juicio and j.deudor=salario.deudor) "
                    + "left join (select "
                    + "j.juicio AS JUICIO, "
                    + "j.deudor AS DEUDOR, "
                    + "ifnull(group_concat(jv.vehiculo),'-') AS VEHICULO, "
                    + "ifnull(group_concat(jv.deligenciado),'-') AS V1 "
                    + "from "
                    + "juicio j "
                    + "left join juicio_vehiculo jv on (j.juicio=jv.juicio) "
                    + "group by "
                    + "j.juicio, j.deudor) vehiculo on (j.juicio=vehiculo.juicio and j.deudor=vehiculo.deudor) "
                    + "where "
                    + "(d.cargado ='CARGADO') "
                    + "group by "
                    + "d.deudor";

//            java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//            Integer filas = resultado.size();
//            Integer columnas = resultado.get(1).getItem().size();
//            String[][] reporte_excel = new String[resultado.size()][columnas];
//            for (Integer i = 0; i < resultado.size(); i++) {
//                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                    reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                }
//            }
//
//            DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void notificados_entidad_garantia(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "SELECT "
                    + "d.moneda AS MONEDA, "
                    + "a.nombre AS ACTOR, "
                    + "g.nombre AS GARANTIA, "
                    + "COUNT(*) AS NOTIFICADOS "
                    + "FROM "
                    + "deudor d "
                    + "LEFT JOIN actor a ON (d.actor = a.actor) "
                    + "LEFT JOIN garantia g ON (d.garantia = g.garantia) "
                    + "LEFT JOIN juicio j ON (d.deudor=j.deudor) "
                    + "WHERE "
                    + "d.cargado='CARGADO' and "
                    + "j.deudor_notificado='SI' "
                    + "GROUP BY "
                    + "d.moneda, "
                    + "d.actor, "
                    + "d.garantia "
                    + "ORDER BY "
                    + "d.moneda, "
                    + "d.actor, "
                    + "d.garantia";

//            java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//            Integer filas = resultado.size();
//            Integer columnas = resultado.get(1).getItem().size();
//            String[][] reporte_excel = new String[resultado.size()][columnas];
//            for (Integer i = 0; i < resultado.size(); i++) {
//                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                    reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                }
//            }
//
//            DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void liquidacion(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.caso AS CASO, "
                    + "jz.nombre AS JUZGADO, "
                    + "ju.no_juicio AS NUMERO_JUICIO, "
                    + "ju.notificador AS NOTIFICADOR, "
                    + "ju.fecha AS FECHA_JUICIO, "
                    + "ju.fecha_admision_demanda AS FECHA_ADMINISION_DEMANDA, "
                    + "ju.fecha_notificacion AS FECHA_NOTIFICACION, "
                    + "ju.memorial AS MEMORIAL, "
                    + "d.anticipo AS ANTICIPO, "
                    + "d.cargado AS CARGADO, "
                    + "a.nombre AS ACTOR "
                    + "from "
                    + "deudor d "
                    + "left join juicio ju on (d.deudor=ju.deudor) "
                    + "left join juzgado jz on (ju.juzgado=jz.juzgado) "
                    + "left join actor a on (d.actor=a.actor)";

//            java.util.List<com.lexcom.ws.StringArray> resultado = reporte(cadenasql);
//            Integer filas = resultado.size();
//            Integer columnas = resultado.get(1).getItem().size();
//            String[][] reporte_excel = new String[resultado.size()][columnas];
//            for (Integer i = 0; i < resultado.size(); i++) {
//                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
//                    reporte_excel[i][j] = resultado.get(i).getItem().get(j);
//                }
//            }
//
//            DExpediente.generar_reporte_w(fileName, reporte_excel, filas, columnas);
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void presentar_demanda(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.garantia AS GARANTIA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "e.estatus AS STATUS, "
                    + "d.situacion_caso AS SITUACION_CASO, "
                    + "j.procuracion AS PROCURACION "
                    + "from "
                    + "juicio j "
                    + "left join deudor d on (j.deudor=d.deudor) "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "where "
                    + "e.nombre='DEMANDAR' and "
                    + "d.cargado='CARGADO'";
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void caratula(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.garantia AS GARANTIA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "e.estatus AS STATUS, "
                    + "d.situacion_caso AS SITUACION_CASO, "
                    + "j.procuracion AS PROCURACION, "
                    + "j.juzgado AS JUZGADO, "
                    + "j.no_juicio AS NO_JUICIO, "
                    + "j.fecha AS FECHA_JUICIO, "
                    + "j.notificador AS NOTIFICADOR, "
                    + "j.procurador AS PROCURADOR, "
                    + "'-' AS ARRAIGO, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS BANCOS, "
                    + "'aaaa-mm-dd' AS FECHA "
                    + "from "
                    + "juicio j "
                    + "left join deudor d on (j.deudor=d.deudor) "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "where "
                    + "e.nombre='PRIMERA RESOLUCION' and "
                    + "d.cargado='CARGADO'";
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void medidas_precautorias(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.garantia AS GARANTIA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "e.estatus AS STATUS, "
                    + "d.situacion_caso AS SITUACION_CASO, "
                    + "j.procuracion AS PROCURACION, "
                    + "j.juzgado AS JUZGADO, "
                    + "j.no_juicio AS NO_JUICIO, "
                    + "j.notificador AS NOTIFICADOR, "
                    + "j.procurador AS PROCURADOR, "
                    + "'-' AS ARRAIGO, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS BANCOS, "
                    + "'aaaa-mm-dd' AS FECHA "
                    + "from "
                    + "juicio j "
                    + "left join deudor d on (j.deudor=d.deudor) "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "where "
                    + "e.nombre='MEDIDAS' and "
                    + "d.cargado='CARGADO'";
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void deligenciar_medidas(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "j.procurador AS PROCURADOR , "
                    + "j.juzgado AS JUZGADO, "
                    + "j.no_juicio AS NO_JUICIO, "
                    + "j.notificador AS NOTIFICADOR, "
                    + "d.sestado AS ESTADO, "
                    + "e.estatus AS STATUS, "
                    + "j.procuracion AS PROCURACION, "
                    + "d.situacion_caso AS SITUACION_CASO, "
                    + "j.razon_notificacion RAZON_NOTIFICACION, "
                    + "ifnull(sum(p.monto),0.00) AS PAGOS, "
                    + "'-' AS ARRAIGO, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS BANCOS, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS FINCA, "
                    + "'-' AS LETRA, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS TRAMITE, "
                    + "'-' AS SALARIO, "
                    + "'-' AS EMPRESA, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS VEHICULO, "
                    + "'-' AS MEDIDA, "
                    + "'aaaa-mm-dd' AS FECHA, "
                    + "'-' AS OTROS, "
                    + "'-' AS MEDIDA, "
                    + "'aaaa-mm-dd' AS FECHA "
                    + "from "
                    + "juicio j "
                    + "left join deudor d on (j.deudor=d.deudor) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join pago p on (d.deudor=p.deudor) "
                    + "where "
                    + "e.nombre='DILIGENCIAR' and "
                    + "d.cargado='CARGADO' "
                    + "group by "
                    + "d.deudor;";
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void Rotacion_manual(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            String fileName = fDialog.getDirectory() + archivo;
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String cadenasql = "select "
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
                    + "a.nombre AS ACTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor)"
                    + "where "
                    + "(d.cargado='CARGADO') AND "
                    + "(g.garantia=7 or g.garantia=13) "
                    + "order by "
                    + "d.monto_inicial desc";
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }

    private void actualizacion_deudores(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String name = fDialog.getFile();
        if (name != null) {
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String fileName = fDialog.getDirectory() + name + ".xls";
            String cadenasql = "SELECT "
                    + "d.deudor, "
                    + "d.actor, "
                    + "d.moneda, "
                    + "d.dpi, "
                    + "d.nit, "
                    + "d.fecha_nacimiento, "
                    + "d.nombre, "
                    + "d.nacionalidad, "
                    + "d.telefono_casa, "
                    + "d.telefono_celular, "
                    + "d.direccion, "
                    + "d.zona, "
                    + "d.pais, "
                    + "d.departamento, "
                    + "d.sexo, "
                    + "d.estado_civil, "
                    + "d.fecha_ingreso, "
                    + "d.profesion, "
                    + "d.correo_electronico, "
                    + "d.lugar_trabajo, "
                    + "d.direccion_trabajo, "
                    + "d.telefono_trabajo, "
                    + "d.monto_inicial, "
                    + "d.usuario, "
                    + "d.sestado, "
                    + "d.estatus, "
                    + "d.no_cuenta, "
                    + "d.garantia, "
                    + "d.cargado, "
                    + "d.estado, "
                    + "d.descripcion, "
                    + "d.codigo_contactabilidad, "
                    + "d.caso, "
                    + "d.PDF, "
                    + "d.INV, "
                    + "d.MAYCOM, "
                    + "d.NITS, "
                    + "d.cosecha, "
                    + "d.no_cuenta_otro, "
                    + "d.descripcion_adicional, "
                    + "d.fecha_recepcion, "
                    + "d.anticipo, "
                    + "d.antiguedad, "
                    + "d.fecha_proximo_pago, "
                    + "d.monto_proximo_pago, "
                    + "d.saldo, "
                    + "d.convenio_pactado, "
                    + "d.cuota_convenio, "
                    + "d.costas_pagadas, "
                    + "d.situacion_caso, "
                    + "d.opcion_proximo_pago, "
                    + "j.procuracion "
                    + "FROM "
                    + "deudor d "
                    + "left join juicio j on (d.deudor=j.deudor)";
            DExpediente.generar_reporte2(fileName, cadenasql);
        }
    }

    private void carga_deudores(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String name = fDialog.getFile();
        if (name != null) {
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            String fileName = fDialog.getDirectory() + name + ".xls";
            String cadenasql = "SELECT "
                    + "d.caso, "
                    + "d.sestado, "
                    + "d.estatus, "
                    + "d.antiguedad, "
                    + "d.fecha_recepcion, "
                    + "d.situacion_caso, "
                    + "d.cargado, "
                    + "d.no_cuenta, "
                    + "d.actor, "
                    + "d.garantia, "
                    + "d.saldo, "
                    + "d.moneda, "
                    + "d.monto_inicial, "
                    + "d.nombre, "
                    + "d.anticipo, "
                    + "d.cosecha, "
                    + "d.usuario "
                    + "FROM "
                    + "deudor d "
                    + "where "
                    + "d.deudor='-1000'";
            DExpediente.generar_reporte1(fileName, cadenasql);
        }
    }

    private void Rotacion_automatica(Frame frame) {
        FileDialog fDialog = new FileDialog(frame, "Abrir ...", FileDialog.LOAD);
        fDialog.setVisible(true);
        String archivo = fDialog.getFile();
        if (archivo != null) {
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(conn, usuario);
            System.out.println("CREDIFACIL");
            DExpediente.generar_reporte_rotacion_credifacil();
            System.out.println("ORO");
            DExpediente.generar_reporte_rotacion_oro();
            System.out.println("PLATA");
            DExpediente.generar_reporte_rotacion_plata();
            System.out.println("BRONCE");
            DExpediente.generar_reporte_rotacion_bronce();

            String cadenasql = "SELECT t.deudor AS DEUDOR, t.caso AS CASO,t.antiguedad AS ANTIGUEDAD,t.fecha_recepcion AS FECHA_RECEPCION,t.no_cuenta AS NO_CUENTA,t.nombre_deudor AS NOMBRE_DEUDOR,t.monto_inicial AS MONTO_INICIAL,t.garantia AS GARANTIA,t.estado AS ESTADO,t.fecha_proximo_pago AS FECHA_PROXIMO_PAGO,t.cuota_proximo_pago AS CUOTA,t.gestor AS GESTOR,t.codigo_contactabilidad AS CODIGO_CONTACTABILIDAD,t.actor AS ACTOR "
                    + "FROM temp_reporte_rotacion_credifacil t UNION ALL "
                    + "SELECT t.deudor AS DEUDOR, t.caso AS CASO,t.antiguedad AS ANTIGUEDAD,t.fecha_recepcion AS FECHA_RECEPCION,t.no_cuenta AS NO_CUENTA,t.nombre_deudor AS NOMBRE_DEUDOR,t.monto_inicial AS MONTO_INICIAL,t.garantia AS GARANTIA,t.estado AS ESTADO,t.fecha_proximo_pago AS FECHA_PROXIMO_PAGO,t.cuota_proximo_pago AS CUOTA,t.gestor AS GESTOR,t.codigo_contactabilidad AS CODIGO_CONTACTABILIDAD,t.actor AS ACTOR "
                    + "FROM temp_reporte_rotacion_oro t UNION ALL "
                    + "SELECT t.deudor AS DEUDOR, t.caso AS CASO,t.antiguedad AS ANTIGUEDAD,t.fecha_recepcion AS FECHA_RECEPCION,t.no_cuenta AS NO_CUENTA,t.nombre_deudor AS NOMBRE_DEUDOR,t.monto_inicial AS MONTO_INICIAL,t.garantia AS GARANTIA,t.estado AS ESTADO,t.fecha_proximo_pago AS FECHA_PROXIMO_PAGO,t.cuota_proximo_pago AS CUOTA,t.gestor AS GESTOR,t.codigo_contactabilidad AS CODIGO_CONTACTABILIDAD,t.actor AS ACTOR "
                    + "FROM temp_reporte_rotacion_plata t UNION ALL "
                    + "SELECT t.deudor AS DEUDOR, t.caso AS CASO,t.antiguedad AS ANTIGUEDAD,t.fecha_recepcion AS FECHA_RECEPCION,t.no_cuenta AS NO_CUENTA,t.nombre_deudor AS NOMBRE_DEUDOR,t.monto_inicial AS MONTO_INICIAL,t.garantia AS GARANTIA,t.estado AS ESTADO,t.fecha_proximo_pago AS FECHA_PROXIMO_PAGO,t.cuota_proximo_pago AS CUOTA,t.gestor AS GESTOR,t.codigo_contactabilidad AS CODIGO_CONTACTABILIDAD,t.actor AS ACTOR "
                    + "FROM temp_reporte_rotacion_bronce t";
            String fileName = fDialog.getDirectory() + archivo;
            DExpediente.generar_reporte(fileName, cadenasql);
        }
    }
}
