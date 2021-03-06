package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Busqueda")
@ViewScoped
public class Expediente_Busqueda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    
    private List<Expediente_List> lst_expediente;
    private Expediente_List sel_expediente;
    
    private Integer deudor;
    private String Directorio = "";
    private String patron;
    private String titulo_panel;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.titulo_panel = "Busqueda de expedientes - USUARIO: " + this.usuario;
            this.patron = "";
            this.lst_expediente = new ArrayList<>();
            this.sel_expediente = null;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Bienvenido " + this.usuario + " al sistema Lexcom."));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Busqueda(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void buscar() {
        try {
            String cadenasql = "select "
                    + "d.deudor id_deudor, "
                    + "d.cargado cargado, "
                    + "a.nombre actor, "
                    + "ga.nombre garantia, "
                    + "u.nombre gestor, "
                    + "d.caso caso, "
                    + "d.no_cuenta no_cuenta, "
                    + "d.nombre nombre_deudor, "
                    + "d.monto_inicial monto_inicial, "
                    + "j.no_juicio no_juicio, "
                    + "se.nombre estado_extrajudicial, "
                    + "ee.nombre status_extrajudicial, "
                    + "sj.nombre sestado_judicial, "
                    + "ej.nombre status_judicial, "
                    + "d.no_cuenta_otro otro_no_cuenta "
                    + "from "
                    + "deudor d "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join sestado sj on (d.sestado=sj.sestado) "
                    + "left join estatus ej on (d.estatus=ej.estatus) "
                    + "left join sestado_extra se on (d.sestado_extra=se.sestado_extra) "
                    + "left join estatus_extra ee on (d.estatus_extra=ee.estatus_extra) "
                    + "left join garantia ga on (d.garantia=ga.garantia) "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "where "
                    + "d.cargado like '%" + this.patron + "%' or "
                    + "a.nombre like '%" + this.patron + "%' or "
                    + "ga.nombre like '%" + this.patron + "%' or "
                    + "u.nombre like '%" + this.patron + "%' or "
                    + "d.caso like '%" + this.patron + "%' or "
                    + "d.no_cuenta like '%" + this.patron + "%' or "
                    + "d.nombre like '%" + this.patron + "%' or "
                    + "d.monto_inicial like '%" + this.patron + "%' or "
                    + "j.no_juicio like '%" + this.patron + "%' or "
                    + "se.nombre like '%" + this.patron + "%' or "
                    + "ee.nombre like '%" + this.patron + "%' or "
                    + "sj.nombre like '%" + this.patron + "%' or "
                    + "ej.nombre like '%" + this.patron + "%' or "
                    + "d.no_cuenta_otro like '%" + this.patron + "%'";

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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.lst_expediente = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Expediente_List exp = new Expediente_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        Double.parseDouble(vector_result[i][8]),
                        vector_result[i][9],
                        vector_result[i][10],
                        vector_result[i][11],
                        vector_result[i][12],
                        vector_result[i][13],
                        vector_result[i][14]);
                this.lst_expediente.add(exp);
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Busqueda(buscar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void limpiar() {
        try {
            this.patron = "";
            this.lst_expediente = new ArrayList<>();
            this.sel_expediente = null;
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Busqueda(limpiar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void Cargar_Carpeta_Digitalizacion() {
        try {
            if(this.sel_expediente != null) {
                if (this.deudor != null) {
                    Driver driver = new Driver();
                    Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                    if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                        Directorio = "http://192.168.2.1:" + "//" + driver.dar_path_actor(this.sel_expediente.getId_deudor(), this.ambiente) + "//" + driver.dar_caso_deudor(this.deudor, this.ambiente);
                        //FacesContext.getCurrentInstance().getExternalContext().redirect("_blank");
                        // FacesContext.getCurrentInstance().getExternalContext().redirect();
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.execute("window.open('" + Directorio + "', '_newtab')");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Busqueda(Cargar_Carpeta_Digitalizacion): " + ex.toString());
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

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public String getDirectorio() {
        return Directorio;
    }

    public void setDirectorio(String Directorio) {
        this.Directorio = Directorio;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public List<Expediente_List> getLst_expediente() {
        return lst_expediente;
    }

    public void setLst_expediente(List<Expediente_List> lst_expediente) {
        this.lst_expediente = lst_expediente;
    }

    public Expediente_List getSel_expediente() {
        return sel_expediente;
    }

    public void setSel_expediente(Expediente_List sel_expediente) {
        this.sel_expediente = sel_expediente;
    }

    public String getTitulo_panel() {
        return titulo_panel;
    }

    public void setTitulo_panel(String titulo_panel) {
        this.titulo_panel = titulo_panel;
    }
    
}
