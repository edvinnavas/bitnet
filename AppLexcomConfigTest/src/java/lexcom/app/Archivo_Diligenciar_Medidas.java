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

@ManagedBean(name = "Archivo_Diligenciar_Medidas")
@ViewScoped
public class Archivo_Diligenciar_Medidas implements Serializable {
    
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
        this.archivo = drive.getPath() + this.usuario + "_archivo_deligenciar_medidas.xls";
        
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
                    + "d.nombre AS NOMBRE_DEUDOR, " 
                    + "j.procurador AS PROCURADOR, " 
                    + "j.juzgado AS JUZGADO, " 
                    + "j.no_juicio AS NO_JUICIO, " 
                    + "j.notificador AS NOTIFICADOR, " 
                    + "d.sestado AS ESTADO, " 
                    + "e.estatus AS STATUS, " 
                    + "j.procuracion AS PROCURACION, " 
                    + "d.situacion_caso AS SITUACION_CASO, " 
                    + "j.razon_notificacion RAZON_NOTIFICACION, " 
                    + "ifnull(sum(p.monto),0.00) AS PAGOS, " 
                    + "'-' AS ARRAIGO, " 
                    + "'aaaa-mm-dd' AS FECHA_ARRAIGO, " 
                    + "'-' AS BANCOS, " 
                    + "'aaaa-mm-dd' AS FECHA_BANCOS, " 
                    + "'-' AS FINCA, " 
                    + "'-' AS LETRA_FINCA, " 
                    + "'aaaa-mm-dd' AS FECHA_FINCA, " 
                    + "'-' AS TRAMITE_FINCA, " 
                    + "'-' AS SALARIO, " 
                    + "'-' AS EMPRESA_SALARIO, " 
                    + "'aaaa-mm-dd' AS FECHA_SALARIO, " 
                    + "'-' AS VEHICULO, " 
                    + "'-' AS MEDIDA_VEHICULO, " 
                    + "'aaaa-mm-dd' AS FECHA_VEHICULO, " 
                    + "'-' AS OTROS, " 
                    + "'-' AS MEDIDA_OTROS, " 
                    + "'aaaa-mm-dd' AS FECHA_OTROS " 
                    + "from "  
                    + "juicio j " 
                    + "left join deudor d on (j.deudor=d.deudor) " 
                    + "left join estatus e on (d.estatus=e.estatus) " 
                    + "left join pago p on (d.deudor=p.deudor) " 
                    + "where " 
                    + "e.nombre='DILIGENCIAR' and " 
                    + "d.cargado='CARGADO' " 
                    + "group by " 
                    + "d.deudor";

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ArchivoDiligenciarMedidas_" + sdf.format(cal.getTime()) + ".xls");
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
