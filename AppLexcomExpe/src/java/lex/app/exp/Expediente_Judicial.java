package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Judicial")
@ViewScoped
public class Expediente_Judicial implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private Integer estado_judicial;
    private Integer status_judicial;

    private String procuracion;
    private String situacion_caso;
    private String razon_notificacion;

    private Integer procurador;
    private Date fecha_juicio;
    private Integer juzgado;
    private String no_juicio;
    private Integer notificador;
    private String abogado_deudor;
    private String sumario;
    private Date memorial;
    private String notificado;
    private Date fecha_notif;
    private Double monto_demanda;

    private List<SelectItem> lst_estado_judicial;
    private List<SelectItem> lst_estatus_judicial;
    private List<SelectItem> lst_procurador;
    private List<SelectItem> lst_juzgado;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    private boolean somestadojudicial;
    private boolean somstatusjudicial;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            Driver drive = new Driver();
            this.estado_judicial = 0;
            this.status_judicial = 0;

            this.procuracion = "Texto de procuracion";
            this.situacion_caso = "Situacion Caso";
            this.razon_notificacion = "Razon de notificacion";

            this.procurador = 0;
            this.fecha_juicio = new Date();
            this.juzgado = 0;
            this.no_juicio = "JUI-000";
            this.notificador = 0;
            this.abogado_deudor = "Nombre del abogado del deudor";
            this.sumario = "Sumario";
            this.memorial = new Date();
            this.notificado = "SI";
            this.fecha_notif = new Date();
            this.monto_demanda = 0.00;
            this.com_judicial = "";

            String lista_estado_judicial_sql = "select s.sestado, s.nombre from sestado s where s.estado='VIGENTE'";
            this.lst_estado_judicial = drive.lista_SelectItem_simple(lista_estado_judicial_sql, this.ambiente);
            this.lst_estatus_judicial = new ArrayList<>();
            String lista_procurador_sql = "select u.usuario, u.nombre from usuario u where u.procurador='SI' and u.estado='VIGENTE'";
            this.lst_procurador = drive.lista_SelectItem_simple(lista_procurador_sql, this.ambiente);
            String lista_juzgado_sql = "select j.juzgado, j.nombre from juzgado j where j.estado='VIGENTE'";
            this.lst_juzgado = drive.lista_SelectItem_simple(lista_juzgado_sql, this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Judicial(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Judicial(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "d.sestado, " // rs.getObject(0);
                            + "d.estatus, " // rs.getObject(1);
                            + "d.situacion_caso, " // rs.getObject(2);
                            + "j.procuracion, " // rs.getObject(3);
                            + "j.razon_notificacion, " // rs.getObject(4);
                            + "j.procurador, " // rs.getObject(5);
                            + "j.fecha, " // rs.getObject(6);
                            + "j.juzgado, " // rs.getObject(7);
                            + "j.no_juicio, " // rs.getObject(8);
                            + "j.notificador, " // rs.getObject(9);
                            + "j.abogado_deudor, " // rs.getObject(10);
                            + "j.sumario, " // rs.getObject(11);
                            + "j.memorial, " // rs.getObject(12);
                            + "j.deudor_notificado, " // rs.getObject(13);
                            + "j.fecha_notificacion, " // rs.getObject(14);
                            + "j.monto, " // rs.getObject(15);
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(16)
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(17);
                            + "d.caso " // rs.getObject(18)
                            + "from "
                            + "deudor d "
                            + "left join actor a on (d.actor=a.actor) "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join juicio j on (d.deudor=j.deudor) "
                            + "where "
                            + "d.deudor=" + this.deudor;

                    Servicio servicio = new Servicio();
                    java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

                    Integer filas = resultado.size();
                    Integer columnas = resultado.get(0).getItem().size();
                    String[][] vector_result = new String[resultado.size()][columnas];
                    for (Integer i = 0; i < resultado.size(); i++) {
                        for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                            vector_result[i][j] = resultado.get(i).getItem().get(j);
                        }
                    }

                    Integer caso = 0;
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    for (Integer i = 1; i < filas; i++) {
                        this.estado_judicial = Integer.parseInt(vector_result[i][0]);
                        this.cambio_estado_judicial();
                        this.status_judicial = Integer.parseInt(vector_result[i][1]);
                        this.situacion_caso = vector_result[i][2];
                        this.procuracion = vector_result[i][3];
                        this.razon_notificacion = vector_result[i][4];
                        this.procurador = Integer.parseInt(vector_result[i][5]);
                        this.fecha_juicio = formatDate.parse(vector_result[i][6]);
                        this.juzgado = Integer.parseInt(vector_result[i][7]);
                        this.no_juicio = vector_result[i][8];
                        this.notificador = Integer.parseInt(vector_result[i][9]);
                        this.abogado_deudor = vector_result[i][10];
                        this.sumario = vector_result[i][11];
                        this.memorial = formatDate.parse(vector_result[i][12]);
                        this.notificado = vector_result[i][13];
                        this.fecha_notif = formatDate.parse(vector_result[i][14]);
                        this.monto_demanda = Double.parseDouble(vector_result[i][15]);
                        this.com_judicial = vector_result[i][16];
                        this.com_extrajudicial = vector_result[i][17];
                        caso = Integer.parseInt(vector_result[i][18]);
                    }

                    this.somestadojudicial = true;
                    this.somstatusjudicial = true;

                    String esAsistente = driver.getString("select u.asistente from usuario u where u.usuario=" + id_usuario, this.ambiente);
                    String esProcurador = driver.getString("select u.procurador from usuario u where u.usuario=" + id_usuario, this.ambiente);
                    String esDigitador = driver.getString("select u.digitador from usuario u where u.usuario=" + id_usuario, this.ambiente);

                    if (esAsistente.equals("SI")) {
                        this.somestadojudicial = false;
                        this.somstatusjudicial = false;
                    }
                    if (esProcurador.equals("SI") || esDigitador.equals("SI")) {
                        this.somestadojudicial = false;
                        this.somstatusjudicial = false;
                    }

                    this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";

                    RequestContext.getCurrentInstance().execute("PF('var_exp_judic').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Judicial(Cargar_Expediente_Judicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cambio_estado_judicial() {
        try {
            String cadenasql = "select "
                    + "distinct e.estatus, e.nombre "
                    + "from "
                    + "estado_status_judicial esj "
                    + "left join estatus e on (esj.estatus=e.estatus) "
                    + "left join sestado s on (esj.sestado=s.sestado) "
                    + "where "
                    + "s.sestado=" + this.estado_judicial + " "
                    + "order by "
                    + "e.nombre";
            Driver driver = new Driver();
            this.lst_estatus_judicial = driver.lista_SelectItem_simple(cadenasql, this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Judicial(cambio_estado_judicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void guardar_expediente_judicial() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha_juicio.getYear() + 1900, this.fecha_juicio.getMonth(), this.fecha_juicio.getDate());
            XMLGregorianCalendar gre_fecha_juicio = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            GregorianCalendar gregory2 = new GregorianCalendar();
            gregory2.set(this.memorial.getYear() + 1900, this.memorial.getMonth(), this.memorial.getDate());
            XMLGregorianCalendar gre_memorial = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);

            GregorianCalendar gregory3 = new GregorianCalendar();
            gregory3.set(this.fecha_notif.getYear() + 1900, this.fecha_notif.getMonth(), this.fecha_notif.getDate());
            XMLGregorianCalendar gre_fecha_notif = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

            Servicio servicio = new Servicio();
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.guardarExpedienteJudicial(id_usuario, this.deudor, this.estado_judicial, this.status_judicial, this.procurador, gre_fecha_juicio, this.juzgado, this.no_juicio, this.notificador, this.abogado_deudor, this.sumario, gre_memorial, this.notificado, gre_fecha_notif, this.monto_demanda, this.procuracion, this.situacion_caso, this.razon_notificacion, this.ambiente);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Judicial(guardar_expediente_judicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
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

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public Integer getEstado_judicial() {
        return estado_judicial;
    }

    public void setEstado_judicial(Integer estado_judicial) {
        this.estado_judicial = estado_judicial;
    }

    public Integer getStatus_judicial() {
        return status_judicial;
    }

    public void setStatus_judicial(Integer status_judicial) {
        this.status_judicial = status_judicial;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public String getSituacion_caso() {
        return situacion_caso;
    }

    public void setSituacion_caso(String situacion_caso) {
        this.situacion_caso = situacion_caso;
    }

    public String getRazon_notificacion() {
        return razon_notificacion;
    }

    public void setRazon_notificacion(String razon_notificacion) {
        this.razon_notificacion = razon_notificacion;
    }

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public Date getFecha_juicio() {
        return fecha_juicio;
    }

    public void setFecha_juicio(Date fecha_juicio) {
        this.fecha_juicio = fecha_juicio;
    }

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public String getAbogado_deudor() {
        return abogado_deudor;
    }

    public void setAbogado_deudor(String abogado_deudor) {
        this.abogado_deudor = abogado_deudor;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Date getMemorial() {
        return memorial;
    }

    public void setMemorial(Date memorial) {
        this.memorial = memorial;
    }

    public String getNotificado() {
        return notificado;
    }

    public void setNotificado(String notificado) {
        this.notificado = notificado;
    }

    public Date getFecha_notif() {
        return fecha_notif;
    }

    public void setFecha_notif(Date fecha_notif) {
        this.fecha_notif = fecha_notif;
    }

    public Double getMonto_demanda() {
        return monto_demanda;
    }

    public void setMonto_demanda(Double monto_demanda) {
        this.monto_demanda = monto_demanda;
    }

    public List<SelectItem> getLst_estado_judicial() {
        return lst_estado_judicial;
    }

    public void setLst_estado_judicial(List<SelectItem> lst_estado_judicial) {
        this.lst_estado_judicial = lst_estado_judicial;
    }

    public List<SelectItem> getLst_estatus_judicial() {
        return lst_estatus_judicial;
    }

    public void setLst_estatus_judicial(List<SelectItem> lst_estatus_judicial) {
        this.lst_estatus_judicial = lst_estatus_judicial;
    }

    public List<SelectItem> getLst_procurador() {
        return lst_procurador;
    }

    public void setLst_procurador(List<SelectItem> lst_procurador) {
        this.lst_procurador = lst_procurador;
    }

    public List<SelectItem> getLst_juzgado() {
        return lst_juzgado;
    }

    public void setLst_juzgado(List<SelectItem> lst_juzgado) {
        this.lst_juzgado = lst_juzgado;
    }

    public String getCom_extrajudicial() {
        return com_extrajudicial;
    }

    public void setCom_extrajudicial(String com_extrajudicial) {
        this.com_extrajudicial = com_extrajudicial;
    }

    public String getCom_judicial() {
        return com_judicial;
    }

    public void setCom_judicial(String com_judicial) {
        this.com_judicial = com_judicial;
    }

    public String getTitulo_deudor() {
        return titulo_deudor;
    }

    public void setTitulo_deudor(String titulo_deudor) {
        this.titulo_deudor = titulo_deudor;
    }

    public boolean isSomestadojudicial() {
        return somestadojudicial;
    }

    public void setSomestadojudicial(boolean somestadojudicial) {
        this.somestadojudicial = somestadojudicial;
    }

    public boolean isSomstatusjudicial() {
        return somstatusjudicial;
    }

    public void setSomstatusjudicial(boolean somstatusjudicial) {
        this.somstatusjudicial = somstatusjudicial;
    }

}
