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

@ManagedBean(name = "Permisos_Rol")
@ViewScoped
public class Permisos_Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private List<SelectItem> lst_Rol;
    private Integer rol;

    private List<String> lst_menu_disponibles;
    private List<String> lst_menu_asignados;

    private DualListModel<String> lst_Rol_permisos;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            Driver drive = new Driver();
            String lista_sql = "select rol,nombre from usuario u order by nombre";
            this.lst_Rol = drive.lista_SelectItem_simple(lista_sql, this.ambiente);
            if (this.lst_Rol.size() > 0) {
                this.rol = Integer.parseInt(this.lst_Rol.get(0).getValue().toString());
            } else {
                this.rol = 0;
            }

            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_disponibles = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }

            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_asignados = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }

            this.lst_Rol_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            Driver drive = new Driver();
            String lista_user_sql = "select rol, nombre from rol u order by nombre";
            this.lst_Rol = drive.lista_SelectItem_simple(lista_user_sql, this.ambiente);
            if (this.lst_Rol.size() > 0) {
                this.rol = Integer.parseInt(this.lst_Rol.get(0).getValue().toString());
            } else {
                this.rol = 0;
            }

            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_disponibles = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }

            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_asignados = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }

            this.lst_Rol_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cambia_usuario() {
        try {
            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_disponibles = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }

            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "rol_menu p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.rol=" + this.rol + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
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
            this.lst_menu_asignados = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }

            this.lst_Rol_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);

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
            resultado = servicio.permisosRolModificar(id_usuario, this.rol, this.lst_Rol_permisos.getSource(), this.lst_Rol_permisos.getTarget(), this.ambiente);
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

    public List<SelectItem> getLst_Rol() {
        return lst_Rol;
    }

    public void setLst_user(List<SelectItem> lst_Rol) {
        this.lst_Rol = lst_Rol;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public List<String> getLst_menu_disponibles() {
        return lst_menu_disponibles;
    }

    public void setLst_menu_disponibles(List<String> lst_menu_disponibles) {
        this.lst_menu_disponibles = lst_menu_disponibles;
    }

    public List<String> getLst_menu_asignados() {
        return lst_menu_asignados;
    }

    public void setLst_menu_asignados(List<String> lst_menu_asignados) {
        this.lst_menu_asignados = lst_menu_asignados;
    }

    public DualListModel<String> getLst_Rol_permisos() {
        return lst_Rol_permisos;
    }

    public void setLst_Rol_permisos(DualListModel<String> lst_Rol_permisos) {
        this.lst_Rol_permisos = lst_Rol_permisos;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

}
