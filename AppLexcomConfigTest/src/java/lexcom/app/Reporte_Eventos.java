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

@ManagedBean(name = "Reporte_Eventos")
@ViewScoped
public class Reporte_Eventos implements Serializable {

    private static final long serialVersionUID = 1L;

    private String archivo;
    private String usuario;
    private String ambiente;

    private Date fecha1;
    private Date fecha2;
    private String user;
    private String Evento;
    private StreamedContent file;

    private List<SelectItem> Lista_user = new ArrayList<>();
    private List<SelectItem> Lista_evento = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        this.archivo = drive.getPath() + this.usuario + "_reporte_pagos.xls";

        String lista_user_sql = "select c.nombre, c.nombre from usuario c ";
        String lista_evento_sql = "select nombre,nombre from tipo_evento";
        this.Lista_user = drive.lista_SelectItem(lista_user_sql, this.ambiente);
        this.Lista_evento = drive.lista_SelectItem(lista_evento_sql, this.ambiente);
        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;

        this.user = "Seleccionar uno...";
        this.Evento = "Seleccionar uno...";
        this.file = null;
    }

    public void constructor() {
        Driver drive = new Driver();
        String lista_user_sql = "select c.nombre, c.nombre from usuario c ";
        String lista_evento_sql = "select nombre,nombre from tipo_evento";
        this.Lista_user = drive.lista_SelectItem(lista_user_sql, this.ambiente);
        this.Lista_evento = drive.lista_SelectItem(lista_evento_sql, this.ambiente);

        Date fecha = new Date();
        this.fecha1 = fecha;
        this.fecha2 = fecha;

        this.user = "Seleccionar uno...";
        this.Evento = "Seleccionar uno...";
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

            int limite = fecha2.getDate() - fecha1.getDate();

            if (limite < 6) {
                if (!t_fecha1.equals("") && !t_fecha2.equals("") && user != null && Evento != null) {

                    String cadenasql = "SELECT "
                            + "tipo_evento.nombre, "
                            + "evento.evento, "
                            + "evento.fecha, "
                            + "evento.descripcion, "
                            + "evento.hora, "
                            + "usuario.nombre "
                            + "FROM "
                            + "evento "
                            + "LEFT OUTER JOIN usuario ON (evento.usuario = usuario.usuario) "
                            + "LEFT OUTER JOIN tipo_evento ON (evento.tipo_evento = tipo_evento.tipo_evento) "
                            + "WHERE "
                            + "fecha BETWEEN '" + t_fecha1 + "' and '" + t_fecha2 + "'";
                    if (user.equals("TODOS")) {

                    } else {
                        cadenasql = cadenasql + " and usuario.nombre = '" + user + "'";
                    }

                    if (Evento.equals("TODOS")) {

                    } else {
                        cadenasql = cadenasql + " and tipo_evento.nombre = '" + Evento + "'";
                    }

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
                    file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ReporteEventos_" + sdf.format(cal.getTime()) + ".xls");
                } else {
                    FacesMessage msg = new FacesMessage("Mensaje del sistema...", "Debe indicar los parametros en los campos.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage("Mensaje del sistema...", "El rango de dias debe ser menor a 6.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesMessage msg = new FacesMessage("Mensaje del sistema...", ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEvento() {
        return Evento;
    }

    public void setEvento(String Evento) {
        this.Evento = Evento;
    }

    public List<SelectItem> getLista_evento() {
        return Lista_evento;
    }

    public void setLista_evento(List<SelectItem> Lista_evento) {
        this.Lista_evento = Lista_evento;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public List<SelectItem> getLista_user() {
        return Lista_user;
    }

    public void setLista_user(List<SelectItem> Lista_user) {
        this.Lista_user = Lista_user;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
