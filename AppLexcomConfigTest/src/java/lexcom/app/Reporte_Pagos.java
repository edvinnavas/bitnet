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

@ManagedBean(name = "Reporte_Pagos")
@ViewScoped
public class Reporte_Pagos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private Date fecha1;
    private Date fecha2;
    private String corporacion;
    private StreamedContent file;
    
    private List<SelectItem> Lista_corporacion = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();
        
        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_pagos.xls";
        
        String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
        this.Lista_corporacion = drive.lista_SelectItem(lista_corporacion_sql, this.ambiente);
        
        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;

        this.corporacion = "Seleccionar uno...";
        
        this.file = null;
    }
    
    public void constructor() {
        Driver drive = new Driver();
        String lista_corporacion_sql = "select c.nombre, c.nombre from cooperacion c where c.estado = 'VIGENTE'";
        this.Lista_corporacion = drive.lista_SelectItem(lista_corporacion_sql, this.ambiente);
        
        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;

        this.corporacion = "Seleccionar uno...";
        
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
            
            if (!t_fecha1.equals("") && !t_fecha2.equals("") && corporacion != null) {
                if (corporacion.equals("TODOS")) {
                    corporacion = "";
                }
                
                String cadenasql = "select "
                        + "d.caso AS CASO, "
                        + "d.fecha_recepcion AS FECHA_RECEPCION, "
                        + "d.antiguedad AS ANTIGUEDAD, "
                        + "dhc.ULTIMA_GESTION AS ULTIMA_GESTION, "
                        + "CAST(d.no_cuenta AS CHAR) AS NO_CREDITO, "
                        + "CAST(d.no_cuenta_otro AS CHAR) AS OTRO_NO_CREDITO, "
                        + "d.saldo AS SALDO_AL_DIA, "
                        + "d.nombre AS NOMBRE_DEUDOR, "
                        + "e.nombre AS STATUS, "
                        + "s.nombre AS ESTADO, "
                        + "d.moneda AS MONEDA, "
                        + "d.monto_inicial AS MONTO_INICIAL, "
                        + "if(d.opcion_proximo_pago='SI', d.cuota_convenio, 0.00) AS CUOTA, "
                        + "pag.fecha_registro AS PAGO_REGISTRO, "
                        + "ban.nombre AS BANCO, "
                        + "pag.no_boleta AS NO_BOLETA, "
                        + "pag.fecha AS FECHA_BOLETA, "
                        + "pag.monto AS PAGO_MES, "
                        + "if(d.opcion_proximo_pago='SI', d.fecha_proximo_pago, '1900-01-01') AS FECHA_PROXIMO_PAGO, "
                        + "g.nombre AS GARANTIA, "
                        + "u.nombre AS GESTOR, "
                        + "cc.nombre as CODIGO_CONTACTABILIDAD, "
                        + "a.nombre AS ACTOR "
                        + "from "
                        + "deudor d "
                        + "left join estatus e on (d.estatus=e.estatus) "
                        + "left join sestado s on (d.sestado=s.sestado) "
                        + "left join garantia g on (d.garantia=g.garantia) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join pago pag on (d.deudor=pag.deudor) "
                        + "left join banco ban on (pag.banco=ban.banco) "
                        + "left join codigo_contactabilidad cc on (d.codigo_contactabilidad=cc.codigo_contactabilidad) "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join cooperacion cor on (a.cooperacion=cor.cooperacion) "
                        + "left join (select "
                        + "dh.deudor AS DEUDOR, "
                        + "max(dh.fecha) AS ULTIMA_GESTION "
                        + "from "
                        + "deudor_historial_cobros dh "
                        + "group by "
                        + "dh.deudor) dhc on (d.deudor=dhc.DEUDOR) "
                        + "where "
                        + "(d.cargado='CARGADO') AND "
                        + "(pag.fecha_registro between '" + t_fecha1 + "' and '" + t_fecha2 + "') AND "
                        + "(pag.monto > 0.00) AND "
                        + "(cor.nombre like '%" + corporacion + "%')";
                
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
                file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReportePagos_" + sdf.format(cal.getTime()) + ".xls");
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
