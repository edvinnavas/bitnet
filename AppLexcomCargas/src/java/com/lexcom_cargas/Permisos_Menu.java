package com.lexcom_cargas;

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

@ManagedBean(name = "Permisos_Menu")
@ViewScoped
public class Permisos_Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String usuario;

    private String Usuarios;
    private String Permisos_Usuarios;
    private String Constantes;
    private List<SelectItem> lst_user;
    private int user;
    private ArrayList<Object> lst_menu_disponibles;
    private ArrayList<Object> lst_menu_asignados;
    private DualListModel<Object> lst_usuario_permisos;
    
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            
            Driver drive = new Driver();
            String lista_user_sql = "select u.usuario, u.nombre from usuario u order by u.nombre";
            this.lst_user = drive.lista_SelectItem_simple(lista_user_sql);
            if(this.lst_user.size() > 0) {
                this.user = Integer.parseInt(this.lst_user.get(0).getValue().toString());
            } else {
                this.user = 0;
            }
            
            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
            Servicio servicio = new Servicio();
            java.util.List<ws.lexcomreportes.StringArray> resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_disponibles = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }
            
            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_asignados = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }
            
            this.lst_usuario_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            Driver drive = new Driver();
            String lista_user_sql = "select u.usuario, u.nombre from usuario u order by u.nombre";
            this.lst_user = drive.lista_SelectItem_simple(lista_user_sql);
            if(this.lst_user.size() > 0) {
                this.user = Integer.parseInt(this.lst_user.get(0).getValue().toString());
            } else {
                this.user = 0;
            }
            
            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
            Servicio servicio = new Servicio();
            java.util.List<ws.lexcomreportes.StringArray> resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_disponibles = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }
            
            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_asignados = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }
            
            this.lst_usuario_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambia_usuario() {
        try {
            String cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='NO' "
                    + "order by "
                    + "m.nombre";
            Servicio servicio = new Servicio();
            java.util.List<ws.lexcomreportes.StringArray> resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_disponibles = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_disponibles.add(vector_result[i][0]);
            }
            
            cadenasql = "select "
                    + "m.nombre "
                    + "from "
                    + "permiso_usuario_uno p "
                    + "left join menu m on (p.menu=m.menu) "
                    + "where "
                    + "p.usuario=" + this.user + " and "
                    + "p.ver='SI' "
                    + "order by "
                    + "m.nombre";
            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql, "LEXCOMJNDI");
            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }
            this.lst_menu_asignados = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                lst_menu_asignados.add(vector_result[i][0]);
            }
            
            this.lst_usuario_permisos = new DualListModel<>(this.lst_menu_disponibles, this.lst_menu_asignados);
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void guardar() {
        String resultado = "";
        
        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'");
     //       resultado = servicio.permisosUsuarioUnoModificar(id_usuario, this.user,this.lst_usuario_permisos.getSource() ,this.lst_usuario_permisos.getTarget());
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

    public List<SelectItem> getLst_user() {
        return lst_user;
    }

    public void setLst_user(List<SelectItem> lst_user) {
        this.lst_user = lst_user;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public ArrayList<Object> getLst_menu_disponibles() {
        return lst_menu_disponibles;
    }
    
}
