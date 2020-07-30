package lexcom.app;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Tipo_Codigo_Resultado_Contacto")
@ViewScoped
public class Tipo_Codigo_Resultado_Contacto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Tipo_Codigo_Resultado_Contacto_List sel_tipo_codigo_resultado_contacto;
    private List<Tipo_Codigo_Resultado_Contacto_List> lst_tipo_codigo_resultado_contacto;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            String cadenasql = "select "
                    + "tcr.tipo_codigo_contactabilidad, "
                    + "tcr.nombre, "
                    + "cc.codigo_contactabilidad, "
                    + "cc.nombre, "
                    + "tcc.contacto "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join tipo_codigo_contactabilidad tcr on (tcc.tipo_codigo_contactabilidad=tcr.tipo_codigo_contactabilidad) "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "order by "
                    + "tcr.nombre, "
                    + "cc.nombre";

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
            
            this.lst_tipo_codigo_resultado_contacto = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Tipo_Codigo_Resultado_Contacto_List pel = new Tipo_Codigo_Resultado_Contacto_List(
                        i,
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        Integer.parseInt(vector_result[i][2]),
                        vector_result[i][3],
                        vector_result[i][4]);
                this.lst_tipo_codigo_resultado_contacto.add(pel);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            String cadenasql = "select "
                    + "tcr.tipo_codigo_contactabilidad, "
                    + "tcr.nombre, "
                    + "cc.codigo_contactabilidad, "
                    + "cc.nombre, "
                    + "tcc.contacto "
                    + "from "
                    + "tipo_codigo_codigo tcc "
                    + "left join tipo_codigo_contactabilidad tcr on (tcc.tipo_codigo_contactabilidad=tcr.tipo_codigo_contactabilidad) "
                    + "left join codigo_contactabilidad cc on (tcc.codigo_contactabilidad=cc.codigo_contactabilidad) "
                    + "order by "
                    + "tcr.nombre, "
                    + "cc.nombre";

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
            
            this.lst_tipo_codigo_resultado_contacto = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Tipo_Codigo_Resultado_Contacto_List pel = new Tipo_Codigo_Resultado_Contacto_List(
                        i,
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        Integer.parseInt(vector_result[i][2]),
                        vector_result[i][3],
                        vector_result[i][4]);
                this.lst_tipo_codigo_resultado_contacto.add(pel);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void guardar_contacto() {
        String resultado = "";

        try {
            List<String> lst_contacto = new ArrayList<>();
            for (int i = 0; i < this.lst_tipo_codigo_resultado_contacto.size(); i++) {
                lst_contacto.add(this.lst_tipo_codigo_resultado_contacto.get(i).getTipo_codigo_resultado() + "," + this.lst_tipo_codigo_resultado_contacto.get(i).getCodigo_resultado() + "," + this.lst_tipo_codigo_resultado_contacto.get(i).getContacto());
            }
            
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.tipoCodigoResultadoContacto(
                    id_usuario,
                    lst_contacto, 
                    this.ambiente);
            
            this.constructor();
            
            RequestContext.getCurrentInstance().execute("PF('vContacto').clearFilters();");
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

    public Tipo_Codigo_Resultado_Contacto_List getSel_tipo_codigo_resultado_contacto() {
        return sel_tipo_codigo_resultado_contacto;
    }

    public void setSel_tipo_codigo_resultado_contacto(Tipo_Codigo_Resultado_Contacto_List sel_tipo_codigo_resultado_contacto) {
        this.sel_tipo_codigo_resultado_contacto = sel_tipo_codigo_resultado_contacto;
    }

    public List<Tipo_Codigo_Resultado_Contacto_List> getLst_tipo_codigo_resultado_contacto() {
        return lst_tipo_codigo_resultado_contacto;
    }

    public void setLst_tipo_codigo_resultado_contacto(List<Tipo_Codigo_Resultado_Contacto_List> lst_tipo_codigo_resultado_contacto) {
        this.lst_tipo_codigo_resultado_contacto = lst_tipo_codigo_resultado_contacto;
    }
    
}
