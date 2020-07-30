package lexcom.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Gestion_Cobros")
@ViewScoped
public class Gestion_Cobros implements Serializable {

    private static final long serialVersionUID = 1L;
    private String usuario;
    private String ambiente;

    private Integer indice;
    private Integer tipo_gestion;
    private String contacto;
    private Integer codigo_resultado;
    private String gestion_descripcion;
    
    private Integer tipo_gestion_p;
    private Integer codigo_resultado_p;
    private Date fecha;
    private String gestion_descripcion_p;
    
    private Integer estado_extrajudicial;
    private Integer status_extrajudicial;
    private Integer estado_judicial;
    private Integer status_judicial;
    private Integer intencion_pago;
    
    private List<SelectItem> lst_tipo_gestion;
    private List<SelectItem> lst_codigo_resultado;
    private List<SelectItem> lst_tipo_gestion_p;
    private List<SelectItem> lst_codigo_resultado_p;
    private List<SelectItem> lst_estado_extrajudicial;
    private List<SelectItem> lst_status_extrajudicial;
    private List<SelectItem> lst_estado_judicial;
    private List<SelectItem> lst_status_judicial;
    private List<SelectItem> lst_intencion_pago;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            this.indice = 0;
            this.tipo_gestion = 0;
            this.contacto = "";
            this.codigo_resultado = 0;
            this.gestion_descripcion = "";
            this.tipo_gestion_p = 0;
            this.codigo_resultado_p = 0;
            this.fecha = new Date();
            this.gestion_descripcion_p = "";
            this.estado_extrajudicial = 0;
            this.status_extrajudicial = 0;
            this.estado_judicial = 0;
            this.status_judicial = 0;
            this.intencion_pago = 0;
            
