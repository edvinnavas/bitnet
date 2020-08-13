package lexcom.app;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "Juicio_Medida_Etapa_Workflow")
@ViewScoped
public class Juicio_Medida_Etapa_Workflow implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    
    //Variables para registrar la información del usuario.
    private List<SelectItem> lst_juicio_medida = new ArrayList<>();
    private List<SelectItem> lst_juicio_medida_etapa = new ArrayList<>();
    
    public Long juicio_medida;
    public Long juicio_medida_etapa;
    
    private List<String> lst_medida_etapa_siguiente_asignada;
    private List<String> lst_medida_etapa_siguiente_no_asignada;

    private DualListModel<String> lst_medida_etapa_asignados;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            Driver driver = new Driver();
            this.lst_juicio_medida = driver.lista_SelectItem_simple("select jm.juicio_medida, jm.nombre from juicio_medida jm order by jm.juicio_medida", this.ambiente);
            this.lst_juicio_medida_etapa = driver.lista_SelectItem_simple("select jme.juicio_medida_etapa, jme.nombre from juicio_medida_etapa jme order by jme.juicio_medida_etapa", this.ambiente);
            
            this.juicio_medida = new Long(1);
            this.juicio_medida_etapa = new Long(1);
            
            this.cargar_info();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            Driver driver = new Driver();
            this.lst_juicio_medida = driver.lista_SelectItem_simple("select jm.juicio_medida, jm.nombre from juicio_medida jm order by jm.juicio_medida", this.ambiente);
            this.lst_juicio_medida_etapa = driver.lista_SelectItem_simple("select jme.juicio_medida_etapa, jme.nombre from juicio_medida_etapa jme order by jme.juicio_medida_etapa", this.ambiente);
            
            this.juicio_medida = new Long(1);
            this.juicio_medida_etapa = new Long(1);
            
            this.cargar_info();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cargar_info() {
        try {
            Driver driver = new Driver();
            
            String get_list_string_sql = "select jme1.nombre "
                    + "from juicio_medida_etapa jme1 "
                    + "where jme1.nombre not in (select jme.nombre "
                    + "from juicio_medida_etapa_workflow jmew left join juicio_medida_etapa jme on (jmew.juicio_medida_etapa_siguiente = jme.juicio_medida_etapa) "
                    + "where jmew.juicio_medida=" + this.juicio_medida + " and jmew.juicio_medida_etapa_actual=" + this.juicio_medida_etapa + " union all "
                    + "select jme.nombre from juicio_medida_etapa jme where jme.juicio_medida_etapa=" + this.juicio_medida_etapa + ") "
                    + "order by jme1.juicio_medida_etapa";
            this.lst_medida_etapa_siguiente_no_asignada = driver.lista_StringItem(get_list_string_sql, ambiente);
            
            get_list_string_sql = "select jme.nombre "
                    + "from juicio_medida_etapa_workflow jmew left join juicio_medida_etapa jme on (jmew.juicio_medida_etapa_siguiente=jme.juicio_medida_etapa) "
                    + "where jmew.juicio_medida=" + this.juicio_medida + " and jmew.juicio_medida_etapa_actual=" + this.juicio_medida_etapa + " "
                    + "order by jme.juicio_medida_etapa";
            this.lst_medida_etapa_siguiente_asignada = driver.lista_StringItem(get_list_string_sql, ambiente);
            
            this.lst_medida_etapa_asignados = new DualListModel<>(this.lst_medida_etapa_siguiente_no_asignada, this.lst_medida_etapa_siguiente_asignada);
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar() {
        String resultado = "";

        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.juicioMedidaEtapaWorkflowActualizar(id_usuario, this.juicio_medida, this.juicio_medida_etapa, this.lst_medida_etapa_asignados.getTarget(), this.ambiente);
            this.constructor();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
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

    public List<SelectItem> getLst_juicio_medida() {
        return lst_juicio_medida;
    }

    public void setLst_juicio_medida(List<SelectItem> lst_juicio_medida) {
        this.lst_juicio_medida = lst_juicio_medida;
    }

    public List<SelectItem> getLst_juicio_medida_etapa() {
        return lst_juicio_medida_etapa;
    }

    public void setLst_juicio_medida_etapa(List<SelectItem> lst_juicio_medida_etapa) {
        this.lst_juicio_medida_etapa = lst_juicio_medida_etapa;
    }

    public Long getJuicio_medida() {
        return juicio_medida;
    }

    public void setJuicio_medida(Long juicio_medida) {
        this.juicio_medida = juicio_medida;
    }

    public Long getJuicio_medida_etapa() {
        return juicio_medida_etapa;
    }

    public void setJuicio_medida_etapa(Long juicio_medida_etapa) {
        this.juicio_medida_etapa = juicio_medida_etapa;
    }

    public List<String> getLst_medida_etapa_siguiente_asignada() {
        return lst_medida_etapa_siguiente_asignada;
    }

    public void setLst_medida_etapa_siguiente_asignada(List<String> lst_medida_etapa_siguiente_asignada) {
        this.lst_medida_etapa_siguiente_asignada = lst_medida_etapa_siguiente_asignada;
    }

    public List<String> getLst_medida_etapa_siguiente_no_asignada() {
        return lst_medida_etapa_siguiente_no_asignada;
    }

    public void setLst_medida_etapa_siguiente_no_asignada(List<String> lst_medida_etapa_siguiente_no_asignada) {
        this.lst_medida_etapa_siguiente_no_asignada = lst_medida_etapa_siguiente_no_asignada;
    }

    public DualListModel<String> getLst_medida_etapa_asignados() {
        return lst_medida_etapa_asignados;
    }

    public void setLst_medida_etapa_asignados(DualListModel<String> lst_medida_etapa_asignados) {
        this.lst_medida_etapa_asignados = lst_medida_etapa_asignados;
    }
    
}
