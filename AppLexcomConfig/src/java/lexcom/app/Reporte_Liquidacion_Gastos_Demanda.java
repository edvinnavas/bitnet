package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

@ManagedBean(name = "Reporte_Liquidacion_Gastos_Demanda")
@ViewScoped
public class Reporte_Liquidacion_Gastos_Demanda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;

    private StreamedContent file;
    private Integer actor;
    
    private List<SelectItem> lst_actores;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_liquidacion_gastos_demanda.xls";
        
        String cadenasql_lst_actores = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
        this.lst_actores = drive.lista_SelectItem_simple(cadenasql_lst_actores, this.ambiente);
        
        this.file = null;
    }
    
    public void constructor() {
        Driver drive = new Driver();
        
        String cadenasql_lst_actores = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
        this.lst_actores = drive.lista_SelectItem_simple(cadenasql_lst_actores, this.ambiente);
        
        this.file = null;
    }

    public void generar_reporte() {
        try {
            String cadenasql = "select "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.caso AS CASO, "
                    + "jz.nombre AS JUZGADO, "
                    + "ju.no_juicio AS NUMERO_JUICIO, "
                    + "ju.notificador AS NOTIFICADOR, "
                    + "ju.fecha AS FECHA_JUICIO, "
                    + "ju.fecha_admision_demanda AS FECHA_ADMINISION_DEMANDA, "
                    + "ju.fecha_notificacion AS FECHA_NOTIFICACION, "
                    + "ju.memorial AS MEMORIAL, "
                    + "d.anticipo AS ANTICIPO, "
                    + "d.cargado AS CARGADO, "
                    + "a.nombre AS ACTOR "
                    + "from "
                    + "deudor d "
                    + "left join juicio ju on (d.deudor=ju.deudor) "
                    + "left join juzgado jz on (ju.juzgado=jz.juzgado) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "where " 
                    + "d.actor=" + this.actor;

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteLiquidacion_" + sdf.format(cal.getTime()) + ".xls");
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

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getActor() {
        return actor;
    }

    public void setActor(Integer actor) {
        this.actor = actor;
    }

    public List<SelectItem> getLst_actores() {
        return lst_actores;
    }

    public void setLst_actores(List<SelectItem> lst_actores) {
        this.lst_actores = lst_actores;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
