package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "Reporte_Historial_Gestiones")
@ViewScoped
public class Reporte_Historial_Gestiones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private Date fecha1;
    private Date fecha2;
    private StreamedContent file;
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();
        
        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_historial_gestiones.xls";
        
        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;
        
        this.file = null;
    }
    
    public void constructor() {
        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;
        
        this.file = null;
    }
    
    public void generar_reporte() {
        try {
            Integer dia = fecha1.getDate();
            Integer mes = fecha1.getMonth() + 1;
            Integer anio = fecha1.getYear() + 1900;
            String t_fecha1 = anio.toString() + "/" + mes.toString() + "/" + dia.toString();
            
            dia = fecha2.getDate();
            mes = fecha2.getMonth() + 1;
            anio = fecha2.getYear() + 1900;
            String t_fecha2 = anio.toString() + "/" + mes.toString() + "/" + dia.toString();
            
            if (!t_fecha1.equals("") && !t_fecha2.equals("")) {
                String cadenasql = "select "
                        + "d.caso AS CASO, "
                        + "d.no_cuenta AS NO_CUENTA, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "u.nombre AS GESTOR, "
                        + "a.nombre AS ACTOR, "
                        + "s.nombre AS ESTADO, "
                        + "e.nombre AS STATUS, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "j.fecha AS FECHA_PRESENTACION_DEMANDA, "
                        + "ju.nombre AS JUZGADO, "
                        + "d.monto_inicial AS MONTO_INICIAL, "
                        + "d.saldo AS SALDO, "
                        + "g.nombre AS GARANTIA, "
                        + "dhc.fecha AS FECHA_GESTION, "
                        + "dhc.hora AS HORA_GESTION, "
                        + "concat(cc.codigo,'|',cc.nombre) AS CODIGO_RESULTADO, "
                        + "dhc.descripcion AS GESTION "
                        + "from "
                        + "deudor_historial_cobros dhc "
                        + "left join codigo_contactabilidad cc on (dhc.codigo_contactabilidad = cc.codigo_contactabilidad) "
                        + "left join deudor d on (dhc.deudor = d.deudor) "
                        + "left join usuario u on (d.usuario = u.usuario) "
                        + "left join actor a on (d.actor = a.actor) "
                        + "left join sestado s on (d.sestado = s.sestado) "
                        + "left join estatus e on (d.estatus = e.estatus) "
                        + "left join juicio j on (d.deudor = j.deudor) "
                        + "left join juzgado ju on (j.juzgado = ju.juzgado) "
                        + "left join garantia g on (d.garantia = g.garantia) "
                        + "where "
                        + "dhc.fecha between '" + t_fecha1 + " 00:00:00' and '" + t_fecha2 + " 23:59:59'";
                
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
                file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteHistorialGestiones_" + sdf.format(cal.getTime()) + ".xls");
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

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
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
