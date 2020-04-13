package com.lexcom.controlador;

import com.lexcom.entidad.Deudores_Actualizacion_Masiva;
import com.lexcom.entidad.Deudores_Carga_Masiva;
import com.lexcom.entidad.Deudores_Demanda_Caratula;
import com.lexcom.entidad.Deudores_Demanda_Deligenciar_Medidas;
import com.lexcom.entidad.Deudores_Demanda_Medidas_Precautorias;
import com.lexcom.entidad.Deudores_Demanda_Presentar_Demanda;
import com.lexcom.entidad.Deudores_Pagos_Masivos;
import com.lexcom.entidad.Deudores_Rotacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Carga_Masiva implements Serializable {

    private static final long serialVersionUID = 1L;

    public Carga_Masiva() {

    }

    public String carga_masiva_deudoroes(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell actor = row.getCell(0);
                XSSFCell caso = row.getCell(1);
                XSSFCell estado_judicial = row.getCell(2);
                XSSFCell estatus_judicial = row.getCell(3);
                XSSFCell estado_extrajudicial = row.getCell(4);
                XSSFCell estatus_extrajudicial = row.getCell(5);
                XSSFCell intencion_pago = row.getCell(6);
                XSSFCell garantia = row.getCell(7);
                XSSFCell gestor = row.getCell(8);
                XSSFCell saldo = row.getCell(9);
                XSSFCell monto_inicial = row.getCell(10);
                XSSFCell nombre_deudor = row.getCell(11);
                XSSFCell no_cuenta = row.getCell(12);
                XSSFCell antiguedad = row.getCell(13);
                XSSFCell situacion_caso = row.getCell(14);
                XSSFCell fecha_recepcion = row.getCell(15);
                XSSFCell cargado = row.getCell(16);
                XSSFCell moneda = row.getCell(17);
                XSSFCell anticipo = row.getCell(18);
                XSSFCell cosecha = row.getCell(19);
                XSSFCell no_juicio = row.getCell(20);
                XSSFCell juzgado = row.getCell(21);
                XSSFCell procurador = row.getCell(22);
                XSSFCell notificador = row.getCell(23);
                XSSFCell monto_demanda = row.getCell(24);
                XSSFCell otro_no_cuenta = row.getCell(25);
                XSSFCell fecha_nacimiento = row.getCell(26);
                XSSFCell telefono_casa = row.getCell(27);
                XSSFCell telefono_celular = row.getCell(28);
                XSSFCell telefono_trabajo = row.getCell(29);
                XSSFCell correo_electronico = row.getCell(30);
                XSSFCell lugar_trabajo = row.getCell(31);
                XSSFCell contacto_trabajo = row.getCell(32);
                XSSFCell direccion = row.getCell(33);
                XSSFCell dpi = row.getCell(34);
                XSSFCell nit = row.getCell(35);
                XSSFCell notas = row.getCell(36);
                XSSFCell procuracion = row.getCell(37);
                XSSFCell cuenta_principal_relacion = row.getCell(38);
                XSSFCell deudor_cuenta_relacionada = row.getCell(39);

                Integer db_actor = 0;
                try {
                    db_actor = Integer.parseInt(formatoInteger.format(Double.parseDouble(actor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Actor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_caso = 0;
                try {
                    db_caso = Integer.parseInt(formatoInteger.format(Double.parseDouble(caso.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Caso en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estado_judicial = 0;
                try {
                    db_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estado Judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estatus_judicial = 0;
                try {
                    db_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estatus Judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estado_extrajudicial = 0;
                try {
                    db_estado_extrajudicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estado_extrajudicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estado Extrajudicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estatus_extrajudicial = 0;
                try {
                    db_estatus_extrajudicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estatus_extrajudicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estatus Extrajudicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_intencion_pago = "";
                if (intencion_pago != null) {
                    db_intencion_pago = intencion_pago.toString().trim();
                    if (db_intencion_pago.equals("")) {
                        db_intencion_pago = "-";
                    }
                } else {
                    db_intencion_pago = "-";
                }

                Integer db_garantia = 0;
                try {
                    db_garantia = Integer.parseInt(formatoInteger.format(Double.parseDouble(garantia.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Garantía en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_gestor = 0;
                try {
                    db_gestor = Integer.parseInt(formatoInteger.format(Double.parseDouble(gestor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Gestor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Double db_saldo = 0.00;
                try {
                    db_saldo = Double.parseDouble(formatoDouble.format(Double.parseDouble(saldo.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Saldo en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Double db_monto_inicial = 0.00;
                try {
                    db_monto_inicial = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_inicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto Inicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_nombre_deudor = "";
                if (nombre_deudor != null) {
                    db_nombre_deudor = nombre_deudor.toString().trim();
                    if (db_nombre_deudor.equals("")) {
                        db_nombre_deudor = "SIN NOMBRE";
                    }
                } else {
                    db_nombre_deudor = "SIN NOMBRE";
                }

                String db_no_cuenta = "";
                if (no_cuenta != null) {
                    db_no_cuenta = no_cuenta.toString().trim();
                    if (db_no_cuenta.equals("")) {
                        db_no_cuenta = "-";
                    }
                } else {
                    db_no_cuenta = "-";
                }

                String db_antiguedad = "";
                if (antiguedad != null) {
                    db_antiguedad = antiguedad.toString().trim();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select a.antiguedad from antiguedad a where a.estado='VIGENTE' and trim(a.nombre)='" + db_antiguedad + "'");
                    Integer antiguedad_l = 0;
                    while (rs.next()) {
                        antiguedad_l = rs.getInt(1);
                    }
                    rs.close();
                    stmt.close();
                    if (antiguedad_l == 0) {
                        throw new Exception("Error al calcular el campo Antigüedad en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Antigüedad en la linea: " + linea_error);
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                Date db_fecha_recepcion = new Date(1900, 0, 1);
                if (fecha_recepcion != null) {
                    try {
                        db_fecha_recepcion = fecha_recepcion.getDateCellValue();
                        formatoDate.format(db_fecha_recepcion);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha Recepción en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha Recepción en la linea: " + linea_error);
                }

                String db_cargado = "";
                if (cargado != null) {
                    db_cargado = cargado.toString().trim();
                    if (!db_cargado.equals("CARGADO") && !db_cargado.equals("DESCARGADO")) {
                        throw new Exception("Error al calcular el campo Cargado en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cargado en la linea: " + linea_error);
                }

                String db_moneda = "";
                if (moneda != null) {
                    db_moneda = moneda.toString().trim();
                    if (!db_moneda.equals("QUETZAL") && !db_moneda.equals("DOLLAR")) {
                        throw new Exception("Error al calcular el campo Moneda en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Moneda en la linea: " + linea_error);
                }

                String db_anticipo = "";
                if (anticipo != null) {
                    db_anticipo = anticipo.toString().trim();
                    if (!db_anticipo.equals("NO") && !db_anticipo.equals("SI") && !db_anticipo.equals("COBRAR") && !db_anticipo.equals("REPRE")) {
                        throw new Exception("Error al calcular el campo Anticipo en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Anticipo en la linea: " + linea_error);
                }

                String db_cosecha = "";
                if (cosecha != null) {
                    db_cosecha = cosecha.toString().trim();
                    if (!db_cosecha.equals("0 antes 31 dic 2008")
                            && !db_cosecha.equals("2009 1er t") && !db_cosecha.equals("2009 2do t") && !db_cosecha.equals("2009 3er t") && !db_cosecha.equals("2009 4to t")
                            && !db_cosecha.equals("2010 1er t") && !db_cosecha.equals("2010 2do t") && !db_cosecha.equals("2010 3er t") && !db_cosecha.equals("2010 4to t")
                            && !db_cosecha.equals("2011 1er t") && !db_cosecha.equals("2011 2do t") && !db_cosecha.equals("2011 3er t") && !db_cosecha.equals("2011 4to t")
                            && !db_cosecha.equals("2012 1er t") && !db_cosecha.equals("2012 2do t") && !db_cosecha.equals("2012 3er t") && !db_cosecha.equals("2012 4to t")
                            && !db_cosecha.equals("2013 1er t") && !db_cosecha.equals("2013 2do t") && !db_cosecha.equals("2013 3er t") && !db_cosecha.equals("2013 4to t")
                            && !db_cosecha.equals("2014 1er t") && !db_cosecha.equals("2014 2do t") && !db_cosecha.equals("2014 3er t") && !db_cosecha.equals("2014 4to t")
                            && !db_cosecha.equals("2015 1er t") && !db_cosecha.equals("2015 2do t") && !db_cosecha.equals("2015 3er t") && !db_cosecha.equals("2015 4to t")
                            && !db_cosecha.equals("2016 1er t") && !db_cosecha.equals("2016 2do t") && !db_cosecha.equals("2016 3er t") && !db_cosecha.equals("2016 4to t")
                            && !db_cosecha.equals("2017 1er t") && !db_cosecha.equals("2017 2do t") && !db_cosecha.equals("2017 3er t") && !db_cosecha.equals("2017 4to t")
                            && !db_cosecha.equals("2018 1er t") && !db_cosecha.equals("2018 2do t") && !db_cosecha.equals("2018 3er t") && !db_cosecha.equals("2018 4to t")
                            && !db_cosecha.equals("2019 1er t") && !db_cosecha.equals("2019 2do t") && !db_cosecha.equals("2019 3er t") && !db_cosecha.equals("2019 4to t")
                            && !db_cosecha.equals("2020 1er t") && !db_cosecha.equals("2020 2do t") && !db_cosecha.equals("2020 3er t") && !db_cosecha.equals("2020 4to t")
                            && !db_cosecha.equals("2021 1er t") && !db_cosecha.equals("2021 2do t") && !db_cosecha.equals("2021 3er t") && !db_cosecha.equals("2021 4to t")
                            && !db_cosecha.equals("2022 1er t") && !db_cosecha.equals("2022 2do t") && !db_cosecha.equals("2022 3er t") && !db_cosecha.equals("2022 4to t")
                            && !db_cosecha.equals("2023 1er t") && !db_cosecha.equals("2023 2do t") && !db_cosecha.equals("2023 3er t") && !db_cosecha.equals("2023 4to t")
                            && !db_cosecha.equals("2024 1er t") && !db_cosecha.equals("2024 2do t") && !db_cosecha.equals("2024 3er t") && !db_cosecha.equals("2024 4to t")
                            && !db_cosecha.equals("2025 1er t") && !db_cosecha.equals("2025 2do t") && !db_cosecha.equals("2025 3er t") && !db_cosecha.equals("2025 4to t")
                            && !db_cosecha.equals("2026 1er t") && !db_cosecha.equals("2026 2do t") && !db_cosecha.equals("2026 3er t") && !db_cosecha.equals("2026 4to t")
                            && !db_cosecha.equals("2027 1er t") && !db_cosecha.equals("2027 2do t") && !db_cosecha.equals("2027 3er t") && !db_cosecha.equals("2027 4to t")
                            && !db_cosecha.equals("2028 1er t") && !db_cosecha.equals("2028 2do t") && !db_cosecha.equals("2028 3er t") && !db_cosecha.equals("2028 4to t")
                            && !db_cosecha.equals("2029 1er t") && !db_cosecha.equals("2029 2do t") && !db_cosecha.equals("2029 3er t") && !db_cosecha.equals("2029 4to t")
                            && !db_cosecha.equals("2030 1er t") && !db_cosecha.equals("2030 2do t") && !db_cosecha.equals("2030 3er t") && !db_cosecha.equals("2030 4to t")) {
                        throw new Exception("Error al calcular el campo Cosecha en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cosecha en la linea: " + linea_error);
                }

                String db_no_juicio = "";
                if (no_juicio != null) {
                    db_no_juicio = no_juicio.toString().trim();
                    if (db_no_juicio.equals("")) {
                        db_no_juicio = "-";
                    }
                } else {
                    db_no_juicio = "-";
                }

                Integer db_juzgado = 0;
                try {
                    db_juzgado = Integer.parseInt(formatoInteger.format(Double.parseDouble(juzgado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Juzgado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_procurador = 0;
                try {
                    db_procurador = Integer.parseInt(formatoInteger.format(Double.parseDouble(procurador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Procurador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_notificador = 0;
                try {
                    db_notificador = Integer.parseInt(formatoInteger.format(Double.parseDouble(notificador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Notificador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Double db_monto_demanda = 0.00;
                try {
                    db_monto_demanda = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_demanda.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto Inicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_otro_no_cuenta = "";
                if (otro_no_cuenta != null) {
                    db_otro_no_cuenta = otro_no_cuenta.toString().trim();
                    if (db_otro_no_cuenta.equals("")) {
                        db_otro_no_cuenta = "-";
                    }
                } else {
                    db_otro_no_cuenta = "-";
                }

                Date db_fecha_nacimiento = new Date();
                if (fecha_nacimiento != null) {
                    try {
                        db_fecha_nacimiento = fecha_nacimiento.getDateCellValue();
                        formatoDate.format(db_fecha_nacimiento);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha nacimiento en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha nacimiento en la linea: " + linea_error);
                }

                String db_telefono_casa = "";
                if (telefono_casa != null) {
                    db_telefono_casa = telefono_casa.toString().trim();
                    if (db_telefono_casa.equals("")) {
                        db_telefono_casa = "-";
                    }
                } else {
                    db_telefono_casa = "-";
                }

                String db_telefono_celular = "";
                if (telefono_celular != null) {
                    db_telefono_celular = telefono_celular.toString().trim();
                    if (db_telefono_celular.equals("")) {
                        db_telefono_celular = "-";
                    }
                } else {
                    db_telefono_celular = "-";
                }

                String db_telefono_trabajo = "";
                if (telefono_trabajo != null) {
                    db_telefono_trabajo = telefono_trabajo.toString().trim();
                    if (db_telefono_trabajo.equals("")) {
                        db_telefono_trabajo = "-";
                    }
                } else {
                    db_telefono_trabajo = "-";
                }

                String db_correo_electronico = "";
                if (correo_electronico != null) {
                    db_correo_electronico = correo_electronico.toString().trim();
                    if (db_correo_electronico.equals("")) {
                        db_correo_electronico = "-";
                    }
                } else {
                    db_correo_electronico = "-";
                }

                String db_lugar_trabajo = "";
                if (lugar_trabajo != null) {
                    db_lugar_trabajo = lugar_trabajo.toString().trim();
                    if (db_lugar_trabajo.equals("")) {
                        db_lugar_trabajo = "-";
                    }
                } else {
                    db_lugar_trabajo = "-";
                }

                String db_contacto_trabajo = "";
                if (contacto_trabajo != null) {
                    db_contacto_trabajo = contacto_trabajo.toString().trim();
                    if (db_contacto_trabajo.equals("")) {
                        db_contacto_trabajo = "-";
                    }
                } else {
                    db_contacto_trabajo = "-";
                }

                String db_direccion = "";
                if (direccion != null) {
                    db_direccion = direccion.toString().trim();
                    if (db_direccion.equals("")) {
                        db_direccion = "-";
                    }
                } else {
                    db_direccion = "-";
                }

                String db_dpi = "";
                if (dpi != null) {
                    db_dpi = dpi.toString().trim();
                    if (db_dpi.equals("")) {
                        db_dpi = "-";
                    }
                } else {
                    db_dpi = "-";
                }

                String db_nit = "";
                if (nit != null) {
                    db_nit = nit.toString().trim();
                    if (db_nit.equals("")) {
                        db_nit = "-";
                    }
                } else {
                    db_nit = "-";
                }

                String db_notas = "";
                if (notas != null) {
                    db_notas = notas.toString().trim();
                    if (db_notas.equals("")) {
                        db_notas = "-";
                    }
                } else {
                    db_notas = "-";
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Integer db_cuenta_principal_relacion = 0;
                if (cuenta_principal_relacion != null) {
                    String cuenta_principal_relacion_str = cuenta_principal_relacion.toString().trim();
                    if (!cuenta_principal_relacion_str.equals("SI") && !cuenta_principal_relacion_str.equals("NO")) {
                        throw new Exception("Error al calcular el campo Cuenta principal rel. en la linea: " + linea_error);
                    } else {
                        if(cuenta_principal_relacion_str.equals("SI")) {
                            db_cuenta_principal_relacion = 1;
                        } else {
                            db_cuenta_principal_relacion = 0;
                        }
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cuenta principal rel. en la linea: " + linea_error);
                }
                
                Integer db_deudor_cuenta_relacionada = 0;
                try {
                    db_deudor_cuenta_relacionada = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor_cuenta_relacionada.toString().trim())));
                    if (db_cuenta_principal_relacion == 1) {
                        db_deudor_cuenta_relacionada = 0;
                    } else {
                        if (db_deudor_cuenta_relacionada > 0) {
                            // Obtener datos de la cuenta relacionada.
                            Integer deudor_relacionado = 0;
                            Integer corporacion_relacionada = 0;
                            Integer cuenta_principal_relacionada = 0;
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("select d.deudor, a.cooperacion, d.cuenta_principal_relacion from deudor d left join actor a on (d.actor=a.actor) where d.deudor=" + db_deudor_cuenta_relacionada);
                            while (rs.next()) {
                                deudor_relacionado = rs.getInt(1);
                                corporacion_relacionada = rs.getInt(2);
                                cuenta_principal_relacionada = rs.getInt(3);
                            }
                            rs.close();
                            stmt.close();
                            // Obtener corporacion del deudor.
                            Integer corporacion_cuenta = 0;
                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("select a.cooperacion from actor a where a.actor=" + db_actor);
                            while (rs.next()) {
                                corporacion_cuenta = rs.getInt(1);
                            }
                            rs.close();
                            stmt.close();

                            if(deudor_relacionado == 0) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Cuenta deudor no existe.");
                            }
                            if (corporacion_relacionada != corporacion_cuenta) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Corporación distinta.");
                            } 
                            if(cuenta_principal_relacionada != 1) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Cuenta relacionada no es principal.");
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Deudores_Carga_Masiva deu_car_mas = new Deudores_Carga_Masiva(
                        db_actor,
                        db_caso,
                        db_estado_judicial,
                        db_estatus_judicial,
                        db_estado_extrajudicial,
                        db_estatus_extrajudicial,
                        db_intencion_pago,
                        db_garantia,
                        db_gestor,
                        db_saldo,
                        db_monto_inicial,
                        db_nombre_deudor,
                        db_no_cuenta,
                        db_antiguedad,
                        db_situacion_caso,
                        db_fecha_recepcion,
                        db_cargado,
                        db_moneda,
                        db_anticipo,
                        db_cosecha,
                        db_no_juicio,
                        db_juzgado,
                        db_procurador,
                        db_notificador,
                        db_monto_demanda,
                        db_otro_no_cuenta,
                        db_fecha_nacimiento,
                        db_telefono_casa,
                        db_telefono_celular,
                        db_telefono_trabajo,
                        db_correo_electronico,
                        db_lugar_trabajo,
                        db_contacto_trabajo,
                        db_direccion,
                        db_dpi,
                        db_nit,
                        db_notas,
                        db_procuracion,
                        db_cuenta_principal_relacion,
                        db_deudor_cuenta_relacionada);

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
                        + "cuenta_principal_relacion, "
                        + "deudor_cuenta_relacionada) values ("
                        + deu_car_mas.getActor() + ",'"
                        + driver.quitar_simbolos(deu_car_mas.getMoneda()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getDpi()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNit()) + "','"
                        + formatoDate1.format(deu_car_mas.getFecha_nacimiento()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNombre_deudor()) + "','"
                        + "GUATEMALTECO(A)" + "','"
                        + driver.quitar_simbolos(deu_car_mas.getTelefono_casa()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getTelefono_celular()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getDireccion()) + "',"
                        + "0" + ",'"
                        + "Guatemala" + "','"
                        + "Guatemala" + "','"
                        + "-" + "','"
                        + "-" + "',"
                        + "CURRENT_DATE()" + ",'"
                        + "-" + "','"
                        + driver.quitar_simbolos(deu_car_mas.getCorreo_electronico()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getLugar_trabajo()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getContacto_trabajo()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getTelefono_trabajo()) + "',"
                        + deu_car_mas.getMonto_inicial() + ","
                        + deu_car_mas.getGestor() + ","
                        + deu_car_mas.getEstado_judicial() + ","
                        + deu_car_mas.getEstatus_judicial() + ",'"
                        + driver.quitar_simbolos(deu_car_mas.getNo_cuenta()) + "',"
                        + deu_car_mas.getGarantia() + ",'"
                        + driver.quitar_simbolos(deu_car_mas.getCargado()) + "','"
                        + "VIGENTE" + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNotas()) + "',"
                        + "1" + ","
                        + deu_car_mas.getCaso() + ",'"
                        + "NO" + "','"
                        + "NO" + "','"
                        + "NO" + "','"
                        + "NO" + "','"
                        + driver.quitar_simbolos(deu_car_mas.getCosecha()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getOtro_no_cuenta()) + "','"
                        + "-" + "','"
                        + formatoDate1.format(deu_car_mas.getFecha_recepcion()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getAnticipo()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getAntiguedad()) + "','"
                        + "1900-01-01" + "',"
                        + "0.00" + ","
                        + deu_car_mas.getSaldo() + ",'"
                        + "-" + "',"
                        + "0.00" + ",'"
                        + "-" + "','"
                        + deu_car_mas.getSituacion_caso() + "','"
                        + "NO" + "',"
                        + deu_car_mas.getEstado_extrajudicial() + ","
                        + deu_car_mas.getEstatus_extrajudicial() + ","
                        + "4" + ","
                        + deu_car_mas.getCuenta_principal_relacion() + ","
                        + deu_car_mas.getDeudor_cuenta_relacionada() + ")";

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
                        + "deudor, "
                        + "juzgado, "
                        + "fecha, "
                        + "no_juicio, "
                        + "monto, "
                        + "descripcion, "
                        + "procurador, "
                        + "razon_notificacion, "
                        + "notificador, "
                        + "abogado_deudor, "
                        + "sumario, "
                        + "memorial, "
                        + "procuracion, "
                        + "fecha_admision_demanda, "
                        + "deudor_notificado, "
                        + "fecha_notificacion, "
                        + "depositario, "
                        + "tiempo_estimado) values ('"
                        + max_deudor + "',"
                        + deu_car_mas.getJuzgado() + ",'"
                        + "1900/01/01" + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNo_juicio()) + "',"
                        + deu_car_mas.getMonto_demanda() + ",'"
                        + "-" + "',"
                        + deu_car_mas.getProcurador() + ",'"
                        + "-" + "',"
                        + deu_car_mas.getNotificador() + ",'"
                        + "-" + "','"
                        + "-" + "','"
                        + "1900/01/01" + "','"
                        + deu_car_mas.getProcuracion() + "','"
                        + "1900/01/01" + "','"
                        + "NO" + "','"
                        + "1900/01/01" + "','"
                        + "-" + "','"
                        + "0" + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva de deudores." + "',"
                    + "11" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga de deudores exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deudoroes): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deudoroes - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String actualizacion_masiva_deudores(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell actor = row.getCell(1);
                XSSFCell moneda = row.getCell(2);
                XSSFCell dpi = row.getCell(3);
                XSSFCell nit = row.getCell(4);
                XSSFCell fecha_nacimiento = row.getCell(5);
                XSSFCell nombre_deudor = row.getCell(6);
                XSSFCell nacionalidad = row.getCell(7);
                XSSFCell telefono_casa = row.getCell(8);
                XSSFCell telefono_celular = row.getCell(9);
                XSSFCell direccion = row.getCell(10);
                XSSFCell zona = row.getCell(11);
                XSSFCell pais = row.getCell(12);
                XSSFCell departamento = row.getCell(13);
                XSSFCell sexo = row.getCell(14);
                XSSFCell estado_civil = row.getCell(15);
                XSSFCell fecha_ingreso = row.getCell(16);
                XSSFCell profesion = row.getCell(17);
                XSSFCell correo_electronico = row.getCell(18);
                XSSFCell lugar_trabajo = row.getCell(19);
                XSSFCell contacto_trabajo = row.getCell(20);
                XSSFCell telefono_trabajo = row.getCell(21);
                XSSFCell monto_inicial = row.getCell(22);
                XSSFCell gestor = row.getCell(23);
                XSSFCell estado_judicial = row.getCell(24);
                XSSFCell estatus_judicial = row.getCell(25);
                XSSFCell estado_extrajudicial = row.getCell(26);
                XSSFCell estatus_extrajudicial = row.getCell(27);
                XSSFCell intencion_pago = row.getCell(28);
                XSSFCell no_cuenta = row.getCell(29);
                XSSFCell garantia = row.getCell(30);
                XSSFCell cargado = row.getCell(31);
                XSSFCell estado_ae = row.getCell(32);
                XSSFCell notas = row.getCell(33);
                XSSFCell codigo_resultado = row.getCell(34);
                XSSFCell caso = row.getCell(35);
                XSSFCell pdf = row.getCell(36);
                XSSFCell inv = row.getCell(37);
                XSSFCell maycom = row.getCell(38);
                XSSFCell nits = row.getCell(39);
                XSSFCell cosecha = row.getCell(40);
                XSSFCell otro_no_cuenta = row.getCell(41);
                XSSFCell descripcion_adicional = row.getCell(42);
                XSSFCell fecha_recepcion = row.getCell(43);
                XSSFCell anticipo = row.getCell(44);
                XSSFCell antiguedad = row.getCell(45);
                XSSFCell fecha_proximo_pago = row.getCell(46);
                XSSFCell monto_proximo_pago = row.getCell(47);
                XSSFCell saldo = row.getCell(48);
                XSSFCell convenio_pactado = row.getCell(49);
                XSSFCell cuota_convenio = row.getCell(50);
                XSSFCell costas_pagadas = row.getCell(51);
                XSSFCell situacion_caso = row.getCell(52);
                XSSFCell opcion_proximo_pago = row.getCell(53);
                XSSFCell procuracion = row.getCell(54);
                XSSFCell fecha_demanda = row.getCell(55);
                XSSFCell procurador = row.getCell(56);
                XSSFCell juzgado = row.getCell(57);
                XSSFCell no_juicio = row.getCell(58);
                XSSFCell notificador = row.getCell(59);
                XSSFCell monto_demanda = row.getCell(60);
                XSSFCell cuenta_principal_relacion = row.getCell(61);
                XSSFCell deudor_cuenta_relacionada = row.getCell(62);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_actor = 0;
                try {
                    db_actor = Integer.parseInt(formatoInteger.format(Double.parseDouble(actor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Actor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_moneda = "";
                if (moneda != null) {
                    db_moneda = moneda.toString().trim();
                    if (!db_moneda.equals("QUETZAL") && !db_moneda.equals("DOLLAR")) {
                        throw new Exception("Error al calcular el campo Moneda en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Moneda en la linea: " + linea_error);
                }

                String db_dpi = "";
                if (dpi != null) {
                    db_dpi = dpi.toString().trim();
                    if (db_dpi.equals("")) {
                        db_dpi = "-";
                    }
                } else {
                    db_dpi = "-";
                }

                String db_nit = "";
                if (nit != null) {
                    db_nit = nit.toString().trim();
                    if (db_nit.equals("")) {
                        db_nit = "-";
                    }
                } else {
                    db_nit = "-";
                }

                Date db_fecha_nacimiento = new Date();
                if (fecha_nacimiento != null) {
                    try {
                        db_fecha_nacimiento = fecha_nacimiento.getDateCellValue();
                        formatoDate.format(db_fecha_nacimiento);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha nacimiento en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha nacimiento en la linea: " + linea_error);
                }

                String db_nombre_deudor = "";
                if (nombre_deudor != null) {
                    db_nombre_deudor = nombre_deudor.toString().trim();
                    if (db_nombre_deudor.equals("")) {
                        db_nombre_deudor = "SIN NOMBRE";
                    }
                } else {
                    db_nombre_deudor = "SIN NOMBRE";
                }

                String db_nacionalidad = "";
                if (nacionalidad != null) {
                    db_nacionalidad = nacionalidad.toString().trim();
                    if (db_nacionalidad.equals("")) {
                        db_nacionalidad = "-";
                    }
                } else {
                    db_nacionalidad = "-";
                }

                String db_telefono_casa = "";
                if (telefono_casa != null) {
                    db_telefono_casa = telefono_casa.toString().trim();
                    if (db_telefono_casa.equals("")) {
                        db_telefono_casa = "-";
                    }
                } else {
                    db_telefono_casa = "-";
                }

                String db_telefono_celular = "";
                if (telefono_celular != null) {
                    db_telefono_celular = telefono_celular.toString().trim();
                    if (db_telefono_celular.equals("")) {
                        db_telefono_celular = "-";
                    }
                } else {
                    db_telefono_celular = "-";
                }

                String db_direccion = "";
                if (direccion != null) {
                    db_direccion = direccion.toString().trim();
                    if (db_direccion.equals("")) {
                        db_direccion = "-";
                    }
                } else {
                    db_direccion = "-";
                }

                Integer db_zona = 0;
                try {
                    db_zona = Integer.parseInt(formatoInteger.format(Double.parseDouble(zona.toString().trim())));
                } catch (Exception ex) {
                    db_zona = 0;
                }

                String db_pais = "";
                if (pais != null) {
                    db_pais = pais.toString().trim();
                    if (db_pais.equals("")) {
                        db_pais = "Guatemala";
                    }
                } else {
                    db_pais = "Guatemala";
                }

                String db_departamento = "";
                if (departamento != null) {
                    db_departamento = departamento.toString().trim();
                    if (db_departamento.equals("")) {
                        db_departamento = "Guatemala";
                    }
                } else {
                    db_departamento = "Guatemala";
                }

                String db_sexo = "";
                if (sexo != null) {
                    db_sexo = sexo.toString().trim();
                    if (!db_sexo.equals("MASCULINO") && !db_sexo.equals("FEMENINO") && !db_sexo.equals("-")) {
                        throw new Exception("Error al calcular el campo Sexo en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Sexo en la linea: " + linea_error);
                }

                String db_estado_civil = "";
                if (estado_civil != null) {
                    db_estado_civil = estado_civil.toString().trim();
                    if (!db_estado_civil.equals("SOLTERO") && !db_estado_civil.equals("CASADO") && !db_estado_civil.equals("-")) {
                        throw new Exception("Error al calcular el campo Estado civil en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Estado civil en la linea: " + linea_error);
                }

                Date db_fecha_ingreso = new Date();
                if (fecha_ingreso != null) {
                    try {
                        db_fecha_ingreso = fecha_ingreso.getDateCellValue();
                        formatoDate.format(db_fecha_ingreso);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha ingreso en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha ingreso en la linea: " + linea_error);
                }

                String db_profesion = "";
                if (profesion != null) {
                    db_profesion = profesion.toString().trim();
                    if (db_profesion.equals("")) {
                        db_profesion = "-";
                    }
                } else {
                    db_profesion = "-";
                }

                String db_correo_electronico = "";
                if (correo_electronico != null) {
                    db_correo_electronico = correo_electronico.toString().trim();
                    if (db_correo_electronico.equals("")) {
                        db_correo_electronico = "-";
                    }
                } else {
                    db_correo_electronico = "-";
                }

                String db_lugar_trabajo = "";
                if (lugar_trabajo != null) {
                    db_lugar_trabajo = lugar_trabajo.toString().trim();
                    if (db_lugar_trabajo.equals("")) {
                        db_lugar_trabajo = "-";
                    }
                } else {
                    db_lugar_trabajo = "-";
                }

                String db_contacto_trabajo = "";
                if (contacto_trabajo != null) {
                    db_contacto_trabajo = contacto_trabajo.toString().trim();
                    if (db_contacto_trabajo.equals("")) {
                        db_contacto_trabajo = "-";
                    }
                } else {
                    db_contacto_trabajo = "-";
                }

                String db_telefono_trabajo = "";
                if (telefono_trabajo != null) {
                    db_telefono_trabajo = telefono_trabajo.toString().trim();
                    if (db_telefono_trabajo.equals("")) {
                        db_telefono_trabajo = "-";
                    }
                } else {
                    db_telefono_trabajo = "-";
                }

                Double db_monto_inicial = 0.00;
                try {
                    db_monto_inicial = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_inicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto Inicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_gestor = 0;
                try {
                    db_gestor = Integer.parseInt(formatoInteger.format(Double.parseDouble(gestor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Gestor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estado_judicial = 0;
                try {
                    db_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estado Judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estatus_judicial = 0;
                try {
                    db_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estatus Judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estado_extrajudicial = 0;
                try {
                    db_estado_extrajudicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estado_extrajudicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estado Extrajudicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_estatus_extrajudicial = 0;
                try {
                    db_estatus_extrajudicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(estatus_extrajudicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Estatus Extrajudicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_intencion_pago = 0;
                try {
                    db_intencion_pago = Integer.parseInt(formatoInteger.format(Double.parseDouble(intencion_pago.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Intención pago en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_no_cuenta = "";
                if (no_cuenta != null) {
                    db_no_cuenta = no_cuenta.toString().trim();
                    if (db_no_cuenta.equals("")) {
                        db_no_cuenta = "-";
                    }
                } else {
                    db_no_cuenta = "-";
                }

                Integer db_garantia = 0;
                try {
                    db_garantia = Integer.parseInt(formatoInteger.format(Double.parseDouble(garantia.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Garantía en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_cargado = "";
                if (cargado != null) {
                    db_cargado = cargado.toString().trim();
                    if (!db_cargado.equals("CARGADO") && !db_cargado.equals("DESCARGADO")) {
                        throw new Exception("Error al calcular el campo Cargado en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cargado en la linea: " + linea_error);
                }

                String db_estado_ae = "";
                if (estado_ae != null) {
                    db_estado_ae = estado_ae.toString().trim();
                    if (!db_estado_ae.equals("VIGENTE") && !db_estado_ae.equals("ELIMINADO")) {
                        throw new Exception("Error al calcular el campo Estado(A/E) en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Estado(A/E) en la linea: " + linea_error);
                }

                String db_notas = "";
                if (notas != null) {
                    db_notas = notas.toString().trim();
                    if (db_notas.equals("")) {
                        db_notas = "-";
                    }
                } else {
                    db_notas = "-";
                }

                Integer db_codigo_resultado = 0;
                try {
                    db_codigo_resultado = Integer.parseInt(formatoInteger.format(Double.parseDouble(codigo_resultado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Código resultado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_caso = 0;
                try {
                    db_caso = Integer.parseInt(formatoInteger.format(Double.parseDouble(caso.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Caso en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_pdf = "";
                if (pdf != null) {
                    db_pdf = pdf.toString().trim();
                    if (!db_pdf.equals("SI") && !db_pdf.equals("NO")) {
                        throw new Exception("Error al calcular el campo PDF en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo PDF en la linea: " + linea_error);
                }

                String db_inv = "";
                if (inv != null) {
                    db_inv = inv.toString().trim();
                    if (!db_inv.equals("SI") && !db_inv.equals("NO")) {
                        throw new Exception("Error al calcular el campo INV en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo INV en la linea: " + linea_error);
                }

                String db_maycom = "";
                if (maycom != null) {
                    db_maycom = maycom.toString().trim();
                    if (!db_maycom.equals("SI") && !db_maycom.equals("NO")) {
                        throw new Exception("Error al calcular el campo MAYCOM en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo MAYCOM en la linea: " + linea_error);
                }

                String db_nits = "";
                if (nits != null) {
                    db_nits = nits.toString().trim();
                    if (!db_nits.equals("SI") && !db_nits.equals("NO")) {
                        throw new Exception("Error al calcular el campo NITS en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo NITS en la linea: " + linea_error);
                }

                String db_cosecha = "";
                if (cosecha != null) {
                    db_cosecha = cosecha.toString().trim();
                    if (!db_cosecha.equals("0 antes 31 dic 2008")
                            && !db_cosecha.equals("2009 1er t") && !db_cosecha.equals("2009 2do t") && !db_cosecha.equals("2009 3er t") && !db_cosecha.equals("2009 4to t")
                            && !db_cosecha.equals("2010 1er t") && !db_cosecha.equals("2010 2do t") && !db_cosecha.equals("2010 3er t") && !db_cosecha.equals("2010 4to t")
                            && !db_cosecha.equals("2011 1er t") && !db_cosecha.equals("2011 2do t") && !db_cosecha.equals("2011 3er t") && !db_cosecha.equals("2011 4to t")
                            && !db_cosecha.equals("2012 1er t") && !db_cosecha.equals("2012 2do t") && !db_cosecha.equals("2012 3er t") && !db_cosecha.equals("2012 4to t")
                            && !db_cosecha.equals("2013 1er t") && !db_cosecha.equals("2013 2do t") && !db_cosecha.equals("2013 3er t") && !db_cosecha.equals("2013 4to t")
                            && !db_cosecha.equals("2014 1er t") && !db_cosecha.equals("2014 2do t") && !db_cosecha.equals("2014 3er t") && !db_cosecha.equals("2014 4to t")
                            && !db_cosecha.equals("2015 1er t") && !db_cosecha.equals("2015 2do t") && !db_cosecha.equals("2015 3er t") && !db_cosecha.equals("2015 4to t")
                            && !db_cosecha.equals("2016 1er t") && !db_cosecha.equals("2016 2do t") && !db_cosecha.equals("2016 3er t") && !db_cosecha.equals("2016 4to t")
                            && !db_cosecha.equals("2017 1er t") && !db_cosecha.equals("2017 2do t") && !db_cosecha.equals("2017 3er t") && !db_cosecha.equals("2017 4to t")
                            && !db_cosecha.equals("2018 1er t") && !db_cosecha.equals("2018 2do t") && !db_cosecha.equals("2018 3er t") && !db_cosecha.equals("2018 4to t")
                            && !db_cosecha.equals("2019 1er t") && !db_cosecha.equals("2019 2do t") && !db_cosecha.equals("2019 3er t") && !db_cosecha.equals("2019 4to t")
                            && !db_cosecha.equals("2020 1er t") && !db_cosecha.equals("2020 2do t") && !db_cosecha.equals("2020 3er t") && !db_cosecha.equals("2020 4to t")
                            && !db_cosecha.equals("2021 1er t") && !db_cosecha.equals("2021 2do t") && !db_cosecha.equals("2021 3er t") && !db_cosecha.equals("2021 4to t")
                            && !db_cosecha.equals("2022 1er t") && !db_cosecha.equals("2022 2do t") && !db_cosecha.equals("2022 3er t") && !db_cosecha.equals("2022 4to t")
                            && !db_cosecha.equals("2023 1er t") && !db_cosecha.equals("2023 2do t") && !db_cosecha.equals("2023 3er t") && !db_cosecha.equals("2023 4to t")
                            && !db_cosecha.equals("2024 1er t") && !db_cosecha.equals("2024 2do t") && !db_cosecha.equals("2024 3er t") && !db_cosecha.equals("2024 4to t")
                            && !db_cosecha.equals("2025 1er t") && !db_cosecha.equals("2025 2do t") && !db_cosecha.equals("2025 3er t") && !db_cosecha.equals("2025 4to t")
                            && !db_cosecha.equals("2026 1er t") && !db_cosecha.equals("2026 2do t") && !db_cosecha.equals("2026 3er t") && !db_cosecha.equals("2026 4to t")
                            && !db_cosecha.equals("2027 1er t") && !db_cosecha.equals("2027 2do t") && !db_cosecha.equals("2027 3er t") && !db_cosecha.equals("2027 4to t")
                            && !db_cosecha.equals("2028 1er t") && !db_cosecha.equals("2028 2do t") && !db_cosecha.equals("2028 3er t") && !db_cosecha.equals("2028 4to t")
                            && !db_cosecha.equals("2029 1er t") && !db_cosecha.equals("2029 2do t") && !db_cosecha.equals("2029 3er t") && !db_cosecha.equals("2029 4to t")
                            && !db_cosecha.equals("2030 1er t") && !db_cosecha.equals("2030 2do t") && !db_cosecha.equals("2030 3er t") && !db_cosecha.equals("2030 4to t")) {
                        throw new Exception("Error al calcular el campo Cosecha en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cosecha en la linea: " + linea_error);
                }

                String db_otro_no_cuenta = "";
                if (otro_no_cuenta != null) {
                    db_otro_no_cuenta = otro_no_cuenta.toString().trim();
                    if (db_otro_no_cuenta.equals("")) {
                        db_otro_no_cuenta = "-";
                    }
                } else {
                    db_otro_no_cuenta = "-";
                }

                String db_descripcion_adicional = "";
                if (descripcion_adicional != null) {
                    db_descripcion_adicional = descripcion_adicional.toString().trim();
                    if (db_descripcion_adicional.equals("")) {
                        db_descripcion_adicional = "-";
                    }
                } else {
                    db_descripcion_adicional = "-";
                }

                Date db_fecha_recepcion = new Date(1900, 0, 1);
                if (fecha_recepcion != null) {
                    try {
                        db_fecha_recepcion = fecha_recepcion.getDateCellValue();
                        formatoDate.format(db_fecha_recepcion);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha Recepción en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha Recepción en la linea: " + linea_error);
                }

                String db_anticipo = "";
                if (anticipo != null) {
                    db_anticipo = anticipo.toString().trim();
                    if (!db_anticipo.equals("NO") && !db_anticipo.equals("SI") && !db_anticipo.equals("COBRAR") && !db_anticipo.equals("REPRE")) {
                        throw new Exception("Error al calcular el campo Anticipo en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Anticipo en la linea: " + linea_error);
                }

                String db_antiguedad = "";
                if (antiguedad != null) {
                    db_antiguedad = antiguedad.toString().trim();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select a.antiguedad from antiguedad a where a.estado='VIGENTE' and trim(a.nombre)='" + db_antiguedad + "'");
                    Integer antiguedad_l = 0;
                    while (rs.next()) {
                        antiguedad_l = rs.getInt(1);
                    }
                    rs.close();
                    stmt.close();
                    if (antiguedad_l == 0) {
                        throw new Exception("Error al calcular el campo Antigüedad en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Antigüedad en la linea: " + linea_error);
                }

                Date db_fecha_proximo_pago = new Date();
                if (fecha_proximo_pago != null) {
                    try {
                        db_fecha_proximo_pago = fecha_proximo_pago.getDateCellValue();
                        formatoDate.format(db_fecha_proximo_pago);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha próximo pago en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha próximo pago en la linea: " + linea_error);
                }

                Double db_monto_proximo_pago = 0.00;
                try {
                    db_monto_proximo_pago = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_proximo_pago.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto próximo pago en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Double db_saldo = 0.00;
                try {
                    db_saldo = Double.parseDouble(formatoDouble.format(Double.parseDouble(saldo.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Saldo en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_convenio_pactado = "";
                if (convenio_pactado != null) {
                    db_convenio_pactado = convenio_pactado.toString().trim();
                    if (db_convenio_pactado.equals("")) {
                        db_convenio_pactado = "-";
                    }
                } else {
                    db_convenio_pactado = "-";
                }

                Double db_cuota_convenio = 0.00;
                try {
                    db_cuota_convenio = Double.parseDouble(formatoDouble.format(Double.parseDouble(cuota_convenio.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Cuota convenio en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_costas_pagadas = "";
                if (costas_pagadas != null) {
                    db_costas_pagadas = costas_pagadas.toString().trim();
                    if (db_costas_pagadas.equals("")) {
                        db_costas_pagadas = "-";
                    }
                } else {
                    db_costas_pagadas = "-";
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                String db_opcion_proximo_pago = "";
                if (opcion_proximo_pago != null) {
                    db_opcion_proximo_pago = opcion_proximo_pago.toString().trim();
                    if (!db_opcion_proximo_pago.equals("SI") && !db_opcion_proximo_pago.equals("NO")) {
                        throw new Exception("Error al calcular el campo Opción próximo pago en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Opción próximo pago en la linea: " + linea_error);
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Date db_fecha_demanda = new Date();
                if (fecha_demanda != null) {
                    try {
                        db_fecha_demanda = fecha_demanda.getDateCellValue();
                        formatoDate.format(db_fecha_demanda);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha demanda pago en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha demanda pago en la linea: " + linea_error);
                }

                Integer db_procurador = 0;
                try {
                    db_procurador = Integer.parseInt(formatoInteger.format(Double.parseDouble(procurador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Procurador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_juzgado = 0;
                try {
                    db_juzgado = Integer.parseInt(formatoInteger.format(Double.parseDouble(juzgado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Juzgado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_no_juicio = "";
                if (no_juicio != null) {
                    db_no_juicio = no_juicio.toString().trim();
                    if (db_no_juicio.equals("")) {
                        db_no_juicio = "-";
                    }
                } else {
                    db_no_juicio = "-";
                }

                Integer db_notificador = 0;
                try {
                    db_notificador = Integer.parseInt(formatoInteger.format(Double.parseDouble(notificador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Notificador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Double db_monto_demanda = 0.00;
                try {
                    db_monto_demanda = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_demanda.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto Demanda en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_cuenta_principal_relacion = 0;
                if (cuenta_principal_relacion != null) {
                    String cuenta_principal_relacion_str = cuenta_principal_relacion.toString().trim();
                    if (!cuenta_principal_relacion_str.equals("SI") && !cuenta_principal_relacion_str.equals("NO")) {
                        throw new Exception("Error al calcular el campo Cuenta principal rel. en la linea: " + linea_error);
                    } else {
                        if(cuenta_principal_relacion_str.equals("SI")) {
                            db_cuenta_principal_relacion = 1;
                        } else {
                            db_cuenta_principal_relacion = 0;
                        }
                    }
                } else {
                    throw new Exception("Error al calcular el campo Cuenta principal rel. en la linea: " + linea_error);
                }
                
                Integer db_deudor_cuenta_relacionada = 0;
                try {
                    db_deudor_cuenta_relacionada = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor_cuenta_relacionada.toString().trim())));
                    if (db_cuenta_principal_relacion == 1) {
                        db_deudor_cuenta_relacionada = 0;
                    } else {
                        if (db_deudor_cuenta_relacionada != 0) {
                            // Obtener datos de la cuenta relacionada.
                            Integer deudor_relacionado = 0;
                            Integer corporacion_relacionada = 0;
                            Integer cuenta_principal_relacionada = 0;
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("select d.deudor, a.cooperacion, d.cuenta_principal_relacion from deudor d left join actor a on (d.actor=a.actor) where d.deudor=" + db_deudor_cuenta_relacionada);
                            while (rs.next()) {
                                deudor_relacionado = rs.getInt(1);
                                corporacion_relacionada = rs.getInt(2);
                                cuenta_principal_relacionada = rs.getInt(3);
                            }
                            rs.close();
                            stmt.close();
                            // Obtener corporacion del deudor.
                            Integer corporacion_cuenta = 0;
                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("select a.cooperacion from actor a where a.actor=" + db_actor);
                            while (rs.next()) {
                                corporacion_cuenta = rs.getInt(1);
                            }
                            rs.close();
                            stmt.close();

                            if(deudor_relacionado == 0) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Cuenta deudor no existe.");
                            }
                            if (corporacion_relacionada != corporacion_cuenta) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Corporación distinta.");
                            } 
                            if(cuenta_principal_relacionada != 1) {
                                throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " => Cuenta relacionada no es principal.");
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Cuenta relacionada en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Deudores_Actualizacion_Masiva deu_act_mas = new Deudores_Actualizacion_Masiva(
                        db_deudor,
                        db_actor,
                        db_moneda,
                        db_dpi,
                        db_nit,
                        db_fecha_nacimiento,
                        db_nombre_deudor,
                        db_nacionalidad,
                        db_telefono_casa,
                        db_telefono_celular,
                        db_direccion,
                        db_zona,
                        db_pais,
                        db_departamento,
                        db_sexo,
                        db_estado_civil,
                        db_fecha_ingreso,
                        db_profesion,
                        db_correo_electronico,
                        db_lugar_trabajo,
                        db_contacto_trabajo,
                        db_telefono_trabajo,
                        db_monto_inicial,
                        db_gestor,
                        db_estado_judicial,
                        db_estatus_judicial,
                        db_estado_extrajudicial,
                        db_estatus_extrajudicial,
                        db_intencion_pago,
                        db_no_cuenta,
                        db_garantia,
                        db_cargado,
                        db_estado_ae,
                        db_notas,
                        db_codigo_resultado,
                        db_caso,
                        db_pdf,
                        db_inv,
                        db_maycom,
                        db_nits,
                        db_cosecha,
                        db_otro_no_cuenta,
                        db_descripcion_adicional,
                        db_fecha_recepcion,
                        db_anticipo,
                        db_antiguedad,
                        db_fecha_proximo_pago,
                        db_monto_proximo_pago,
                        db_saldo,
                        db_convenio_pactado,
                        db_cuota_convenio,
                        db_costas_pagadas,
                        db_situacion_caso,
                        db_opcion_proximo_pago,
                        db_procuracion,
                        db_fecha_demanda,
                        db_procurador,
                        db_juzgado,
                        db_no_juicio,
                        db_notificador,
                        db_monto_demanda,
                        db_cuenta_principal_relacion,
                        db_deudor_cuenta_relacionada);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL ****************************
                Integer int_estado_judicial_actual = 0;
                String str_estado_judicial_actual = "";
                Integer int_status_judicial_actual = 0;
                String str_status_judicial_actual = "";
                Integer int_estado_extrajudicial_actual = 0;
                String str_estado_extrajudicial_actual = "";
                Integer int_status_extrajudicial_actual = 0;
                String str_status_extrajudicial_actual = "";
                String nombre_deudor_flow = "";
                String cadenasql = "select "
                        + "d.sestado, "
                        + "s.nombre, "
                        + "d.estatus, "
                        + "e.nombre, "
                        + "d.sestado_extra, "
                        + "sx.nombre, "
                        + "d.estatus_extra, "
                        + "ex.nombre, "
                        + "d.nombre "
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
                    nombre_deudor_flow = rs.getString(9);
                }
                rs.close();
                stmt.close();
                
                // **************************** ACTUALIZACION DE LA TABLA DEUDOR. ****************************
                cadenasql = "update deudor set "
                        + "actor=" + deu_act_mas.getActor() + ", "
                        + "moneda='" + deu_act_mas.getMoneda() + "', "
                        + "dpi='" + deu_act_mas.getDpi() + "', "
                        + "nit='" + deu_act_mas.getNit() + "', "
                        + "fecha_nacimiento='" + formatoDate1.format(deu_act_mas.getFecha_nacimiento()) + "', "
                        + "nombre='" + deu_act_mas.getNombre_deudor() + "', "
                        + "nacionalidad='" + deu_act_mas.getNacionalidad() + "', "
                        + "telefono_casa='" + deu_act_mas.getTelefono_casa() + "', "
                        + "telefono_celular='" + deu_act_mas.getTelefono_celular() + "', "
                        + "direccion='" + deu_act_mas.getDireccion() + "', "
                        + "zona=" + deu_act_mas.getZona() + ", "
                        + "pais='" + deu_act_mas.getPais() + "', "
                        + "departamento='" + deu_act_mas.getDepartamento() + "', "
                        + "sexo='" + deu_act_mas.getSexo() + "', "
                        + "estado_civil='" + deu_act_mas.getEstado_civil() + "', "
                        + "fecha_ingreso='" + formatoDate1.format(deu_act_mas.getFecha_ingreso()) + "', "
                        + "profesion='" + deu_act_mas.getProfesion() + "', "
                        + "correo_electronico='" + deu_act_mas.getCorreo_electronico() + "', "
                        + "lugar_trabajo='" + deu_act_mas.getLugar_trabajo() + "', "
                        + "direccion_trabajo='" + deu_act_mas.getContacto_trabajo() + "', "
                        + "telefono_trabajo='" + deu_act_mas.getTelefono_trabajo() + "', "
                        + "monto_inicial=" + deu_act_mas.getMonto_inicial() + ", "
                        + "usuario=" + deu_act_mas.getGestor() + ", "
                        + "sestado=" + deu_act_mas.getEstado_judicial() + ", "
                        + "estatus=" + deu_act_mas.getEstatus_judicial() + ", "
                        + "no_cuenta='" + deu_act_mas.getNo_cuenta() + "', "
                        + "garantia=" + deu_act_mas.getGarantia() + ", "
                        + "cargado='" + deu_act_mas.getCargado() + "', "
                        + "estado='" + deu_act_mas.getEstado_ae() + "', "
                        + "descripcion='" + deu_act_mas.getNotas() + "', "
                        + "codigo_contactabilidad=" + deu_act_mas.getCodigo_resultado() + ", "
                        + "caso=" + deu_act_mas.getCaso() + ", "
                        + "PDF='" + deu_act_mas.getPdf() + "', "
                        + "INV='" + deu_act_mas.getInv() + "', "
                        + "MAYCOM='" + deu_act_mas.getMaycom() + "', "
                        + "NITS='" + deu_act_mas.getNits() + "', "
                        + "cosecha='" + deu_act_mas.getCosecha() + "', "
                        + "no_cuenta_otro='" + deu_act_mas.getOtro_no_cuenta() + "', "
                        + "descripcion_adicional='" + deu_act_mas.getDescripcion_adicional() + "', "
                        + "fecha_recepcion='" + formatoDate1.format(deu_act_mas.getFecha_recepcion()) + "', "
                        + "anticipo='" + deu_act_mas.getAnticipo() + "', "
                        + "antiguedad='" + deu_act_mas.getAntiguedad() + "', "
                        + "fecha_proximo_pago='" + formatoDate1.format(deu_act_mas.getFecha_proximo_pago()) + "', "
                        + "monto_proximo_pago=" + deu_act_mas.getMonto_proximo_pago() + ", "
                        + "saldo=" + deu_act_mas.getSaldo() + ", "
                        + "convenio_pactado='" + deu_act_mas.getConvenio_pactado() + "', "
                        + "cuota_convenio=" + deu_act_mas.getCuota_convenio() + ", "
                        + "costas_pagadas='" + deu_act_mas.getCostas_pagadas() + "', "
                        + "situacion_caso='" + deu_act_mas.getSituacion_caso() + "', "
                        + "opcion_proximo_pago='" + deu_act_mas.getOpcion_proximo_pago() + "', "
                        + "sestado_extra=" + deu_act_mas.getEstado_extrajudicial() + ", "
                        + "estatus_extra=" + deu_act_mas.getEstatus_extrajudicial() + ", "
                        + "intencion_pago=" + deu_act_mas.getIntencion_pago() + ", "
                        + "cuenta_principal_relacion=" + deu_act_mas.getCuenta_principal_relacion() + ", "
                        + "deudor_cuenta_relacionada=" + deu_act_mas.getDeudor_cuenta_relacionada() + " "
                        + "where deudor=" + deu_act_mas.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update juicio set "
                        + "procuracion='" + deu_act_mas.getProcuracion() + "', "
                        + "fecha='" + formatoDate1.format(deu_act_mas.getFecha_demanda()) + "', "
                        + "procurador=" + deu_act_mas.getProcurador() + ", "
                        + "juzgado=" + deu_act_mas.getJuzgado() + ", "
                        + "no_juicio='" + deu_act_mas.getNo_juicio() + "', "
                        + "notificador=" + deu_act_mas.getNotificador() + ", "
                        + "monto=" + deu_act_mas.getMonto_demanda() + " "
                        + "where deudor=" + deu_act_mas.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON ****************************
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON ****************************
                if (!(deu_act_mas.getEstado_judicial() == int_estado_judicial_actual && deu_act_mas.getEstatus_judicial() == int_status_judicial_actual)) {
                    String str_sestado_judicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado s where s.sestado=" + deu_act_mas.getEstado_judicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_judicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_judicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus e where e.estatus=" + deu_act_mas.getEstatus_judicial();
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
                            + deu_act_mas.getEstatus_judicial() + "','"
                            + str_estatus_judicial_nuevo + "','"
                            + deu_act_mas.getEstado_judicial() + "','"
                            + str_sestado_judicial_nuevo + "','"
                            + int_status_judicial_actual.toString() + "','"
                            + str_status_judicial_actual + "','"
                            + int_estado_judicial_actual.toString() + "','"
                            + str_estado_judicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (ACTUALIZACION MASIVA).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (!(deu_act_mas.getEstado_extrajudicial() == int_estado_extrajudicial_actual && deu_act_mas.getEstatus_extrajudicial() == int_status_extrajudicial_actual)) {
                    String str_sestado_extrajudicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + deu_act_mas.getEstado_extrajudicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_extrajudicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_extrajudicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + deu_act_mas.getEstatus_extrajudicial();
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
                            + deu_act_mas.getEstatus_extrajudicial() + "','"
                            + str_estatus_extrajudicial_nuevo + "','"
                            + deu_act_mas.getEstado_extrajudicial() + "','"
                            + str_sestado_extrajudicial_nuevo + "','"
                            + int_status_extrajudicial_actual.toString() + "','"
                            + str_status_extrajudicial_actual + "','"
                            + int_estado_extrajudicial_actual.toString() + "','"
                            + str_estado_extrajudicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor_flow + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (ACTUALIZACION MASIVA).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Actualización masiva de deudores." + "',"
                    + "12" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actualización de deudores exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actualizacion_Masiva_Deudores): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actualizacion_Masiva_Deudores - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_rotacion_cartera(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = null;
        Integer fila = 0;

        try {
            conn.setAutoCommit(false);

            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(new File(archivo), workbookSettings);
            Sheet sheet = workbook.getSheet(0);
            Integer filas = sheet.getRows();

            for (fila = 1; fila < filas; fila++) {
                Cell dato1 = sheet.getCell(0, fila);
                Cell dato2 = sheet.getCell(1, fila);
                Cell dato3 = sheet.getCell(2, fila);
                Cell dato4 = sheet.getCell(3, fila);
                Cell dato5 = sheet.getCell(4, fila);
                Cell dato6 = sheet.getCell(5, fila);
                Cell dato7 = sheet.getCell(6, fila);
                Cell dato8 = sheet.getCell(7, fila);
                Cell dato9 = sheet.getCell(8, fila);
                Cell dato10 = sheet.getCell(9, fila);
                Cell dato11 = sheet.getCell(10, fila);
                Cell dato12 = sheet.getCell(11, fila);
                Cell dato13 = sheet.getCell(12, fila);
                Cell dato14 = sheet.getCell(13, fila);
                Cell dato15 = sheet.getCell(14, fila);

                Integer deudor = driver.valida_integer(dato1.getContents());
                if (deudor != 0) {
                    Deudores_Rotacion deu_rot = new Deudores_Rotacion(
                            dato1.getContents(),
                            dato2.getContents(),
                            dato3.getContents(),
                            dato4.getContents(),
                            dato5.getContents(),
                            dato6.getContents(),
                            dato7.getContents(),
                            dato8.getContents(),
                            dato9.getContents(),
                            dato10.getContents(),
                            dato11.getContents(),
                            dato12.getContents(),
                            dato13.getContents(),
                            dato14.getContents(),
                            dato15.getContents());

                    String cadenasql = "update deudor set "
                            + "usuario='" + driver.quitar_simbolos(deu_rot.getGestor()) + "' "
                            + "where deudor='" + driver.quitar_simbolos(deu_rot.getDeudor()) + "'";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            workbook.close();
            sheet = null;
            workbook = null;

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga rotación cartera mensual." + "',"
                    + "13" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rotación de cartera manual exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Rotacion_Cartera): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + fila + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Rotacion_Cartera - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_pagos_masivos(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell fecha_pago = row.getCell(8);
                XSSFCell monto_pago = row.getCell(9);
                XSSFCell banco_pago = row.getCell(10);
                XSSFCell boleta_pago = row.getCell(11);
                XSSFCell descripcion = row.getCell(19);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Date db_fecha_pago = new Date(1900, 0, 1);
                if (fecha_pago != null) {
                    try {
                        db_fecha_pago = fecha_pago.getDateCellValue();
                        formatoDate.format(db_fecha_pago);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha pago en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha pago en la linea: " + linea_error);
                }

                Double db_monto_pago = 0.00;
                try {
                    db_monto_pago = Double.parseDouble(formatoDouble.format(Double.parseDouble(monto_pago.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Monto pago en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_banco_pago = 0;
                try {
                    db_banco_pago = Integer.parseInt(formatoInteger.format(Double.parseDouble(banco_pago.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Banco pago en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_boleta_pago = "";
                if (boleta_pago != null) {
                    db_boleta_pago = boleta_pago.toString().trim();
                    if (db_boleta_pago.equals("")) {
                        db_boleta_pago = "BOL-SYS";
                    }
                } else {
                    db_boleta_pago = "BOL-SYS";
                }

                String db_descripcion = "";
                if (descripcion != null) {
                    db_descripcion = descripcion.toString().trim();
                    if (db_descripcion.equals("")) {
                        db_descripcion = "Pago carga masiva.";
                    }
                } else {
                    db_descripcion = "Pago carga masiva.";
                }

                Deudores_Pagos_Masivos deu_car_mas = new Deudores_Pagos_Masivos(
                        db_deudor,
                        db_fecha_pago,
                        db_monto_pago,
                        db_banco_pago,
                        db_boleta_pago,
                        db_descripcion);

                String cadenasql = "insert into pago ("
                        + "deudor, "
                        + "banco, "
                        + "fecha, "
                        + "no_boleta, "
                        + "monto, "
                        + "descripcion, "
                        + "fecha_registro) values ("
                        + deu_car_mas.getDeudor() + ","
                        + deu_car_mas.getBanco_pago() + ",'"
                        + formatoDate1.format(deu_car_mas.getFecha_pago()) + "','"
                        + deu_car_mas.getBoleta_pago() + "',"
                        + deu_car_mas.getMonto_pago() + ",'"
                        + deu_car_mas.getDescripcion() + "',"
                        + "CURRENT_DATE()" + ")";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                Date fecha_actual = new Date();
                DecimalFormat df = new DecimalFormat("#,###,##0.00");
                cadenasql = "insert into deudor_historial_cobros ("
                        + "deudor, "
                        + "fecha, "
                        + "hora, "
                        + "usuario, "
                        + "codigo_contactabilidad, "
                        + "descripcion, "
                        + "contacto) values ("
                        + deu_car_mas.getDeudor() + ","
                        + "CURRENT_DATE()" + ","
                        + "CURRENT_TIME()" + ","
                        + usuario_sys + ","
                        + "14" + ",'"
                        + "El dia " + formatoDate.format(fecha_actual) + " se reporto el pago en cuenta por aplicar de Q. " + df.format(deu_car_mas.getMonto_pago()) + " efectuado el dia de " + formatoDate.format(deu_car_mas.getFecha_pago()) + "." + "','"
                        + "SI" + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update deudor set codigo_contactabilidad=14 where deudor=" + deu_car_mas.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva de pagos." + "',"
                    + "14" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga de pagos exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Pagos_Masivos): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Pagos_Masivos - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_masiva_presentar_demanda(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell nuevo_estado_judicial = row.getCell(10);
                XSSFCell nuevo_estatus_judicial = row.getCell(11);
                XSSFCell situacion_caso = row.getCell(12);
                XSSFCell procuracion = row.getCell(13);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estado_judicial = 0;
                try {
                    db_nuevo_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estado judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estatus_judicial = 0;
                try {
                    db_nuevo_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estatus judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Deudores_Demanda_Presentar_Demanda deu_dem_pre_dem = new Deudores_Demanda_Presentar_Demanda(
                        db_deudor,
                        db_nuevo_estado_judicial,
                        db_nuevo_estatus_judicial,
                        db_situacion_caso,
                        db_procuracion);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL. ****************************
                Integer int_estado_judicial_actual = 0;
                String str_estado_judicial_actual = "";
                Integer int_status_judicial_actual = 0;
                String str_status_judicial_actual = "";
                Integer int_estado_extrajudicial_actual = 0;
                String str_estado_extrajudicial_actual = "";
                Integer int_status_extrajudicial_actual = 0;
                String str_status_extrajudicial_actual = "";
                String nombre_deudor_flow = "";
                String cadenasql = "select "
                        + "d.sestado, "
                        + "s.nombre, "
                        + "d.estatus, "
                        + "e.nombre, "
                        + "d.sestado_extra, "
                        + "sx.nombre, "
                        + "d.estatus_extra, "
                        + "ex.nombre, "
                        + "d.nombre "
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
                    nombre_deudor_flow = rs.getString(9);
                }
                rs.close();
                stmt.close();

                // **************************** Modificación de juicios en Presentar Demanda. ****************************
                cadenasql = "update deudor set "
                        + "sestado=" + deu_dem_pre_dem.getNuevo_estado_judicial() + ", "
                        + "estatus=" + deu_dem_pre_dem.getNuevo_estatus_judicial() + ", "
                        + "situacion_caso='" + driver.quitar_simbolos(deu_dem_pre_dem.getSituacion_caso()) + "' "
                        + "where deudor=" + deu_dem_pre_dem.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update juicio set "
                        + "procuracion='" + driver.quitar_simbolos(deu_dem_pre_dem.getProcuracion()) + "' "
                        + "where deudor=" + deu_dem_pre_dem.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON. ****************************
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON. ****************************
                if (!(deu_dem_pre_dem.getNuevo_estado_judicial() == int_estado_judicial_actual && deu_dem_pre_dem.getNuevo_estatus_judicial() == int_status_judicial_actual)) {
                    String str_sestado_judicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado s where s.sestado=" + deu_dem_pre_dem.getNuevo_estado_judicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_judicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_judicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus e where e.estatus=" + deu_dem_pre_dem.getNuevo_estatus_judicial();
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
                            + deu_dem_pre_dem.getNuevo_estatus_judicial() + "','"
                            + str_estatus_judicial_nuevo + "','"
                            + deu_dem_pre_dem.getNuevo_estado_judicial() + "','"
                            + str_sestado_judicial_nuevo + "','"
                            + int_status_judicial_actual.toString() + "','"
                            + str_status_judicial_actual + "','"
                            + int_estado_judicial_actual.toString() + "','"
                            + str_estado_judicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor_flow + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (CARGA MASIVA PRESENTAR DEMANDA).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva presentar demanda." + "',"
                    + "15" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga masiva presentar demanda existosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Presentar_Demanda): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Presentar_Demanda - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_masiva_caratula(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell nuevo_estado_judicial = row.getCell(10);
                XSSFCell nuevo_estatus_judicial = row.getCell(11);
                XSSFCell situacion_caso = row.getCell(12);
                XSSFCell procuracion = row.getCell(13);
                XSSFCell juzgado = row.getCell(14);
                XSSFCell no_juicio = row.getCell(15);
                XSSFCell fecha_juicio = row.getCell(16);
                XSSFCell notificador = row.getCell(17);
                XSSFCell procurador = row.getCell(18);
                XSSFCell arraigo = row.getCell(19);
                XSSFCell fecha_arraigo = row.getCell(20);
                XSSFCell banco = row.getCell(21);
                XSSFCell fecha_banco = row.getCell(22);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estado_judicial = 0;
                try {
                    db_nuevo_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estado judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estatus_judicial = 0;
                try {
                    db_nuevo_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estatus judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Integer db_juzgado = 0;
                try {
                    db_juzgado = Integer.parseInt(formatoInteger.format(Double.parseDouble(juzgado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Juzgado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_no_juicio = "";
                if (no_juicio != null) {
                    db_no_juicio = no_juicio.toString().trim();
                    if (db_no_juicio.equals("")) {
                        db_no_juicio = "-";
                    }
                } else {
                    db_no_juicio = "-";
                }

                Date db_fecha_juicio = new Date(1900, 0, 1);
                if (fecha_juicio != null) {
                    try {
                        db_fecha_juicio = fecha_juicio.getDateCellValue();
                        formatoDate.format(db_fecha_juicio);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha juicio en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha juicio en la linea: " + linea_error);
                }

                Integer db_notificador = 0;
                try {
                    db_notificador = Integer.parseInt(formatoInteger.format(Double.parseDouble(notificador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Notificador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_procurador = 0;
                try {
                    db_procurador = Integer.parseInt(formatoInteger.format(Double.parseDouble(procurador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Procurador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_arraigo = "";
                if (arraigo != null) {
                    db_arraigo = arraigo.toString().trim();
                    if (db_arraigo.equals("")) {
                        db_arraigo = "-";
                    }
                } else {
                    db_arraigo = "-";
                }

                Date db_fecha_arraigo = new Date(1900, 0, 1);
                if (fecha_arraigo != null) {
                    try {
                        db_fecha_arraigo = fecha_arraigo.getDateCellValue();
                        formatoDate.format(db_fecha_arraigo);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error);
                }

                String db_banco = "";
                if (banco != null) {
                    db_banco = banco.toString().trim();
                    if (db_banco.equals("")) {
                        db_banco = "-";
                    }
                } else {
                    db_banco = "-";
                }

                Date db_fecha_banco = new Date(1900, 0, 1);
                if (fecha_banco != null) {
                    try {
                        db_fecha_banco = fecha_banco.getDateCellValue();
                        formatoDate.format(db_fecha_banco);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error);
                }

                Deudores_Demanda_Caratula deu_dem_car = new Deudores_Demanda_Caratula(
                        db_deudor,
                        db_nuevo_estado_judicial,
                        db_nuevo_estatus_judicial,
                        db_situacion_caso,
                        db_procuracion,
                        db_juzgado,
                        db_no_juicio,
                        db_fecha_juicio,
                        db_notificador,
                        db_procurador,
                        db_arraigo,
                        db_fecha_arraigo,
                        db_banco,
                        db_fecha_banco);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL. ****************************
                Integer int_estado_judicial_actual = 0;
                String str_estado_judicial_actual = "";
                Integer int_status_judicial_actual = 0;
                String str_status_judicial_actual = "";
                Integer int_estado_extrajudicial_actual = 0;
                String str_estado_extrajudicial_actual = "";
                Integer int_status_extrajudicial_actual = 0;
                String str_status_extrajudicial_actual = "";
                String nombre_deudor_flow = "";
                String cadenasql = "select "
                        + "d.sestado, "
                        + "s.nombre, "
                        + "d.estatus, "
                        + "e.nombre, "
                        + "d.sestado_extra, "
                        + "sx.nombre, "
                        + "d.estatus_extra, "
                        + "ex.nombre, "
                        + "d.nombre "
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
                    nombre_deudor_flow = rs.getString(9);
                }
                rs.close();
                stmt.close();

                // **************************** Modificación de juicios en Caratula. ****************************
                cadenasql = "update deudor set "
                        + "sestado=" + deu_dem_car.getNuevo_estado_judicial() + ", "
                        + "estatus=" + deu_dem_car.getNuevo_estatus_judicial() + ", "
                        + "situacion_caso='" + driver.quitar_simbolos(deu_dem_car.getSituacion_caso()) + "' "
                        + "where deudor=" + deu_dem_car.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update juicio set "
                        + "procuracion='" + driver.quitar_simbolos(deu_dem_car.getProcuracion()) + "', "
                        + "juzgado=" + deu_dem_car.getJuzgado() + ", "
                        + "no_juicio='" + driver.quitar_simbolos(deu_dem_car.getNo_juicio()) + "', "
                        + "fecha='" + formatoDate1.format(deu_dem_car.getFecha_juicio()) + "', "
                        + "notificador=" + deu_dem_car.getNotificador() + ", "
                        + "procurador=" + deu_dem_car.getProcurador() + " "
                        + "where deudor=" + deu_dem_car.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                String juicio = "";
                cadenasql = "select j.juicio from juicio j where j.deudor=" + deu_dem_car.getDeudor();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    juicio = rs.getString(1);
                }
                rs.close();
                stmt.close();

                if (driver.valido_arraigo(deu_dem_car.getArraigo())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(ja.correlativo) + 1,0) from juicio_arraigo ja where ja.juicio = '" + juicio + "'";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_arraigo ("
                            + "juicio,"
                            + "correlativo,"
                            + "arraigo,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_car.getArraigo()) + "','"
                            + formatoDate1.format(deu_dem_car.getFecha_arraigo()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_banco(deu_dem_car.getBanco())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jb.correlativo) + 1,0) from juicio_banco jb where jb.juicio = '" + juicio + "'";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_banco ("
                            + "juicio,"
                            + "correlativo,"
                            + "bancos,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_car.getBanco()) + "','"
                            + formatoDate1.format(deu_dem_car.getFecha_banco()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON. ****************************
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON. ****************************
                if (!(deu_dem_car.getNuevo_estado_judicial() == int_estado_judicial_actual && deu_dem_car.getNuevo_estatus_judicial() == int_status_judicial_actual)) {
                    String str_sestado_judicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado s where s.sestado=" + deu_dem_car.getNuevo_estado_judicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_judicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_judicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus e where e.estatus=" + deu_dem_car.getNuevo_estatus_judicial();
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
                            + deu_dem_car.getNuevo_estatus_judicial() + "','"
                            + str_estatus_judicial_nuevo + "','"
                            + deu_dem_car.getNuevo_estado_judicial() + "','"
                            + str_sestado_judicial_nuevo + "','"
                            + int_status_judicial_actual.toString() + "','"
                            + str_status_judicial_actual + "','"
                            + int_estado_judicial_actual.toString() + "','"
                            + str_estado_judicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor_flow + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (CARGA MASIVA CARATULA).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva carátula demanda." + "',"
                    + "16" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga masiva carátula demanda exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Caratula): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Caratula - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_masiva_medidas_precautorias(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell nuevo_estado_judicial = row.getCell(11);
                XSSFCell nuevo_estatus_judicial = row.getCell(12);
                XSSFCell situacion_caso = row.getCell(13);
                XSSFCell procuracion = row.getCell(14);
                XSSFCell juzgado = row.getCell(15);
                XSSFCell no_juicio = row.getCell(16);
                XSSFCell notificador = row.getCell(17);
                XSSFCell procurador = row.getCell(18);
                XSSFCell razon_notificacion = row.getCell(19);
                XSSFCell arraigo = row.getCell(20);
                XSSFCell fecha_arraigo = row.getCell(21);
                XSSFCell banco = row.getCell(22);
                XSSFCell fecha_banco = row.getCell(23);
                XSSFCell finca = row.getCell(24);
                XSSFCell letra_finca = row.getCell(25);
                XSSFCell fecha_finca = row.getCell(26);
                XSSFCell tramite_finca = row.getCell(27);
                XSSFCell salario = row.getCell(28);
                XSSFCell empresa_salario = row.getCell(29);
                XSSFCell fecha_salario = row.getCell(30);
                XSSFCell vehiculo = row.getCell(31);
                XSSFCell medida_vehiculo = row.getCell(32);
                XSSFCell fecha_vehiculo = row.getCell(33);
                XSSFCell otros = row.getCell(34);
                XSSFCell medida_otros = row.getCell(35);
                XSSFCell fecha_otros = row.getCell(36);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estado_judicial = 0;
                try {
                    db_nuevo_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estado judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estatus_judicial = 0;
                try {
                    db_nuevo_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estatus judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Integer db_juzgado = 0;
                try {
                    db_juzgado = Integer.parseInt(formatoInteger.format(Double.parseDouble(juzgado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Juzgado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_no_juicio = "";
                if (no_juicio != null) {
                    db_no_juicio = no_juicio.toString().trim();
                    if (db_no_juicio.equals("")) {
                        db_no_juicio = "-";
                    }
                } else {
                    db_no_juicio = "-";
                }

                Integer db_notificador = 0;
                try {
                    db_notificador = Integer.parseInt(formatoInteger.format(Double.parseDouble(notificador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Notificador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_procurador = 0;
                try {
                    db_procurador = Integer.parseInt(formatoInteger.format(Double.parseDouble(procurador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Procurador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_razon_notificacion = "";
                if (razon_notificacion != null) {
                    db_razon_notificacion = razon_notificacion.toString().trim();
                    if (db_razon_notificacion.equals("")) {
                        db_razon_notificacion = "-";
                    }
                } else {
                    db_razon_notificacion = "-";
                }

                String db_arraigo = "";
                if (arraigo != null) {
                    db_arraigo = arraigo.toString().trim();
                    if (db_arraigo.equals("")) {
                        db_arraigo = "-";
                    }
                } else {
                    db_arraigo = "-";
                }

                Date db_fecha_arraigo = new Date(1900, 0, 1);
                if (fecha_arraigo != null) {
                    try {
                        db_fecha_arraigo = fecha_arraigo.getDateCellValue();
                        formatoDate.format(db_fecha_arraigo);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error);
                }

                String db_banco = "";
                if (banco != null) {
                    db_banco = banco.toString().trim();
                    if (db_banco.equals("")) {
                        db_banco = "-";
                    }
                } else {
                    db_banco = "-";
                }

                Date db_fecha_banco = new Date(1900, 0, 1);
                if (fecha_banco != null) {
                    try {
                        db_fecha_banco = fecha_banco.getDateCellValue();
                        formatoDate.format(db_fecha_banco);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error);
                }

                String db_finca = "";
                if (finca != null) {
                    db_finca = finca.toString().trim();
                    if (db_finca.equals("")) {
                        db_finca = "-";
                    }
                } else {
                    db_finca = "-";
                }

                String db_letra_finca = "";
                if (letra_finca != null) {
                    db_letra_finca = letra_finca.toString().trim();
                    if (db_letra_finca.equals("")) {
                        db_letra_finca = "-";
                    }
                } else {
                    db_letra_finca = "-";
                }

                Date db_fecha_finca = new Date(1900, 0, 1);
                if (fecha_finca != null) {
                    try {
                        db_fecha_finca = fecha_finca.getDateCellValue();
                        formatoDate.format(db_fecha_finca);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha finca en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha finca en la linea: " + linea_error);
                }

                String db_tramite_finca = "";
                if (tramite_finca != null) {
                    db_tramite_finca = tramite_finca.toString().trim();
                    if (db_tramite_finca.equals("")) {
                        db_tramite_finca = "-";
                    }
                } else {
                    db_tramite_finca = "-";
                }

                String db_salario = "";
                if (salario != null) {
                    db_salario = salario.toString().trim();
                    if (db_salario.equals("")) {
                        db_salario = "-";
                    }
                } else {
                    db_salario = "-";
                }

                String db_empresa_salario = "";
                if (empresa_salario != null) {
                    db_empresa_salario = empresa_salario.toString().trim();
                    if (db_empresa_salario.equals("")) {
                        db_empresa_salario = "-";
                    }
                } else {
                    db_empresa_salario = "-";
                }

                Date db_fecha_salario = new Date(1900, 0, 1);
                if (fecha_salario != null) {
                    try {
                        db_fecha_salario = fecha_salario.getDateCellValue();
                        formatoDate.format(db_fecha_salario);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha salario en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha salario en la linea: " + linea_error);
                }

                String db_vehiculo = "";
                if (vehiculo != null) {
                    db_vehiculo = vehiculo.toString().trim();
                    if (db_vehiculo.equals("")) {
                        db_vehiculo = "-";
                    }
                } else {
                    db_vehiculo = "-";
                }

                String db_medida_vehiculo = "";
                if (medida_vehiculo != null) {
                    db_medida_vehiculo = medida_vehiculo.toString().trim();
                    if (db_medida_vehiculo.equals("")) {
                        db_medida_vehiculo = "-";
                    }
                } else {
                    db_medida_vehiculo = "-";
                }

                Date db_fecha_vehiculo = new Date(1900, 0, 1);
                if (fecha_vehiculo != null) {
                    try {
                        db_fecha_vehiculo = fecha_vehiculo.getDateCellValue();
                        formatoDate.format(db_fecha_vehiculo);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha vehículo en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha vehículo en la linea: " + linea_error);
                }

                String db_otros = "";
                if (otros != null) {
                    db_otros = otros.toString().trim();
                    if (db_otros.equals("")) {
                        db_otros = "-";
                    }
                } else {
                    db_otros = "-";
                }

                String db_medida_otros = "";
                if (medida_otros != null) {
                    db_medida_otros = medida_otros.toString().trim();
                    if (db_medida_otros.equals("")) {
                        db_medida_otros = "-";
                    }
                } else {
                    db_medida_otros = "-";
                }

                Date db_fecha_otros = new Date(1900, 0, 1);
                if (fecha_otros != null) {
                    try {
                        db_fecha_otros = fecha_otros.getDateCellValue();
                        formatoDate.format(db_fecha_otros);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha otros en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha otros en la linea: " + linea_error);
                }

                Deudores_Demanda_Medidas_Precautorias deu_dem_med_pre = new Deudores_Demanda_Medidas_Precautorias(
                        db_deudor,
                        db_nuevo_estado_judicial,
                        db_nuevo_estatus_judicial,
                        db_situacion_caso,
                        db_procuracion,
                        db_juzgado,
                        db_no_juicio,
                        db_notificador,
                        db_procurador,
                        db_razon_notificacion,
                        db_arraigo,
                        db_fecha_arraigo,
                        db_banco,
                        db_fecha_banco,
                        db_finca,
                        db_letra_finca,
                        db_fecha_finca,
                        db_tramite_finca,
                        db_salario,
                        db_empresa_salario,
                        db_fecha_salario,
                        db_vehiculo,
                        db_medida_vehiculo,
                        db_fecha_vehiculo,
                        db_otros,
                        db_medida_otros,
                        db_fecha_otros);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL. ****************************
                Integer int_estado_judicial_actual = 0;
                String str_estado_judicial_actual = "";
                Integer int_status_judicial_actual = 0;
                String str_status_judicial_actual = "";
                Integer int_estado_extrajudicial_actual = 0;
                String str_estado_extrajudicial_actual = "";
                Integer int_status_extrajudicial_actual = 0;
                String str_status_extrajudicial_actual = "";
                String nombre_deudor_flow = "";
                String cadenasql = "select "
                        + "d.sestado, "
                        + "s.nombre, "
                        + "d.estatus, "
                        + "e.nombre, "
                        + "d.sestado_extra, "
                        + "sx.nombre, "
                        + "d.estatus_extra, "
                        + "ex.nombre, "
                        + "d.nombre "
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
                    nombre_deudor_flow = rs.getString(9);
                }
                rs.close();
                stmt.close();

                cadenasql = "update deudor set "
                        + "sestado=" + deu_dem_med_pre.getNuevo_estado_judicial() + ", "
                        + "estatus=" + deu_dem_med_pre.getNuevo_estatus_judicial() + ", "
                        + "situacion_caso='" + driver.quitar_simbolos(deu_dem_med_pre.getSituacion_caso()) + "' "
                        + "where deudor=" + deu_dem_med_pre.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update juicio set "
                        + "procurador=" + deu_dem_med_pre.getProcurador() + ", "
                        + "juzgado=" + deu_dem_med_pre.getJuzgado() + ", "
                        + "no_juicio='" + driver.quitar_simbolos(deu_dem_med_pre.getNo_juicio()) + "', "
                        + "notificador=" + deu_dem_med_pre.getNotificador() + ", "
                        + "procuracion='" + driver.quitar_simbolos(deu_dem_med_pre.getProcuracion()) + "', "
                        + "razon_notificacion='" + driver.quitar_simbolos(deu_dem_med_pre.getRazon_notificacion()) + "' "
                        + "where deudor=" + deu_dem_med_pre.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                String juicio = "";
                cadenasql = "select j.juicio from juicio j where j.deudor=" + deu_dem_med_pre.getDeudor();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    juicio = rs.getString(1);
                }
                rs.close();
                stmt.close();

                if (driver.valido_arraigo(deu_dem_med_pre.getArraigo())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(ja.correlativo) + 1,0) from juicio_arraigo ja where ja.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_arraigo ("
                            + "juicio,"
                            + "correlativo,"
                            + "arraigo,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getArraigo()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_arraigo()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_banco(deu_dem_med_pre.getBanco())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jb.correlativo) + 1,0) from juicio_banco jb where jb.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_banco ("
                            + "juicio,"
                            + "correlativo,"
                            + "bancos,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getBanco()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_banco()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_finca(deu_dem_med_pre.getTramite_finca())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jf.correlativo) + 1,0) from juicio_finca jf where jf.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_finca ("
                            + "juicio,"
                            + "correlativo,"
                            + "finca,"
                            + "letra,"
                            + "deligenciado,"
                            + "tramite) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getFinca()) + "','"
                            + driver.quitar_simbolos(deu_dem_med_pre.getLetra_finca()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_finca()) + "','"
                            + driver.quitar_simbolos(deu_dem_med_pre.getTramite_finca()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_salario(deu_dem_med_pre.getSalario())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(js.correlativo) + 1,0) from juicio_salario js where js.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_salario ("
                            + "juicio,"
                            + "correlativo,"
                            + "salario,"
                            + "empresa,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getSalario()) + "','"
                            + driver.quitar_simbolos(deu_dem_med_pre.getEmpresa_salario()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_salario()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_vehiculo(deu_dem_med_pre.getMedida_vehiculo())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jv.correlativo) + 1,0) from juicio_vehiculo jv where jv.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_vehiculo ("
                            + "juicio,"
                            + "correlativo,"
                            + "vehiculo,"
                            + "medida,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getVehiculo()) + "','"
                            + driver.quitar_simbolos(deu_dem_med_pre.getMedida_vehiculo()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_vehiculo()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_otro(deu_dem_med_pre.getMedida_otros())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jo.correlativo) + 1,0) from juicio_otros jo where jo.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_otros ("
                            + "juicio,"
                            + "correlativo,"
                            + "otros,"
                            + "medida,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_med_pre.getOtros()) + "','"
                            + driver.quitar_simbolos(deu_dem_med_pre.getMedida_otros()) + "','"
                            + formatoDate1.format(deu_dem_med_pre.getFecha_otros()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON. ****************************
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON. ****************************
                if (!(deu_dem_med_pre.getNuevo_estado_judicial() == int_estado_judicial_actual && deu_dem_med_pre.getNuevo_estatus_judicial() == int_status_judicial_actual)) {
                    String str_sestado_judicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado s where s.sestado=" + deu_dem_med_pre.getNuevo_estado_judicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_judicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_judicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus e where e.estatus=" + deu_dem_med_pre.getNuevo_estatus_judicial();
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
                            + deu_dem_med_pre.getNuevo_estatus_judicial() + "','"
                            + str_estatus_judicial_nuevo + "','"
                            + deu_dem_med_pre.getNuevo_estado_judicial() + "','"
                            + str_sestado_judicial_nuevo + "','"
                            + int_status_judicial_actual.toString() + "','"
                            + str_status_judicial_actual + "','"
                            + int_estado_judicial_actual.toString() + "','"
                            + str_estado_judicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor_flow + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (CARGA MASIVA MEDIDAS PRECAUTORIAS).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva medidas precautorias demanda." + "',"
                    + "17" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga masiva medidas precautorias exitosa.";
        } catch (Exception ex) {
            try {
                conn.rollback();
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Medidas_Precautorias): " + ex.toString());
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Medidas_Precautorias - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    public String carga_masiva_deligenciar_medidas(
            Integer usuario_sys, 
            String archivo, 
            String poolConexion) {
        
        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            conn.setAutoCommit(false);

            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                XSSFCell deudor = row.getCell(0);
                XSSFCell nuevo_estado_judicial = row.getCell(11);
                XSSFCell nuevo_estatus_judicial = row.getCell(12);
                XSSFCell situacion_caso = row.getCell(13);
                XSSFCell procuracion = row.getCell(14);
                XSSFCell juzgado = row.getCell(15);
                XSSFCell no_juicio = row.getCell(16);
                XSSFCell notificador = row.getCell(17);
                XSSFCell procurador = row.getCell(18);
                XSSFCell razon_notificacion = row.getCell(19);
                XSSFCell arraigo = row.getCell(20);
                XSSFCell fecha_arraigo = row.getCell(21);
                XSSFCell banco = row.getCell(22);
                XSSFCell fecha_banco = row.getCell(23);
                XSSFCell finca = row.getCell(24);
                XSSFCell letra_finca = row.getCell(25);
                XSSFCell fecha_finca = row.getCell(26);
                XSSFCell tramite_finca = row.getCell(27);
                XSSFCell salario = row.getCell(28);
                XSSFCell empresa_salario = row.getCell(29);
                XSSFCell fecha_salario = row.getCell(30);
                XSSFCell vehiculo = row.getCell(31);
                XSSFCell medida_vehiculo = row.getCell(32);
                XSSFCell fecha_vehiculo = row.getCell(33);
                XSSFCell otros = row.getCell(34);
                XSSFCell medida_otros = row.getCell(35);
                XSSFCell fecha_otros = row.getCell(36);

                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estado_judicial = 0;
                try {
                    db_nuevo_estado_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estado_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estado judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_nuevo_estatus_judicial = 0;
                try {
                    db_nuevo_estatus_judicial = Integer.parseInt(formatoInteger.format(Double.parseDouble(nuevo_estatus_judicial.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Nuevo estatus judicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_situacion_caso = "";
                if (situacion_caso != null) {
                    db_situacion_caso = situacion_caso.toString().trim();
                    if (db_situacion_caso.equals("")) {
                        db_situacion_caso = "-";
                    }
                } else {
                    db_situacion_caso = "-";
                }

                String db_procuracion = "";
                if (procuracion != null) {
                    db_procuracion = procuracion.toString().trim();
                    if (db_procuracion.equals("")) {
                        db_procuracion = "-";
                    }
                } else {
                    db_procuracion = "-";
                }

                Integer db_juzgado = 0;
                try {
                    db_juzgado = Integer.parseInt(formatoInteger.format(Double.parseDouble(juzgado.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Juzgado en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_no_juicio = "";
                if (no_juicio != null) {
                    db_no_juicio = no_juicio.toString().trim();
                    if (db_no_juicio.equals("")) {
                        db_no_juicio = "-";
                    }
                } else {
                    db_no_juicio = "-";
                }

                Integer db_notificador = 0;
                try {
                    db_notificador = Integer.parseInt(formatoInteger.format(Double.parseDouble(notificador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Notificador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_procurador = 0;
                try {
                    db_procurador = Integer.parseInt(formatoInteger.format(Double.parseDouble(procurador.toString().trim())));
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Procurador en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                String db_razon_notificacion = "";
                if (razon_notificacion != null) {
                    db_razon_notificacion = razon_notificacion.toString().trim();
                    if (db_razon_notificacion.equals("")) {
                        db_razon_notificacion = "-";
                    }
                } else {
                    db_razon_notificacion = "-";
                }

                String db_arraigo = "";
                if (arraigo != null) {
                    db_arraigo = arraigo.toString().trim();
                    if (db_arraigo.equals("")) {
                        db_arraigo = "-";
                    }
                } else {
                    db_arraigo = "-";
                }

                Date db_fecha_arraigo = new Date(1900, 0, 1);
                if (fecha_arraigo != null) {
                    try {
                        db_fecha_arraigo = fecha_arraigo.getDateCellValue();
                        formatoDate.format(db_fecha_arraigo);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha arraigo en la linea: " + linea_error);
                }

                String db_banco = "";
                if (banco != null) {
                    db_banco = banco.toString().trim();
                    if (db_banco.equals("")) {
                        db_banco = "-";
                    }
                } else {
                    db_banco = "-";
                }

                Date db_fecha_banco = new Date(1900, 0, 1);
                if (fecha_banco != null) {
                    try {
                        db_fecha_banco = fecha_banco.getDateCellValue();
                        formatoDate.format(db_fecha_banco);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha banco en la linea: " + linea_error);
                }

                String db_finca = "";
                if (finca != null) {
                    db_finca = finca.toString().trim();
                    if (db_finca.equals("")) {
                        db_finca = "-";
                    }
                } else {
                    db_finca = "-";
                }

                String db_letra_finca = "";
                if (letra_finca != null) {
                    db_letra_finca = letra_finca.toString().trim();
                    if (db_letra_finca.equals("")) {
                        db_letra_finca = "-";
                    }
                } else {
                    db_letra_finca = "-";
                }

                Date db_fecha_finca = new Date(1900, 0, 1);
                if (fecha_finca != null) {
                    try {
                        db_fecha_finca = fecha_finca.getDateCellValue();
                        formatoDate.format(db_fecha_finca);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha finca en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha finca en la linea: " + linea_error);
                }

                String db_tramite_finca = "";
                if (tramite_finca != null) {
                    db_tramite_finca = tramite_finca.toString().trim();
                    if (db_tramite_finca.equals("")) {
                        db_tramite_finca = "-";
                    }
                } else {
                    db_tramite_finca = "-";
                }

                String db_salario = "";
                if (salario != null) {
                    db_salario = salario.toString().trim();
                    if (db_salario.equals("")) {
                        db_salario = "-";
                    }
                } else {
                    db_salario = "-";
                }

                String db_empresa_salario = "";
                if (empresa_salario != null) {
                    db_empresa_salario = empresa_salario.toString().trim();
                    if (db_empresa_salario.equals("")) {
                        db_empresa_salario = "-";
                    }
                } else {
                    db_empresa_salario = "-";
                }

                Date db_fecha_salario = new Date(1900, 0, 1);
                if (fecha_salario != null) {
                    try {
                        db_fecha_salario = fecha_salario.getDateCellValue();
                        formatoDate.format(db_fecha_salario);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha salario en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha salario en la linea: " + linea_error);
                }

                String db_vehiculo = "";
                if (vehiculo != null) {
                    db_vehiculo = vehiculo.toString().trim();
                    if (db_vehiculo.equals("")) {
                        db_vehiculo = "-";
                    }
                } else {
                    db_vehiculo = "-";
                }

                String db_medida_vehiculo = "";
                if (medida_vehiculo != null) {
                    db_medida_vehiculo = medida_vehiculo.toString().trim();
                    if (db_medida_vehiculo.equals("")) {
                        db_medida_vehiculo = "-";
                    }
                } else {
                    db_medida_vehiculo = "-";
                }

                Date db_fecha_vehiculo = new Date(1900, 0, 1);
                if (fecha_vehiculo != null) {
                    try {
                        db_fecha_vehiculo = fecha_vehiculo.getDateCellValue();
                        formatoDate.format(db_fecha_vehiculo);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha vehículo en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha vehículo en la linea: " + linea_error);
                }

                String db_otros = "";
                if (otros != null) {
                    db_otros = otros.toString().trim();
                    if (db_otros.equals("")) {
                        db_otros = "-";
                    }
                } else {
                    db_otros = "-";
                }

                String db_medida_otros = "";
                if (medida_otros != null) {
                    db_medida_otros = medida_otros.toString().trim();
                    if (db_medida_otros.equals("")) {
                        db_medida_otros = "-";
                    }
                } else {
                    db_medida_otros = "-";
                }

                Date db_fecha_otros = new Date(1900, 0, 1);
                if (fecha_otros != null) {
                    try {
                        db_fecha_otros = fecha_otros.getDateCellValue();
                        formatoDate.format(db_fecha_otros);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha otros en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha otros en la linea: " + linea_error);
                }

                Deudores_Demanda_Deligenciar_Medidas deu_dem_dil_med = new Deudores_Demanda_Deligenciar_Medidas(
                        db_deudor,
                        db_nuevo_estado_judicial,
                        db_nuevo_estatus_judicial,
                        db_situacion_caso,
                        db_procuracion,
                        db_juzgado,
                        db_no_juicio,
                        db_notificador,
                        db_procurador,
                        db_razon_notificacion,
                        db_arraigo,
                        db_fecha_arraigo,
                        db_banco,
                        db_fecha_banco,
                        db_finca,
                        db_letra_finca,
                        db_fecha_finca,
                        db_tramite_finca,
                        db_salario,
                        db_empresa_salario,
                        db_fecha_salario,
                        db_vehiculo,
                        db_medida_vehiculo,
                        db_fecha_vehiculo,
                        db_otros,
                        db_medida_otros,
                        db_fecha_otros);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL. ****************************
                Integer int_estado_judicial_actual = 0;
                String str_estado_judicial_actual = "";
                Integer int_status_judicial_actual = 0;
                String str_status_judicial_actual = "";
                Integer int_estado_extrajudicial_actual = 0;
                String str_estado_extrajudicial_actual = "";
                Integer int_status_extrajudicial_actual = 0;
                String str_status_extrajudicial_actual = "";
                String nombre_deudor_flow = "";
                String cadenasql = "select "
                        + "d.sestado, "
                        + "s.nombre, "
                        + "d.estatus, "
                        + "e.nombre, "
                        + "d.sestado_extra, "
                        + "sx.nombre, "
                        + "d.estatus_extra, "
                        + "ex.nombre, "
                        + "d.nombre "
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
                    nombre_deudor_flow = rs.getString(9);
                }
                rs.close();
                stmt.close();

                cadenasql = "update deudor set "
                        + "sestado=" + deu_dem_dil_med.getNuevo_estado_judicial() + ", "
                        + "estatus=" + deu_dem_dil_med.getNuevo_estatus_judicial() + ", "
                        + "situacion_caso='" + driver.quitar_simbolos(deu_dem_dil_med.getSituacion_caso()) + "' "
                        + "where deudor=" + deu_dem_dil_med.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                cadenasql = "update juicio set "
                        + "procurador=" + deu_dem_dil_med.getProcurador() + ", "
                        + "juzgado=" + deu_dem_dil_med.getJuzgado() + ", "
                        + "no_juicio='" + driver.quitar_simbolos(deu_dem_dil_med.getNo_juicio()) + "', "
                        + "notificador=" + deu_dem_dil_med.getNotificador() + ", "
                        + "procuracion='" + driver.quitar_simbolos(deu_dem_dil_med.getProcuracion()) + "', "
                        + "razon_notificacion='" + driver.quitar_simbolos(deu_dem_dil_med.getRazon_notificacion()) + "' "
                        + "where deudor=" + deu_dem_dil_med.getDeudor();
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();

                String juicio = "";
                cadenasql = "select j.juicio from juicio j where j.deudor=" + deu_dem_dil_med.getDeudor();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    juicio = rs.getString(1);
                }
                rs.close();
                stmt.close();

                if (driver.valido_arraigo(deu_dem_dil_med.getArraigo())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(ja.correlativo) + 1,0) from juicio_arraigo ja where ja.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_arraigo ("
                            + "juicio,"
                            + "correlativo,"
                            + "arraigo,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getArraigo()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_arraigo()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_banco(deu_dem_dil_med.getBanco())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jb.correlativo) + 1,0) from juicio_banco jb where jb.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_banco ("
                            + "juicio,"
                            + "correlativo,"
                            + "bancos,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getBanco()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_banco()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_finca(deu_dem_dil_med.getTramite_finca())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jf.correlativo) + 1,0) from juicio_finca jf where jf.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_finca ("
                            + "juicio,"
                            + "correlativo,"
                            + "finca,"
                            + "letra,"
                            + "deligenciado,"
                            + "tramite) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getFinca()) + "','"
                            + driver.quitar_simbolos(deu_dem_dil_med.getLetra_finca()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_finca()) + "','"
                            + driver.quitar_simbolos(deu_dem_dil_med.getTramite_finca()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_salario(deu_dem_dil_med.getSalario())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(js.correlativo) + 1,0) from juicio_salario js where js.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_salario ("
                            + "juicio,"
                            + "correlativo,"
                            + "salario,"
                            + "empresa,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getSalario()) + "','"
                            + driver.quitar_simbolos(deu_dem_dil_med.getEmpresa_salario()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_salario()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_vehiculo(deu_dem_dil_med.getMedida_vehiculo())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jv.correlativo) + 1,0) from juicio_vehiculo jv where jv.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_vehiculo ("
                            + "juicio,"
                            + "correlativo,"
                            + "vehiculo,"
                            + "medida,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getVehiculo()) + "','"
                            + driver.quitar_simbolos(deu_dem_dil_med.getMedida_vehiculo()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_vehiculo()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                if (driver.valido_otro(deu_dem_dil_med.getMedida_otros())) {
                    String correlativo = "";
                    cadenasql = "select ifnull(max(jo.correlativo) + 1,0) from juicio_otros jo where jo.juicio = " + juicio;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        correlativo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    cadenasql = "insert into juicio_otros ("
                            + "juicio,"
                            + "correlativo,"
                            + "otros,"
                            + "medida,"
                            + "deligenciado) values ("
                            + juicio + ","
                            + correlativo + ",'"
                            + driver.quitar_simbolos(deu_dem_dil_med.getOtros()) + "','"
                            + driver.quitar_simbolos(deu_dem_dil_med.getMedida_otros()) + "','"
                            + formatoDate1.format(deu_dem_dil_med.getFecha_otros()) + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON. ****************************
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON. ****************************
                if (!(deu_dem_dil_med.getNuevo_estado_judicial() == int_estado_judicial_actual && deu_dem_dil_med.getNuevo_estatus_judicial() == int_status_judicial_actual)) {
                    String str_sestado_judicial_nuevo = "";
                    cadenasql = "select s.nombre from sestado s where s.sestado=" + deu_dem_dil_med.getNuevo_estado_judicial();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while (rs.next()) {
                        str_sestado_judicial_nuevo = rs.getString(1);
                    }
                    rs.close();
                    stmt.close();

                    String str_estatus_judicial_nuevo = "";
                    cadenasql = "select e.nombre from estatus e where e.estatus=" + deu_dem_dil_med.getNuevo_estatus_judicial();
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
                            + deu_dem_dil_med.getNuevo_estatus_judicial() + "','"
                            + str_estatus_judicial_nuevo + "','"
                            + deu_dem_dil_med.getNuevo_estado_judicial() + "','"
                            + str_sestado_judicial_nuevo + "','"
                            + int_status_judicial_actual.toString() + "','"
                            + str_status_judicial_actual + "','"
                            + int_estado_judicial_actual.toString() + "','"
                            + str_estado_judicial_actual + "','"
                            + deudor.toString() + "','"
                            + nombre_deudor_flow + "','"
                            + usuario_sys + "','"
                            + usuario_nombre + " (CARGA MASIVA DILIGENCIAR MEDIDAS).')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva diligenciar medidas precautorias demanda." + "',"
                    + "18" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga masiva diligenciar medidas precautorias exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deligenciar_Medidas): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Masiva_Deligenciar_Medidas - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

}
