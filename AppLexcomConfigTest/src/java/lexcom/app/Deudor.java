package lexcom.app;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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

@ManagedBean(name = "Deudor")
@ViewScoped
public class Deudor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private String opcion;

    private List<Deudor_List> lst_deudor;
    private Deudor_List selectedDeudor;
    private Boolean solo_cargados;

    // Variables para registrar la información del usuario.
    private Integer actor_d;
    private String moneda_d;
    private String dpi_d;
    private String nit_d;
    private Date fecha_nacimiento_d;
    private String nombre_d;
    private String nacionalidad_d;
    private String telefono_casa_d;
    private String telefono_celular_d;
    private String direccion_d;
    private Integer zona_d;
    private String pais_d;
    private String departamento_d;
    private String sexo_d;
    private String estado_civil_d;
    private Date fecha_ingreso_d;
    private String profesion_d;
    private String correo_electronico_d;
    private String lugar_trabajo_d;
    private String direccion_trabajo_d;
    private String telefono_trabajo_d;
    private Double monto_inicial_d;
    private Integer gestor_d;
    private Integer sestado_d;
    private Integer estatus_d;
    private String no_cuenta_d;
    private Integer garantia_d;
    private String cargado_d;
    private String estado_d;
    private String descripcion_d;
    private Integer codigo_contactabilidad_d;
    private Integer caso_d;
    private Boolean PDF_d;
    private Boolean INV_d;
    private Boolean MAYCOM_d;
    private Boolean NITS_d;
    private String cosecha_d;
    private String no_cuenta_otro_d;
    private String descripcion_adicional_d;
    private Date fecha_recepcion_d;
    private String anticipo_d;
    private String antiguedad_d;
    private Date fecha_proximo_pago_d;
    private Double monto_proximo_pago_d;
    private Double saldo_d;
    private String convenio_pactado_d;
    private Double cuota_convenio_d;
    private String costas_pagadas_d;
    private String situacion_caso_d;
    private Boolean opcion_proximo_pago_d;
    private Integer sestado_extra_d;
    private Integer estatus_extra_d;
    private Integer intencion_pago_d;
    private Integer razon_deuda_d;
    private Boolean cuenta_principal_relacion_d;
    private Integer deudor_cuenta_relacionada_d;

    //Habilitar los campos del formulario Usuario.
    private Boolean cbxActor;
    private Boolean cbxMoneda;
    private Boolean txtDpi;
    private Boolean txtNit;
    private Boolean dateFechaNacimiento;
    private Boolean txtNombre;
    private Boolean cbxNacionalidad;
    private Boolean txtTelefonoCasa;
    private Boolean txtTelefonoCelular;
    private Boolean txtDireccion;
    private Boolean SpnZona;
    private Boolean cbxPais;
    private Boolean cbxDepartamento;
    private Boolean cbxSexo;
    private Boolean cbxEstadoCivil;
    private Boolean dateFechaIngreso;
    private Boolean txtProfesion;
    private Boolean txtCorreoElectronico;
    private Boolean txtLugarTrabajo;
    private Boolean txtDireccionTrabajo;
    private Boolean txtTelefonoTrabajo;
    private Boolean spnMontoInicial;
    private Boolean cbxGestor;
    private Boolean cbxEstadoJudicial;
    private Boolean cbxEstatusJudicial;
    private Boolean txtNoCuenta;
    private Boolean cbxGarantia;
    private Boolean cbxCargado;
    private Boolean cbxEstado;
    private Boolean areDescripcion;
    private Boolean cbxCodigoContactabilidad;
    private Boolean spnCaso;
    private Boolean chkPDF;
    private Boolean chkINV;
    private Boolean chkMAYCOM;
    private Boolean chkNITS;
    private Boolean cbxCosecha;
    private Boolean txtNoCuentaOtro;
    private Boolean areDescripcionAdicional;
    private Boolean dateFechaRecepcion;
    private Boolean cbxAnticipo;
    private Boolean cbxAntiguedad;
    private Boolean dateFechaProximoPago;
    private Boolean spnMontoProximoPago;
    private Boolean spnSaldo;
    private Boolean areConvenioPactado;
    private Boolean spnCuotaConvenio;
    private Boolean areCostasPagadas;
    private Boolean areSituacionCaso;
    private Boolean chkOpcionProximoPago;
    private Boolean cbxEstadoExtrajudicial;
    private Boolean cbxEstatusExtrajudicial;
    private Boolean cbxIntencionPago;
    private Boolean cbxRazonDeuda;
    private Boolean chkCuentaPrincipalRelacion;
    private Boolean txtCuentaRelacionada;
    private Boolean btnAceptar;
    private Boolean btnCancelar;

    private List<SelectItem> lst_actor = new ArrayList<>();
    private List<SelectItem> lst_moneda = new ArrayList<>();
    private List<SelectItem> lst_nacionalidad = new ArrayList<>();
    private List<SelectItem> lst_pais = new ArrayList<>();
    private List<SelectItem> lst_departamento = new ArrayList<>();
    private List<SelectItem> lst_sexo = new ArrayList<>();
    private List<SelectItem> lst_estado_civil = new ArrayList<>();
    private List<SelectItem> lst_gestor = new ArrayList<>();
    private List<SelectItem> lst_garantia = new ArrayList<>();
    private List<SelectItem> lst_cargado = new ArrayList<>();
    private List<SelectItem> lst_codigo_resultado = new ArrayList<>();
    private List<SelectItem> lst_cosecha = new ArrayList<>();
    private List<SelectItem> lst_anticipo = new ArrayList<>();
    private List<SelectItem> lst_antiguedad = new ArrayList<>();
    private List<SelectItem> lst_estado_judicial = new ArrayList<>();
    private List<SelectItem> lst_estatus_judicial = new ArrayList<>();
    private List<SelectItem> lst_estado_extrajudicial = new ArrayList<>();
    private List<SelectItem> lst_estatus_extrajudicial = new ArrayList<>();
    private List<SelectItem> lst_intension_pago = new ArrayList<>();
    private List<SelectItem> lst_razon_deuda = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            this.solo_cargados = true;
            this.cargar_tabla();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_tabla() {
        try {
            String condicion_cargados = "";
            if (this.solo_cargados) {
                condicion_cargados = "d.cargado like 'CARGADO'";
            } else {
                condicion_cargados = "d.cargado like '%%'";
            }

            String cadenasql = "select "
                    + "d.deudor, "
                    + "a.nombre, "
                    + "d.caso, "
                    + "d.nombre, "
                    + "d.no_cuenta, "
                    + "d.no_cuenta_otro, "
                    + "d.cargado as cargado "
                    + "from "
                    + "deudor d left join actor a on (d.actor = a.actor) "
                    + "where "
                    + condicion_cargados;

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

            this.lst_deudor = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Deudor_List deu = new Deudor_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                this.lst_deudor.add(deu);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        try {
            Driver drive = new Driver();
            String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
            this.lst_actor = drive.lista_SelectItem_simple(lista_actor_sql, this.ambiente);
            String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
            this.lst_gestor = drive.lista_SelectItem_simple(lista_gestor_sql, this.ambiente);
            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE' order by g.nombre";
            this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
            String lista_codigo_resultado_sql = "select cc.codigo_contactabilidad, cc.nombre from codigo_contactabilidad cc where cc.estado='VIGENTE' order by cc.nombre";
            this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE' order by s.nombre";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            String lista_estatus_judicial_sql = "select e.estatus, e.nombre from estatus e where e.estado='VIGENTE' order by e.estatus";
            this.lst_estatus_judicial = drive.lista_SelectItem_simple(lista_estatus_judicial_sql, this.ambiente);
            String lista_estado_extrajudicial_sql = "select se.sestado_extra, se.nombre from sestado_extra se where se.estado='VIGENTE' order by se.nombre";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
            String lista_estatus_extrajudicial_sql = "select ee.estatus_extra, ee.nombre from estatus_extra ee where ee.estado='VIGENTE' order by ee.nombre";
            this.lst_estatus_extrajudicial = drive.lista_SelectItem_simple(lista_estatus_extrajudicial_sql, this.ambiente);
            String lista_intension_pago_sql = "select ip.intencion_pago, ip.nombre from intencion_pago ip where ip.estado='VIGENTE' order by ip.nombre";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intension_pago_sql, this.ambiente);
            String lista_razon_deuda_sql = "select rd.razon_deuda, rd.nombre from razon_deuda rd where rd.estado='VIGENTE' order by rd.nombre";
            this.lst_razon_deuda = drive.lista_SelectItem_simple(lista_razon_deuda_sql, this.ambiente);
            String lista_antiguedad_sql = "select a.nombre, a.nombre from antiguedad a where a.estado='VIGENTE' order by a.nombre";
            this.lst_antiguedad = drive.lista_SelectItem_simple(lista_antiguedad_sql, this.ambiente);

            this.lst_moneda = drive.lista_moneda();
            this.lst_nacionalidad = drive.lista_nacionalidad();
            this.lst_pais = drive.lista_paises();
            this.lst_departamento = drive.lista_departamentos();
            this.lst_sexo = drive.lista_sexo();
            this.lst_estado_civil = drive.lista_estado_civil();
            this.lst_cosecha = drive.lista_cosechas();
            this.lst_cargado = drive.lista_cargado();
            this.lst_anticipo = drive.lista_anticipo();

            Date fecha_actual = new Date();

            this.actor_d = 0;
            this.moneda_d = "QUETZAL";
            this.dpi_d = "-";
            this.nit_d = "-";
            this.fecha_nacimiento_d = fecha_actual;
            this.nombre_d = "";
            this.nacionalidad_d = "GUATEMALTECO(A)";
            this.telefono_casa_d = "-";
            this.telefono_celular_d = "-";
            this.direccion_d = "-";
            this.zona_d = 0;
            this.pais_d = "Guatemala";
            this.departamento_d = "Guatemala";
            this.sexo_d = "-";
            this.estado_civil_d = "-";
            this.fecha_ingreso_d = fecha_actual;
            this.profesion_d = "-";
            this.correo_electronico_d = "-";
            this.lugar_trabajo_d = "-";
            this.direccion_trabajo_d = "-";
            this.telefono_trabajo_d = "-";
            this.monto_inicial_d = 0.00;
            this.gestor_d = 0;
            this.sestado_d = 2;
            this.estatus_d = 60;
            this.no_cuenta_d = "";
            this.garantia_d = 0;
            this.cargado_d = "CARGADO";
            this.estado_d = "VIGENTE";
            this.descripcion_d = "-";
            this.codigo_contactabilidad_d = 1;
            this.caso_d = 0;
            this.PDF_d = false;
            this.INV_d = false;
            this.MAYCOM_d = false;
            this.NITS_d = false;
            this.cosecha_d = "0 antes 31 dic 2008";
            this.no_cuenta_otro_d = "0";
            this.descripcion_adicional_d = "-";
            this.fecha_recepcion_d = fecha_actual;
            this.anticipo_d = "NO";
            this.antiguedad_d = "BRONCE";
            this.fecha_proximo_pago_d = new Date();
            this.monto_proximo_pago_d = 0.00;
            this.saldo_d = 0.00;
            this.convenio_pactado_d = "-";
            this.cuota_convenio_d = 0.00;
            this.costas_pagadas_d = "-";
            this.situacion_caso_d = "-";
            this.opcion_proximo_pago_d = false;
            this.sestado_extra_d = 2;
            this.estatus_extra_d = 2;
            this.intencion_pago_d = 4;
            this.razon_deuda_d = 1;
            this.cuenta_principal_relacion_d = false;
            this.deudor_cuenta_relacionada_d = 0;

            this.cbxActor = false;
            this.cbxMoneda = false;
            this.txtDpi = false;
            this.txtNit = false;
            this.dateFechaNacimiento = false;
            this.txtNombre = false;
            this.cbxNacionalidad = false;
            this.txtTelefonoCasa = false;
            this.txtTelefonoCelular = false;
            this.txtDireccion = false;
            this.SpnZona = false;
            this.cbxPais = false;
            this.cbxDepartamento = false;
            this.cbxSexo = false;
            this.cbxEstadoCivil = false;
            this.dateFechaIngreso = true;
            this.txtProfesion = false;
            this.txtCorreoElectronico = false;
            this.txtLugarTrabajo = false;
            this.txtDireccionTrabajo = false;
            this.txtTelefonoTrabajo = false;
            this.spnMontoInicial = false;
            this.cbxGestor = false;
            this.cbxEstadoJudicial = true;
            this.cbxEstatusJudicial = true;
            this.txtNoCuenta = false;
            this.cbxGarantia = false;
            this.cbxCargado = false;
            this.cbxEstado = true;
            this.areDescripcion = false;
            this.cbxCodigoContactabilidad = false;
            this.spnCaso = false;
            this.chkPDF = false;
            this.chkINV = false;
            this.chkMAYCOM = false;
            this.chkNITS = false;
            this.cbxCosecha = false;
            this.txtNoCuentaOtro = false;
            this.areDescripcionAdicional = false;
            this.dateFechaRecepcion = false;
            this.cbxAnticipo = false;
            this.cbxAntiguedad = false;
            this.dateFechaProximoPago = true;
            this.spnMontoProximoPago = true;
            this.spnSaldo = false;
            this.areConvenioPactado = true;
            this.spnCuotaConvenio = true;
            this.areCostasPagadas = false;
            this.areSituacionCaso = true;
            this.chkOpcionProximoPago = true;
            this.cbxEstadoExtrajudicial = true;
            this.cbxEstatusExtrajudicial = true;
            this.cbxIntencionPago = false;
            this.cbxRazonDeuda = false;
            this.chkCuentaPrincipalRelacion = false;
            this.txtCuentaRelacionada = false;

            this.btnAceptar = false;
            this.btnCancelar = false;

            this.opcion = "INSERTAR";

            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_modificar() {
        try {
            if (this.selectedDeudor != null) {
                Driver drive = new Driver();
                String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
                this.lst_actor = drive.lista_SelectItem_simple(lista_actor_sql, this.ambiente);
                String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
                this.lst_gestor = drive.lista_SelectItem_simple(lista_gestor_sql, this.ambiente);
                String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE' order by g.nombre";
                this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
                String lista_codigo_resultado_sql = "select cc.codigo_contactabilidad, cc.nombre from codigo_contactabilidad cc where cc.estado='VIGENTE' order by cc.nombre";
                this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
                String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE' order by s.nombre";
                this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
                String lista_estatus_judicial_sql = "select e.estatus, e.nombre from estatus e where e.estado='VIGENTE' order by e.estatus";
                this.lst_estatus_judicial = drive.lista_SelectItem_simple(lista_estatus_judicial_sql, this.ambiente);
                String lista_estado_extrajudicial_sql = "select se.sestado_extra, se.nombre from sestado_extra se where se.estado='VIGENTE' order by se.nombre";
                this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
                String lista_estatus_extrajudicial_sql = "select ee.estatus_extra, ee.nombre from estatus_extra ee where ee.estado='VIGENTE' order by ee.nombre";
                this.lst_estatus_extrajudicial = drive.lista_SelectItem_simple(lista_estatus_extrajudicial_sql, this.ambiente);
                String lista_intension_pago_sql = "select ip.intencion_pago, ip.nombre from intencion_pago ip where ip.estado='VIGENTE' order by ip.nombre";
                this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intension_pago_sql, this.ambiente);
                String lista_razon_deuda_sql = "select rd.razon_deuda, rd.nombre from razon_deuda rd where rd.estado='VIGENTE' order by rd.nombre";
                this.lst_razon_deuda = drive.lista_SelectItem_simple(lista_razon_deuda_sql, this.ambiente);
                String lista_antiguedad_sql = "select a.nombre, a.nombre from antiguedad a where a.estado='VIGENTE' order by a.nombre";
                this.lst_antiguedad = drive.lista_SelectItem_simple(lista_antiguedad_sql, this.ambiente);

                this.lst_moneda = drive.lista_moneda();
                this.lst_nacionalidad = drive.lista_nacionalidad();
                this.lst_pais = drive.lista_paises();
                this.lst_departamento = drive.lista_departamentos();
                this.lst_sexo = drive.lista_sexo();
                this.lst_estado_civil = drive.lista_estado_civil();
                this.lst_cosecha = drive.lista_cosechas();
                this.lst_cargado = drive.lista_cargado();
                this.lst_anticipo = drive.lista_anticipo();

                String cadenasql = "select "
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
                        + "d.sestado_extra, "
                        + "d.estatus_extra, "
                        + "d.intencion_pago, "
                        + "d.razon_deuda, "
                        + "d.cuenta_principal_relacion, "
                        + "ifnull(d.deudor_cuenta_relacionada,0) deudor_cuenta_relacionada "
                        + "from "
                        + "deudor d "
                        + "where "
                        + "d.deudor=" + this.selectedDeudor.getDeudor();
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

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (Integer i = 1; i < filas; i++) {
                    this.actor_d = Integer.parseInt(vector_result[i][0]);
                    this.moneda_d = vector_result[i][1];
                    this.dpi_d = vector_result[i][2];
                    this.nit_d = vector_result[i][3];
                    this.fecha_nacimiento_d = formatter.parse(vector_result[i][4]);
                    this.nombre_d = vector_result[i][5];
                    this.nacionalidad_d = vector_result[i][6];
                    this.telefono_casa_d = vector_result[i][7];
                    this.telefono_celular_d = vector_result[i][8];
                    this.direccion_d = vector_result[i][9];
                    this.zona_d = Integer.parseInt(vector_result[i][10]);
                    this.pais_d = vector_result[i][11];
                    this.departamento_d = vector_result[i][12];
                    this.sexo_d = vector_result[i][13];
                    this.estado_civil_d = vector_result[i][14];
                    this.fecha_ingreso_d = formatter.parse(vector_result[i][15]);
                    this.profesion_d = vector_result[i][16];
                    this.correo_electronico_d = vector_result[i][17];
                    this.lugar_trabajo_d = vector_result[i][18];
                    this.direccion_trabajo_d = vector_result[i][19];
                    this.telefono_trabajo_d = vector_result[i][20];
                    this.monto_inicial_d = Double.parseDouble(vector_result[i][21]);
                    this.gestor_d = Integer.parseInt(vector_result[i][22]);
                    this.sestado_d = Integer.parseInt(vector_result[i][23]);
                    this.estatus_d = Integer.parseInt(vector_result[i][24]);
                    this.no_cuenta_d = vector_result[i][25];
                    this.garantia_d = Integer.parseInt(vector_result[i][26]);
                    this.cargado_d = vector_result[i][27];
                    this.estado_d = vector_result[i][28];
                    this.descripcion_d = vector_result[i][29];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][30]);
                    this.caso_d = Integer.parseInt(vector_result[i][31]);
                    if (vector_result[i][32].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][35].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][36];
                    this.no_cuenta_otro_d = vector_result[i][37];
                    this.descripcion_adicional_d = vector_result[i][38];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][39]);
                    this.anticipo_d = vector_result[i][40];
                    this.antiguedad_d = vector_result[i][41];
                    this.fecha_proximo_pago_d = formatter.parse(vector_result[i][42]);
                    this.monto_proximo_pago_d = Double.parseDouble(vector_result[i][43]);
                    this.saldo_d = Double.parseDouble(vector_result[i][44]);
                    this.convenio_pactado_d = vector_result[i][45];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][46]);
                    this.costas_pagadas_d = vector_result[i][47];
                    this.situacion_caso_d = vector_result[i][48];
                    if (vector_result[i][49].equals("SI")) {
                        this.opcion_proximo_pago_d = true;
                    } else {
                        this.opcion_proximo_pago_d = false;
                    }
                    this.sestado_extra_d = Integer.parseInt(vector_result[i][50]);
                    this.estatus_extra_d = Integer.parseInt(vector_result[i][51]);
                    this.intencion_pago_d = Integer.parseInt(vector_result[i][52]);
                    this.razon_deuda_d = Integer.parseInt(vector_result[i][53]);
                    if (vector_result[i][54].equals("1")) {
                        this.cuenta_principal_relacion_d = true;
                        this.txtCuentaRelacionada = true;
                    } else {
                        this.cuenta_principal_relacion_d = false;
                        this.txtCuentaRelacionada = false;
                    }
                    if(this.cuenta_principal_relacion_d) {
                        this.deudor_cuenta_relacionada_d = 0;
                    } else {
                        this.deudor_cuenta_relacionada_d = Integer.parseInt(vector_result[i][55]);
                    }
                }

                this.cbxActor = false;
                this.cbxMoneda = false;
                this.txtDpi = false;
                this.txtNit = false;
                this.dateFechaNacimiento = false;
                this.txtNombre = false;
                this.cbxNacionalidad = false;
                this.txtTelefonoCasa = false;
                this.txtTelefonoCelular = false;
                this.txtDireccion = false;
                this.SpnZona = false;
                this.cbxPais = false;
                this.cbxDepartamento = false;
                this.cbxSexo = false;
                this.cbxEstadoCivil = false;
                this.dateFechaIngreso = true;
                this.txtProfesion = false;
                this.txtCorreoElectronico = false;
                this.txtLugarTrabajo = false;
                this.txtDireccionTrabajo = false;
                this.txtTelefonoTrabajo = false;
                this.spnMontoInicial = false;
                this.cbxGestor = false;
                this.cbxEstadoJudicial = true;
                this.cbxEstatusJudicial = true;
                this.txtNoCuenta = false;
                this.cbxGarantia = false;
                this.cbxCargado = false;
                this.cbxEstado = true;
                this.areDescripcion = false;
                this.cbxCodigoContactabilidad = false;
                this.spnCaso = false;
                this.chkPDF = false;
                this.chkINV = false;
                this.chkMAYCOM = false;
                this.chkNITS = false;
                this.cbxCosecha = false;
                this.txtNoCuentaOtro = false;
                this.areDescripcionAdicional = false;
                this.dateFechaRecepcion = false;
                this.cbxAnticipo = false;
                this.cbxAntiguedad = false;
                this.dateFechaProximoPago = true;
                this.spnMontoProximoPago = true;
                this.spnSaldo = false;
                this.areConvenioPactado = true;
                this.spnCuotaConvenio = true;
                this.areCostasPagadas = false;
                this.areSituacionCaso = true;
                this.chkOpcionProximoPago = true;
                this.cbxEstadoExtrajudicial = true;
                this.cbxEstatusExtrajudicial = true;
                this.cbxIntencionPago = false;
                this.cbxRazonDeuda = false;
                this.chkCuentaPrincipalRelacion = false;

                this.btnAceptar = false;
                this.btnCancelar = false;

                this.opcion = "MODIFICAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un deudor del listado."));
            }
        } catch (NumberFormatException | ParseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_eliminar() {
        try {
            if (this.selectedDeudor != null) {
                Driver drive = new Driver();
                String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
                this.lst_actor = drive.lista_SelectItem_simple(lista_actor_sql, this.ambiente);
                String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
                this.lst_gestor = drive.lista_SelectItem_simple(lista_gestor_sql, this.ambiente);
                String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE' order by g.nombre";
                this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
                String lista_codigo_resultado_sql = "select cc.codigo_contactabilidad, cc.nombre from codigo_contactabilidad cc where cc.estado='VIGENTE' order by cc.nombre";
                this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
                String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE' order by s.nombre";
                this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
                String lista_estatus_judicial_sql = "select e.estatus, e.nombre from estatus e where e.estado='VIGENTE' order by e.estatus";
                this.lst_estatus_judicial = drive.lista_SelectItem_simple(lista_estatus_judicial_sql, this.ambiente);
                String lista_estado_extrajudicial_sql = "select se.sestado_extra, se.nombre from sestado_extra se where se.estado='VIGENTE' order by se.nombre";
                this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
                String lista_estatus_extrajudicial_sql = "select ee.estatus_extra, ee.nombre from estatus_extra ee where ee.estado='VIGENTE' order by ee.nombre";
                this.lst_estatus_extrajudicial = drive.lista_SelectItem_simple(lista_estatus_extrajudicial_sql, this.ambiente);
                String lista_intension_pago_sql = "select ip.intencion_pago, ip.nombre from intencion_pago ip where ip.estado='VIGENTE' order by ip.nombre";
                this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intension_pago_sql, this.ambiente);
                String lista_razon_deuda_sql = "select rd.razon_deuda, rd.nombre from razon_deuda rd where rd.estado='VIGENTE' order by rd.nombre";
                this.lst_razon_deuda = drive.lista_SelectItem_simple(lista_razon_deuda_sql, this.ambiente);
                String lista_antiguedad_sql = "select a.nombre, a.nombre from antiguedad a where a.estado='VIGENTE' order by a.nombre";
                this.lst_antiguedad = drive.lista_SelectItem_simple(lista_antiguedad_sql, this.ambiente);

                this.lst_moneda = drive.lista_moneda();
                this.lst_nacionalidad = drive.lista_nacionalidad();
                this.lst_pais = drive.lista_paises();
                this.lst_departamento = drive.lista_departamentos();
                this.lst_sexo = drive.lista_sexo();
                this.lst_estado_civil = drive.lista_estado_civil();
                this.lst_cosecha = drive.lista_cosechas();
                this.lst_cargado = drive.lista_cargado();
                this.lst_anticipo = drive.lista_anticipo();

                String cadenasql = "select "
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
                        + "d.sestado_extra, "
                        + "d.estatus_extra, "
                        + "d.intencion_pago, "
                        + "d.razon_deuda, "
                        + "d.cuenta_principal_relacion, "
                        + "ifnull(d.deudor_cuenta_relacionada,0) deudor_cuenta_relacionada "
                        + "from "
                        + "deudor d "
                        + "where "
                        + "d.deudor=" + this.selectedDeudor.getDeudor();
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

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (Integer i = 1; i < filas; i++) {
                    this.actor_d = Integer.parseInt(vector_result[i][0]);
                    this.moneda_d = vector_result[i][1];
                    this.dpi_d = vector_result[i][2];
                    this.nit_d = vector_result[i][3];
                    this.fecha_nacimiento_d = formatter.parse(vector_result[i][4]);
                    this.nombre_d = vector_result[i][5];
                    this.nacionalidad_d = vector_result[i][6];
                    this.telefono_casa_d = vector_result[i][7];
                    this.telefono_celular_d = vector_result[i][8];
                    this.direccion_d = vector_result[i][9];
                    this.zona_d = Integer.parseInt(vector_result[i][10]);
                    this.pais_d = vector_result[i][11];
                    this.departamento_d = vector_result[i][12];
                    this.sexo_d = vector_result[i][13];
                    this.estado_civil_d = vector_result[i][14];
                    this.fecha_ingreso_d = formatter.parse(vector_result[i][15]);
                    this.profesion_d = vector_result[i][16];
                    this.correo_electronico_d = vector_result[i][17];
                    this.lugar_trabajo_d = vector_result[i][18];
                    this.direccion_trabajo_d = vector_result[i][19];
                    this.telefono_trabajo_d = vector_result[i][20];
                    this.monto_inicial_d = Double.parseDouble(vector_result[i][21]);
                    this.gestor_d = Integer.parseInt(vector_result[i][22]);
                    this.sestado_d = Integer.parseInt(vector_result[i][23]);
                    this.estatus_d = Integer.parseInt(vector_result[i][24]);
                    this.no_cuenta_d = vector_result[i][25];
                    this.garantia_d = Integer.parseInt(vector_result[i][26]);
                    this.cargado_d = vector_result[i][27];
                    this.estado_d = vector_result[i][28];
                    this.descripcion_d = vector_result[i][29];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][30]);
                    this.caso_d = Integer.parseInt(vector_result[i][31]);
                    if (vector_result[i][32].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][35].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][36];
                    this.no_cuenta_otro_d = vector_result[i][37];
                    this.descripcion_adicional_d = vector_result[i][38];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][39]);
                    this.anticipo_d = vector_result[i][40];
                    this.antiguedad_d = vector_result[i][41];
                    this.fecha_proximo_pago_d = formatter.parse(vector_result[i][42]);
                    this.monto_proximo_pago_d = Double.parseDouble(vector_result[i][43]);
                    this.saldo_d = Double.parseDouble(vector_result[i][44]);
                    this.convenio_pactado_d = vector_result[i][45];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][46]);
                    this.costas_pagadas_d = vector_result[i][47];
                    this.situacion_caso_d = vector_result[i][48];
                    if (vector_result[i][49].equals("SI")) {
                        this.opcion_proximo_pago_d = true;
                    } else {
                        this.opcion_proximo_pago_d = false;
                    }
                    this.sestado_extra_d = Integer.parseInt(vector_result[i][50]);
                    this.estatus_extra_d = Integer.parseInt(vector_result[i][51]);
                    this.intencion_pago_d = Integer.parseInt(vector_result[i][52]);
                    this.razon_deuda_d = Integer.parseInt(vector_result[i][53]);
                    if (vector_result[i][54].equals("1")) {
                        this.cuenta_principal_relacion_d = true;
                    } else {
                        this.cuenta_principal_relacion_d = false;
                    }
                    this.deudor_cuenta_relacionada_d = Integer.parseInt(vector_result[i][55]);
                }

                this.cbxActor = true;
                this.cbxMoneda = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.dateFechaNacimiento = true;
                this.txtNombre = true;
                this.cbxNacionalidad = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtDireccion = true;
                this.SpnZona = true;
                this.cbxPais = true;
                this.cbxDepartamento = true;
                this.cbxSexo = true;
                this.cbxEstadoCivil = true;
                this.dateFechaIngreso = true;
                this.txtProfesion = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtDireccionTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.spnMontoInicial = true;
                this.cbxGestor = true;
                this.cbxEstadoJudicial = true;
                this.cbxEstatusJudicial = true;
                this.txtNoCuenta = true;
                this.cbxGarantia = true;
                this.cbxCargado = true;
                this.cbxEstado = true;
                this.areDescripcion = true;
                this.cbxCodigoContactabilidad = true;
                this.spnCaso = true;
                this.chkPDF = true;
                this.chkINV = true;
                this.chkMAYCOM = true;
                this.chkNITS = true;
                this.cbxCosecha = true;
                this.txtNoCuentaOtro = true;
                this.areDescripcionAdicional = true;
                this.dateFechaRecepcion = true;
                this.cbxAnticipo = true;
                this.cbxAntiguedad = true;
                this.dateFechaProximoPago = true;
                this.spnMontoProximoPago = true;
                this.spnSaldo = true;
                this.areConvenioPactado = true;
                this.spnCuotaConvenio = true;
                this.areCostasPagadas = true;
                this.areSituacionCaso = true;
                this.chkOpcionProximoPago = true;
                this.cbxEstadoExtrajudicial = true;
                this.cbxEstatusExtrajudicial = true;
                this.cbxIntencionPago = true;
                this.cbxRazonDeuda = true;
                this.chkCuentaPrincipalRelacion = true;
                this.txtCuentaRelacionada = true;

                this.btnAceptar = false;
                this.btnCancelar = false;

                this.opcion = "ELIMINAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un deudor del listado."));
            }
        } catch (NumberFormatException | ParseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_activar() {
        try {
            if (this.selectedDeudor != null) {
                Driver drive = new Driver();
                String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
                this.lst_actor = drive.lista_SelectItem_simple(lista_actor_sql, this.ambiente);
                String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
                this.lst_gestor = drive.lista_SelectItem_simple(lista_gestor_sql, this.ambiente);
                String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE' order by g.nombre";
                this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
                String lista_codigo_resultado_sql = "select cc.codigo_contactabilidad, cc.nombre from codigo_contactabilidad cc where cc.estado='VIGENTE' order by cc.nombre";
                this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
                String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE' order by s.nombre";
                this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
                String lista_estatus_judicial_sql = "select e.estatus, e.nombre from estatus e where e.estado='VIGENTE' order by e.estatus";
                this.lst_estatus_judicial = drive.lista_SelectItem_simple(lista_estatus_judicial_sql, this.ambiente);
                String lista_estado_extrajudicial_sql = "select se.sestado_extra, se.nombre from sestado_extra se where se.estado='VIGENTE' order by se.nombre";
                this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
                String lista_estatus_extrajudicial_sql = "select ee.estatus_extra, ee.nombre from estatus_extra ee where ee.estado='VIGENTE' order by ee.nombre";
                this.lst_estatus_extrajudicial = drive.lista_SelectItem_simple(lista_estatus_extrajudicial_sql, this.ambiente);
                String lista_intension_pago_sql = "select ip.intencion_pago, ip.nombre from intencion_pago ip where ip.estado='VIGENTE' order by ip.nombre";
                this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intension_pago_sql, this.ambiente);
                String lista_razon_deuda_sql = "select rd.razon_deuda, rd.nombre from razon_deuda rd where rd.estado='VIGENTE' order by rd.nombre";
                this.lst_razon_deuda = drive.lista_SelectItem_simple(lista_razon_deuda_sql, this.ambiente);
                String lista_antiguedad_sql = "select a.nombre, a.nombre from antiguedad a where a.estado='VIGENTE' order by a.nombre";
                this.lst_antiguedad = drive.lista_SelectItem_simple(lista_antiguedad_sql, this.ambiente);

                this.lst_moneda = drive.lista_moneda();
                this.lst_nacionalidad = drive.lista_nacionalidad();
                this.lst_pais = drive.lista_paises();
                this.lst_departamento = drive.lista_departamentos();
                this.lst_sexo = drive.lista_sexo();
                this.lst_estado_civil = drive.lista_estado_civil();
                this.lst_cosecha = drive.lista_cosechas();
                this.lst_cargado = drive.lista_cargado();
                this.lst_anticipo = drive.lista_anticipo();

                String cadenasql = "select "
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
                        + "d.sestado_extra, "
                        + "d.estatus_extra, "
                        + "d.intencion_pago, "
                        + "d.razon_deuda, "
                        + "d.cuenta_principal_relacion, "
                        + "ifnull(d.deudor_cuenta_relacionada,0) deudor_cuenta_relacionada "
                        + "from "
                        + "deudor d "
                        + "where "
                        + "d.deudor=" + this.selectedDeudor.getDeudor();
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

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (Integer i = 1; i < filas; i++) {
                    this.actor_d = Integer.parseInt(vector_result[i][0]);
                    this.moneda_d = vector_result[i][1];
                    this.dpi_d = vector_result[i][2];
                    this.nit_d = vector_result[i][3];
                    this.fecha_nacimiento_d = formatter.parse(vector_result[i][4]);
                    this.nombre_d = vector_result[i][5];
                    this.nacionalidad_d = vector_result[i][6];
                    this.telefono_casa_d = vector_result[i][7];
                    this.telefono_celular_d = vector_result[i][8];
                    this.direccion_d = vector_result[i][9];
                    this.zona_d = Integer.parseInt(vector_result[i][10]);
                    this.pais_d = vector_result[i][11];
                    this.departamento_d = vector_result[i][12];
                    this.sexo_d = vector_result[i][13];
                    this.estado_civil_d = vector_result[i][14];
                    this.fecha_ingreso_d = formatter.parse(vector_result[i][15]);
                    this.profesion_d = vector_result[i][16];
                    this.correo_electronico_d = vector_result[i][17];
                    this.lugar_trabajo_d = vector_result[i][18];
                    this.direccion_trabajo_d = vector_result[i][19];
                    this.telefono_trabajo_d = vector_result[i][20];
                    this.monto_inicial_d = Double.parseDouble(vector_result[i][21]);
                    this.gestor_d = Integer.parseInt(vector_result[i][22]);
                    this.sestado_d = Integer.parseInt(vector_result[i][23]);
                    this.estatus_d = Integer.parseInt(vector_result[i][24]);
                    this.no_cuenta_d = vector_result[i][25];
                    this.garantia_d = Integer.parseInt(vector_result[i][26]);
                    this.cargado_d = vector_result[i][27];
                    this.estado_d = vector_result[i][28];
                    this.descripcion_d = vector_result[i][29];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][30]);
                    this.caso_d = Integer.parseInt(vector_result[i][31]);
                    if (vector_result[i][32].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][35].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][36];
                    this.no_cuenta_otro_d = vector_result[i][37];
                    this.descripcion_adicional_d = vector_result[i][38];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][39]);
                    this.anticipo_d = vector_result[i][40];
                    this.antiguedad_d = vector_result[i][41];
                    this.fecha_proximo_pago_d = formatter.parse(vector_result[i][42]);
                    this.monto_proximo_pago_d = Double.parseDouble(vector_result[i][43]);
                    this.saldo_d = Double.parseDouble(vector_result[i][44]);
                    this.convenio_pactado_d = vector_result[i][45];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][46]);
                    this.costas_pagadas_d = vector_result[i][47];
                    this.situacion_caso_d = vector_result[i][48];
                    if (vector_result[i][49].equals("SI")) {
                        this.opcion_proximo_pago_d = true;
                    } else {
                        this.opcion_proximo_pago_d = false;
                    }
                    this.sestado_extra_d = Integer.parseInt(vector_result[i][50]);
                    this.estatus_extra_d = Integer.parseInt(vector_result[i][51]);
                    this.intencion_pago_d = Integer.parseInt(vector_result[i][52]);
                    this.razon_deuda_d = Integer.parseInt(vector_result[i][53]);
                    if (vector_result[i][54].equals("1")) {
                        this.cuenta_principal_relacion_d = true;
                    } else {
                        this.cuenta_principal_relacion_d = false;
                    }
                    this.deudor_cuenta_relacionada_d = Integer.parseInt(vector_result[i][55]);
                }

                this.cbxActor = true;
                this.cbxMoneda = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.dateFechaNacimiento = true;
                this.txtNombre = true;
                this.cbxNacionalidad = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtDireccion = true;
                this.SpnZona = true;
                this.cbxPais = true;
                this.cbxDepartamento = true;
                this.cbxSexo = true;
                this.cbxEstadoCivil = true;
                this.dateFechaIngreso = true;
                this.txtProfesion = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtDireccionTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.spnMontoInicial = true;
                this.cbxGestor = true;
                this.cbxEstadoJudicial = true;
                this.cbxEstatusJudicial = true;
                this.txtNoCuenta = true;
                this.cbxGarantia = true;
                this.cbxCargado = true;
                this.cbxEstado = true;
                this.areDescripcion = true;
                this.cbxCodigoContactabilidad = true;
                this.spnCaso = true;
                this.chkPDF = true;
                this.chkINV = true;
                this.chkMAYCOM = true;
                this.chkNITS = true;
                this.cbxCosecha = true;
                this.txtNoCuentaOtro = true;
                this.areDescripcionAdicional = true;
                this.dateFechaRecepcion = true;
                this.cbxAnticipo = true;
                this.cbxAntiguedad = true;
                this.dateFechaProximoPago = true;
                this.spnMontoProximoPago = true;
                this.spnSaldo = true;
                this.areConvenioPactado = true;
                this.spnCuotaConvenio = true;
                this.areCostasPagadas = true;
                this.areSituacionCaso = true;
                this.chkOpcionProximoPago = true;
                this.cbxEstadoExtrajudicial = true;
                this.cbxEstatusExtrajudicial = true;
                this.cbxIntencionPago = true;
                this.cbxRazonDeuda = true;
                this.chkCuentaPrincipalRelacion = true;
                this.txtCuentaRelacionada = true;

                this.btnAceptar = false;
                this.btnCancelar = false;

                this.opcion = "ACTIVAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un deudor del listado."));
            }
        } catch (NumberFormatException | ParseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_ver() {
        try {
            if (this.selectedDeudor != null) {
                Driver drive = new Driver();
                String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
                this.lst_actor = drive.lista_SelectItem_simple(lista_actor_sql, this.ambiente);
                String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
                this.lst_gestor = drive.lista_SelectItem_simple(lista_gestor_sql, this.ambiente);
                String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE' order by g.nombre";
                this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
                String lista_codigo_resultado_sql = "select cc.codigo_contactabilidad, cc.nombre from codigo_contactabilidad cc where cc.estado='VIGENTE' order by cc.nombre";
                this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
                String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE' order by s.nombre";
                this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
                String lista_estatus_judicial_sql = "select e.estatus, e.nombre from estatus e where e.estado='VIGENTE' order by e.estatus";
                this.lst_estatus_judicial = drive.lista_SelectItem_simple(lista_estatus_judicial_sql, this.ambiente);
                String lista_estado_extrajudicial_sql = "select se.sestado_extra, se.nombre from sestado_extra se where se.estado='VIGENTE' order by se.nombre";
                this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
                String lista_estatus_extrajudicial_sql = "select ee.estatus_extra, ee.nombre from estatus_extra ee where ee.estado='VIGENTE' order by ee.nombre";
                this.lst_estatus_extrajudicial = drive.lista_SelectItem_simple(lista_estatus_extrajudicial_sql, this.ambiente);
                String lista_intension_pago_sql = "select ip.intencion_pago, ip.nombre from intencion_pago ip where ip.estado='VIGENTE' order by ip.nombre";
                this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intension_pago_sql, this.ambiente);
                String lista_razon_deuda_sql = "select rd.razon_deuda, rd.nombre from razon_deuda rd where rd.estado='VIGENTE' order by rd.nombre";
                this.lst_razon_deuda = drive.lista_SelectItem_simple(lista_razon_deuda_sql, this.ambiente);
                String lista_antiguedad_sql = "select a.nombre, a.nombre from antiguedad a where a.estado='VIGENTE' order by a.nombre";
                this.lst_antiguedad = drive.lista_SelectItem_simple(lista_antiguedad_sql, this.ambiente);

                this.lst_moneda = drive.lista_moneda();
                this.lst_nacionalidad = drive.lista_nacionalidad();
                this.lst_pais = drive.lista_paises();
                this.lst_departamento = drive.lista_departamentos();
                this.lst_sexo = drive.lista_sexo();
                this.lst_estado_civil = drive.lista_estado_civil();
                this.lst_cosecha = drive.lista_cosechas();
                this.lst_cargado = drive.lista_cargado();
                this.lst_anticipo = drive.lista_anticipo();

                String cadenasql = "select "
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
                        + "d.sestado_extra, "
                        + "d.estatus_extra, "
                        + "d.intencion_pago, "
                        + "d.razon_deuda, "
                        + "d.cuenta_principal_relacion, "
                        + "ifnull(d.deudor_cuenta_relacionada,0) deudor_cuenta_relacionada "
                        + "from "
                        + "deudor d "
                        + "where "
                        + "d.deudor=" + this.selectedDeudor.getDeudor();
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

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                for (Integer i = 1; i < filas; i++) {
                    this.actor_d = Integer.parseInt(vector_result[i][0]);
                    this.moneda_d = vector_result[i][1];
                    this.dpi_d = vector_result[i][2];
                    this.nit_d = vector_result[i][3];
                    this.fecha_nacimiento_d = formatter.parse(vector_result[i][4]);
                    this.nombre_d = vector_result[i][5];
                    this.nacionalidad_d = vector_result[i][6];
                    this.telefono_casa_d = vector_result[i][7];
                    this.telefono_celular_d = vector_result[i][8];
                    this.direccion_d = vector_result[i][9];
                    this.zona_d = Integer.parseInt(vector_result[i][10]);
                    this.pais_d = vector_result[i][11];
                    this.departamento_d = vector_result[i][12];
                    this.sexo_d = vector_result[i][13];
                    this.estado_civil_d = vector_result[i][14];
                    this.fecha_ingreso_d = formatter.parse(vector_result[i][15]);
                    this.profesion_d = vector_result[i][16];
                    this.correo_electronico_d = vector_result[i][17];
                    this.lugar_trabajo_d = vector_result[i][18];
                    this.direccion_trabajo_d = vector_result[i][19];
                    this.telefono_trabajo_d = vector_result[i][20];
                    this.monto_inicial_d = Double.parseDouble(vector_result[i][21]);
                    this.gestor_d = Integer.parseInt(vector_result[i][22]);
                    this.sestado_d = Integer.parseInt(vector_result[i][23]);
                    this.estatus_d = Integer.parseInt(vector_result[i][24]);
                    this.no_cuenta_d = vector_result[i][25];
                    this.garantia_d = Integer.parseInt(vector_result[i][26]);
                    this.cargado_d = vector_result[i][27];
                    this.estado_d = vector_result[i][28];
                    this.descripcion_d = vector_result[i][29];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][30]);
                    this.caso_d = Integer.parseInt(vector_result[i][31]);
                    if (vector_result[i][32].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][35].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][36];
                    this.no_cuenta_otro_d = vector_result[i][37];
                    this.descripcion_adicional_d = vector_result[i][38];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][39]);
                    this.anticipo_d = vector_result[i][40];
                    this.antiguedad_d = vector_result[i][41];
                    this.fecha_proximo_pago_d = formatter.parse(vector_result[i][42]);
                    this.monto_proximo_pago_d = Double.parseDouble(vector_result[i][43]);
                    this.saldo_d = Double.parseDouble(vector_result[i][44]);
                    this.convenio_pactado_d = vector_result[i][45];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][46]);
                    this.costas_pagadas_d = vector_result[i][47];
                    this.situacion_caso_d = vector_result[i][48];
                    if (vector_result[i][49].equals("SI")) {
                        this.opcion_proximo_pago_d = true;
                    } else {
                        this.opcion_proximo_pago_d = false;
                    }
                    this.sestado_extra_d = Integer.parseInt(vector_result[i][50]);
                    this.estatus_extra_d = Integer.parseInt(vector_result[i][51]);
                    this.intencion_pago_d = Integer.parseInt(vector_result[i][52]);
                    this.razon_deuda_d = Integer.parseInt(vector_result[i][53]);
                    if (vector_result[i][54].equals("1")) {
                        this.cuenta_principal_relacion_d = true;
                    } else {
                        this.cuenta_principal_relacion_d = false;
                    }
                    this.deudor_cuenta_relacionada_d = Integer.parseInt(vector_result[i][55]);
                }

                this.cbxActor = true;
                this.cbxMoneda = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.dateFechaNacimiento = true;
                this.txtNombre = true;
                this.cbxNacionalidad = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtDireccion = true;
                this.SpnZona = true;
                this.cbxPais = true;
                this.cbxDepartamento = true;
                this.cbxSexo = true;
                this.cbxEstadoCivil = true;
                this.dateFechaIngreso = true;
                this.txtProfesion = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtDireccionTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.spnMontoInicial = true;
                this.cbxGestor = true;
                this.cbxEstadoJudicial = true;
                this.cbxEstatusJudicial = true;
                this.txtNoCuenta = true;
                this.cbxGarantia = true;
                this.cbxCargado = true;
                this.cbxEstado = true;
                this.areDescripcion = true;
                this.cbxCodigoContactabilidad = true;
                this.spnCaso = true;
                this.chkPDF = true;
                this.chkINV = true;
                this.chkMAYCOM = true;
                this.chkNITS = true;
                this.cbxCosecha = true;
                this.txtNoCuentaOtro = true;
                this.areDescripcionAdicional = true;
                this.dateFechaRecepcion = true;
                this.cbxAnticipo = true;
                this.cbxAntiguedad = true;
                this.dateFechaProximoPago = true;
                this.spnMontoProximoPago = true;
                this.spnSaldo = true;
                this.areConvenioPactado = true;
                this.spnCuotaConvenio = true;
                this.areCostasPagadas = true;
                this.areSituacionCaso = true;
                this.chkOpcionProximoPago = true;
                this.cbxEstadoExtrajudicial = true;
                this.cbxEstatusExtrajudicial = true;
                this.cbxIntencionPago = true;
                this.cbxRazonDeuda = true;
                this.chkCuentaPrincipalRelacion = true;
                this.txtCuentaRelacionada = true;

                this.btnAceptar = true;
                this.btnCancelar = false;

                this.opcion = "VER";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un deudor del listado."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void aceptar() {
        if (this.opcion.equals("INSERTAR")) {
            this.insertar();
        }
        if (this.opcion.equals("MODIFICAR")) {
            this.modificar();
        }
        if (this.opcion.equals("ELIMINAR")) {
            this.eliminar();
        }
        if (this.opcion.equals("ACTIVAR")) {
            this.activar();
        }
    }

    public void activar_desactivar_relacionada() {
        try {
            if (this.cuenta_principal_relacion_d) {
                this.txtCuentaRelacionada = true;
            } else {
                this.deudor_cuenta_relacionada_d = 0;
                this.txtCuentaRelacionada = false;
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void insertar() {
        Driver driver = new Driver();
        String resultado;

        try {
            // Valida telefono_celular.
            Boolean telefono_celular_valido = false;
            Long numero_celular = new Long(0);
            try {
                numero_celular = Long.parseLong(this.telefono_celular_d);
                if (numero_celular == 0 || (numero_celular >= 10000000 && numero_celular <= 99999999)) {
                    telefono_celular_valido = true;
                }
            } catch (Exception ex) {
                telefono_celular_valido = false;
            }

            if (this.nacionalidad_d != null) {
                if (this.sexo_d != null) {
                    if (this.estado_civil_d != null) {
                        if (this.pais_d != null) {
                            if (this.departamento_d != null) {
                                if (this.actor_d != null) {
                                    if (this.cargado_d != null) {
                                        if (this.moneda_d != null) {
                                            if (this.gestor_d != null) {
                                                if (this.garantia_d != null) {
                                                    if (this.codigo_contactabilidad_d != null) {
                                                        if (this.cosecha_d != null) {
                                                            if (this.anticipo_d != null) {
                                                                if (this.antiguedad_d != null) {
                                                                    if (this.intencion_pago_d != null) {
                                                                        if (this.razon_deuda_d != null) {
                                                                            if (this.sestado_extra_d != null) {
                                                                                if (this.sestado_d != null) {
                                                                                    if (this.estatus_extra_d != null) {
                                                                                        if (this.estatus_d != null) {
                                                                                            if (!this.nombre_d.equals("")) {
                                                                                                if (this.validar_cuenta_relacionada()) {
                                                                                                    if (telefono_celular_valido == true) {
                                                                                                        if (this.correo_electronico_d.equals("-") || driver.getInt("select lower('" + correo_electronico_d + "') regexp '^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9._-]@[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]\\\\.[a-zA-Z]{2,63}$' EsCorreo", this.ambiente) == 1) {
                                                                                                            String PDF = "";
                                                                                                            if (this.PDF_d) {
                                                                                                                PDF = "SI";
                                                                                                            } else {
                                                                                                                PDF = "NO";
                                                                                                            }
                                                                                                            String INV = "";
                                                                                                            if (this.INV_d) {
                                                                                                                INV = "SI";
                                                                                                            } else {
                                                                                                                INV = "NO";
                                                                                                            }
                                                                                                            String MAYCOM = "";
                                                                                                            if (this.MAYCOM_d) {
                                                                                                                MAYCOM = "SI";
                                                                                                            } else {
                                                                                                                MAYCOM = "NO";
                                                                                                            }
                                                                                                            String NITS = "";
                                                                                                            if (this.NITS_d) {
                                                                                                                NITS = "SI";
                                                                                                            } else {
                                                                                                                NITS = "NO";
                                                                                                            }
                                                                                                            String opcion_proximo_pago = "";
                                                                                                            if (this.opcion_proximo_pago_d) {
                                                                                                                opcion_proximo_pago = "SI";
                                                                                                            } else {
                                                                                                                opcion_proximo_pago = "NO";
                                                                                                            }
                                                                                                            Integer cuenta_principal_relacion;
                                                                                                            if (this.cuenta_principal_relacion_d) {
                                                                                                                cuenta_principal_relacion = 1;
                                                                                                            } else {
                                                                                                                cuenta_principal_relacion = 0;
                                                                                                            }

                                                                                                            GregorianCalendar gregory1 = new GregorianCalendar();
                                                                                                            gregory1.set(this.fecha_nacimiento_d.getYear() + 1900, this.fecha_nacimiento_d.getMonth(), this.fecha_nacimiento_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_nacimiento_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

                                                                                                            GregorianCalendar gregory2 = new GregorianCalendar();
                                                                                                            gregory2.set(this.fecha_ingreso_d.getYear() + 1900, this.fecha_ingreso_d.getMonth(), this.fecha_ingreso_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_ingreso_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);

                                                                                                            GregorianCalendar gregory3 = new GregorianCalendar();
                                                                                                            gregory3.set(this.fecha_recepcion_d.getYear() + 1900, this.fecha_recepcion_d.getMonth(), this.fecha_recepcion_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_recepcion_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

                                                                                                            GregorianCalendar gregory4 = new GregorianCalendar();
                                                                                                            gregory4.set(this.fecha_proximo_pago_d.getYear() + 1900, this.fecha_proximo_pago_d.getMonth(), this.fecha_proximo_pago_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_proximo_pago_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

                                                                                                            Servicio servicio = new Servicio();
                                                                                                            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                                                                                                            resultado = servicio.deudorInsertar(
                                                                                                                    id_usuario,
                                                                                                                    this.actor_d,
                                                                                                                    this.moneda_d,
                                                                                                                    this.dpi_d,
                                                                                                                    this.nit_d,
                                                                                                                    gre_fecha_nacimiento_d,
                                                                                                                    this.nombre_d,
                                                                                                                    this.nacionalidad_d,
                                                                                                                    this.telefono_casa_d,
                                                                                                                    this.telefono_celular_d,
                                                                                                                    this.direccion_d,
                                                                                                                    this.zona_d,
                                                                                                                    this.pais_d,
                                                                                                                    this.departamento_d,
                                                                                                                    this.sexo_d,
                                                                                                                    this.estado_civil_d,
                                                                                                                    gre_fecha_ingreso_d,
                                                                                                                    this.profesion_d,
                                                                                                                    this.correo_electronico_d,
                                                                                                                    this.lugar_trabajo_d,
                                                                                                                    this.direccion_trabajo_d,
                                                                                                                    this.telefono_trabajo_d,
                                                                                                                    this.monto_inicial_d,
                                                                                                                    this.gestor_d,
                                                                                                                    this.sestado_d,
                                                                                                                    this.estatus_d,
                                                                                                                    this.no_cuenta_d,
                                                                                                                    this.garantia_d,
                                                                                                                    this.cargado_d,
                                                                                                                    this.estado_d,
                                                                                                                    this.descripcion_d,
                                                                                                                    this.codigo_contactabilidad_d,
                                                                                                                    this.caso_d,
                                                                                                                    PDF,
                                                                                                                    INV,
                                                                                                                    MAYCOM,
                                                                                                                    NITS,
                                                                                                                    this.cosecha_d,
                                                                                                                    this.no_cuenta_otro_d,
                                                                                                                    this.descripcion_adicional_d,
                                                                                                                    gre_fecha_recepcion_d,
                                                                                                                    this.anticipo_d,
                                                                                                                    this.antiguedad_d,
                                                                                                                    gre_fecha_proximo_pago_d,
                                                                                                                    this.monto_proximo_pago_d,
                                                                                                                    this.saldo_d,
                                                                                                                    this.convenio_pactado_d,
                                                                                                                    this.cuota_convenio_d,
                                                                                                                    this.costas_pagadas_d,
                                                                                                                    this.situacion_caso_d,
                                                                                                                    opcion_proximo_pago,
                                                                                                                    this.sestado_extra_d,
                                                                                                                    this.estatus_extra_d,
                                                                                                                    this.intencion_pago_d,
                                                                                                                    this.razon_deuda_d,
                                                                                                                    cuenta_principal_relacion,
                                                                                                                    this.deudor_cuenta_relacionada_d,
                                                                                                                    this.ambiente);
                                                                                                            this.constructor();
                                                                                                            RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

                                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                                                                                                        } else {
                                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El correo electrónico no es valido."));
                                                                                                        }
                                                                                                    } else {
                                                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El teléfono celular en el campo contacto principal no es valido."));
                                                                                                    }
                                                                                                } else {
                                                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El número de cuenta relacionada no existe, la cuenta no es principal o la corporación de la cuenta es diferente."));
                                                                                                }
                                                                                            } else {
                                                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del deudor."));
                                                                                            }
                                                                                        } else {
                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTATUS JUDICIAL del deudor."));
                                                                                        }
                                                                                    } else {
                                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTATUS EXTRAJUDICIAL del deudor."));
                                                                                    }
                                                                                } else {
                                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO JUDICIAL del deudor."));
                                                                                }
                                                                            } else {
                                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO EXTRAJUDICIAL del deudor."));
                                                                            }
                                                                        } else {
                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la RAZÓN DE DEUDA del deudor."));
                                                                        }
                                                                    } else {
                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la INTENSIÓN DE PAGO del deudor."));
                                                                    }
                                                                } else {
                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la ANTIGÜEDAD del deudor."));
                                                                }
                                                            } else {
                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ANTICIPO del deudor."));
                                                            }
                                                        } else {
                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la COSECHA del deudor."));
                                                        }
                                                    } else {
                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el CÓDIGO DE RESULTADO del deudor."));
                                                    }
                                                } else {
                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la GARANTÍA del deudor."));
                                                }
                                            } else {
                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el GESTOR del deudor."));
                                            }
                                        } else {
                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la MONEDA del deudor."));
                                        }
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar CARGADO/DESCARGADO del deudor."));
                                    }
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ACTOR del deudor."));
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el DEPARTAMENTO del deudor."));
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el PAÍS del deudor."));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO CIVIL del deudor."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el GENERO del deudor."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la NACIONALIDAD del deudor."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void modificar() {
        Driver driver = new Driver();
        String resultado;

        try {
            // Valida telefono_celular.
            Boolean telefono_celular_valido = false;
            Long numero_celular = new Long(0);
            try {
                numero_celular = Long.parseLong(this.telefono_celular_d);
                if (numero_celular == 0 || (numero_celular >= 10000000 && numero_celular <= 99999999)) {
                    telefono_celular_valido = true;
                }
            } catch (Exception ex) {
                telefono_celular_valido = false;
            }

            if (this.nacionalidad_d != null) {
                if (this.sexo_d != null) {
                    if (this.estado_civil_d != null) {
                        if (this.pais_d != null) {
                            if (this.departamento_d != null) {
                                if (this.actor_d != null) {
                                    if (this.cargado_d != null) {
                                        if (this.moneda_d != null) {
                                            if (this.gestor_d != null) {
                                                if (this.garantia_d != null) {
                                                    if (this.codigo_contactabilidad_d != null) {
                                                        if (this.cosecha_d != null) {
                                                            if (this.anticipo_d != null) {
                                                                if (this.antiguedad_d != null) {
                                                                    if (this.intencion_pago_d != null) {
                                                                        if (this.razon_deuda_d != null) {
                                                                            if (this.sestado_extra_d != null) {
                                                                                if (this.sestado_d != null) {
                                                                                    if (this.estatus_extra_d != null) {
                                                                                        if (this.estatus_d != null) {
                                                                                            if (!this.nombre_d.equals("")) {
                                                                                                if (this.validar_cuenta_relacionada()) {
                                                                                                    if (telefono_celular_valido == true) {
                                                                                                        if (this.correo_electronico_d.equals("-") || driver.getInt("select lower('" + correo_electronico_d + "') regexp '^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9._-]@[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]\\\\.[a-zA-Z]{2,63}$' EsCorreo", this.ambiente) == 1) {
                                                                                                            String PDF = "";
                                                                                                            if (this.PDF_d) {
                                                                                                                PDF = "SI";
                                                                                                            } else {
                                                                                                                PDF = "NO";
                                                                                                            }
                                                                                                            String INV = "";
                                                                                                            if (this.INV_d) {
                                                                                                                INV = "SI";
                                                                                                            } else {
                                                                                                                INV = "NO";
                                                                                                            }
                                                                                                            String MAYCOM = "";
                                                                                                            if (this.MAYCOM_d) {
                                                                                                                MAYCOM = "SI";
                                                                                                            } else {
                                                                                                                MAYCOM = "NO";
                                                                                                            }
                                                                                                            String NITS = "";
                                                                                                            if (this.NITS_d) {
                                                                                                                NITS = "SI";
                                                                                                            } else {
                                                                                                                NITS = "NO";
                                                                                                            }
                                                                                                            String opcion_proximo_pago = "";
                                                                                                            if (this.opcion_proximo_pago_d) {
                                                                                                                opcion_proximo_pago = "SI";
                                                                                                            } else {
                                                                                                                opcion_proximo_pago = "NO";
                                                                                                            }
                                                                                                            Integer cuenta_principal_relacion;
                                                                                                            if (this.cuenta_principal_relacion_d) {
                                                                                                                cuenta_principal_relacion = 1;
                                                                                                            } else {
                                                                                                                cuenta_principal_relacion = 0;
                                                                                                            }

                                                                                                            GregorianCalendar gregory1 = new GregorianCalendar();
                                                                                                            gregory1.set(this.fecha_nacimiento_d.getYear() + 1900, this.fecha_nacimiento_d.getMonth(), this.fecha_nacimiento_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_nacimiento_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

                                                                                                            GregorianCalendar gregory2 = new GregorianCalendar();
                                                                                                            gregory2.set(this.fecha_ingreso_d.getYear() + 1900, this.fecha_ingreso_d.getMonth(), this.fecha_ingreso_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_ingreso_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);

                                                                                                            GregorianCalendar gregory3 = new GregorianCalendar();
                                                                                                            gregory3.set(this.fecha_recepcion_d.getYear() + 1900, this.fecha_recepcion_d.getMonth(), this.fecha_recepcion_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_recepcion_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

                                                                                                            GregorianCalendar gregory4 = new GregorianCalendar();
                                                                                                            gregory4.set(this.fecha_proximo_pago_d.getYear() + 1900, this.fecha_proximo_pago_d.getMonth(), this.fecha_proximo_pago_d.getDate());
                                                                                                            XMLGregorianCalendar gre_fecha_proximo_pago_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

                                                                                                            Servicio servicio = new Servicio();
                                                                                                            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                                                                                                            resultado = servicio.deudorModificar(
                                                                                                                    id_usuario,
                                                                                                                    this.selectedDeudor.getDeudor(),
                                                                                                                    this.actor_d,
                                                                                                                    this.moneda_d,
                                                                                                                    this.dpi_d,
                                                                                                                    this.nit_d,
                                                                                                                    gre_fecha_nacimiento_d,
                                                                                                                    this.nombre_d,
                                                                                                                    this.nacionalidad_d,
                                                                                                                    this.telefono_casa_d,
                                                                                                                    this.telefono_celular_d,
                                                                                                                    this.direccion_d,
                                                                                                                    this.zona_d,
                                                                                                                    this.pais_d,
                                                                                                                    this.departamento_d,
                                                                                                                    this.sexo_d,
                                                                                                                    this.estado_civil_d,
                                                                                                                    gre_fecha_ingreso_d,
                                                                                                                    this.profesion_d,
                                                                                                                    this.correo_electronico_d,
                                                                                                                    this.lugar_trabajo_d,
                                                                                                                    this.direccion_trabajo_d,
                                                                                                                    this.telefono_trabajo_d,
                                                                                                                    this.monto_inicial_d,
                                                                                                                    this.gestor_d,
                                                                                                                    this.sestado_d,
                                                                                                                    this.estatus_d,
                                                                                                                    this.no_cuenta_d,
                                                                                                                    this.garantia_d,
                                                                                                                    this.cargado_d,
                                                                                                                    this.estado_d,
                                                                                                                    this.descripcion_d,
                                                                                                                    this.codigo_contactabilidad_d,
                                                                                                                    this.caso_d,
                                                                                                                    PDF,
                                                                                                                    INV,
                                                                                                                    MAYCOM,
                                                                                                                    NITS,
                                                                                                                    this.cosecha_d,
                                                                                                                    this.no_cuenta_otro_d,
                                                                                                                    this.descripcion_adicional_d,
                                                                                                                    gre_fecha_recepcion_d,
                                                                                                                    this.anticipo_d,
                                                                                                                    this.antiguedad_d,
                                                                                                                    gre_fecha_proximo_pago_d,
                                                                                                                    this.monto_proximo_pago_d,
                                                                                                                    this.saldo_d,
                                                                                                                    this.convenio_pactado_d,
                                                                                                                    this.cuota_convenio_d,
                                                                                                                    this.costas_pagadas_d,
                                                                                                                    this.situacion_caso_d,
                                                                                                                    opcion_proximo_pago,
                                                                                                                    this.sestado_extra_d,
                                                                                                                    this.estatus_extra_d,
                                                                                                                    this.intencion_pago_d,
                                                                                                                    this.razon_deuda_d,
                                                                                                                    cuenta_principal_relacion,
                                                                                                                    this.deudor_cuenta_relacionada_d,
                                                                                                                    this.ambiente);
                                                                                                            this.constructor();
                                                                                                            RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

                                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                                                                                                        } else {
                                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El correo electrónico no es valido."));
                                                                                                        }
                                                                                                    } else {
                                                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El teléfono celular en el campo contacto principal no es valido."));
                                                                                                    }
                                                                                                } else {
                                                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "El número de cuenta relacionada no existe, la cuenta no es principal o la corporación de la cuenta es diferente."));
                                                                                                }
                                                                                            } else {
                                                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del deudor."));
                                                                                            }
                                                                                        } else {
                                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTATUS JUDICIAL del deudor."));
                                                                                        }
                                                                                    } else {
                                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTATUS EXTRAJUDICIAL del deudor."));
                                                                                    }
                                                                                } else {
                                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO JUDICIAL del deudor."));
                                                                                }
                                                                            } else {
                                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO EXTRAJUDICIAL del deudor."));
                                                                            }
                                                                        } else {
                                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la RAZÓN DE DEUDA del deudor."));
                                                                        }
                                                                    } else {
                                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la INTENSIÓN DE PAGO del deudor."));
                                                                    }
                                                                } else {
                                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la ANTIGÜEDAD del deudor."));
                                                                }
                                                            } else {
                                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ANTICIPO del deudor."));
                                                            }
                                                        } else {
                                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la COSECHA del deudor."));
                                                        }
                                                    } else {
                                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el CÓDIGO DE RESULTADO del deudor."));
                                                    }
                                                } else {
                                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la GARANTÍA del deudor."));
                                                }
                                            } else {
                                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el GESTOR del deudor."));
                                            }
                                        } else {
                                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la MONEDA del deudor."));
                                        }
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar CARGADO/DESCARGADO del deudor."));
                                    }
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ACTOR del deudor."));
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el DEPARTAMENTO del deudor."));
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el PAÍS del deudor."));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el ESTADO CIVIL del deudor."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar el GENERO del deudor."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe seleccionar la NACIONALIDAD del deudor."));
            }

            if (!this.nombre_d.equals("")) {

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del deudor."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void eliminar() {
        String resultado;

        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.deudorEliminar(
                    id_usuario,
                    this.selectedDeudor.getDeudor(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void activar() {
        String resultado;

        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.deudorActivar(
                    id_usuario,
                    this.selectedDeudor.getDeudor(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }

    private Boolean validar_cuenta_relacionada() {
        Driver driver = new Driver();
        Boolean resultado = false;

        try {
            if(!this.cuenta_principal_relacion_d && this.deudor_cuenta_relacionada_d != 0) {
                String corporacion_deudor = driver.getString("select a.cooperacion from actor a where a.actor=" + this.actor_d, ambiente);
                String deudor_relacionado = driver.getString("select d.deudor from deudor d where d.deudor=" + this.deudor_cuenta_relacionada_d, ambiente);
                String corporacion_relacionado = driver.getString("select a.cooperacion from deudor d left join actor a on (d.actor=a.actor) where d.deudor=" + this.deudor_cuenta_relacionada_d, ambiente);
                String deudor_relacionado_principal = driver.getString("select d.cuenta_principal_relacion from deudor d where d.deudor=" + this.deudor_cuenta_relacionada_d, ambiente);
                if (!deudor_relacionado.equals("")) {
                    if(corporacion_deudor.trim().equals(corporacion_relacionado.trim())) {
                        if(deudor_relacionado_principal.trim().equals("1")) {
                            resultado = true;
                        } else {
                            resultado = false;
                        }
                    } else {
                        resultado = false;
                    }
                } else {
                    resultado = false;
                }
            } else {
                resultado = true;
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }

        return resultado;
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

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public List<Deudor_List> getLst_deudor() {
        return lst_deudor;
    }

    public void setLst_deudor(List<Deudor_List> lst_deudor) {
        this.lst_deudor = lst_deudor;
    }

    public Deudor_List getSelectedDeudor() {
        return selectedDeudor;
    }

    public void setSelectedDeudor(Deudor_List selectedDeudor) {
        this.selectedDeudor = selectedDeudor;
    }

    public Integer getActor_d() {
        return actor_d;
    }

    public void setActor_d(Integer actor_d) {
        this.actor_d = actor_d;
    }

    public String getMoneda_d() {
        return moneda_d;
    }

    public void setMoneda_d(String moneda_d) {
        this.moneda_d = moneda_d;
    }

    public String getDpi_d() {
        return dpi_d;
    }

    public void setDpi_d(String dpi_d) {
        this.dpi_d = dpi_d;
    }

    public String getNit_d() {
        return nit_d;
    }

    public void setNit_d(String nit_d) {
        this.nit_d = nit_d;
    }

    public Date getFecha_nacimiento_d() {
        return fecha_nacimiento_d;
    }

    public void setFecha_nacimiento_d(Date fecha_nacimiento_d) {
        this.fecha_nacimiento_d = fecha_nacimiento_d;
    }

    public String getNombre_d() {
        return nombre_d;
    }

    public void setNombre_d(String nombre_d) {
        this.nombre_d = nombre_d;
    }

    public String getNacionalidad_d() {
        return nacionalidad_d;
    }

    public void setNacionalidad_d(String nacionalidad_d) {
        this.nacionalidad_d = nacionalidad_d;
    }

    public String getTelefono_casa_d() {
        return telefono_casa_d;
    }

    public void setTelefono_casa_d(String telefono_casa_d) {
        this.telefono_casa_d = telefono_casa_d;
    }

    public String getTelefono_celular_d() {
        return telefono_celular_d;
    }

    public void setTelefono_celular_d(String telefono_celular_d) {
        this.telefono_celular_d = telefono_celular_d;
    }

    public String getDireccion_d() {
        return direccion_d;
    }

    public void setDireccion_d(String direccion_d) {
        this.direccion_d = direccion_d;
    }

    public Integer getZona_d() {
        return zona_d;
    }

    public void setZona_d(Integer zona_d) {
        this.zona_d = zona_d;
    }

    public String getPais_d() {
        return pais_d;
    }

    public void setPais_d(String pais_d) {
        this.pais_d = pais_d;
    }

    public String getDepartamento_d() {
        return departamento_d;
    }

    public void setDepartamento_d(String departamento_d) {
        this.departamento_d = departamento_d;
    }

    public String getSexo_d() {
        return sexo_d;
    }

    public void setSexo_d(String sexo_d) {
        this.sexo_d = sexo_d;
    }

    public String getEstado_civil_d() {
        return estado_civil_d;
    }

    public void setEstado_civil_d(String estado_civil_d) {
        this.estado_civil_d = estado_civil_d;
    }

    public Date getFecha_ingreso_d() {
        return fecha_ingreso_d;
    }

    public void setFecha_ingreso_d(Date fecha_ingreso_d) {
        this.fecha_ingreso_d = fecha_ingreso_d;
    }

    public String getProfesion_d() {
        return profesion_d;
    }

    public void setProfesion_d(String profesion_d) {
        this.profesion_d = profesion_d;
    }

    public String getCorreo_electronico_d() {
        return correo_electronico_d;
    }

    public void setCorreo_electronico_d(String correo_electronico_d) {
        this.correo_electronico_d = correo_electronico_d;
    }

    public String getLugar_trabajo_d() {
        return lugar_trabajo_d;
    }

    public void setLugar_trabajo_d(String lugar_trabajo_d) {
        this.lugar_trabajo_d = lugar_trabajo_d;
    }

    public String getDireccion_trabajo_d() {
        return direccion_trabajo_d;
    }

    public void setDireccion_trabajo_d(String direccion_trabajo_d) {
        this.direccion_trabajo_d = direccion_trabajo_d;
    }

    public String getTelefono_trabajo_d() {
        return telefono_trabajo_d;
    }

    public void setTelefono_trabajo_d(String telefono_trabajo_d) {
        this.telefono_trabajo_d = telefono_trabajo_d;
    }

    public Double getMonto_inicial_d() {
        return monto_inicial_d;
    }

    public void setMonto_inicial_d(Double monto_inicial_d) {
        this.monto_inicial_d = monto_inicial_d;
    }

    public Integer getGestor_d() {
        return gestor_d;
    }

    public void setGestor_d(Integer gestor_d) {
        this.gestor_d = gestor_d;
    }

    public Integer getSestado_d() {
        return sestado_d;
    }

    public void setSestado_d(Integer sestado_d) {
        this.sestado_d = sestado_d;
    }

    public Integer getEstatus_d() {
        return estatus_d;
    }

    public void setEstatus_d(Integer estatus_d) {
        this.estatus_d = estatus_d;
    }

    public String getNo_cuenta_d() {
        return no_cuenta_d;
    }

    public void setNo_cuenta_d(String no_cuenta_d) {
        this.no_cuenta_d = no_cuenta_d;
    }

    public Integer getGarantia_d() {
        return garantia_d;
    }

    public void setGarantia_d(Integer garantia_d) {
        this.garantia_d = garantia_d;
    }

    public String getCargado_d() {
        return cargado_d;
    }

    public void setCargado_d(String cargado_d) {
        this.cargado_d = cargado_d;
    }

    public String getEstado_d() {
        return estado_d;
    }

    public void setEstado_d(String estado_d) {
        this.estado_d = estado_d;
    }

    public String getDescripcion_d() {
        return descripcion_d;
    }

    public void setDescripcion_d(String descripcion_d) {
        this.descripcion_d = descripcion_d;
    }

    public Integer getCodigo_contactabilidad_d() {
        return codigo_contactabilidad_d;
    }

    public void setCodigo_contactabilidad_d(Integer codigo_contactabilidad_d) {
        this.codigo_contactabilidad_d = codigo_contactabilidad_d;
    }

    public Integer getCaso_d() {
        return caso_d;
    }

    public void setCaso_d(Integer caso_d) {
        this.caso_d = caso_d;
    }

    public Boolean getPDF_d() {
        return PDF_d;
    }

    public void setPDF_d(Boolean PDF_d) {
        this.PDF_d = PDF_d;
    }

    public Boolean getINV_d() {
        return INV_d;
    }

    public void setINV_d(Boolean INV_d) {
        this.INV_d = INV_d;
    }

    public Boolean getMAYCOM_d() {
        return MAYCOM_d;
    }

    public void setMAYCOM_d(Boolean MAYCOM_d) {
        this.MAYCOM_d = MAYCOM_d;
    }

    public Boolean getNITS_d() {
        return NITS_d;
    }

    public void setNITS_d(Boolean NITS_d) {
        this.NITS_d = NITS_d;
    }

    public String getCosecha_d() {
        return cosecha_d;
    }

    public void setCosecha_d(String cosecha_d) {
        this.cosecha_d = cosecha_d;
    }

    public String getNo_cuenta_otro_d() {
        return no_cuenta_otro_d;
    }

    public void setNo_cuenta_otro_d(String no_cuenta_otro_d) {
        this.no_cuenta_otro_d = no_cuenta_otro_d;
    }

    public String getDescripcion_adicional_d() {
        return descripcion_adicional_d;
    }

    public void setDescripcion_adicional_d(String descripcion_adicional_d) {
        this.descripcion_adicional_d = descripcion_adicional_d;
    }

    public Date getFecha_recepcion_d() {
        return fecha_recepcion_d;
    }

    public void setFecha_recepcion_d(Date fecha_recepcion_d) {
        this.fecha_recepcion_d = fecha_recepcion_d;
    }

    public String getAnticipo_d() {
        return anticipo_d;
    }

    public void setAnticipo_d(String anticipo_d) {
        this.anticipo_d = anticipo_d;
    }

    public String getAntiguedad_d() {
        return antiguedad_d;
    }

    public void setAntiguedad_d(String antiguedad_d) {
        this.antiguedad_d = antiguedad_d;
    }

    public Date getFecha_proximo_pago_d() {
        return fecha_proximo_pago_d;
    }

    public void setFecha_proximo_pago_d(Date fecha_proximo_pago_d) {
        this.fecha_proximo_pago_d = fecha_proximo_pago_d;
    }

    public Double getMonto_proximo_pago_d() {
        return monto_proximo_pago_d;
    }

    public void setMonto_proximo_pago_d(Double monto_proximo_pago_d) {
        this.monto_proximo_pago_d = monto_proximo_pago_d;
    }

    public Double getSaldo_d() {
        return saldo_d;
    }

    public void setSaldo_d(Double saldo_d) {
        this.saldo_d = saldo_d;
    }

    public String getConvenio_pactado_d() {
        return convenio_pactado_d;
    }

    public void setConvenio_pactado_d(String convenio_pactado_d) {
        this.convenio_pactado_d = convenio_pactado_d;
    }

    public Double getCuota_convenio_d() {
        return cuota_convenio_d;
    }

    public void setCuota_convenio_d(Double cuota_convenio_d) {
        this.cuota_convenio_d = cuota_convenio_d;
    }

    public String getCostas_pagadas_d() {
        return costas_pagadas_d;
    }

    public void setCostas_pagadas_d(String costas_pagadas_d) {
        this.costas_pagadas_d = costas_pagadas_d;
    }

    public String getSituacion_caso_d() {
        return situacion_caso_d;
    }

    public void setSituacion_caso_d(String situacion_caso_d) {
        this.situacion_caso_d = situacion_caso_d;
    }

    public Boolean getOpcion_proximo_pago_d() {
        return opcion_proximo_pago_d;
    }

    public void setOpcion_proximo_pago_d(Boolean opcion_proximo_pago_d) {
        this.opcion_proximo_pago_d = opcion_proximo_pago_d;
    }

    public Integer getSestado_extra_d() {
        return sestado_extra_d;
    }

    public void setSestado_extra_d(Integer sestado_extra_d) {
        this.sestado_extra_d = sestado_extra_d;
    }

    public Integer getEstatus_extra_d() {
        return estatus_extra_d;
    }

    public void setEstatus_extra_d(Integer estatus_extra_d) {
        this.estatus_extra_d = estatus_extra_d;
    }

    public Integer getIntencion_pago_d() {
        return intencion_pago_d;
    }

    public void setIntencion_pago_d(Integer intencion_pago_d) {
        this.intencion_pago_d = intencion_pago_d;
    }

    public Integer getRazon_deuda_d() {
        return razon_deuda_d;
    }

    public void setRazon_deuda_d(Integer razon_deuda_d) {
        this.razon_deuda_d = razon_deuda_d;
    }

    public Boolean getCuenta_principal_relacion_d() {
        return cuenta_principal_relacion_d;
    }

    public void setCuenta_principal_relacion_d(Boolean cuenta_principal_relacion_d) {
        this.cuenta_principal_relacion_d = cuenta_principal_relacion_d;
    }

    public Integer getDeudor_cuenta_relacionada_d() {
        return deudor_cuenta_relacionada_d;
    }

    public void setDeudor_cuenta_relacionada_d(Integer deudor_cuenta_relacionada_d) {
        this.deudor_cuenta_relacionada_d = deudor_cuenta_relacionada_d;
    }

    public Boolean getCbxActor() {
        return cbxActor;
    }

    public void setCbxActor(Boolean cbxActor) {
        this.cbxActor = cbxActor;
    }

    public Boolean getCbxMoneda() {
        return cbxMoneda;
    }

    public void setCbxMoneda(Boolean cbxMoneda) {
        this.cbxMoneda = cbxMoneda;
    }

    public Boolean getTxtDpi() {
        return txtDpi;
    }

    public void setTxtDpi(Boolean txtDpi) {
        this.txtDpi = txtDpi;
    }

    public Boolean getTxtNit() {
        return txtNit;
    }

    public void setTxtNit(Boolean txtNit) {
        this.txtNit = txtNit;
    }

    public Boolean getDateFechaNacimiento() {
        return dateFechaNacimiento;
    }

    public void setDateFechaNacimiento(Boolean dateFechaNacimiento) {
        this.dateFechaNacimiento = dateFechaNacimiento;
    }

    public Boolean getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(Boolean txtNombre) {
        this.txtNombre = txtNombre;
    }

    public Boolean getCbxNacionalidad() {
        return cbxNacionalidad;
    }

    public void setCbxNacionalidad(Boolean cbxNacionalidad) {
        this.cbxNacionalidad = cbxNacionalidad;
    }

    public Boolean getTxtTelefonoCasa() {
        return txtTelefonoCasa;
    }

    public void setTxtTelefonoCasa(Boolean txtTelefonoCasa) {
        this.txtTelefonoCasa = txtTelefonoCasa;
    }

    public Boolean getTxtTelefonoCelular() {
        return txtTelefonoCelular;
    }

    public void setTxtTelefonoCelular(Boolean txtTelefonoCelular) {
        this.txtTelefonoCelular = txtTelefonoCelular;
    }

    public Boolean getTxtDireccion() {
        return txtDireccion;
    }

    public void setTxtDireccion(Boolean txtDireccion) {
        this.txtDireccion = txtDireccion;
    }

    public Boolean getSpnZona() {
        return SpnZona;
    }

    public void setSpnZona(Boolean SpnZona) {
        this.SpnZona = SpnZona;
    }

    public Boolean getCbxPais() {
        return cbxPais;
    }

    public void setCbxPais(Boolean cbxPais) {
        this.cbxPais = cbxPais;
    }

    public Boolean getCbxDepartamento() {
        return cbxDepartamento;
    }

    public void setCbxDepartamento(Boolean cbxDepartamento) {
        this.cbxDepartamento = cbxDepartamento;
    }

    public Boolean getCbxSexo() {
        return cbxSexo;
    }

    public void setCbxSexo(Boolean cbxSexo) {
        this.cbxSexo = cbxSexo;
    }

    public Boolean getCbxEstadoCivil() {
        return cbxEstadoCivil;
    }

    public void setCbxEstadoCivil(Boolean cbxEstadoCivil) {
        this.cbxEstadoCivil = cbxEstadoCivil;
    }

    public Boolean getDateFechaIngreso() {
        return dateFechaIngreso;
    }

    public void setDateFechaIngreso(Boolean dateFechaIngreso) {
        this.dateFechaIngreso = dateFechaIngreso;
    }

    public Boolean getTxtProfesion() {
        return txtProfesion;
    }

    public void setTxtProfesion(Boolean txtProfesion) {
        this.txtProfesion = txtProfesion;
    }

    public Boolean getTxtCorreoElectronico() {
        return txtCorreoElectronico;
    }

    public void setTxtCorreoElectronico(Boolean txtCorreoElectronico) {
        this.txtCorreoElectronico = txtCorreoElectronico;
    }

    public Boolean getTxtLugarTrabajo() {
        return txtLugarTrabajo;
    }

    public void setTxtLugarTrabajo(Boolean txtLugarTrabajo) {
        this.txtLugarTrabajo = txtLugarTrabajo;
    }

    public Boolean getTxtDireccionTrabajo() {
        return txtDireccionTrabajo;
    }

    public void setTxtDireccionTrabajo(Boolean txtDireccionTrabajo) {
        this.txtDireccionTrabajo = txtDireccionTrabajo;
    }

    public Boolean getTxtTelefonoTrabajo() {
        return txtTelefonoTrabajo;
    }

    public void setTxtTelefonoTrabajo(Boolean txtTelefonoTrabajo) {
        this.txtTelefonoTrabajo = txtTelefonoTrabajo;
    }

    public Boolean getSpnMontoInicial() {
        return spnMontoInicial;
    }

    public void setSpnMontoInicial(Boolean spnMontoInicial) {
        this.spnMontoInicial = spnMontoInicial;
    }

    public Boolean getCbxGestor() {
        return cbxGestor;
    }

    public void setCbxGestor(Boolean cbxGestor) {
        this.cbxGestor = cbxGestor;
    }

    public Boolean getCbxEstadoJudicial() {
        return cbxEstadoJudicial;
    }

    public void setCbxEstadoJudicial(Boolean cbxEstadoJudicial) {
        this.cbxEstadoJudicial = cbxEstadoJudicial;
    }

    public Boolean getCbxEstatusJudicial() {
        return cbxEstatusJudicial;
    }

    public void setCbxEstatusJudicial(Boolean cbxEstatusJudicial) {
        this.cbxEstatusJudicial = cbxEstatusJudicial;
    }

    public Boolean getTxtNoCuenta() {
        return txtNoCuenta;
    }

    public void setTxtNoCuenta(Boolean txtNoCuenta) {
        this.txtNoCuenta = txtNoCuenta;
    }

    public Boolean getCbxGarantia() {
        return cbxGarantia;
    }

    public void setCbxGarantia(Boolean cbxGarantia) {
        this.cbxGarantia = cbxGarantia;
    }

    public Boolean getCbxCargado() {
        return cbxCargado;
    }

    public void setCbxCargado(Boolean cbxCargado) {
        this.cbxCargado = cbxCargado;
    }

    public Boolean getCbxEstado() {
        return cbxEstado;
    }

    public void setCbxEstado(Boolean cbxEstado) {
        this.cbxEstado = cbxEstado;
    }

    public Boolean getAreDescripcion() {
        return areDescripcion;
    }

    public void setAreDescripcion(Boolean areDescripcion) {
        this.areDescripcion = areDescripcion;
    }

    public Boolean getCbxCodigoContactabilidad() {
        return cbxCodigoContactabilidad;
    }

    public void setCbxCodigoContactabilidad(Boolean cbxCodigoContactabilidad) {
        this.cbxCodigoContactabilidad = cbxCodigoContactabilidad;
    }

    public Boolean getSpnCaso() {
        return spnCaso;
    }

    public void setSpnCaso(Boolean spnCaso) {
        this.spnCaso = spnCaso;
    }

    public Boolean getChkPDF() {
        return chkPDF;
    }

    public void setChkPDF(Boolean chkPDF) {
        this.chkPDF = chkPDF;
    }

    public Boolean getChkINV() {
        return chkINV;
    }

    public void setChkINV(Boolean chkINV) {
        this.chkINV = chkINV;
    }

    public Boolean getChkMAYCOM() {
        return chkMAYCOM;
    }

    public void setChkMAYCOM(Boolean chkMAYCOM) {
        this.chkMAYCOM = chkMAYCOM;
    }

    public Boolean getChkNITS() {
        return chkNITS;
    }

    public void setChkNITS(Boolean chkNITS) {
        this.chkNITS = chkNITS;
    }

    public Boolean getCbxCosecha() {
        return cbxCosecha;
    }

    public void setCbxCosecha(Boolean cbxCosecha) {
        this.cbxCosecha = cbxCosecha;
    }

    public Boolean getTxtNoCuentaOtro() {
        return txtNoCuentaOtro;
    }

    public void setTxtNoCuentaOtro(Boolean txtNoCuentaOtro) {
        this.txtNoCuentaOtro = txtNoCuentaOtro;
    }

    public Boolean getAreDescripcionAdicional() {
        return areDescripcionAdicional;
    }

    public void setAreDescripcionAdicional(Boolean areDescripcionAdicional) {
        this.areDescripcionAdicional = areDescripcionAdicional;
    }

    public Boolean getDateFechaRecepcion() {
        return dateFechaRecepcion;
    }

    public void setDateFechaRecepcion(Boolean dateFechaRecepcion) {
        this.dateFechaRecepcion = dateFechaRecepcion;
    }

    public Boolean getCbxAnticipo() {
        return cbxAnticipo;
    }

    public void setCbxAnticipo(Boolean cbxAnticipo) {
        this.cbxAnticipo = cbxAnticipo;
    }

    public Boolean getCbxAntiguedad() {
        return cbxAntiguedad;
    }

    public void setCbxAntiguedad(Boolean cbxAntiguedad) {
        this.cbxAntiguedad = cbxAntiguedad;
    }

    public Boolean getDateFechaProximoPago() {
        return dateFechaProximoPago;
    }

    public void setDateFechaProximoPago(Boolean dateFechaProximoPago) {
        this.dateFechaProximoPago = dateFechaProximoPago;
    }

    public Boolean getSpnMontoProximoPago() {
        return spnMontoProximoPago;
    }

    public void setSpnMontoProximoPago(Boolean spnMontoProximoPago) {
        this.spnMontoProximoPago = spnMontoProximoPago;
    }

    public Boolean getSpnSaldo() {
        return spnSaldo;
    }

    public void setSpnSaldo(Boolean spnSaldo) {
        this.spnSaldo = spnSaldo;
    }

    public Boolean getAreConvenioPactado() {
        return areConvenioPactado;
    }

    public void setAreConvenioPactado(Boolean areConvenioPactado) {
        this.areConvenioPactado = areConvenioPactado;
    }

    public Boolean getSpnCuotaConvenio() {
        return spnCuotaConvenio;
    }

    public void setSpnCuotaConvenio(Boolean spnCuotaConvenio) {
        this.spnCuotaConvenio = spnCuotaConvenio;
    }

    public Boolean getAreCostasPagadas() {
        return areCostasPagadas;
    }

    public void setAreCostasPagadas(Boolean areCostasPagadas) {
        this.areCostasPagadas = areCostasPagadas;
    }

    public Boolean getAreSituacionCaso() {
        return areSituacionCaso;
    }

    public void setAreSituacionCaso(Boolean areSituacionCaso) {
        this.areSituacionCaso = areSituacionCaso;
    }

    public Boolean getChkOpcionProximoPago() {
        return chkOpcionProximoPago;
    }

    public void setChkOpcionProximoPago(Boolean chkOpcionProximoPago) {
        this.chkOpcionProximoPago = chkOpcionProximoPago;
    }

    public Boolean getCbxEstadoExtrajudicial() {
        return cbxEstadoExtrajudicial;
    }

    public void setCbxEstadoExtrajudicial(Boolean cbxEstadoExtrajudicial) {
        this.cbxEstadoExtrajudicial = cbxEstadoExtrajudicial;
    }

    public Boolean getCbxEstatusExtrajudicial() {
        return cbxEstatusExtrajudicial;
    }

    public void setCbxEstatusExtrajudicial(Boolean cbxEstatusExtrajudicial) {
        this.cbxEstatusExtrajudicial = cbxEstatusExtrajudicial;
    }

    public Boolean getCbxIntencionPago() {
        return cbxIntencionPago;
    }

    public void setCbxIntencionPago(Boolean cbxIntencionPago) {
        this.cbxIntencionPago = cbxIntencionPago;
    }

    public Boolean getCbxRazonDeuda() {
        return cbxRazonDeuda;
    }

    public void setCbxRazonDeuda(Boolean cbxRazonDeuda) {
        this.cbxRazonDeuda = cbxRazonDeuda;
    }

    public Boolean getChkCuentaPrincipalRelacion() {
        return chkCuentaPrincipalRelacion;
    }

    public void setChkCuentaPrincipalRelacion(Boolean chkCuentaPrincipalRelacion) {
        this.chkCuentaPrincipalRelacion = chkCuentaPrincipalRelacion;
    }

    public Boolean getTxtCuentaRelacionada() {
        return txtCuentaRelacionada;
    }

    public void setTxtCuentaRelacionada(Boolean txtCuentaRelacionada) {
        this.txtCuentaRelacionada = txtCuentaRelacionada;
    }

    public Boolean getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(Boolean btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public Boolean getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Boolean btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public List<SelectItem> getLst_actor() {
        return lst_actor;
    }

    public void setLst_actor(List<SelectItem> lst_actor) {
        this.lst_actor = lst_actor;
    }

    public List<SelectItem> getLst_moneda() {
        return lst_moneda;
    }

    public void setLst_moneda(List<SelectItem> lst_moneda) {
        this.lst_moneda = lst_moneda;
    }

    public List<SelectItem> getLst_nacionalidad() {
        return lst_nacionalidad;
    }

    public void setLst_nacionalidad(List<SelectItem> lst_nacionalidad) {
        this.lst_nacionalidad = lst_nacionalidad;
    }

    public List<SelectItem> getLst_pais() {
        return lst_pais;
    }

    public void setLst_pais(List<SelectItem> lst_pais) {
        this.lst_pais = lst_pais;
    }

    public List<SelectItem> getLst_departamento() {
        return lst_departamento;
    }

    public void setLst_departamento(List<SelectItem> lst_departamento) {
        this.lst_departamento = lst_departamento;
    }

    public List<SelectItem> getLst_sexo() {
        return lst_sexo;
    }

    public void setLst_sexo(List<SelectItem> lst_sexo) {
        this.lst_sexo = lst_sexo;
    }

    public List<SelectItem> getLst_estado_civil() {
        return lst_estado_civil;
    }

    public void setLst_estado_civil(List<SelectItem> lst_estado_civil) {
        this.lst_estado_civil = lst_estado_civil;
    }

    public List<SelectItem> getLst_gestor() {
        return lst_gestor;
    }

    public void setLst_gestor(List<SelectItem> lst_gestor) {
        this.lst_gestor = lst_gestor;
    }

    public List<SelectItem> getLst_garantia() {
        return lst_garantia;
    }

    public void setLst_garantia(List<SelectItem> lst_garantia) {
        this.lst_garantia = lst_garantia;
    }

    public List<SelectItem> getLst_cargado() {
        return lst_cargado;
    }

    public void setLst_cargado(List<SelectItem> lst_cargado) {
        this.lst_cargado = lst_cargado;
    }

    public List<SelectItem> getLst_codigo_resultado() {
        return lst_codigo_resultado;
    }

    public void setLst_codigo_resultado(List<SelectItem> lst_codigo_resultado) {
        this.lst_codigo_resultado = lst_codigo_resultado;
    }

    public List<SelectItem> getLst_cosecha() {
        return lst_cosecha;
    }

    public void setLst_cosecha(List<SelectItem> lst_cosecha) {
        this.lst_cosecha = lst_cosecha;
    }

    public List<SelectItem> getLst_anticipo() {
        return lst_anticipo;
    }

    public void setLst_anticipo(List<SelectItem> lst_anticipo) {
        this.lst_anticipo = lst_anticipo;
    }

    public List<SelectItem> getLst_antiguedad() {
        return lst_antiguedad;
    }

    public void setLst_antiguedad(List<SelectItem> lst_antiguedad) {
        this.lst_antiguedad = lst_antiguedad;
    }

    public List<SelectItem> getLst_estado_judicial() {
        return lst_estado_judicial;
    }

    public void setLst_estado_judicial(List<SelectItem> lst_estado_judicial) {
        this.lst_estado_judicial = lst_estado_judicial;
    }

    public List<SelectItem> getLst_estatus_judicial() {
        return lst_estatus_judicial;
    }

    public void setLst_estatus_judicial(List<SelectItem> lst_estatus_judicial) {
        this.lst_estatus_judicial = lst_estatus_judicial;
    }

    public List<SelectItem> getLst_estado_extrajudicial() {
        return lst_estado_extrajudicial;
    }

    public void setLst_estado_extrajudicial(List<SelectItem> lst_estado_extrajudicial) {
        this.lst_estado_extrajudicial = lst_estado_extrajudicial;
    }

    public List<SelectItem> getLst_estatus_extrajudicial() {
        return lst_estatus_extrajudicial;
    }

    public void setLst_estatus_extrajudicial(List<SelectItem> lst_estatus_extrajudicial) {
        this.lst_estatus_extrajudicial = lst_estatus_extrajudicial;
    }

    public List<SelectItem> getLst_intension_pago() {
        return lst_intension_pago;
    }

    public void setLst_intension_pago(List<SelectItem> lst_intension_pago) {
        this.lst_intension_pago = lst_intension_pago;
    }

    public List<SelectItem> getLst_razon_deuda() {
        return lst_razon_deuda;
    }

    public void setLst_razon_deuda(List<SelectItem> lst_razon_deuda) {
        this.lst_razon_deuda = lst_razon_deuda;
    }

    public Boolean getSolo_cargados() {
        return solo_cargados;
    }

    public void setSolo_cargados(Boolean solo_cargados) {
        this.solo_cargados = solo_cargados;
    }

}
