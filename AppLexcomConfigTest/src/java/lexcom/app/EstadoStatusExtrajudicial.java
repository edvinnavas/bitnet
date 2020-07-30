package lexcom.app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "EstadoStatusExtrajudicial")
@ViewScoped
public class EstadoStatusExtrajudicial implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    //Variables para registrar la información del usuario.
    private Integer estado_d;
    private List<SelectItem> lst_estados;
    private DualListModel<String> status_d;
    private List<String> status_source;
    private List<String> status_target;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean somEstado;
    private Boolean pckStatus;
    private Boolean btnGuardar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            this.status_source = new ArrayList<>();
            this.status_target = new ArrayList<>();
            this.status_d = new DualListModel<>(status_source, status_target);
            
            Driver drive = new Driver();
            String lista_estado_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estados = drive.lista_SelectItem_simple(lista_estado_sql, this.ambiente);
            if(this.lst_estados.size() > 0) {
                estado_d = Integer.parseInt(lst_estados.get(0).getValue().toString());
                cargar_status();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            this.status_source = new ArrayList<>();
            this.status_target = new ArrayList<>();
            this.status_d = new DualListModel<>(status_source, status_target);
            
            Driver drive = new Driver();
            String lista_estado_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estados = drive.lista_SelectItem_simple(lista_estado_sql, this.ambiente);
            if(this.lst_estados.size() > 0) {
                estado_d = Integer.parseInt(lst_estados.get(0).getValue().toString());
                cargar_status();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_status() {
        try {
            Driver drive = new Driver();
            
            this.status_source = new ArrayList<>();
            String cadenasql = "select "
                    + "e.nombre "
                    + "from "
                    + "estatus_extra e "
                    + "where "
                    + "e.estatus_extra not in "
                    + "(select es.estatus_extra from estado_status_extrajudicial es where es.sestado_extra=" + this.estado_d + ")";
            this.status_source = drive.lista_StringItem(cadenasql, this.ambiente);

            this.status_target = new ArrayList<>();
            cadenasql = "select "
                    + "e.nombre "
                    + "from "
                    + "estado_status_extrajudicial es "
                    + "left join estatus_extra e on (es.estatus_extra=e.estatus_extra) "
                    + "where "
                    + "es.sestado_extra=" + this.estado_d;
            this.status_target = drive.lista_StringItem(cadenasql, this.ambiente);

            this.status_d = new DualListModel<>(status_source, status_target);
        } catch (Exception ex) {
            Driver drive = new Driver();
            
            this.status_source = new ArrayList<>();
            String cadenasql = "select '***ERROR***'";
            this.status_source = drive.lista_StringItem(cadenasql, this.ambiente);

            this.status_target = new ArrayList<>();
            cadenasql = "select '***ERROR***'";
            this.status_target = drive.lista_StringItem(cadenasql, this.ambiente);

            this.status_d = new DualListModel<>(status_source, status_target);
        }
    }
    
    public void guardar() {
        String resultado = "";

        try {
            Driver driver = new Driver();
            List<Integer> lst_status = new ArrayList<>();
            for(Integer i=0; i<this.status_d.getTarget().size(); i++) {
                lst_status.add(driver.getInt("select e.estatus_extra from estatus_extra e where e.nombre='" + this.status_d.getTarget().get(i) + "'", this.ambiente));
            }
            
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.estadoStatusExtrajudicial(id_usuario, this.estado_d, lst_status, this.ambiente);
            this.constructor();

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

    public Integer getEstado_d() {
        return estado_d;
    }

    public void setEstado_d(Integer estado_d) {
        this.estado_d = estado_d;
    }

    public List<SelectItem> getLst_estados() {
        return lst_estados;
    }

    public void setLst_estados(List<SelectItem> lst_estados) {
        this.lst_estados = lst_estados;
    }

    public DualListModel<String> getStatus_d() {
        return status_d;
    }

    public void setStatus_d(DualListModel<String> status_d) {
        this.status_d = status_d;
    }

    public Boolean getSomEstado() {
        return somEstado;
    }

    public void setSomEstado(Boolean somEstado) {
        this.somEstado = somEstado;
    }

    public Boolean getPckStatus() {
        return pckStatus;
    }

    public void setPckStatus(Boolean pckStatus) {
        this.pckStatus = pckStatus;
    }

    public Boolean getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(Boolean btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public List<String> getStatus_source() {
        return status_source;
    }

    public void setStatus_source(List<String> status_source) {
        this.status_source = status_source;
    }

    public List<String> getStatus_target() {
        return status_target;
    }

    public void setStatus_target(List<String> status_target) {
        this.status_target = status_target;
    }
    
}
