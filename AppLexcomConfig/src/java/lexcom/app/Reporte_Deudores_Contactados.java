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

@ManagedBean(name = "Reporte_Deudores_Contactados")
@ViewScoped
public class Reporte_Deudores_Contactados implements Serializable {
    
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
        this.archivo = drive.getPath() + this.usuario + "_reporte_deudores_contactados.xls";
        
        this.file = null;
    }
    
    public void constructor() {
        this.file = null;
    }

    public void generar_reporte() {
        try {
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.no_cuenta AS MO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "a.nombre AS ACTOR, "
                    + "g.nombre AS GARANTIA, "
                    + "contactado.fecha_maxima AS FECHA_MAXIMA, "
                    + "contactado.hora_maxima AS HORA_MAXIMA, "
                    + "cc.nombre AS CODIGO_RESULTADO, "
                    + "dhc3.descripcion AS GESTION "
                    + "from "
                    + "(select "
                    + "fm.deudor, "
                    + "fm.fecha_maxima, "
                    + "max(dhc2.hora) as hora_maxima "
                    + "from "
                    + "(select "
                    + "dhc1.deudor as deudor, "
                    + "max(dhc1.fecha) as fecha_maxima "
                    + "from "
                    + "deudor_historial_cobros dhc1 "
                    + "where "
                    + "dhc1.codigo_contactabilidad in (2, 3, 6, 7, 11, 12) "
                    + "group by "
                    + "dhc1.deudor) fm "
                    + "left join deudor_historial_cobros dhc2 on (fm.deudor = dhc2.deudor and fm.fecha_maxima = dhc2.fecha and dhc2.codigo_contactabilidad in (2, 3, 6, 7, 11, 12)) "
                    + "group by "
                    + "fm.deudor, "
                    + "fm.fecha_maxima) contactado "
                    + "left join deudor_historial_cobros dhc3 on (contactado.deudor = dhc3.deudor and contactado.fecha_maxima = dhc3.fecha and contactado.hora_maxima = dhc3.hora and dhc3.codigo_contactabilidad in (2, 3, 6, 7, 11, 12)) "
                    + "left join deudor d on (contactado.deudor = d.deudor) "
                    + "left join codigo_contactabilidad cc on (dhc3.codigo_contactabilidad = cc.codigo_contactabilidad) "
                    + "left join actor a on (d.actor = a.actor) "
                    + "left join garantia g on (d.garantia = g.garantia)";

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteContactados_" + sdf.format(cal.getTime()) + ".xls");
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
