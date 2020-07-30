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

@ManagedBean(name = "Archivo_Rotacion_Cartera_Manual")
@ViewScoped
public class Archivo_Rotacion_Cartera_Manual implements Serializable {
    
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
        this.archivo = drive.getPath() + this.usuario + "_archivo_rotacion_cartera_manual.xls";
        
        this.file = null;
    }
    
    public void constructor () {
        this.file = null;
    }

    public void generar_reporte() {
        try {
            String cadenasql = "select "
                    + "d.deudor AS DEUDOR, "
                    + "d.caso AS CASO, "
                    + "d.antiguedad AS ANTIGUEDAD, "
                    + "d.fecha_recepcion AS FECHA_RECEPCION, "
                    + "d.no_cuenta AS NO_CUENTA, "
                    + "d.nombre AS NOMBRE_DEUDOR, "
                    + "d.monto_inicial AS MONTO_INICIAL, "
                    + "g.nombre AS GARANTIA, "
                    + "s.nombre AS ESTADO, "
                    + "if(d.cuota_convenio>0.00,d.fecha_proximo_pago,'-') AS FECHA_PROXIMO_PAGO, "
                    + "if(d.cuota_convenio>0.00,d.cuota_convenio,'-') AS CUOTA, "
                    + "d.usuario AS GESTOR, "
                    + "u.tipo_usuario AS TIPO_GESTOR, "
                    + "c.nombre AS CODIGO_CONTACTABILIDAD, "
                    + "a.nombre AS ACTOR "
                    + "from "
                    + "deudor d "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join usuario u on (d.usuario = u.usuario) "
                    + "where "
                    + "(d.cargado='CARGADO') AND "
                    + "(g.garantia=7 or g.garantia=13) "
                    + "order by "
                    + "d.monto_inicial desc";

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ArchivoRotacionManual_" + sdf.format(cal.getTime()) + ".xls");
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
