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

@ManagedBean(name = "estado_status_extrajudicial")
@ViewScoped
public class estado_status_extrajudicial implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private String opcion;

    private estado_status_extrajudicial_List selected;
    private List<estado_status_extrajudicial_List> lst_estado_status_extrajudicial;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            String cadenasql = "SELECT "
                    + "estado_status_extrajudicial.sestado_extra, "
                    + "sestado_extra.nombre, "
                    + "estado_status_extrajudicial.estatus_extra, "
                    + "estatus_extra.nombre, "
                    + "estado_status_extrajudicial.permite_estado_judicial, "
                    + "if(estado_status_extrajudicial.permite_estado_judicial=0,'NO','SI') permite_nombre "
                    + "FROM "
                    + "estado_status_extrajudicial "
                    + "LEFT JOIN sestado_extra ON (estado_status_extrajudicial.sestado_extra = sestado_extra.sestado_extra) "
                    + "LEFT JOIN estatus_extra ON (estado_status_extrajudicial.estatus_extra = estatus_extra.estatus_extra)";

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

            lst_estado_status_extrajudicial = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                estado_status_extrajudicial_List ses = new estado_status_extrajudicial_List(
                        i,
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        Integer.parseInt(vector_result[i][2]),
                        vector_result[i][3],
                        Integer.parseInt(vector_result[i][4]),
                        vector_result[i][5]);
                lst_estado_status_extrajudicial.add(ses);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();

            String cadenasql = "SELECT "
                    + "estado_status_extrajudicial.sestado_extra, "
                    + "sestado_extra.nombre, "
                    + "estado_status_extrajudicial.estatus_extra, "
                    + "estatus_extra.nombre, "
                    + "estado_status_extrajudicial.permite_estado_judicial, "
                    + "if(estado_status_extrajudicial.permite_estado_judicial=0,'NO','SI') permite_nombre "
                    + "FROM "
                    + "estado_status_extrajudicial "
                    + "LEFT JOIN sestado_extra ON (estado_status_extrajudicial.sestado_extra = sestado_extra.sestado_extra) "
                    + "LEFT JOIN estatus_extra ON (estado_status_extrajudicial.estatus_extra = estatus_extra.estatus_extra)";

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

            lst_estado_status_extrajudicial = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                estado_status_extrajudicial_List ses = new estado_status_extrajudicial_List(
                        i,
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        Integer.parseInt(vector_result[i][2]),
                        vector_result[i][3],
                        Integer.parseInt(vector_result[i][4]),
                        vector_result[i][5]);
                lst_estado_status_extrajudicial.add(ses);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar() {
        String resultado = "";

        try {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < lst_estado_status_extrajudicial.size(); i++) {
                String temp = "";
                if (lst_estado_status_extrajudicial.get(i).getPermite_nombre().equals("SI")) {
                    temp = "1";
                } else {
                    temp = "0";
                }
                data.add(lst_estado_status_extrajudicial.get(i).getSestado_extra() + "," + lst_estado_status_extrajudicial.get(i).getStatus_extra() + "," + temp);
            }

            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.estadoStatusExtraPermite(
                    id_usuario,
                    data,
                    this.ambiente);

            this.constructor();

            RequestContext.getCurrentInstance().execute("PF('dtblWidgetPermite').clearFilters();");
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

    public estado_status_extrajudicial_List getSelected() {
        return selected;
    }

    public void setSelected(estado_status_extrajudicial_List selected) {
        this.selected = selected;
    }

    public List<estado_status_extrajudicial_List> getLst_estado_status_extrajudicial() {
        return lst_estado_status_extrajudicial;
    }

    public void setLst_estado_status_extrajudicial(List<estado_status_extrajudicial_List> lst_estado_status_extrajudicial) {
        this.lst_estado_status_extrajudicial = lst_estado_status_extrajudicial;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

}
