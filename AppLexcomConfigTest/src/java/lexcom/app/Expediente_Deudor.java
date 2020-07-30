package lexcom.app;

import java.io.File;
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
import jcifs.smb.SmbFile;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name = "Expediente_Deudor")
@ViewScoped
public class Expediente_Deudor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ambiente;
    // ================> USUARIO Y DEUDOR <=====================================
    private String usuario;
    private Integer deudor;
    private Boolean redender_exp_busqueda;
    private Boolean redender_exp_deudor;
    // ================> EXPEDIENTE BUSQUEDA <==================================
    private String patron;
    private List<Expediente_List> lst_expediente;
    private Expediente_List sel_expediente; 
    // ================> PESTANA CASO <=========================================
    private String nombre_deudor;
    private String actor;

    private Integer caso;
    private String no_cuenta;
    private String otro_no_cuenta;
    private Integer garantia;
    private String cargado;
    private Integer fiador;
    private String anticipo;
    
    private Date fecha_recepcion;
    private Double monto_inicial;
    private String Moneda;
    private String gestor;

    private boolean inv_pdf;
    private boolean inv_inv;
    private boolean inv_may;
    private boolean inv_nit;

    private Double pagos;
    private Double aumentos;
    private Double descuentos;
    private Double saldo;
    private Date fecha_u_pago;
    private Double monto_u_pago;
    private Boolean opcion_proximo_pago;
    private Date fecha_p_pago;
    private Double monto_p_pago;
    
    private List<SelectItem> lst_garantia;
    private List<SelectItem> lst_fiador;
    // ================> PESTANA EXTRAJUDICIAL <================================
    private String telefono_casa;
    private String telefono_celular;
    private String correo_electronico;
    private String lugar_trabajo;
    private String contacto_trabajo;
    private String telefono_trabajo;
    private String dpi;
    private String nit;
    private Integer intencion_pago;

    private Integer estado_extrajudicial;
    private Integer status_extrajudicial;

    private String convenio_pactado;
    private String notas;
    private String direccion;
    
    private List<SelectItem> lst_estado_extrajudicial;
    private List<SelectItem> lst_estatus_extrajudicial;
    private List<SelectItem> lst_intension_pago;
    
    private String com_judicial;
    // ================> PESTANA JUDICIAL <=====================================
    private Integer estado_judicial;
    private Integer status_judicial;

    private String procuracion;
    private String situacion_caso;
    private String razon_notificacion;

    private Integer procurador;
    private Date fecha_juicio;
    private Integer juzgado;
    private String no_juicio;
    private Integer notificador;
    private String abogado_deudor;
    private String sumario;
    private Date memorial;
    private String notificado;
    private Date fecha_notif;
    private Double monto_demanda;

    private List<SelectItem> lst_estado_judicial;
    private List<SelectItem> lst_estatus_judicial;
    private List<SelectItem> lst_procurador;
    private List<SelectItem> lst_juzgado;
    
    private String com_extrajudicial;
    // ================> PESTANA COBROS <=======================================
    private List<Cobro_List> lst_cobros;
    private Cobro_List cobro_sel;
    private String lb_numero_gestiones_cobros;
    // ================> PESTANA ADMON <========================================
    private List<Administrativo_List> lst_admom;
    private Administrativo_List admon_sel;
    // ================> PESTANA JURIDICO <=====================================
    private List<Juridico_List> lst_juridico;
    private Juridico_List juridico_sel;
    // ================> PESTANA PAGOS <========================================
    private List<Pago_List> lst_pagos;
    private Pago_List pago_sel;
    // ================> PESTANA RECORDATORIOS <================================
    private List<Recordatorio_List> lst_recordatorios;
    private Recordatorio_List recordatorio_sel;
    // ================> PESTANA JUICIOS <======================================
    private List<Juicio_List> lst_juicios;
    private Juicio_List juicio_sel;
    // ================> PESTANA DESCUENTOS <===================================
    private List<Descuento_List> lst_descuentos;
    private Descuento_List descuento_sel;
    // ================> PESTANA AUMENTOS <=====================================
    private List<Aumento_List> lst_aumentos;
    private Aumento_List aumento_sel;
    // ================> PESTANA FIADORES <=====================================
    private List<Fiador_List> lst_fiadores;
    private Fiador_List fiador_sel;
    // ================> PESTANA REFERENCIAS <==================================
    private List<Referencia_List> lst_referencias;
    private Referencia_List referencia_sel;
    // ================> PESTANA DIGITALIZACIONES <=============================
    private List<Digitalizacion_List> lst_digitalizaciones;
    private Digitalizacion_List digitalizacion_sel;
    // ================> PESTANA CONVENIOS <====================================
    private List<Convenio_List> lst_convenios;
    private Convenio_List convenio_sel;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            this.redender_exp_busqueda = true;
            this.redender_exp_deudor = false;
            
            Driver drive = new Driver();
            // ================> USUARIO Y DEUDOR <=============================
            this.patron = "";
            this.lst_expediente = new ArrayList<>();
            this.sel_expediente = null;
            // ================> PESTANA CASO <=================================
            this.nombre_deudor = "Nombre del deudor";
            this.actor = "Nombre del actor";

            this.caso = 0;
            this.no_cuenta = "00-00000-0";
            this.otro_no_cuenta = "00-00000-0";
            this.garantia = 0;
            this.cargado = "CARGADO";
            this.fiador = 0;
            this.anticipo = "NO";

            this.fecha_recepcion = new Date();
            this.monto_inicial = 0.00;
            this.Moneda = "QUETZAL";
            this.gestor = "Nombre del gestor";

            this.inv_pdf = false;
            this.inv_inv = false;
            this.inv_may = false;
            this.inv_nit = false;

            this.pagos = 0.00;
            this.aumentos = 0.00;
            this.descuentos = 0.00;
            this.saldo = 0.00;
            this.fecha_u_pago = new Date();
            this.monto_u_pago = 0.00;
            this.opcion_proximo_pago = false;
            this.fecha_p_pago = new Date();
            this.monto_p_pago = 0.00;
            
            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE'";
            this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
            String lista_fiador_sql = "select f.nombre, f.nombre from fiador f where f.deudor=" + this.deudor;
            this.lst_fiador = drive.lista_SelectItem_simple(lista_fiador_sql, this.ambiente);
            // ================> PESTANA EXTRAJUDICIAL <========================
            this.telefono_casa = "0000-0000";
            this.telefono_celular = "0000-0000";
            this.correo_electronico = "Correo electronico";
            this.lugar_trabajo = "Lugar de trabajo";
            this.contacto_trabajo = "Contacto trabajo";
            this.telefono_trabajo = "0000-0000";
            this.dpi = "DPI";
            this.nit = "NIT";
            this.intencion_pago = 0;

            this.estado_extrajudicial = 0;
            this.status_extrajudicial = 0;

            this.convenio_pactado = "Convenio pactado";
            this.notas = "Notas del gestor";
            this.direccion = "Direccion deudor";
            
            this.com_judicial = "";
            
            String lista_estado_extra_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extra_sql, this.ambiente);
            this.lst_estatus_extrajudicial = new ArrayList<>();
            String lista_intencion_pago_sql = "select i.intencion_pago, i.nombre from intencion_pago i where i.estado='VIGENTE'";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
            // ================> PESTANA JUDICIAL <=============================
            this.estado_judicial = 0;
            this.status_judicial = 0;

            this.procuracion = "Texto de procuracion";
            this.situacion_caso = "Situacion Caso";
            this.razon_notificacion = "Razon de notificacion";

            this.procurador = 0;
            this.fecha_juicio = new Date();
            this.juzgado = 0;
            this.no_juicio = "JUI-000";
            this.notificador = 0;
            this.abogado_deudor = "Nombre del abogado del deudor";
            this.sumario = "Sumario";
            this.memorial = new Date();
            this.notificado = "SI";
            this.fecha_notif = new Date();
            this.monto_demanda = 0.00;
            this.com_extrajudicial = "";
            
            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE'";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            this.lst_estatus_judicial = new ArrayList<>();
            String lista_procurador_sql = "select u.usuario, u.nombre from usuario u where u.procurador='SI' and u.estado='VIGENTE'";
            this.lst_procurador = drive.lista_SelectItem_simple(lista_procurador_sql, this.ambiente);
            String lista_juzgado_sql = "select j.juzgado, j.nombre from juzgado j where j.estado='VIGENTE'";
            this.lst_juzgado = drive.lista_SelectItem_simple(lista_juzgado_sql, this.ambiente);
            // ================> PESTANA COBROS <===============================
            this.lst_cobros = new ArrayList<>();
            this.cobro_sel = null;
            this.lb_numero_gestiones_cobros = "";
            // ================> PESTANA ADMON <================================
            this.lst_admom = new ArrayList<>();
            this.admon_sel = null;
            // ================> PESTANA JURIDICO <=============================
            this.lst_juridico = new ArrayList<>();
            this.juridico_sel = null;
            // ================> PESTANA PAGOS <================================
            this.lst_pagos = new ArrayList<>();
            this.pago_sel = null;
            // ================> PESTANA RECORDATORIOS <========================
            this.lst_recordatorios = new ArrayList<>();
            this.recordatorio_sel = null;
            // ================> PESTANA JUICIOS <==============================
            this.lst_juicios = new ArrayList<>();
            this.juicio_sel = null;
            // ================> PESTANA DESCUENTOS <===========================
            this.lst_descuentos = new ArrayList<>();
            this.descuento_sel = null;
            // ================> PESTANA AUMENTOS <=============================
            this.lst_aumentos = new ArrayList<>();
            this.aumento_sel = null;
            // ================> PESTANA FIADORES <=============================
            this.lst_fiadores = new ArrayList<>();
            this.fiador_sel = null;
            // ================> PESTANA REFERENCIAS <==========================
            this.lst_referencias = new ArrayList<>();
            this.referencia_sel = null;
            // ================> PESTANA DIGITALIZACIONES <=====================
            this.lst_digitalizaciones = new ArrayList<>();
            this.digitalizacion_sel = null;
            // ================> PESTANA CONVENIOS <============================
            this.lst_convenios = new ArrayList<>();
            this.convenio_sel = null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            this.redender_exp_busqueda = true;
            this.redender_exp_deudor = false;
            
            Driver drive = new Driver();
            // ================> USUARIO Y DEUDOR <=============================
            this.patron = "";
            this.lst_expediente = new ArrayList<>();
            this.sel_expediente = null;
            // ================> PESTANA CASO <=================================
            this.nombre_deudor = "Nombre del deudor";
            this.actor = "Nombre del actor";

            this.caso = 0;
            this.no_cuenta = "00-00000-0";
            this.otro_no_cuenta = "00-00000-0";
            this.garantia = 0;
            this.cargado = "CARGADO";
            this.fiador = 0;
            this.anticipo = "NO";

            this.fecha_recepcion = new Date();
            this.monto_inicial = 0.00;
            this.Moneda = "QUETZAL";
            this.gestor = "Nombre del gestor";

            this.inv_pdf = false;
            this.inv_inv = false;
            this.inv_may = false;
            this.inv_nit = false;

            this.pagos = 0.00;
            this.aumentos = 0.00;
            this.descuentos = 0.00;
            this.saldo = 0.00;
            this.fecha_u_pago = new Date();
            this.monto_u_pago = 0.00;
            this.opcion_proximo_pago = false;
            this.fecha_p_pago = new Date();
            this.monto_p_pago = 0.00;
            
            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE'";
            this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql, this.ambiente);
            String lista_fiador_sql = "select f.nombre, f.nombre from fiador f where f.deudor=" + this.deudor;
            this.lst_fiador = drive.lista_SelectItem_simple(lista_fiador_sql, this.ambiente);
            // ================> PESTANA EXTRAJUDICIAL <========================
            this.telefono_casa = "0000-0000";
            this.telefono_celular = "0000-0000";
            this.correo_electronico = "Correo electronico";
            this.lugar_trabajo = "Lugar de trabajo";
            this.contacto_trabajo = "Contacto trabajo";
            this.telefono_trabajo = "0000-0000";
            this.dpi = "DPI";
            this.nit = "NIT";
            this.intencion_pago = 0;

            this.estado_extrajudicial = 0;
            this.status_extrajudicial = 0;

            this.convenio_pactado = "Convenio pactado";
            this.notas = "Notas del gestor";
            this.direccion = "Direccion deudor";
            
            this.com_judicial = "";
            
            String lista_estado_extra_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extra_sql, this.ambiente);
            this.lst_estatus_extrajudicial = new ArrayList<>();
            String lista_intencion_pago_sql = "select i.intencion_pago, i.nombre from intencion_pago i where i.estado='VIGENTE'";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
            // ================> PESTANA JUDICIAL <=============================
            this.estado_judicial = 0;
            this.status_judicial = 0;

            this.procuracion = "Texto de procuracion";
            this.situacion_caso = "Situacion Caso";
            this.razon_notificacion = "Razon de notificacion";

            this.procurador = 0;
            this.fecha_juicio = new Date();
            this.juzgado = 0;
            this.no_juicio = "JUI-000";
            this.notificador = 0;
            this.abogado_deudor = "Nombre del abogado del deudor";
            this.sumario = "Sumario";
            this.memorial = new Date();
            this.notificado = "SI";
            this.fecha_notif = new Date();
            this.monto_demanda = 0.00;
            this.com_extrajudicial = "";
            
            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE'";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            this.lst_estatus_judicial = new ArrayList<>();
            String lista_procurador_sql = "select u.usuario, u.nombre from usuario u where u.procurador='SI' and u.estado='VIGENTE'";
            this.lst_procurador = drive.lista_SelectItem_simple(lista_procurador_sql, this.ambiente);
            String lista_juzgado_sql = "select j.juzgado, j.nombre from juzgado j where j.estado='VIGENTE'";
            this.lst_juzgado = drive.lista_SelectItem_simple(lista_juzgado_sql, this.ambiente);
            // ================> PESTANA COBROS <===============================
            this.lst_cobros = new ArrayList<>();
            this.cobro_sel = null;
            this.lb_numero_gestiones_cobros = "";
            // ================> PESTANA ADMON <================================
            this.lst_admom = new ArrayList<>();
            this.admon_sel = null;
            // ================> PESTANA JURIDICO <=============================
            this.lst_juridico = new ArrayList<>();
            this.juridico_sel = null;
            // ================> PESTANA PAGOS <================================
            this.lst_pagos = new ArrayList<>();
            this.pago_sel = null;
            // ================> PESTANA RECORDATORIOS <========================
            this.lst_recordatorios = new ArrayList<>();
            this.recordatorio_sel = null;
            // ================> PESTANA JUICIOS <==============================
            this.lst_juicios = new ArrayList<>();
            this.juicio_sel = null;
            // ================> PESTANA DESCUENTOS <===========================
            this.lst_descuentos = new ArrayList<>();
            this.descuento_sel = null;
            // ================> PESTANA AUMENTOS <=============================
            this.lst_aumentos = new ArrayList<>();
            this.aumento_sel = null;
            // ================> PESTANA FIADORES <=============================
            this.lst_fiadores = new ArrayList<>();
            this.fiador_sel = null;
            // ================> PESTANA REFERENCIAS <==========================
            this.lst_referencias = new ArrayList<>();
            this.referencia_sel = null;
            // ================> PESTANA DIGITALIZACIONES <=====================
            this.lst_digitalizaciones = new ArrayList<>();
            this.digitalizacion_sel = null;
            // ================> PESTANA CONVENIOS <============================
            this.lst_convenios = new ArrayList<>();
            this.convenio_sel = null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void buscar() {
        try {
            String cadenasql = "select "
                    + "d.deudor id_deudor, "
                    + "d.cargado cargado, "
                    + "a.nombre actor, "
                    + "ga.nombre garantia, "
                    + "u.nombre gestor, "
                    + "d.caso caso, "
                    + "d.no_cuenta no_cuenta, "
                    + "d.nombre nombre_deudor, "
                    + "d.monto_inicial monto_inicial, "
                    + "j.no_juicio no_juicio, "
                    + "se.nombre estado_extrajudicial, "
                    + "ee.nombre status_extrajudicial, "
                    + "sj.nombre sestado_judicial, "
                    + "ej.nombre status_judicial, "
                    + "d.no_cuenta_otro otro_no_cuenta "
                    + "from "
                    + "deudor d "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join sestado sj on (d.sestado=sj.sestado) "
                    + "left join estatus ej on (d.estatus=ej.estatus) "
                    + "left join sestado_extra se on (d.sestado_extra=se.sestado_extra) "
                    + "left join estatus_extra ee on (d.estatus_extra=ee.estatus_extra) "
                    + "left join garantia ga on (d.garantia=ga.garantia) "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "where "
                    + "d.cargado like '%" + this.patron + "%' or "
                    + "a.nombre like '%" + this.patron + "%' or "
                    + "ga.nombre like '%" + this.patron + "%' or "
                    + "u.nombre like '%" + this.patron + "%' or "
                    + "d.caso like '%" + this.patron + "%' or "
                    + "d.no_cuenta like '%" + this.patron + "%' or "
                    + "d.nombre like '%" + this.patron + "%' or "
                    + "d.monto_inicial like '%" + this.patron + "%' or "
                    + "j.no_juicio like '%" + this.patron + "%' or "
                    + "se.nombre like '%" + this.patron + "%' or "
                    + "ee.nombre like '%" + this.patron + "%' or "
                    + "sj.nombre like '%" + this.patron + "%' or "
                    + "ej.nombre like '%" + this.patron + "%' or "
                    + "d.no_cuenta_otro like '%" + this.patron + "%'";

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
            this.lst_expediente = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Expediente_List exp = new Expediente_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        Double.parseDouble(vector_result[i][8]),
                        vector_result[i][9],
                        vector_result[i][10],
                        vector_result[i][11],
                        vector_result[i][12],
                        vector_result[i][13],
                        vector_result[i][14]);
                this.lst_expediente.add(exp);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar() {
        try {
            this.patron = "";
            this.lst_expediente = new ArrayList<>();
            this.sel_expediente = null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void mostrar_expediente() {
        try {
            this.deudor = this.sel_expediente.getId_deudor();
            if (this.deudor != null) {
                this.redender_exp_busqueda = false;
                this.redender_exp_deudor = true;
            
                RequestContext.getCurrentInstance().execute("PF('VTabWiew1').select(0);");
                RequestContext.getCurrentInstance().execute("PF('VTabWiew2').select(0);");
                // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Entro al metodo de carga."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un deudor de la tabla."));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void onTabChange_1(TabChangeEvent event) {
        try {
            if (event.getTab().getTitle().equals("Caso")) {
                this.CargarTabExpediente();
            }
            if (event.getTab().getTitle().equals("Extrajudicial")) {
                this.CargarTabExpediente();
            }
            if (event.getTab().getTitle().equals("Judicial")) {
                this.CargarTabExpediente();
            }
            if (event.getTab().getTitle().equals("Pagos")) {
                this.CargarTabPagos();
            }
            if (event.getTab().getTitle().equals("Recordatorios")) {
                this.CargarTabRecordatorios();
            }
            if (event.getTab().getTitle().equals("Juicio")) {
                this.CargarTabJuicio();
            }
            if (event.getTab().getTitle().equals("Descuentos")) {
                this.CargarTabDescuentos();
            }
            if (event.getTab().getTitle().equals("Aumentos")) {
                this.CargarTabAumentos();
            }
            if (event.getTab().getTitle().equals("Fiadores")) {
                this.CargarTabFiadores();
            }
            if (event.getTab().getTitle().equals("Referencias")) {
                this.CargarTabReferencias();
            }
            if (event.getTab().getTitle().equals("Digitalizados")) {
                this.CargarTabDigitalizados();
            }
            if (event.getTab().getTitle().equals("Convenios")) {
                this.CargarTabConvenios();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void onTabChange_2(TabChangeEvent event) {
        try {
            if(event.getTab().getTitle().equals("Cobros")) {
                this.CargarTabCobros();
            }
            if(event.getTab().getTitle().equals("Admón")) {
                this.CargarTabAdmon();
            }
            if(event.getTab().getTitle().equals("Jurídico")) {
                this.CargarTabJuridico();
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabExpediente() {
        try {
            //DATOS DEL DEUDOR
            String cadenasql = "select "
                    + "d.nombre, "               // rs.getObject(0);
                    + "a.nombre, "               // rs.getObject(1);
                    + "d.caso, "                 // rs.getObject(2);
                    + "d.no_cuenta, "            // rs.getObject(3);
                    + "d.no_cuenta_otro, "       // rs.getObject(4);
                    + "d.garantia, "             // rs.getObject(5);
                    + "d.cargado, "              // rs.getObject(6);
                    + "d.anticipo, "             // rs.getObject(7);
                    + "d.fecha_recepcion, "      // rs.getObject(8);
                    + "d.monto_inicial, "        // rs.getObject(9);
                    + "d.moneda, "               // rs.getObject(10);
                    + "u.nombre, "               // rs.getObject(11);
                    + "d.pdf, "                  // rs.getObject(12);
                    + "d.inv, "                  // rs.getObject(13);
                    + "d.maycom, "               // rs.getObject(14);
                    + "d.nits, "                 // rs.getObject(15);
                    + "d.saldo, "                // rs.getObject(16);
                    + "d.fecha_proximo_pago, "   // rs.getObject(17);
                    + "d.cuota_convenio, "       // rs.getObject(18);
                    + "d.telefono_casa, "        // rs.getObject(19);
                    + "d.telefono_celular, "     // rs.getObject(20);
                    + "d.correo_electronico, "   // rs.getObject(21);
                    + "d.lugar_trabajo, "        // rs.getObject(22);
                    + "d.direccion_trabajo, "    // rs.getObject(23);
                    + "d.telefono_trabajo, "     // rs.getObject(24);
                    + "d.dpi, "                  // rs.getObject(25);
                    + "d.nit, "                  // rs.getObject(26);
                    + "d.intencion_pago, "       // rs.getObject(27);
                    + "d.sestado_extra, "        // rs.getObject(28);
                    + "d.estatus_extra, "        // rs.getObject(29);
                    + "d.convenio_pactado, "     // rs.getObject(30);
                    + "d.descripcion, "          // rs.getObject(31);
                    + "d.direccion, "            // rs.getObject(32);
                    + "d.opcion_proximo_pago, "  // rs.getObject(33);
                    + "d.sestado, "              // rs.getObject(34);
                    + "d.estatus, "              // rs.getObject(35);
                    + "d.situacion_caso, "       // rs.getObject(36);
                    + "j.procuracion, "          // rs.getObject(37);
                    + "j.razon_notificacion, "   // rs.getObject(38);
                    + "j.procurador, "           // rs.getObject(39);
                    + "j.fecha, "                // rs.getObject(40);
                    + "j.juzgado, "              // rs.getObject(41);
                    + "j.no_juicio, "            // rs.getObject(42);
                    + "j.notificador, "          // rs.getObject(43);
                    + "j.abogado_deudor, "       // rs.getObject(44);
                    + "j.sumario, "              // rs.getObject(45);
                    + "j.memorial, "             // rs.getObject(46);
                    + "j.deudor_notificado, "    // rs.getObject(47);
                    + "j.fecha_notificacion, "   // rs.getObject(48);
                    + "j.monto, "                // rs.getObject(49);
                    + "(select ifnull(sum(p.monto),0.00) from pago p where p.deudor=" + this.deudor + ") pagos, "                         // rs.getObject(50);
                    + "(select ifnull(sum(a.monto),0.00) from aumento a where a.deudor=" + this.deudor + ") aumento, "                    // rs.getObject(41);
                    + "(select ifnull(sum(d.monto),0.00) from descuento d where d.deudor=" + this.deudor + ") descuentos, "               // rs.getObject(52);
                    + "(select ifnull(max(p.fecha),date('1900-01-01')) from pago p where p.deudor=" + this.deudor + ") fecha_u_pago, "    // rs.getObject(53);
                    + "ifnull((select ifnull(p.monto,0.00) from pago p where p.deudor=" + this.deudor + " and p.fecha=(select ifnull(max(p.fecha),date('1900-01-01')) from pago p where p.deudor=" + this.deudor + ")),0.00) monto_u_pago, "                               // rs.getObject(54);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, "                                      // rs.getObject(55);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial "     // rs.getObject(56);
                    + "from "
                    + "deudor d "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "where "
                    + "d.deudor=" + this.deudor;
            
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
                this.nombre_deudor = vector_result[i][0];
                this.actor = vector_result[i][1];
                this.caso = Integer.parseInt(vector_result[i][2]);
                this.no_cuenta = vector_result[i][3];
                this.otro_no_cuenta = vector_result[i][4];
                this.garantia = Integer.parseInt(vector_result[i][5]);
                this.cargado = vector_result[i][6];
                this.anticipo = vector_result[i][7];
                this.fecha_recepcion = formatDate.parse(vector_result[i][8]);
                this.monto_inicial = Double.parseDouble(vector_result[i][9]);
                this.Moneda = vector_result[i][10];
                this.gestor = vector_result[i][11];
                this.inv_pdf = vector_result[i][12].equals("SI");
                this.inv_inv = vector_result[i][13].equals("SI");
                this.inv_may = vector_result[i][14].equals("SI");
                this.inv_nit = vector_result[i][15].equals("SI");
                this.saldo = Double.parseDouble(vector_result[i][16]);
                this.fecha_p_pago = formatDate.parse(vector_result[i][17]);
                this.monto_p_pago = Double.parseDouble(vector_result[i][18]);
                this.telefono_casa = vector_result[i][19];
                this.telefono_celular = vector_result[i][20];
                this.correo_electronico = vector_result[i][21];
                this.lugar_trabajo = vector_result[i][22];
                this.contacto_trabajo = vector_result[i][23];
                this.telefono_trabajo = vector_result[i][24];
                this.dpi = vector_result[i][25];
                this.nit = vector_result[i][26];
                this.intencion_pago = Integer.parseInt(vector_result[i][27]);
                this.estado_extrajudicial = Integer.parseInt(vector_result[i][28]);
                this.cambio_estado_extrajudicial();
                this.status_extrajudicial = Integer.parseInt(vector_result[i][29]);
                this.convenio_pactado = vector_result[i][30];
                this.notas = vector_result[i][31];
                this.direccion = vector_result[i][32];
                this.opcion_proximo_pago = vector_result[i][33].equals("SI");
                this.estado_judicial = Integer.parseInt(vector_result[i][34]);
                this.cambio_estado_judicial();
                this.status_judicial = Integer.parseInt(vector_result[i][35]);
                this.situacion_caso = vector_result[i][36];
                this.procuracion = vector_result[i][37];
                this.razon_notificacion = vector_result[i][38];
                this.procurador = Integer.parseInt(vector_result[i][39]);
                this.fecha_juicio = formatDate.parse(vector_result[i][40]);
                this.juzgado = Integer.parseInt(vector_result[i][41]);
                this.no_juicio = vector_result[i][42];
                this.notificador = Integer.parseInt(vector_result[i][43]);
                this.abogado_deudor = vector_result[i][44];
                this.sumario = vector_result[i][45];
                this.memorial = formatDate.parse(vector_result[i][46]);
                this.notificado = vector_result[i][47];
                this.fecha_notif = formatDate.parse(vector_result[i][48]);
                this.monto_demanda = Double.parseDouble(vector_result[i][49]);
                this.pagos = Double.parseDouble(vector_result[i][50]);
                this.aumentos = Double.parseDouble(vector_result[i][51]);
                this.descuentos = Double.parseDouble(vector_result[i][52]);
                this.fecha_u_pago = formatDate.parse(vector_result[i][53]);
                this.monto_u_pago = Double.parseDouble(vector_result[i][54]);
                this.com_judicial = vector_result[i][55];
                this.com_extrajudicial = vector_result[i][56];
            }

            Driver drive = new Driver();
            this.lst_fiador = drive.lista_SelectItem_simple("select f.fiador, f.nombre from fiador f where f.deudor=" + this.deudor, this.ambiente);
            if (this.lst_fiador.isEmpty()) {
                this.lst_fiador.add(new SelectItem(0, "SIN FIADOR"));
                this.fiador = 0;
            } else {
                this.fiador = Integer.parseInt(this.lst_fiador.get(0).getValue().toString());
            }
            //this.estilo = "font-size:12px; font-weight:bold; color:blue;";
            //this.titulo_deudor = "CASO: " + this.caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: " + this.hora + ":" + this.minuto + ": 00:00:00";
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabCobros() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_cobros indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "d.fecha fecha, "
                    + "d.hora hora, "
                    + "u.nombre usuario, "
                    + "concat(c.codigo,'|',c.nombre) codigo, "
                    + "d.contacto contacto, "
                    + "d.descripcion observacion "
                    + "from "
                    + "deudor_historial_cobros d "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join deudor de on (d.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "d.deudor=" + this.deudor + " "
                    + "order by "
                    + "d.fecha desc, "
                    + "d.hora desc";
            
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
            this.lst_cobros = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Cobro_List nod = new Cobro_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        vector_result[i][8]);
                this.lst_cobros.add(nod);
            }
            
            filas = filas - 1;
            this.lb_numero_gestiones_cobros = "No. de gestiones: " + filas;
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabAdmon() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_administrativo indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "d.fecha fecha, "
                    + "d.hora hora, "
                    + "u.nombre usuario, "
                    + "concat(c.codigo,'|',c.nombre) codigo, "
                    + "d.descripcion observacion "
                    + "from "
                    + "deudor_historial_administrativo d "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join deudor de on (d.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "d.deudor=" + this.deudor + " "
                    + "order "
                    + "by d.fecha desc, "
                    + "d.hora desc";
            
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
            this.lst_admom = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Administrativo_List nod = new Administrativo_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7]);
                this.lst_admom.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void CargarTabJuridico() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_juridico indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "d.fecha fecha, "
                    + "d.hora hora, "
                    + "u.nombre usuario,"
                    + "concat(c.codigo,'|',c.nombre) codigo, "
                    + "d.descripcion observacion "
                    + "from "
                    + "deudor_historial_juridico d "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join deudor de on (d.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "d.deudor=" + this.deudor + " "
                    + "order by "
                    + "d.fecha desc, "
                    + "d.hora desc";
            
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
            this.lst_juridico = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Juridico_List nod = new Juridico_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7]);
                this.lst_juridico.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabPagos() {
        try {
            String cadenasql = "select "
                    + "p.pago indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "b.nombre banco, "
                    + "p.fecha fecha, "
                    + "p.monto monto, "
                    + "p.no_boleta no_boleta, "
                    + "p.fecha_registro fecha_registro "
                    + "from "
                    + "pago p "
                    + "left join banco b on (p.banco=b.banco) "
                    + "left join deudor de on (p.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "p.deudor=" + this.deudor + " "
                    + "order by "
                    + "p.fecha desc";
            
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
            this.lst_pagos = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Pago_List nod = new Pago_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        formatDate.parse(vector_result[i][4]),
                        Double.parseDouble(vector_result[i][5]),
                        vector_result[i][6],
                        formatDate.parse(vector_result[i][7]));
                this.lst_pagos.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabRecordatorios() {
        try {
            String cadenasql = "select "
                    + "p.promesa_pago indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "p.fecha_ingreso fecha_ingreso, "
                    + "p.fecha_pago fecha_pago, "
                    + "p.estado_promesa estado_promesa, "
                    + "p.monto monto "
                    + "from "
                    + "promesa_pago p "
                    + "left join deudor de on (p.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "p.deudor=" + this.deudor + " "
                    + "order by "
                    + "p.fecha_ingreso desc";
            
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
            this.lst_recordatorios = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Recordatorio_List nod = new Recordatorio_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        formatDate.parse(vector_result[i][4]),
                        vector_result[i][5],
                        Double.parseDouble(vector_result[i][6]));
                this.lst_recordatorios.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabJuicio() {
        try {
            String cadenasql = "select "
                    + "j.juicio inidice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "ju.nombre juzgado, "
                    + "j.fecha fecha, "
                    + "j.no_juicio no_juicio, "
                    + "j.monto monto "
                    + "from "
                    + "juicio j "
                    + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                    + "left join deudor de on (j.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "j.deudor=" + this.deudor + " "
                    + "order by "
                    + "j.fecha";
            
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
            this.lst_juicios = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Juicio_List nod = new Juicio_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        formatDate.parse(vector_result[i][4]),
                        vector_result[i][5],
                        Double.parseDouble(vector_result[i][6]));
                this.lst_juicios.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabDescuentos() {
        try {
            String cadenasql = "select "
                    + "d.descuento indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "t.nombre tipo_descuento, "
                    + "d.fecha fecha, "
                    + "d.monto monto "
                    + "from "
                    + "descuento d "
                    + "left join tipo_descuento t on (d.tipo_descuento=t.tipo_descuento) "
                    + "left join deudor de on (d.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "d.deudor=" + this.deudor + " "
                    + "order by "
                    + "d.fecha";
            
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
            this.lst_descuentos = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Descuento_List nod = new Descuento_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        formatDate.parse(vector_result[i][4]),
                        Double.parseDouble(vector_result[i][5]));
                this.lst_descuentos.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabAumentos() {
        try {
            String cadenasql = "select "
                    + "au.aumento indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "t.nombre tipo_descuento, "
                    + "au.fecha fecha, "
                    + "au.monto monto "
                    + "from "
                    + "aumento au "
                    + "left join tipo_aumento t on (au.tipo_aumento=t.tipo_aumento) "
                    + "left join deudor de on (au.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "au.deudor=" + this.deudor + " "
                    + "order by "
                    + "au.fecha";
            
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
            this.lst_aumentos = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Aumento_List nod = new Aumento_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        formatDate.parse(vector_result[i][4]),
                        Double.parseDouble(vector_result[i][5]));
                this.lst_aumentos.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabFiadores() {
        try {
            String cadenasql = "select "
                    + "f.fiador indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "f.dpi dpi, "
                    + "f.nit nit, "
                    + "f.nombre nombre, "
                    + "f.telefono telefono, "
                    + "f.direccion direccion, "
                    + "f.zona zona, "
                    + "f.correo_electronico correo_electronico "
                    + "from "
                    + "fiador f "
                    + "left join deudor de on (f.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "f.deudor=" + this.deudor;
            
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
            this.lst_fiadores = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Fiador_List nod = new Fiador_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        vector_result[i][8],
                        vector_result[i][9]);
                this.lst_fiadores.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabReferencias() {
        try {
            String cadenasql = "select "
                    + "r.referencia indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "r.dpi dpi, "
                    + "r.nit nit, "
                    + "r.nombre nombre, "
                    + "r.telefono telefono, "
                    + "r.direccion direccion, "
                    + "r.zona zona, "
                    + "r.correo_electronico correo_electronico "
                    + "from "
                    + "referencia r "
                    + "left join deudor de on (r.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "r.deudor=" + this.deudor;
            
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
            this.lst_referencias = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Referencia_List nod = new Referencia_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        vector_result[i][8],
                        vector_result[i][9]);
                this.lst_referencias.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabDigitalizados() {
        try {
            this.lst_digitalizaciones = new ArrayList<>();
            
            String OS = System.getProperty("os.name").toLowerCase();
            Driver driver = new Driver();
            String nombre_actor = driver.getString("select a.nombre from deudor d left join actor a on (d.actor=a.actor) where d.deudor=" + this.deudor, this.ambiente);
            String nombre_deudor_d = driver.getString("select d.nombre from deudor d where d.deudor=" + this.deudor, this.ambiente);
            if(OS.toUpperCase().contains("WIN")) {
                String Directorio = driver.dar_path_digitalizados(1, this.ambiente) + "\\" + driver.dar_path_actor(this.deudor, this.ambiente) + "\\" + driver.dar_caso_deudor(this.deudor, this.ambiente);
                File f = new File(Directorio);
                if (f.exists()) {
                    File[] ficheros = f.listFiles();
                    for (Integer i = 0; i < ficheros.length; i++) {
                        Digitalizacion_List nod = new Digitalizacion_List(
                                i,
                                nombre_actor,
                                nombre_deudor_d,
                                ficheros[i].getName(),
                                ficheros[i].getPath());
                        this.lst_digitalizaciones.add(nod);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Directorio no existe."));
                }
            } else {
                String Directorio = driver.dar_path_digitalizados(2, this.ambiente) + "/" + driver.dar_path_actor(this.deudor, this.ambiente) + "/" + driver.dar_caso_deudor(this.deudor, this.ambiente) + "/";
                System.out.println(Directorio);
                SmbFile f = new SmbFile(Directorio);
                if (f.exists()) {
                    SmbFile[] ficheros = f.listFiles();
                    for (Integer i = 0; i < ficheros.length; i++) {
                        Digitalizacion_List nod = new Digitalizacion_List(
                                i,
                                nombre_actor,
                                nombre_deudor_d,
                                ficheros[i].getName(),
                                ficheros[i].getPath());
                        this.lst_digitalizaciones.add(nod);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Directorio no existe."));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void CargarTabConvenios() {
        try {
            String cadenasql = "select "
                    + "c.convenio indice, "
                    + "a.nombre actor, "
                    + "de.nombre nombre_deudor, "
                    + "c.fecha_creacion fecha_creacion, "
                    + "c.estado estado, "
                    + "c.fecha_pago_inicial fecha_pago, "
                    + "c.total_pagar total_deuda, "
                    + "c.numero_cuotas no_cuotas, "
                    + "c.frecuencia frecuencia, "
                    + "c.monto_cuota cuota "
                    + "from "
                    + "convenio c "
                    + "left join deudor de on (c.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "c.deudor=" + this.deudor + " "
                    + "order by "
                    + "c.fecha_creacion";
            
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
            this.lst_convenios = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Convenio_List nod = new Convenio_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        formatDate.parse(vector_result[i][3]),
                        vector_result[i][4],
                        formatDate.parse(vector_result[i][5]),
                        Double.parseDouble(vector_result[i][6]),
                        Integer.parseInt(vector_result[i][7]),
                        vector_result[i][8],
                        Double.parseDouble(vector_result[i][9]));
                this.lst_convenios.add(nod);
            }
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar_expediente_caso() {
        try {
            String inv_pdf_s;
            if (this.inv_pdf) {
                inv_pdf_s = "SI";
            } else {
                inv_pdf_s = "NO";
            }
            String inv_inv_s;
            if (this.inv_inv) {
                inv_inv_s = "SI";
            } else {
                inv_inv_s = "NO";
            }
            String inv_may_s;
            if (this.inv_may) {
                inv_may_s = "SI";
            } else {
                inv_may_s = "NO";
            }
            String inv_nit_s;
            if (this.inv_nit) {
                inv_nit_s = "SI";
            } else {
                inv_nit_s = "NO";
            }
            
            Servicio servicio = new Servicio();
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.guardarExpedienteCaso(id_usuario, this.deudor, this.garantia, this.cargado, this.anticipo, this.saldo, inv_pdf_s, inv_inv_s, inv_may_s, inv_nit_s, this.ambiente);
            this.CargarTabExpediente();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar_expediente_extrajudicial() {
        try {
            Servicio servicio = new Servicio();
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.guardarExpedienteExtrajudicial(id_usuario, this.deudor, this.estado_extrajudicial, this.status_extrajudicial, this.telefono_casa, this.telefono_celular, this.correo_electronico, this.lugar_trabajo, this.contacto_trabajo, this.telefono_trabajo,this.dpi, this.nit, this.intencion_pago,this.direccion, this.notas, 1, this.ambiente);
            this.CargarTabExpediente();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar_expediente_judicial() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_juicio.getYear() + 1900, this.fecha_juicio.getMonth(), this.fecha_juicio.getDate());
            XMLGregorianCalendar gre_fecha_juicio = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);
            
            GregorianCalendar gregory2 = new GregorianCalendar();
            gregory2.set(this.memorial.getYear() + 1900, this.memorial.getMonth(), this.memorial.getDate());
            XMLGregorianCalendar gre_memorial = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);
            
            GregorianCalendar gregory3 = new GregorianCalendar();
            gregory3.set(this.fecha_notif.getYear() + 1900, this.fecha_notif.getMonth(), this.fecha_notif.getDate());
            XMLGregorianCalendar gre_fecha_notif = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);
            
            Servicio servicio = new Servicio();
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.guardarExpedienteJudicial(id_usuario, this.deudor, this.estado_judicial, this.status_judicial, this.procurador, gre_fecha_juicio, this.juzgado, this.no_juicio, this.notificador, this.abogado_deudor, this.sumario, gre_memorial, this.notificado, gre_fecha_notif, this.monto_demanda, this.procuracion, this.situacion_caso, this.razon_notificacion, this.ambiente);
            this.CargarTabExpediente();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch(Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambio_estado_extrajudicial() {
        try {
            String cadenasql = "select "
                    + "distinct ee.estatus_extra, ee.nombre "
                    + "from "
                    + "estado_status_extrajudicial ese "
                    + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                    + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                    + "where "
                    + "se.sestado_extra = " + this.estado_extrajudicial + " "
                    + "order by "
                    + "ee.nombre";
            Driver driver = new Driver();
            this.lst_estatus_extrajudicial = driver.lista_SelectItem_simple(cadenasql, this.ambiente);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambio_estado_judicial() {
        try {
            String cadenasql = "select "
                    + "distinct e.estatus, e.nombre "
                    + "from "
                    + "estado_status_judicial esj "
                    + "left join estatus e on (esj.estatus=e.estatus) "
                    + "left join sestado s on (esj.sestado=s.sestado) "
                    + "where "
                    + "s.sestado=" + this.estado_judicial + " "
                    + "order by "
                    + "e.nombre";
            Driver driver = new Driver();
            this.lst_estatus_judicial = driver.lista_SelectItem_simple(cadenasql, this.ambiente);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    // ====================================> GETTERS & SETTERS <====================================

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Boolean getRedender_exp_busqueda() {
        return redender_exp_busqueda;
    }

    public void setRedender_exp_busqueda(Boolean redender_exp_busqueda) {
        this.redender_exp_busqueda = redender_exp_busqueda;
    }

    public Boolean getRedender_exp_deudor() {
        return redender_exp_deudor;
    }

    public void setRedender_exp_deudor(Boolean redender_exp_deudor) {
        this.redender_exp_deudor = redender_exp_deudor;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public List<Expediente_List> getLst_expediente() {
        return lst_expediente;
    }

    public void setLst_expediente(List<Expediente_List> lst_expediente) {
        this.lst_expediente = lst_expediente;
    }

    public Expediente_List getSel_expediente() {
        return sel_expediente;
    }

    public void setSel_expediente(Expediente_List sel_expediente) {
        this.sel_expediente = sel_expediente;
    }

    public String getNombre_deudor() {
        return nombre_deudor;
    }

    public void setNombre_deudor(String nombre_deudor) {
        this.nombre_deudor = nombre_deudor;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Integer getCaso() {
        return caso;
    }

    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public String getOtro_no_cuenta() {
        return otro_no_cuenta;
    }

    public void setOtro_no_cuenta(String otro_no_cuenta) {
        this.otro_no_cuenta = otro_no_cuenta;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public Integer getFiador() {
        return fiador;
    }

    public void setFiador(Integer fiador) {
        this.fiador = fiador;
    }

    public String getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public boolean isInv_pdf() {
        return inv_pdf;
    }

    public void setInv_pdf(boolean inv_pdf) {
        this.inv_pdf = inv_pdf;
    }

    public boolean isInv_inv() {
        return inv_inv;
    }

    public void setInv_inv(boolean inv_inv) {
        this.inv_inv = inv_inv;
    }

    public boolean isInv_may() {
        return inv_may;
    }

    public void setInv_may(boolean inv_may) {
        this.inv_may = inv_may;
    }

    public boolean isInv_nit() {
        return inv_nit;
    }

    public void setInv_nit(boolean inv_nit) {
        this.inv_nit = inv_nit;
    }

    public Double getPagos() {
        return pagos;
    }

    public void setPagos(Double pagos) {
        this.pagos = pagos;
    }

    public Double getAumentos() {
        return aumentos;
    }

    public void setAumentos(Double aumentos) {
        this.aumentos = aumentos;
    }

    public Double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_u_pago() {
        return fecha_u_pago;
    }

    public void setFecha_u_pago(Date fecha_u_pago) {
        this.fecha_u_pago = fecha_u_pago;
    }

    public Double getMonto_u_pago() {
        return monto_u_pago;
    }

    public void setMonto_u_pago(Double monto_u_pago) {
        this.monto_u_pago = monto_u_pago;
    }

    public Boolean getOpcion_proximo_pago() {
        return opcion_proximo_pago;
    }

    public void setOpcion_proximo_pago(Boolean opcion_proximo_pago) {
        this.opcion_proximo_pago = opcion_proximo_pago;
    }

    public Date getFecha_p_pago() {
        return fecha_p_pago;
    }

    public void setFecha_p_pago(Date fecha_p_pago) {
        this.fecha_p_pago = fecha_p_pago;
    }

    public Double getMonto_p_pago() {
        return monto_p_pago;
    }

    public void setMonto_p_pago(Double monto_p_pago) {
        this.monto_p_pago = monto_p_pago;
    }

    public List<SelectItem> getLst_garantia() {
        return lst_garantia;
    }

    public void setLst_garantia(List<SelectItem> lst_garantia) {
        this.lst_garantia = lst_garantia;
    }

    public List<SelectItem> getLst_fiador() {
        return lst_fiador;
    }

    public void setLst_fiador(List<SelectItem> lst_fiador) {
        this.lst_fiador = lst_fiador;
    }

    public String getTelefono_casa() {
        return telefono_casa;
    }

    public void setTelefono_casa(String telefono_casa) {
        this.telefono_casa = telefono_casa;
    }

    public String getTelefono_celular() {
        return telefono_celular;
    }

    public void setTelefono_celular(String telefono_celular) {
        this.telefono_celular = telefono_celular;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getLugar_trabajo() {
        return lugar_trabajo;
    }

    public void setLugar_trabajo(String lugar_trabajo) {
        this.lugar_trabajo = lugar_trabajo;
    }

    public String getContacto_trabajo() {
        return contacto_trabajo;
    }

    public void setContacto_trabajo(String contacto_trabajo) {
        this.contacto_trabajo = contacto_trabajo;
    }

    public String getTelefono_trabajo() {
        return telefono_trabajo;
    }

    public void setTelefono_trabajo(String telefono_trabajo) {
        this.telefono_trabajo = telefono_trabajo;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Integer getIntencion_pago() {
        return intencion_pago;
    }

    public void setIntencion_pago(Integer intencion_pago) {
        this.intencion_pago = intencion_pago;
    }

    public Integer getEstado_extrajudicial() {
        return estado_extrajudicial;
    }

    public void setEstado_extrajudicial(Integer estado_extrajudicial) {
        this.estado_extrajudicial = estado_extrajudicial;
    }

    public Integer getStatus_extrajudicial() {
        return status_extrajudicial;
    }

    public void setStatus_extrajudicial(Integer status_extrajudicial) {
        this.status_extrajudicial = status_extrajudicial;
    }

    public String getConvenio_pactado() {
        return convenio_pactado;
    }

    public void setConvenio_pactado(String convenio_pactado) {
        this.convenio_pactado = convenio_pactado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getCom_judicial() {
        return com_judicial;
    }

    public void setCom_judicial(String com_judicial) {
        this.com_judicial = com_judicial;
    }

    public Integer getEstado_judicial() {
        return estado_judicial;
    }

    public void setEstado_judicial(Integer estado_judicial) {
        this.estado_judicial = estado_judicial;
    }

    public Integer getStatus_judicial() {
        return status_judicial;
    }

    public void setStatus_judicial(Integer status_judicial) {
        this.status_judicial = status_judicial;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public String getRazon_notificacion() {
        return razon_notificacion;
    }

    public void setRazon_notificacion(String razon_notificacion) {
        this.razon_notificacion = razon_notificacion;
    }

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public Date getFecha_juicio() {
        return fecha_juicio;
    }

    public void setFecha_juicio(Date fecha_juicio) {
        this.fecha_juicio = fecha_juicio;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public String getAbogado_deudor() {
        return abogado_deudor;
    }

    public void setAbogado_deudor(String abogado_deudor) {
        this.abogado_deudor = abogado_deudor;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Date getMemorial() {
        return memorial;
    }

    public void setMemorial(Date memorial) {
        this.memorial = memorial;
    }

    public String getNotificado() {
        return notificado;
    }

    public void setNotificado(String notificado) {
        this.notificado = notificado;
    }

    public Date getFecha_notif() {
        return fecha_notif;
    }

    public void setFecha_notif(Date fecha_notif) {
        this.fecha_notif = fecha_notif;
    }

    public Double getMonto_demanda() {
        return monto_demanda;
    }

    public void setMonto_demanda(Double monto_demanda) {
        this.monto_demanda = monto_demanda;
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

    public List<SelectItem> getLst_procurador() {
        return lst_procurador;
    }

    public void setLst_procurador(List<SelectItem> lst_procurador) {
        this.lst_procurador = lst_procurador;
    }

    public List<SelectItem> getLst_juzgado() {
        return lst_juzgado;
    }

    public void setLst_juzgado(List<SelectItem> lst_juzgado) {
        this.lst_juzgado = lst_juzgado;
    }

    public String getCom_extrajudicial() {
        return com_extrajudicial;
    }

    public void setCom_extrajudicial(String com_extrajudicial) {
        this.com_extrajudicial = com_extrajudicial;
    }

    public List<Cobro_List> getLst_cobros() {
        return lst_cobros;
    }

    public void setLst_cobros(List<Cobro_List> lst_cobros) {
        this.lst_cobros = lst_cobros;
    }

    public Cobro_List getCobro_sel() {
        return cobro_sel;
    }

    public void setCobro_sel(Cobro_List cobro_sel) {
        this.cobro_sel = cobro_sel;
    }

    public String getLb_numero_gestiones_cobros() {
        return lb_numero_gestiones_cobros;
    }

    public void setLb_numero_gestiones_cobros(String lb_numero_gestiones_cobros) {
        this.lb_numero_gestiones_cobros = lb_numero_gestiones_cobros;
    }

    public List<Administrativo_List> getLst_admom() {
        return lst_admom;
    }

    public void setLst_admom(List<Administrativo_List> lst_admom) {
        this.lst_admom = lst_admom;
    }

    public Administrativo_List getAdmon_sel() {
        return admon_sel;
    }

    public void setAdmon_sel(Administrativo_List admon_sel) {
        this.admon_sel = admon_sel;
    }

    public List<Juridico_List> getLst_juridico() {
        return lst_juridico;
    }

    public void setLst_juridico(List<Juridico_List> lst_juridico) {
        this.lst_juridico = lst_juridico;
    }

    public Juridico_List getJuridico_sel() {
        return juridico_sel;
    }

    public void setJuridico_sel(Juridico_List juridico_sel) {
        this.juridico_sel = juridico_sel;
    }

    public List<Pago_List> getLst_pagos() {
        return lst_pagos;
    }

    public void setLst_pagos(List<Pago_List> lst_pagos) {
        this.lst_pagos = lst_pagos;
    }

    public Pago_List getPago_sel() {
        return pago_sel;
    }

    public void setPago_sel(Pago_List pago_sel) {
        this.pago_sel = pago_sel;
    }

    public List<Recordatorio_List> getLst_recordatorios() {
        return lst_recordatorios;
    }

    public void setLst_recordatorios(List<Recordatorio_List> lst_recordatorios) {
        this.lst_recordatorios = lst_recordatorios;
    }

    public Recordatorio_List getRecordatorio_sel() {
        return recordatorio_sel;
    }

    public void setRecordatorio_sel(Recordatorio_List recordatorio_sel) {
        this.recordatorio_sel = recordatorio_sel;
    }

    public List<Juicio_List> getLst_juicios() {
        return lst_juicios;
    }

    public void setLst_juicios(List<Juicio_List> lst_juicios) {
        this.lst_juicios = lst_juicios;
    }

    public Juicio_List getJuicio_sel() {
        return juicio_sel;
    }

    public void setJuicio_sel(Juicio_List juicio_sel) {
        this.juicio_sel = juicio_sel;
    }

    public List<Descuento_List> getLst_descuentos() {
        return lst_descuentos;
    }

    public void setLst_descuentos(List<Descuento_List> lst_descuentos) {
        this.lst_descuentos = lst_descuentos;
    }

    public Descuento_List getDescuento_sel() {
        return descuento_sel;
    }

    public void setDescuento_sel(Descuento_List descuento_sel) {
        this.descuento_sel = descuento_sel;
    }

    public List<Aumento_List> getLst_aumentos() {
        return lst_aumentos;
    }

    public void setLst_aumentos(List<Aumento_List> lst_aumentos) {
        this.lst_aumentos = lst_aumentos;
    }

    public Aumento_List getAumento_sel() {
        return aumento_sel;
    }

    public void setAumento_sel(Aumento_List aumento_sel) {
        this.aumento_sel = aumento_sel;
    }

    public List<Fiador_List> getLst_fiadores() {
        return lst_fiadores;
    }

    public void setLst_fiadores(List<Fiador_List> lst_fiadores) {
        this.lst_fiadores = lst_fiadores;
    }

    public Fiador_List getFiador_sel() {
        return fiador_sel;
    }

    public void setFiador_sel(Fiador_List fiador_sel) {
        this.fiador_sel = fiador_sel;
    }

    public List<Referencia_List> getLst_referencias() {
        return lst_referencias;
    }

    public void setLst_referencias(List<Referencia_List> lst_referencias) {
        this.lst_referencias = lst_referencias;
    }

    public Referencia_List getReferencia_sel() {
        return referencia_sel;
    }

    public void setReferencia_sel(Referencia_List referencia_sel) {
        this.referencia_sel = referencia_sel;
    }

    public List<Digitalizacion_List> getLst_digitalizaciones() {
        return lst_digitalizaciones;
    }

    public void setLst_digitalizaciones(List<Digitalizacion_List> lst_digitalizaciones) {
        this.lst_digitalizaciones = lst_digitalizaciones;
    }

    public Digitalizacion_List getDigitalizacion_sel() {
        return digitalizacion_sel;
    }

    public void setDigitalizacion_sel(Digitalizacion_List digitalizacion_sel) {
        this.digitalizacion_sel = digitalizacion_sel;
    }

    public List<Convenio_List> getLst_convenios() {
        return lst_convenios;
    }

    public void setLst_convenios(List<Convenio_List> lst_convenios) {
        this.lst_convenios = lst_convenios;
    }

    public Convenio_List getConvenio_sel() {
        return convenio_sel;
    }

    public void setConvenio_sel(Convenio_List convenio_sel) {
        this.convenio_sel = convenio_sel;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
