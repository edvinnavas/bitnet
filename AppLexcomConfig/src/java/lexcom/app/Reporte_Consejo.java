package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

@ManagedBean(name = "Reporte_Consejo")
@ViewScoped
public class Reporte_Consejo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private Date Per1Fech1;
    private Date Per1Fech2;
    private Date Per2Fech1;
    private Date Per2Fech2;
    private String corporacion;
    private StreamedContent file;
    
    private List<SelectItem> Lista_corporacion = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();
        
        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_consejo.xls";
        
        String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
        this.Lista_corporacion = drive.lista_SelectItem(lista_corporacion_sql, this.ambiente);
        
        Date fecha = new Date();
        this.Per1Fech1 = fecha;
        this.Per1Fech2 = fecha;
        this.Per2Fech1 = fecha;
        this.Per2Fech2 = fecha;

        this.corporacion = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void constructor() {
        Date fecha = new Date();
        this.Per1Fech1 = fecha;
        this.Per1Fech2 = fecha;
        this.Per2Fech1 = fecha;
        this.Per2Fech2 = fecha;
        
        Driver drive = new Driver();
        String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
        this.Lista_corporacion = drive.lista_SelectItem(lista_corporacion_sql, this.ambiente);

        this.corporacion = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void generar_reporte() {
        try {
            String periodo1 = "";
            String periodo2 = "";
            
            Integer dia = Per1Fech1.getDate();
            Integer mes = Per1Fech1.getMonth() + 1;
            Integer anio = Per1Fech1.getYear() + 1900;
            periodo1 = "'" + anio.toString() + "/" + mes.toString() + "/" + dia.toString() + "'" + " AND ";
            
            dia = Per1Fech2.getDate();
            mes = Per1Fech2.getMonth() + 1;
            anio = Per1Fech2.getYear() + 1900;
            periodo1 = periodo1 + "'" + anio.toString() + "/" + mes.toString() + "/" + dia.toString() + "'";
            
            dia = Per2Fech1.getDate();
            mes = Per2Fech1.getMonth() + 1;
            anio = Per2Fech1.getYear() + 1900;
            periodo2 = "'" + anio.toString() + "/" + mes.toString() + "/" + dia.toString() + "'" + " AND ";
            
            dia = Per2Fech2.getDate();
            mes = Per2Fech2.getMonth() + 1;
            anio = Per2Fech2.getYear() + 1900;
            periodo2 = periodo2 + "'" + anio.toString() + "/" + mes.toString() + "/" + dia.toString() + "'";
            
            if (!periodo1.equals("") && !periodo2.equals("") && corporacion != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                
                String cadenasql = "SELECT "
                        + "d.caso AS CASO, "
                        + "u.nombre AS GESTOR, "
                        + "d.no_cuenta_otro AS OTRO_NO_CUENTA, "
                        + "d.cargado AS CARGADO, "
                        + "a.nombre AS ACTOR, "
                        + "s.nombre AS ESTADO, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "j.fecha AS PRESENTACION_DEMANDA, "
                        + "j.tiempo_estimado AS TIEMPO_ESTIMADO, "
                        + "ju.nombre AS JUZGADO, "
                        + "j.no_juicio AS NUMERO_JUICIO, "
                        + "d.no_cuenta AS NUMERO_CREDITO, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "d.moneda AS TIPO_MONEDA, "
                        + "d.monto_inicial AS MONTO, "
                        + "d.saldo AS SALDO, "
                        + "g.nombre AS GARANTIA, "
                        + "d.convenio_pactado AS CONVENIO, "
                        + "CONCAT(IFNULL(M_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(J_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(P_ANT.ANTERIORES_ACCIONES,'-'),IFNULL(JU_ANT.ANTERIORES_ACCIONES,'-'))  AS ANTERIORES_ACCIONES, "
                        + "CONCAT(IFNULL(M_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(J_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(P_SIG.ULTIMAS_ACCIONES,'-'),IFNULL(JU_SIG.ULTIMAS_ACCIONES,'-')) AS ULTIMAS_ACCIONES, "
                        + "d.situacion_caso AS SITUACION_CASO "
                        + "FROM  "
                        + "deudor d "
                        + "LEFT JOIN actor a ON (d.actor=a.actor) "
                        + "LEFT JOIN cooperacion cor ON (a.cooperacion=cor.cooperacion) "
                        + "LEFT JOIN sestado s ON (d.sestado=s.sestado) "
                        + "LEFT JOIN juicio j ON (d.deudor=j.deudor) "
                        + "LEFT JOIN juzgado ju ON (j.juzgado=ju.juzgado) "
                        + "LEFT JOIN garantia g ON (d.garantia=g.garantia) "
                        + "LEFT JOIN usuario u on (d.usuario=u.usuario) "
                        + "LEFT JOIN (SELECT "
                        + "dhc.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhc.descripcion,'\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "deudor_historial_cobros dhc "
                        + "WHERE "
                        + "dhc.fecha between " + periodo1 + " "
                        + "GROUP BY "
                        + "dhc.deudor "
                        + "ORDER BY "
                        + "dhc.fecha) M_ANT ON (d.deudor=M_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhc.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhc.descripcion,'\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "deudor_historial_cobros dhc "
                        + "WHERE "
                        + "dhc.fecha between " + periodo2 + " "
                        + "GROUP BY "
                        + "dhc.deudor "
                        + "ORDER BY "
                        + "dhc.fecha) M_SIG ON (d.deudor=M_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhj.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhj.descripcion,'\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "deudor_historial_juridico dhj "
                        + "WHERE "
                        + "dhj.fecha between " + periodo1 + " "
                        + "GROUP BY "
                        + "dhj.deudor "
                        + "ORDER BY "
                        + "dhj.fecha) J_ANT ON (d.deudor=J_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "dhj.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('-',dhj.descripcion,'\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "deudor_historial_juridico dhj "
                        + "WHERE "
                        + "dhj.fecha between " + periodo2 + " "
                        + "GROUP BY "
                        + "dhj.deudor "
                        + "ORDER BY "
                        + "dhj.fecha) J_SIG ON (d.deudor=J_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "p.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Pago realizado ', p.fecha_registro, ' Q.', p.monto, '\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "pago p "
                        + "WHERE "
                        + "p.fecha_registro BETWEEN " + periodo1 + " "
                        + "GROUP BY "
                        + "p.deudor "
                        + "ORDER BY "
                        + "p.fecha_registro) P_ANT ON (d.deudor=P_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "p.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Pago realizado ', p.fecha_registro, ' Q.', p.monto, '\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "pago p "
                        + "WHERE "
                        + "p.fecha_registro BETWEEN " + periodo2 + " "
                        + "GROUP BY "
                        + "p.deudor "
                        + "ORDER BY "
                        + "p.fecha_registro) P_SIG ON (d.deudor=P_SIG.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "ju.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Presentación Demanda ', ju.fecha, ' Numero Juicio: ', ju.no_juicio, ' ',ju.procuracion, '\n') AS ANTERIORES_ACCIONES "
                        + "FROM "
                        + "juicio ju "
                        + "WHERE "
                        + "ju.fecha BETWEEN " + periodo1 + " "
                        + "GROUP BY "
                        + "ju.deudor "
                        + "ORDER BY "
                        + "ju.fecha) JU_ANT ON (d.deudor=JU_ANT.COD_DEUDOR) "
                        + "LEFT JOIN (SELECT "
                        + "ju.deudor AS COD_DEUDOR, "
                        + "GROUP_CONCAT('- Presentación Demanda ', ju.fecha, ' Numero Juicio: ', ju.no_juicio, ' ',ju.procuracion, '\n') AS ULTIMAS_ACCIONES "
                        + "FROM "
                        + "juicio ju "
                        + "WHERE "
                        + "ju.fecha BETWEEN " + periodo2 + " "
                        + "GROUP BY "
                        + "ju.deudor "
                        + "ORDER BY "
                        + "ju.fecha) JU_SIG ON (d.deudor=JU_SIG.COD_DEUDOR) "
                        + "WHERE "
                        + "cor.nombre like '%" + corporacion + "%'";
                
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
                file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteConsejo_" + sdf.format(cal.getTime()) + ".xls");
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

    public Date getPer1Fech1() {
        return Per1Fech1;
    }

    public void setPer1Fech1(Date Per1Fech1) {
        this.Per1Fech1 = Per1Fech1;
    }

    public Date getPer1Fech2() {
        return Per1Fech2;
    }

    public void setPer1Fech2(Date Per1Fech2) {
        this.Per1Fech2 = Per1Fech2;
    }

    public Date getPer2Fech1() {
        return Per2Fech1;
    }

    public void setPer2Fech1(Date Per2Fech1) {
        this.Per2Fech1 = Per2Fech1;
    }

    public Date getPer2Fech2() {
        return Per2Fech2;
    }

    public void setPer2Fech2(Date Per2Fech2) {
        this.Per2Fech2 = Per2Fech2;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
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
