package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "Reporte_Notificados_Entidad_Garantia")
@ViewScoped
public class Reporte_Notificados_Entidad_Garantia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;

    private StreamedContent file;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_notificados_entidad_garantia.xls";
        
        this.file = null;
    }
    
    public void constructor() {
        this.file = null;
    }

    public void generar_reporte() {
        try {
            String cadenasql = "SELECT "
                    + "d.moneda AS MONEDA, "
                    + "a.nombre AS ACTOR, "
                    + "g.nombre AS GARANTIA, "
                    + "COUNT(*) AS NOTIFICADOS "
                    + "FROM "
                    + "deudor d "
                    + "LEFT JOIN actor a ON (d.actor = a.actor) "
                    + "LEFT JOIN garantia g ON (d.garantia = g.garantia) "
                    + "LEFT JOIN juicio j ON (d.deudor=j.deudor) "
                    + "WHERE "
                    + "d.cargado='CARGADO' and "
                    + "j.deudor_notificado='SI' "
                    + "GROUP BY "
                    + "d.moneda, "
                    + "d.actor, "
                    + "d.garantia "
                    + "ORDER BY "
                    + "d.moneda, "
                    + "d.actor, "
                    + "d.garantia";

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteNotificados_" + sdf.format(cal.getTime()) + ".xls");
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

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
