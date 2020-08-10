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

@ManagedBean(name = "Juicio_Medida_Etapa")
@ViewScoped
public class Juicio_Medida_Etapa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    private Juicio_Medida_Etapa_List selectedJuicio_Medida_Etapa;
    private List<Juicio_Medida_Etapa_List> lst_juicio_medida_etapa;
    
    //Variables para registrar la información del usuario.
    public String nombre_d;
    public String descripcion_d;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean txtNombre;
    private Boolean itaDescripcion;
    private Boolean btnAceptar;
    private Boolean btnCancelar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            
            String cadenasql = "select " +
                    "j.juicio_medida_etapa, " +
                    "j.nombre, " +
                    "j.estado " + 
                    "from " +
                    "juicio_medida_etapa j";
            
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
            
            lst_juicio_medida_etapa = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Juicio_Medida_Etapa_List juz = new Juicio_Medida_Etapa_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2]);
                lst_juicio_medida_etapa.add(juz);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            String cadenasql = "select " +
                    "j.juicio_medida_etapa, " +
                    "j.nombre, " +
                    "j.estado " + 
                    "from " +
                    "juicio_medida_etapa j";
            
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
            
            lst_juicio_medida_etapa = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Juicio_Medida_Etapa_List juz = new Juicio_Medida_Etapa_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2]);
                lst_juicio_medida_etapa.add(juz);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        this.nombre_d = "";
        this.descripcion_d = "";
        
        this.txtNombre = false;
        this.itaDescripcion = false;
        this.btnAceptar = false;
        this.btnCancelar = false;
            
        this.opcion = "INSERTAR";
        
        RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
    }
    
    public void carga_info_modificar() {
        if(this.selectedJuicio_Medida_Etapa != null) {
            String cadenasql = "select "
                    + "j.nombre, "
                    + "j.descripcion "
                    + "from "
                    + "juicio_medida_etapa j "
                    + "where "
                    + "j.juicio_medida_etapa = " + this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa();
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
                this.descripcion_d = vector_result[i][1];
            }
            
            this.txtNombre = false;
            this.itaDescripcion = false;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "MODIFICAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un juicio_medida_etapa del listado."));
        }
    }
    
    public void carga_info_eliminar() {
        if (this.selectedJuicio_Medida_Etapa != null) {
            String cadenasql = "select "
                    + "j.nombre, "
                    + "j.descripcion "
                    + "from "
                    + "juicio_medida_etapa j "
                    + "where "
                    + "j.juicio_medida_etapa = " + this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa();
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
                this.descripcion_d = vector_result[i][1];
            }

            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = false;
            this.btnCancelar = false;

            this.opcion = "ELIMINAR";

            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un juicio_medida_etapa del listado."));
        }
    }
    
    public void carga_info_activar() {
        if(this.selectedJuicio_Medida_Etapa != null) {
            String cadenasql = "select "
                    + "j.nombre, "
                    + "j.descripcion "
                    + "from "
                    + "juicio_medida_etapa j "
                    + "where "
                    + "j.juicio_medida_etapa = " + this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa();
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
                this.descripcion_d = vector_result[i][1];
            }
            
            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ACTIVAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un juicio_medida_etapa del listado."));
        }
    }
    
    public void carga_info_ver() {
        if(this.selectedJuicio_Medida_Etapa != null) {
            String cadenasql = "select "
                    + "j.nombre, "
                    + "j.descripcion "
                    + "from "
                    + "juicio_medida_etapa j "
                    + "where "
                    + "j.juicio_medida_etapa = " + this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa();
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
                this.descripcion_d = vector_result[i][1];
            }
            
            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = true;
            this.btnCancelar = false;
            
            this.opcion = "VER";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un juicio_medida_etapa del listado."));
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
                resultado = servicio.juicioMedidaEtapaInsertar(
                        id_usuario,
                        this.nombre_d,
                        this.descripcion_d, 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetJuicioMedidaEtapa').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del juicio_medida_etapa."));
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
                resultado = servicio.juicioMedidaEtapaModificar(
                        id_usuario,
                        this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa(),
                        this.nombre_d,
                        this.descripcion_d, 
                        this.ambiente);
                this.constructor();
                RequestContext.getCurrentInstance().execute("PF('dtblWidgetJuicioMedidaEtapa').clearFilters();");

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del juicio_medida_etapa."));
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
            resultado = servicio.juicioMedidaEtapaEliminar(
                    id_usuario,
                    this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetJuicioMedidaEtapa').clearFilters();");

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
            resultado = servicio.juicioMedidaEtapaActivar(
                    id_usuario,
                    this.selectedJuicio_Medida_Etapa.getJuicio_medida_etapa(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetJuicioMedidaEtapa').clearFilters();");

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

    public Juicio_Medida_Etapa_List getSelectedJuicio_Medida_Etapa() {
        return selectedJuicio_Medida_Etapa;
    }

    public void setSelectedJuicio_Medida_Etapa(Juicio_Medida_Etapa_List selectedJuicio_Medida_Etapa) {
        this.selectedJuicio_Medida_Etapa = selectedJuicio_Medida_Etapa;
    }

    public List<Juicio_Medida_Etapa_List> getLst_juicio_medida_etapa() {
        return lst_juicio_medida_etapa;
    }

    public void setLst_juicio_medida_etapa(List<Juicio_Medida_Etapa_List> lst_juicio_medida_etapa) {
        this.lst_juicio_medida_etapa = lst_juicio_medida_etapa;
    }

    public String getNombre_d() {
        return nombre_d;
    }

    public void setNombre_d(String nombre_d) {
        this.nombre_d = nombre_d;
    }

    public String getDescripcion_d() {
        return descripcion_d;
    }

    public void setDescripcion_d(String descripcion_d) {
        this.descripcion_d = descripcion_d;
    }

    public Boolean getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(Boolean txtNombre) {
        this.txtNombre = txtNombre;
    }

    public Boolean getItaDescripcion() {
        return itaDescripcion;
    }

    public void setItaDescripcion(Boolean itaDescripcion) {
        this.itaDescripcion = itaDescripcion;
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

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