            this.lst_tipo_gestion = new ArrayList<>();
            this.lst_codigo_resultado = new ArrayList<>();
            this.lst_tipo_gestion_p = new ArrayList<>();
            this.lst_codigo_resultado_p = new ArrayList<>();
            this.lst_estado_extrajudicial = new ArrayList<>();
            this.lst_status_extrajudicial = new ArrayList<>();
            this.lst_estado_judicial = new ArrayList<>();
            this.lst_status_judicial = new ArrayList<>();
            this.lst_intencion_pago = new ArrayList<>();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void mostrar_ventana(
            Integer estado_extrajudicial, 
            Integer status_extrajudicial, 
            Integer estado_judicial, 
            Integer status_judicial, 
            Integer intencion_pago) {
        
        try {
            Driver drive = new Driver();
            this.estado_extrajudicial = estado_extrajudicial;
            this.status_extrajudicial = status_extrajudicial;
            this.estado_judicial = estado_judicial;
            this.status_judicial = status_judicial;
            this.intencion_pago = intencion_pago;
            
            String lista_tipo_gestion_sql = "select distinct "
                    + "tct.tipo_codigo_contactabilidad, "
                    + "tct.nombre "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join tipo_codigo_contactabilidad tct on (tcc.tipo_codigo_contactabilidad=tct.tipo_codigo_contactabilidad) "
                    + "order by "
                    + "tct.nombre";
            this.lst_tipo_gestion = drive.lista_SelectItem_simple(lista_tipo_gestion_sql, this.ambiente);
            if(this.lst_tipo_gestion.isEmpty()) {
                this.tipo_gestion = 0;
            } else {
                this.tipo_gestion = Integer.parseInt(this.lst_tipo_gestion.get(0).getValue().toString());
            }
            String lista_codigo_resultado_sql = "select "
                    + "distinct concat(cc.codigo,'|',cc.nombre) codigo "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                    + "where "
                    + "tcci.tipo_codigo_contactabilidad= " + this.tipo_gestion + " and "
                    + "tcc.contacto like '" + this.contacto + "' "
                    + "order by "
                    + "cc.nombre";
            this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
            
            String lista_intencion_pago_sql = "select t.tipo_codigo_contactabilidad, t.nombre from tipo_codigo_contactabilidad t where t.estado='VIGENTE'";
            this.lst_intencion_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
            
            String lista_tipo_gestion_p_sql = "select distinct "
                    + "tct.tipo_codigo_contactabilidad, "
                    + "tct.nombre "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join tipo_codigo_contactabilidad tct on (tcc.tipo_codigo_contactabilidad=tct.tipo_codigo_contactabilidad) "
                    + "order by "
                    + "tct.nombre";
            this.lst_tipo_gestion_p = drive.lista_SelectItem_simple(lista_tipo_gestion_p_sql, this.ambiente);
            if(this.lst_tipo_gestion_p.isEmpty()) {
                this.tipo_gestion_p = 0;
            } else {
                this.tipo_gestion_p = Integer.parseInt(this.lst_tipo_gestion_p.get(0).getValue().toString());
            }
            String lista_codigo_resultado_p_sql = "select distinct "
                    + "cc.codigo_contactabilidad, "
                    + "concat(cc.codigo,'|',cc.nombre) codigo "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                    + "where "
                    + "tcci.tipo_codigo_contactabilidad=" + this.tipo_gestion_p + " and "
                    + "tcc.contacto like '%%' "
                    + "order by "
                    + "cc.nombre";
            this.lst_codigo_resultado_p = drive.lista_SelectItem_simple(lista_codigo_resultado_p_sql, this.ambiente);
            
            //Llena los comboBox de Estado-Status extrajudicial.
            String lista_estado_extrajudicial_sql = "select distinct "
                    + "se.sestado_extra, "
                    + "se.nombre "
                    + "from "
                    + "estado_status_extrajudicial ese "
                    + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                    + "order by "
                    + "se.nombre";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extrajudicial_sql, this.ambiente);
            
            String lista_status_extrajudicial_sql = "select distinct "
                    + "ee.estatus_extra, "
                    + "ee.nombre "
                    + "from "
                    + "estado_status_extrajudicial ese "
                    + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                    + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                    + "where "
                    + "se.sestado_extra= " + this.estado_extrajudicial + " "
                    + "order by "
                    + "ee.nombre";
            this.lst_status_extrajudicial = drive.lista_SelectItem_simple(lista_status_extrajudicial_sql, this.ambiente);
            
            //Llena los comboBox de Estado-Status judicial.
            String lista_estado_judicial_sql = "select distinct "
                    + "s.sestado, "
                    + "s.nombre "
                    + "from "
                    + "estado_status_judicial esj "
                    + "left join sestado s on (esj.sestado=s.sestado) "
                    + "order by "
                    + "s.nombre";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            
            String lista_status_judicial_sql = "select distinct "
                    + "e.estatus, "
                    + "e.nombre "
                    + "from "
                    + "estado_status_judicial esj "
                    + "left join estatus e on (esj.estatus=e.estatus) "
                    + "left join sestado s on (esj.sestado=s.sestado) "
                    + "where "
                    + "s.sestado=" + this.estado_judicial + " "
                    + "order by "
                    + "e.nombre";
            this.lst_status_judicial = drive.lista_SelectItem_simple(lista_status_judicial_sql, this.ambiente);
            
            
            RequestContext.getCurrentInstance().execute("PF('var_cobros').show();");
            RequestContext.getCurrentInstance().execute("PF('VTabWiew').select(0);");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambiar_tipo_gestion() {
        try {
            Driver drive = new Driver();
            String lista_codigo_resultado_sql = "select "
                    + "distinct concat(cc.codigo,'|',cc.nombre) codigo "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                    + "where "
                    + "tcci.tipo_codigo_contactabilidad= " + this.tipo_gestion + " and "
                    + "tcc.contacto like '" + this.contacto + "' "
                    + "order by "
                    + "cc.nombre";
            this.lst_codigo_resultado = drive.lista_SelectItem_simple(lista_codigo_resultado_sql, this.ambiente);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambiar_tipo_gestion_p() {
        try {
            Driver drive = new Driver();
            String lista_codigo_resultado_p_sql = "select distinct "
                    + "cc.codigo_contactabilidad, "
                    + "concat(cc.codigo,'|',cc.nombre) codigo "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "left join tipo_codigo_contactabilidad tcci on (tcc.tipo_codigo_contactabilidad=tcci.tipo_codigo_contactabilidad) "
                    + "where "
                    + "tcci.tipo_codigo_contactabilidad=" + this.tipo_gestion_p + " and "
                    + "tcc.contacto like '%%' "
                    + "order by "
                    + "cc.nombre";
            this.lst_codigo_resultado_p = drive.lista_SelectItem_simple(lista_codigo_resultado_p_sql, this.ambiente);
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambiar_estado_extrajudicial() {
        try {
            Driver drive = new Driver();
            String lista_status_extrajudicial_sql = "select distinct "
                    + "ee.estatus_extra, "
                    + "ee.nombre "
                    + "from "
                    + "estado_status_extrajudicial ese "
                    + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                    + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                    + "where "
                    + "se.sestado_extra= " + this.estado_extrajudicial + " "
                    + "order by "
                    + "ee.nombre";
            this.lst_status_extrajudicial = drive.lista_SelectItem_simple(lista_status_extrajudicial_sql, this.ambiente);
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambiar_estado_judicial() {
        try {
            Driver drive = new Driver();
            String lista_status_judicial_sql = "select distinct "
                    + "e.estatus, "
                    + "e.nombre "
                    + "from "
                    + "estado_status_judicial esj "
                    + "left join estatus e on (esj.estatus=e.estatus) "
                    + "left join sestado s on (esj.sestado=s.sestado) "
                    + "where "
                    + "s.sestado=" + this.estado_judicial + " "
                    + "order by "
                    + "e.nombre";
            this.lst_status_judicial = drive.lista_SelectItem_simple(lista_status_judicial_sql, this.ambiente);
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void insertar_gestion() {
        try {
            
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Integer getTipo_gestion() {
        return tipo_gestion;
    }

    public void setTipo_gestion(Integer tipo_gestion) {
        this.tipo_gestion = tipo_gestion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Integer getCodigo_resultado() {
        return codigo_resultado;
    }

    public void setCodigo_resultado(Integer codigo_resultado) {
        this.codigo_resultado = codigo_resultado;
    }

    public String getGestion_descripcion() {
        return gestion_descripcion;
    }

    public void setGestion_descripcion(String gestion_descripcion) {
        this.gestion_descripcion = gestion_descripcion;
    }

    public Integer getTipo_gestion_p() {
        return tipo_gestion_p;
    }

    public void setTipo_gestion_p(Integer tipo_gestion_p) {
        this.tipo_gestion_p = tipo_gestion_p;
    }

    public Integer getCodigo_resultado_p() {
        return codigo_resultado_p;
    }

    public void setCodigo_resultado_p(Integer codigo_resultado_p) {
        this.codigo_resultado_p = codigo_resultado_p;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGestion_descripcion_p() {
        return gestion_descripcion_p;
    }

    public void setGestion_descripcion_p(String gestion_descripcion_p) {
        this.gestion_descripcion_p = gestion_descripcion_p;
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

    public List<SelectItem> getLst_tipo_gestion_p() {
        return lst_tipo_gestion_p;
    }

    public void setLst_tipo_gestion_p(List<SelectItem> lst_tipo_gestion_p) {
        this.lst_tipo_gestion_p = lst_tipo_gestion_p;
    }

    public List<SelectItem> getLst_codigo_resultado_p() {
        return lst_codigo_resultado_p;
    }

    public void setLst_codigo_resultado_p(List<SelectItem> lst_codigo_resultado_p) {
        this.lst_codigo_resultado_p = lst_codigo_resultado_p;
    }

    public List<SelectItem> getLst_estado_extrajudicial() {
        return lst_estado_extrajudicial;
    }

    public void setLst_estado_extrajudicial(List<SelectItem> lst_estado_extrajudicial) {
        this.lst_estado_extrajudicial = lst_estado_extrajudicial;
    }

    public List<SelectItem> getLst_status_extrajudicial() {
        return lst_status_extrajudicial;
    }

    public void setLst_status_extrajudicial(List<SelectItem> lst_status_extrajudicial) {
        this.lst_status_extrajudicial = lst_status_extrajudicial;
    }

    public List<SelectItem> getLst_estado_judicial() {
        return lst_estado_judicial;
    }

    public void setLst_estado_judicial(List<SelectItem> lst_estado_judicial) {
        this.lst_estado_judicial = lst_estado_judicial;
    }

    public List<SelectItem> getLst_status_judicial() {
        return lst_status_judicial;
    }

    public void setLst_status_judicial(List<SelectItem> lst_status_judicial) {
        this.lst_status_judicial = lst_status_judicial;
    }

    public List<SelectItem> getLst_intencion_pago() {
        return lst_intencion_pago;
    }

    public void setLst_intencion_pago(List<SelectItem> lst_intencion_pago) {
        this.lst_intencion_pago = lst_intencion_pago;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
