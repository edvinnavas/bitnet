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

@ManagedBean(name = "Permiso_Expediente")
@ViewScoped
public class Permiso_Expediente implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private String opcion;

    private Permiso_Expediente_List sel_permiso_expediente;
    private List<Permiso_Expediente_List> lst_permiso_expediente;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            String cadenasql = "select "
                    + "u.usuario, "
                    + "u.nombre, "
                    + "'Expedientes' menu, "
                    + "ifnull(p.ver,'NO') ver "
                    + "from "
                    + "usuario u "
                    + "left join permiso_usuario_uno p on (u.usuario=p.usuario and p.menu=28) "
                    + "where "
                    + "u.estado='VIGENTE' "
                    + "order by "
                    + "u.nombre";

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
            
            this.lst_permiso_expediente = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Permiso_Expediente_List pel = new Permiso_Expediente_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3]);
                this.lst_permiso_expediente.add(pel);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            String cadenasql = "select "
                    + "u.usuario, "
                    + "u.nombre, "
                    + "'Expedientes' menu, "
                    + "p.ver "
                    + "from "
                    + "usuario u "
                    + "left join permiso_usuario_uno p on (u.usuario=p.usuario and p.menu=28) "
                    + "where "
                    + "u.estado='VIGENTE' "
                    + "order by "
                    + "u.nombre";

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
            
            this.lst_permiso_expediente = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Permiso_Expediente_List pel = new Permiso_Expediente_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3]);
                this.lst_permiso_expediente.add(pel);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void guardar_permisos() {
        String resultado = "";

        try {
            List<String> permisos = new ArrayList<>();
            for (int i = 0; i < this.lst_permiso_expediente.size(); i++) {
                permisos.add(this.lst_permiso_expediente.get(i).getId_usuario() + "," + this.lst_permiso_expediente.get(i).getPermiso());
            }
            
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.permisoExpediente(
                    id_usuario,
                    permisos, 
                    this.ambiente);
            
            this.constructor();
            
            RequestContext.getCurrentInstance().execute("PF('vPermisos').clearFilters();");
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

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Permiso_Expediente_List getSel_permiso_expediente() {
        return sel_permiso_expediente;
    }

    public void setSel_permiso_expediente(Permiso_Expediente_List sel_permiso_expediente) {
        this.sel_permiso_expediente = sel_permiso_expediente;
    }

    public List<Permiso_Expediente_List> getLst_permiso_expediente() {
        return lst_permiso_expediente;
    }

    public void setLst_permiso_expediente(List<Permiso_Expediente_List> lst_permiso_expediente) {
        this.lst_permiso_expediente = lst_permiso_expediente;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
