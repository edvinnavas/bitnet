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

@ManagedBean(name = "Reporte_Calificacion_Casos")
@ViewScoped
public class Reporte_Calificacion_Casos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private Integer actor;
    private List<SelectItem> lst_actores;

    private StreamedContent file;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_calificacion_casos.xls";
        
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
                    + "d.caso CASO, "
                    + "d.cargado CARGADO, "
                    + "a.nombre ACTOR, "
                    + "d.fecha_recepcion FECHA_RECEPCION, "
                    + "d.antiguedad ANTIGUEDAD, "
                    + "d.no_cuenta NO_CUENTA, "
                    + "d.no_cuenta_otro OTRO_NO_CUENTA, "
                    + "d.nombre NOMBRE_DEUDOR, "
                    + "d.monto_inicial MONTO_INICIAL, "
                    + "d.saldo SALDO, "
                    + "d.cuota_convenio CUOTA, "
                    + "d.fecha_proximo_pago FECHA_PROXIMO_PAGO, "
                    + "1 RANGO, "
                    + "g.nombre GARANTIA, "
                    + "u.nombre GESTOR, "
                    + "'NOMBRE_GESTOR' NOMBRE_GESTOR, "
                    + "u.tipo_usuario TIPO_GESTOR, "
                    + "'SUPERVISOR' SUPERVISOR, "
                    + "j.fecha FECHA_PRESENTACION_DEMANDA, "
                    + "YEAR(j.fecha) AÑO_PRESENTACION_DEMANDA, "
                    + "MONTH(j.fecha) MES_PRESENTACION_DEMANDA, "
                    + "j.no_juicio NO_JUICIO, "
                    + "(select ifnull(max(fecha),'-') from pago p where p.deudor=d.deudor) FECHA_ULTIMO_PAGO, "
                    + "ifnull(cc.codigo,'-') CODIGO_RESULTADO, "
                    + "ifnull(contacto.fecha_maxima,'-') FECHA_ULTIMO_CONTACTO, "
                    + "ifnull(contacto.descripcion,'-') DESCRIPCION_GESTION, "
                    + "ifnull(medida.fecha_maxima,'-') ULTIMA_FECHA_FINCA, "
                    + "ifnull(medida.finca,'-') FINCA, "
                    + "'CALIFICACION' CALIFICACION, "
                    + "s.nombre ESTADO_JUDICIAL, "
                    + "e.nombre STATUS_JUDICIAL, "
                    + "xs.nombre ESTADO_EXTRAJUDICIAL, "
                    + "xe.nombre STATUS_EXTRAJUDICIAL, "
                    + "d.situacion_caso SITUACION_JUDICIAL, "
                    + "d.convenio_pactado SITUACION_EXTRAJUDICIAL "
                    + "from "
                    + "deudor d "
                    + "left join actor a on (d.actor=a.actor) "
                    + "left join garantia g on (d.garantia=g.garantia) "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "left join sestado s on (d.sestado=s.sestado) "
                    + "left join estatus e on (d.estatus=e.estatus) "
                    + "left join sestado_extra xs on (d.sestado_extra=xs.sestado_extra) "
                    + "left join estatus_extra xe on (d.estatus_extra=xe.estatus_extra) "
                    + "left join ( "
                    + "select "
                    + "hm.deudor, "
                    + "hm.fecha_maxima, "
                    + "hm.hora_maxima, "
                    + "dhc3.codigo_contactabilidad, "
                    + "dhc3.descripcion "
                    + "from "
                    + "(select "
                    + "fm.deudor, "
                    + "fm.fecha_maxima, "
                    + "max(dhc2.hora) as hora_maxima "
                    + "from "
                    + "(select "
                    + "dhc1.deudor, "
                    + "max(dhc1.fecha) as fecha_maxima "
                    + "from "
                    + "deudor_historial_cobros dhc1 "
                    + "where "
                    + "dhc1.codigo_contactabilidad in (2, 3, 6, 7, 11, 12) "
                    + "group by "
                    + "dhc1.deudor) fm "
                    + "left join deudor_historial_cobros dhc2 on (fm.deudor=dhc2.deudor and fm.fecha_maxima=dhc2.fecha and dhc2.codigo_contactabilidad in (2, 3, 6, 7, 11, 12)) "
                    + "group by "
                    + "fm.deudor, "
                    + "fm.fecha_maxima) hm "
                    + "left join deudor_historial_cobros dhc3 on (hm.deudor=dhc3.deudor and hm.fecha_maxima=dhc3.fecha and hm.hora_maxima=dhc3.hora and dhc3.codigo_contactabilidad in (2, 3, 6, 7, 11, 12)) "
                    + ") contacto on (d.deudor=contacto.deudor) "
                    + "left join codigo_contactabilidad cc on (cc.codigo_contactabilidad=contacto.codigo_contactabilidad) "
                    + "left join "
                    + "(select "
                    + "fm.juicio, "
                    + "fm.fecha_maxima, "
                    + "concat('ANOTADA: ', jf2.deligenciado,' FINCA: ',jf2.finca,' LETRA: ',jf2.letra) finca "
                    + "from "
                    + "(select "
                    + "jf1.juicio, "
                    + "max(jf1.deligenciado) fecha_maxima "
                    + "from "
                    + "juicio_finca jf1 "
                    + "where "
                    + "jf1.tramite='ANOTADA' "
                    + "group by "
                    + "jf1.juicio) fm "
                    + "left join juicio_finca jf2 on (fm.juicio=jf2.juicio and fm.fecha_maxima=jf2.deligenciado) "
                    + "where "
                    + "jf2.tramite='ANOTADA' "
                    + "group by "
                    + "fm.juicio, "
                    + "fm.fecha_maxima) medida on (j.juicio=medida.juicio) "
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
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteCalificacionCasos_" + sdf.format(cal.getTime()) + ".xls");
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
