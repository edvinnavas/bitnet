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
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Actor")
@ViewScoped
public class Actor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    private Actor_List selectedActor;
    private List<Actor_List> lst_actor;
    private List<SelectItem> lst_corporacion = new ArrayList<>();
    
    //Variables para registrar la información del usuario.
    public String nombre_d;
    public String nit_d;
    public String telefono_d;
    public String descripcion_d;
    public String cooperacion_d;
    public String digitalizados_d;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean txtNombre;
    private Boolean txtNit;
    private Boolean txtTelefono;
    private Boolean itaDescripcion;
    private Boolean somCorporacion;
    private Boolean txtdigitalizados;
    private Boolean btnAceptar;
    private Boolean btnCancelar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            Driver drive = new Driver();
            String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
            this.lst_corporacion = drive.lista_SelectItem_simple(lista_corporacion_sql, this.ambiente);
         
            String cadenasql = "select "
                    + "a.actor as actor, "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.estado as estado, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as path_digital "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion)";
            
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
            
            lst_actor = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Actor_List act = new Actor_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                lst_actor.add(act);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            Driver drive = new Driver();
            String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
            this.lst_corporacion = drive.lista_SelectItem_simple(lista_corporacion_sql, this.ambiente);
            
            String cadenasql = "select "
                    + "a.actor as actor, "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.estado as estado, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as path_digital "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion)";
            
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
            
            lst_actor = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Actor_List act = new Actor_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6]);
                lst_actor.add(act);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        this.nombre_d = "";
        this.nit_d = "";
        this.telefono_d = "";
        this.descripcion_d = "";
        this.cooperacion_d = "";
        this.digitalizados_d = "";

        //Habilitar los campos del formulario Usuario. 
        this.txtNombre = false;
        this.txtNit = false;
        this.txtTelefono = false;
        this.itaDescripcion = false;
        this.somCorporacion = false;
        this.txtdigitalizados = false;
        this.btnAceptar = false;
        this.btnCancelar = false;
            
        this.opcion = "INSERTAR";
        
        RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
    }
    
    public void carga_info_modificar() {
        if(this.selectedActor != null) {
            String cadenasql = "select "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.descripcion as descripcion, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as digitalizados "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion) "
                    + "where "
                    + "a.actor = " + this.selectedActor.getActor();
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
            
            for (Integer i = 1; i < filas; i++) {
                this.nombre_d = vector_result[i][0];
                this.nit_d = vector_result[i][1];
                this.telefono_d = vector_result[i][2];
                this.descripcion_d = vector_result[i][3];
                this.cooperacion_d = vector_result[i][4];
                this.digitalizados_d = vector_result[i][5];
            }
            
            this.txtNombre = false;
            this.txtNit = false;
            this.txtTelefono = false;
            this.itaDescripcion = false;
            this.somCorporacion = false;
            this.txtdigitalizados = false;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "MODIFICAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un actor del listado."));
        }
    }
    
    public void carga_info_eliminar() {
        if(this.selectedActor != null) {
            String cadenasql = "select "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.descripcion as descripcion, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as digitalizados "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion) "
                    + "where "
                    + "a.actor = " + this.selectedActor.getActor();
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
            
            for (Integer i = 1; i < filas; i++) {
                this.nombre_d = vector_result[i][0];
                this.nit_d = vector_result[i][1];
                this.telefono_d = vector_result[i][2];
                this.descripcion_d = vector_result[i][3];
                this.cooperacion_d = vector_result[i][4];
                this.digitalizados_d = vector_result[i][5];
            }
            
            this.txtNombre = true;
            this.txtNit = true;
            this.txtTelefono = true;
            this.itaDescripcion = true;
            this.somCorporacion = true;
            this.txtdigitalizados = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ELIMINAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un actor del listado."));
        }
    }
    
    public void carga_info_activar() {
        if(this.selectedActor != null) {
            String cadenasql = "select "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.descripcion as descripcion, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as digitalizados "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion) "
                    + "where "
                    + "a.actor = " + this.selectedActor.getActor();
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
            
            for (Integer i = 1; i < filas; i++) {
                this.nombre_d = vector_result[i][0];
                this.nit_d = vector_result[i][1];
                this.telefono_d = vector_result[i][2];
                this.descripcion_d = vector_result[i][3];
                this.cooperacion_d = vector_result[i][4];
                this.digitalizados_d = vector_result[i][5];
            }
            
            this.txtNombre = true;
            this.txtNit = true;
            this.txtTelefono = true;
            this.itaDescripcion = true;
            this.somCorporacion = true;
            this.txtdigitalizados = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ACTIVAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un actor del listado."));
        }
    }
    
    public void carga_info_ver() {
        if(this.selectedActor != null) {
            String cadenasql = "select "
                    + "a.nombre as nombre, "
                    + "a.nit as nit, "
                    + "a.telefono as telefono, "
                    + "a.descripcion as descripcion, "
                    + "c.nombre as corporacion, "
                    + "a.digitalizados as digitalizados "
                    + "from "
                    + "actor a left join cooperacion c on (a.cooperacion = c.cooperacion) "
                    + "where "
                    + "a.actor = " + this.selectedActor.getActor();
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
            
            for (Integer i = 1; i < filas; i++) {
                this.nombre_d = vector_result[i][0];
                this.nit_d = vector_result[i][1];
                this.telefono_d = vector_result[i][2];
                this.descripcion_d = vector_result[i][3];
                this.cooperacion_d = vector_result[i][4];
                this.digitalizados_d = vector_result[i][5];
            }
            
            this.txtNombre = true;
            this.txtNit = true;
            this.txtTelefono = true;
            this.itaDescripcion = true;
            this.somCorporacion = true;
            this.txtdigitalizados = true;
            this.btnAceptar = true;
            this.btnCancelar = false;
            
            this.opcion = "VER";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un actor del listado."));
        }
    }
    
    public void aceptar() {
        if(this.opcion.equals("INSERTAR")) {
            this.insertar();
        }
        if(this.opcion.equals("MODIFICAR")) {
            this.modificar();
        }
        if(this.opcion.equals("ELIMINAR")) {
            this.eliminar();
        }
        if(this.opcion.equals("ACTIVAR")) {
            this.activar();
        }
    }
    
    private void insertar() {
        String resultado = "";

        try {
            if (!this.nombre_d.equals("")) {
                Driver driver = new Driver();
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                resultado = servicio.actorInsertar(
                        id_usuario,
                        this.nombre_d,
                        this.nit_d,
                        this.telefono_d,
                        this.descripcion_d,
                        this.cooperacion_d,
                        this.digitalizados_d, 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetAct').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del actor."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void modificar() {
        String resultado = "";

        try {
            if (!this.nombre_d.equals("")) {
                Driver driver = new Driver();
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                resultado = servicio.actorModificar(
                        id_usuario,
                        this.selectedActor.getActor(),
                        this.nombre_d,
                        this.nit_d,
                        this.telefono_d,
                        this.descripcion_d,
                        this.cooperacion_d,
                        this.digitalizados_d, 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetAct').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del actor."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void eliminar() {
        String resultado = "";

        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.actorEliminar(
                    id_usuario,
                    this.selectedActor.getActor(), 
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetAct').clearFilters();");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void activar() {
        String resultado = "";

        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            resultado = servicio.actorActivar(
                    id_usuario,
                    this.selectedActor.getActor(), 
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetAct').clearFilters();");

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

    public Actor_List getSelectedActor() {
        return selectedActor;
    }

    public void setSelectedActor(Actor_List selectedActor) {
        this.selectedActor = selectedActor;
    }

    public List<Actor_List> getLst_actor() {
        return lst_actor;
    }

    public void setLst_actor(List<Actor_List> lst_actor) {
        this.lst_actor = lst_actor;
    }

    public String getNombre_d() {
        return nombre_d;
    }

    public void setNombre_d(String nombre_d) {
        this.nombre_d = nombre_d;
    }

    public String getNit_d() {
        return nit_d;
    }

    public void setNit_d(String nit_d) {
        this.nit_d = nit_d;
    }

    public Boolean getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(Boolean txtTelefono) {
        this.txtTelefono = txtTelefono;
    }
    
    public String getTelefono_d() {
        return telefono_d;
    }

    public void setTelefono_d(String telefono_d) {
        this.telefono_d = telefono_d;
    }

    public String getDescripcion_d() {
        return descripcion_d;
    }

    public void setDescripcion_d(String descripcion_d) {
        this.descripcion_d = descripcion_d;
    }

    public String getCooperacion_d() {
        return cooperacion_d;
    }

    public void setCooperacion_d(String cooperacion_d) {
        this.cooperacion_d = cooperacion_d;
    }

    public String getDigitalizados_d() {
        return digitalizados_d;
    }

    public void setDigitalizados_d(String digitalizados_d) {
        this.digitalizados_d = digitalizados_d;
    }

    public Boolean getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(Boolean txtNombre) {
        this.txtNombre = txtNombre;
    }

    public Boolean getTxtNit() {
        return txtNit;
    }

    public void setTxtNit(Boolean txtNit) {
        this.txtNit = txtNit;
    }

    public Boolean getItaDescripcion() {
        return itaDescripcion;
    }

    public void setItaDescripcion(Boolean itaDescripcion) {
        this.itaDescripcion = itaDescripcion;
    }

    public Boolean getSomCorporacion() {
        return somCorporacion;
    }

    public void setSomCorporacion(Boolean somCorporacion) {
        this.somCorporacion = somCorporacion;
    }

    public Boolean getTxtdigitalizados() {
        return txtdigitalizados;
    }

    public void setTxtdigitalizados(Boolean txtdigitalizados) {
        this.txtdigitalizados = txtdigitalizados;
    }

    public Boolean getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(Boolean btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public Boolean getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Boolean btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public List<SelectItem> getLst_corporacion() {
        return lst_corporacion;
    }

    public void setLst_corporacion(List<SelectItem> lst_corporacion) {
        this.lst_corporacion = lst_corporacion;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
