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

@ManagedBean(name = "Archivo_Deudores_Actualizacion")
@ViewScoped
public class Archivo_Deudores_Actualizacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;
    
    private Integer actor;
    private List<SelectItem> lst_actores;
    private String cargado;
    private List<SelectItem> lst_cargado;

    private StreamedContent file;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_archivo_deudores_actualizacion.xls";
        
        String cadenasql_lst_actores = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
        this.lst_actores = drive.lista_SelectItem_simple(cadenasql_lst_actores, this.ambiente);
        this.lst_cargado = drive.lista_cargado();
        
        this.file = null;
    }

    public void constructor () {
        Driver drive = new Driver();
        String cadenasql_lst_actores = "select a.actor, a.nombre from actor a where a.estado='VIGENTE' order by a.nombre";
        this.lst_actores = drive.lista_SelectItem_simple(cadenasql_lst_actores, this.ambiente);
        this.lst_cargado = drive.lista_cargado();
        
        this.file = null;
    }
    
    public void generar_reporte() {
        try {
            String cadenasql = "SELECT "
                    + "d.deudor, "
                    + "d.actor, "
                    + "d.moneda, "
                    + "d.dpi, "
                    + "d.nit, "
                    + "d.fecha_nacimiento, "
                    + "d.nombre, "
                    + "d.nacionalidad, "
                    + "d.telefono_casa, "
                    + "d.telefono_celular, "
                    + "d.direccion, "
                    + "d.zona, "
                    + "d.pais, "
                    + "d.departamento, "
                    + "d.sexo, "
                    + "d.estado_civil, "
                    + "d.fecha_ingreso, "
                    + "d.profesion, "
                    + "d.correo_electronico, "
                    + "d.lugar_trabajo, "
                    + "d.direccion_trabajo, "
                    + "d.telefono_trabajo, "
                    + "d.monto_inicial, "
                    + "d.usuario, "
                    + "d.sestado, "
                    + "d.estatus, "
                    + "d.no_cuenta, "
                    + "d.garantia, "
                    + "d.cargado, "
                    + "d.estado, "
                    + "d.descripcion, "
                    + "d.codigo_contactabilidad, "
                    + "d.caso, "
                    + "d.PDF, "
                    + "d.INV, "
                    + "d.MAYCOM, "
                    + "d.NITS, "
                    + "d.cosecha, "
                    + "d.no_cuenta_otro, "
                    + "d.descripcion_adicional, "
                    + "d.fecha_recepcion, "
                    + "d.anticipo, "
                    + "d.antiguedad, "
                    + "d.fecha_proximo_pago, "
                    + "d.monto_proximo_pago, "
                    + "d.saldo, "
                    + "d.convenio_pactado, "
                    + "d.cuota_convenio, "
                    + "d.costas_pagadas, "
                    + "d.situacion_caso, "
                    + "d.opcion_proximo_pago, "
                    + "j.procuracion, "
                    + "j.fecha fecha_demanda, "
                    + "d.sestado_extra, "
                    + "d.estatus_extra "
                    + "FROM "
                    + "deudor d "
                    + "left join juicio j on (d.deudor=j.deudor) "
                    + "where "
                    + "d.actor=" + this.actor + " and "
                    + "d.cargado='" + this.cargado + "'";
            
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
            drive.generar_reporte_excel_1(this.archivo, reporte_excel, filas, columnas);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            InputStream stream = new FileInputStream(this.archivo);
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ArchivoActualizacionDeudores_" + sdf.format(cal.getTime()) + ".xls");
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

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public List<SelectItem> getLst_cargado() {
        return lst_cargado;
    }

    public void setLst_cargado(List<SelectItem> lst_cargado) {
        this.lst_cargado = lst_cargado;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
