package com.lexcom;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import jcifs.smb.SmbFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebService(serviceName = "ServiciosLexcom")
public class ServiciosLexcom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param cadenasql
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Reporte")
    public String[][] Reporte(
            @WebParam(name = "cadenasql") String cadenasql,
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado;
        Integer filas = 0;
        Integer columnas = 0;

        //Establece conexcion con la base de datos.
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Determina numero de filas y columnas de la consulta.
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            ResultSetMetaData metadatos = rs.getMetaData();
            columnas = metadatos.getColumnCount();
            while (rs.next()) {
                filas++;
            }
            resultado = new String[filas + 1][columnas];

            //Agrega los nombre de las columnas a la tabla.
            Integer i = 0;
            for (Integer j = 0; j < columnas; j++) {
                resultado[i][j] = metadatos.getColumnLabel(j + 1);
            }
            rs.close();
            stmt.close();

            //Llena tabla con la informacion de la consulta.
            i = 1;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                for (Integer j = 0; j < columnas; j++) {
                    if (rs.getString(j + 1) == null) {
                        resultado[i][j] = "-";
                    } else {
                        char caracter_old = (char) 31;
                        char caracter_new = (char) 32;
                        resultado[i][j] = rs.getString(j + 1).replace(caracter_old, caracter_new);
                    }
                }
                i++;
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Reporte): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario
     * @param contrasena
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Logueo")
    public String Logueo(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "contrasena") String contrasena,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "index";

        //Establece conexcion con la base de datos.
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Verifica usuario y contraseña registrada en la base de datos.
            String cadenasql = "select u.reinicio from usuario u where u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if (rs.getString(1).equals("1")) {
                    resultado = "reinicio";
                } else {
                    resultado = "menu"; //Si el usuario es autenticado devuelve la pagina del menu.
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Logueo): " + ex.toString());
            resultado = "index"; //Si occure alguna excepcion la aplicacion no loguea al usuario.
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario
     * @param contrasena
     * @param new_contrasena
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Logueo_cambio")
    public String Logueo_cambio(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "contrasena") String contrasena,
            @WebParam(name = "new_contrasena") String new_contrasena,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = "index";

        //Establece conexion con la base de datos.
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Verifica usuario y contraseña registrada en la base de datos.
            String cadenasql = "select u.reinicio from usuario u where u.nombre='" + usuario + "' and u.contrasena='" + contrasena + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = "menu";
            }
            rs.close();
            stmt.close();

            if (resultado.endsWith("menu")) {
                cadenasql = "update usuario set "
                        + "contrasena='" + new_contrasena + "', "
                        + "reinicio=0 "
                        + "where "
                        + "nombre='" + usuario + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            } else {
                resultado = "Ingrese la contraseña antigua.";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Logueo_cambio): " + ex.toString());
            resultado = "index" + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param cadenasql
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "SqlTransaccion")
    public String SqlTransaccion(
            @WebParam(name = "cadenasql") String cadenasql,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado;

        //Establece conexcion con la base de datos.
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "1, Transacción completa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(SqlTransaccion): " + ex.toString());
                conn.rollback();
                resultado = "2," + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(SqlTransaccion - rollback): " + ex1.toString());
                resultado = "3," + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rotacion_Automatica")
    public String[][] Rotacion_Automatica(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "cartera") Integer cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        String[][] resultado = null;
        Driver driver = new Driver();

        try {
            driver.getConn(poolConexion);
            resultado = driver.rotacion_automatica(usuario_sys, cartera);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Rotacion_Automatica): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = ex.toString();
        } finally {
            driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Deudoroes")
    public String Carga_Masiva_Deudoroes(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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
                    if (!db_antiguedad.equals("ORO") && !db_antiguedad.equals("PLATA") && !db_antiguedad.equals("BRONCE")) {
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
                    if (!db_cosecha.equals("0 antes 31 dic 2008") && !db_cosecha.equals("2009 1er t") && !db_cosecha.equals("2009 2do t") && !db_cosecha.equals("2009 3er t")
                            && !db_cosecha.equals("2009 4to t") && !db_cosecha.equals("2010 1er t") && !db_cosecha.equals("2010 2do t") && !db_cosecha.equals("2010 3er t")
                            && !db_cosecha.equals("2010 4to t") && !db_cosecha.equals("2011 1er t") && !db_cosecha.equals("2011 2do t") && !db_cosecha.equals("2011 3er t")
                            && !db_cosecha.equals("2011 4to t") && !db_cosecha.equals("2012 1er t") && !db_cosecha.equals("2012 2do t") && !db_cosecha.equals("2012 3er t")
                            && !db_cosecha.equals("2012 4to t") && !db_cosecha.equals("2013 1er t") && !db_cosecha.equals("2013 2do t") && !db_cosecha.equals("2013 3er t")
                            && !db_cosecha.equals("2013 4to t") && !db_cosecha.equals("2014 1er t") && !db_cosecha.equals("2014 2do t") && !db_cosecha.equals("2014 3er t")
                            && !db_cosecha.equals("2014 4to t") && !db_cosecha.equals("2015 1er t") && !db_cosecha.equals("2015 2do t") && !db_cosecha.equals("2015 3er t")
                            && !db_cosecha.equals("2015 4to t") && !db_cosecha.equals("2016 1er t") && !db_cosecha.equals("2016 2do t") && !db_cosecha.equals("2016 3er t")
                            && !db_cosecha.equals("2016 4to t") && !db_cosecha.equals("2017 1er t") && !db_cosecha.equals("2017 2do t") && !db_cosecha.equals("2017 3er t")
                            && !db_cosecha.equals("2017 4to t") && !db_cosecha.equals("2018 1er t") && !db_cosecha.equals("2018 2do t") && !db_cosecha.equals("2018 3er t")
                            && !db_cosecha.equals("2018 4to t") && !db_cosecha.equals("2019 1er t") && !db_cosecha.equals("2019 2do t") && !db_cosecha.equals("2019 3er t")
                            && !db_cosecha.equals("2019 4to ")) {
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

                //Carga estructura DEUDORES.
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
                        db_procuracion);

                //Carga de deudor
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
                        + "intencion_pago) values ("
                        + deu_car_mas.getActor() + ",'"
                        + driver.quitar_simbolos(deu_car_mas.getMoneda()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getDpi()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNit()) + "','"
                        + formatoDate1.format(deu_car_mas.getFecha_nacimiento()) + "','"
                        + driver.quitar_simbolos(deu_car_mas.getNombre_deudor()) + "','"
                        + "Guatemalteco" + "','"
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
                        + "4" + ")";

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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva de deudores." + "',"
                    + "11" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actualizacion_Masiva_Deudores")
    public String Actualizacion_Masiva_Deudores(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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
                    if (!db_cosecha.equals("0 antes 31 dic 2008") && !db_cosecha.equals("2009 1er t") && !db_cosecha.equals("2009 2do t") && !db_cosecha.equals("2009 3er t")
                            && !db_cosecha.equals("2009 4to t") && !db_cosecha.equals("2010 1er t") && !db_cosecha.equals("2010 2do t") && !db_cosecha.equals("2010 3er t")
                            && !db_cosecha.equals("2010 4to t") && !db_cosecha.equals("2011 1er t") && !db_cosecha.equals("2011 2do t") && !db_cosecha.equals("2011 3er t")
                            && !db_cosecha.equals("2011 4to t") && !db_cosecha.equals("2012 1er t") && !db_cosecha.equals("2012 2do t") && !db_cosecha.equals("2012 3er t")
                            && !db_cosecha.equals("2012 4to t") && !db_cosecha.equals("2013 1er t") && !db_cosecha.equals("2013 2do t") && !db_cosecha.equals("2013 3er t")
                            && !db_cosecha.equals("2013 4to t") && !db_cosecha.equals("2014 1er t") && !db_cosecha.equals("2014 2do t") && !db_cosecha.equals("2014 3er t")
                            && !db_cosecha.equals("2014 4to t") && !db_cosecha.equals("2015 1er t") && !db_cosecha.equals("2015 2do t") && !db_cosecha.equals("2015 3er t")
                            && !db_cosecha.equals("2015 4to t") && !db_cosecha.equals("2016 1er t") && !db_cosecha.equals("2016 2do t") && !db_cosecha.equals("2016 3er t")
                            && !db_cosecha.equals("2016 4to t") && !db_cosecha.equals("2017 1er t") && !db_cosecha.equals("2017 2do t") && !db_cosecha.equals("2017 3er t")
                            && !db_cosecha.equals("2017 4to t") && !db_cosecha.equals("2018 1er t") && !db_cosecha.equals("2018 2do t") && !db_cosecha.equals("2018 3er t")
                            && !db_cosecha.equals("2018 4to t") && !db_cosecha.equals("2019 1er t") && !db_cosecha.equals("2019 2do t") && !db_cosecha.equals("2019 3er t")
                            && !db_cosecha.equals("2019 4to ")) {
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
                    if (!db_antiguedad.equals("ORO") && !db_antiguedad.equals("PLATA") && !db_antiguedad.equals("BRONCE")) {
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
                    throw new Exception("Error al calcular el campo Monto Inicial en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                //Carga estructura DEUDORES.
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
                        db_monto_demanda);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
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

                //ACTUALIZACION DE LA TABLA DEUDOR.
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
                        + "intencion_pago=" + deu_act_mas.getIntencion_pago() + " "
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

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Actualización masiva de deudores." + "',"
                    + "12" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Rotacion_Cartera")
    public String Carga_Rotacion_Cartera(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = null;
        Integer fila = 0;

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("Cp1252");
            Workbook workbook = Workbook.getWorkbook(new File(archivo), workbookSettings);
            Sheet sheet = workbook.getSheet(0);
            Integer filas = sheet.getRows();

            for (fila = 1; fila < filas; fila++) {
                //Lee celdas del archivo Excel.
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

                //Valida que la fila sea valida con el campo numero de caso.
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

                    //Rotar cartera.
                    String cadenasql = "update deudor set "
                            + "usuario='" + driver.quitar_simbolos(deu_rot.getGestor()) + "' "
                            + "where deudor='" + driver.quitar_simbolos(deu_rot.getDeudor()) + "'";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            //Cierra libro excel y libera memoria.
            workbook.close();
            sheet = null;
            workbook = null;

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga rotación cartera mensual." + "',"
                    + "13" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Pagos_Masivos")
    public String Carga_Pagos_Masivos(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
                XSSFCell deudor = row.getCell(0);
                XSSFCell fecha_pago = row.getCell(8);
                XSSFCell monto_pago = row.getCell(9);
                XSSFCell banco_pago = row.getCell(10);
                XSSFCell boleta_pago = row.getCell(11);

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

                //Carga estructura DEUDORES.
                Deudores_Pagos_Masivos deu_car_mas = new Deudores_Pagos_Masivos(
                        db_deudor,
                        db_fecha_pago,
                        db_monto_pago,
                        db_banco_pago,
                        db_boleta_pago);

                //Cargar pagos al sistema.
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
                        + "Pago carga masiva." + "',"
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva de pagos." + "',"
                    + "14" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Presentar_Demanda")
    public String Carga_Masiva_Presentar_Demanda(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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

                //Carga estructura DEUDORES.
                Deudores_Demanda_Presentar_Demanda deu_dem_pre_dem = new Deudores_Demanda_Presentar_Demanda(
                        db_deudor,
                        db_nuevo_estado_judicial,
                        db_nuevo_estatus_judicial,
                        db_situacion_caso,
                        db_procuracion);

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
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

                //Modificación de juicios en Presentar Demanda.
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

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva presentar demanda." + "',"
                    + "15" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Caratula")
    public String Carga_Masiva_Caratula(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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

                //Carga estructura CARATTULA.
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

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
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

                //Modificación de juicios en Caratula.
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

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva carátula demanda." + "',"
                    + "16" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Medidas_Precautorias")
    public String Carga_Masiva_Medidas_Precautorias(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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

                //Carga estructura DEUDORES.
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

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
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

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva medidas precautorias demanda." + "',"
                    + "17" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Masiva_Deligenciar_Medidas")
    public String Carga_Masiva_Deligenciar_Medidas(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
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

                //Carga estructura DEUDORES.
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

                // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
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

                // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
                String usuario_nombre = "";
                cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    usuario_nombre = rs.getString(1);
                }
                rs.close();
                stmt.close();

                // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
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

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva diligenciar medidas precautorias demanda." + "',"
                    + "18" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param nombre_completo_d
     * @param nombre_d
     * @param contrasena_d
     * @param recontrasena_d
     * @param descripcion_d
     * @param gestor_d
     * @param procurador_d
     * @param asistente_d
     * @param digitador_d
     * @param investigador_d
     * @param tipo_usuario_d
     * @param reinicio
     * @param rol
     * @param usuario_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Insertar")
    public String Usuario_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_completo_d") String nombre_completo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "contrasena_d") String contrasena_d,
            @WebParam(name = "recontrasena_d") String recontrasena_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "gestor_d") String gestor_d,
            @WebParam(name = "procurador_d") String procurador_d,
            @WebParam(name = "asistente_d") String asistente_d,
            @WebParam(name = "digitador_d") String digitador_d,
            @WebParam(name = "investigador_d") String investigador_d,
            @WebParam(name = "tipo_usuario_d") Integer tipo_usuario_d,
            @WebParam(name = "reinicio") Integer reinicio,
            @WebParam(name = "rol") Integer rol,
            @WebParam(name = "usuario_corporacion") String[] usuario_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into usuario (nombre_completo,nombre,contrasena,estado,descripcion,gestor,procurador,asistente,digitador,investigador,tipo_usuario,rol) values ('"
                    + nombre_completo_d + "','"
                    + nombre_d + "','"
                    + contrasena_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "','"
                    + gestor_d + "','"
                    + procurador_d + "','"
                    + asistente_d + "','"
                    + digitador_d + "','"
                    + investigador_d + "','"
                    + tipo_usuario_d + "',"
                    + rol + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select max(u.usuario) from usuario u";
            String usuario_temp = "select max(u.usuario) from usuario u";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_temp = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m where m.tipo=1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                cadenasql = "insert into permiso_usuario (usuario,menu,nuevo,modificar,eliminar,activar,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO','NO','NO','NO','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m where m.tipo=2";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                cadenasql = "insert into permiso_usuario_uno (usuario,menu,ver) values ('" + usuario_temp + "','" + rs.getString(1) + "','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();
            
            for(Integer i=0; i < usuario_corporacion.length; i++) {
                cadenasql = "select c.cooperacion from cooperacion c where c.nombre='" + usuario_corporacion[i] + "'";
                System.out.println(cadenasql);
                Integer id_corporacion = 0;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    id_corporacion = rs.getInt(1);
                }
                rs.close();
                stmt.close();
                
                cadenasql = "insert into usuario_corporacion (usuario, corporacion) values ("
                        + usuario_temp + "," 
                        + id_corporacion + ")";
                stmt = conn.createStatement();
                System.out.println(cadenasql);
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre_Completo: " + nombre_completo_d + "|Nombre: " + nombre_d + "|Contraseña: " + contrasena_d + "|Descripcion: " + descripcion_d + "|Gestor: " + gestor_d + "|Procurador: " + procurador_d + "|Asistente:" + asistente_d + "|Digitador: " + digitador_d + "|Investigador:" + investigador_d + "|Tipo Usuario:" + tipo_usuario_d + "|Rol:" + rol + "',"
                    + "29" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_usuario
     * @param nombre_completo_d
     * @param nombre_d
     * @param contrasena_d
     * @param descripcion_d
     * @param gestor_d
     * @param procurador_d
     * @param asistente_d
     * @param digitador_d
     * @param investigador_d
     * @param tipo_usuario_d
     * @param reinicio
     * @param rol
     * @param usuario_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Modificar")
    public String Usuario_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "nombre_completo_d") String nombre_completo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "contrasena_d") String contrasena_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "gestor_d") String gestor_d,
            @WebParam(name = "procurador_d") String procurador_d,
            @WebParam(name = "asistente_d") String asistente_d,
            @WebParam(name = "digitador_d") String digitador_d,
            @WebParam(name = "investigador_d") String investigador_d,
            @WebParam(name = "tipo_usuario_d") Integer tipo_usuario_d,
            @WebParam(name = "reinicio") Integer reinicio,
            @WebParam(name = "rol") Integer rol,
            @WebParam(name = "usuario_corporacion") String[] usuario_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";
        String cadenasql = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            cadenasql = "update usuario set "
                    + "nombre_completo='" + nombre_completo_d + "', "
                    + "nombre='" + nombre_d + "', "
                    + "contrasena='" + contrasena_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "gestor='" + gestor_d + "', "
                    + "procurador='" + procurador_d + "', "
                    + "asistente='" + asistente_d + "', "
                    + "digitador='" + digitador_d + "', "
                    + "investigador='" + investigador_d + "', "
                    + "tipo_usuario='" + tipo_usuario_d + "', "
                    + "reinicio=" + reinicio + ", "
                    + "rol=" + rol + "  "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            cadenasql = "delete from usuario_corporacion where usuario=" + id_usuario;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            for(Integer i=0; i < usuario_corporacion.length; i++) {
                cadenasql = "select c.cooperacion from cooperacion c where c.nombre='" + usuario_corporacion[i] + "'";
                Integer id_corporacion = 0;
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    id_corporacion = rs.getInt(1);
                }
                rs.close();
                stmt.close();
                
                cadenasql = "insert into usuario_corporacion (usuario, corporacion) values ("
                        + id_usuario + "," 
                        + id_corporacion + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Usuario: " + id_usuario + "|Nombre_Completo: " + nombre_completo_d + "|Nombre: " + nombre_d + "|Contraseña: " + contrasena_d + "|Descripcion: " + descripcion_d + "|Gestor: " + gestor_d + "|Procurador: " + procurador_d + "|Asistente:" + asistente_d + "|Digitador: " + digitador_d + "|Investigador:" + investigador_d + "|Tipo Usuario:" + tipo_usuario_d + "|Rol :" + rol + "|Reinicio:" + reinicio + "',"
                    + "30 )";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString() + " " + cadenasql;
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_usuario
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Eliminar")
    public String Usuario_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update usuario set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "USUARIO: " + id_usuario + "',"
                    + "31" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_usuario
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Usuario_Activar")
    public String Usuario_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update usuario set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "USUARIO: " + id_usuario + "',"
                    + "32" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Usuario activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Usuario_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Insertar")
    public String Corporacion_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into cooperacion (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "33" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Corporación registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_corporacion
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Modificar")
    public String Corporacion_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cooperacion set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where cooperacion=" + id_corporacion;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Corporación: " + id_corporacion + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "34" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Corporación modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Eliminar")
    public String Corporacion_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cooperacion set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where cooperacion=" + id_corporacion;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CORPORACIÓN: " + id_corporacion + "',"
                    + "35" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Corporación eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_corporacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Corporacion_Activar")
    public String Corporacion_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_corporacion") Integer id_corporacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cooperacion set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where cooperacion=" + id_corporacion;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CORPORACIÓN: " + id_corporacion + "',"
                    + "36" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Corporación activada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Corporacion_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param nit_d
     * @param telefono_d
     * @param descripcion_d
     * @param corporacion_d
     * @param digitalizados_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Insertar")
    public String Actor_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "telefono_d") String telefono_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "corporacion_d") String corporacion_d,
            @WebParam(name = "digitalizados_d") String digitalizados_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String id_corporacion = "";
            String cadenasql = "select c.cooperacion from cooperacion c where c.nombre = '" + corporacion_d + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                id_corporacion = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "insert into actor (nombre,nit,telefono,estado,descripcion,cooperacion,digitalizados) values ('"
                    + nombre_d + "','"
                    + nit_d + "','"
                    + telefono_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "','"
                    + id_corporacion + "','"
                    + digitalizados_d + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + " Nombre: " + nombre_d + " Nit: " + nit_d + " telefono: " + telefono_d + " descripcion: " + descripcion_d + " corporación:" + corporacion_d + " digitalizados:" + digitalizados_d + "',"
                    + "37" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_actor
     * @param nombre_d
     * @param nit_d
     * @param telefono_d
     * @param descripcion_d
     * @param corporacion_d
     * @param digitalizados_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Modificar")
    public String Actor_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "telefono_d") String telefono_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "corporacion_d") String corporacion_d,
            @WebParam(name = "digitalizados_d") String digitalizados_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String id_corporacion = "";
            String cadenasql = "select c.cooperacion from cooperacion c where c.nombre = '" + corporacion_d + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                id_corporacion = rs.getString(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "update actor set "
                    + "nombre='" + nombre_d + "', "
                    + "nit='" + nit_d + "', "
                    + "telefono='" + telefono_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "cooperacion='" + id_corporacion + "', "
                    + "digitalizados='" + digitalizados_d + "' "
                    + "where actor=" + id_actor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Actor: " + id_actor + " Nombre: " + nombre_d + " Nit: " + nit_d + " telefono: " + telefono_d + " descripcion: " + descripcion_d + " corporación:" + corporacion_d + " digitalizados:" + digitalizados_d + "',"
                    + "38" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_actor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Eliminar")
    public String Actor_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update actor set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where actor=" + id_actor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ACTOR: " + id_actor + "',"
                    + "39" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_actor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Actor_Activar")
    public String Actor_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_actor") Integer id_actor,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update actor set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where actor=" + id_actor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ACTOR: " + id_actor + "',"
                    + "40" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Actor activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Actor_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param actor_d
     * @param moneda_d
     * @param dpi_d
     * @param nit_d
     * @param fecha_nacimiento_d
     * @param nombre_d
     * @param nacionalidad_d
     * @param telefono_casa_d
     * @param telefono_celular_d
     * @param direccion_d
     * @param zona_d
     * @param pais_d
     * @param departamento_d
     * @param sexo_d
     * @param estado_civil_d
     * @param fecha_ingreso_d
     * @param profesion_d
     * @param correo_electronico_d
     * @param lugar_trabajo_d
     * @param direccion_trabajo_d
     * @param telefono_trabajo_d
     * @param monto_inicial_d
     * @param gestor_d
     * @param sestado_d
     * @param estatus_d
     * @param no_cuenta_d
     * @param garantia_d
     * @param cargado_d
     * @param estado_d
     * @param descripcion_d
     * @param codigo_contactabilidad_d
     * @param caso_d
     * @param PDF_d
     * @param INV_d
     * @param MAYCOM_d
     * @param NITS_d
     * @param cosecha_d
     * @param no_cuenta_otro_d
     * @param descripcion_adicional_d
     * @param fecha_recepcion_d
     * @param anticipo_d
     * @param antiguedad_d
     * @param saldo_d
     * @param convenio_pactado_d
     * @param cuota_convenio_d
     * @param costas_pagadas_d
     * @param situacion_caso_d
     * @param opcion_proximo_pago_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Insertar")
    public String Deudor_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "actor_d") Integer actor_d,
            @WebParam(name = "moneda_d") String moneda_d,
            @WebParam(name = "dpi_d") String dpi_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "fecha_nacimiento_d") Calendar fecha_nacimiento_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nacionalidad_d") String nacionalidad_d,
            @WebParam(name = "telefono_casa_d") String telefono_casa_d,
            @WebParam(name = "telefono_celular_d") String telefono_celular_d,
            @WebParam(name = "direccion_d") String direccion_d,
            @WebParam(name = "zona_d") Integer zona_d,
            @WebParam(name = "pais_d") String pais_d,
            @WebParam(name = "departamento_d") String departamento_d,
            @WebParam(name = "sexo_d") String sexo_d,
            @WebParam(name = "estado_civil_d") String estado_civil_d,
            @WebParam(name = "fecha_ingreso_d") Calendar fecha_ingreso_d,
            @WebParam(name = "profesion_d") String profesion_d,
            @WebParam(name = "correo_electronico_d") String correo_electronico_d,
            @WebParam(name = "lugar_trabajo_d") String lugar_trabajo_d,
            @WebParam(name = "direccion_trabajo_d") String direccion_trabajo_d,
            @WebParam(name = "telefono_trabajo_d") String telefono_trabajo_d,
            @WebParam(name = "monto_inicial_d") Double monto_inicial_d,
            @WebParam(name = "gestor_d") Integer gestor_d,
            @WebParam(name = "sestado_d") Integer sestado_d,
            @WebParam(name = "estatus_d") Integer estatus_d,
            @WebParam(name = "no_cuenta_d") String no_cuenta_d,
            @WebParam(name = "garantia_d") Integer garantia_d,
            @WebParam(name = "cargado_d") String cargado_d,
            @WebParam(name = "estado_d") String estado_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "codigo_contactabilidad_d") Integer codigo_contactabilidad_d,
            @WebParam(name = "caso_d") Integer caso_d,
            @WebParam(name = "PDF_d") String PDF_d,
            @WebParam(name = "INV_d") String INV_d,
            @WebParam(name = "MAYCOM_d") String MAYCOM_d,
            @WebParam(name = "NITS_d") String NITS_d,
            @WebParam(name = "cosecha_d") String cosecha_d,
            @WebParam(name = "no_cuenta_otro_d") String no_cuenta_otro_d,
            @WebParam(name = "descripcion_adicional_d") String descripcion_adicional_d,
            @WebParam(name = "fecha_recepcion_d") Calendar fecha_recepcion_d,
            @WebParam(name = "anticipo_d") String anticipo_d,
            @WebParam(name = "antiguedad_d") String antiguedad_d,
            @WebParam(name = "saldo_d") Double saldo_d,
            @WebParam(name = "convenio_pactado_d") String convenio_pactado_d,
            @WebParam(name = "cuota_convenio_d") Double cuota_convenio_d,
            @WebParam(name = "costas_pagadas_d") String costas_pagadas_d,
            @WebParam(name = "situacion_caso_d") String situacion_caso_d,
            @WebParam(name = "opcion_proximo_pago_d") String opcion_proximo_pago_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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
                    + "opcion_proximo_pago) values ('"
                    + actor_d + "','"
                    + moneda_d + "','"
                    + dpi_d + "','"
                    + nit_d + "','"
                    + fecha_nac_d + "','"
                    + nombre_d + "','"
                    + nacionalidad_d + "','"
                    + telefono_casa_d + "','"
                    + telefono_celular_d + "','"
                    + direccion_d + "','"
                    + zona_d + "','"
                    + pais_d + "','"
                    + departamento_d + "','"
                    + sexo_d + "','"
                    + estado_civil_d + "','"
                    + fecha_ing_d + "','"
                    + profesion_d + "','"
                    + correo_electronico_d + "','"
                    + lugar_trabajo_d + "','"
                    + direccion_trabajo_d + "','"
                    + telefono_trabajo_d + "','"
                    + monto_inicial_d + "','"
                    + gestor_d + "','"
                    + sestado_d + "','"
                    + estatus_d + "','"
                    + no_cuenta_d + "','"
                    + garantia_d + "','"
                    + cargado_d + "','"
                    + estado_d + "','"
                    + descripcion_d + "','"
                    + codigo_contactabilidad_d.toString() + "','"
                    + caso_d + "','"
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
                    + "CURRENT_DATE()" + ",'"
                    + "0.00" + "','"
                    + saldo_d + "','"
                    + convenio_pactado_d + "','"
                    + cuota_convenio_d + "','"
                    + costas_pagadas_d + "','"
                    + situacion_caso_d + "','"
                    + opcion_proximo_pago_d + "')";
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

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Actor: " + actor_d + " Moneda: " + moneda_d + " dpi:" + dpi_d + " nit:" + nit_d + " caso:" + caso_d.toString() + " PDF: " + PDF_d + " INV: " + INV_d + " MAYCOM: " + MAYCOM_d + " NITS: " + NITS_d + " cosecha: " + cosecha_d + "',"
                    + "41" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_deudor
     * @param actor_d
     * @param moneda_d
     * @param dpi_d
     * @param nit_d
     * @param fecha_nacimiento_d
     * @param nombre_d
     * @param nacionalidad_d
     * @param telefono_casa_d
     * @param telefono_celular_d
     * @param direccion_d
     * @param zona_d
     * @param pais_d
     * @param departamento_d
     * @param sexo_d
     * @param estado_civil_d
     * @param fecha_ingreso_d
     * @param profesion_d
     * @param correo_electronico_d
     * @param lugar_trabajo_d
     * @param direccion_trabajo_d
     * @param telefono_trabajo_d
     * @param monto_inicial_d
     * @param gestor_d
     * @param sestado_d
     * @param estatus_d
     * @param no_cuenta_d
     * @param garantia_d
     * @param cargado_d
     * @param estado_d
     * @param descripcion_d
     * @param codigo_contactabilidad_d
     * @param caso_d
     * @param PDF_d
     * @param INV_d
     * @param MAYCOM_d
     * @param NITS_d
     * @param cosecha_d
     * @param no_cuenta_otro_d
     * @param descripcion_adicional_d
     * @param fecha_recepcion_d
     * @param anticipo_d
     * @param antiguedad_d
     * @param saldo_d
     * @param convenio_pactado_d
     * @param cuota_convenio_d
     * @param costas_pagadas_d
     * @param situacion_caso_d
     * @param opcion_proximo_pago_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Modificar")
    public String Deudor_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "actor_d") Integer actor_d,
            @WebParam(name = "moneda_d") String moneda_d,
            @WebParam(name = "dpi_d") String dpi_d,
            @WebParam(name = "nit_d") String nit_d,
            @WebParam(name = "fecha_nacimiento_d") Calendar fecha_nacimiento_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "nacionalidad_d") String nacionalidad_d,
            @WebParam(name = "telefono_casa_d") String telefono_casa_d,
            @WebParam(name = "telefono_celular_d") String telefono_celular_d,
            @WebParam(name = "direccion_d") String direccion_d,
            @WebParam(name = "zona_d") Integer zona_d,
            @WebParam(name = "pais_d") String pais_d,
            @WebParam(name = "departamento_d") String departamento_d,
            @WebParam(name = "sexo_d") String sexo_d,
            @WebParam(name = "estado_civil_d") String estado_civil_d,
            @WebParam(name = "fecha_ingreso_d") Calendar fecha_ingreso_d,
            @WebParam(name = "profesion_d") String profesion_d,
            @WebParam(name = "correo_electronico_d") String correo_electronico_d,
            @WebParam(name = "lugar_trabajo_d") String lugar_trabajo_d,
            @WebParam(name = "direccion_trabajo_d") String direccion_trabajo_d,
            @WebParam(name = "telefono_trabajo_d") String telefono_trabajo_d,
            @WebParam(name = "monto_inicial_d") Double monto_inicial_d,
            @WebParam(name = "gestor_d") Integer gestor_d,
            @WebParam(name = "sestado_d") Integer sestado_d,
            @WebParam(name = "estatus_d") Integer estatus_d,
            @WebParam(name = "no_cuenta_d") String no_cuenta_d,
            @WebParam(name = "garantia_d") Integer garantia_d,
            @WebParam(name = "cargado_d") String cargado_d,
            @WebParam(name = "estado_d") String estado_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "codigo_contactabilidad_d") Integer codigo_contactabilidad_d,
            @WebParam(name = "caso_d") Integer caso_d,
            @WebParam(name = "PDF_d") String PDF_d,
            @WebParam(name = "INV_d") String INV_d,
            @WebParam(name = "MAYCOM_d") String MAYCOM_d,
            @WebParam(name = "NITS_d") String NITS_d,
            @WebParam(name = "cosecha_d") String cosecha_d,
            @WebParam(name = "no_cuenta_otro_d") String no_cuenta_otro_d,
            @WebParam(name = "descripcion_adicional_d") String descripcion_adicional_d,
            @WebParam(name = "fecha_recepcion_d") Calendar fecha_recepcion_d,
            @WebParam(name = "anticipo_d") String anticipo_d,
            @WebParam(name = "antiguedad_d") String antiguedad_d,
            @WebParam(name = "saldo_d") Double saldo_d,
            @WebParam(name = "convenio_pactado_d") String convenio_pactado_d,
            @WebParam(name = "cuota_convenio_d") Double cuota_convenio_d,
            @WebParam(name = "costas_pagadas_d") String costas_pagadas_d,
            @WebParam(name = "situacion_caso_d") String situacion_caso_d,
            @WebParam(name = "opcion_proximo_pago_d") String opcion_proximo_pago_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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
                    + "actor='" + actor_d + "', "
                    + "dpi='" + dpi_d + "', "
                    + "nit='" + nit_d + "', "
                    + "fecha_nacimiento='" + fecha_nac_d + "', "
                    + "nombre='" + nombre_d + "', "
                    + "nacionalidad='" + nacionalidad_d + "', "
                    + "telefono_casa='" + telefono_casa_d + "', "
                    + "telefono_celular='" + telefono_celular_d + "', "
                    + "direccion='" + direccion_d + "', "
                    + "zona='" + zona_d + "', "
                    + "pais='" + pais_d + "', "
                    + "departamento='" + departamento_d + "', "
                    + "sexo='" + sexo_d + "', "
                    + "estado_civil='" + estado_civil_d + "', "
                    + "moneda='" + moneda_d + "', "
                    + "fecha_ingreso='" + fecha_ing_d + "', "
                    + "profesion='" + profesion_d + "', "
                    + "correo_electronico='" + correo_electronico_d + "', "
                    + "lugar_trabajo='" + lugar_trabajo_d + "', "
                    + "direccion_trabajo='" + direccion_trabajo_d + "', "
                    + "telefono_trabajo='" + telefono_trabajo_d + "', "
                    + "monto_inicial='" + monto_inicial_d + "', "
                    + "usuario='" + gestor_d + "', "
                    + "sestado='" + sestado_d + "', "
                    + "estatus='" + estatus_d + "', "
                    + "no_cuenta='" + no_cuenta_d + "', "
                    + "garantia='" + garantia_d + "', "
                    + "cargado='" + cargado_d + "', "
                    + "descripcion='" + descripcion_d + "', "
                    + "caso='" + caso_d.toString() + "', "
                    + "PDF='" + PDF_d + "', "
                    + "INV='" + INV_d + "', "
                    + "MAYCOM='" + MAYCOM_d + "', "
                    + "NITS='" + NITS_d + "', "
                    + "cosecha='" + cosecha_d + "', "
                    + "antiguedad='" + antiguedad_d + "', "
                    + "no_cuenta_otro='" + no_cuenta_otro_d + "', "
                    + "costas_pagadas='" + costas_pagadas_d + "', "
                    + "descripcion_adicional='" + descripcion_adicional_d + "', "
                    + "convenio_pactado='" + convenio_pactado_d + "', "
                    + "cuota_convenio='" + cuota_convenio_d + "', "
                    + "fecha_recepcion='" + fecha_recep_d + "', "
                    + "situacion_caso='" + situacion_caso_d + "', "
                    + "opcion_proximo_pago='" + opcion_proximo_pago_d + "', "
                    + "saldo='" + saldo_d + "', "
                    + "anticipo='" + anticipo_d + "' "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Deudor:" + id_deudor + " Actor: " + actor_d + " Moneda: " + moneda_d + " dpi:" + dpi_d + " nit:" + nit_d + " caso:" + caso_d.toString() + " PDF: " + PDF_d + " INV: " + INV_d + " MAYCOM: " + MAYCOM_d + " NITS: " + NITS_d + " cosecha: " + cosecha_d + "',"
                    + "42" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Eliminar")
    public String Deudor_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "estado='" + "ELIMINADO" + "', "
                    + "cargado='" + "DESCARGADO" + "' "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DEUDOR: " + id_deudor + "',"
                    + "43" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Deudor_Activar")
    public String Deudor_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_deudor") Integer id_deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "estado='" + "VIGENTE" + "', "
                    + "cargado='" + "CARGADO" + "' "
                    + "where deudor=" + id_deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DEUDOR: " + id_deudor + "',"
                    + "44" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Deudor activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Deudor_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Insertar")
    public String Juzgado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into juzgado (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "45" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juzgado registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_juzgado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Modificar")
    public String Juzgado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update juzgado set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where juzgado=" + id_juzgado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Juzgado: " + id_juzgado + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "46" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juzgado modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_juzgado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Eliminar")
    public String Juzgado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update juzgado set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where juzgado=" + id_juzgado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUZGADO: " + id_juzgado + "',"
                    + "47" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juzgado eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_juzgado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juzgado_Activar")
    public String Juzgado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juzgado") Integer id_juzgado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update juzgado set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where juzgado=" + id_juzgado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUZGADO: " + id_juzgado + "',"
                    + "48" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Juzgado activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Juzgado_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Insertar")
    public String Garantia_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into garantia (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "49" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Garantía registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_garantia
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Modificar")
    public String Garantia_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update garantia set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where garantia=" + id_garantia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Garantia: " + id_garantia + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "50" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Garantía modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_garantia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Eliminar")
    public String Garantia_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update garantia set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where garantia=" + id_garantia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "GARANTÍA: " + id_garantia + "',"
                    + "51" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Garantía eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_garantia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Garantia_Activar")
    public String Garantia_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_garantia") Integer id_garantia,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update garantia set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where garantia=" + id_garantia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "GARANTÍA: " + id_garantia + "',"
                    + "52" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Garantía activada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Garantia_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Insertar")
    public String Banco_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into banco (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "53" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Banco registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_banco
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Modificar")
    public String Banco_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update banco set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where banco=" + id_banco;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Banco: " + id_banco + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "54" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Banco modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_banco
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Eliminar")
    public String Banco_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update banco set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where banco=" + id_banco;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "BANCO: " + id_banco + "',"
                    + "55" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Banco eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_banco
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Banco_Activar")
    public String Banco_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_banco") Integer id_banco,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update banco set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where banco=" + id_banco;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "BANCO: " + id_banco + "',"
                    + "56" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Banco activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Banco_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Insertar")
    public String Estado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into sestado (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "57" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado judicial registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Modificar")
    public String Estado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where sestado=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Estado: " + id_sestado + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "58" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado judicial modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Eliminar")
    public String Estado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where sestado=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + id_sestado + "',"
                    + "59" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado judicial eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Activar")
    public String Estado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where sestado=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + id_sestado + "',"
                    + "60" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado judicial activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Insertar")
    public String Status_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into estatus (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "61" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status judicial registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Modificar")
    public String Status_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where estatus=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Status: " + id_estatus + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "62" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status judicial modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Eliminar")
    public String Status_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where estatus=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "STATUS: " + id_estatus + "',"
                    + "63" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status judicial eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Status_Activar")
    public String Status_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where estatus=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "STATUS: " + id_estatus + "',"
                    + "64" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status judicial activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Status_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Insertar")
    public String Tipo_Descuento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into tipo_descuento (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "65" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Modificar")
    public String Tipo_Descuento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Tipo_Descuento: " + id_tipo_descuento + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "66" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Eliminar")
    public String Tipo_Descuento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO DESCUENTO: " + id_tipo_descuento + "',"
                    + "67" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Descuento_Activar")
    public String Tipo_Descuento_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_descuento") Integer id_tipo_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_descuento set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where tipo_descuento=" + id_tipo_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_DESCUENTO: " + id_tipo_descuento + "',"
                    + "68" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de descuento activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Descuento_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Insertar")
    public String Tipo_Aumento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into tipo_aumento (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "69" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de aumento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Modificar")
    public String Tipo_Aumento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_aumento set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where tipo_aumento=" + id_tipo_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Tipo_Aumento: " + id_tipo_aumento + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "70" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de aumento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Eliminar")
    public String Tipo_Aumento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_aumento set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where tipo_aumento=" + id_tipo_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO AUMENTO: " + id_tipo_aumento + "',"
                    + "71" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de aumento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Aumento_Activar")
    public String Tipo_Aumento_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_tipo_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_aumento set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where tipo_aumento=" + id_tipo_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_AUMENTO: " + id_tipo_aumento + "',"
                    + "72" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo de aumento activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Aumento_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param tipo_d
     * @param frase_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Insertar")
    public String Frase_Predeterminada_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "tipo_d") String tipo_d,
            @WebParam(name = "frase_d") String frase_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into frase_predeterminada (nombre,tipo,frase,estado) values ('"
                    + nombre_d + "','"
                    + tipo_d + "','"
                    + frase_d + "','"
                    + "VIGENTE" + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " tipo: " + tipo_d + " frase: " + frase_d + "',"
                    + "73" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Frase predeterminada registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param nombre_d
     * @param tipo_d
     * @param frase_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Modificar")
    public String Frase_Predeterminada_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "tipo_d") String tipo_d,
            @WebParam(name = "frase_d") String frase_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update frase_predeterminada set "
                    + "nombre='" + nombre_d + "', "
                    + "tipo='" + tipo_d + "', "
                    + "frase='" + frase_d + "' "
                    + "where frase_predeterminada=" + id_frase_predeterminada;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Predefinida_Modificar: " + id_frase_predeterminada + "Nombre: " + nombre_d + " tipo: " + tipo_d + " frase: " + frase_d + "',"
                    + "74" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Frase predeterminada modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Eliminar")
    public String Frase_Predeterminada_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update frase_predeterminada set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where frase_predeterminada=" + id_frase_predeterminada;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "FRASE_PREDETERMINADA: " + id_frase_predeterminada + "',"
                    + "75" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Frase predeterminada eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_frase_predeterminada
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Frase_Predeterminada_Activar")
    public String Frase_Predeterminada_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_frase_predeterminada,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update frase_predeterminada set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where frase_predeterminada=" + id_frase_predeterminada;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "FRASE_PREDETERMINADA: " + id_frase_predeterminada + "',"
                    + "76" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Frase predeterminada activada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Frase_Predeterminada_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param codigo_d
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Insertar")
    public String Codigo_Contactabilidad_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "codigo_d") String codigo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into codigo_contactabilidad (codigo,nombre,estado,descripcion) values ('"
                    + codigo_d + "','"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Codigo Resultado: " + codigo_d + " Nombre: " + nombre_d + " Descripción: " + descripcion_d + "',"
                    + "77" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Codigo de resultado registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param codigo_d
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Modificar")
    public String Codigo_Contactabilidad_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_codigo_contactabilidad") Integer id_codigo_contactabilidad,
            @WebParam(name = "codigo_d") String codigo_d,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update codigo_contactabilidad set "
                    + "codigo='" + codigo_d + "', "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where codigo_contactabilidad=" + id_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Codigo_Contactabilidad: " + id_codigo_contactabilidad + "Codigo: " + codigo_d + " Nombre: " + nombre_d + " Descripción: " + descripcion_d + "',"
                    + "78" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Codigo de resultado modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Eliminar")
    public String Codigo_Contactabilidad_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update codigo_contactabilidad set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where codigo_contactabilidad=" + id_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CODIGO_CONTACTABILIDAD: " + id_codigo_contactabilidad + "',"
                    + "79" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Codigo de resultado eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Codigo_Contactabilidad_Activar")
    public String Codigo_Contactabilidad_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_aumento") Integer id_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update codigo_contactabilidad set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where codigo_contactabilidad=" + id_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CODIGO_CONTACTABILIDAD: " + id_codigo_contactabilidad + "',"
                    + "80" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Codigo de resultado activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Contactabilidad_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param contacto
     * @param estado_extrajudicial
     * @param estatus_extrajudicial
     * @param estado_judicial
     * @param estatus_judicial
     * @param intencion_pago
     * @param razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Cobros_Insertar")
    public String Gestion_Cobros_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "contacto") String contacto,
            @WebParam(name = "estado_extrajudicial") Integer estado_extrajudicial,
            @WebParam(name = "estatus_extrajudicial") Integer estatus_extrajudicial,
            @WebParam(name = "estado_judicial") Integer estado_judicial,
            @WebParam(name = "estatus_judicial") Integer estatus_judicial,
            @WebParam(name = "intencion_pago") Integer intencion_pago,
            @WebParam(name = "razon_deuda") Integer razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_cobros (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion, contacto) values ("
                    + deudor + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ","
                    + usuario + ","
                    + codigo_contactabiliad + ",'"
                    + descripcion + "','"
                    + contacto + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            // **************************** ACTUALIZAR DEUDOR
            cadenasql = "update deudor set "
                    + "codigo_contactabilidad=" + codigo_contactabiliad + ", "
                    + "sestado=" + estado_judicial + ", "
                    + "estatus=" + estatus_judicial + ", "
                    + "sestado_extra=" + estado_extrajudicial + ", "
                    + "estatus_extra=" + estatus_extrajudicial + ", "
                    + "intencion_pago=" + intencion_pago + ", "
                    + "razon_deuda=" + razon_deuda + " "
                    + "where deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Cobros Insertar: Codigo Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "81" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            String nombre_deudor = "";
            cadenasql = "select d.nombre from deudor d where d.deudor=" + deudor;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                nombre_deudor = rs.getString(1);
            }
            rs.close();
            stmt.close();
            stmt.close();

            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            cadenasql = "select "
                    + "d.sestado, "
                    + "s.nombre, "
                    + "d.estatus, "
                    + "e.nombre, "
                    + "d.sestado_extra, "
                    + "sx.nombre, "
                    + "d.estatus_extra, "
                    + "ex.nombre "
                    + "from "
                    + "deudor d "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra sx on (d.sestado_extra=sx.sestado_extra) "
                    + "left join estatus_extra ex on (d.estatus_extra=ex.estatus_extra) where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                int_estado_judicial_actual = rs.getInt(1);
                str_estado_judicial_actual = rs.getString(2);
                int_status_judicial_actual = rs.getInt(3);
                str_status_judicial_actual = rs.getString(4);
                int_estado_extrajudicial_actual = rs.getInt(5);
                str_estado_extrajudicial_actual = rs.getString(6);
                int_status_extrajudicial_actual = rs.getInt(7);
                str_status_extrajudicial_actual = rs.getString(8);
            }
            rs.close();
            stmt.close();
            stmt.close();

            String nombre_usuario = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                nombre_usuario = rs.getString(1);
            }
            rs.close();
            stmt.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
            if (!(estado_judicial == int_estado_judicial_actual && estatus_judicial == int_status_judicial_actual)) {
                String str_sestado_judicial_nuevo = "";
                cadenasql = "select s.nombre from sestado s where s.sestado=" + estado_judicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                String str_estatus_judicial_nuevo = "";
                cadenasql = "select e.nombre from estatus e where e.estatus=" + estatus_judicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
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
                        + estatus_judicial + "','"
                        + str_estatus_judicial_nuevo + "','"
                        + estado_judicial + "','"
                        + str_sestado_judicial_nuevo + "','"
                        + int_status_judicial_actual.toString() + "','"
                        + str_status_judicial_actual + "','"
                        + int_estado_judicial_actual.toString() + "','"
                        + str_estado_judicial_actual + "','"
                        + deudor + "','"
                        + nombre_deudor + "','"
                        + usuario + "','"
                        + nombre_usuario + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
            if (!(estado_extrajudicial == int_estado_extrajudicial_actual && estatus_extrajudicial == int_status_extrajudicial_actual)) {
                String str_sestado_extrajudicial_nuevo = "";
                cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + estado_extrajudicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
                stmt.close();

                String str_estatus_extrajudicial_nuevo = "";
                cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + estatus_extrajudicial;
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_estatus_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();
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
                        + estatus_extrajudicial + "','"
                        + str_estatus_extrajudicial_nuevo + "','"
                        + estado_extrajudicial + "','"
                        + str_sestado_extrajudicial_nuevo + "','"
                        + int_status_extrajudicial_actual.toString() + "','"
                        + str_status_extrajudicial_actual + "','"
                        + int_estado_extrajudicial_actual.toString() + "','"
                        + str_estado_extrajudicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario + "','"
                        + nombre_usuario + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión de cobro registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Cobros_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Cobros_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Administracion_Insertar")
    public String Gestion_Administracion_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_administrativo (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                    + deudor.toString() + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + usuario.toString() + "','"
                    + codigo_contactabiliad.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Adminstración Insertar: Codigo Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "82" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión administrativa registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Administracion_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Administracion_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param usuario
     * @param codigo_contactabiliad
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Gestion_Juridico_Insertar")
    public String Gestion_Juridico_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "codigo_contactabiliad") Integer codigo_contactabiliad,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into deudor_historial_juridico (deudor, fecha, hora, usuario, codigo_contactabilidad, descripcion) values ('"
                    + deudor.toString() + "',"
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + usuario.toString() + "','"
                    + codigo_contactabiliad.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Gestion Jurídico Insertar: Código Resultado: " + codigo_contactabiliad + " Descripción: " + descripcion + "',"
                    + "83" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Gestión Jurídica registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Juridico_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Gestion_Juridico_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param banco
     * @param fecha
     * @param no_boleta
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Insertar")
    public String Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "banco") Integer banco,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_boleta") String no_boleta,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into pago (deudor, banco, fecha, no_boleta, monto, descripcion, fecha_registro) values ('"
                    + deudor.toString() + "','"
                    + banco.toString() + "','"
                    + fecha_pago + "','"
                    + no_boleta + "','"
                    + monto.toString() + "','"
                    + descripcion + "',"
                    + "CURRENT_DATE()" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Pago:= Deudor: " + deudor.toString() + " Banco: " + banco.toString() + " Fecha pago: " + fecha_pago + " No Boleta: " + no_boleta + " Monto: " + monto.toString() + " Descripcion: " + descripcion + "',"
                    + "84" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_pago
     * @param deudor
     * @param banco
     * @param fecha
     * @param no_boleta
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Modificar")
    public String Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_pago") Integer id_pago,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "banco") Integer banco,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_boleta") String no_boleta,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update pago set "
                    + "deudor='" + deudor.toString() + "', "
                    + "banco='" + banco.toString() + "', "
                    + "fecha='" + fecha_pago + "', "
                    + "no_boleta='" + no_boleta + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where pago=" + id_pago.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Pago: " + id_pago + " Deudor: " + deudor.toString() + " Banco: " + banco.toString() + " Fecha pago: " + fecha_pago + " No Boleta: " + no_boleta + " Monto: " + monto.toString() + " Descripcion: " + descripcion + "',"
                    + "85" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Pago_Eliminar")
    public String Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_pago") Integer id_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from pago where pago=" + id_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "PAGO: " + id_pago + "',"
                    + "86" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Pago eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param fecha_ingreso
     * @param fecha_pago
     * @param hora_pago
     * @param minuto_pago
     * @param estado_promesa
     * @param observacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Insertar")
    public String Promesa_Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "fecha_ingreso") Calendar fecha_ingreso,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") Integer hora_pago,
            @WebParam(name = "minuto_pago") Integer minuto_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_ingreso.get(Calendar.DATE);
            Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
            Integer ano = fecha_ingreso.get(Calendar.YEAR);
            String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_pago.get(Calendar.DATE);
            mes = fecha_pago.get(Calendar.MONTH) + 1;
            ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into promesa_pago (fecha_ingreso, fecha_pago, hora_pago, estado_promesa, observacion, monto, deudor) values ('"
                    + fecha_ingreso_t + "','"
                    + fecha_pago_t + "','"
                    + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "','"
                    + estado_promesa + "','"
                    + observacion + "','"
                    + monto + "','"
                    + deudor.toString() + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa Pago:= Deudor: " + deudor.toString() + " Fecha ingreso: " + fecha_ingreso + " Fecha pago: " + fecha_pago_t + " Hora pago: " + hora_pago.toString() + " Estado Promesa: " + estado_promesa + " Observacion: " + observacion + " Monto: " + "',"
                    + "87" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_promesa_pago
     * @param deudor
     * @param fecha_ingreso
     * @param fecha_pago
     * @param hora_pago
     * @param minuto_pago
     * @param estado_promesa
     * @param observacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Modificar")
    public String Promesa_Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "fecha_ingreso") Calendar fecha_ingreso,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") Integer hora_pago,
            @WebParam(name = "minuto_pago") Integer minuto_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_ingreso.get(Calendar.DATE);
            Integer mes = fecha_ingreso.get(Calendar.MONTH) + 1;
            Integer ano = fecha_ingreso.get(Calendar.YEAR);
            String fecha_ingreso_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_pago.get(Calendar.DATE);
            mes = fecha_pago.get(Calendar.MONTH) + 1;
            ano = fecha_pago.get(Calendar.YEAR);
            String fecha_pago_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update promesa_pago set "
                    + "deudor='" + deudor.toString() + "', "
                    + "fecha_ingreso='" + fecha_ingreso_t + "', "
                    + "fecha_pago='" + fecha_pago_t + "', "
                    + "hora_pago='" + hora_pago.toString() + ":" + minuto_pago.toString() + ":0" + "', "
                    + "estado_promesa='" + estado_promesa + "', "
                    + "observacion='" + observacion + "', "
                    + "monto='" + monto + "' "
                    + "where promesa_pago=" + id_promesa_pago.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Promesa_Pago: " + id_promesa_pago + " Deudor: " + deudor.toString() + " Fecha ingreso: " + fecha_ingreso + " Fecha pago: " + fecha_pago_t + " Hora pago: " + hora_pago.toString() + " Estado Promesa: " + estado_promesa + " Observacion: " + observacion + " Monto: " + "',"
                    + "88" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_promesa_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Promesa_Pago_Eliminar")
    public String Promesa_Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from promesa_pago where promesa_pago=" + id_promesa_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "PROMESA_PAGO: " + id_promesa_pago + "',"
                    + "89" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Promesa de pago eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Promesa_Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_juicio
     * @param deudor
     * @param juzgado
     * @param fecha
     * @param no_juicio
     * @param monto
     * @param descripcion
     * @param modelo_arraigo
     * @param modelo_banco
     * @param modelo_finca
     * @param modelo_salario
     * @param modelo_vehiculo
     * @param modelo_otros
     * @param procurador
     * @param razon_notificacion
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param procuracion
     * @param fecha_admision_demanda
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param depositario
     * @param tiempo_estimado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juicio_Modificar")
    public String Juicio_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juicio") Integer id_juicio,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "modelo_arraigo") String[][] modelo_arraigo,
            @WebParam(name = "modelo_banco") String[][] modelo_banco,
            @WebParam(name = "modelo_finca") String[][] modelo_finca,
            @WebParam(name = "modelo_salario") String[][] modelo_salario,
            @WebParam(name = "modelo_vehiculo") String[][] modelo_vehiculo,
            @WebParam(name = "modelo_otros") String[][] modelo_otros,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "fecha_admision_demanda") Calendar fecha_admision_demanda,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "depositario") String depositario,
            @WebParam(name = "tiempo_estimado") String tiempo_estimado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Juicio: " + id_juicio + " Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " monto: " + monto.toString() + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " fecha_adminision_demanda: " + fecha_admision + " deudor_notificado: " + deudor_notificado + " fecha_notificacion: " + fecha_noti + " depositario: " + depositario + " tiempo_estamado: " + tiempo_estimado + "',"
                    + "91" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_juicio
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Juicio_Eliminar")
    public String Juicio_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_juicio") Integer id_juicio,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from juicio where juicio=" + id_juicio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUICIO: " + id_juicio + "',"
                    + "92" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param tipo_descuento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Insertar")
    public String Descuento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_descuento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into descuento (deudor, tipo_descuento, fecha, monto, descripcion) values ('"
                    + deudor.toString() + "','"
                    + tipo_descuento.toString() + "','"
                    + fecha_i + "','"
                    + monto.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Descuento:= Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "93" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_descuento
     * @param deudor
     * @param tipo_descuento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Modificar")
    public String Descuento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_descuento") Integer id_descuento,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_descuento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update descuento set "
                    + "deudor='" + deudor.toString() + "', "
                    + "tipo_descuento='" + tipo_descuento.toString() + "', "
                    + "fecha='" + fecha_i + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where descuento=" + id_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Descuento: " + id_descuento + " Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "94" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_descuento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Descuento_Eliminar")
    public String Descuento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_descuento") Integer id_descuento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from descuento where descuento=" + id_descuento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "DESCUENTO: " + id_descuento + "',"
                    + "95" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Descuento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Descuento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param tipo_aumento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Insertar")
    public String Aumento_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_descuento") Integer tipo_aumento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into aumento (deudor, tipo_aumento, fecha, monto, descripcion) values ('"
                    + deudor.toString() + "','"
                    + tipo_aumento.toString() + "','"
                    + fecha_i + "','"
                    + monto.toString() + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Aumento:= Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "96" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_aumento
     * @param deudor
     * @param tipo_aumento
     * @param fecha
     * @param monto
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Modificar")
    public String Aumento_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_aumento") Integer id_aumento,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_aumento") Integer tipo_aumento,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_i = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update aumento set "
                    + "deudor='" + deudor.toString() + "', "
                    + "tipo_aumento='" + tipo_aumento.toString() + "', "
                    + "fecha='" + fecha_i + "', "
                    + "monto='" + monto + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where aumento=" + id_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Aumento: " + id_aumento + " Deudor: " + deudor + " fecha_pago: " + fecha_i + " monto: " + monto.toString() + "',"
                    + "97" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_aumento
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Aumento_Eliminar")
    public String Aumento_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_aumento") Integer id_aumento,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from aumento where aumento=" + id_aumento;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "AUMENTO: " + id_aumento + "',"
                    + "98" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Aumento_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Insertar")
    public String Fiador_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into fiador ("
                    + "deudor,"
                    + "dpi,"
                    + "nit,"
                    + "fecha_nacimiento,"
                    + "nombre,"
                    + "nacionalidad,"
                    + "telefono,"
                    + "direccion,"
                    + "zona,"
                    + "pais,"
                    + "departamento,"
                    + "sexo,"
                    + "estado_civil,"
                    + "profesion,"
                    + "correo_electronico,"
                    + "descripcion) values ('"
                    + deudor.toString() + "','"
                    + dpi + "','"
                    + nit + "','"
                    + fecha + "','"
                    + nombre + "','"
                    + nacionalidad + "','"
                    + telefono + "','"
                    + direccion + "','"
                    + zona.toString() + "','"
                    + pais + "','"
                    + departamento + "','"
                    + sexo + "','"
                    + estado_civil + "','"
                    + profesion + "','"
                    + correo_electronico + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Fiador registrado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "99" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Fiador registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_fiador
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Modificar")
    public String Fiador_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_fiador") Integer id_fiador,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update fiador set "
                    + "deudor='" + deudor + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "fecha_nacimiento='" + fecha + "', "
                    + "nombre='" + nombre + "', "
                    + "nacionalidad='" + nacionalidad + "', "
                    + "telefono='" + telefono + "', "
                    + "direccion='" + direccion + "', "
                    + "zona='" + zona + "', "
                    + "pais='" + pais + "', "
                    + "departamento='" + departamento + "', "
                    + "sexo='" + sexo + "', "
                    + "estado_civil='" + estado_civil + "', "
                    + "profesion='" + profesion + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where fiador=" + id_fiador;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Aumento: " + id_fiador + " Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "100" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Aumento modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_fiador
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Fiador_Eliminar")
    public String Fiador_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_fiador") Integer id_fiador,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from fiador where fiador=" + id_fiador;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "FIADOR: " + id_fiador + "',"
                    + "101" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Fiador eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Fiador_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Insertar")
    public String Referencia_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "insert into referencia ("
                    + "deudor,"
                    + "dpi,"
                    + "nit,"
                    + "fecha_nacimiento,"
                    + "nombre,"
                    + "nacionalidad,"
                    + "telefono,"
                    + "direccion,"
                    + "zona,"
                    + "pais,"
                    + "departamento,"
                    + "sexo,"
                    + "estado_civil,"
                    + "profesion,"
                    + "correo_electronico,"
                    + "descripcion) values ('"
                    + deudor.toString() + "','"
                    + dpi + "','"
                    + nit + "','"
                    + fecha + "','"
                    + nombre + "','"
                    + nacionalidad + "','"
                    + telefono + "','"
                    + direccion + "','"
                    + zona.toString() + "','"
                    + pais + "','"
                    + departamento + "','"
                    + sexo + "','"
                    + estado_civil + "','"
                    + profesion + "','"
                    + correo_electronico + "','"
                    + descripcion + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Referencia registrado=> Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "102" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_referencia
     * @param deudor
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param nacionalidad
     * @param telefono
     * @param direccion
     * @param zona
     * @param pais
     * @param departamento
     * @param sexo
     * @param estado_civil
     * @param profesion
     * @param correo_electronico
     * @param descripcion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Modificar")
    public String Referencia_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_referencia") Integer id_referencia,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "zona") Integer zona,
            @WebParam(name = "pais") String pais,
            @WebParam(name = "departamento") String departamento,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "estado_civil") String estado_civil,
            @WebParam(name = "profesion") String profesion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update referencia set "
                    + "deudor='" + deudor + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "fecha_nacimiento='" + fecha + "', "
                    + "nombre='" + nombre + "', "
                    + "nacionalidad='" + nacionalidad + "', "
                    + "telefono='" + telefono + "', "
                    + "direccion='" + direccion + "', "
                    + "zona='" + zona + "', "
                    + "pais='" + pais + "', "
                    + "departamento='" + departamento + "', "
                    + "sexo='" + sexo + "', "
                    + "estado_civil='" + estado_civil + "', "
                    + "profesion='" + profesion + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "descripcion='" + descripcion + "' "
                    + "where referencia=" + id_referencia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Referencia: " + id_referencia + " Deudor: " + deudor + " dpi:" + dpi + " nit:" + nit + "',"
                    + "103" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_referencia
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Referencia_Eliminar")
    public String Referencia_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_referencia") Integer id_referencia,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from referencia where referencia=" + id_referencia;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "REFERENCIA: " + id_referencia + "',"
                    + "104" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Referencia eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Referencia_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param actor
     * @param moneda
     * @param dpi
     * @param nit
     * @param fecha_nacimiento
     * @param nombre
     * @param telefono_casa
     * @param telefono_celular
     * @param direccion
     * @param fecha_recepcion
     * @param correo_electronico
     * @param lugar_trabajo
     * @param direccion_trabajo
     * @param telefono_trabajo
     * @param monto_inicial
     * @param gestor
     * @param sestado
     * @param estatus
     * @param no_cuenta
     * @param garantia
     * @param cargado
     * @param estado
     * @param PDF
     * @param INV
     * @param MAYCOM
     * @param NITS
     * @param saldo
     * @param fecha_proximo_pago
     * @param caso
     * @param convenio_pactado
     * @param cuota_convenio
     * @param no_cuenta_otro
     * @param situacion_caso
     * @param opcion_proximo_pago
     * @param anticipo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Modificar_Deudor_Expediente")
    public String Modificar_Deudor_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "actor") Integer actor,
            @WebParam(name = "moneda") String moneda,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "fecha_nacimiento") Calendar fecha_nacimiento,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "telefono_casa") String telefono_casa,
            @WebParam(name = "telefono_celular") String telefono_celular,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "fecha_recepcion") Calendar fecha_recepcion,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "lugar_trabajo") String lugar_trabajo,
            @WebParam(name = "direccion_trabajo") String direccion_trabajo,
            @WebParam(name = "telefono_trabajo") String telefono_trabajo,
            @WebParam(name = "monto_inicial") Double monto_inicial,
            @WebParam(name = "gestor") Integer gestor,
            @WebParam(name = "sestado") Integer sestado,
            @WebParam(name = "estatus") Integer estatus,
            @WebParam(name = "no_cuenta") String no_cuenta,
            @WebParam(name = "garantia") Integer garantia,
            @WebParam(name = "cargado") String cargado,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "PDF") String PDF,
            @WebParam(name = "INV") String INV,
            @WebParam(name = "MAYCOM") String MAYCOM,
            @WebParam(name = "NITS") String NITS,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "fecha_proximo_pago") Calendar fecha_proximo_pago,
            @WebParam(name = "caso") Integer caso,
            @WebParam(name = "convenio_pactado") String convenio_pactado,
            @WebParam(name = "cuota_convenio") Double cuota_convenio,
            @WebParam(name = "no_cuenta_otro") String no_cuenta_otro,
            @WebParam(name = "situacion_caso") String situacion_caso,
            @WebParam(name = "opcion_proximo_pago") String opcion_proximo_pago,
            @WebParam(name = "anticipo") String anticipo,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha_nacimiento.get(Calendar.DATE);
            Integer mes = fecha_nacimiento.get(Calendar.MONTH) + 1;
            Integer ano = fecha_nacimiento.get(Calendar.YEAR);
            String fecha_nac = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_recepcion.get(Calendar.DATE);
            mes = fecha_recepcion.get(Calendar.MONTH) + 1;
            ano = fecha_recepcion.get(Calendar.YEAR);
            String fecha_rec = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_proximo_pago.get(Calendar.DATE);
            mes = fecha_proximo_pago.get(Calendar.MONTH) + 1;
            ano = fecha_proximo_pago.get(Calendar.YEAR);
            String fecha_prox_pago = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            if (opcion_proximo_pago.equals("NO")) {
                cuota_convenio = 0.00;
                fecha_prox_pago = "1900/01/01";
            }

            String cadenasql = "update deudor set "
                    + "actor='" + actor + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "fecha_nacimiento='" + fecha_nac + "', "
                    + "nombre='" + nombre + "', "
                    + "telefono_casa='" + telefono_casa + "', "
                    + "telefono_celular='" + telefono_celular + "', "
                    + "direccion='" + direccion + "', "
                    + "moneda='" + moneda + "', "
                    + "fecha_recepcion='" + fecha_rec + "', "
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
                    + "PDF='" + PDF + "', "
                    + "INV='" + INV + "', "
                    + "MAYCOM='" + MAYCOM + "', "
                    + "NITS='" + NITS + "', "
                    + "saldo='" + saldo + "', "
                    + "caso='" + caso + "', "
                    + "convenio_pactado='" + convenio_pactado + "', "
                    + "cuota_convenio='" + cuota_convenio + "', "
                    + "no_cuenta_otro='" + no_cuenta_otro + "', "
                    + "situacion_caso='" + situacion_caso + "', "
                    + "opcion_proximo_pago='" + opcion_proximo_pago + "', "
                    + "fecha_proximo_pago='" + fecha_prox_pago + "', "
                    + "anticipo='" + anticipo + "' "
                    + "where deudor=" + deudor.toString();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Expediente Deudor Modificar=> Deudor: " + deudor + " Actor: " + actor + " Moneda: " + moneda + " dpi:" + dpi + " nit:" + nit + " PDF: " + PDF + " INV: " + INV + " MAYCOM: " + MAYCOM + " NITS: " + NITS + " Anticipo: " + anticipo + "',"
                    + "105" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "EXITO";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Deudor_Expediente): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Deudor_Expediente - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param juicio
     * @param procurador
     * @param juzgado
     * @param fecha
     * @param razon_notificacion
     * @param no_juicio
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param procuracion
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param monto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Modificar_Juicio_Expediente")
    public String Modificar_Juicio_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "juicio") Integer juicio,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "fecha") Calendar fecha,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            Integer dia = fecha.get(Calendar.DATE);
            Integer mes = fecha.get(Calendar.MONTH) + 1;
            Integer ano = fecha.get(Calendar.YEAR);
            String fecha_juicio = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = memorial.get(Calendar.DATE);
            mes = memorial.get(Calendar.MONTH) + 1;
            ano = memorial.get(Calendar.YEAR);
            String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_notificacion.get(Calendar.DATE);
            mes = fecha_notificacion.get(Calendar.MONTH) + 1;
            ano = fecha_notificacion.get(Calendar.YEAR);
            String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            String cadenasql = "update juicio set "
                    + "deudor='" + deudor + "', "
                    + "juzgado='" + juzgado + "', "
                    + "fecha='" + fecha_juicio + "', "
                    + "no_juicio='" + no_juicio + "', "
                    + "procurador='" + procurador.toString() + "', "
                    + "razon_notificacion='" + razon_notificacion + "', "
                    + "notificador='" + notificador.toString() + "', "
                    + "abogado_deudor='" + abogado_deudor + "', "
                    + "sumario='" + sumario + "', "
                    + "procuracion='" + procuracion + "', "
                    + "deudor_notificado='" + deudor_notificado + "', "
                    + "fecha_notificacion='" + fecha_noti + "', "
                    + "memorial='" + fecha_memorial + "', "
                    + "monto='" + monto + "' "
                    + "where juicio=" + juicio;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Expediente Juicio Modificar=>  Deudor: " + deudor + " juzgado: " + juzgado.toString() + " fecha " + fecha_juicio + " no_juicio: " + no_juicio + " notificador: " + notificador + " abogado_deudor: " + abogado_deudor + " sumario: " + sumario + " memorial: " + fecha_memorial + " Procuración: " + procuracion + "',"
                    + "106" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "EXITO";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Modificar_Juicio_Expediente - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Lista_Archivos_Digitalizados")
    public String[][] Lista_Archivos_Digitalizados(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String[][] resultado = null;

        try {
            String servidor = "";
            String cadenasql_1 = "select c.valor from constantes c where c.constantes=2";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql_1);
            while (rs.next()) {
                servidor = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String carpeta = "";
            String cadenasql_2 = "select a.digitalizados from actor a left join deudor d on (a.actor=d.actor) where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql_2);
            while (rs.next()) {
                carpeta = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String caso = "";
            String cadenasql_3 = "select d.caso from deudor d where d.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql_3);
            while (rs.next()) {
                caso = rs.getString(1);
            }
            rs.close();
            stmt.close();

            String Directorio = servidor + "/" + carpeta + "/" + caso + "/";
            SmbFile f = new SmbFile(Directorio);
            if (f.exists()) {
                SmbFile[] ficheros = f.listFiles();
                resultado = new String[ficheros.length][3];
                for (Integer i = 0; i < ficheros.length; i++) {
                    resultado[i][0] = i.toString();
                    resultado[i][1] = ficheros[i].getName();
                    resultado[i][2] = ficheros[i].getPath();
                }
            } else {
                resultado = new String[1][1];
                resultado[0][0] = "*** ERROR: NO EXISTE DIRECTORIO ***";
            }

        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(Lista_Archivos_Digitalizados): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param usuario
     * @param menus_no_asignados
     * @param menus_asignados
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Usuario_Uno_Modificar")
    public String Permisos_Usuario_Uno_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menus_no_asignados") String[] menus_no_asignados,
            @WebParam(name = "menus_asignados") String[] menus_asignados,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            for (Integer i = 0; i < menus_no_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_no_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update permiso_usuario_uno set ver='NO' where usuario=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < menus_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update permiso_usuario_uno set ver='SI' where usuario=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Permisos Usuario Uno Modificar=>  usuario: " + usuario + "',"
                    + "108" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Permisos del usuario modificados.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Uno_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Uno_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param usuario
     * @param menu
     * @param nuevo
     * @param modificar
     * @param eliminar
     * @param ver
     * @param activar
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Usuario_Modificar")
    public String Permisos_Usuario_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menu") Integer menu,
            @WebParam(name = "nuevo") String nuevo,
            @WebParam(name = "modificar") String modificar,
            @WebParam(name = "eliminar") String eliminar,
            @WebParam(name = "activar") String activar,
            @WebParam(name = "ver") String ver,
            @WebParam(name = "poolConexion") String poolConexion) {
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update permiso_usuario set "
                    + "nuevo='" + nuevo + "', "
                    + "modificar='" + modificar + "', "
                    + "eliminar='" + eliminar + "', "
                    + "activar='" + modificar + "', "
                    + "ver='" + ver + "' "
                    + "where usuario=" + usuario + " and "
                    + " menu = " + menu;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Permiso Modificar=>  usuario: " + usuario + " menu: " + menu + " nuevo: '" + nuevo + "' modificar: '" + modificar + "' eliminar: '" + eliminar + "' activar: '" + activar + "' ver :'" + ver + "'   "
                    + "108" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "EXITO";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Usuario_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Insertar")
    public String EstadoExtra_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into sestado_extra (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "112" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado extrajudicial registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Modificar")
    public String EstadoExtra_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado_extra set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where sestado_extra=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Estado: " + id_sestado + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "113" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado extrajudicial modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Eliminar")
    public String EstadoExtra_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado_extra set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where sestado_extra=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + id_sestado + "',"
                    + "114" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado extrajudicial eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_sestado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "EstadoExtra_Activar")
    public String EstadoExtra_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_sestado") Integer id_sestado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update sestado_extra set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where sestado_extra=" + id_sestado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + id_sestado + "',"
                    + "115" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado extrajudicial activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(EstadoExtra_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Insertar")
    public String StatusExtra_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into estatus_extra (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "116" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status extrajudicial registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Modificar")
    public String StatusExtra_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus_extra set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where estatus_extra=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Status: " + id_estatus + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "117" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status extrajudicial modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Eliminar")
    public String StatusExtra_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus_extra set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where estatus_extra=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "STATUS: " + id_estatus + "',"
                    + "118" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status extrajudicial eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "StatusExtra_Activar")
    public String StatusExtra_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_estatus") Integer id_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update estatus_extra set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where estatus_extra=" + id_estatus;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "STATUS: " + id_estatus + "',"
                    + "119" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Status extrajudicial activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(StatusExtra_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param estado_d
     * @param estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Judicial")
    public String Estado_Status_Judicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estado_d") Integer estado_d,
            @WebParam(name = "estatus") Integer[] estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from estado_status_judicial where sestado=" + estado_d;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < estatus.length; i++) {
                cadenasql = "insert into estado_status_judicial (sestado,estatus) values (" + estado_d + "," + estatus[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + estado_d + "',"
                    + "120" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado-Status judicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Judicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Judicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param estado_d
     * @param estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Extrajudicial")
    public String Estado_Status_Extrajudicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estado_d") Integer estado_d,
            @WebParam(name = "estatus") Integer[] estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from estado_status_extrajudicial where sestado_extra=" + estado_d;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < estatus.length; i++) {
                cadenasql = "insert into estado_status_extrajudicial (sestado_extra,estatus_extra) values (" + estado_d + "," + estatus[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "ESTADO: " + estado_d + "',"
                    + "121" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Estado-Status extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extrajudicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extrajudicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param estado_estatus
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Estado_Status_Extra_Permite")
    public String Estado_Status_Extra_Permite(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "estatus") String[] estado_estatus,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            for (Integer i = 0; i < estado_estatus.length; i++) {
                String[] registro = estado_estatus[i].split(",");
                String cadenasql = "update estado_status_extrajudicial set permite_estado_judicial=" + registro[2] + " where sestado_extra=" + registro[0] + " and estatus_extra=" + registro[1];
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "MODIFICACION ESTADO-STATUS PERMITE" + "',"
                    + "122" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Configuración bloqueo Estado-Status extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extra_Permite): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Estado_Status_Extra_Permite - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param constantes
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Constantes_Modificar")
    public String Constantes_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "constantes") String[] constantes,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            for (Integer i = 0; i < constantes.length; i++) {
                String[] constante = constantes[i].split(",");

                if (constante[0].trim().equals("1")) {
                    constante[1] = "\\\\\\\\192.168.2.1\\\\discodered\\\\EXPEDIENTES ELECTRONICO";
                }

                String cadenasql = "update constantes set valor='" + constante[1] + "' where constantes=" + constante[0];
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CONSTANTES MODIFICACION" + "',"
                    + "123" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Constantes modificadas en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Constantes_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Constantes_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Insertar")
    public String Tipo_Codigo_Resultado_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into tipo_codigo_contactabilidad (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "124" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Modificar")
    public String Tipo_Codigo_Resultado_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Tipo_Codigo_Resultado: " + id_tipo_codigo_contactabilidad + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "125" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Eliminar")
    public String Tipo_Codigo_Resultado_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO CODIGO RESULTADO: " + id_tipo_codigo_contactabilidad + "',"
                    + "126" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_tipo_codigo_contactabilidad
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Activar")
    public String Tipo_Codigo_Resultado_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_tipo_codigo_contactabilidad") Integer id_tipo_codigo_contactabilidad,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update tipo_codigo_contactabilidad set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where tipo_codigo_contactabilidad=" + id_tipo_codigo_contactabilidad;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_CODIGO_RESULTADO: " + id_tipo_codigo_contactabilidad + "',"
                    + "127" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Tipo codigo de resultado activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Tipo_Codigo_Resultado_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param tipo_codigo_resultado
     * @param codigo_resultado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "TipoCodigo_Codigo_Resultado")
    public String TipoCodigo_Codigo_Resultado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "tipo_codigo_resultado") Integer tipo_codigo_resultado,
            @WebParam(name = "codigo_resultado") Integer[] codigo_resultado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from tipo_codigo_codigo where tipo_codigo_contactabilidad=" + tipo_codigo_resultado;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            for (Integer i = 0; i < codigo_resultado.length; i++) {
                cadenasql = "insert into tipo_codigo_codigo (tipo_codigo_contactabilidad,codigo_contactabilidad) values (" + tipo_codigo_resultado + "," + codigo_resultado[i].toString() + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "TIPO_CODIGO_RESULTADO: " + tipo_codigo_resultado + "',"
                    + "128" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "TipoCodigoResultado-CodigoResultado guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(TipoCodigo_Codigo_Resultado): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(TipoCodigo_Codigo_Resultado - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_usuario
     * @param contrasena_vieja
     * @param contrasena_nueva
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Reiniciar_Contrasena")
    public String Reiniciar_Contrasena(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_usuario") Integer id_usuario,
            @WebParam(name = "contrasena_vieja") String contrasena_vieja,
            @WebParam(name = "contrasena_nueva") String contrasena_nueva,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";
        String cadenasql = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            cadenasql = "update usuario set "
                    + "reinicio=1, "
                    + "contrasena='" + contrasena_nueva + "' "
                    + "where usuario=" + id_usuario;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Usuario: " + id_usuario + "contraseña_antigua: " + contrasena_vieja + "',"
                    + "129)";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Contraseña reiniciada.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Reiniciar_Contrasena): " + ex.toString());
                conn.rollback();
                resultado = ex.toString() + " " + cadenasql;
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Reiniciar_Contrasena - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param usuario
     * @param menus_no_asignados
     * @param menus_asignados
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permisos_Rol_Modificar")
    public String Permisos_Rol_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "usuario") Integer usuario,
            @WebParam(name = "menus_no_asignados") String[] menus_no_asignados,
            @WebParam(name = "menus_asignados") String[] menus_asignados,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            for (Integer i = 0; i < menus_no_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_no_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update rol_menu set ver='NO' where rol=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            for (Integer i = 0; i < menus_asignados.length; i++) {
                String cadenasql = "select m.menu from menu m where m.nombre='" + menus_asignados[i] + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer menu = 0;
                while (rs.next()) {
                    menu = rs.getInt(1);
                }
                rs.close();
                stmt.close();

                cadenasql = "update rol_menu set ver='SI' where rol=" + usuario + " and menu=" + menu;
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Rol Menu Modificar=>  usuario: " + usuario + "',"
                    + "130" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Permisos del Rol modificados.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Rol_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Permisos_Rol_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Insertar")
    public String Rol_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into rol (nombre,descripcion) values ('"
                    + nombre_d + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select max(rol) from rol";
            int rolact = 0;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                rolact = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            cadenasql = "select m.menu from menu m ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);

            while (rs.next()) {
                cadenasql = "insert into rol_menu (rol,menu,ver) values ('" + rolact + "','" + rs.getString(1) + "','NO')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }
            rs.close();
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "131" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol registrado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_rol
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Modificar")
    public String Rol_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_rol") Integer id_rol,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update rol set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where rol=" + id_rol;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Rol: " + id_rol + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "132" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_rol
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Rol_Eliminar")
    public String Rol_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_rol") Integer id_rol,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update rol set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where juzgado=" + id_rol;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "JUZGADO: " + id_rol + "',"
                    + "133" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Rol eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Rol_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param garantia
     * @param cargado
     * @param anticipo
     * @param saldo
     * @param pdf
     * @param inv
     * @param maycom
     * @param nits
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Caso")
    public String Guardar_Expediente_Caso(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "garantia") Integer garantia,
            @WebParam(name = "cargado") String cargado,
            @WebParam(name = "anticipo") String anticipo,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "pdf") String pdf,
            @WebParam(name = "inv") String inv,
            @WebParam(name = "maycom") String maycom,
            @WebParam(name = "nits") String nits,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            cargado = driver.quitar_simbolos(cargado);
            anticipo = driver.quitar_simbolos(anticipo);
            pdf = driver.quitar_simbolos(pdf);
            inv = driver.quitar_simbolos(inv);
            maycom = driver.quitar_simbolos(maycom);
            nits = driver.quitar_simbolos(nits);

            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update deudor set "
                    + "garantia=" + garantia + ", "
                    + "cargado='" + cargado + "', "
                    + "anticipo='" + anticipo + "', "
                    + "saldo=" + saldo + ", "
                    + "pdf='" + pdf + "', "
                    + "inv='" + inv + "', "
                    + "maycom='" + maycom + "', "
                    + "nits='" + nits + "' "
                    + "where "
                    + "deudor=" + deudor;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "|Garantía: " + garantia + "|Cargado: " + cargado + "|Anticipo: " + anticipo + "|Saldo: " + saldo + "|PDF: " + pdf + "|INV: " + inv + "|MAYCOM: " + maycom + "|NITS: " + nits + "',"
                    + "134" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Caso guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Caso): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Caso - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param estado_extra
     * @param status_extra
     * @param telefono_casa
     * @param telefono_celular
     * @param correo_electronico
     * @param lugar_trabajo
     * @param contacto_trabajo
     * @param telefono_trabajo
     * @param dpi
     * @param nit
     * @param intension_pago
     * @param direccion
     * @param notas
     * @param razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Extrajudicial")
    public String Guardar_Expediente_Extrajudicial(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "estado_extra") Integer estado_extra,
            @WebParam(name = "status_extra") Integer status_extra,
            @WebParam(name = "telefono_casa") String telefono_casa,
            @WebParam(name = "telefono_celular") String telefono_celular,
            @WebParam(name = "correo_electronico") String correo_electronico,
            @WebParam(name = "lugar_trabajo") String lugar_trabajo,
            @WebParam(name = "contacto_trabajo") String contacto_trabajo,
            @WebParam(name = "telefono_trabajo") String telefono_trabajo,
            @WebParam(name = "dpi") String dpi,
            @WebParam(name = "nit") String nit,
            @WebParam(name = "intension_pago") Integer intension_pago,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "notas") String notas,
            @WebParam(name = "razon_deuda") Integer razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            telefono_casa = driver.quitar_simbolos(telefono_casa);
            telefono_celular = driver.quitar_simbolos(telefono_celular);
            correo_electronico = driver.quitar_simbolos(correo_electronico);
            lugar_trabajo = driver.quitar_simbolos(lugar_trabajo);
            contacto_trabajo = driver.quitar_simbolos(contacto_trabajo);
            telefono_trabajo = driver.quitar_simbolos(telefono_trabajo);
            dpi = driver.quitar_simbolos(dpi);
            nit = driver.quitar_simbolos(nit);
            direccion = driver.quitar_simbolos(direccion);
            notas = driver.quitar_simbolos(notas);

            //Modo transaccion.
            conn.setAutoCommit(false);

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            String nombre_deudor = "";
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
                nombre_deudor = rs.getString(9);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set "
                    + "sestado_extra=" + estado_extra + ", "
                    + "estatus_extra=" + status_extra + ", "
                    + "telefono_casa='" + telefono_casa + "', "
                    + "telefono_celular='" + telefono_celular + "', "
                    + "correo_electronico='" + correo_electronico + "', "
                    + "lugar_trabajo='" + lugar_trabajo + "', "
                    + "direccion_trabajo='" + contacto_trabajo + "', "
                    + "telefono_trabajo='" + telefono_trabajo + "', "
                    + "dpi='" + dpi + "', "
                    + "nit='" + nit + "', "
                    + "intencion_pago=" + intension_pago + ", "
                    + "direccion='" + direccion + "', "
                    + "descripcion='" + notas + "', "
                    + "razon_deuda=" + razon_deuda + " "
                    + "where "
                    + "deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            String usuario_nombre = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW EXTRAJUDICIAL SI CAMBIARON
            if (!(estado_extra == int_estado_extrajudicial_actual && status_extra == int_status_extrajudicial_actual)) {
                String str_sestado_extrajudicial_nuevo = "";
                cadenasql = "select s.nombre from sestado_extra s where s.sestado_extra=" + estado_extra.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_extrajudicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                String str_estatus_extrajudicial_nuevo = "";
                cadenasql = "select e.nombre from estatus_extra e where e.estatus_extra=" + status_extra.toString();
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
                        + status_extra.toString() + "','"
                        + str_estatus_extrajudicial_nuevo + "','"
                        + estado_extra.toString() + "','"
                        + str_sestado_extrajudicial_nuevo + "','"
                        + int_status_extrajudicial_actual.toString() + "','"
                        + str_status_extrajudicial_actual + "','"
                        + int_estado_extrajudicial_actual.toString() + "','"
                        + str_estado_extrajudicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "|Estado Extrajudicial: " + estado_extra + "|Status Extrajudicial: " + status_extra + "|Teléfono casa: " + telefono_casa + "|Teléfono celular: " + telefono_celular + "|Correo electrónico: " + correo_electronico + "|Lugar de Trabajo: " + lugar_trabajo + "|Contacto Trabajo: " + contacto_trabajo + "|Teléfono Trabajo: " + telefono_trabajo + "|DPI: " + dpi + "|NIT: " + nit + "|Intensión pago: " + intension_pago + "|Dirección: " + direccion + "|Notas: " + notas + "|Razón deuda: " + razon_deuda + "',"
                    + "135" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Extrajudicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Extrajudicial - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     * @param usuario_sys
     * @param deudor
     * @param estado_judicial
     * @param status_judicial
     * @param procurador
     * @param fecha_juicio
     * @param juzgado
     * @param no_juicio
     * @param notificador
     * @param abogado_deudor
     * @param sumario
     * @param memorial
     * @param deudor_notificado
     * @param fecha_notificacion
     * @param monto_demanda
     * @param procuracion
     * @param situacion_caso
     * @param razon_notificacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Guardar_Expediente_Judicial")
    public String Guardar_Expediente_Judical(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "estado_judicial") Integer estado_judicial,
            @WebParam(name = "status_extra") Integer status_judicial,
            @WebParam(name = "procurador") Integer procurador,
            @WebParam(name = "fecha_juicio") Calendar fecha_juicio,
            @WebParam(name = "juzgado") Integer juzgado,
            @WebParam(name = "no_juicio") String no_juicio,
            @WebParam(name = "notificador") Integer notificador,
            @WebParam(name = "abogado_deudor") String abogado_deudor,
            @WebParam(name = "sumario") String sumario,
            @WebParam(name = "memorial") Calendar memorial,
            @WebParam(name = "deudor_notificado") String deudor_notificado,
            @WebParam(name = "fecha_notificacion") Calendar fecha_notificacion,
            @WebParam(name = "monto_demanda") Double monto_demanda,
            @WebParam(name = "procuracion") String procuracion,
            @WebParam(name = "situacion_caso") String situacion_caso,
            @WebParam(name = "razon_notificacion") String razon_notificacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            no_juicio = driver.quitar_simbolos(no_juicio);
            abogado_deudor = driver.quitar_simbolos(abogado_deudor);
            sumario = driver.quitar_simbolos(sumario);
            deudor_notificado = driver.quitar_simbolos(deudor_notificado);
            procuracion = driver.quitar_simbolos(procuracion);
            situacion_caso = driver.quitar_simbolos(situacion_caso);
            razon_notificacion = driver.quitar_simbolos(razon_notificacion);
            //Modo transaccion.
            conn.setAutoCommit(false);

            // **************************** OBTENER ESTADO Y ESTADOS ACTUAL
            Integer int_estado_judicial_actual = 0;
            String str_estado_judicial_actual = "";
            Integer int_status_judicial_actual = 0;
            String str_status_judicial_actual = "";
            Integer int_estado_extrajudicial_actual = 0;
            String str_estado_extrajudicial_actual = "";
            Integer int_status_extrajudicial_actual = 0;
            String str_status_extrajudicial_actual = "";
            String nombre_deudor = "";
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
                nombre_deudor = rs.getString(9);
            }
            rs.close();
            stmt.close();

            cadenasql = "update deudor set "
                    + "sestado=" + estado_judicial + ", "
                    + "estatus=" + status_judicial + ", "
                    + "situacion_caso='" + situacion_caso + "' "
                    + "where "
                    + "deudor=" + deudor;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            cadenasql = "select j.juicio from juicio j where j.deudor=" + deudor;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            Integer id_juicio = 0;
            while (rs.next()) {
                id_juicio = rs.getInt(1);
            }
            rs.close();
            stmt.close();

            Integer dia = fecha_juicio.get(Calendar.DATE);
            Integer mes = fecha_juicio.get(Calendar.MONTH) + 1;
            Integer ano = fecha_juicio.get(Calendar.YEAR);
            String fecha_juicio_t = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = fecha_notificacion.get(Calendar.DATE);
            mes = fecha_notificacion.get(Calendar.MONTH) + 1;
            ano = fecha_notificacion.get(Calendar.YEAR);
            String fecha_noti = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            dia = memorial.get(Calendar.DATE);
            mes = memorial.get(Calendar.MONTH) + 1;
            ano = memorial.get(Calendar.YEAR);
            String fecha_memorial = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            cadenasql = "update juicio set "
                    + "juzgado=" + juzgado + ", "
                    + "fecha='" + fecha_juicio_t + "', "
                    + "no_juicio='" + no_juicio + "', "
                    + "procurador=" + procurador + ", "
                    + "razon_notificacion='" + razon_notificacion + "', "
                    + "notificador=" + notificador + ", "
                    + "abogado_deudor='" + abogado_deudor + "', "
                    + "sumario='" + sumario + "', "
                    + "procuracion='" + procuracion + "', "
                    + "deudor_notificado='" + deudor_notificado + "', "
                    + "fecha_notificacion='" + fecha_noti + "', "
                    + "memorial='" + fecha_memorial + "', "
                    + "monto=" + monto_demanda + " "
                    + "where juicio=" + id_juicio;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            String usuario_nombre = "";
            cadenasql = "select u.nombre from usuario u where u.usuario=" + usuario_sys;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                usuario_nombre = rs.getString(1);
            }
            rs.close();
            stmt.close();

            // **************************** INSERTA EN EL WORKFLOW JUDICIAL SI CAMBIARON
            if (!(estado_judicial == int_estado_judicial_actual && status_judicial == int_status_judicial_actual)) {
                String str_sestado_judicial_nuevo = "";
                cadenasql = "select s.nombre from sestado s where s.sestado=" + estado_judicial.toString();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    str_sestado_judicial_nuevo = rs.getString(1);
                }
                rs.close();
                stmt.close();

                String str_estatus_judicial_nuevo = "";
                cadenasql = "select e.nombre from estatus e where e.estatus=" + status_judicial.toString();
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
                        + status_judicial.toString() + "','"
                        + str_estatus_judicial_nuevo + "','"
                        + estado_judicial.toString() + "','"
                        + str_sestado_judicial_nuevo + "','"
                        + int_status_judicial_actual.toString() + "','"
                        + str_status_judicial_actual + "','"
                        + int_estado_judicial_actual.toString() + "','"
                        + str_estado_judicial_actual + "','"
                        + deudor.toString() + "','"
                        + nombre_deudor + "','"
                        + usuario_sys + "','"
                        + usuario_nombre + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Deudor: " + deudor + "Juicio: " + id_juicio + "|Estado Judicial: " + estado_judicial + "|Status Judicial: " + status_judicial + "|Situación caso: " + situacion_caso + "|Juzgado: " + juzgado + "|Fecha juicio: " + fecha_juicio_t + "|No. juicio: " + no_juicio + "|Procurador: " + procurador + "|Razon notificación: " + razon_notificacion + "|Notificador: " + notificador + "|Abogado deudor: " + abogado_deudor + "|Sumario: " + sumario + "|Procuracion: " + procuracion + "|Deudor notificado: " + deudor_notificado + "|Fecha notificación: " + fecha_noti + "|Memorial: " + fecha_memorial + "|Monto demanda: " + monto_demanda + "',"
                    + "136" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Expediente-Judicial guardado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Judical): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Guardar_Expediente_Judical - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Insertar")
    public String Cartera_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into cartera (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + "|Descripcion: " + descripcion_d + "',"
                    + "137" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Cartera registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_cartera
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Modificar")
    public String Cartera_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cartera set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where cartera=" + id_cartera;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_Cartera: " + id_cartera + "|Nombre: " + nombre_d + "|Ddescripcion: " + descripcion_d + "',"
                    + "138" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Cartera modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Eliminar")
    public String Cartera_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cartera set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where cartera=" + id_cartera;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CARTERA: " + id_cartera + "',"
                    + "139" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Cartera eliminada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_cartera
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Cartera_Activar")
    public String Cartera_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") Integer id_cartera,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update cartera set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where cartera=" + id_cartera;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "CARTERA: " + id_cartera + "',"
                    + "140" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Cartera activada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Cartera_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param permisos
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Permiso_Expediente")
    public String Permiso_Expediente(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_cartera") String[] permisos,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            for (Integer i = 0; i < permisos.length; i++) {
                String[] usuario_permiso = permisos[i].trim().split(",");
                Integer usuario = Integer.parseInt(usuario_permiso[0]);
                String permiso = usuario_permiso[1];

                String cadenasql = "select p.ver from permiso_usuario_uno p where p.usuario=" + usuario + " and p.menu=28";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Boolean existe = false;
                while (rs.next()) {
                    rs.getString(1);
                    existe = true;
                }
                rs.close();
                stmt.close();

                if (existe) {
                    cadenasql = "update permiso_usuario_uno set ver='" + permiso + "' where usuario=" + usuario + " and menu=28";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                } else {
                    cadenasql = "insert into permiso_usuario_uno (usuario,menu,ver) values (" + usuario + ",28,'" + permiso + "')";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
            }

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Permisos de usuario asignados en la opción de menu Expediente.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Permiso_Expediente): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Permiso_Expediente - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param deudor
     * @param tipo_convenio
     * @param estado
     * @param saldo
     * @param interes
     * @param mora
     * @param gasto_otros
     * @param rebaja_interes
     * @param subtotal_pagar
     * @param costas
     * @param monto_costas
     * @param total
     * @param cuota_inicial
     * @param total_pagar
     * @param numero_cuotas
     * @param monto_cuota
     * @param frecuencia
     * @param fecha_pago_inicial
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Insertar")
    public String Convenio_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_convenio") String tipo_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "interes") Double interes,
            @WebParam(name = "mora") Double mora,
            @WebParam(name = "gasto_otros") Double gasto_otros,
            @WebParam(name = "rebaja_interes") Double rebaja_interes,
            @WebParam(name = "subtotal_pagar") Double subtotal_pagar,
            @WebParam(name = "costas") Double costas,
            @WebParam(name = "monto_costas") Double monto_costas,
            @WebParam(name = "total") Double total,
            @WebParam(name = "cuota_inicial") Double cuota_inicial,
            @WebParam(name = "total_pagar") Double total_pagar,
            @WebParam(name = "numero_cuotas") Integer numero_cuotas,
            @WebParam(name = "monto_cuota") Double monto_cuota,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "fecha_pago_inicial") Calendar fecha_pago_inicial,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
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

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_convenio
     * @param deudor
     * @param tipo_convenio
     * @param estado
     * @param saldo
     * @param interes
     * @param mora
     * @param gasto_otros
     * @param rebaja_interes
     * @param subtotal_pagar
     * @param costas
     * @param monto_costas
     * @param total
     * @param cuota_inicial
     * @param total_pagar
     * @param numero_cuotas
     * @param monto_cuota
     * @param frecuencia
     * @param fecha_pago_inicial
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Modificar")
    public String Convenio_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "deudor") Integer deudor,
            @WebParam(name = "tipo_convenio") String tipo_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "saldo") Double saldo,
            @WebParam(name = "interes") Double interes,
            @WebParam(name = "mora") Double mora,
            @WebParam(name = "gasto_otros") Double gasto_otros,
            @WebParam(name = "rebaja_interes") Double rebaja_interes,
            @WebParam(name = "subtotal_pagar") Double subtotal_pagar,
            @WebParam(name = "costas") Double costas,
            @WebParam(name = "monto_costas") Double monto_costas,
            @WebParam(name = "total") Double total,
            @WebParam(name = "cuota_inicial") Double cuota_inicial,
            @WebParam(name = "total_pagar") Double total_pagar,
            @WebParam(name = "numero_cuotas") Integer numero_cuotas,
            @WebParam(name = "monto_cuota") Double monto_cuota,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "fecha_pago_inicial") Calendar fecha_pago_inicial,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
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

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_convenio
     * @param estado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Modificar_Estado")
    public String Convenio_Modificar_Estado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Convenio de pago modificar estado=> Convenio: " + id_convenio + " Estado: " + estado + "',"
                    + "111" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_convenio
     * @param fecha_pago
     * @param hora_pago
     * @param estado_promesa
     * @param monto
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Insertar")
    public String Convenio_Detalle_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") String hora_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
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

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_convenio
     * @param id_promesa_pago
     * @param fecha_pago
     * @param hora_pago
     * @param estado_promesa
     * @param monto
     * @param observacion
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Modificar")
    public String Convenio_Detalle_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "fecha_pago") Calendar fecha_pago,
            @WebParam(name = "hora_pago") String hora_pago,
            @WebParam(name = "estado_promesa") String estado_promesa,
            @WebParam(name = "monto") Double monto,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
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

            //Commit hacia la base de datos y cierra conexion.
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

    /**
     *
     * @param usuario_sys
     * @param id_convenio
     * @param id_promesa_pago
     * @param estado
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Convenio_Detalle_Modificar_Estado")
    public String Convenio_Detalle_Modificar_Estado(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_convenio") Integer id_convenio,
            @WebParam(name = "id_promesa_pago") Integer id_promesa_pago,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
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

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Promesa de pago modificar estado=> Convenio: " + id_convenio + " Promesa Pago: " + id_promesa_pago + " Estado: " + estado + "',"
                    + "143" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
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
    
    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Insertar")
    public String Intencion_Pago_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into intencion_pago (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "144" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención pago registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_intencion_pago
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Modificar")
    public String Intencion_Pago_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_IntencionPago: " + id_intencion_pago + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "145" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención pago modificado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_intencion_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Eliminar")
    public String Intencion_Pago_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "IntensiónPago: " + id_intencion_pago + "',"
                    + "146" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención de pago eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_intencion_pago
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Intencion_Pago_Activar")
    public String Intencion_Pago_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_intencion_pago") Integer id_intencion_pago,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update intencion_pago set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where intencion_pago=" + id_intencion_pago;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "IntenciónPago: " + id_intencion_pago + "',"
                    + "147" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Intención pago activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Intencion_Pago_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    /**
     *
     * @param usuario_sys
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Insertar")
    public String Razon_Deuda_Insertar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "insert into razon_deuda (nombre,estado,descripcion) values ('"
                    + nombre_d + "','"
                    + "VIGENTE" + "','"
                    + descripcion_d + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "148" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Razón deuda registrada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Insertar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Insertar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_razon_deuda
     * @param nombre_d
     * @param descripcion_d
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Modificar")
    public String Razon_Deuda_Modificar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "nombre_d") String nombre_d,
            @WebParam(name = "descripcion_d") String descripcion_d,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update razon_deuda set "
                    + "nombre='" + nombre_d + "', "
                    + "descripcion='" + descripcion_d + "' "
                    + "where razon_deuda=" + id_razon_deuda;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Id_RazonDeuda: " + id_razon_deuda + " Nombre: " + nombre_d + " descripcion: " + descripcion_d + "',"
                    + "149" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Razon deuda modificada en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Modificar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Modificar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Eliminar")
    public String Razon_Deuda_Eliminar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update razon_deuda set "
                    + "estado='" + "ELIMINADO" + "' "
                    + "where razon_deuda=" + id_razon_deuda;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "RazónDeuda: " + id_razon_deuda + "',"
                    + "150" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Razón deuda eliminado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Eliminar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Eliminar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario_sys
     * @param id_razon_deuda
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Razon_Deuda_Activar")
    public String Razon_Deuda_Activar(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "id_razon_deuda") Integer id_razon_deuda,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "update razon_deuda set "
                    + "estado='" + "VIGENTE" + "' "
                    + "where razon_deuda=" + id_razon_deuda;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "IntenciónPago: " + id_razon_deuda + "',"
                    + "151" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Razón deuda activado en el sistema.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Activar): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Razon_Deuda_Activar - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    /**
     *
     * @param usuario_sys
     * @param lst_tipo_codigo_resultado_contacto
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Tipo_Codigo_Resultado_Contacto")
    public String Tipo_Codigo_Resultado_Contacto(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "lst_tipo_codigo_resultado_contacto") String[] lst_tipo_codigo_resultado_contacto,
            @WebParam(name = "poolConexion") String poolConexion) {

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String resultado = "";

        try {
            //Modo transaccion.
            conn.setAutoCommit(false);

            String cadenasql = "delete from tipo_codigo_codigo where tipo_codigo_contactabilidad > 0";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
                
            for (Integer i = 0; i < lst_tipo_codigo_resultado_contacto.length; i++) {
                String[] tipo_codigo_resultado_contacto = lst_tipo_codigo_resultado_contacto[i].split(",");
                Integer tipo_codigo_resultado = Integer.parseInt(tipo_codigo_resultado_contacto[0]);
                Integer codigo_resultado = Integer.parseInt(tipo_codigo_resultado_contacto[1]);
                String contacto = tipo_codigo_resultado_contacto[2];

                cadenasql = "insert into tipo_codigo_codigo ("
                        + "tipo_codigo_contactabilidad,"
                        + "codigo_contactabilidad,"
                        + "contacto) values ("
                        + tipo_codigo_resultado + ","
                        + codigo_resultado + ",'"
                        + contacto + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Tipo_Codigo_Resultado_Contacto: Usuario(" + usuario_sys + ")" + "',"
                    + "152" + ")";
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Contacto de los códigos de resultado actualizado.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Resultado_Contacto): " + ex.toString());
                conn.rollback();
                resultado = ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Codigo_Resultado_Contacto - rollback): " + ex1.toString());
                resultado = ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    /**
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "getMonitor")
    public String[][] getMonitor(
        @WebParam(name = "poolConexion") String poolConexion) {
        
        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);
        String[][] resultado;

        try {
            resultado = new String[2][3];
            resultado[0][0] = "fecha_ultima_gestion";
            resultado[0][1] = "hora_ultima_gestion";
            resultado[0][2] = "numero_gestion";
            
            String cadenasql = "select max(e.fecha) from evento e";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado[1][0] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select max(e.hora) from evento e where e.fecha='" + resultado[1][0] + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado[1][1] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
            cadenasql = "select count(*) from evento e";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                resultado[1][2] = rs.getString(1);
            }
            rs.close();
            stmt.close();
            
        } catch (Exception ex) {
            System.out.println("ERROR => WS-ServiciosLexcom(getMonitor): " + ex.toString());
            resultado = new String[1][1];
            resultado[0][0] = "*** ERROR *** : " + ex.toString();
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
    /**
     *
     * @param usuario_sys
     * @param archivo
     * @param poolConexion
     * @return
     */
    @WebMethod(operationName = "Carga_Gestiones_Cobros")
    public String Carga_Gestiones_Cobros(
            @WebParam(name = "usuario_sys") Integer usuario_sys,
            @WebParam(name = "archivo") String archivo,
            @WebParam(name = "poolConexion") String poolConexion) {

        String resultado = null;
        Integer linea_error = 1;
        Integer filas;

        Driver driver = new Driver();
        Connection conn = driver.getConn(poolConexion);

        try {
            //Formatos Integer, Double y Date.
            DecimalFormat formatoInteger = new DecimalFormat("#");
            DecimalFormat formatoDouble = new DecimalFormat("#0.00");
            SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDate1 = new SimpleDateFormat("yyyy-MM-dd");
            formatoDate.setLenient(false);
            formatoDate1.setLenient(false);

            //Modo transaccion.
            conn.setAutoCommit(false);

            //Selecciona el archivo excel para leerlo.
            File excel = new File(archivo);
            FileInputStream fis = new FileInputStream(excel);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheetAt(0);

            filas = ws.getLastRowNum() + 1;

            for (Integer i = 1; i < filas; i++) {
                linea_error = i + 1;

                XSSFRow row = ws.getRow(i);

                //LEER CAMPOS DEL ARCHIVO EXCEL.
                XSSFCell deudor = row.getCell(0);
                XSSFCell fecha = row.getCell(1);
                XSSFCell hora = row.getCell(2);
                XSSFCell usuario = row.getCell(3);
                XSSFCell codigo_resultado = row.getCell(4);
                XSSFCell contacto = row.getCell(5);
                XSSFCell descripcion = row.getCell(6);
                
                Integer db_deudor = 0;
                try {
                    db_deudor = Integer.parseInt(formatoInteger.format(Double.parseDouble(deudor.toString().trim())));
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select d.deudor from deudor d where d.deudor=" + db_deudor);
                    Boolean existe = false;
                    while (rs.next()) {
                        existe = true;
                    }
                    rs.close();
                    stmt.close();
                    if(!existe) {
                        throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error);
                    }
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Deudor en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Date db_fecha = new Date(1900, 0, 1);
                if (fecha != null) {
                    try {
                        db_fecha = fecha.getDateCellValue();
                        formatoDate.format(db_fecha);
                    } catch (Exception ex) {
                        throw new Exception("Error al calcular el campo Fecha de gestión en la linea: " + linea_error + " Exception: " + ex.toString());
                    }
                } else {
                    throw new Exception("Error al calcular el campo Fecha de gestión en la linea: " + linea_error);
                }

                String db_hora = "";
                if (hora != null) {
                    db_hora = hora.toString().trim();
                    if (db_hora.equals("")) {
                        throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                    } else {
                        if(db_hora.length() != 8) {
                            throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                        } else {
                            try {
                                Integer hora_num = Integer.parseInt(db_hora.substring(0, 2));
                                Integer minuto_num = Integer.parseInt(db_hora.substring(3, 5));
                                Integer segundo_num = Integer.parseInt(db_hora.substring(6, 8));
                                if(hora_num < 0 && hora_num > 23) {
                                    throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                                }
                                if(minuto_num < 0 && minuto_num > 59) {
                                    throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                                }
                                if(segundo_num < 0 && segundo_num > 59) {
                                    throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                                }
                            } catch(Exception ex_hora) {
                                throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                            }
                        }
                    }
                } else {
                    throw new Exception("Error al calcular el campo Hora de gestión en la linea: " + linea_error);
                }

                Integer db_usuario = 0;
                try {
                    db_usuario = Integer.parseInt(formatoInteger.format(Double.parseDouble(usuario.toString().trim())));
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select u.usuario from usuario u where u.usuario=" + db_usuario);
                    Boolean existe = false;
                    while (rs.next()) {
                        existe = true;
                    }
                    rs.close();
                    stmt.close();
                    if(!existe) {
                        throw new Exception("Error al calcular el campo Usuario en la linea: " + linea_error);
                    }
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Usuario en la linea: " + linea_error + " Exception: " + ex.toString());
                }

                Integer db_codigo_contactabilidad = 0;
                try {
                    db_codigo_contactabilidad = Integer.parseInt(formatoInteger.format(Double.parseDouble(codigo_resultado.toString().trim())));
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select cc.codigo_contactabilidad from codigo_contactabilidad cc where cc.codigo_contactabilidad=" + db_codigo_contactabilidad);
                    Boolean existe = false;
                    while (rs.next()) {
                        existe = true;
                    }
                    rs.close();
                    stmt.close();
                    if(!existe) {
                        throw new Exception("Error al calcular el campo Código de Resultado Usuario en la linea: " + linea_error);
                    }
                } catch (Exception ex) {
                    throw new Exception("Error al calcular el campo Código de Resultado en la linea: " + linea_error + " Exception: " + ex.toString());
                }
                
                String db_contacto = "";
                if (contacto != null) {
                    db_contacto = contacto.toString().trim();
                    if (db_contacto.equals("")) {
                        throw new Exception("Error al calcular el campo Contacto de gestión en la linea: " + linea_error);
                    } else {
                        if(!db_contacto.equals("SI") || !db_contacto.equals("NO")) {
                            throw new Exception("Error al calcular el campo Contacto de gestión en la linea: " + linea_error);
                        }
                    }
                } else {
                    throw new Exception("Error al calcular el campo Contacto de gestión en la linea: " + linea_error);
                }
                
                String db_descripcion = "";
                if (descripcion != null) {
                    db_descripcion = descripcion.toString().trim();
                    if (db_descripcion.equals("")) {
                        throw new Exception("Error al calcular el campo Descripción de gestión en la linea: " + linea_error);
                    }
                } else {
                    throw new Exception("Error al calcular el campo Descripción de gestión en la linea: " + linea_error);
                }

                //Carga estructura DEUDORES.
                Deudores_Gestion_Cobro deu_ges_cob = new Deudores_Gestion_Cobro(
                        db_deudor,
                        db_fecha,
                        db_hora,
                        db_usuario,
                        db_codigo_contactabilidad,
                        db_descripcion,
                        db_contacto);

                //Cargar pagos al sistema.
                String cadenasql = "insert deudor_historial_cobros ("
                        + "deudor, "
                        + "fecha, "
                        + "hora, "
                        + "usuario, "
                        + "codigo_contactabilidad, "
                        + "descripcion, "
                        + "contacto) values ("
                        + deu_ges_cob.getDeudor() + ",'"
                        + formatoDate1.format(deu_ges_cob.getFecha()) + "','"
                        + deu_ges_cob.getHora() + "',"
                        + deu_ges_cob.getUsuario() + ","
                        + deu_ges_cob.getCodigo_contactabilidad() + ",'"
                        + deu_ges_cob.getDescripcion() + "','"
                        + deu_ges_cob.getContacto() + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            //Inserta el evento en la bitacora de eventos del sistema.
            String cadenasql = "insert into evento (usuario,fecha,hora,descripcion,tipo_evento) values ("
                    + usuario_sys + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + "Carga masiva gestión cobros." + "',"
                    + "153" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            //Commit hacia la base de datos y cierra conexion.
            conn.commit();
            conn.setAutoCommit(true);

            resultado = "Carga masiva gestion de cobros exitosa.";
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Gestiones_Cobros): " + ex.toString());
                conn.rollback();
                resultado = "Error linea(" + linea_error + "): " + ex.toString();
            } catch (Exception ex1) {
                System.out.println("ERROR => WS-ServiciosLexcom(Carga_Gestiones_Cobros - rollback): " + ex1.toString());
                resultado = "ERROR ROLLBACK: " + ex1.toString();
            }
        } finally {
            conn = driver.closeConn();
            driver = null;
        }

        return resultado;
    }
    
}
