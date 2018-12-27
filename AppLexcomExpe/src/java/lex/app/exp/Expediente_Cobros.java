package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Cobros")
@ViewScoped
public class Expediente_Cobros implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    
    private Integer deudor;

    private List<Cobro_List> lst_cobros;
    private Cobro_List cobro_sel;
    private String lb_numero_gestiones_cobros;
    
    private Integer estado_extrajudicial;
    private Integer status_extrajudicial;
    private Integer estado_judicial;
    private Integer status_judicial;
    private Integer intencion_pago;
    
    private Integer tipo_gestion;
    private String gestion_contacto;
    private Integer codigo_resultado;
    private String descripcion_gestion;
    
    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;
    
    private List<SelectItem> lst_estado_extrajudicial;
    private List<SelectItem> lst_estatus_extrajudicial;
    private List<SelectItem> lst_estado_judicial;
    private List<SelectItem> lst_estatus_judicial;
    private List<SelectItem> lst_intension_pago;
    private List<SelectItem> lst_tipo_gestion;
    private List<SelectItem> lst_codigo_resultado;
    
    private boolean somestadoextrajudicial;
    private boolean somstatusextrajudicial;
    private boolean somintensionpago;
    private boolean somestadojudicial;
    private boolean somstatusjudicial;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            System.out.println("USUARIO : => LexcomExpediente-Expediente_Caso(init): " + this.usuario);
            System.out.println("AMBIENTE: => LexcomExpediente-Expediente_Caso(init): " + this.ambiente);
            
            Driver drive = new Driver();
            this.lst_cobros = new ArrayList<>();
            this.cobro_sel = null;
            this.lb_numero_gestiones_cobros = "";
            this.gestion_contacto = "SI";
            this.codigo_resultado = 0;
            
            String lista_estado_extra_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extra_sql, this.ambiente);
            if(!this.lst_estado_extrajudicial.isEmpty()) {
                this.estado_extrajudicial = Integer.parseInt(this.lst_estado_extrajudicial.get(0).getValue().toString());
                this.cambio_estado_extrajudicial();
            } else {
                this.estado_extrajudicial = 0;
                this.lst_estatus_extrajudicial = new ArrayList<>();
            }
            
            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE'";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            if(!this.lst_estado_judicial.isEmpty()) {
                this.estado_judicial = Integer.parseInt(this.lst_estado_judicial.get(0).getValue().toString());
                this.cambio_estado_judicial();
            } else {
                this.estado_judicial = 0;
                this.lst_estatus_judicial = new ArrayList<>();
            }
            
            String lista_intencion_pago_sql = "select i.intencion_pago, i.nombre from intencion_pago i where i.estado='VIGENTE'";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
            if(!this.lst_intension_pago.isEmpty()) {
                this.intencion_pago = Integer.parseInt(this.lst_intension_pago.get(0).getValue().toString());
            } else {
                this.intencion_pago = 0;
            }
            
            String lista_tipo_gestion_sql = "select distinct tct.tipo_codigo_contactabilidad, tct.nombre from tipo_codigo_codigo tcc left join tipo_codigo_contactabilidad tct on (tcc.tipo_codigo_contactabilidad=tct.tipo_codigo_contactabilidad) order by tct.nombre";
            this.lst_tipo_gestion = drive.lista_SelectItem_simple(lista_tipo_gestion_sql, this.ambiente);
            if(!this.lst_tipo_gestion.isEmpty()) {
                this.tipo_gestion = Integer.parseInt(this.lst_tipo_gestion.get(0).getValue().toString());
                this.cambio_gestion_contacto();
            } else {
                this.tipo_gestion = 0;
                this.lst_codigo_resultado = new ArrayList<>();
            }
            
            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void limpiar_expediente_cobros() {
        try {
            Driver drive = new Driver();
            this.lst_cobros = new ArrayList<>();
            this.cobro_sel = null;
            this.lb_numero_gestiones_cobros = "";
            this.gestion_contacto = "SI";
            this.codigo_resultado = 0;
            
            String lista_estado_extra_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extra_sql, this.ambiente);
            if(!this.lst_estado_extrajudicial.isEmpty()) {
                this.estado_extrajudicial = Integer.parseInt(this.lst_estado_extrajudicial.get(0).getValue().toString());
                this.cambio_estado_extrajudicial();
            } else {
                this.estado_extrajudicial = 0;
                this.lst_estatus_extrajudicial = new ArrayList<>();
            }
            
            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE'";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            if(!this.lst_estado_judicial.isEmpty()) {
                this.estado_judicial = Integer.parseInt(this.lst_estado_judicial.get(0).getValue().toString());
                this.cambio_estado_judicial();
            } else {
                this.estado_judicial = 0;
                this.lst_estatus_judicial = new ArrayList<>();
            }
            
            String lista_intencion_pago_sql = "select i.intencion_pago, i.nombre from intencion_pago i where i.estado='VIGENTE'";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
            if(!this.lst_intension_pago.isEmpty()) {
                this.intencion_pago = Integer.parseInt(this.lst_intension_pago.get(0).getValue().toString());
            } else {
                this.intencion_pago = 0;
            }
            
            String lista_tipo_gestion_sql = "select distinct tct.tipo_codigo_contactabilidad, tct.nombre from tipo_codigo_codigo tcc left join tipo_codigo_contactabilidad tct on (tcc.tipo_codigo_contactabilidad=tct.tipo_codigo_contactabilidad) order by tct.nombre";
            this.lst_tipo_gestion = drive.lista_SelectItem_simple(lista_tipo_gestion_sql, this.ambiente);
            if(!this.lst_tipo_gestion.isEmpty()) {
                this.tipo_gestion = Integer.parseInt(this.lst_tipo_gestion.get(0).getValue().toString());
                this.cambio_gestion_contacto();
            } else {
                this.tipo_gestion = 0;
                this.lst_codigo_resultado = new ArrayList<>();
            }
            
            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(limpiar_expediente_cobros): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Cobros(Integer deudor) {
        try {
            this.deudor = deudor;
            
            if (this.deudor != null) {
                String cadenasql = "select "
                        + "d.deudor_historial_cobros indice, "// rs.getObject(0)
                        + "d.fecha fecha, "// rs.getObject(1)
                        + "d.hora hora, "// rs.getObject(2)
                        + "u.nombre usuario, "// rs.getObject(3)
                        + "concat(c.codigo,'|',c.nombre) codigo, "// rs.getObject(4)
                        + "d.contacto contacto, "// rs.getObject(5)
                        + "d.descripcion observacion "// rs.getObject(6)
                        + "from "
                        + "deudor_historial_cobros d "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
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
                for (Integer i = 1; i < filas; i++) {
                    Cobro_List nod = new Cobro_List(
                            Integer.parseInt(vector_result[i][0]),
                            formatDate.parse(vector_result[i][1]),
                            vector_result[i][2],
                            vector_result[i][3],
                            vector_result[i][4],
                            vector_result[i][5],
                            vector_result[i][6]);
                    this.lst_cobros.add(nod);
                }

                filas = filas - 1;
                this.lb_numero_gestiones_cobros = "No. de gestiones: " + filas;

                cadenasql = "select "
                        + "d.sestado_extra, " // rs.getObject(0);
                        + "d.estatus_extra, " // rs.getObject(1);
                        + "d.sestado, " // rs.getObject(2);
                        + "d.estatus, " // rs.getObject(3);
                        + "d.intencion_pago, " // rs.getObject(4);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(5);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(6)
                        + "d.caso " // rs.getObject(7)
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
                    this.estado_extrajudicial = Integer.parseInt(vector_result[i][0]);
                    this.cambio_estado_extrajudicial();
                    this.status_extrajudicial = Integer.parseInt(vector_result[i][1]);
                    this.estado_judicial = Integer.parseInt(vector_result[i][2]);
                    this.cambio_estado_judicial();
                    this.status_judicial = Integer.parseInt(vector_result[i][3]);
                    this.intencion_pago = Integer.parseInt(vector_result[i][4]);
                    this.com_extrajudicial = vector_result[i][5];
                    this.com_judicial = vector_result[i][6];
                    caso = Integer.parseInt(vector_result[i][7]);
                }
                
                this.somestadoextrajudicial = true;
                this.somstatusextrajudicial = true;
                this.somintensionpago = true;
                this.somestadojudicial = true;
                this.somstatusjudicial = true;
                
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                String esAsistente = driver.getString("select u.asistente from usuario u where u.usuario=" + id_usuario, this.ambiente);
                String esGestor = driver.getString("select u.gestor from usuario u where u.usuario=" + id_usuario, this.ambiente);
                String esProcurador = driver.getString("select u.procurador from usuario u where u.usuario=" + id_usuario, this.ambiente);
                String esDigitador = driver.getString("select u.digitador from usuario u where u.usuario=" + id_usuario, this.ambiente);
                
                if(esAsistente.equals("SI")) {
                    this.somestadoextrajudicial = false;
                    this.somstatusextrajudicial = false;
                    this.somintensionpago = false;
                    this.somestadojudicial = false;
                    this.somstatusjudicial = false;
                } 
                if(esGestor.equals("SI")) {
                    this.somestadoextrajudicial = false;
                    this.somstatusextrajudicial = false;
                    this.somintensionpago = false;
                }
                if(esProcurador.equals("SI") || esDigitador.equals("SI")) {
                    this.somestadojudicial = false;
                    this.somstatusjudicial = false;
                }

                this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";
                
                RequestContext.getCurrentInstance().execute("PF('var_exp_cobros').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(Cargar_Expediente_Cobros): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void Actualizar_Expediente_Cobros() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_cobros indice, "// rs.getObject(0)
                    + "d.fecha fecha, "// rs.getObject(1)
                    + "d.hora hora, "// rs.getObject(2)
                    + "u.nombre usuario, "// rs.getObject(3)
                    + "concat(c.codigo,'|',c.nombre) codigo, "// rs.getObject(4)
                    + "d.contacto contacto, "// rs.getObject(5)
                    + "d.descripcion observacion "// rs.getObject(6)
                    + "from "
                    + "deudor_historial_cobros d "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
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
            for (Integer i = 1; i < filas; i++) {
                Cobro_List nod = new Cobro_List(
                        Integer.parseInt(vector_result[i][0]),
                        formatDate.parse(vector_result[i][1]),
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                this.lst_cobros.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_gestiones_cobros = "No. de gestiones: " + filas;

            cadenasql = "select "
                    + "d.sestado_extra, " // rs.getObject(0);
                    + "d.estatus_extra, " // rs.getObject(1);
                    + "d.sestado, " // rs.getObject(2);
                    + "d.estatus, " // rs.getObject(3);
                    + "d.intencion_pago, " // rs.getObject(4);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(5);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(6)
                    + "d.caso " // rs.getObject(7)
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
                this.estado_extrajudicial = Integer.parseInt(vector_result[i][0]);
                this.cambio_estado_extrajudicial();
                this.status_extrajudicial = Integer.parseInt(vector_result[i][1]);
                this.estado_judicial = Integer.parseInt(vector_result[i][2]);
                this.cambio_estado_judicial();
                this.status_judicial = Integer.parseInt(vector_result[i][3]);
                this.intencion_pago = Integer.parseInt(vector_result[i][4]);
                this.com_extrajudicial = vector_result[i][5];
                this.com_judicial = vector_result[i][6];
                caso = Integer.parseInt(vector_result[i][7]);
            }

            this.somestadoextrajudicial = true;
            this.somstatusextrajudicial = true;
            this.somintensionpago = true;
            this.somestadojudicial = true;
            this.somstatusjudicial = true;

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String esAsistente = driver.getString("select u.asistente from usuario u where u.usuario=" + id_usuario, this.ambiente);
            String esGestor = driver.getString("select u.gestor from usuario u where u.usuario=" + id_usuario, this.ambiente);
            String esProcurador = driver.getString("select u.procurador from usuario u where u.usuario=" + id_usuario, this.ambiente);
            String esDigitador = driver.getString("select u.digitador from usuario u where u.usuario=" + id_usuario, this.ambiente);

            if (esAsistente.equals("SI")) {
                this.somestadoextrajudicial = false;
                this.somstatusextrajudicial = false;
                this.somintensionpago = false;
                this.somestadojudicial = false;
                this.somstatusjudicial = false;
            }
            if (esGestor.equals("SI")) {
                this.somestadoextrajudicial = false;
                this.somstatusextrajudicial = false;
                this.somintensionpago = false;
            }
            if (esProcurador.equals("SI") || esDigitador.equals("SI")) {
                this.somestadojudicial = false;
                this.somstatusjudicial = false;
            }

            this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(Actualizar_Expediente_Cobros): " + ex.toString());
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
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(cambio_estado_extrajudicial): " + ex.toString());
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
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(cambio_estado_judicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambio_gestion_contacto() {
        try {
            String cadenasql = "select "
                    + "distinct "
                    + "cc.codigo_contactabilidad id_codigo, "
                    + "concat(cc.codigo,'|',cc.nombre) codigo_resultado "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                    + "where "
                    + "tcci.tipo_codigo_contactabilidad = " + this.tipo_gestion + " and "
                    + "tcc.contacto = '" + this.gestion_contacto + "' "
                    + "order by "
                    + "concat(cc.codigo,'|',cc.nombre)";
            Driver driver = new Driver();
            this.lst_codigo_resultado = driver.lista_SelectItem_simple(cadenasql, this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(cambio_gestion_contacto): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void insertar_gestion_cobros() {
        try {
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.gestionCobrosInsertar(id_usuario, this.deudor, id_usuario, this.codigo_resultado, this.descripcion_gestion, this.gestion_contacto, this.estado_extrajudicial, this.status_extrajudicial, this.estado_judicial, this.status_judicial, this.intencion_pago, this.ambiente);
            
            this.limpiar_expediente_cobros();
            this.Actualizar_Expediente_Cobros();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Cobros(insertar_gestion_cobros): " + ex.toString());
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

    public Integer getIntencion_pago() {
        return intencion_pago;
    }

    public void setIntencion_pago(Integer intencion_pago) {
        this.intencion_pago = intencion_pago;
    }

    public Integer getTipo_gestion() {
        return tipo_gestion;
    }

    public void setTipo_gestion(Integer tipo_gestion) {
        this.tipo_gestion = tipo_gestion;
    }

    public String getGestion_contacto() {
        return gestion_contacto;
    }

    public void setGestion_contacto(String gestion_contacto) {
        this.gestion_contacto = gestion_contacto;
    }

    public Integer getCodigo_resultado() {
        return codigo_resultado;
    }

    public void setCodigo_resultado(Integer codigo_resultado) {
        this.codigo_resultado = codigo_resultado;
    }

    public String getDescripcion_gestion() {
        return descripcion_gestion;
    }

    public void setDescripcion_gestion(String descripcion_gestion) {
        this.descripcion_gestion = descripcion_gestion;
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

    public List<SelectItem> getLst_intension_pago() {
        return lst_intension_pago;
    }

    public void setLst_intension_pago(List<SelectItem> lst_intension_pago) {
        this.lst_intension_pago = lst_intension_pago;
    }

    public List<SelectItem> getLst_tipo_gestion() {
        return lst_tipo_gestion;
    }

    public void setLst_tipo_gestion(List<SelectItem> lst_tipo_gestion) {
        this.lst_tipo_gestion = lst_tipo_gestion;
    }

    public List<SelectItem> getLst_codigo_resultado() {
        return lst_codigo_resultado;
    }

    public void setLst_codigo_resultado(List<SelectItem> lst_codigo_resultado) {
        this.lst_codigo_resultado = lst_codigo_resultado;
    }

    public boolean isSomestadoextrajudicial() {
        return somestadoextrajudicial;
    }

    public void setSomestadoextrajudicial(boolean somestadoextrajudicial) {
        this.somestadoextrajudicial = somestadoextrajudicial;
    }

    public boolean isSomstatusextrajudicial() {
        return somstatusextrajudicial;
    }

    public void setSomstatusextrajudicial(boolean somstatusextrajudicial) {
        this.somstatusextrajudicial = somstatusextrajudicial;
    }

    public boolean isSomintensionpago() {
        return somintensionpago;
    }

    public void setSomintensionpago(boolean somintensionpago) {
        this.somintensionpago = somintensionpago;
    }

    public boolean isSomestadojudicial() {
        return somestadojudicial;
    }

    public void setSomestadojudicial(boolean somestadojudicial) {
        this.somestadojudicial = somestadojudicial;
    }

    public boolean isSomstatusjudicial() {
        return somstatusjudicial;
    }

    public void setSomstatusjudicial(boolean somstatusjudicial) {
        this.somstatusjudicial = somstatusjudicial;
    }
    
}
