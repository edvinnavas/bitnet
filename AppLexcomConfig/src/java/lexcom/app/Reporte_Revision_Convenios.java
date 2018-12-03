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

@ManagedBean(name = "Reporte_Revision_Convenios")
@ViewScoped
public class Reporte_Revision_Convenios implements Serializable {
    
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
        this.archivo = drive.getPath() + this.usuario + "_reporte_revision_convenios.xls";

        this.file = null;
    }

    public void constructor() {
        this.file = null;
    }

    public void generar_reporte() {
        try {
            String cadenasql = "select "
                    + "act.nombre Actor, "
                    + "deu.caso Caso, "
                    + "deu.fecha_recepcion Fecha_Recepcion, "
                    + "deu.antiguedad Antiguedad, "
                    + "gar.nombre Garantia, "
                    + "usu.nombre Gestor, "
                    + "deu.no_cuenta No_Cuenta, "
                    + "deu.nombre Deudor, "
                    + "deu.monto_inicial Monto_Inicial, "
                    + "deu.saldo Saldo, "
                    + "ifnull(pag.monto,0.00) Pagos_Acumulados, "
                    + "ifnull(con.tipo_convenio,'-') Tipo_Convenio, "
                    + "ifnull(con.estado_convenio,'-') Estado_Convenio, "
                    + "ifnull(con.fecha_convenio,'-') Fecha_Convenio, "
                    + "ifnull(con.monto_convenio,0.00) Monto_Convenio, "
                    + "ifnull(con.q_pago_inicial,0.00) Q_Pago_Inicial, "
                    + "ifnull(con.cantidad_cuotas,0) Cantidad_Cuotas, "
                    + "ifnull(con.frecuencia,'-') Frecuencia_Cuotas, "
                    + "ifnull(con.monto_cuota,0.00) Monto_Cuota, "
                    + "ifnull(con.convenio_pactado,'-') Convenio_Pactado, "
                    + "ifnull((select distinct cd.estado_promesa from convenio_detalle cd " 
                    + "where " 
                    + "cd.convenio=con.convenio and " 
                    + "cd.fecha_pago=if(ifnull(con.estado_convenio,'1900-01-01')='ANULADO','1900-01-01',if(ifnull(con.estado_convenio,'1900-01-01')='NEGOCIACION','1900-01-01',deu.fecha_proximo_pago)) and " 
                    + "cd.monto=if(ifnull(con.estado_convenio,'0.00')='ANULADO',0.00,if(ifnull(con.estado_convenio,'0.00')='NEGOCIACION',0.00,deu.cuota_convenio)) " 
                    + "group by cd.convenio ),'-') Estado_PP, "
                    + "if(ifnull(con.estado_convenio,'-')='ANULADO','-',if(ifnull(con.estado_convenio,'-')='NEGOCIACION','-',deu.fecha_proximo_pago)) F_Prox_Cuota, "
                    + "if(ifnull(con.estado_convenio,'-')='ANULADO',0.00,if(ifnull(con.estado_convenio,'-')='NEGOCIACION',0.00,deu.cuota_convenio)) Q_Prox_Cuota, "
                    + "(select count(*) from convenio_detalle cd where cd.estado_promesa='CUMPLIDO' and cd.convenio=con.convenio) Promesas_Cumplidas, "
                    + "(select count(*) from convenio_detalle cd where cd.estado_promesa='INCUMPLIDO' and cd.convenio=con.convenio) Promesas_Incumplidas, "
                    + "(select ifnull(max(p.fecha),'-') from pago p where p.deudor=con.deudor and p.fecha>=con.fecha_convenio) Ultimo_Pago, "
                    + "(select ifnull(count(*),0) from pago p where p.deudor=con.deudor and p.fecha>=con.fecha_convenio) Cant_Pagos_Convenio, "
                    + "(select ifnull(sum(p.monto),0) from pago p where p.deudor=con.deudor and p.fecha>=con.fecha_convenio) Q_Pagos_Convenio, "
                    + "ifnull(con.cantidad_cuotas,0) - (select ifnull(count(*),0) from pago p where p.deudor=con.deudor and p.fecha>=con.fecha_convenio) Cuotas_Pendientes "
                    + "from "
                    + "deudor deu "
                    + "left join garantia gar on (deu.garantia=gar.garantia) "
                    + "left join usuario usu on (deu.usuario=usu.usuario) "
                    + "left join actor act on (deu.actor=act.actor) "
                    + "left join (select p.deudor, sum(p.monto) monto from pago p group by p.deudor) pag on (deu.deudor=pag.deudor) "
                    + "left join (select "
                    + "c3.deudor as deudor, "
                    + "c3.convenio convenio, "
                    + "c3.tipo_convenio tipo_convenio, "
                    + "c3.estado estado_convenio, "
                    + "c3.fecha_creacion fecha_convenio, "
                    + "c3.total monto_convenio, "
                    + "c3.cuota_inicial q_pago_inicial, "
                    + "c3.numero_cuotas cantidad_cuotas, "
                    + "c3.frecuencia frecuencia, "
                    + "c3.monto_cuota monto_cuota, "
                    + "c3.observacion convenio_pactado "
                    + "from "
                    + "convenio c3 "
                    + "where (c3.deudor, c3.fecha_creacion, c3.convenio) in (select "
                    + "c2.deudor deudor, "
                    + "c2.fecha_creacion fecha_creacion, "
                    + "max(c2.convenio) convenio "
                    + "from "
                    + "convenio c2 "
                    + "where "
                    + "(c2.deudor,c2.fecha_creacion) in (select "
                    + "c1.deudor, "
                    + "max(c1.fecha_creacion) "
                    + "from "
                    + "convenio c1 "
                    + "group by "
                    + "c1.deudor) "
                    + "group by "
                    + "c2.deudor, c2.fecha_creacion)) con on (deu.deudor=con.deudor) "
                    + "where "
                    + "deu.cargado='CARGADO'";

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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteRevisionConvenios_" + sdf.format(cal.getTime()) + ".xls");
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
