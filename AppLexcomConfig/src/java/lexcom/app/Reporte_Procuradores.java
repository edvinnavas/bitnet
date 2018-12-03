package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "Reporte_Procuradores")
@ViewScoped
public class Reporte_Procuradores implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private String procurador;
    private String garantia;
    private String status;
    private String estado;
    private String juzgado;
    
    private StreamedContent file;
    
    private List<SelectItem> Lista_procurador = new ArrayList<>();
    private List<SelectItem> Lista_garantia = new ArrayList<>();
    private List<SelectItem> Lista_status = new ArrayList<>();
    private List<SelectItem> Lista_estado = new ArrayList<>();
    private List<SelectItem> Lista_juzgado = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();
        
        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_procuradores.xls";
        
        String lista_procurador_sql = "SELECT u.nombre, u.nombre FROM usuario u where u.procurador='SI' and u.estado='VIGENTE'";
        this.Lista_procurador = drive.lista_SelectItem(lista_procurador_sql, this.ambiente);
        String lista_garantia_sql = "SELECT g.nombre, g.nombre FROM garantia g where g.estado = 'VIGENTE'";
        this.Lista_garantia = drive.lista_SelectItem(lista_garantia_sql, this.ambiente);
        String lista_status_sql = "SELECT e.nombre, e.nombre FROM estatus e where e.estado = 'VIGENTE'";
        this.Lista_status = drive.lista_SelectItem(lista_status_sql, this.ambiente);
        String lista_estado_sql = "SELECT s.nombre, s.nombre FROM sestado s WHERE s.estado = 'VIGENTE'";
        this.Lista_estado = drive.lista_SelectItem(lista_estado_sql, this.ambiente);
        String lista_juzgado_sql = "SELECT j.nombre, j.nombre FROM juzgado j WHERE j.estado = 'VIGENTE'";
        this.Lista_juzgado = drive.lista_SelectItem(lista_juzgado_sql, this.ambiente);
        
        this.procurador = "Seleccionar uno...";
        this.garantia = "Seleccionar uno...";
        this.status = "Seleccionar uno...";
        this.estado = "Seleccionar uno...";
        this.juzgado = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void constructor() {
        Driver drive = new Driver();
        
        String lista_procurador_sql = "SELECT u.nombre, u.nombre FROM usuario u where u.procurador='SI' and u.estado='VIGENTE'";
        this.Lista_procurador = drive.lista_SelectItem(lista_procurador_sql, this.ambiente);
        String lista_garantia_sql = "SELECT g.nombre, g.nombre FROM garantia g where g.estado = 'VIGENTE'";
        this.Lista_garantia = drive.lista_SelectItem(lista_garantia_sql, this.ambiente);
        String lista_status_sql = "SELECT e.nombre, e.nombre FROM estatus e where e.estado = 'VIGENTE'";
        this.Lista_status = drive.lista_SelectItem(lista_status_sql, this.ambiente);
        String lista_estado_sql = "SELECT s.nombre, s.nombre FROM sestado s WHERE s.estado = 'VIGENTE'";
        this.Lista_estado = drive.lista_SelectItem(lista_estado_sql, this.ambiente);
        String lista_juzgado_sql = "SELECT j.nombre, j.nombre FROM juzgado j WHERE j.estado = 'VIGENTE'";
        this.Lista_juzgado = drive.lista_SelectItem(lista_juzgado_sql, this.ambiente);
        
        this.procurador = "Seleccionar uno...";
        this.garantia = "Seleccionar uno...";
        this.status = "Seleccionar uno...";
        this.estado = "Seleccionar uno...";
        this.juzgado = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void generar_reporte() {
        try {
            if (this.procurador != null && this.garantia != null && this.status != null && this.estado != null && this.juzgado != null) {
                
                if (procurador.equals("TODOS")) {
                    procurador = "";
                }
                if (garantia.equals("TODOS")) {
                    garantia = "";
                }
                if (status.equals("TODOS")) {
                    status = "";
                }
                if (estado.equals("TODOS")) {
                    estado = "";
                }
                if (juzgado.equals("TODOS")) {
                    juzgado = "";
                }
                
                String cadenasql = "SELECT "
                        + "g.nombre AS GARANTIA, "
                        + "if(d.moneda='QUETZAL',d.monto_inicial,0.00) AS MontoQQ, "
                        + "if(d.moneda='DOLLAR',d.monto_inicial,0.00) AS MontoUSD, "
                        + "d.caso AS CASO, "
                        + "d.nombre AS NOMBRE, "
                        + "s.nombre AS ESTADO, "
                        + "e.nombre AS STATUS, "
                        + "jg.nombre AS JUZGADO, "
                        + "j.no_juicio AS NO_JUICIO, "
                        + "j.notificador AS NOTIFICADOR, "
                        + "u.nombre AS PROCURADOR, "
                        + "j.procuracion AS PROCURACION, "
                        + "d.situacion_caso AS SITUACION_JUICIO, "
                        + "j.razon_notificacion AS RAZON_NOTIFICACION, "
                        + "j.fecha AS PRESENTACION_DEMANDA, "
                        + "j.fecha AS ADMINISION_DEMANDA, "
                        + "j.sumario AS SUMARIO, "
                        + "j.memorial AS MEMORIAL, "
                        + "j.deudor_notificado AS NOTIFICADO, "
                        + "j.fecha_notificacion AS FECHA_NOTIFICADO, "
                        + "a.nombre AS ACTOR "
                        + "FROM "
                        + "deudor d "
                        + "LEFT JOIN juicio j ON (d.deudor = j.deudor) "
                        + "LEFT JOIN usuario u ON (j.procurador = u.usuario) "
                        + "LEFT JOIN garantia g ON (d.garantia = g.garantia) "
                        + "LEFT JOIN estatus e ON (d.estatus = e.estatus) "
                        + "LEFT JOIN sestado s ON (d.sestado = s.sestado) "
                        + "LEFT JOIN juzgado jg ON (j.juzgado = jg.juzgado) "
                        + "LEFT JOIN actor a ON (d.actor = a.actor) "
                        + "WHERE "
                        + "d.cargado='CARGADO' and "
                        + "u.nombre like '%" + procurador + "%' and "
                        + "g.nombre like '%" + garantia + "%' and "
                        + "e.nombre like '%" + status + "%' and "
                        + "s.nombre like '%" + estado + "%' and "
                        + "jg.nombre like '%" + juzgado + "%'";
                
                Servicio servicio = new Servicio();
                java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);
                
                Integer filas = resultado.size();
                Integer columnas = resultado.get(0).getItem().size();
                String[][] reporte_excel = new String[resultado.size()][columnas];
                for (Integer i = 0; i < resultado.size(); i++) {
                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                        reporte_excel[i][j] = resultado.get(i).getItem().get(j);
                    }
                }
                
                Driver drive = new Driver();
                drive.generar_reporte_excel(this.archivo, reporte_excel, filas, columnas);
                
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                
                InputStream stream = new FileInputStream(this.archivo);
                file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteProcuradores_" + sdf.format(cal.getTime()) + ".xls");
            } else {
                FacesMessage msg = new FacesMessage("Mensaje del sistema...", "Debe indicar los parametros en los campos.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesMessage msg = new FacesMessage("Mensaje del sistema...", ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProcurador() {
        return procurador;
    }

    public void setProcurador(String procurador) {
        this.procurador = procurador;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public List<SelectItem> getLista_procurador() {
        return Lista_procurador;
    }

    public void setLista_procurador(List<SelectItem> Lista_procurador) {
        this.Lista_procurador = Lista_procurador;
    }

    public List<SelectItem> getLista_garantia() {
        return Lista_garantia;
    }

    public void setLista_garantia(List<SelectItem> Lista_garantia) {
        this.Lista_garantia = Lista_garantia;
    }

    public List<SelectItem> getLista_status() {
        return Lista_status;
    }

    public void setLista_status(List<SelectItem> Lista_status) {
        this.Lista_status = Lista_status;
    }

    public List<SelectItem> getLista_estado() {
        return Lista_estado;
    }

    public void setLista_estado(List<SelectItem> Lista_estado) {
        this.Lista_estado = Lista_estado;
    }

    public List<SelectItem> getLista_juzgado() {
        return Lista_juzgado;
    }

    public void setLista_juzgado(List<SelectItem> Lista_juzgado) {
        this.Lista_juzgado = Lista_juzgado;
    }
    
    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
