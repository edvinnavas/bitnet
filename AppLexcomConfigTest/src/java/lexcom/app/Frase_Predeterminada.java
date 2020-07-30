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

@ManagedBean(name = "Frase_Predeterminada")
@ViewScoped
public class Frase_Predeterminada implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    private Frase_Predeterminada_List selectedFrase_Predeterminada;
    private List<Frase_Predeterminada_List> lst_frase_predeterminada;
    private List<SelectItem> lst_tipo = new ArrayList<>();
    
    //Variables para registrar la información del usuario.
    public String nombre_d;
    public String tipo_d;
    public String frase_d;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean txtNombre;
    private Boolean somTipo;
    private Boolean itaFrase;
    private Boolean btnAceptar;
    private Boolean btnCancelar;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
         
            lst_tipo = new ArrayList<>();
            lst_tipo.add(new SelectItem("CONVENIO", "CONVENIO"));
            lst_tipo.add(new SelectItem("SITUACION CASO", "SITUACION CASO"));
            lst_tipo.add(new SelectItem("PROCURACION", "PROCURACION"));
            lst_tipo.add(new SelectItem("GESTION", "GESTION"));
            
            String cadenasql = "select "
                    + "f.frase_predeterminada as frase_predeterminada, "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase, "
                    + "f.estado as estado "
                    + "from "
                    + "frase_predeterminada f";
            
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
            
            lst_frase_predeterminada = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Frase_Predeterminada_List frp = new Frase_Predeterminada_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4]);
                lst_frase_predeterminada.add(frp);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            lst_tipo = new ArrayList<>();
            lst_tipo.add(new SelectItem("CONVENIO", "CONVENIO"));
            lst_tipo.add(new SelectItem("SITUACION CASO", "SITUACION CASO"));
            lst_tipo.add(new SelectItem("PROCURACION", "PROCURACION"));
            lst_tipo.add(new SelectItem("GESTION", "GESTION"));
            
            String cadenasql = "select "
                    + "f.frase_predeterminada as frase_predeterminada, "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase, "
                    + "f.estado as estado "
                    + "from "
                    + "frase_predeterminada f";
            
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
            
            lst_frase_predeterminada = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Frase_Predeterminada_List frp = new Frase_Predeterminada_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4]);
                lst_frase_predeterminada.add(frp);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        this.nombre_d = "";
        this.tipo_d = "";
        this.frase_d = "";

        //Habilitar los campos del formulario Usuario. 
        this.txtNombre = false;
        this.somTipo = false;
        this.itaFrase = false;
        this.btnAceptar = false;
        this.btnCancelar = false;
            
        this.opcion = "INSERTAR";
        
        RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
    }
    
    public void carga_info_modificar() {
        if(this.selectedFrase_Predeterminada != null) {
            String cadenasql = "select "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase "
                    + "from "
                    + "frase_predeterminada f "
                    + "where "
                    + "f.frase_predeterminada = " + this.selectedFrase_Predeterminada.getFrase_predeterminada();
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
                this.tipo_d = vector_result[i][1];
                this.frase_d = vector_result[i][2];
            }
            
            this.txtNombre = false;
            this.somTipo = false;
            this.itaFrase = false;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "MODIFICAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar una frase predeterminada del listado."));
        }
    }
    
    public void carga_info_eliminar() {
        if(this.selectedFrase_Predeterminada != null) {
            String cadenasql = "select "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase "
                    + "from "
                    + "frase_predeterminada f "
                    + "where "
                    + "f.frase_predeterminada = " + this.selectedFrase_Predeterminada.getFrase_predeterminada();
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
                this.tipo_d = vector_result[i][1];
                this.frase_d = vector_result[i][2];
            }
            
            this.txtNombre = true;
            this.somTipo = true;
            this.itaFrase = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ELIMINAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar una frase predeterminada del listado."));
        }
    }
    
    public void carga_info_activar() {
        if(this.selectedFrase_Predeterminada != null) {
            String cadenasql = "select "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase "
                    + "from "
                    + "frase_predeterminada f "
                    + "where "
                    + "f.frase_predeterminada = " + this.selectedFrase_Predeterminada.getFrase_predeterminada();
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
                this.tipo_d = vector_result[i][1];
                this.frase_d = vector_result[i][2];
            }
            
            this.txtNombre = true;
            this.somTipo = true;
            this.itaFrase = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ACTIVAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar una frase predeterminada del listado."));
        }
    }
    
    public void carga_info_ver() {
        if(this.selectedFrase_Predeterminada != null) {
            String cadenasql = "select "
                    + "f.nombre as nombre, "
                    + "f.tipo as tipo, "
                    + "f.frase as frase "
                    + "from "
                    + "frase_predeterminada f "
                    + "where "
                    + "f.frase_predeterminada = " + this.selectedFrase_Predeterminada.getFrase_predeterminada();
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
                this.tipo_d = vector_result[i][1];
                this.frase_d = vector_result[i][2];
            }
            
            this.txtNombre = true;
            this.somTipo = true;
            this.itaFrase = true;
            this.btnAceptar = true;
            this.btnCancelar = false;
            
            this.opcion = "VER";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar una frase predeterminada del listado."));
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
                if (!this.frase_d.equals("")) {
                    Driver driver = new Driver();
                    Servicio servicio = new Servicio();
                    Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                    resultado = servicio.frasePredeterminadaInsertar(
                            id_usuario,
                            this.nombre_d,
                            this.tipo_d,
                            this.frase_d, 
                            this.ambiente);
                    this.constructor();
                    RequestContext.getCurrentInstance().execute("PF('dtblWidgetFrp').clearFilters();");

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar la frase predeterminada."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre de la frase predeterminada."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void modificar() {
        String resultado = "";

        try {
            if (!this.nombre_d.equals("")) {
                if (!this.frase_d.equals("")) {
                    Driver driver = new Driver();
                    Servicio servicio = new Servicio();
                    Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                    resultado = servicio.frasePredeterminadaModificar(
                            id_usuario,
                            this.selectedFrase_Predeterminada.getFrase_predeterminada(),
                            this.nombre_d,
                            this.tipo_d,
                            this.frase_d, 
                            this.ambiente);
                    this.constructor();
                    RequestContext.getCurrentInstance().execute("PF('dtblWidgetFrp').clearFilters();");

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar la frase predeterminada."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre de la frase predeterminada."));
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
            resultado = servicio.frasePredeterminadaEliminar(
                    id_usuario,
                    this.selectedFrase_Predeterminada.getFrase_predeterminada(), 
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetFrp').clearFilters();");

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
            resultado = servicio.frasePredeterminadaActivar(
                    id_usuario,
                    this.selectedFrase_Predeterminada.getFrase_predeterminada(), 
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetFrp').clearFilters();");

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

    public Frase_Predeterminada_List getSelectedFrase_Predeterminada() {
        return selectedFrase_Predeterminada;
    }

    public void setSelectedFrase_Predeterminada(Frase_Predeterminada_List selectedFrase_Predeterminada) {
        this.selectedFrase_Predeterminada = selectedFrase_Predeterminada;
    }

    public List<Frase_Predeterminada_List> getLst_frase_predeterminada() {
        return lst_frase_predeterminada;
    }

    public void setLst_frase_predeterminada(List<Frase_Predeterminada_List> lst_frase_predeterminada) {
        this.lst_frase_predeterminada = lst_frase_predeterminada;
    }

    public String getNombre_d() {
        return nombre_d;
    }

    public void setNombre_d(String nombre_d) {
        this.nombre_d = nombre_d;
    }

    public String getTipo_d() {
        return tipo_d;
    }

    public void setTipo_d(String tipo_d) {
        this.tipo_d = tipo_d;
    }

    public String getFrase_d() {
        return frase_d;
    }

    public void setFrase_d(String frase_d) {
        this.frase_d = frase_d;
    }

    public Boolean getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(Boolean txtNombre) {
        this.txtNombre = txtNombre;
    }

    public Boolean getSomTipo() {
        return somTipo;
    }

    public void setSomTipo(Boolean somTipo) {
        this.somTipo = somTipo;
    }

    public Boolean getItaFrase() {
        return itaFrase;
    }

    public void setItaFrase(Boolean itaFrase) {
        this.itaFrase = itaFrase;
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

    public List<SelectItem> getLst_tipo() {
        return lst_tipo;
    }

    public void setLst_tipo(List<SelectItem> lst_tipo) {
        this.lst_tipo = lst_tipo;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
