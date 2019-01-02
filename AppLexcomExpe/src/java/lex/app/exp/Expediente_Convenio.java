package lex.app.exp;

import java.io.Serializable;
import java.text.DecimalFormat;
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

@ManagedBean(name = "Expediente_Convenio")
@ViewScoped
public class Expediente_Convenio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private List<Convenio_List> lst_convenio;
    private Convenio_List convenio_sel;
    private String lb_numero_convenio;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    //Campos formulario Juicio
    private String tipo_convenio;
    private List<SelectItem> lst_tipo_convenio;
    private String estado_convenio;
    private String estado_convenio_temp;
    private List<SelectItem> lst_estado_convenio;
    private Double saldo;
    private Double interes;
    private Double mora;
    private Double gasto_otros;
    private Double rebaja_interes;
    private Double subtotal_pagar;
    private Double costas;
    private Double monto_costas;
    private Double total;
    private Double cuota_inicial;
    private Double total_pagar;
    private Integer numero_cuotas;
    private Double monto_cuota;
    private String frecuencia;
    private List<SelectItem> lst_frecuencia;
    private Date fecha_pago_inicial;
    private String observacion;

    private Integer opcion_gestion; // 1: INSERTAR  2: MODIFICAR

    private Boolean somTipoConvenio;
    private Boolean somEstadoConvenio;
    private Boolean spnSaldoDeudor;
    private Boolean spnInteres;
    private Boolean spnMora;
    private Boolean spnGastosOtros;
    private Boolean spnRebajaIntereses;
    private Boolean spnCostas;
    private Boolean spnCuotaInicial;
    private Boolean spnNoCuotas;
    private Boolean somFrecuencia;
    private Boolean calFechaPagoInicial;
    private Boolean areObservacionConvenio;
    private Boolean btnGuardar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.lst_convenio = new ArrayList<>();
            this.convenio_sel = null;
            this.lb_numero_convenio = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar_expediente_convenio() {
        try {
            this.lst_convenio = new ArrayList<>();
            this.convenio_sel = null;
            this.lb_numero_convenio = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(limpiar_expediente_convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Convenio(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "c.convenio, "// rs.getObject(0)
                            + "c.fecha_creacion, "// rs.getObject(1)
                            + "c.estado, "// rs.getObject(2)
                            + "c.fecha_pago_inicial, "// rs.getObject(3)
                            + "c.total_pagar, "// rs.getObject(4)
                            + "c.numero_cuotas, "// rs.getObject(5)
                            + "c.frecuencia, "// rs.getObject(6)
                            + "c.monto_cuota "// rs.getObject(7)
                            + "from "
                            + "convenio c "
                            + "left join deudor de on (c.deudor=de.deudor) "
                            + "left join actor a on (de.actor=a.actor) "
                            + "where "
                            + "c.deudor=" + this.deudor + " "
                            + "order by "
                            + "c.estado, "
                            + "c.fecha_creacion desc";

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
                    this.lst_convenio = new ArrayList<>();
                    for (Integer i = 1; i < filas; i++) {
                        Convenio_List nod = new Convenio_List(
                                Integer.parseInt(vector_result[i][0]),
                                formatDate.parse(vector_result[i][1]),
                                vector_result[i][2],
                                formatDate.parse(vector_result[i][3]),
                                Double.parseDouble(vector_result[i][4]),
                                Integer.parseInt(vector_result[i][5]),
                                vector_result[i][6],
                                Double.parseDouble(vector_result[i][7]));
                        this.lst_convenio.add(nod);
                    }

                    filas = filas - 1;
                    this.lb_numero_convenio = "No. de convenios: " + filas;

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

                    RequestContext.getCurrentInstance().execute("PF('var_exp_convenio').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(Cargar_Expediente_Convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Actualizar_Expediente_convenio() {
        try {
            String cadenasql = "select "
                    + "c.convenio, "// rs.getObject(0)
                    + "c.fecha_creacion, "// rs.getObject(1)
                    + "c.estado, "// rs.getObject(2)
                    + "c.fecha_pago_inicial, "// rs.getObject(3)
                    + "c.total_pagar, "// rs.getObject(4)
                    + "c.numero_cuotas, "// rs.getObject(5)
                    + "c.frecuencia, "// rs.getObject(6)
                    + "c.monto_cuota "// rs.getObject(7)
                    + "from "
                    + "convenio c "
                    + "left join deudor de on (c.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "c.deudor=" + this.deudor + " "
                    + "order by "
                    + "c.estado, "
                    + "c.fecha_creacion desc";

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
            this.lst_convenio = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Convenio_List nod = new Convenio_List(
                        Integer.parseInt(vector_result[i][0]),
                        formatDate.parse(vector_result[i][1]),
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        Double.parseDouble(vector_result[i][4]),
                        Integer.parseInt(vector_result[i][5]),
                        vector_result[i][6],
                        Double.parseDouble(vector_result[i][7]));
                this.lst_convenio.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_convenio = "No. de convenios: " + filas;

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
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(Actualizar_Expediente_convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_convenio_agregar() {
        try {
            String cadenasql = "select c.convenio from convenio c where c.deudor=" + this.deudor + " and c.estado in ('ACTIVO','NEGOCIACION')";
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

            Integer id_convenio = 0;
            for (Integer i = 1; i < filas; i++) {
                id_convenio = Integer.parseInt(vector_result[i][0]);
            }

            cadenasql = "select d.saldo from deudor d where d.deudor=" + this.deudor;
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

            for (Integer i = 1; i < filas; i++) {
                this.saldo = Double.parseDouble(vector_result[i][0]);
            }

            if (id_convenio == 0) {
                opcion_gestion = 1;
                Driver drive = new Driver();

                this.lst_tipo_convenio = drive.lista_tipo_convenio();
                if (!this.lst_tipo_convenio.isEmpty()) {
                    this.tipo_convenio = this.lst_tipo_convenio.get(0).getValue().toString();
                } else {
                    this.tipo_convenio = "";
                }

                this.lst_estado_convenio = drive.lista_estado_convenio();
                if (!this.lst_estado_convenio.isEmpty()) {
                    this.estado_convenio = this.lst_estado_convenio.get(0).getValue().toString();
                } else {
                    this.estado_convenio = "";
                }

                this.lst_frecuencia = drive.lista_frecuencia_convenio();
                if (!this.lst_frecuencia.isEmpty()) {
                    this.frecuencia = this.lst_frecuencia.get(0).getValue().toString();
                } else {
                    this.frecuencia = "";
                }

                this.interes = 0.00;
                this.mora = 0.00;
                this.gasto_otros = 0.00;
                this.rebaja_interes = 0.00;
                this.subtotal_pagar = 0.00;
                this.costas = 15.00;
                this.monto_costas = 0.00;
                this.total = 0.00;
                this.cuota_inicial = 0.00;
                this.total_pagar = 0.00;
                this.numero_cuotas = 1;
                this.monto_cuota = 0.00;
                this.fecha_pago_inicial = new Date();
                this.observacion = "-";

                //CALCULA VALORES AUTOMATICOS.
                this.calcula_valores_modificar();

                //DESHABILITA LOS CONTROLES
                this.somTipoConvenio = false;
                this.somEstadoConvenio = true;
                this.spnSaldoDeudor = false;
                this.spnInteres = false;
                this.spnMora = false;
                this.spnGastosOtros = false;
                this.spnRebajaIntereses = false;
                this.spnCostas = false;
                this.spnCuotaInicial = false;
                this.spnNoCuotas = false;
                this.somFrecuencia = false;
                this.calFechaPagoInicial = false;
                this.areObservacionConvenio = false;
                this.btnGuardar = false;

                RequestContext.getCurrentInstance().execute("PF('var_convenio').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "El deudor no puede tener mas de un convenio en estado ACTIVO o NEGOCIACIÓN."));
            }

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(cargar_convenio_agregar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_convenio_modificar() {
        try {
            opcion_gestion = 2;
            Driver drive = new Driver();

            this.lst_tipo_convenio = drive.lista_tipo_convenio();
            if (!this.lst_tipo_convenio.isEmpty()) {
                this.tipo_convenio = this.lst_tipo_convenio.get(0).getValue().toString();
            } else {
                this.tipo_convenio = "";
            }

            this.lst_estado_convenio = drive.lista_estado_convenio();
            if (!this.lst_estado_convenio.isEmpty()) {
                this.estado_convenio = this.lst_estado_convenio.get(0).getValue().toString();
            } else {
                this.estado_convenio = "";
            }

            this.lst_frecuencia = drive.lista_frecuencia_convenio();
            if (!this.lst_frecuencia.isEmpty()) {
                this.frecuencia = this.lst_frecuencia.get(0).getValue().toString();
            } else {
                this.frecuencia = "";
            }

            String cadenasql = "select "
                    + "c.tipo_convenio, "//rs.getObject(0)
                    + "c.estado, "//rs.getObject(1)
                    + "c.saldo, "//rs.getObject(2)
                    + "c.interes, "//rs.getObject(3)
                    + "c.mora, "//rs.getObject(4)
                    + "c.gasto_otros, "//rs.getObject(5)
                    + "c.rebaja_interes, "//rs.getObject(6)
                    + "c.costas, "//rs.getObject(7)
                    + "c.cuota_inicial, "//rs.getObject(8)
                    + "c.numero_cuotas, "//rs.getObject(9)
                    + "c.frecuencia, "//rs.getObject(10)
                    + "c.fecha_pago_inicial, "//rs.getObject(11)
                    + "c.observacion "//rs.getObject(12)
                    + "from "
                    + "convenio c "
                    + "where "
                    + "c.convenio = " + this.convenio_sel.getIndice();

            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

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
                this.tipo_convenio = vector_result[i][0];
                this.estado_convenio = vector_result[i][1];
                this.saldo = Double.parseDouble(vector_result[i][2]);
                this.interes = Double.parseDouble(vector_result[i][3]);
                this.mora = Double.parseDouble(vector_result[i][4]);
                this.gasto_otros = Double.parseDouble(vector_result[i][5]);
                this.rebaja_interes = Double.parseDouble(vector_result[i][6]);
                this.costas = Double.parseDouble(vector_result[i][7]);
                this.cuota_inicial = Double.parseDouble(vector_result[i][8]);
                this.numero_cuotas = Integer.parseInt(vector_result[i][9]);
                this.frecuencia = vector_result[i][10];
                this.fecha_pago_inicial = formatDate.parse(vector_result[i][11]);
                this.observacion = vector_result[i][12];
            }

            //CALCULA VALORES AUTOMATICOS.
            this.calcula_valores_modificar();

            //DESHABILITA LOS CONTROLES
            if (this.estado_convenio.equals("NEGOCIACION")) {
                this.somTipoConvenio = false;
                this.somEstadoConvenio = true;
                this.spnSaldoDeudor = false;
                this.spnInteres = false;
                this.spnMora = false;
                this.spnGastosOtros = false;
                this.spnRebajaIntereses = false;
                this.spnCostas = false;
                this.spnCuotaInicial = false;
                this.spnNoCuotas = false;
                this.somFrecuencia = false;
                this.calFechaPagoInicial = false;
                this.areObservacionConvenio = false;
                this.btnGuardar = false;
            } else {
                this.somTipoConvenio = true;
                this.somEstadoConvenio = true;
                this.spnSaldoDeudor = true;
                this.spnInteres = true;
                this.spnMora = true;
                this.spnGastosOtros = true;
                this.spnRebajaIntereses = true;
                this.spnCostas = true;
                this.spnCuotaInicial = true;
                this.spnNoCuotas = true;
                this.somFrecuencia = true;
                this.calFechaPagoInicial = true;
                this.areObservacionConvenio = true;
                this.btnGuardar = true;
            }

            RequestContext.getCurrentInstance().execute("PF('var_convenio').show();");

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(cargar_convenio_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_convenio_modificar_estado() {
        try {
            if (convenio_sel != null) {
                opcion_gestion = 3;
                Driver drive = new Driver();

                this.lst_tipo_convenio = drive.lista_tipo_convenio();
                if (!this.lst_tipo_convenio.isEmpty()) {
                    this.tipo_convenio = this.lst_tipo_convenio.get(0).getValue().toString();
                } else {
                    this.tipo_convenio = "";
                }

                this.lst_estado_convenio = drive.lista_estado_convenio();
                if (!this.lst_estado_convenio.isEmpty()) {
                    this.estado_convenio = this.lst_estado_convenio.get(0).getValue().toString();
                } else {
                    this.estado_convenio = "";
                }

                this.lst_frecuencia = drive.lista_frecuencia_convenio();
                if (!this.lst_frecuencia.isEmpty()) {
                    this.frecuencia = this.lst_frecuencia.get(0).getValue().toString();
                } else {
                    this.frecuencia = "";
                }

                String cadenasql = "select "
                        + "c.tipo_convenio, "//rs.getObject(0)
                        + "c.estado, "//rs.getObject(1)
                        + "c.saldo, "//rs.getObject(2)
                        + "c.interes, "//rs.getObject(3)
                        + "c.mora, "//rs.getObject(4)
                        + "c.gasto_otros, "//rs.getObject(5)
                        + "c.rebaja_interes, "//rs.getObject(6)
                        + "c.costas, "//rs.getObject(7)
                        + "c.cuota_inicial, "//rs.getObject(8)
                        + "c.numero_cuotas, "//rs.getObject(9)
                        + "c.frecuencia, "//rs.getObject(10)
                        + "c.fecha_pago_inicial, "//rs.getObject(11)
                        + "c.observacion "//rs.getObject(12)
                        + "from "
                        + "convenio c "
                        + "where "
                        + "c.convenio = " + this.convenio_sel.getIndice();

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
                    this.tipo_convenio = vector_result[i][0];
                    this.estado_convenio = vector_result[i][1];
                    this.estado_convenio_temp = this.estado_convenio;
                    this.saldo = Double.parseDouble(vector_result[i][2]);
                    this.interes = Double.parseDouble(vector_result[i][3]);
                    this.mora = Double.parseDouble(vector_result[i][4]);
                    this.gasto_otros = Double.parseDouble(vector_result[i][5]);
                    this.rebaja_interes = Double.parseDouble(vector_result[i][6]);
                    this.costas = Double.parseDouble(vector_result[i][7]);
                    this.cuota_inicial = Double.parseDouble(vector_result[i][8]);
                    this.numero_cuotas = Integer.parseInt(vector_result[i][9]);
                    this.frecuencia = vector_result[i][10];
                    this.fecha_pago_inicial = formatDate.parse(vector_result[i][11]);
                    this.observacion = vector_result[i][12];
                }

                //CALCULA VALORES AUTOMATICOS.
                this.calcula_valores_modificar_estado();

                if (this.estado_convenio.equals("ACTIVO")) {
                    this.lst_estado_convenio.remove(0);
                }

                //DESHABILITA LOS CONTROLES
                this.somTipoConvenio = true;
                if (this.estado_convenio.equals("ANULADO") || this.estado_convenio.equals("TERMINADO")) {
                    this.somEstadoConvenio = true;
                } else {
                    this.somEstadoConvenio = false;
                }
                this.spnSaldoDeudor = true;
                this.spnInteres = true;
                this.spnMora = true;
                this.spnGastosOtros = true;
                this.spnRebajaIntereses = true;
                this.spnCostas = true;
                this.spnCuotaInicial = true;
                this.spnNoCuotas = true;
                this.somFrecuencia = true;
                this.calFechaPagoInicial = true;
                this.areObservacionConvenio = true;
                this.btnGuardar = false;

                RequestContext.getCurrentInstance().execute("PF('var_convenio').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un convenio de pago del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(cargar_convenio_modificar_estado): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void gestion_convenio() {
        try {
            if (opcion_gestion == 1) {
                this.insertar_gestion_convenio();
            }
            if (opcion_gestion == 2) {
                this.modificar_gestion_convenio();
            }
            if (opcion_gestion == 3) {
                this.modificar_gestion_convenio_estado();
            }

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(gestion_convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void insertar_gestion_convenio() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_pago_inicial.getYear() + 1900, this.fecha_pago_inicial.getMonth(), this.fecha_pago_inicial.getDate());
            XMLGregorianCalendar gre_fecha_pago_inicial = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.convenioInsertar(id_usuario, this.deudor, this.tipo_convenio, this.estado_convenio, this.saldo, this.interes, this.mora, this.gasto_otros, this.rebaja_interes, this.subtotal_pagar, this.costas, this.monto_costas, this.total, this.cuota_inicial, this.total_pagar, this.numero_cuotas, this.monto_cuota, this.frecuencia, gre_fecha_pago_inicial, this.observacion, this.ambiente);

            this.limpiar_expediente_convenio();
            this.Actualizar_Expediente_convenio();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(insertar_gestion_convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_convenio() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_pago_inicial.getYear() + 1900, this.fecha_pago_inicial.getMonth(), this.fecha_pago_inicial.getDate());
            XMLGregorianCalendar gre_fecha_pago_inicial = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.convenioModificar(id_usuario, this.convenio_sel.getIndice(), this.deudor, this.tipo_convenio, this.estado_convenio, this.saldo, this.interes, this.mora, this.gasto_otros, this.rebaja_interes, this.subtotal_pagar, this.costas, this.monto_costas, this.total, this.cuota_inicial, this.total_pagar, this.numero_cuotas, this.monto_cuota, this.frecuencia, gre_fecha_pago_inicial, this.observacion, this.ambiente);

            this.limpiar_expediente_convenio();
            this.Actualizar_Expediente_convenio();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(modificar_gestion_convenio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_convenio_estado() {
        try {
            if (this.estado_convenio_temp.equals(this.estado_convenio)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "No se realizo ningun cambio en el estado del convenio"));
            } else {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                Servicio servicio = new Servicio();
                String resultado = servicio.convenioModificarEstado(id_usuario, this.convenio_sel.getIndice(), this.estado_convenio, this.ambiente);

                this.limpiar_expediente_convenio();
                this.Actualizar_Expediente_convenio();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(modificar_gestion_convenio_estado): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void calcula_valores_modificar() {
        try {
            calcular_saldos();
            generar_observacion();
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(calcula_valores_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void calcula_valores_modificar_estado() {
        try {
            calcular_saldos();
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(calcula_valores_modificar_estado): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void calcular_saldos() {
        try {
            this.subtotal_pagar = this.saldo + this.interes + this.mora + this.gasto_otros - this.rebaja_interes;
            this.monto_costas = this.subtotal_pagar * (this.costas / 100);
            this.total = this.subtotal_pagar + monto_costas;
            this.total_pagar = this.total - this.cuota_inicial;
            this.monto_cuota = total_pagar / numero_cuotas;
            if (this.monto_cuota.floatValue() > this.monto_cuota.intValue()) {
                Integer temp = this.monto_cuota.intValue();
                this.monto_cuota = temp + 1.00;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(calcular_saldos): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void generar_observacion() {
        try {
            Date actual = new Date();

            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (this.tipo_convenio.equals("NORMAL")) {
                this.observacion = dateFormat.format(actual) + " CONVENIO: \n"
                        + "Total a Pagar Q. " + formatter.format(total_pagar) + ". \n"
                        + "Se pactó pago inicial de Q. " + formatter.format(cuota_inicial) + " y \n"
                        + numero_cuotas + " cuotas " + this.frecuencia + " de Q. " + formatter.format(this.monto_cuota) + ". \n"
                        + "Se inician pagos el " + dateFormat.format(this.fecha_pago_inicial);
            }
            if (this.tipo_convenio.equals("CANCELACION TOTAL")) {
                this.observacion = dateFormat.format(actual) + " CONVENIO: \n"
                        + "Saldo Q. " + formatter.format(saldo) + " \n"
                        + "Rebaja Autorizada de Q. " + formatter.format(rebaja_interes) + " \n"
                        + "Costas Q. " + formatter.format(monto_costas) + " \n"
                        + "Cancelación Total Autorizada por Q. " + formatter.format(total_pagar) + " \n"
                        + "Pago el " + dateFormat.format(this.fecha_pago_inicial);
            }
            if (this.tipo_convenio.equals("TRANSACCION")) {
                this.observacion = dateFormat.format(actual) + " CONVENIO: \n"
                        + "Cancelación Total Transacción Judicial por Q. " + formatter.format(total) + " por medio \n"
                        + "Deudor " + formatter.format(costas) + "% Costas  Q. " + formatter.format(cuota_inicial) + " \n"
                        + "Restante Transacción Q. " + formatter.format(this.monto_cuota) + " \n"
                        + "Pago el " + dateFormat.format(this.fecha_pago_inicial);
            }
            if (this.tipo_convenio.equals("TEMPORAL")) {
                this.observacion = dateFormat.format(actual) + " CONVENIO: \n"
                        + "Se pactaron Cuota Inicial de Q. " + formatter.format(cuota_inicial) + " y \n"
                        + numero_cuotas + " cuotas temporales de Q. " + formatter.format(cuota_inicial) + " y pagos " + this.frecuencia + " \n"
                        + "Se inician pagos el " + dateFormat.format(this.fecha_pago_inicial) + ". \n"
                        + "Al finalizar estos pagos se realizará convenio normal.";
            }
            if (this.tipo_convenio.equals("PAGOS SIN CONVENIO")) {
                this.observacion = dateFormat.format(actual) + " Pagos Sin Convenio. \n"
                        + "Caso con gestiones Anteriores, \n"
                        + "con Demanda o Sin Demanda (modificar manualmente). \n"
                        + "Cliente ilocalizado o Cliente localizado \n"
                        + "pero no pacto forma de pago.";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Convenio(generar_observacion): " + ex.toString());
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

    public List<Convenio_List> getLst_convenio() {
        return lst_convenio;
    }

    public void setLst_convenio(List<Convenio_List> lst_convenio) {
        this.lst_convenio = lst_convenio;
    }

    public Convenio_List getConvenio_sel() {
        return convenio_sel;
    }

    public void setConvenio_sel(Convenio_List convenio_sel) {
        this.convenio_sel = convenio_sel;
    }

    public String getLb_numero_convenio() {
        return lb_numero_convenio;
    }

    public void setLb_numero_convenio(String lb_numero_convenio) {
        this.lb_numero_convenio = lb_numero_convenio;
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

    public String getTipo_convenio() {
        return tipo_convenio;
    }

    public void setTipo_convenio(String tipo_convenio) {
        this.tipo_convenio = tipo_convenio;
    }

    public List<SelectItem> getLst_tipo_convenio() {
        return lst_tipo_convenio;
    }

    public void setLst_tipo_convenio(List<SelectItem> lst_tipo_convenio) {
        this.lst_tipo_convenio = lst_tipo_convenio;
    }

    public String getEstado_convenio() {
        return estado_convenio;
    }

    public void setEstado_convenio(String estado_convenio) {
        this.estado_convenio = estado_convenio;
    }

    public String getEstado_convenio_temp() {
        return estado_convenio_temp;
    }

    public void setEstado_convenio_temp(String estado_convenio_temp) {
        this.estado_convenio_temp = estado_convenio_temp;
    }

    public List<SelectItem> getLst_estado_convenio() {
        return lst_estado_convenio;
    }

    public void setLst_estado_convenio(List<SelectItem> lst_estado_convenio) {
        this.lst_estado_convenio = lst_estado_convenio;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public Double getGasto_otros() {
        return gasto_otros;
    }

    public void setGasto_otros(Double gasto_otros) {
        this.gasto_otros = gasto_otros;
    }

    public Double getRebaja_interes() {
        return rebaja_interes;
    }

    public void setRebaja_interes(Double rebaja_interes) {
        this.rebaja_interes = rebaja_interes;
    }

    public Double getSubtotal_pagar() {
        return subtotal_pagar;
    }

    public void setSubtotal_pagar(Double subtotal_pagar) {
        this.subtotal_pagar = subtotal_pagar;
    }

    public Double getCostas() {
        return costas;
    }

    public void setCostas(Double costas) {
        this.costas = costas;
    }

    public Double getMonto_costas() {
        return monto_costas;
    }

    public void setMonto_costas(Double monto_costas) {
        this.monto_costas = monto_costas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCuota_inicial() {
        return cuota_inicial;
    }

    public void setCuota_inicial(Double cuota_inicial) {
        this.cuota_inicial = cuota_inicial;
    }

    public Double getTotal_pagar() {
        return total_pagar;
    }

    public void setTotal_pagar(Double total_pagar) {
        this.total_pagar = total_pagar;
    }

    public Integer getNumero_cuotas() {
        return numero_cuotas;
    }

    public void setNumero_cuotas(Integer numero_cuotas) {
        this.numero_cuotas = numero_cuotas;
    }

    public Double getMonto_cuota() {
        return monto_cuota;
    }

    public void setMonto_cuota(Double monto_cuota) {
        this.monto_cuota = monto_cuota;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public List<SelectItem> getLst_frecuencia() {
        return lst_frecuencia;
    }

    public void setLst_frecuencia(List<SelectItem> lst_frecuencia) {
        this.lst_frecuencia = lst_frecuencia;
    }

    public Date getFecha_pago_inicial() {
        return fecha_pago_inicial;
    }

    public void setFecha_pago_inicial(Date fecha_pago_inicial) {
        this.fecha_pago_inicial = fecha_pago_inicial;
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

    public Boolean getSomTipoConvenio() {
        return somTipoConvenio;
    }

    public void setSomTipoConvenio(Boolean somTipoConvenio) {
        this.somTipoConvenio = somTipoConvenio;
    }

    public Boolean getSomEstadoConvenio() {
        return somEstadoConvenio;
    }

    public void setSomEstadoConvenio(Boolean somEstadoConvenio) {
        this.somEstadoConvenio = somEstadoConvenio;
    }

    public Boolean getSpnSaldoDeudor() {
        return spnSaldoDeudor;
    }

    public void setSpnSaldoDeudor(Boolean spnSaldoDeudor) {
        this.spnSaldoDeudor = spnSaldoDeudor;
    }

    public Boolean getSpnInteres() {
        return spnInteres;
    }

    public void setSpnInteres(Boolean spnInteres) {
        this.spnInteres = spnInteres;
    }

    public Boolean getSpnMora() {
        return spnMora;
    }

    public void setSpnMora(Boolean spnMora) {
        this.spnMora = spnMora;
    }

    public Boolean getSpnGastosOtros() {
        return spnGastosOtros;
    }

    public void setSpnGastosOtros(Boolean spnGastosOtros) {
        this.spnGastosOtros = spnGastosOtros;
    }

    public Boolean getSpnRebajaIntereses() {
        return spnRebajaIntereses;
    }

    public void setSpnRebajaIntereses(Boolean spnRebajaIntereses) {
        this.spnRebajaIntereses = spnRebajaIntereses;
    }

    public Boolean getSpnCostas() {
        return spnCostas;
    }

    public void setSpnCostas(Boolean spnCostas) {
        this.spnCostas = spnCostas;
    }

    public Boolean getSpnCuotaInicial() {
        return spnCuotaInicial;
    }

    public void setSpnCuotaInicial(Boolean spnCuotaInicial) {
        this.spnCuotaInicial = spnCuotaInicial;
    }

    public Boolean getSpnNoCuotas() {
        return spnNoCuotas;
    }

    public void setSpnNoCuotas(Boolean spnNoCuotas) {
        this.spnNoCuotas = spnNoCuotas;
    }

    public Boolean getSomFrecuencia() {
        return somFrecuencia;
    }

    public void setSomFrecuencia(Boolean somFrecuencia) {
        this.somFrecuencia = somFrecuencia;
    }

    public Boolean getCalFechaPagoInicial() {
        return calFechaPagoInicial;
    }

    public void setCalFechaPagoInicial(Boolean calFechaPagoInicial) {
        this.calFechaPagoInicial = calFechaPagoInicial;
    }

    public Boolean getAreObservacionConvenio() {
        return areObservacionConvenio;
    }

    public void setAreObservacionConvenio(Boolean areObservacionConvenio) {
        this.areObservacionConvenio = areObservacionConvenio;
    }

    public Boolean getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(Boolean btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

}
