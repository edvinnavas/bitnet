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
    
    private Deudor_List selectedDeudor;
    private List<Deudor_List> lst_deudor;
    private List<SelectItem> lst_sexo = new ArrayList<>();
    private List<SelectItem> lst_estado_civil = new ArrayList<>();
    private List<SelectItem> lst_pais = new ArrayList<>();
    private List<SelectItem> lst_departamento = new ArrayList<>();
    private List<SelectItem> lst_actor = new ArrayList<>();
    private List<SelectItem> lst_cosecha = new ArrayList<>();
    private List<SelectItem> lst_moneda = new ArrayList<>();
    private List<SelectItem> lst_estado = new ArrayList<>();
    private List<SelectItem> lst_status = new ArrayList<>();
    private List<SelectItem> lst_garantia = new ArrayList<>();
    private List<SelectItem> lst_gestor = new ArrayList<>();
    private List<SelectItem> lst_cargado = new ArrayList<>();
    private List<SelectItem> lst_anticipo = new ArrayList<>();
    private List<SelectItem> lst_antiguedad = new ArrayList<>();
    
    //Variables para registrar la información del usuario.
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
    private boolean PDF_d;
    private boolean INV_d;
    private boolean MAYCOM_d;
    private boolean NITS_d;
    private String cosecha_d;
    private String no_cuenta_otro_d;
    private String descripcion_adicional_d;
    private Date fecha_recepcion_d;
    private String anticipo_d;
    private String antiguedad_d;
    private Double saldo_d;
    private String convenio_pactado_d;
    private Double cuota_convenio_d;
    private String costas_pagadas_d;
    private String situacion_caso_d;
    private String opcion_proximo_pago_d;
    
    //Habilitar los campos del formulario Usuario.
    private Boolean txtNombre;
    private Boolean txtDpi;
    private Boolean txtNit;
    private Boolean calFechaNac;
    private Boolean somSexo;
    private Boolean somEstadoCivil;
    private Boolean txtProfesion;
    private Boolean somPais;
    private Boolean somDepartamento;
    private Boolean txtNacionalidad;
    private Boolean txtDireccion;
    private Boolean spnZona;
    private Boolean txtTelefonoCasa;
    private Boolean txtTelefonoCelular;
    private Boolean txtCorreoElectronico;
    private Boolean txtLugarTrabajo;
    private Boolean txtContactoTrabajo;
    private Boolean txtTelefonoTrabajo;
    private Boolean somActor;
    private Boolean spnCaso;
    private Boolean calFechaIngreso;
    private Boolean somCosecha;
    private Boolean txtNoCuenta;
    private Boolean txtOtroNoCuenta;
    private Boolean somMoneda;
    private Boolean spnMontoInicial;
    private Boolean somEstado;
    private Boolean somStatus;
    private Boolean somGarantia;
    private Boolean calFechaRecepcion;
    private Boolean somGestor;
    private Boolean somCargado;
    private Boolean somAnticipo;
    private Boolean somAntiguedad;
    private Boolean spnSaldo;
    private Boolean chkPdf;
    private Boolean chkInv;
    private Boolean chkMay;
    private Boolean chkNit;
    private Boolean itaDescripcion;
    private Boolean itaDescripcionAdicional;
    private Boolean btnAceptar;
    private Boolean btnCancelar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
         
            Driver drive = new Driver();
            this.lst_sexo = drive.lista_sexo();
            this.lst_estado_civil = drive.lista_estado_civil();
            this.lst_pais = drive.lista_paises();
            this.lst_departamento = drive.lista_departamentos();
            String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado = 'VIGENTE'";
            this.lst_actor = drive.lista_SelectItem(lista_actor_sql, this.ambiente);
            this.lst_cosecha = drive.lista_cosechas();
            this.lst_moneda = drive.lista_moneda();
            String lista_sestado_sql = "select s.sestado, s.nombre from sestado s where s.estado = 'VIGENTE'";
            this.lst_estado = drive.lista_SelectItem(lista_sestado_sql, this.ambiente);
            String lista_estatus_sql = "select e.estatus, e.nombre from estatus e where e.estado = 'VIGENTE'";
            this.lst_status = drive.lista_SelectItem(lista_estatus_sql, this.ambiente);
            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado = 'VIGENTE'";
            this.lst_garantia = drive.lista_SelectItem(lista_garantia_sql, this.ambiente);
            String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
            this.lst_gestor = drive.lista_SelectItem(lista_gestor_sql, this.ambiente);
            lst_cargado = drive.lista_cargado();
            lst_anticipo = drive.lista_anticipo();
            lst_antiguedad = drive.lista_antiguedad();
            
            String cadenasql = "select "
                    + "d.deudor as deudor, "
                    + "a.nombre as actor, "
                    + "d.caso as caso, "
                    + "d.nombre as nombre, "
                    + "d.no_cuenta as no_cuenta, "
                    + "d.no_cuenta_otro as no_cuenta_otro, "
                    + "d.cargado as cargado "
                    + "from "
                    + "deudor d left join actor a on (d.actor = a.actor)";
            
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
            
            lst_deudor = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Deudor_List deu = new Deudor_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                lst_deudor.add(deu);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            Driver drive = new Driver();
            this.lst_sexo = drive.lista_sexo();
            this.lst_estado_civil = drive.lista_estado_civil();
            this.lst_pais = drive.lista_paises();
            this.lst_departamento = drive.lista_departamentos();            
            String lista_actor_sql = "select a.actor, a.nombre from actor a where a.estado = 'VIGENTE'";
            this.lst_actor = drive.lista_SelectItem(lista_actor_sql, this.ambiente);
            this.lst_cosecha = drive.lista_cosechas();
            this.lst_moneda = drive.lista_moneda();
            String lista_sestado_sql = "select s.sestado, s.nombre from sestado s where s.estado = 'VIGENTE'";
            this.lst_estado = drive.lista_SelectItem(lista_sestado_sql, this.ambiente);
            String lista_estatus_sql = "select e.estatus, e.nombre from estatus e where e.estado = 'VIGENTE'";
            this.lst_status = drive.lista_SelectItem(lista_estatus_sql, this.ambiente);
            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado = 'VIGENTE'";
            this.lst_garantia = drive.lista_SelectItem(lista_garantia_sql, this.ambiente);
            String lista_gestor_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.gestor='SI' order by u.nombre";
            this.lst_gestor = drive.lista_SelectItem(lista_gestor_sql, this.ambiente);
            lst_cargado = drive.lista_cargado();
            lst_anticipo = drive.lista_anticipo();
            lst_antiguedad = drive.lista_antiguedad();
            
            String cadenasql = "select "
                    + "d.deudor as deudor, "
                    + "a.nombre as actor, "
                    + "d.caso as caso, "
                    + "d.nombre as nombre, "
                    + "d.no_cuenta as no_cuenta, "
                    + "d.no_cuenta_otro as no_cuenta_otro, "
                    + "d.cargado as cargado "
                    + "from "
                    + "deudor d left join actor a on (d.actor = a.actor)";
            
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
            
            lst_deudor = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Deudor_List deu = new Deudor_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                lst_deudor.add(deu);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        Date fecha_actual = new Date();
        
        this.actor_d = 0;
        this.moneda_d = "QUETZAL";
        this.dpi_d = "";
        this.nit_d = "";
        this.fecha_nacimiento_d = fecha_actual;
        this.nombre_d = "";
        this.nacionalidad_d = "Guatemalteco";
        this.telefono_casa_d = "";
        this.telefono_celular_d = "";
        this.direccion_d = "";
        this.zona_d = 0;
        this.pais_d = "Guatemala";
        this.departamento_d = "Guatemala";
        this.sexo_d = "-";
        this.estado_civil_d = "-";
        this.fecha_ingreso_d = fecha_actual;
        this.profesion_d = null;
        this.correo_electronico_d = null;
        this.lugar_trabajo_d = null;
        this.direccion_trabajo_d = null;
        this.telefono_trabajo_d = null;
        this.monto_inicial_d = 0.00;
        this.gestor_d = 0;
        this.sestado_d = 0;
        this.estatus_d = 0;
        this.no_cuenta_d = "";
        this.garantia_d = 0;
        this.cargado_d = "CARGADO";
        this.estado_d = "VIGENTE";
        this.descripcion_d = "";
        this.codigo_contactabilidad_d = 1;
        this.caso_d = 0;
        this.PDF_d = false;
        this.INV_d = false;
        this.MAYCOM_d = false;
        this.NITS_d = false;
        this.cosecha_d = "0 antes 31 dic 2008";
        this.no_cuenta_otro_d = "";
        this.descripcion_adicional_d = "";
        this.fecha_recepcion_d = fecha_actual;
        this.anticipo_d = "NO";
        this.antiguedad_d = "BRONCE";
        this.saldo_d = 0.00;
        this.convenio_pactado_d = "";
        this.cuota_convenio_d = 0.00;
        this.costas_pagadas_d = "";
        this.situacion_caso_d = "";
        this.opcion_proximo_pago_d = "NO";
        
        this.txtNombre = false;
        this.txtDpi = false;
        this.txtNit = false;
        this.calFechaNac = false;
        this.somSexo = false;
        this.somEstadoCivil = false;
        this.txtProfesion = false;
        this.somPais = false;
        this.somDepartamento = false;
        this.txtNacionalidad = false;
        this.txtDireccion = false;
        this.spnZona = false;
        this.txtTelefonoCasa = false;
        this.txtTelefonoCelular = false;
        this.txtCorreoElectronico = false;
        this.txtLugarTrabajo = false;
        this.txtContactoTrabajo = false;
        this.txtTelefonoTrabajo = false;
        this.somActor = false;
        this.spnCaso = false;
        this.calFechaIngreso = false;
        this.somCosecha = false;
        this.txtNoCuenta = false;
        this.txtOtroNoCuenta = false;
        this.somMoneda = false;
        this.spnMontoInicial = false;
        this.somEstado = false;
        this.somStatus = false;
        this.somGarantia = false;
        this.calFechaRecepcion = false;
        this.somGestor = false;
        this.somCargado = false;
        this.somAnticipo = false;
        this.somAntiguedad = false;
        this.spnSaldo = false;
        this.chkPdf = false;
        this.chkInv = false;
        this.chkMay = false;
        this.chkNit = false;
        this.itaDescripcion = false;
        this.itaDescripcionAdicional = false;
        this.btnAceptar = false;
        this.btnCancelar = false;
        
        this.opcion = "INSERTAR";
        
        RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
    }
    
    public void carga_info_modificar() {
        try {
            if (this.selectedDeudor != null) {
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
                        + "u.opcion_proximo_pago "
                        + "from deudor u where u.deudor=" + this.selectedDeudor.getDeudor();
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
                    this.descripcion_d = vector_result[i][28];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][29]);
                    this.caso_d = Integer.parseInt(vector_result[i][30]);
                    if (vector_result[i][31].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][32].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][35];
                    this.no_cuenta_otro_d = vector_result[i][36];
                    this.descripcion_adicional_d = vector_result[i][37];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][38]);
                    this.anticipo_d = vector_result[i][39];
                    this.antiguedad_d = vector_result[i][40];
                    //this.fecha_proximo_pago_d = vector_result[i][41];
                    //this.monto_proximo_pago_d = vector_result[i][42];
                    this.saldo_d = Double.parseDouble(vector_result[i][43]);
                    this.convenio_pactado_d = vector_result[i][44];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][45]);
                    this.costas_pagadas_d = vector_result[i][46];
                    this.situacion_caso_d = vector_result[i][47];
                    this.opcion_proximo_pago_d = vector_result[i][48];
                }

                this.txtNombre = false;
                this.txtDpi = false;
                this.txtNit = false;
                this.calFechaNac = false;
                this.somSexo = false;
                this.somEstadoCivil = false;
                this.txtProfesion = false;
                this.somPais = false;
                this.somDepartamento = false;
                this.txtNacionalidad = false;
                this.txtDireccion = false;
                this.spnZona = false;
                this.txtTelefonoCasa = false;
                this.txtTelefonoCelular = false;
                this.txtCorreoElectronico = false;
                this.txtLugarTrabajo = false;
                this.txtContactoTrabajo = false;
                this.txtTelefonoTrabajo = false;
                this.somActor = false;
                this.spnCaso = false;
                this.calFechaIngreso = false;
                this.somCosecha = false;
                this.txtNoCuenta = false;
                this.txtOtroNoCuenta = false;
                this.somMoneda = false;
                this.spnMontoInicial = false;
                this.somEstado = false;
                this.somStatus = false;
                this.somGarantia = false;
                this.calFechaRecepcion = false;
                this.somGestor = false;
                this.somCargado = false;
                this.somAnticipo = false;
                this.somAntiguedad = false;
                this.spnSaldo = false;
                this.chkPdf = false;
                this.chkInv = false;
                this.chkMay = false;
                this.chkNit = false;
                this.itaDescripcion = false;
                this.itaDescripcionAdicional = false;
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
                        + "u.opcion_proximo_pago "
                        + "from deudor u where u.deudor=" + this.selectedDeudor.getDeudor();
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
                    this.descripcion_d = vector_result[i][28];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][29]);
                    this.caso_d = Integer.parseInt(vector_result[i][30]);
                    if (vector_result[i][31].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][32].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][35];
                    this.no_cuenta_otro_d = vector_result[i][36];
                    this.descripcion_adicional_d = vector_result[i][37];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][38]);
                    this.anticipo_d = vector_result[i][39];
                    this.antiguedad_d = vector_result[i][40];
                    //this.fecha_proximo_pago_d = vector_result[i][41];
                    //this.monto_proximo_pago_d = vector_result[i][42];
                    this.saldo_d = Double.parseDouble(vector_result[i][43]);
                    this.convenio_pactado_d = vector_result[i][44];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][45]);
                    this.costas_pagadas_d = vector_result[i][46];
                    this.situacion_caso_d = vector_result[i][47];
                    this.opcion_proximo_pago_d = vector_result[i][48];
                }

                this.txtNombre = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.calFechaNac = true;
                this.somSexo = true;
                this.somEstadoCivil = true;
                this.txtProfesion = true;
                this.somPais = true;
                this.somDepartamento = true;
                this.txtNacionalidad = true;
                this.txtDireccion = true;
                this.spnZona = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtContactoTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.somActor = true;
                this.spnCaso = true;
                this.calFechaIngreso = true;
                this.somCosecha = true;
                this.txtNoCuenta = true;
                this.txtOtroNoCuenta = true;
                this.somMoneda = true;
                this.spnMontoInicial = true;
                this.somEstado = true;
                this.somStatus = true;
                this.somGarantia = true;
                this.calFechaRecepcion = true;
                this.somGestor = true;
                this.somCargado = true;
                this.somAnticipo = true;
                this.somAntiguedad = true;
                this.spnSaldo = true;
                this.chkPdf = true;
                this.chkInv = true;
                this.chkMay = true;
                this.chkNit = true;
                this.itaDescripcion = true;
                this.itaDescripcionAdicional = true;
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
                        + "u.opcion_proximo_pago "
                        + "from deudor u where u.deudor=" + this.selectedDeudor.getDeudor();
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
                    this.descripcion_d = vector_result[i][28];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][29]);
                    this.caso_d = Integer.parseInt(vector_result[i][30]);
                    if (vector_result[i][31].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][32].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][35];
                    this.no_cuenta_otro_d = vector_result[i][36];
                    this.descripcion_adicional_d = vector_result[i][37];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][38]);
                    this.anticipo_d = vector_result[i][39];
                    this.antiguedad_d = vector_result[i][40];
                    //this.fecha_proximo_pago_d = vector_result[i][41];
                    //this.monto_proximo_pago_d = vector_result[i][42];
                    this.saldo_d = Double.parseDouble(vector_result[i][43]);
                    this.convenio_pactado_d = vector_result[i][44];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][45]);
                    this.costas_pagadas_d = vector_result[i][46];
                    this.situacion_caso_d = vector_result[i][47];
                    this.opcion_proximo_pago_d = vector_result[i][48];
                }

                this.txtNombre = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.calFechaNac = true;
                this.somSexo = true;
                this.somEstadoCivil = true;
                this.txtProfesion = true;
                this.somPais = true;
                this.somDepartamento = true;
                this.txtNacionalidad = true;
                this.txtDireccion = true;
                this.spnZona = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtContactoTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.somActor = true;
                this.spnCaso = true;
                this.calFechaIngreso = true;
                this.somCosecha = true;
                this.txtNoCuenta = true;
                this.txtOtroNoCuenta = true;
                this.somMoneda = true;
                this.spnMontoInicial = true;
                this.somEstado = true;
                this.somStatus = true;
                this.somGarantia = true;
                this.calFechaRecepcion = true;
                this.somGestor = true;
                this.somCargado = true;
                this.somAnticipo = true;
                this.somAntiguedad = true;
                this.spnSaldo = true;
                this.chkPdf = true;
                this.chkInv = true;
                this.chkMay = true;
                this.chkNit = true;
                this.itaDescripcion = true;
                this.itaDescripcionAdicional = true;
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
                        + "u.opcion_proximo_pago "
                        + "from deudor u where u.deudor=" + this.selectedDeudor.getDeudor();
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
                    this.descripcion_d = vector_result[i][28];
                    this.codigo_contactabilidad_d = Integer.parseInt(vector_result[i][29]);
                    this.caso_d = Integer.parseInt(vector_result[i][30]);
                    if (vector_result[i][31].equals("SI")) {
                        this.PDF_d = true;
                    } else {
                        this.PDF_d = false;
                    }
                    if (vector_result[i][32].equals("SI")) {
                        this.INV_d = true;
                    } else {
                        this.INV_d = false;
                    }
                    if (vector_result[i][33].equals("SI")) {
                        this.MAYCOM_d = true;
                    } else {
                        this.MAYCOM_d = false;
                    }
                    if (vector_result[i][34].equals("SI")) {
                        this.NITS_d = true;
                    } else {
                        this.NITS_d = false;
                    }
                    this.cosecha_d = vector_result[i][35];
                    this.no_cuenta_otro_d = vector_result[i][36];
                    this.descripcion_adicional_d = vector_result[i][37];
                    this.fecha_recepcion_d = formatter.parse(vector_result[i][38]);
                    this.anticipo_d = vector_result[i][39];
                    this.antiguedad_d = vector_result[i][40];
                    //this.fecha_proximo_pago_d = vector_result[i][41];
                    //this.monto_proximo_pago_d = vector_result[i][42];
                    this.saldo_d = Double.parseDouble(vector_result[i][43]);
                    this.convenio_pactado_d = vector_result[i][44];
                    this.cuota_convenio_d = Double.parseDouble(vector_result[i][45]);
                    this.costas_pagadas_d = vector_result[i][46];
                    this.situacion_caso_d = vector_result[i][47];
                    this.opcion_proximo_pago_d = vector_result[i][48];
                }

                this.txtNombre = true;
                this.txtDpi = true;
                this.txtNit = true;
                this.calFechaNac = true;
                this.somSexo = true;
                this.somEstadoCivil = true;
                this.txtProfesion = true;
                this.somPais = true;
                this.somDepartamento = true;
                this.txtNacionalidad = true;
                this.txtDireccion = true;
                this.spnZona = true;
                this.txtTelefonoCasa = true;
                this.txtTelefonoCelular = true;
                this.txtCorreoElectronico = true;
                this.txtLugarTrabajo = true;
                this.txtContactoTrabajo = true;
                this.txtTelefonoTrabajo = true;
                this.somActor = true;
                this.spnCaso = true;
                this.calFechaIngreso = true;
                this.somCosecha = true;
                this.txtNoCuenta = true;
                this.txtOtroNoCuenta = true;
                this.somMoneda = true;
                this.spnMontoInicial = true;
                this.somEstado = true;
                this.somStatus = true;
                this.somGarantia = true;
                this.calFechaRecepcion = true;
                this.somGestor = true;
                this.somCargado = true;
                this.somAnticipo = true;
                this.somAntiguedad = true;
                this.spnSaldo = true;
                this.chkPdf = true;
                this.chkInv = true;
                this.chkMay = true;
                this.chkNit = true;
                this.itaDescripcion = true;
                this.itaDescripcionAdicional = true;
                this.btnAceptar = true;
                this.btnCancelar = false;

                this.opcion = "VER";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un deudor del listado."));
            }
        } catch (NumberFormatException | ParseException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void aceptar() {
        if(this.opcion.equals("INSERTAR")) {
            this.insertar();
        }
        if(this.opcion.equals("MODIFICAR")) {
            this.modificar();
        }
        if(this.opcion.equals("ELIMINAR")) {
            this.eliminar();
        }
        if(this.opcion.equals("ACTIVAR")) {
            this.activar();
        }
    }
    
    private void insertar() {
        String resultado;

        try {
            if (!this.nombre_d.equals("")) {
                
                String PDF = "";
                if(this.PDF_d) { PDF = "SI"; } else { PDF = "NO"; }
                String INV = "";
                if(this.INV_d) { INV = "SI"; } else { INV = "NO"; }
                String MAYCOM = "";
                if(this.MAYCOM_d) { MAYCOM = "SI"; } else { MAYCOM = "NO"; }
                String NITS = "";
                if(this.NITS_d) { NITS = "SI"; } else { NITS = "NO"; }
                
                GregorianCalendar gregory1 = new GregorianCalendar();
                gregory1.set(this.fecha_nacimiento_d.getYear() + 1900, this.fecha_nacimiento_d.getMonth(), this.fecha_nacimiento_d.getDate());
                XMLGregorianCalendar gre_fecha_nacimiento_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);
                
                GregorianCalendar gregory2 = new GregorianCalendar();
                gregory2.set(this.fecha_ingreso_d.getYear() + 1900, this.fecha_ingreso_d.getMonth(), this.fecha_ingreso_d.getDate());
                XMLGregorianCalendar gre_fecha_ingreso_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);
                
                GregorianCalendar gregory3 = new GregorianCalendar();
                gregory3.set(this.fecha_recepcion_d.getYear() + 1900, this.fecha_recepcion_d.getMonth(), this.fecha_recepcion_d.getDate());
                XMLGregorianCalendar gre_fecha_recepcion_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);
                
                Driver driver = new Driver();
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                resultado = servicio.deudorInsertar(
                        id_usuario,
                        actor_d, 
                        moneda_d, 
                        dpi_d, 
                        nit_d, 
                        gre_fecha_nacimiento_d, 
                        nombre_d, 
                        nacionalidad_d, 
                        telefono_casa_d, 
                        telefono_celular_d, 
                        direccion_d, 
                        zona_d, 
                        pais_d, 
                        departamento_d, 
                        sexo_d, 
                        estado_civil_d, 
                        gre_fecha_ingreso_d, 
                        profesion_d, 
                        correo_electronico_d, 
                        lugar_trabajo_d, 
                        direccion_trabajo_d, 
                        telefono_trabajo_d, 
                        monto_inicial_d, 
                        gestor_d, 
                        sestado_d, 
                        estatus_d, 
                        no_cuenta_d, 
                        garantia_d, 
                        cargado_d,
                        "VIGENTE",
                        descripcion_d, 
                        1,
                        caso_d, 
                        PDF, 
                        INV, 
                        MAYCOM, 
                        NITS, 
                        cosecha_d, 
                        no_cuenta_otro_d, 
                        descripcion_adicional_d, 
                        gre_fecha_recepcion_d, 
                        anticipo_d, 
                        antiguedad_d, 
                        saldo_d, 
                        "-",
                        0.00,
                        "-",
                        "-",
                        "NO", 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del deudor."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void modificar() {
        String resultado;

        try {
            if (!this.nombre_d.equals("")) {
                
                String PDF = "";
                if(this.PDF_d) { PDF = "SI"; } else { PDF = "NO"; }
                String INV = "";
                if(this.INV_d) { INV = "SI"; } else { INV = "NO"; }
                String MAYCOM = "";
                if(this.MAYCOM_d) { MAYCOM = "SI"; } else { MAYCOM = "NO"; }
                String NITS = "";
                if(this.NITS_d) { NITS = "SI"; } else { NITS = "NO"; }
                
                GregorianCalendar gregory1 = new GregorianCalendar();
                gregory1.set(this.fecha_nacimiento_d.getYear() + 1900, this.fecha_nacimiento_d.getMonth(), this.fecha_nacimiento_d.getDate());
                XMLGregorianCalendar gre_fecha_nacimiento_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);
                
                GregorianCalendar gregory2 = new GregorianCalendar();
                gregory2.set(this.fecha_ingreso_d.getYear() + 1900, this.fecha_ingreso_d.getMonth(), this.fecha_ingreso_d.getDate());
                XMLGregorianCalendar gre_fecha_ingreso_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);
                
                GregorianCalendar gregory3 = new GregorianCalendar();
                gregory3.set(this.fecha_recepcion_d.getYear() + 1900, this.fecha_recepcion_d.getMonth(), this.fecha_recepcion_d.getDate());
                XMLGregorianCalendar gre_fecha_recepcion_d = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);
                
                Driver driver = new Driver();
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                resultado = servicio.deudorModificar(
                        id_usuario,
                        this.selectedDeudor.getDeudor(),
                        actor_d, 
                        moneda_d, 
                        dpi_d, 
                        nit_d, 
                        gre_fecha_nacimiento_d, 
                        nombre_d, 
                        nacionalidad_d, 
                        telefono_casa_d, 
                        telefono_celular_d, 
                        direccion_d, 
                        zona_d, 
                        pais_d, 
                        departamento_d, 
                        sexo_d, 
                        estado_civil_d, 
                        gre_fecha_ingreso_d, 
                        profesion_d, 
                        correo_electronico_d, 
                        lugar_trabajo_d, 
                        direccion_trabajo_d, 
                        telefono_trabajo_d, 
                        monto_inicial_d, 
                        gestor_d, 
                        sestado_d, 
                        estatus_d, 
                        no_cuenta_d, 
                        garantia_d, 
                        cargado_d,
                        "VIGENTE",
                        descripcion_d, 
                        1,
                        caso_d, 
                        PDF, 
                        INV, 
                        MAYCOM, 
                        NITS, 
                        cosecha_d, 
                        no_cuenta_otro_d, 
                        descripcion_adicional_d, 
                        gre_fecha_recepcion_d, 
                        anticipo_d, 
                        antiguedad_d, 
                        saldo_d, 
                        "-",
                        0.00,
                        "-",
                        "-",
                        "NO", 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetDeu').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
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
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Deudor_List getSelectedDeudor() {
        return selectedDeudor;
    }

    public void setSelectedDeudor(Deudor_List selectedDeudor) {
        this.selectedDeudor = selectedDeudor;
    }

    public List<Deudor_List> getLst_deudor() {
        return lst_deudor;
    }

    public void setLst_deudor(List<Deudor_List> lst_deudor) {
        this.lst_deudor = lst_deudor;
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

    public String getNo_cuenta_d() {
        return no_cuenta_d;
    }

    public void setNo_cuenta_d(String no_cuenta_d) {
        this.no_cuenta_d = no_cuenta_d;
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

    public List<SelectItem> getLst_actor() {
        return lst_actor;
    }

    public void setLst_actor(List<SelectItem> lst_actor) {
        this.lst_actor = lst_actor;
    }

    public List<SelectItem> getLst_cosecha() {
        return lst_cosecha;
    }

    public void setLst_cosecha(List<SelectItem> lst_cosecha) {
        this.lst_cosecha = lst_cosecha;
    }

    public List<SelectItem> getLst_moneda() {
        return lst_moneda;
    }

    public void setLst_moneda(List<SelectItem> lst_moneda) {
        this.lst_moneda = lst_moneda;
    }

    public List<SelectItem> getLst_estado() {
        return lst_estado;
    }

    public void setLst_estado(List<SelectItem> lst_estado) {
        this.lst_estado = lst_estado;
    }

    public List<SelectItem> getLst_status() {
        return lst_status;
    }

    public void setLst_status(List<SelectItem> lst_status) {
        this.lst_status = lst_status;
    }

    public List<SelectItem> getLst_garantia() {
        return lst_garantia;
    }

    public void setLst_garantia(List<SelectItem> lst_garantia) {
        this.lst_garantia = lst_garantia;
    }

    public List<SelectItem> getLst_gestor() {
        return lst_gestor;
    }

    public void setLst_gestor(List<SelectItem> lst_gestor) {
        this.lst_gestor = lst_gestor;
    }

    public List<SelectItem> getLst_cargado() {
        return lst_cargado;
    }

    public void setLst_cargado(List<SelectItem> lst_cargado) {
        this.lst_cargado = lst_cargado;
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

    public Integer getGarantia_d() {
        return garantia_d;
    }

    public void setGarantia_d(Integer garantia_d) {
        this.garantia_d = garantia_d;
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

    public boolean isPDF_d() {
        return PDF_d;
    }

    public void setPDF_d(boolean PDF_d) {
        this.PDF_d = PDF_d;
    }

    public boolean isINV_d() {
        return INV_d;
    }

    public void setINV_d(boolean INV_d) {
        this.INV_d = INV_d;
    }

    public boolean isMAYCOM_d() {
        return MAYCOM_d;
    }

    public void setMAYCOM_d(boolean MAYCOM_d) {
        this.MAYCOM_d = MAYCOM_d;
    }

    public boolean isNITS_d() {
        return NITS_d;
    }

    public void setNITS_d(boolean NITS_d) {
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

    public Date getFecha_nacimiento_d() {
        return fecha_nacimiento_d;
    }

    public void setFecha_nacimiento_d(Date fecha_nacimiento_d) {
        this.fecha_nacimiento_d = fecha_nacimiento_d;
    }

    public Date getFecha_ingreso_d() {
        return fecha_ingreso_d;
    }

    public void setFecha_ingreso_d(Date fecha_ingreso_d) {
        this.fecha_ingreso_d = fecha_ingreso_d;
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

    public String getOpcion_proximo_pago_d() {
        return opcion_proximo_pago_d;
    }

    public void setOpcion_proximo_pago_d(String opcion_proximo_pago_d) {
        this.opcion_proximo_pago_d = opcion_proximo_pago_d;
    }
    
    public Boolean getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(Boolean txtNombre) {
        this.txtNombre = txtNombre;
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

    public Boolean getCalFechaNac() {
        return calFechaNac;
    }

    public void setCalFechaNac(Boolean calFechaNac) {
        this.calFechaNac = calFechaNac;
    }

    public Boolean getSomSexo() {
        return somSexo;
    }

    public void setSomSexo(Boolean somSexo) {
        this.somSexo = somSexo;
    }

    public Boolean getSomEstadoCivil() {
        return somEstadoCivil;
    }

    public void setSomEstadoCivil(Boolean somEstadoCivil) {
        this.somEstadoCivil = somEstadoCivil;
    }

    public Boolean getTxtProfesion() {
        return txtProfesion;
    }

    public void setTxtProfesion(Boolean txtProfesion) {
        this.txtProfesion = txtProfesion;
    }

    public Boolean getSomPais() {
        return somPais;
    }

    public void setSomPais(Boolean somPais) {
        this.somPais = somPais;
    }

    public Boolean getSomDepartamento() {
        return somDepartamento;
    }

    public void setSomDepartamento(Boolean somDepartamento) {
        this.somDepartamento = somDepartamento;
    }

    public Boolean getTxtNacionalidad() {
        return txtNacionalidad;
    }

    public void setTxtNacionalidad(Boolean txtNacionalidad) {
        this.txtNacionalidad = txtNacionalidad;
    }

    public Boolean getTxtDireccion() {
        return txtDireccion;
    }

    public void setTxtDireccion(Boolean txtDireccion) {
        this.txtDireccion = txtDireccion;
    }

    public Boolean getSpnZona() {
        return spnZona;
    }

    public void setSpnZona(Boolean spnZona) {
        this.spnZona = spnZona;
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

    public Boolean getTxtContactoTrabajo() {
        return txtContactoTrabajo;
    }

    public void setTxtContactoTrabajo(Boolean txtContactoTrabajo) {
        this.txtContactoTrabajo = txtContactoTrabajo;
    }

    public Boolean getTxtTelefonoTrabajo() {
        return txtTelefonoTrabajo;
    }

    public void setTxtTelefonoTrabajo(Boolean txtTelefonoTrabajo) {
        this.txtTelefonoTrabajo = txtTelefonoTrabajo;
    }

    public Boolean getSomActor() {
        return somActor;
    }

    public void setSomActor(Boolean somActor) {
        this.somActor = somActor;
    }

    public Boolean getSpnCaso() {
        return spnCaso;
    }

    public void setSpnCaso(Boolean spnCaso) {
        this.spnCaso = spnCaso;
    }

    public Boolean getCalFechaIngreso() {
        return calFechaIngreso;
    }

    public void setCalFechaIngreso(Boolean calFechaIngreso) {
        this.calFechaIngreso = calFechaIngreso;
    }

    public Boolean getSomCosecha() {
        return somCosecha;
    }

    public void setSomCosecha(Boolean somCosecha) {
        this.somCosecha = somCosecha;
    }

    public Boolean getTxtNoCuenta() {
        return txtNoCuenta;
    }

    public void setTxtNoCuenta(Boolean txtNoCuenta) {
        this.txtNoCuenta = txtNoCuenta;
    }

    public Boolean getTxtOtroNoCuenta() {
        return txtOtroNoCuenta;
    }

    public void setTxtOtroNoCuenta(Boolean txtOtroNoCuenta) {
        this.txtOtroNoCuenta = txtOtroNoCuenta;
    }

    public Boolean getSomMoneda() {
        return somMoneda;
    }

    public void setSomMoneda(Boolean somMoneda) {
        this.somMoneda = somMoneda;
    }

    public Boolean getSpnMontoInicial() {
        return spnMontoInicial;
    }

    public void setSpnMontoInicial(Boolean spnMontoInicial) {
        this.spnMontoInicial = spnMontoInicial;
    }

    public Boolean getSomEstado() {
        return somEstado;
    }

    public void setSomEstado(Boolean somEstado) {
        this.somEstado = somEstado;
    }

    public Boolean getSomStatus() {
        return somStatus;
    }

    public void setSomStatus(Boolean somStatus) {
        this.somStatus = somStatus;
    }

    public Boolean getSomGarantia() {
        return somGarantia;
    }

    public void setSomGarantia(Boolean somGarantia) {
        this.somGarantia = somGarantia;
    }

    public Boolean getCalFechaRecepcion() {
        return calFechaRecepcion;
    }

    public void setCalFechaRecepcion(Boolean calFechaRecepcion) {
        this.calFechaRecepcion = calFechaRecepcion;
    }

    public Boolean getSomGestor() {
        return somGestor;
    }

    public void setSomGestor(Boolean somGestor) {
        this.somGestor = somGestor;
    }

    public Boolean getSomCargado() {
        return somCargado;
    }

    public void setSomCargado(Boolean somCargado) {
        this.somCargado = somCargado;
    }

    public Boolean getSomAnticipo() {
        return somAnticipo;
    }

    public void setSomAnticipo(Boolean somAnticipo) {
        this.somAnticipo = somAnticipo;
    }

    public Boolean getSomAntiguedad() {
        return somAntiguedad;
    }

    public void setSomAntiguedad(Boolean somAntiguedad) {
        this.somAntiguedad = somAntiguedad;
    }

    public Boolean getSpnSaldo() {
        return spnSaldo;
    }

    public void setSpnSaldo(Boolean spnSaldo) {
        this.spnSaldo = spnSaldo;
    }

    public Boolean getChkPdf() {
        return chkPdf;
    }

    public void setChkPdf(Boolean chkPdf) {
        this.chkPdf = chkPdf;
    }

    public Boolean getChkInv() {
        return chkInv;
    }

    public void setChkInv(Boolean chkInv) {
        this.chkInv = chkInv;
    }

    public Boolean getChkMay() {
        return chkMay;
    }

    public void setChkMay(Boolean chkMay) {
        this.chkMay = chkMay;
    }

    public Boolean getChkNit() {
        return chkNit;
    }

    public void setChkNit(Boolean chkNit) {
        this.chkNit = chkNit;
    }

    public Boolean getItaDescripcion() {
        return itaDescripcion;
    }

    public void setItaDescripcion(Boolean itaDescripcion) {
        this.itaDescripcion = itaDescripcion;
    }

    public Boolean getItaDescripcionAdicional() {
        return itaDescripcionAdicional;
    }

    public void setItaDescripcionAdicional(Boolean itaDescripcionAdicional) {
        this.itaDescripcionAdicional = itaDescripcionAdicional;
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

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
