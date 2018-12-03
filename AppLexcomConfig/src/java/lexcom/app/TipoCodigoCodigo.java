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

@ManagedBean(name = "TipoCodigoCodigo")
@ViewScoped
public class TipoCodigoCodigo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    //Variables para registrar la información del usuario.
    private Integer tipo_codigo_resultado_d;
    private List<SelectItem> lst_tipo_codigo_resultado;
    private DualListModel<String> codigo_resultado_d;
    private List<String> codigo_resultado_source;
    private List<String> codigo_resultado_target;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean somTipoCodigoResultado;
    private Boolean pckCodigoResultado;
    private Boolean btnGuardar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            this.codigo_resultado_source = new ArrayList<>();
            this.codigo_resultado_target = new ArrayList<>();
            this.codigo_resultado_d = new DualListModel<>(codigo_resultado_source, codigo_resultado_target);
            
            Driver drive = new Driver();
            String lista_tipo_codigo_resultado_sql = "select s.tipo_codigo_contactabilidad, s.nombre from tipo_codigo_contactabilidad s where s.estado='VIGENTE'";
            this.lst_tipo_codigo_resultado = drive.lista_SelectItem_simple(lista_tipo_codigo_resultado_sql, this.ambiente);
            if(this.lst_tipo_codigo_resultado.size() > 0) {
                tipo_codigo_resultado_d = Integer.parseInt(lst_tipo_codigo_resultado.get(0).getValue().toString());
                cargar_codigo_resultado();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            this.codigo_resultado_source = new ArrayList<>();
            this.codigo_resultado_target = new ArrayList<>();
            this.codigo_resultado_d = new DualListModel<>(codigo_resultado_source, codigo_resultado_target);
            
            Driver drive = new Driver();
            String lista_tipo_codigo_resultado_sql = "select s.tipo_codigo_contactabilidad, s.nombre from tipo_codigo_contactabilidad s where s.estado='VIGENTE'";
            this.lst_tipo_codigo_resultado = drive.lista_SelectItem_simple(lista_tipo_codigo_resultado_sql, this.ambiente);
            if(this.lst_tipo_codigo_resultado.size() > 0) {
                tipo_codigo_resultado_d = Integer.parseInt(lst_tipo_codigo_resultado.get(0).getValue().toString());
                cargar_codigo_resultado();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_codigo_resultado() {
        try {
            Driver drive = new Driver();
            
            this.codigo_resultado_source = new ArrayList<>();
            String cadenasql = "select "
                    + "cc.nombre "
                    + "from "
                    + "codigo_contactabilidad cc "
                    + "where "
                    + "cc.codigo_contactabilidad not in "
                    + "(select tcc.codigo_contactabilidad from tipo_codigo_codigo tcc where tcc.tipo_codigo_contactabilidad=" + this.tipo_codigo_resultado_d + ")";
            this.codigo_resultado_source = drive.lista_StringItem(cadenasql, this.ambiente);

            this.codigo_resultado_target = new ArrayList<>();
            cadenasql = "select "
                    + "cc.nombre "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "where "
                    + "tcc.tipo_codigo_contactabilidad=" + this.tipo_codigo_resultado_d;
            this.codigo_resultado_target = drive.lista_StringItem(cadenasql, this.ambiente);

            this.codigo_resultado_d = new DualListModel<>(codigo_resultado_source, codigo_resultado_target);
        } catch (Exception ex) {
            Driver drive = new Driver();
            
            this.codigo_resultado_source = new ArrayList<>();
            String cadenasql = "select '***ERROR***'";
            this.codigo_resultado_source = drive.lista_StringItem(cadenasql, this.ambiente);

            this.codigo_resultado_target = new ArrayList<>();
            cadenasql = "select '***ERROR***'";
            this.codigo_resultado_target = drive.lista_StringItem(cadenasql, this.ambiente);

            this.codigo_resultado_d = new DualListModel<>(codigo_resultado_source, codigo_resultado_target);
        }
    }
    
    public void guardar() {
        String resultado = "";

        try {
            Driver driver = new Driver();
            List<Integer> lst_codigo_resultado = new ArrayList<>();
            for(Integer i=0; i<this.codigo_resultado_d.getTarget().size(); i++) {
                lst_codigo_resultado.add(driver.getInt("select cc.codigo_contactabilidad from codigo_contactabilidad cc where cc.nombre='" + this.codigo_resultado_d.getTarget().get(i) + "'", this.ambiente));
            }
            
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.tipoCodigoCodigoResultado(id_usuario, this.tipo_codigo_resultado_d, lst_codigo_resultado, this.ambiente);
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

    public Integer getTipo_codigo_resultado_d() {
        return tipo_codigo_resultado_d;
    }

    public void setTipo_codigo_resultado_d(Integer tipo_codigo_resultado_d) {
        this.tipo_codigo_resultado_d = tipo_codigo_resultado_d;
    }

    public List<SelectItem> getLst_tipo_codigo_resultado() {
        return lst_tipo_codigo_resultado;
    }

    public void setLst_tipo_codigo_resultado(List<SelectItem> lst_tipo_codigo_resultado) {
        this.lst_tipo_codigo_resultado = lst_tipo_codigo_resultado;
    }

    public DualListModel<String> getCodigo_resultado_d() {
        return codigo_resultado_d;
    }

    public void setCodigo_resultado_d(DualListModel<String> codigo_resultado_d) {
        this.codigo_resultado_d = codigo_resultado_d;
    }

    public List<String> getCodigo_resultado_source() {
        return codigo_resultado_source;
    }

    public void setCodigo_resultado_source(List<String> codigo_resultado_source) {
        this.codigo_resultado_source = codigo_resultado_source;
    }

    public List<String> getCodigo_resultado_target() {
        return codigo_resultado_target;
    }

    public void setCodigo_resultado_target(List<String> codigo_resultado_target) {
        this.codigo_resultado_target = codigo_resultado_target;
    }

    public Boolean getSomTipoCodigoResultado() {
        return somTipoCodigoResultado;
    }

    public void setSomTipoCodigoResultado(Boolean somTipoCodigoResultado) {
        this.somTipoCodigoResultado = somTipoCodigoResultado;
    }

    public Boolean getPckCodigoResultado() {
        return pckCodigoResultado;
    }

    public void setPckCodigoResultado(Boolean pckCodigoResultado) {
        this.pckCodigoResultado = pckCodigoResultado;
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
    
}
