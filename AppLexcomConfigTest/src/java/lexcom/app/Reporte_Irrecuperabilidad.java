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

@ManagedBean(name = "Reporte_Irrecuperabilidad")
@ViewScoped
public class Reporte_Irrecuperabilidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private String corporacion;
    private StreamedContent file;
    
    private List<SelectItem> Lista_corporacion = new ArrayList<>();

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_irrecuperabilidad.xls";
        
        String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
        this.Lista_corporacion = drive.lista_SelectItem(lista_corporacion_sql, this.ambiente);

        this.corporacion = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void constructor() {
        this.file = null;
    }

    public void generar_reporte() {
        try {            
            if (this.corporacion.equals("TODOS")) {
                this.corporacion = "%%";
            }
            
            Driver drive = new Driver();
            Integer numero_max_arraigo = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_arraigo ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            Integer numero_max_banco = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_banco ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            Integer numero_max_finca = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_finca ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            Integer numero_max_otros = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_otros ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            Integer numero_max_salario = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_salario ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            Integer numero_max_vehiculo = drive.getInt("select "
                    + "ifnull(max(medida.numero),0) maximo "
                    + "from (select ja.juicio, count(*) numero "
                    + "from juicio_vehiculo ja left join juicio j on (ja.juicio=j.juicio) left join deudor d on (j.deudor=d.deudor) left join actor a on (d.actor=a.actor) left join cooperacion c on (a.cooperacion=c.cooperacion) "
                    + "where c.nombre like '" + this.corporacion + "' "
                    + "group by ja.juicio) medida", this.ambiente);
            
            String cadenasql = "SELECT " +
                    "DEUDOR.ACTOR AS ACTOR, " +
                    "DEUDOR.GESTOR AS GESTOR, " +
                    "DEUDOR.PROCURADOR AS PROCURADOR, " +
                    "DEUDOR.CASO AS CASO, " +
                    "DEUDOR.GARANTIA AS GARANTIA, " +
                    "DEUDOR.ESTADO AS ESTADO, " +
                    "DEUDOR.STATUS AS STATUS, " +
                    "DEUDOR.PROCURACION AS PROCURACION, " +
                    "DEUDOR.NOMBRE_DEUDOR AS NOMBRE_DEUDOR, " +
                    "DEUDOR.NO_CUENTA AS NO_CUENTA, " +
                    "DEUDOR.OTRO_NO_CUENTA AS OTRO_NO_CUENTA, " +
                    "DEUDOR.MONTO_INICIAL AS MONTO_INICIAL, " +
                    "DEUDOR.MONEDA AS MONEDA, " +
                    "DEUDOR.PRESENTACION_DEMANDA AS PRESENTACION_DEMANDA, " +
                    "DEUDOR.JUZGADO AS JUZGADO, " +
                    "DEUDOR.NO_JUICIO AS NO_JUICIO, " + 
                    "DEUDOR.NOTIFICADOR AS NOTIFICADOR, " +
                    "DEUDOR.SUMA_PAGOS_REALIZADOS AS SUMA_PAGOS_REALIZADOS, " +
                    "DEUDOR.FECHA_ULTIMO_PAGO AS FECHA_ULTIMO_PAGO, " +
                    "DEUDOR.FECHA_PROXIMO_PAGO AS FECHA_PROXIMO_PAGO ";
            
            for(Integer i = 0; i < numero_max_arraigo; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.ARRAIGO_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.ARRAIGO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_banco; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.BANCO_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.BANCO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_finca; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.FINCA_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.FINCA_FINCA_" + i + ", MEDIDAS_PRECAUTORIAS.FINCA_LETRA_" + i + ", MEDIDAS_PRECAUTORIAS.FINCA_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_otros; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.OTROS_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.OTROS_OTROS_" + i + ", MEDIDAS_PRECAUTORIAS.OTROS_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_salario; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.SALARIO_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.SALARIO_EMPRESA_" + i + ", MEDIDAS_PRECAUTORIAS.SALARIO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_vehiculo; i++) {
                cadenasql = cadenasql + ", MEDIDAS_PRECAUTORIAS.VEHICULO_MEDIDA_" + i + ", MEDIDAS_PRECAUTORIAS.VEHICULO_" + i + ", MEDIDAS_PRECAUTORIAS.VEHICULO_FECHA_" + i;
            }
            
            cadenasql = cadenasql + " FROM " +
                    "(SELECT " +
                    "a.nombre AS ACTOR, " +
                    "g.nombre AS GESTOR, " +
                    "g.nombre AS PROCURADOR, " +
                    "d.caso AS CASO, " +
                    "ga.nombre AS GARANTIA, " +
                    "se.nombre AS ESTADO, " +
                    "es.nombre AS STATUS, " +
                    "j.procuracion AS PROCURACION, " +
                    "d.nombre AS NOMBRE_DEUDOR, " +
                    "d.no_cuenta AS NO_CUENTA, " +
                    "d.no_cuenta_otro AS OTRO_NO_CUENTA, " +
                    "d.monto_inicial AS MONTO_INICIAL, " +
                    "d.moneda AS MONEDA, " +
                    "j.fecha AS PRESENTACION_DEMANDA, " +
                    "ju.nombre AS JUZGADO, " +
                    "j.no_juicio AS NO_JUICIO, " +
                    "j.notificador AS NOTIFICADOR, " +
                    "ifnull(sum(pa.monto),0.00) AS SUMA_PAGOS_REALIZADOS, " +
                    "(select ifnull(max(fecha),'-') from pago p where p.deudor=d.deudor) FECHA_ULTIMO_PAGO, " +
                    "(select ifnull(min(cd.fecha_pago),'-') from convenio_detalle cd left join convenio c on (cd.convenio=c.convenio) where estado_promesa='PENDIENTE' and c.estado='ACTIVO' and c.deudor=d.deudor) FECHA_PROXIMO_PAGO, " +
                    "j.juicio AS JUICIO " +
                    "FROM " +
                    "deudor d " +
                    "left join actor a on (d.actor=a.actor) " +
                    "left join usuario g on (d.usuario=g.usuario) " +
                    "left join juicio j on (d.deudor=j.deudor) " +
                    "left join usuario p on (j.procurador=p.usuario) " +
                    "left join garantia ga on (d.garantia=ga.garantia) " +
                    "left join sestado se on (d.sestado=se.sestado) " +
                    "left join estatus es on (d.estatus=es.estatus) " +
                    "left join juzgado ju on (j.juzgado=ju.juzgado) " +
                    "left join pago pa on (d.deudor=pa.deudor) " + 
                    "left join cooperacion c on (a.cooperacion=c.cooperacion) " +
                    "WHERE " +
                    "(d.cargado ='CARGADO') and " + 
                    "(c.nombre like '" + this.corporacion + "') " +
                    "GROUP BY " +
                    "d.deudor) AS DEUDOR " +
                    "LEFT JOIN " +
                    "(select " +
                    "ju_ar.juicio AS JUICIO";
            
            for(Integer i = 0; i < numero_max_arraigo; i++) {
                cadenasql = cadenasql + ", ja_" + i + ".arraigo AS ARRAIGO_MEDIDA_" + i + ", ja_" + i + ".deligenciado AS ARRAIGO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_banco; i++) {
                cadenasql = cadenasql + ", jb_" + i + ".bancos AS BANCO_MEDIDA_" + i + ", jb_" + i + ".deligenciado AS BANCO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_finca; i++) {
                cadenasql = cadenasql + ", jf_" + i + ".tramite AS FINCA_MEDIDA_" + i + ", jf_" + i + ".finca AS FINCA_FINCA_" + i + ", jf_" + i + ".letra AS FINCA_LETRA_" + i + ", jf_" + i + ".deligenciado AS FINCA_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_otros; i++) {
                cadenasql = cadenasql + ", jo_" + i + ".medida AS OTROS_MEDIDA_" + i + ", jo_" + i + ".otros AS OTROS_OTROS_" + i + ", jo_" + i + ".deligenciado AS OTROS_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_salario; i++) {
                cadenasql = cadenasql + ", js_" + i + ".salario AS SALARIO_MEDIDA_" + i + ", js_" + i + ".empresa AS SALARIO_EMPRESA_" + i + ", js_" + i + ".deligenciado AS SALARIO_FECHA_" + i;
            }
            for(Integer i = 0; i < numero_max_vehiculo; i++) {
                cadenasql = cadenasql + ", jv_" + i + ".medida AS VEHICULO_MEDIDA_" + i + ", jv_" + i + ".vehiculo AS VEHICULO_" + i + ", jv_" + i + ".deligenciado AS VEHICULO_FECHA_" + i;
            }
            
            cadenasql = cadenasql + " from " +
                    "(select j.juicio from juicio j where j.juicio in ( " +
                    "select ja.juicio from juicio_arraigo ja union all " +
                    "select ja.juicio from juicio_banco ja union all " +
                    "select ja.juicio from juicio_finca ja union all " +
                    "select ja.juicio from juicio_otros ja union all " +
                    "select ja.juicio from juicio_salario ja union all " +
                    "select ja.juicio from juicio_vehiculo ja)) ju_ar ";
            
            for(Integer i = 0; i < numero_max_arraigo; i++) {
                cadenasql = cadenasql + "left join (select ja.juicio, ja.arraigo, ja.deligenciado from juicio_arraigo ja where ja.correlativo = " + i + ") as ja_" + i + " on (ju_ar.juicio = ja_" + i + ".juicio) ";
            }
            for(Integer i = 0; i < numero_max_banco; i++) {
                cadenasql = cadenasql + "left join (select jb.juicio, jb.bancos, jb.deligenciado from juicio_banco jb where jb.correlativo = " + i + ") as jb_" + i + " on (ju_ar.juicio = jb_" + i + ".juicio) ";
            }
            for(Integer i = 0; i < numero_max_finca; i++) {
                cadenasql = cadenasql + "left join (select jf.juicio, jf.tramite, jf.finca, jf.letra, jf.deligenciado from juicio_finca jf where jf.correlativo = " + i + ") as jf_" + i + " on (ju_ar.juicio = jf_" + i + ".juicio) ";
            }
            for(Integer i = 0; i < numero_max_otros; i++) {
                cadenasql = cadenasql + "left join (select jo.juicio, jo.medida, jo.otros, jo.deligenciado from juicio_otros jo where jo.correlativo = " + i + ") as jo_" + i + " on (ju_ar.juicio = jo_" + i + ".juicio) ";
            }
            for(Integer i = 0; i < numero_max_salario; i++) {
                cadenasql = cadenasql + "left join (select js.juicio, js.salario, js.empresa, js.deligenciado from juicio_salario js where js.correlativo = " + i + ") as js_" + i + " on (ju_ar.juicio = js_" + i + ".juicio) ";
            }
            for(Integer i = 0; i < numero_max_vehiculo; i++) {
                cadenasql = cadenasql + "left join (select jv.juicio, jv.medida, jv.vehiculo, jv.deligenciado from juicio_vehiculo jv where jv.correlativo = " + i + ") as jv_" + i + " on (ju_ar.juicio = jv_" + i + ".juicio) ";
            }
            
            cadenasql = cadenasql + "group by ju_ar.juicio) AS MEDIDAS_PRECAUTORIAS ON (DEUDOR.JUICIO = MEDIDAS_PRECAUTORIAS.JUICIO)";

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

            drive.generar_reporte_excel(this.archivo, reporte_excel, filas, columnas);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            InputStream stream = new FileInputStream(this.archivo);
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteIrrecuperabilidad_" + sdf.format(cal.getTime()) + ".xls");
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

    public String getCorporacion() {
        return corporacion;
    }

    public void setCorporacion(String corporacion) {
        this.corporacion = corporacion;
    }

    public List<SelectItem> getLista_corporacion() {
        return Lista_corporacion;
    }

    public void setLista_corporacion(List<SelectItem> Lista_corporacion) {
        this.Lista_corporacion = Lista_corporacion;
    }
    
}
