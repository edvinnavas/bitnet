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

@ManagedBean(name = "Codigo_Contactabilidad")
@ViewScoped
public class Codigo_Contactabilidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private String ambiente;
    private String opcion;
    
    private Codigo_Contactabilidad_List selectedCodigo_Contactabilidad;
    private List<Codigo_Contactabilidad_List> lst_codigo_contactabilidad;
    
    //Variables para registrar la información del usuario.
    public String codigo_d;
    public String nombre_d;
    public String descripcion_d;
    
    //Habilitar los campos del formulario Usuario. 
    private Boolean txtCodigo;
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
         
            String cadenasql = "select "
                    + "c.codigo_contactabilidad as codigo_contactabilidad, "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.estado as estado "
                    + "from "
                    + "codigo_contactabilidad c";
            
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
            
            lst_codigo_contactabilidad = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Codigo_Contactabilidad_List coc = new Codigo_Contactabilidad_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3]);
                lst_codigo_contactabilidad.add(coc);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void constructor() {
        try {
            String cadenasql = "select "
                    + "c.codigo_contactabilidad as codigo_contactabilidad, "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.estado as estado "
                    + "from "
                    + "codigo_contactabilidad c";
            
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
            
            lst_codigo_contactabilidad = new ArrayList<>();
            for(Integer i = 1; i < filas; i++) {
                Codigo_Contactabilidad_List coc = new Codigo_Contactabilidad_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3]);
                lst_codigo_contactabilidad.add(coc);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        this.codigo_d = "";
        this.nombre_d = "";
        this.descripcion_d = "";

        //Habilitar los campos del formulario Usuario. 
        this.txtCodigo = false;
        this.txtNombre = false;
        this.itaDescripcion = false;
        this.btnAceptar = false;
        this.btnCancelar = false;
            
        this.opcion = "INSERTAR";
        
        RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
    }
    
    public void carga_info_modificar() {
        if(this.selectedCodigo_Contactabilidad != null) {
            String cadenasql = "select "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.descripcion as descripcion "
                    + "from "
                    + "codigo_contactabilidad c "
                    + "where "
                    + "c.codigo_contactabilidad = " + this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad();
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
                this.codigo_d = vector_result[i][0];
                this.nombre_d = vector_result[i][1];
                this.descripcion_d = vector_result[i][2];
            }
            
            this.txtCodigo = false;
            this.txtNombre = false;
            this.itaDescripcion = false;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "MODIFICAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un codigo de resultado del listado."));
        }
    }
    
    public void carga_info_eliminar() {
        if(this.selectedCodigo_Contactabilidad != null) {
            String cadenasql = "select "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.descripcion as descripcion "
                    + "from "
                    + "codigo_contactabilidad c "
                    + "where "
                    + "c.codigo_contactabilidad = " + this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad();
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
                this.codigo_d = vector_result[i][0];
                this.nombre_d = vector_result[i][1];
                this.descripcion_d = vector_result[i][2];
            }
            
            this.txtCodigo = true;
            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ELIMINAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un codigo de resultado del listado."));
        }
    }
    
    public void carga_info_activar() {
        if(this.selectedCodigo_Contactabilidad != null) {
            String cadenasql = "select "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.descripcion as descripcion "
                    + "from "
                    + "codigo_contactabilidad c "
                    + "where "
                    + "c.codigo_contactabilidad = " + this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad();
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
                this.codigo_d = vector_result[i][0];
                this.nombre_d = vector_result[i][1];
                this.descripcion_d = vector_result[i][2];
            }
            
            this.txtCodigo = true;
            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = false;
            this.btnCancelar = false;
            
            this.opcion = "ACTIVAR";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un codigo de resultado del listado."));
        }
    }
    
    public void carga_info_ver() {
        if(this.selectedCodigo_Contactabilidad != null) {
            String cadenasql = "select "
                    + "c.codigo as codigo, "
                    + "c.nombre as nombre, "
                    + "c.descripcion as descripcion "
                    + "from "
                    + "codigo_contactabilidad c "
                    + "where "
                    + "c.codigo_contactabilidad = " + this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad();
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
                this.codigo_d = vector_result[i][0];
                this.nombre_d = vector_result[i][1];
                this.descripcion_d = vector_result[i][2];
            }
            
            this.txtCodigo = true;
            this.txtNombre = true;
            this.itaDescripcion = true;
            this.btnAceptar = true;
            this.btnCancelar = false;
            
            this.opcion = "VER";
            
            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un codigo de resultado del listado."));
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
            if (!this.codigo_d.equals("")) {
                if (!this.nombre_d.equals("")) {
                    Driver driver = new Driver();
                    Servicio servicio = new Servicio();
                    Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                    resultado = servicio.codigoContactabilidadInsertar(
                            id_usuario,
                            this.codigo_d,
                            this.nombre_d,
                            this.descripcion_d,
                            this.ambiente);
                    this.constructor();
                    RequestContext.getCurrentInstance().execute("PF('dtblWidgetCoc').clearFilters();");

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del codigo de resultado."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el codigo del codigo de resultado."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    private void modificar() {
        String resultado = "";

        try {
            if (!this.codigo_d.equals("")) {
                if (!this.nombre_d.equals("")) {
                    Driver driver = new Driver();
                    Servicio servicio = new Servicio();
                    Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                    resultado = servicio.codigoContactabilidadModificar(
                            id_usuario,
                            this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad(),
                            this.codigo_d,
                            this.nombre_d,
                            this.descripcion_d, 
                            this.ambiente);
                    this.constructor();
                    RequestContext.getCurrentInstance().execute("PF('dtblWidgetCoc').clearFilters();");

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el nombre del codigo de resultado."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", "Debe ingresar el codigo del codigo de resultado."));
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
            resultado = servicio.codigoContactabilidadEliminar(
                    id_usuario,
                    this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetCoc').clearFilters();");

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
            resultado = servicio.codigoContactabilidadActivar(
                    id_usuario,
                    this.selectedCodigo_Contactabilidad.getCodigo_contactabilidad(), 
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetCoc').clearFilters();");

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

    public Codigo_Contactabilidad_List getSelectedCodigo_Contactabilidad() {
        return selectedCodigo_Contactabilidad;
    }

    public void setSelectedCodigo_Contactabilidad(Codigo_Contactabilidad_List selectedCodigo_Contactabilidad) {
        this.selectedCodigo_Contactabilidad = selectedCodigo_Contactabilidad;
    }

    public List<Codigo_Contactabilidad_List> getLst_codigo_contactabilidad() {
        return lst_codigo_contactabilidad;
    }

    public void setLst_codigo_contactabilidad(List<Codigo_Contactabilidad_List> lst_codigo_contactabilidad) {
        this.lst_codigo_contactabilidad = lst_codigo_contactabilidad;
    }

    public String getCodigo_d() {
        return codigo_d;
    }

    public void setCodigo_d(String codigo_d) {
        this.codigo_d = codigo_d;
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

    public Boolean getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(Boolean txtCodigo) {
        this.txtCodigo = txtCodigo;
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
