package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_PromesaPago")
@ViewScoped
public class Expediente_PromesaPago implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private List<PromesaPago_List> lst_promesapago;
    private PromesaPago_List promesapago_sel;
    private String lb_numero_promesapagos;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    //Campos formulario Juicio
    private Integer id_convenio;
    private Integer id_promesa_pago;

    private String estado_promesa;
    private String estado_promesa_temp;
    private List<SelectItem> lst_estado_promesa;
    private Date fecha_pago;
    private Integer hora_pago;
    private Integer minuto_pago;
    private Integer segundo_pago;
    private Double monto;
    private String observacion;

    private Integer opcion_gestion; // 1: INSERTAR  2: MODIFICAR

    private Boolean somEstadoPromesa;
    private Boolean calFechaPago;
    private Boolean spnHoraPago;
    private Boolean spnMinutoPago;
    private Boolean spnSegundoPago;
    private Boolean spnMonto;
    private Boolean areObservacionPromesa;
    private Boolean btnGuardar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.lst_promesapago = new ArrayList<>();
            this.promesapago_sel = null;
            this.lb_numero_promesapagos = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar_expediente_promesapago() {
        try {
            this.lst_promesapago = new ArrayList<>();
            this.promesapago_sel = null;
            this.lb_numero_promesapagos = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(limpiar_expediente_promesapago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_PromesaPago(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "cd.promesa_pago, "// rs.getObject(0)
                            + "cd.convenio, "// rs.getObject(1)
                            + "cd.fecha_pago, "// rs.getObject(2)
                            + "cd.hora_pago, "// rs.getObject(3)
                            + "cd.estado_promesa, "// rs.getObject(4)
                            + "cd.monto "// rs.getObject(5)
                            + "from "
                            + "convenio_detalle cd "
                            + "left join convenio c on (cd.convenio=c.convenio) "
                            + "where "
                            + "c.deudor = " + this.deudor + " "
                            + "order by "
                            + "cd.estado_promesa, "
                            + "cd.fecha_pago desc";

                    Servicio servicio = new Servicio();
                    java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

                    Integer filas = resultado.size();
                    Integer columnas = resultado.get(0).getItem().size();
                    String[][] vector_result = new String[resultado.size()][columnas];
                    for (Integer i = 0; i < resultado.size(); i++) {
                        for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                            vector_result[i][j] = resultado.get(i).getItem().get(j);
                        }
                    }

                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    this.lst_promesapago = new ArrayList<>();
                    for (Integer i = 1; i < filas; i++) {
                        PromesaPago_List nod = new PromesaPago_List(
                                i,
                                Integer.parseInt(vector_result[i][0]),
                                Integer.parseInt(vector_result[i][1]),
                                formatDate.parse(vector_result[i][2]),
                                vector_result[i][3],
                                vector_result[i][4],
                                Double.parseDouble(vector_result[i][5]));
                        this.lst_promesapago.add(nod);
                    }

                    filas = filas - 1;
                    this.lb_numero_promesapagos = "No. de promesas de pago: " + filas;

                    cadenasql = "select "
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                            + "d.caso " // rs.getObject(2)
                            + "from "
                            + "deudor d "
                            + "where "
                            + "d.deudor=" + this.deudor;

                    servicio = new Servicio();
                    resultado = servicio.reporte(cadenasql, this.ambiente);

                    filas = resultado.size();
                    columnas = resultado.get(0).getItem().size();
                    vector_result = new String[resultado.size()][columnas];
                    for (Integer i = 0; i < resultado.size(); i++) {
                        for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                            vector_result[i][j] = resultado.get(i).getItem().get(j);
                        }
                    }

                    Integer caso = 0;
                    for (Integer i = 1; i < filas; i++) {
                        this.com_extrajudicial = vector_result[i][0];
                        this.com_judicial = vector_result[i][1];
                        caso = Integer.parseInt(vector_result[i][2]);
                    }

                    this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";

                    RequestContext.getCurrentInstance().execute("PF('var_exp_promesapago').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(Cargar_Expediente_PromesaPago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Actualizar_Expediente_PromesaPago() {
        try {
            String cadenasql = "select "
                    + "cd.promesa_pago, "// rs.getObject(0)
                    + "cd.convenio, "// rs.getObject(1)
                    + "cd.fecha_pago, "// rs.getObject(2)
                    + "cd.hora_pago, "// rs.getObject(3)
                    + "cd.estado_promesa, "// rs.getObject(4)
                    + "cd.monto "// rs.getObject(5)
                    + "from "
                    + "convenio_detalle cd "
                    + "left join convenio c on (cd.convenio=c.convenio) "
                    + "where "
                    + "c.deudor = " + this.deudor + " "
                    + "order by "
                    + "cd.estado_promesa, "
                    + "cd.fecha_pago desc";

            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            this.lst_promesapago = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                PromesaPago_List nod = new PromesaPago_List(
                        i,
                        Integer.parseInt(vector_result[i][0]),
                        Integer.parseInt(vector_result[i][1]),
                        formatDate.parse(vector_result[i][2]),
                        vector_result[i][3],
                        vector_result[i][4],
                        Double.parseDouble(vector_result[i][5]));
                this.lst_promesapago.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_promesapagos = "No. de promesas de pago: " + filas;

            cadenasql = "select "
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                    + "d.caso " // rs.getObject(2)
                    + "from "
                    + "deudor d "
                    + "where "
                    + "d.deudor=" + this.deudor;

            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql, this.ambiente);

            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            Integer caso = 0;
            for (Integer i = 1; i < filas; i++) {
                this.com_extrajudicial = vector_result[i][0];
                this.com_judicial = vector_result[i][1];
                caso = Integer.parseInt(vector_result[i][2]);
            }

            this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(Actualizar_Expediente_PromesaPago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_promesapago_agregar() {
        try {
            String cadenasql = "select 1 from convenio_detalle cd left join convenio c on (cd.convenio=c.convenio) where c.deudor=" + this.deudor + " and cd.estado_promesa in ('PENDIENTE')";
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            this.id_promesa_pago = 0;
            for (Integer i = 1; i < filas; i++) {
                this.id_promesa_pago = Integer.parseInt(vector_result[i][0]);
            }

            if (this.id_promesa_pago == 0) {
                cadenasql = "select c.convenio from convenio c where c.deudor=" + this.deudor + " and c.estado in ('ACTIVO')";
                servicio = new Servicio();
                resultado = servicio.reporte(cadenasql, this.ambiente);

                filas = resultado.size();
                columnas = resultado.get(0).getItem().size();
                vector_result = new String[resultado.size()][columnas];
                for (Integer i = 0; i < resultado.size(); i++) {
                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                        vector_result[i][j] = resultado.get(i).getItem().get(j);
                    }
                }

                this.id_convenio = 0;
                for (Integer i = 1; i < filas; i++) {
                    this.id_convenio = Integer.parseInt(vector_result[i][0]);
                }

                if (this.id_convenio > 0) {
                    opcion_gestion = 1;
                    Driver drive = new Driver();

                    this.lst_estado_promesa = drive.lista_estado_convenio_detalle();
                    this.estado_promesa = "PENDIENTE";
                    this.fecha_pago = new Date();
                    this.hora_pago = fecha_pago.getHours();
                    this.minuto_pago = fecha_pago.getMinutes();
                    this.segundo_pago = fecha_pago.getSeconds();
                    this.monto = 0.00;
                    this.observacion = "-";

                    this.somEstadoPromesa = true;
                    this.calFechaPago = false;
                    this.spnHoraPago = false;
                    this.spnMinutoPago = false;
                    this.spnSegundoPago = false;
                    this.spnMonto = false;
                    this.areObservacionPromesa = false;
                    this.btnGuardar = false;

                    RequestContext.getCurrentInstance().execute("PF('var_promesapago').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "El deudor debe tener creado un convenio de pago en estado ACTIVO."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "El deudor no puede tener mas de una promesa de pago en estado PENDIENTE."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(cargar_promesapago_agregar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_promesapago_modificar() {
        try {
            opcion_gestion = 2;

            Driver drive = new Driver();
            this.lst_estado_promesa = drive.lista_estado_convenio_detalle();

            String cadenasql = "select "
                    + "cd.estado_promesa, "//rs.getObject(0)
                    + "cd.fecha_pago, "//rs.getObject(1)
                    + "hour(cd.hora_pago) hora, "//rs.getObject(2)
                    + "minute(cd.hora_pago) minuto, "//rs.getObject(3)
                    + "second(cd.hora_pago) segundo, "//rs.getObject(4)
                    + "cd.monto, "//rs.getObject(5)
                    + "cd.observacion "//rs.getObject(6)
                    + "from "
                    + "convenio_detalle cd "
                    + "where "
                    + "cd.convenio = " + this.promesapago_sel.getNo_convenio() + " and cd.promesa_pago=" + this.promesapago_sel.getIndice();

            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            for (Integer i = 1; i < filas; i++) {
                this.estado_promesa = vector_result[i][0];
                this.estado_promesa_temp = this.estado_promesa;
                this.fecha_pago = formatDate.parse(vector_result[i][1]);
                this.hora_pago = Integer.parseInt(vector_result[i][2]);
                this.minuto_pago = Integer.parseInt(vector_result[i][3]);
                this.segundo_pago = Integer.parseInt(vector_result[i][4]);
                this.monto = Double.parseDouble(vector_result[i][5]);
                this.observacion = vector_result[i][6];
            }

            if (this.estado_promesa.equals("CUMPLIDO") || this.estado_promesa.equals("INCUMPLIDO")) {
                this.somEstadoPromesa = true;
                this.calFechaPago = true;
                this.spnHoraPago = true;
                this.spnMinutoPago = true;
                this.spnSegundoPago = true;
                this.spnMonto = true;
                this.areObservacionPromesa = true;
                this.btnGuardar = true;
            } else {
                this.somEstadoPromesa = true;
                this.calFechaPago = false;
                this.spnHoraPago = false;
                this.spnMinutoPago = false;
                this.spnSegundoPago = false;
                this.spnMonto = false;
                this.areObservacionPromesa = false;
                this.btnGuardar = false;
            }

            RequestContext.getCurrentInstance().execute("PF('var_promesapago').show();");

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(cargar_promesapago_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_promesapago_modificar_estado() {
        try {
            if (this.promesapago_sel != null) {
                opcion_gestion = 3;

                Driver drive = new Driver();
                this.lst_estado_promesa = drive.lista_estado_convenio_detalle();

                String cadenasql = "select "
                        + "cd.estado_promesa, "//rs.getObject(0)
                        + "cd.fecha_pago, "//rs.getObject(1)
                        + "hour(cd.hora_pago) hora, "//rs.getObject(2)
                        + "minute(cd.hora_pago) minuto, "//rs.getObject(3)
                        + "second(cd.hora_pago) segundo, "//rs.getObject(4)
                        + "cd.monto, "//rs.getObject(5)
                        + "cd.observacion "//rs.getObject(6)
                        + "from "
                        + "convenio_detalle cd "
                        + "where "
                        + "cd.convenio = " + this.promesapago_sel.getNo_convenio() + " and cd.promesa_pago=" + this.promesapago_sel.getIndice();

                Servicio servicio = new Servicio();
                java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

                Integer filas = resultado.size();
                Integer columnas = resultado.get(0).getItem().size();
                String[][] vector_result = new String[resultado.size()][columnas];
                for (Integer i = 0; i < resultado.size(); i++) {
                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                        vector_result[i][j] = resultado.get(i).getItem().get(j);
                    }
                }

                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                for (Integer i = 1; i < filas; i++) {
                    this.estado_promesa = vector_result[i][0];
                    this.estado_promesa_temp = this.estado_promesa;
                    this.fecha_pago = formatDate.parse(vector_result[i][1]);
                    this.hora_pago = Integer.parseInt(vector_result[i][2]);
                    this.minuto_pago = Integer.parseInt(vector_result[i][3]);
                    this.segundo_pago = Integer.parseInt(vector_result[i][4]);
                    this.monto = Double.parseDouble(vector_result[i][5]);
                    this.observacion = vector_result[i][6];
                }

                //DESHABILITA LOS CONTROLES
                if (this.estado_promesa.equals("CUMPLIDO") || this.estado_promesa.equals("INCUMPLIDO")) {
                    this.somEstadoPromesa = true;
                    this.btnGuardar = true;
                } else {
                    this.somEstadoPromesa = false;
                    this.btnGuardar = false;
                }
                this.calFechaPago = true;
                this.spnHoraPago = true;
                this.spnMinutoPago = true;
                this.spnSegundoPago = true;
                this.spnMonto = true;
                this.areObservacionPromesa = true;

                RequestContext.getCurrentInstance().execute("PF('var_promesapago').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una promesa de pago del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(cargar_promesapago_modificar_estado): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void gestion_promesapago() {
        try {
            if (opcion_gestion == 1) {
                this.insertar_gestion_promesapago();
            }
            if (opcion_gestion == 2) {
                this.modificar_gestion_promesapago();
            }
            if (opcion_gestion == 3) {
                this.modificar_gestion_promesapago_estado();
            }

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(gestion_promesapago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void insertar_gestion_promesapago() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_pago.getYear() + 1900, this.fecha_pago.getMonth(), this.fecha_pago.getDate());
            XMLGregorianCalendar gre_fecha_pago = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            String hora_pago_p = "";
            if (this.hora_pago < 10) {
                hora_pago_p = "0" + this.hora_pago.toString() + ":";
            } else {
                hora_pago_p = this.hora_pago.toString() + ":";
            }

            if (this.minuto_pago < 10) {
                hora_pago_p = hora_pago_p + "0" + this.minuto_pago.toString() + ":";
            } else {
                hora_pago_p = hora_pago_p + this.minuto_pago.toString() + ":";
            }

            if (this.segundo_pago < 10) {
                hora_pago_p = hora_pago_p + "0" + this.segundo_pago.toString();
            } else {
                hora_pago_p = hora_pago_p + this.segundo_pago.toString();
            }

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.convenioDetalleInsertar(id_usuario, this.id_convenio, gre_fecha_pago, hora_pago_p, this.estado_promesa, this.monto, this.observacion, this.ambiente);

            this.limpiar_expediente_promesapago();
            this.Actualizar_Expediente_PromesaPago();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(insertar_gestion_promesapago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_promesapago() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_pago.getYear() + 1900, this.fecha_pago.getMonth(), this.fecha_pago.getDate());
            XMLGregorianCalendar gre_fecha_pago = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            String hora_pago_p = "";
            if (this.hora_pago < 10) {
                hora_pago_p = "0" + this.hora_pago.toString() + ":";
            } else {
                hora_pago_p = this.hora_pago.toString() + ":";
            }

            if (this.minuto_pago < 10) {
                hora_pago_p = hora_pago_p + "0" + this.minuto_pago.toString() + ":";
            } else {
                hora_pago_p = hora_pago_p + this.minuto_pago.toString() + ":";
            }

            if (this.segundo_pago < 10) {
                hora_pago_p = hora_pago_p + "0" + this.segundo_pago.toString();
            } else {
                hora_pago_p = hora_pago_p + this.segundo_pago.toString();
            }

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.convenioDetalleModificar(id_usuario, this.promesapago_sel.getNo_convenio(), this.promesapago_sel.getIndice(), gre_fecha_pago, hora_pago_p, this.estado_promesa, this.monto, this.observacion, this.ambiente);

            this.limpiar_expediente_promesapago();
            this.Actualizar_Expediente_PromesaPago();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(modificar_gestion_promesapago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_promesapago_estado() {
        try {
            if (this.estado_promesa_temp.equals(this.estado_promesa)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "No se realizo ningun cambio en el estado del convenio"));
            } else {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                Servicio servicio = new Servicio();
                String resultado = servicio.convenioDetalleModificarEstado(id_usuario, this.promesapago_sel.getNo_convenio(), this.promesapago_sel.getIndice(), this.estado_promesa, this.ambiente);

                this.limpiar_expediente_promesapago();
                this.Actualizar_Expediente_PromesaPago();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_PromesaPago(modificar_gestion_promesapago_estado): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public List<PromesaPago_List> getLst_promesapago() {
        return lst_promesapago;
    }

    public void setLst_promesapago(List<PromesaPago_List> lst_promesapago) {
        this.lst_promesapago = lst_promesapago;
    }

    public PromesaPago_List getPromesapago_sel() {
        return promesapago_sel;
    }

    public void setPromesapago_sel(PromesaPago_List promesapago_sel) {
        this.promesapago_sel = promesapago_sel;
    }

    public String getLb_numero_promesapagos() {
        return lb_numero_promesapagos;
    }

    public void setLb_numero_promesapagos(String lb_numero_promesapagos) {
        this.lb_numero_promesapagos = lb_numero_promesapagos;
    }

    public String getCom_extrajudicial() {
        return com_extrajudicial;
    }

    public void setCom_extrajudicial(String com_extrajudicial) {
        this.com_extrajudicial = com_extrajudicial;
    }

    public String getCom_judicial() {
        return com_judicial;
    }

    public void setCom_judicial(String com_judicial) {
        this.com_judicial = com_judicial;
    }

    public String getTitulo_deudor() {
        return titulo_deudor;
    }

    public void setTitulo_deudor(String titulo_deudor) {
        this.titulo_deudor = titulo_deudor;
    }

    public Integer getId_convenio() {
        return id_convenio;
    }

    public void setId_convenio(Integer id_convenio) {
        this.id_convenio = id_convenio;
    }

    public Integer getId_promesa_pago() {
        return id_promesa_pago;
    }

    public void setId_promesa_pago(Integer id_promesa_pago) {
        this.id_promesa_pago = id_promesa_pago;
    }

    public String getEstado_promesa() {
        return estado_promesa;
    }

    public void setEstado_promesa(String estado_promesa) {
        this.estado_promesa = estado_promesa;
    }

    public String getEstado_promesa_temp() {
        return estado_promesa_temp;
    }

    public void setEstado_promesa_temp(String estado_promesa_temp) {
        this.estado_promesa_temp = estado_promesa_temp;
    }

    public List<SelectItem> getLst_estado_promesa() {
        return lst_estado_promesa;
    }

    public void setLst_estado_promesa(List<SelectItem> lst_estado_promesa) {
        this.lst_estado_promesa = lst_estado_promesa;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Integer getHora_pago() {
        return hora_pago;
    }

    public void setHora_pago(Integer hora_pago) {
        this.hora_pago = hora_pago;
    }

    public Integer getMinuto_pago() {
        return minuto_pago;
    }

    public void setMinuto_pago(Integer minuto_pago) {
        this.minuto_pago = minuto_pago;
    }

    public Integer getSegundo_pago() {
        return segundo_pago;
    }

    public void setSegundo_pago(Integer segundo_pago) {
        this.segundo_pago = segundo_pago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getOpcion_gestion() {
        return opcion_gestion;
    }

    public void setOpcion_gestion(Integer opcion_gestion) {
        this.opcion_gestion = opcion_gestion;
    }

    public Boolean getSomEstadoPromesa() {
        return somEstadoPromesa;
    }

    public void setSomEstadoPromesa(Boolean somEstadoPromesa) {
        this.somEstadoPromesa = somEstadoPromesa;
    }

    public Boolean getCalFechaPago() {
        return calFechaPago;
    }

    public void setCalFechaPago(Boolean calFechaPago) {
        this.calFechaPago = calFechaPago;
    }

    public Boolean getSpnHoraPago() {
        return spnHoraPago;
    }

    public void setSpnHoraPago(Boolean spnHoraPago) {
        this.spnHoraPago = spnHoraPago;
    }

    public Boolean getSpnMinutoPago() {
        return spnMinutoPago;
    }

    public void setSpnMinutoPago(Boolean spnMinutoPago) {
        this.spnMinutoPago = spnMinutoPago;
    }

    public Boolean getSpnSegundoPago() {
        return spnSegundoPago;
    }

    public void setSpnSegundoPago(Boolean spnSegundoPago) {
        this.spnSegundoPago = spnSegundoPago;
    }

    public Boolean getSpnMonto() {
        return spnMonto;
    }

    public void setSpnMonto(Boolean spnMonto) {
        this.spnMonto = spnMonto;
    }

    public Boolean getAreObservacionPromesa() {
        return areObservacionPromesa;
    }

    public void setAreObservacionPromesa(Boolean areObservacionPromesa) {
        this.areObservacionPromesa = areObservacionPromesa;
    }

    public Boolean getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(Boolean btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

}
