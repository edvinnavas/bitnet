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

@ManagedBean(name = "Constantes")
@ViewScoped
public class Constantes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;

    private List<Constantes_List> lst_constantes;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            String cadenasql = "select "
                    + "c.constantes as constantes, "
                    + "c.nombre as nombre, "
                    + "c.valor as valor "
                    + "from "
                    + "constantes c";
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
            
            this.lst_constantes = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Constantes_List con = new Constantes_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2]);
                this.lst_constantes.add(con);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            String cadenasql = "select "
                    + "c.constantes as constantes, "
                    + "c.nombre as nombre, "
                    + "c.valor as valor "
                    + "from "
                    + "constantes c";
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
            
            this.lst_constantes = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Constantes_List con = new Constantes_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2]);
                this.lst_constantes.add(con);
            }
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar() {
        String resultado = "";
        
        try {
            List<String> lista_constantes = new ArrayList<>();
            for(Integer i=0; i < this.lst_constantes.size(); i++) {
                lista_constantes.add(this.lst_constantes.get(i).getConstantes().toString() + "," + this.lst_constantes.get(i).getValor());
            }
            
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.constantesModificar(id_usuario, lista_constantes, 
                    this.ambiente);
            this.constructor();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
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

    public List<Constantes_List> getLst_constantes() {
        return lst_constantes;
    }

    public void setLst_constantes(List<Constantes_List> lst_constantes) {
        this.lst_constantes = lst_constantes;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
